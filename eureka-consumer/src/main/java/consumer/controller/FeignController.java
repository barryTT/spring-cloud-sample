package consumer.controller;

import consumer.feign.MessageClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.security.SecureRandom;
import java.util.Collections;

/**
 * Created by barry on 2017/3/3.
 */
@RestController
public class FeignController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final SecureRandom secureRandom = new SecureRandom();

    @Resource
    private MessageClient client;

//    @Resource
//    private DiscoveryClient discoveryClient;

    @RequestMapping(path = "/test")
    public String test() {
//        List<ServiceInstance> provider = discoveryClient.getInstances("provider");

        int random = secureRandom.nextInt(99);

        LOGGER.info("random number:{}", random);

        if (random > 90) {
            throw new RuntimeException("random exception");
        }

        return String.format("consumer random:%d, %s", random, client.sendMessage(Collections.emptyList()));
    }

    @RequestMapping(path = "/exception")
    public String exception() {
        return client.exception();
    }
}
