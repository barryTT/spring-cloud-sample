package org.springframework.cloud.sample.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;

/**
 * Created by barry on 2016/8/12.
 */
@SpringBootApplication
@EnableDiscoveryClient
public class EurekaClientApplication {
    public static void main(String args[]) {
        SpringApplication.run(EurekaClientApplication.class, args);
//        new SpringApplicationBuilder(SampleApplication.class).web(true).run(args);
    }
    @Bean
    public AlwaysSampler defaultSampler() {
        return new AlwaysSampler();
    }
}
