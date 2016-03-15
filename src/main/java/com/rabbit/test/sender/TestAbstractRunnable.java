package com.rabbit.test.sender;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public abstract class TestAbstractRunnable implements Runnable {
    private static Logger LOG = LogManager.getRootLogger();
    private static final int SLEEP_ON_EXCEPTION_THROWN_MS = 5 * 1000;
    
    @Override
    public void run() {
        LOG.info("RabbitTestSender is Running!!!");
        try {
            testInfiniteLoop();
        } catch (InterruptedException e) {
            LOG.error("InterruptedException at top level: ", e);
        }
        LOG.warn("RabbitTestSending is Completed!!!");
    }
    
    private void testInfiniteLoop() throws InterruptedException {
        while (true) {
            try {
                startSendingTestMessages();
            } catch (Exception ex) {
                if (ex instanceof InterruptedException) {
                    LOG.info("InterruptedException at testInfiniteLoopMain");
                    throw ex;
                }
                LOG.error("Exception at testInfiniteLoopMain:", ex);
                Thread.sleep(SLEEP_ON_EXCEPTION_THROWN_MS);
            }
        }
    }
    
    protected abstract void startSendingTestMessages() throws InterruptedException;

}
