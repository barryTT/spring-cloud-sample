package org.springframework.cloud.sample.hystrix.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class StoreIntegration {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @HystrixCommand(fallbackMethod = "defaultStores")
    public Object getStores(Map<String, Object> parameters) {
        return "getStores";
    }

    public Object defaultStores(Map<String, Object> parameters) {
        return "defaultStores";
    }

}