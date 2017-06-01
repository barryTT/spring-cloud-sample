package consumer.feign;

import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * Created by barry on 2017/3/27.
 */
public class HystrixClientFallbackFactory implements FallbackFactory<MessageClient> {

    private Logger logger = LoggerFactory.getLogger(HystrixClientFallback.class);

    @Override
    public MessageClient create(final Throwable cause) {

        return new MessageClient() {
            @Override
            public String sendMessage(ArrayList<String> list) {
                logger.error("create fail client", cause);
                return "fallbackFactory";
            }

            @Override
            public String exception() {
                logger.error("create fail client", cause);
                return "failbackFactory";
            }
        };
    }
}
