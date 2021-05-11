package com.ccm.pokemon.pokemon.infrastructure.repository;

import com.ccm.pokemon.pokemon.domain.aggregate.Pokemon;
import com.ccm.pokemon.pokemon.domain.vo.Name;
import com.ccm.pokemon.pokemon.domain.vo.PokemonId;
import com.ccm.pokemon.pokemon.domain.vo.PokemonType;
import io.quarkus.runtime.StartupEvent;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

@QuarkusTest
public class InMemoryPokemonRepositoryTest {

    InMemoryPokemonRepository tested;

    protected void setUpTest () {
        Pokemon pokemon = new Pokemon(
                new Name("TestPokemon"),
                new PokemonId(1)
        );

        tested = new InMemoryPokemonRepository();
        tested.create(pokemon);
    }

    @Test
    public void verify_find_returnsPokemon_whenPokemonIsStoragedAndPokemonIdIsPassed () {
        setUpTest();

        Pokemon result = tested.find(new PokemonId(1));

        Assertions.assertNotNull(result);
        Assertions.assertEquals("TestPokemon", result.getName().getName());
        Assertions.assertEquals(1, result.getPokemonId().getPokemonId());
    }

    @Test
    public void verify_find_returnsNull_whenPokemonDoesNotExists () {
        setUpTest();

        Pokemon result = tested.find(new PokemonId(2));

        Assertions.assertNull(result);
    }

    @Test
    public void verify_exists_returnsTrue_whenPokemonExists () {
        setUpTest();

        boolean result = tested.exists(new PokemonId(1));

        Assertions.assertTrue(result);
    }

    @Test
    public void verify_exists_returnsFalse_whenPokemonDoesNotExist () {
        setUpTest();

        boolean result = tested.exists(new PokemonId(2));

        Assertions.assertFalse(result);
    }

    @Test
    public void verify_update_updatesPokemon_whenPokemonExist () {
        setUpTest();

        Pokemon newPokemon = new Pokemon(
                new Name("newPokemon"),
                new PokemonId(1)
        );
        newPokemon.addPokemonType(new PokemonType("electric"));
        newPokemon.addFavouriteCounter();

        tested.update(newPokemon);
        Pokemon result = tested.find(new PokemonId(1));

        Assertions.assertEquals("newPokemon", result.getName().getName());
        Assertions.assertEquals(1, result.getPokemonId().getPokemonId());
        Assertions.assertFalse(result.getPokemonTypes().getPokemonTypes().isEmpty());
        Assertions.assertEquals("electric", result.getPokemonTypes().getPokemonTypes().get(0).getPokemonType());
        Assertions.assertEquals(1, result.getFavouriteCounter().getFavouriteCounter());
    }
}
