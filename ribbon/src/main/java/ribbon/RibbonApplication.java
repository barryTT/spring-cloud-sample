package ribbon;

import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ServerList;
import com.netflix.loadbalancer.ZoneAwareLoadBalancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

/**
 * Created by barry on 2016/8/16.
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
@ComponentScan(
// Exclude @Configuration classes that should be included only in sub contexts created
// by Ribbon's SpringClientFactory.
        excludeFilters = { @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*RibbonConfig") })
@RibbonClients(defaultConfiguration = DefaultRibbonConfig.class)
public class RibbonApplication {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

//    @Autowired
//    private SpringClientFactory clientFactory;

    /**
     * Throws exception if the SpringClientFactory doesn't return a balancer with a server
     * list of the expected type.
     *
     */
//    @PostConstruct
//    public void test() throws Exception {
//        @SuppressWarnings("unchecked")
//        ZoneAwareLoadBalancer<Server> lb = (ZoneAwareLoadBalancer<Server>) this.clientFactory.getLoadBalancer("COMPUTE-SERVICE");
//
//        ServerList<Server> serverList = lb.getServerListImpl();
//        if (!(serverList instanceof DefaultRibbonConfig.BazServiceList)) {
//            throw new Exception("wrong server list type");
//        }
//    }

    public static void main(String args[]) {
        SpringApplication.run(RibbonApplication.class, args);
//        new SpringApplicationBuilder(SampleApplication.class).web(true).run(args);
    }
}
