package com.ccm.user.user.infrastructure.publisher;

import com.ccm.pokemon.pokemon.application.useCases.FavouritePokemonAddedNotifierUseCase;
import com.ccm.pokemon.pokemon.domain.exceptions.NetworkConnectionException;
import com.ccm.pokemon.pokemon.domain.exceptions.PokemonNotFoundException;
import com.ccm.pokemon.pokemon.domain.exceptions.TimeoutException;
import com.ccm.pokemon.pokemon.domain.exceptions.UnknownException;
import io.quarkus.test.junit.QuarkusMock;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.inject.Inject;
import java.io.IOException;

import static org.mockito.Mockito.*;

@QuarkusTest
public class RabbitMqEventPublisherIT  {

    @Inject
    RabbitMqEventPublisher tested;

    @Test
    public void verify_publish_sendsProperlyMessageToQueue_whenMessageIsCorrect () throws IOException,
            PokemonNotFoundException, TimeoutException, UnknownException, NetworkConnectionException {

        String message = "{\"pokemonId\":1}";

        FavouritePokemonAddedNotifierUseCase favouritePokemonAddedNotifierUseCase =
                mock(FavouritePokemonAddedNotifierUseCase.class);
        doNothing().when(favouritePokemonAddedNotifierUseCase).addFavouritePokemonCounter(any());
        QuarkusMock.installMockForType(favouritePokemonAddedNotifierUseCase, FavouritePokemonAddedNotifierUseCase.class);

        tested.publish(message);

        Mockito.verify(favouritePokemonAddedNotifierUseCase, Mockito.timeout(1000).times(1))
                .addFavouritePokemonCounter(any());
    }
}
