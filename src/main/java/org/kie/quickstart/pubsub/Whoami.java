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

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.coreos.jetcd.data.KeyValue;
import com.coreos.jetcd.kv.GetResponse;
import com.coreos.jetcd.kv.PutResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Ask to the etc if is the leader/master or not to decide to produce or consume
 */
public class Whoami {

    private static final String MASTER_POD = "MASTER_POD";
    private Logger logger = LoggerFactory.getLogger(Whoami.class);
    private EtcdClient etcdClient;

    public Whoami() {
        etcdClient = new EtcdClient();
        etcdClient.startClient();
    }

    public boolean amIMaster(String podID) {
        CompletableFuture<GetResponse> cfResp = etcdClient.getKey(MASTER_POD);
        GetResponse res = null;
        try {
            res = cfResp.get();
        } catch (Exception e) {
            logger.error(e.getMessage(),
                         e);
        }
        if (res == null) {
            return false;
        }
        List<KeyValue> values = res.getKvs();
        if (values.size() > 1) {
            logger.error("more than once master pod");
        }
        KeyValue value = values.get(0);
        return podID.equals(value.getKey().toStringUtf8());
    }

    public boolean iAmTheMaster(String podID){
        CompletableFuture<PutResponse> cfPutResponse = etcdClient.putKey(MASTER_POD, podID);
        PutResponse putResponse = null;
        try {
            putResponse = cfPutResponse.get();
        } catch (Exception e) {
            logger.error(e.getMessage(),
                         e);
        }
        if (putResponse == null) {
            return false;
        }
        if(putResponse.hasPrevKv()){
            KeyValue previousMaster = putResponse.getPrevKv();
            return ! previousMaster.getValue().toStringUtf8().equals(podID);
        }else{
            return true;
        }
    }

}
