package com.ccm.pokemon.pokemon.infrastructure.listener;

import com.ccm.pokemon.pokemon.domain.aggregate.Pokemon;
import com.ccm.pokemon.pokemon.domain.vo.PokemonId;
import com.ccm.pokemon.pokemon.infrastructure.repository.InMemoryPokemonRepository;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

@QuarkusTest
public class RabbitMqEventListenerIT {

    @Inject
    RabbitMqEventListener tested;

    @Inject
    InMemoryPokemonRepository inMemoryPokemonRepository;

    @Test
    public void verify_handle_savesPokemonInformation_whenMessageIsReceived () {
        tested.handle("{\"pokemonId\":1}");

        Pokemon result = inMemoryPokemonRepository.find(new PokemonId(1));
        Assert.assertNotNull(result);
        Assert.assertEquals(1, result.getPokemonId().getPokemonId());
    }
}
