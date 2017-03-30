package org.springframework.cloud.sample.consumer;

import com.google.common.collect.Lists;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by barry on 2017/3/3.
 */
@RestController
public class FeiginController {
    @Resource
    private MessageClient client;

//    @Resource
//    private DiscoveryClient discoveryClient;

    @RequestMapping(path = "/test")
    public String test() {
//        List<ServiceInstance> provider = discoveryClient.getInstances("provider");
        return client.sendMessage(Lists.<String>newArrayList());
    }
}
