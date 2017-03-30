package org.springframework.cloud.sample.hystrix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

/**
 * Created by barry on 2016/8/16.
 */
@SpringBootApplication
@EnableCircuitBreaker
public class HystrixClientApplication {
    public static void main(String args[]) {
        SpringApplication.run(HystrixClientApplication.class, args);
//        new SpringApplicationBuilder(SampleApplication.class).web(true).run(args);
    }
}
