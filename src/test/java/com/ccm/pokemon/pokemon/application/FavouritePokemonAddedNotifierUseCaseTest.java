package com.ccm.pokemon.pokemon.application;

import com.ccm.pokemon.pokemon.application.dto.PokemonDto;
import com.ccm.pokemon.pokemon.application.useCases.FavouritePokemonAddedNotifierUseCase;
import com.ccm.pokemon.pokemon.domain.aggregate.Pokemon;
import com.ccm.pokemon.pokemon.domain.exceptions.NetworkConnectionException;
import com.ccm.pokemon.pokemon.domain.exceptions.PokemonNotFoundException;
import com.ccm.pokemon.pokemon.domain.exceptions.TimeoutException;
import com.ccm.pokemon.pokemon.domain.exceptions.UnknownException;
import com.ccm.pokemon.pokemon.domain.services.FavouritePokemonCounterUpdater;
import com.ccm.pokemon.pokemon.domain.services.PokemonFinder;
import com.ccm.user.user.domain.exceptions.UserAlreadyExistsException;
import io.quarkus.test.junit.QuarkusMock;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@QuarkusTest
public class FavouritePokemonAddedNotifierUseCaseTest {

    @Inject
    FavouritePokemonAddedNotifierUseCase tested;

    @Test
    public void verify_addFavouritePokemonCounter_methodsCalled () throws PokemonNotFoundException,
            TimeoutException, UnknownException, NetworkConnectionException {

        Pokemon pokemon = Mockito.mock(Pokemon.class);
        PokemonDto pokemonDto = Mockito.mock(PokemonDto.class);

        PokemonFinder pokemonFinder = Mockito.mock(PokemonFinder.class);
        Mockito.doReturn(pokemon).when(pokemonFinder).findPokemon(any());
        QuarkusMock.installMockForType(pokemonFinder, PokemonFinder.class);

        FavouritePokemonCounterUpdater favouritePokemonCounterUpdater = Mockito.mock(FavouritePokemonCounterUpdater.class);
        Mockito.doNothing().when(favouritePokemonCounterUpdater).execute(pokemon);
        QuarkusMock.installMockForType(favouritePokemonCounterUpdater, FavouritePokemonCounterUpdater.class);

        tested.addFavouritePokemonCounter(pokemonDto);

        verify(favouritePokemonCounterUpdater, times(1)).execute(any());
    }

    @Test
    public void verify_addFavouritePokemonCounter_throwsPokemonNotFound_whenPokemonDoesNotExist () throws PokemonNotFoundException,
            TimeoutException, UnknownException, NetworkConnectionException {

        Pokemon pokemon = Mockito.mock(Pokemon.class);
        PokemonDto pokemonDto = Mockito.mock(PokemonDto.class);

        PokemonFinder pokemonFinder = Mockito.mock(PokemonFinder.class);
        Mockito.doThrow(PokemonNotFoundException.class).when(pokemonFinder).findPokemon(any());
        QuarkusMock.installMockForType(pokemonFinder, PokemonFinder.class);

        assertThrows(PokemonNotFoundException.class, () -> {
            tested.addFavouritePokemonCounter(pokemonDto);
        });
    }
}
