package zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.route.ZuulFallbackProvider;
import org.springframework.context.annotation.Bean;
import zuul.provider.CustomZuulFallbackProvider;

/**
 * Created by barry on 2017/4/10.
 */
@SpringBootApplication
@EnableZuulProxy
public class ZuulApplication {
    public static void main(String args[]) {
        SpringApplication.run(ZuulApplication.class, args);
    }

    @Bean
    public ZuulFallbackProvider zuulFallbackProvider() {
        return new CustomZuulFallbackProvider();
    }
}
