package com.ccm.pokemon.pokemon.domain.services;

import com.ccm.pokemon.pokemon.domain.aggregate.Pokemon;
import com.ccm.pokemon.pokemon.domain.vo.Name;
import com.ccm.pokemon.pokemon.domain.vo.PokemonId;
import io.quarkus.test.junit.QuarkusMock;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import javax.inject.Inject;

import static org.mockito.ArgumentMatchers.any;

@QuarkusTest
public class FavouritePokemonCounterUpdaterTest {

    @Inject
    FavouritePokemonCounterUpdater tested;

    @Test
    public void verify_execute_updatesCounter_whenGetsAPokemon() {
        Pokemon pokemon = new Pokemon(
                new Name("testPokemon"),
                new PokemonId(1)
        );

        PokemonSaver pokemonSaver = Mockito.mock(PokemonSaver.class);
        Mockito.doNothing().when(pokemonSaver).persist(any());
        QuarkusMock.installMockForType(pokemonSaver, PokemonSaver.class);
        tested.execute(pokemon);

        Assert.assertEquals(pokemon.getFavouriteCounter().getFavouriteCounter(), 1);
    }
}
