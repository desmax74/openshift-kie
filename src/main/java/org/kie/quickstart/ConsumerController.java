package org.kie.quickstart;

import org.kie.quickstart.pubsub.consumer.ConsumerThread;
import org.kie.quickstart.pubsub.consumer.EmptyConsumerHandler;
import org.kie.quickstart.pubsub.model.MyEvent;

public class ConsumerController {

    private final String TOPIC = "orders";

    public void consumeEvents(int numberOfConsumer, String groupName, int duration, int pollSize) {
        for(int i = 0; i < numberOfConsumer; i++) {
            Thread t = new Thread(
                    new ConsumerThread<MyEvent>(
                            String.valueOf(i),
                            groupName,
                            TOPIC,
                            "com.redhat.kafka.order.process.consumer.OrderEventJsonDeserializer",
                            pollSize,
                            duration,
                            false ,
                            true,
                            true,
                            new EmptyConsumerHandler()));
            t.start();
        }
    }
}
