package com.ccm.user.user.domain.services;

import com.ccm.user.user.domain.entities.Message;
import com.ccm.user.user.domain.interfaces.EventPublisherClient;
import com.ccm.user.user.domain.vo.FavouritePokemonId;
import com.ccm.user.user.infrastructure.converter.ObjectToJsonStringConverter;
import com.ccm.user.user.infrastructure.rabbitmq.RabbitMqEventPublisher;
import io.quarkus.test.junit.QuarkusMock;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.inject.Inject;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@QuarkusTest
public class EventPublisherTest {

    @Inject
    EventPublisher tested;

    @Test
    public void verify_publish_callsMethods_whenMessageIsPassed () throws IOException {
        Message message = new Message(new FavouritePokemonId(1));

        EventPublisherClient eventPublisherClient = Mockito.mock(RabbitMqEventPublisher.class);
        Mockito.doNothing().when(eventPublisherClient).publish(anyString());
        QuarkusMock.installMockForType(eventPublisherClient, EventPublisherClient.class);

        tested.publish(message);

        Mockito.verify(eventPublisherClient, Mockito.times(1)).publish(any());
    }
}
