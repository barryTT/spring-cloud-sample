package hystrix.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.net.URI;

/**
 * Created by barry on 2017/6/13.
 */
@RestController
public class StoreController {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private LoadBalancerClient loadBalancer;

    private String uri = "http://localhost:8081/stores";

    @RequestMapping(path = "/")
    @HystrixCommand(fallbackMethod = "defaultStores", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")
    })
    public Object getStores() {

        URI storesUri = null;

        ServiceInstance instance = null;
        try {
            instance = loadBalancer.choose("provider");
            if (instance != null) {
                storesUri = URI.create(String.format("http://%s:%s", instance.getHost(),
                        instance.getPort()));
            }
        } catch (RuntimeException e) {
            logger.error("", e);
        }

        return storesUri;
    }

    public String defaultStores() {
        return uri;
    }
}
