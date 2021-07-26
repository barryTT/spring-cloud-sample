package ribbon.controller;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ServerList;
import com.netflix.loadbalancer.ZoneAwareLoadBalancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import ribbon.DefaultRibbonConfig;

import javax.annotation.Resource;

/**
 * Created by barry on 2016/8/18.
 */
@RestController
public class RibbonController {
    @Resource
    private RestTemplate client;

    @Resource
    private EurekaClient discoveryClient;

    @RequestMapping("/service")
    public String serviceUrl() {
        InstanceInfo instance = discoveryClient.getNextServerFromEureka("provider", false);
        return instance.getHomePageUrl();
    }
//    @Autowired
//    private SpringClientFactory clientFactory;

    @RequestMapping("/")
    @HystrixCommand(fallbackMethod = "fallbackMethod", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "120000")
    })
    public Integer first() {

        Object[] urlVariables = {1, 2};

        ResponseEntity<Integer> entity = this.client.getForEntity("http://PROVIDER/add?a={0}&b={1}", Integer.class, 1, 2);
        return entity.getBody();
    }

    @RequestMapping("/{s}")
    @HystrixCommand(fallbackMethod = "fallbackMethod")
    public Integer second(@PathVariable String s) {

//        Object[] urlVariables = {1, s};

        ResponseEntity<Integer> entity = this.client.getForEntity("http://provider/add?a={1}&b={2}", Integer.class, 1, s);
        return entity.getBody();
    }

    public Integer fallbackMethod() {
        return Integer.MIN_VALUE;
    }

    public Integer fallbackMethod(String s) {
        return Integer.MIN_VALUE;
    }
}
