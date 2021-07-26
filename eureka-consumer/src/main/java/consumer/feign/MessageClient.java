package consumer.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by barry on 2017/3/3.
 */
@FeignClient(
        name = "provider",
//        fallback = HystrixClientFallback.class
        fallbackFactory = HystrixClientFallbackFactory.class,
        configuration = MessageConfiguration.class
)
public interface MessageClient {

    @RequestMapping(value = "/send" ,method = RequestMethod.POST)
    String sendMessage(@RequestParam("dddd") List<String> list);

    @RequestMapping(value = "/exception")
    String exception();
}


