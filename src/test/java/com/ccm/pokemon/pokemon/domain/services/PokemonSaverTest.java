package com.ccm.pokemon.pokemon.domain.services;

import com.ccm.pokemon.pokemon.domain.aggregate.Pokemon;
import com.ccm.pokemon.pokemon.domain.interfaces.PokemonRepository;
import com.ccm.pokemon.pokemon.domain.vo.Name;
import com.ccm.pokemon.pokemon.domain.vo.PokemonId;
import com.ccm.pokemon.pokemon.infrastructure.repository.InMemoryPokemonRepository;
import io.quarkus.test.junit.QuarkusMock;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.inject.Inject;

import static org.mockito.ArgumentMatchers.any;

@QuarkusTest
public class PokemonSaverTest {

    @Inject
    PokemonSaver tested;

    @Test
    public void verify_persist_createsPokemon_whenIsNotFoundInRepository () {
        Pokemon pokemon = new Pokemon(
                new Name("test"),
                new PokemonId(1)
        );

        PokemonRepository pokemonRepository = Mockito.mock(InMemoryPokemonRepository.class);
        Mockito.doReturn(false).when(pokemonRepository).exists(any());
        Mockito.doNothing().when(pokemonRepository).create(any());

        QuarkusMock.installMockForType(pokemonRepository, PokemonRepository.class);

        tested.persist(pokemon);

        Mockito.verify(pokemonRepository, Mockito.times(1)).create(Mockito.any());
    }

    @Test
    public void verify_persist_updatesPokemon_whenIsFoundInRepository () {
        Pokemon pokemon = new Pokemon(
                new Name("test"),
                new PokemonId(1)
        );

        PokemonRepository pokemonRepository = Mockito.mock(InMemoryPokemonRepository.class);
        Mockito.doReturn(true).when(pokemonRepository).exists(any());
        Mockito.doReturn(pokemon).when(pokemonRepository).update(any());

        QuarkusMock.installMockForType(pokemonRepository, PokemonRepository.class);

        tested.persist(pokemon);

        Mockito.verify(pokemonRepository, Mockito.times(1)).update(Mockito.any());
    }
}
