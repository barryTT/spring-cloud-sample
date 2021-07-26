package hystrix;

import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ServerListFilter;
import hystrix.ribbon.CustomServerListFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

/**
 * Created by barry on 2016/8/16.
 */
@SpringBootApplication
@EnableCircuitBreaker
@EnableDiscoveryClient
public class HystrixClientApplication {

    public static void main(String args[]) {
        SpringApplication.run(HystrixClientApplication.class, args);
//        new SpringApplicationBuilder(SampleApplication.class).web(true).run(args);
    }

    @Bean
    public ServerListFilter<Server> ribbonServerListFilter() {
        return new CustomServerListFilter();
    }
}
