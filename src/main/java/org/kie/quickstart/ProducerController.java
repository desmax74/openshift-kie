package org.kie.quickstart;

import java.util.List;
import java.util.Properties;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.kie.quickstart.pubsub.consumer.ConsumerConfig;
import org.kie.quickstart.pubsub.model.MyEvent;
import org.kie.quickstart.pubsub.producer.EventProducer;
import org.kie.quickstart.pubsub.utils.RecordMetadataUtil;

public class ProducerController {

    private static Properties properties;

    static {
        properties = new Properties();
        properties.put("valueSerializer", "org.kie.quickstart.pubsub.EventJsonSerializer");
    }


    public RecordMetadata create(MyEvent event) {
        EventProducer<MyEvent> eventProducer = new EventProducer<>();
        eventProducer.start(properties);
        RecordMetadata lastRecord = eventProducer.produceSync(new ProducerRecord<>(ConsumerConfig.USERS_INPUT_TOPIC, event.getId(), event));
        RecordMetadataUtil.prettyPrinter(lastRecord);
        eventProducer.stop();
        return lastRecord;
    }

    public RecordMetadata create(List<MyEvent> events) {
        EventProducer<MyEvent> eventProducer = new EventProducer<>();
        RecordMetadata lastRecord = null;
        for(MyEvent event: events) {
            eventProducer.start(properties);
            lastRecord = eventProducer.produceSync(new ProducerRecord<>(ConsumerConfig.USERS_INPUT_TOPIC,
                                                                                       event.getId(),
                                                                                       event));
            RecordMetadataUtil.prettyPrinter(lastRecord);
        }
        eventProducer.stop();
        return lastRecord;
    }


}
