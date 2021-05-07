package com.ccm.pokemon.pokemon.infrastructure.listener;

import com.rabbitmq.client.*;
import org.jboss.logging.Logger;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeoutException;

public abstract class BaseRabbitMqListener {

    private final Logger LOGGER = Logger.getLogger(BaseRabbitMqListener.class);
    private final static String CHARSET_NAME = "UTF-8";

    protected Connection connection;
    protected Channel channel;

    protected void configureListener(String queue, String host) throws IOException, TimeoutException {
        LOGGER.info("RabbitMqEventListener is about to be configured");
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        connection = factory.newConnection();
        channel = connection.createChannel();
        channel.queueDeclare(queue, true, false, false, null);
        DeliverCallback deliverCallback = this::onMessage;
        channel.basicConsume(queue, true, deliverCallback, consumerTag -> {});
    }

    protected void onMessage (String consumerTag, Delivery delivery) throws UnsupportedEncodingException {
        LOGGER.info("================= Message received =================");
        String message = new String(delivery.getBody(), CHARSET_NAME);
        handle(message);
    }

    abstract void handle(String message);
}
