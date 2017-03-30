package org.springframework.cloud.sample.ribbon;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by barry on 2016/8/19.
 */
@Configuration
public class DefaultRibbonConfig {
    @Bean
    public IRule ribbonRule() {
        return new BestAvailableRule();
    }
//
    @Bean
    public IPing ribbonPing() {
        return new PingUrl();
    }

    @Bean
    public ServerList<Server> ribbonServerList(IClientConfig config) {
        return new DefaultRibbonConfig.BazServiceList(config);
    }
//
//    @Bean
//    public ServerListSubsetFilter<Server> serverListFilter() {
//        ServerListSubsetFilter<Server> filter = new ServerListSubsetFilter<Server>();
//        return filter;
//    }
//
    public static class BazServiceList extends ConfigurationBasedServerList {
        public BazServiceList(IClientConfig config) {
            super.initWithNiwsConfig(config);
        }
    }
}
