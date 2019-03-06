package org.kie.quickstart;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.common.PartitionInfo;
import org.kie.quickstart.pubsub.consumer.BaseConsumer;
import org.kie.quickstart.pubsub.consumer.ConsumerConfig;
import org.kie.quickstart.pubsub.consumer.ConsumerThread;
import org.kie.quickstart.pubsub.consumer.EmptyConsumerHandler;
import org.kie.quickstart.pubsub.model.MyEvent;

public class ConsumerController {

    public void consumeEvents(int numberOfConsumer, String groupName, int duration, int pollSize) {
        for(int i = 0; i < numberOfConsumer; i++) {
            Thread t = new Thread(
                    new ConsumerThread<MyEvent>(
                            String.valueOf(i),
                            groupName,
                            ConsumerConfig.USERS_INPUT_TOPIC,
                            "org.kie.quickstart.pubsub.EventJsonSerializer",
                            pollSize,
                            duration,
                            false ,
                            true,
                            true,
                            new EmptyConsumerHandler()));
            t.start();
        }
    }

    public Map<String, List<PartitionInfo>> getTopics() {
        Properties properties = new Properties();
        properties.setProperty("desererializerClass", "org.kie.quickstart.pubsub.EventJsonSerializer");
        properties.put("bootstrap.servers", ConsumerConfig.BROKER_URL+":"+ConsumerConfig.BROKER_PORT);
        properties.put("group.id", "1");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.kie.quickstart.pubsub.EventJsonSerializer");
        properties.setProperty("enable.auto.commit",String.valueOf(true));
        BaseConsumer<MyEvent> consumer = new BaseConsumer<>("1", properties, new EmptyConsumerHandler());
        return consumer.getTopics();
    }
}
