package com.nus.edtech.blogs.listener;

import com.amazon.sqs.javamessaging.AmazonSQSMessagingClientWrapper;
import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnection;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;

import com.nus.edtech.blogs.models.Interaction;
import com.nus.edtech.blogs.services.BlogsService;
import com.nus.edtech.dto.BlogsInteractionDto;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.jms.*;

import org.springframework.web.client.RestTemplate;

public class MessageQueueListener implements Runnable {
    MessageConsumer consumer = null;
    protected Session session = null;

    private static final String BLOG_SERVICE_URL = "http://a7c8c8eadfccd4c229643034e64e7566-632117854.ap-southeast-1.elb.amazonaws.com:9001/v1/blogs/blog/";

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public void run() {
        try {
            // Create a new connection factory with all defaults (credentials and region) set automatically
            SQSConnectionFactory connectionFactory = new SQSConnectionFactory(
                    new ProviderConfiguration(),
                    AmazonSQSClientBuilder.defaultClient()
            );

            // Create the connection.
            SQSConnection connection = connectionFactory.createConnection();

            // Get the wrapped client
            AmazonSQSMessagingClientWrapper client = connection.getWrappedAmazonSQSClient();

            // Create an SQS queue named MyQueue, if it doesn't already exist
            if (!client.queueExists("BlogsQueue")) {
                client.createQueue("BlogsQueue");
            }

            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue("BlogsQueue");

            // Create a consumer for the 'MyQueue'
            consumer = session.createConsumer(queue);

            // Start receiving incoming messages
            connection.start();

            // Receive a message from 'MyQueue' and wait up to 1 second
            Message receivedMessage = consumer.receive(1000);

            // Cast the received message as TextMessage and display the text
            if (receivedMessage != null) {
                BlogsInteractionDto blogsInteraction = (BlogsInteractionDto)((ObjectMessage) receivedMessage).getObject();
                System.out.println("Received: " + blogsInteraction);

                Interaction interaction = new Interaction();
                interaction.setInteractionId(blogsInteraction.getInteractionId());
                interaction.setInteractionType(blogsInteraction.getInteractionType());
                interaction.setInteractionValue(blogsInteraction.getInteractionValue()+"");

                restTemplate.put(BLOG_SERVICE_URL + blogsInteraction.getBlogId() + "/interaction", interaction, String.class);
            }
        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            try {
                consumer.close();
                session.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}