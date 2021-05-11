package com.ccm.pokemon.pokemon.domain.services;

import com.ccm.pokemon.pokemon.domain.aggregate.Pokemon;
import com.ccm.pokemon.pokemon.domain.exceptions.NetworkConnectionException;
import com.ccm.pokemon.pokemon.domain.exceptions.PokemonNotFoundException;
import com.ccm.pokemon.pokemon.domain.exceptions.TimeoutException;
import com.ccm.pokemon.pokemon.domain.exceptions.UnknownException;
import com.ccm.pokemon.pokemon.domain.interfaces.PokemonRepository;
import com.ccm.pokemon.pokemon.domain.interfaces.PokemonRetrieverClient;
import com.ccm.pokemon.pokemon.domain.vo.PokemonId;
import com.ccm.pokemon.pokemon.infrastructure.api.PokemonRetrieverApiClient;
import com.ccm.pokemon.pokemon.infrastructure.repository.InMemoryPokemonRepository;
import io.quarkus.test.junit.QuarkusMock;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.inject.Inject;
import static org.mockito.ArgumentMatchers.any;

@QuarkusTest
public class PokemonFinderTest {

    @Inject
    PokemonFinder tested;

    @Test
    public void verify_findPokemon_returnsPokemon_whenPokemonExistsInRepository () throws PokemonNotFoundException,
            TimeoutException, UnknownException, NetworkConnectionException {
        Pokemon pokemon = Mockito.mock(Pokemon.class);

        PokemonRepository pokemonRepository = Mockito.mock(InMemoryPokemonRepository.class);
        Mockito.doReturn(pokemon).when(pokemonRepository).find(any());
        Mockito.doReturn(true).when(pokemonRepository).exists(any());

        QuarkusMock.installMockForType(pokemonRepository, PokemonRepository.class);

        tested.findPokemon(new PokemonId(1));

        Mockito.verify(pokemonRepository, Mockito.times(1)).find(Mockito.any());
    }

    @Test
    public void verify_findPokemon_returnsPokemonFromPokemonApi_whenPokemonDoesNotExistInRepository () throws PokemonNotFoundException,
            TimeoutException, UnknownException, NetworkConnectionException {
        Pokemon pokemon = Mockito.mock(Pokemon.class);

        PokemonRetrieverClient pokemonRetrieverClient = Mockito.mock(PokemonRetrieverApiClient.class);
        Mockito.doReturn(pokemon).when(pokemonRetrieverClient).find(any());

        PokemonRepository pokemonRepository = Mockito.mock(InMemoryPokemonRepository.class);
        Mockito.doReturn(false).when(pokemonRepository).exists(any());
        Mockito.doNothing().when(pokemonRepository).create(any());

        QuarkusMock.installMockForType(pokemonRepository, PokemonRepository.class);
        QuarkusMock.installMockForType(pokemonRetrieverClient, PokemonRetrieverClient.class);

        tested.findPokemon(new PokemonId(1));

        Mockito.verify(pokemonRepository, Mockito.times(1)).create(Mockito.any());
    }
}
