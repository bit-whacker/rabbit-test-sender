/**
 * 
 */
package com.rabbit.test.sender;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lacheff.commonutil.rabbitmq.RabbitGate;
import com.lacheff.commonutil.rabbitmq.request.NlpProcessingRequest;
import com.rabbit.test.sender.rabbitmq.RabbitGateManager;

public class TestRunnable extends TestAbstractRunnable {
    
    private static Logger LOG = LogManager.getRootLogger();
    private static final int SLEEP_TIME_MS = 2 * 1000;
    private RabbitGate rabbitGate = RabbitGateManager.getInstance().getRabbitGate();
    
    private static final int NUMBER_OF_MAX_MESSAGES_TO_DELIVER = 10;
    private static int testMessageCounter = 1;
    
    @Override
    protected void startSendingTestMessages() throws InterruptedException {
        
        NlpProcessingRequest nlpProcessingRequest = createNlpProcessingRequest();
        LOG.info("Sending Request[{}]: {}", testMessageCounter, nlpProcessingRequest);
        
        rabbitGate.send(nlpProcessingRequest);
        
        LOG.info("waiting for {} ms", SLEEP_TIME_MS);
        Thread.sleep(SLEEP_TIME_MS);
        
        if(++testMessageCounter > NUMBER_OF_MAX_MESSAGES_TO_DELIVER){
            LOG.info("MESSAGING LIMIT OF {} REACHED", NUMBER_OF_MAX_MESSAGES_TO_DELIVER);
            throw new InterruptedException("MESSAGING LIMIT REACHED");
        }
    }
    
    /**
     * Create Nlp Processing Request Here.
     * 
     * @return NlpProcessingRequest
     */
    private NlpProcessingRequest createNlpProcessingRequest(){
        
        NlpProcessingRequest nlpProcessingRequest = new NlpProcessingRequest();
        nlpProcessingRequest.setReviewUUID("UUID_" + testMessageCounter);
        nlpProcessingRequest.setReviewText("review text - " + testMessageCounter);
        
        return nlpProcessingRequest;
    }

}
