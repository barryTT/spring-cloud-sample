package stream.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@EnableBinding(Source.class)
public class SendingController {
    @Autowired
    private Source source;

    @RequestMapping("/send")
    public String sendMessage(@RequestParam String message) {
        source.output().send(MessageBuilder.withPayload(message).build());

        return "OK";
    }


    @RequestMapping("/test")
    public String sendMessage() {


        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, Object> message = new LinkedHashMap<>();
        Map<String, Object> data = new LinkedHashMap<>();


        message.put("send_time", System.currentTimeMillis());

        message.put("data", data);


        data.put("company_id", 1);
        data.put("user_id", 351);
        data.put("staff_id", 62);
        data.put("name", "吕洋");
        data.put("mobile", "18443167555");
        data.put("company", "国美互联网生态（分享）科技公司");
        data.put("platForm", "web");
        data.put("version", "other");
        message.put("action", "CREATE");
        message.put("type", "oa.user");


        try {
            for (int i = 0; i < 1000; i++) {

                data.put("registerTime", System.currentTimeMillis());

                source.output().send(MessageBuilder.withPayload(objectMapper.writeValueAsString(message)).build());

            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return "OK";
    }
}