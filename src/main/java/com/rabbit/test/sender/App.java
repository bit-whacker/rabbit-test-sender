package com.rabbit.test.sender;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lacheff.commonutil.rabbitmq.RabbitConnectionManager;
import com.lacheff.commonutil.rabbitmq.RabbitMessageConsumer;
import com.rabbit.test.sender.rabbitmq.RabbitGateManager;

public class App 
{
    private static Logger LOG = LogManager.getRootLogger();
    private RabbitConnectionManager rabbitConnectionManager = RabbitGateManager.getInstance().getRabbitConnectionManager();
    
    public static void main( String[] args )
    {
        LOG.info( "Hello World!" );
        App app = new App();
        app.sleepUntilRabbitConnectionSucceed();
        
        Thread rabbitTestThread = new Thread(new TestRunnable(), "TestSenderRunnable");
        rabbitTestThread.start();
    }
    
    private void sleepUntilRabbitConnectionSucceed() {
        for (;;) {
            RabbitMessageConsumer rConsumer = rabbitConnectionManager.getRabbitMessageConsumer();
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
