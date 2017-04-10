package org.springframework.cloud.sample;

import com.netflix.ribbon.proxy.annotation.Hystrix;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;

/**
 * Created by barry on 2017/3/3.
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableCircuitBreaker
public class ConsumerApplication {
    public static void main(String args[]) {
        SpringApplication.run(ConsumerApplication.class, args);
    }

    /**
     * 抽样
     */
    @Bean
    public AlwaysSampler defaultSampler() {
        return new AlwaysSampler();
    }

//    @Bean
//    public ZipkinAspect getZipkinAspect(BeanFactory beanFactory) {
//        return new ZipkinAspect(beanFactory);
////        .aspectOf(ZipkinAspect.class);
//    }
}

