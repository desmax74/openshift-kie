/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kie.quickstart.pubsub.consumer;

import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsumerConfig {

    public static final String MASTER_TOPIC = "master.events";
    public static final String USERS_INPUT_TOPIC = "users.input.events";
    public static final String MY_CLUSTER_KAFKA_BOOTSTRAP_SERVICE_HOST = "MY_CLUSTER_KAFKA_BOOTSTRAP_SERVICE_HOST";
    public static final String MY_CLUSTER_KAFKA_BOOTSTRAP_SERVICE_PORT = "MY_CLUSTER_KAFKA_BOOTSTRAP_SERVICE_PORT";
    public static final String BROKER_LIST = System.getenv("kafka.broker.list") != null ? System.getenv("kafka.broker.list") : "localhost:9092,localhost:9093,localhost:9094";
    private static final Logger logger = LoggerFactory.getLogger(ConsumerConfig.class);

    public static Properties getConfig(String groupId,
                                       String valueSerializerClassName,
                                       boolean autoCommit) {
        Properties producerProperties = new Properties();
        producerProperties.put("bootstrap.servers",
                               BROKER_LIST);
        producerProperties.put("group.id",
                               groupId);
        producerProperties.put("key.deserializer",
                               "org.apache.kafka.common.serialization.StringDeserializer");
        producerProperties.put("value.deserializer",
                               valueSerializerClassName);
        producerProperties.setProperty("enable.auto.commit",
                                       String.valueOf(autoCommit));
        logConfig(producerProperties);
        return producerProperties;
    }

    private static void logConfig(Properties producerProperties) {
        if (logger.isInfoEnabled()) {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<Object, Object> entry : producerProperties.entrySet()) {
                sb.append(entry.getKey().toString()).append(":").append(entry.getValue());
            }
            logger.info(sb.toString());
        }
    }
}
