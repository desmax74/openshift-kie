package org.kie.quickstart;

import java.util.List;
import java.util.Properties;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.kie.quickstart.pubsub.consumer.PubSubConfig;
import org.kie.quickstart.pubsub.model.MyEvent;
import org.kie.quickstart.pubsub.producer.EventProducer;
import org.kie.quickstart.pubsub.utils.RecordMetadataUtil;

public class ProducerController {

    public RecordMetadata create(MyEvent event) {
        EventProducer<MyEvent> eventProducer = new EventProducer<>();
        eventProducer.start(PubSubConfig.getDefaultConfig());
        RecordMetadata lastRecord = eventProducer.produceSync(new ProducerRecord<>(PubSubConfig.MASTER_TOPIC, event.getId(), event));
        RecordMetadataUtil.prettyPrinter(lastRecord);
        eventProducer.stop();
        return lastRecord;
    }

    public RecordMetadata create(List<MyEvent> events) {
        EventProducer<MyEvent> eventProducer = new EventProducer<>();
        eventProducer.start(PubSubConfig.getDefaultConfig());
        RecordMetadata lastRecord = null;
        for(MyEvent event: events) {
            lastRecord = eventProducer.produceSync(new ProducerRecord<>(PubSubConfig.MASTER_TOPIC,
                                                                                       event.getId(),
                                                                                       event));
            RecordMetadataUtil.prettyPrinter(lastRecord);
        }
        eventProducer.stop();
        return lastRecord;
    }


}
