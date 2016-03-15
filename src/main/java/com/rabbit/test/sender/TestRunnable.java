/**
 * 
 */
package com.rabbit.test.sender;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lacheff.commonutil.rabbitmq.RabbitGate;
import com.lacheff.commonutil.rabbitmq.request.NlpProcessingRequest;
import com.rabbit.test.sender.rabbitmq.RabbitGateManager;

public class TestRunnable extends TestAbstractRunnable {
    private static Logger LOG = LogManager.getRootLogger();
    private RabbitGate rabbitGate = RabbitGateManager.getInstance().getRabbitGate();
    
    static int counter = 0;
    @Override
    protected void startSendingTestMessages() throws InterruptedException {
        LOG.info("Sending Test Message");
        NlpProcessingRequest nlpProcessingRequest = new NlpProcessingRequest();
        nlpProcessingRequest.setReviewUUID(new Date() + "");
        rabbitGate.send(nlpProcessingRequest);
        if(++counter == 5){
            LOG.info("Now Exiting...");
            throw new InterruptedException("Exiting thread");
        }
    }

}
