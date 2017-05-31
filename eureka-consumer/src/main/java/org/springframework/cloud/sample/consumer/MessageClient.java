package org.springframework.cloud.sample.consumer;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.sample.consumer.HystrixClientFallbackFactory;
import org.springframework.cloud.sample.consumer.MessageConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

/**
 * Created by barry on 2017/3/3.
 */
@FeignClient(
        name = "provider",
//        fallback = HystrixClientFallback.class
        fallbackFactory = HystrixClientFallbackFactory.class
//        configuration = MessageConfiguration.class
)
public interface MessageClient {

    @RequestMapping(value = "/send" ,method = RequestMethod.POST)
    String sendMessage(@RequestParam("dddd") ArrayList<String> list);

    @RequestMapping(value = "/exception")
    String exception();
}


