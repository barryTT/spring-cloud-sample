package org.springframework.cloud.sample.consumer;


import org.springframework.stereotype.Component;

import java.util.ArrayList;
@Component
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