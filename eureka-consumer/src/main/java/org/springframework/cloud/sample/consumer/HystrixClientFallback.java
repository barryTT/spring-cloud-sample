package org.springframework.cloud.sample.consumer;


import java.util.ArrayList;

public class HystrixClientFallback implements MessageClient {

    @Override
    public String sendMessage(ArrayList<String> list) {
        return "fail";
    }
}