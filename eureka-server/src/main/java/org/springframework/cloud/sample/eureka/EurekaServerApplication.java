package org.springframework.cloud.sample.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Created by barry on 2016/8/12.
 */
@SpringBootApplication
@EnableEurekaServer
//@EnableDiscoveryClient
public class EurekaServerApplication {
    public static void main(String args[]) {
        SpringApplication.run(EurekaServerApplication.class, args);
//        new SpringApplicationBuilder(SampleApplication.class).web(true).run(args);
    }
}
