package consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Created by barry on 2017/3/3.
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class ConsumerApplication {
    public static void main(String args[]) {
        SpringApplication.run(ConsumerApplication.class, args);
    }

//    @Bean
//    public AlwaysSampler defaultSampler() {
//        return new AlwaysSampler();
//    }

//    @Bean
//    public ZipkinAspect getZipkinAspect(BeanFactory beanFactory) {
//        return new ZipkinAspect(beanFactory);
////        .aspectOf(ZipkinAspect.class);
//    }
}

