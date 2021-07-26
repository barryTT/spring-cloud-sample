package consumer.feign;

import java.util.List;

public class HystrixClientFallback implements MessageClient {

    @Override
    public String sendMessage(List<String> list) {
        return "fail";
    }

    @Override
    public String exception() {
        return "fail";
    }
}