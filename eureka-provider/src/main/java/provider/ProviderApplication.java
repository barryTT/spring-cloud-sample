package provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Created by barry on 2017/6/1.
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ProviderApplication {
    public static void main(String args[]) {
        SpringApplication.run(ProviderApplication.class, args);
//        new SpringApplicationBuilder(SampleApplication.class).web(true).run(args);
    }
}
