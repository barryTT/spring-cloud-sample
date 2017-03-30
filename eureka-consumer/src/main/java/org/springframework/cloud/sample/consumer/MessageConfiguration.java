package org.springframework.cloud.sample.consumer;

import org.springframework.cloud.sample.consumer.HystrixClientFallback;
import org.springframework.cloud.sample.consumer.HystrixClientFallbackFactory;
import org.springframework.context.annotation.Bean;

/**
 * Created by barry on 2017/3/27.
 */
public class MessageConfiguration {
    @Bean
    public MessageClient getHystrixClientFallback() {
        return new HystrixClientFallback();
    }

    @Bean
    public HystrixClientFallbackFactory getHystrixClientFallbackFactory() {
        return new HystrixClientFallbackFactory();
    }
}
