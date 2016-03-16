package com.rabbit.test.sender;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lacheff.commonutil.rabbitmq.RabbitConnectionManager;
import com.lacheff.commonutil.rabbitmq.RabbitMessageConsumer;
import com.rabbit.test.sender.rabbitmq.RabbitGateManager;

public class App 
{
    private static Logger LOG = LogManager.getRootLogger();
    private RabbitConnectionManager rcm;
    
    public static void main( String[] args ) throws InterruptedException  {
        App app = new App();
        app.initPro();
    }
    
    private void initPro() throws InterruptedException{
        rcm = RabbitGateManager.getInstance().getRabbitConnectionManager();
        LOG.info("getting rabbitConnectionManager ready!!!");
        Thread.sleep(5000);
        sleepUntilRabbitConnectionSucceed();
        
        LOG.info("RabbitMq is now ready to send and recieve messages");
        Thread rabbitTestThread = new Thread(new TestRunnable(), "TestSenderRunnable");
        rabbitTestThread.start();
    }
    
    /**
     * wait until rabbitConnectionManager is ready
     */
    private void sleepUntilRabbitConnectionSucceed() {
        for (;;) {
            RabbitMessageConsumer rConsumer = rcm.getRabbitMessageConsumer();
            if (rConsumer == null) {
                LOG.info("rConsumer is null. Rabbit connection is not ready yeat");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    LOG.error("InterruptedException e");
                }
            } else {
                return;
            }
        }
    }
}
