package provider.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

@RestController
@RefreshScope
public class ComputeController {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final SecureRandom secureRandom = new SecureRandom();

    @Resource
    private DiscoveryClient client;


    @Value("${spring.application.name}")
    private String serverId;

    @Value("${message.defaultValue:default}")
    private String defaultMessage;

    @RequestMapping(value = "/add" ,method = RequestMethod.GET)
    public Integer add(@RequestParam Integer a, @RequestParam Integer b) {
        List<ServiceInstance> instances = client.getInstances(serverId);
        for (ServiceInstance instance : instances) {
            LOGGER.info("/add, host:{}, , service_id:{}", instance.getHost(), instance.getServiceId());
        }
        return a + b;
    }

    @RequestMapping(value = "/send" ,method = RequestMethod.POST)
    public String sendMessage(ArrayList<String> list) {

        int random = secureRandom.nextInt(99);

        LOGGER.info("random number:{}", random);

        if (random > 90) {
            throw new RuntimeException("random exception");
        }

        return String.format("provider random:%d", random);
    }

    @RequestMapping(value = "/exception" ,method = RequestMethod.GET)
    public String exception(HttpServletRequest request, HttpServletResponse response) {

        response.setHeader("X_error_code", "0");
        response.setStatus(HttpServletResponse.SC_ACCEPTED);

        return "fail";
    }

    @RequestMapping(value = "message")
    public String getMessage() {
        return defaultMessage;
    }

}