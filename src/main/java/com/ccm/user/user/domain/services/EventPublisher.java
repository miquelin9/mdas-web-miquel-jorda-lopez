package com.ccm.user.user.domain.services;

import com.ccm.user.user.domain.entities.Message;
import com.ccm.user.user.domain.interfaces.EventPublisherClient;
import com.ccm.user.user.domain.interfaces.SimpleConverter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@ApplicationScoped
public class EventPublisher {

    @Inject
    @Named("RabbitMq")
    EventPublisherClient eventPublisherClient;

    @Inject
    @Named("JsonString")
    SimpleConverter<Object,String> simpleConverter;

    public void publish(Message message) throws IOException {
        String content = simpleConverter.convert(message.getContent());
        eventPublisherClient.publish(content);
    }
}
