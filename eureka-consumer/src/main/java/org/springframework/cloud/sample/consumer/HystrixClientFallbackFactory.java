package org.springframework.cloud.sample.consumer;

import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.sample.consumer.HystrixClientFallback;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Created by barry on 2017/3/27.
 */
@Component
public class HystrixClientFallbackFactory implements FallbackFactory<MessageClient> {

    private Logger logger = LoggerFactory.getLogger(HystrixClientFallback.class);

    @Override
    public MessageClient create(Throwable cause) {
        logger.error("create fail client", cause);
        return new MessageClient() {
            @Override
            public String sendMessage(ArrayList<String> list) {
                return "fallbackFactory";
            }

            @Override
            public String exception() {
                return "failbackFactory";
            }
        };
    }
}
