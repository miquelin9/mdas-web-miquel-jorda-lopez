package com.ccm.pokemon.pokemon.domain.services;

import com.ccm.user.user.domain.entities.Message;
import com.ccm.user.user.domain.interfaces.EventPublisherClient;
import com.ccm.user.user.domain.services.EventPublisher;
import com.ccm.user.user.infrastructure.rabbitmq.RabbitMqEventPublisher;
import io.quarkus.test.junit.QuarkusMock;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.inject.Inject;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@QuarkusTest
public class EventPublisherTest {

    @Inject
    EventPublisher tested;

    @Test
    public void verify_publish_isCalled_whenMessageReceived() throws IOException {
        Message message = Mockito.mock(Message.class);

        EventPublisherClient eventPublisherClient = Mockito.mock(RabbitMqEventPublisher.class);
        Mockito.doNothing().when(eventPublisherClient).publish(anyString());
        QuarkusMock.installMockForType(eventPublisherClient, EventPublisherClient.class);

        tested.publish(message);

        verify(eventPublisherClient, times(1)).publish(anyString());
    }

    @Test()
    public void verify_publish_throwsIOException_whenEventPublisherClientThrowsIOException() throws IOException {
        Message message = Mockito.mock(Message.class);

        EventPublisherClient eventPublisherClient = Mockito.mock(RabbitMqEventPublisher.class);
        Mockito.doThrow(new IOException()).when(eventPublisherClient).publish(anyString());
        QuarkusMock.installMockForType(eventPublisherClient, EventPublisherClient.class);

        assertThrows(
                IOException.class,
                () -> {
                    tested.publish(message);
                }
        );
    }
}
