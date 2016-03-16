package com.rabbit.test.sender.rabbitmq;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lacheff.commonutil.rabbitmq.RabbitMessageHander;
import com.lacheff.commonutil.rabbitmq.response.NlpProcessingResponse;
import com.lacheff.commonutil.rabbitmq.response.NlpSummarizationResponse;
import com.lacheff.commonutil.rabbitmq.support.ExchangeType;
import com.lacheff.commonutil.rabbitmq.support.RoutingKey;

public class NlpProcessingResponseHandler implements RabbitMessageHander {
    private static Logger LOG = LogManager.getRootLogger();
    
    @Override
    public void handle(Object receivedObject) {
        LOG.debug("Received object of {} type", receivedObject.getClass());
        if (receivedObject instanceof NlpProcessingResponse) {
            handleObj((NlpProcessingResponse) receivedObject);
        } else if(receivedObject instanceof NlpSummarizationResponse) {
            handleNlpSummarizationResponse((NlpSummarizationResponse) receivedObject);
        } else {
            LOG.error("Handler for type = {} not  defined", receivedObject.getClass());
        }
    }

    @Override
    public List<Class> getHandlingTypes() {
        List<Class> list = new ArrayList<Class>();
        list.add(NlpProcessingResponse.class);
        list.add(NlpSummarizationResponse.class);
        return list;
    }

    @Override
    public ExchangeType getExchangeType() {
        return ExchangeType.NLP_PROCESSING;
    }

    @Override
    public RoutingKey getRoutingKey() {
        return RoutingKey.TO_WEBSITE_PROCESSOR;
    }
    
    private void handleObj(NlpProcessingResponse response) {
        LOG.info("NlpProcessingResponse handled successfully: {}", response);
    }
    
    private void handleNlpSummarizationResponse(NlpSummarizationResponse response){
        LOG.info("NlpSummarizationResponse handled successfully: {}", response);
    }

}
