package org.kie.quickstart.endpoint;

import java.util.List;
import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.kafka.common.PartitionInfo;
import org.kie.quickstart.MyEventConsumerApp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/sub")
public class ConsumerEndpoint {

    private Logger logger = LoggerFactory.getLogger(ProducerEndpoint.class);

    @GET
    @Path("/demo/{consumerNumber}")
    @Produces(MediaType.TEXT_PLAIN)
    public String demo(@PathParam("consumerNumber") Integer consumerNumber) {
        logger.info("Requested {} consumers", consumerNumber);
        MyEventConsumerApp.businessLogic(consumerNumber);
        return "started" +consumerNumber + " consumer";
    }

    @GET
    @Path("/demo")
    @Produces(MediaType.TEXT_PLAIN)
    public String demo() {
        logger.info("Requested {} consumers", 3);
        MyEventConsumerApp.businessLogic(3);
        return "started 3 consumer";
    }

    @GET
    @Path("/topics")
    @Produces(MediaType.TEXT_PLAIN)
    public String topics() {
        logger.info("Topics");
        Map<String, List<PartitionInfo>> topics = MyEventConsumerApp.getTopics();
        StringBuilder sb = new StringBuilder();
        for(Map.Entry entry :topics.entrySet()){
            sb.append("Key:").append(entry.getKey()).append(":]");
            List<PartitionInfo> itemValues = (List<PartitionInfo>)entry.getValue();
            for(PartitionInfo info: itemValues){
                sb.append(info.toString());
            }
            sb.append("]");
        }
        return sb.toString();
    }
}
