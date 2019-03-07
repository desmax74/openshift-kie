package org.kie.quickstart;

import java.util.List;
import java.util.Map;
import org.apache.kafka.common.PartitionInfo;
import org.kie.quickstart.pubsub.consumer.BaseConsumer;
import org.kie.quickstart.pubsub.consumer.ConsumerThread;
import org.kie.quickstart.pubsub.consumer.EmptyConsumerHandler;
import org.kie.quickstart.pubsub.consumer.PubSubConfig;
import org.kie.quickstart.pubsub.model.MyEvent;

public class ConsumerController {

    public void consumeEvents(int numberOfConsumer, String groupName, int duration, int pollSize) {
        for(int i = 0; i < numberOfConsumer; i++) {
            Thread t = new Thread(
                    new ConsumerThread<MyEvent>(
                            String.valueOf(i),
                            groupName,
                            PubSubConfig.MASTER_TOPIC,
                            "org.kie.quickstart.pubsub.utils.EventJsonSerializer",
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
        BaseConsumer<MyEvent> consumer = new BaseConsumer<>("1", PubSubConfig.getDefaultConfig(), new EmptyConsumerHandler());
        return consumer.getTopics();
    }
}
