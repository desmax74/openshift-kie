package org.kie.quickstart.pubsub.utils;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.kie.quickstart.pubsub.model.MyEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventJsonDeserializer implements Deserializer<MyEvent> {

  private Logger logger = LoggerFactory.getLogger(EventJsonSerializer.class);

  private ObjectMapper objectMapper;

  @Override
  public void configure(Map configs, boolean isKey) {
    this.objectMapper = new ObjectMapper();
  }


  @Override
  public MyEvent deserialize(String s, byte[] data) {
    try {
      return objectMapper.readValue(data, MyEvent.class);
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
    }
    return null;
  }

  @Override
  public void close() { }
}
