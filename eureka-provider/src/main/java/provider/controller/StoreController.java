package provider.controller;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Map;

/**
 * Created by barry on 2017/6/13.
 */
@RestController
public class StoreController {

    @RequestMapping(path = "/stores")
    public Object getStores(Map<String, Object> parameters) {

        return "stores";
    }
}
