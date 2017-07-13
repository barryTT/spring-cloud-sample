package hystrix;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ServerListFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.ribbon.PropertiesFactory;
import org.springframework.cloud.netflix.ribbon.ZonePreferenceServerListFilter;
import org.springframework.context.annotation.Bean;

/**
 * Created by barry on 2016/8/16.
 */
@SpringBootApplication
@EnableCircuitBreaker
@EnableDiscoveryClient
public class HystrixClientApplication {

//    @Value("${ribbon.client.name}")
//    private String name = "client";
//    @Autowired
//    private PropertiesFactory propertiesFactory;

    public static void main(String args[]) {
        SpringApplication.run(HystrixClientApplication.class, args);
//        new SpringApplicationBuilder(SampleApplication.class).web(true).run(args);
    }

//    @Bean
//    @SuppressWarnings("unchecked")
//    public ServerListFilter<Server> ribbonServerListFilter(IClientConfig config) {
//        if (this.propertiesFactory.isSet(ServerListFilter.class, name)) {
//            return this.propertiesFactory.get(ServerListFilter.class, config, name);
//        }
//        ZonePreferenceServerListFilter filter = new ZonePreferenceServerListFilter();
//        filter.initWithNiwsConfig(config);
//        return filter;
//    }
}
