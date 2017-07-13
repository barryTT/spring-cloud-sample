package stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.integration.annotation.ServiceActivator;

@EnableBinding(Sink.class)
public class Listener {

    private Logger logger = LoggerFactory.getLogger(Listener.class);

    public Listener() {
    }

    @ServiceActivator(inputChannel = Sink.INPUT)
//    @StreamListener(Sink.INPUT)
    public void process(Object payload) {
        logger.info("Received1: " + payload);
    }
}