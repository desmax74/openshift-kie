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
package org.kie.quickstart.pubsub;

import java.util.concurrent.CompletableFuture;

import com.coreos.jetcd.Client;
import com.coreos.jetcd.KV;
import com.coreos.jetcd.data.ByteSequence;
import com.coreos.jetcd.kv.DeleteResponse;
import com.coreos.jetcd.kv.GetResponse;
import com.coreos.jetcd.kv.PutResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EtcdClient {

    private final Logger logger = LoggerFactory.getLogger(EtcdClient.class);

    private Client client;
    private KV kvClient;

    public EtcdClient(){}

    public void startClient(){
        logger.info("Start etcd client");
        client = Client.builder().endpoints("http://localhost:2379").build();
        kvClient = client.getKVClient();
    }

    public void closeClient(){
        client.close();
        logger.info("Close etcd client");
    }

    public Client getClient(){
        return client;
    }

    public KV getKVClient(){
        return kvClient;
    }

    public CompletableFuture<PutResponse> putKey(String key, String value) {
        return kvClient.put(ByteSequence.fromString(key),
                     ByteSequence.fromString(value));
    }

    public CompletableFuture<GetResponse> getKey(String key){
        return kvClient.get(ByteSequence.fromString(key));
    }

    public CompletableFuture<DeleteResponse> deleteKey(String key){
        return kvClient.delete(ByteSequence.fromString(key));
    }


}
