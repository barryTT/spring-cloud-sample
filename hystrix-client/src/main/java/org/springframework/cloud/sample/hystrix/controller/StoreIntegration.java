package org.springframework.cloud.sample.hystrix.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.Map;

@Component
public class StoreIntegration {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private LoadBalancerClient loadBalancer;

    private String uri = "http://localhost:8081/stores";

    @HystrixCommand(fallbackMethod = "defaultStores")
    public Object getStores(Map<String, Object> parameters) {

        URI storesUri = URI.create(uri);

        ServiceInstance instance = null;
        try {
            instance = loadBalancer.choose("stores");
            if (instance != null) {
                storesUri = URI.create(String.format("http://%s:%s", instance.getHost(),
                        instance.getPort()));
            }
        }

        return "getStores";
    }

    public Object defaultStores(Map<String, Object> parameters) {
        return "defaultStores";
    }

}