package org.springframework.cloud.sample.consumer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.TraceKeys;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.cloud.sleuth.instrument.web.TraceRequestAttributes;
import org.springframework.cloud.sleuth.util.ExceptionUtils;
import org.springframework.cloud.sleuth.util.SpanNameUtil;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by barry on 2017/3/28.
 */
@Aspect
public class ZipkinAspect {

    private static final Log log = LogFactory.getLog(ZipkinAspect.class);

    private static final Map<String, Span> spanMap = new ConcurrentHashMap<String, Span>();

    private final BeanFactory beanFactory;

    private Tracer tracer;
    private TraceKeys traceKeys;

    private AtomicReference<ErrorController> errorController;

    public ZipkinAspect(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Around("execution(* org.springframework.cloud.sample.consumer.*.*(..))")
    public Object doAroundAdvice(ProceedingJoinPoint proceedingJoinPoint){

        Object handler = proceedingJoinPoint.getTarget();
        Signature signature = proceedingJoinPoint.getSignature();

        HttpServletRequest httpServletRequest = getHttpServletRequest();
        try {
            if (httpServletRequest != null) {
                preHandle(httpServletRequest, handler, signature);
            }
            return proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            if (httpServletRequest != null) {
                afterCompletion(httpServletRequest, handler, new Exception(throwable));
            }
        } finally {
            if (httpServletRequest != null) {
                afterCompletion(httpServletRequest, handler, null);
            }
        }
        return null;
    }

    private HttpServletRequest getHttpServletRequest() {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();

        if (ra != null) {
            if (ra instanceof ServletRequestAttributes) {
                ServletRequestAttributes sra = (ServletRequestAttributes) ra;
                return sra.getRequest();
            }
        }
        return null;
    }

    private boolean isErrorControllerRelated(HttpServletRequest request) {
        return getErrorController() != null && getErrorController().getErrorPath()
                .equals(request.getRequestURI());
    }

    protected void preHandle(HttpServletRequest request, Object handler, Signature signature) {
        String spanName = signature.getName();
        boolean continueSpan = getRootSpanFromAttribute(request) != null;
        Span span = continueSpan ? getRootSpanFromAttribute(request) : getTracer().createSpan(spanName);
        if (log.isDebugEnabled()) {
            log.debug("Handling span " + span);
        }
        addClassMethodTag(signature, span);
        addClassNameTag(handler, span);
        setSpanInAttribute(request, span);
        if (!continueSpan) {
            setNewSpanCreatedAttribute(request, span);
        }
    }

    protected void afterCompletion(HttpServletRequest request, Object handler, Exception ex) {
        if (isErrorControllerRelated(request)) {
            if (log.isDebugEnabled()) {
                log.debug("Skipping closing of a span for error controller processing");
            }
            return;
        }
        Span span = getRootSpanFromAttribute(request);
        if (ex != null) {
            String errorMsg = ExceptionUtils.getExceptionMessage(ex);
            if (log.isDebugEnabled()) {
                log.debug("Adding an error tag [" + errorMsg + "] to span " + span + "");
            }
            getTracer().addTag(Span.SPAN_ERROR_TAG_NAME, errorMsg);
        }
        if (getNewSpanFromAttribute(request) != null) {
            if (log.isDebugEnabled()) {
                log.debug("Closing span " + span);
            }
            Span newSpan = getNewSpanFromAttribute(request);
            getTracer().continueSpan(newSpan);
            getTracer().close(newSpan);
            clearNewSpanCreatedAttribute(request);
        }
    }

    private Span getNewSpanFromAttribute(HttpServletRequest request) {
        return (Span) request.getAttribute(TraceRequestAttributes.NEW_SPAN_REQUEST_ATTR);
    }

    private Span getRootSpanFromAttribute(HttpServletRequest request) {
        return (Span) request.getAttribute("org.springframework.cloud.sleuth.instrument.web.TraceFilter.TRACE");
    }

    private void addClassMethodTag(Signature signature, Span span) {
        String methodName = signature.getName();
        getTracer().addTag(getTraceKeys().getMvc().getControllerMethod(), methodName);
        if (log.isDebugEnabled()) {
            log.debug("Adding a method tag with value [" + methodName + "] to a span " + span);
        }
    }

    private void setSpanInAttribute(HttpServletRequest request, Span span) {
        request.setAttribute(TraceRequestAttributes.HANDLED_SPAN_REQUEST_ATTR, span);
    }

    private void setNewSpanCreatedAttribute(HttpServletRequest request, Span span) {
        request.setAttribute(TraceRequestAttributes.NEW_SPAN_REQUEST_ATTR, span);
    }
    private void clearNewSpanCreatedAttribute(HttpServletRequest request) {
        request.removeAttribute(TraceRequestAttributes.NEW_SPAN_REQUEST_ATTR);
    }

    private void addClassNameTag(Object handler, Span span) {
        String className;
        if (handler instanceof HandlerMethod) {
            className = ((HandlerMethod) handler).getBeanType().getSimpleName();
        } else {
            className = handler.getClass().getSimpleName();
        }
        if (log.isDebugEnabled()) {
            log.debug("Adding a class tag with value [" + className + "] to a span " + span);
        }
        getTracer().addTag(getTraceKeys().getMvc().getControllerClass(), className);
    }


    private String spanName(Object handler) {
        if (handler instanceof HandlerMethod) {
            return SpanNameUtil.toLowerHyphen(((HandlerMethod) handler).getMethod().getName());
        }
        return SpanNameUtil.toLowerHyphen(handler.getClass().getSimpleName());
    }

    private Tracer getTracer() {
        if (this.tracer == null) {
            this.tracer = this.beanFactory.getBean(Tracer.class);
        }
        return this.tracer;
    }

    private TraceKeys getTraceKeys() {
        if (this.traceKeys == null) {
            this.traceKeys = this.beanFactory.getBean(TraceKeys.class);
        }
        return this.traceKeys;
    }

    private ErrorController getErrorController() {
        if (this.errorController == null) {
            try {
                ErrorController errorController = this.beanFactory.getBean(ErrorController.class);
                this.errorController = new AtomicReference<ErrorController>(errorController);
            } catch (NoSuchBeanDefinitionException e) {
                if (log.isTraceEnabled()) {
                    log.trace("ErrorController bean not found");
                }
                this.errorController = new AtomicReference<ErrorController>();
            }
        }
        return this.errorController.get();
    }
}
