package consumer.feign;

import java.util.ArrayList;

public class HystrixClientFallback implements MessageClient {

    @Override
    public String sendMessage(ArrayList<String> list) {
        return "fail";
    }

    @Override
    public String exception() {
        return "fail";
    }
}