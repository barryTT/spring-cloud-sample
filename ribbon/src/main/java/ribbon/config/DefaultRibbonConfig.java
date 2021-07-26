package ribbon.config;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.*;
import com.netflix.niws.loadbalancer.DiscoveryEnabledNIWSServerList;
import com.netflix.niws.loadbalancer.NIWSDiscoveryPing;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.eureka.DomainExtractingServerList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Created by barry on 2016/8/19.
 */
@Configuration
public class DefaultRibbonConfig {
    @Bean
    public IRule ribbonRule() {
        return new IRule() {
            private ILoadBalancer loadBalancer;
            @Override
            public Server choose(Object key) {

                ILoadBalancer loadBalancer = getLoadBalancer();

                List<Server> allServers = loadBalancer.getAllServers();
                return null;
            }

            @Override
            public void setLoadBalancer(ILoadBalancer lb) {
                this.loadBalancer = lb;
            }

            @Override
            public ILoadBalancer getLoadBalancer() {
                return loadBalancer;
            }
        };
//        return new BestAvailableRule();
    }

    @Bean
    public IPing ribbonPing() {
        return new NIWSDiscoveryPing();
    }

//    @Bean
//    public ServerList<Server> ribbonServerList(IClientConfig config) {
//        return new DomainExtractingServerList(config);
//    }

    @Bean
    public ServerListSubsetFilter<Server> serverListFilter() {
        ServerListSubsetFilter<Server> filter = new ServerListSubsetFilter<Server>();
        return filter;
    }

    public static class BazServiceList extends ConfigurationBasedServerList {
        public BazServiceList(IClientConfig config) {
            super.initWithNiwsConfig(config);
        }
    }
}
