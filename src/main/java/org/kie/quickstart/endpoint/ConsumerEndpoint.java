package org.kie.quickstart.endpoint;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.kie.quickstart.MyEventProducerApp;
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
        MyEventProducerApp.businessLogic(consumerNumber);
        return "started" +consumerNumber + "consumer";
    }
}
