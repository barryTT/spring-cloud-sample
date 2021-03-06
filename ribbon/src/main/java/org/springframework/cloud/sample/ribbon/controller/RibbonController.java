package org.springframework.cloud.sample.ribbon.controller;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ServerList;
import com.netflix.loadbalancer.ZoneAwareLoadBalancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.cloud.sample.ribbon.DefaultRibbonConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created by barry on 2016/8/18.
 */
@RestController
public class RibbonController {
    @Autowired
    private RestTemplate client;

    @Autowired
    private EurekaClient discoveryClient;

    @RequestMapping("/service")
    public String serviceUrl() {
        InstanceInfo instance = discoveryClient.getNextServerFromEureka("COMPUTE-SERVICE", false);
        return instance.getHomePageUrl();
    }
//    @Autowired
//    private SpringClientFactory clientFactory;

    @RequestMapping("/")
    @HystrixCommand(fallbackMethod = "fallbackMethod")
    public Integer first() {

        Object[] urlVariables = {1, 2};

        ResponseEntity<Integer> entity = this.client.getForEntity("http://COMPUTE-SERVICE/add?a={1}&b={2}", Integer.class, urlVariables);
        return entity.getBody();
    }

    @RequestMapping("/{s}")
    @HystrixCommand(fallbackMethod = "fallbackMethod")
    public Integer second(@PathVariable String s) {

        Object[] urlVariables = {1, s};

        ResponseEntity<Integer> entity = this.client.getForEntity("http://COMPUTE-SERVICE/add?a={1}&b={2}", Integer.class, urlVariables);
        return entity.getBody();
    }

    public Integer fallbackMethod() {
        return Integer.MIN_VALUE;
    }

    public Integer fallbackMethod(String s) {
        return Integer.MIN_VALUE;
    }
}
