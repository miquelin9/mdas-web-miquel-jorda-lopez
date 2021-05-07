package com.ccm.pokemon.pokemon.application.useCases;

import com.ccm.pokemon.pokemon.application.dto.PokemonDto;
import com.ccm.pokemon.pokemon.domain.aggregate.Pokemon;
import com.ccm.pokemon.pokemon.domain.exceptions.NetworkConnectionException;
import com.ccm.pokemon.pokemon.domain.exceptions.PokemonNotFoundException;
import com.ccm.pokemon.pokemon.domain.exceptions.TimeoutException;
import com.ccm.pokemon.pokemon.domain.exceptions.UnknownException;
import com.ccm.pokemon.pokemon.domain.services.FavouritePokemonCounterUpdater;
import com.ccm.pokemon.pokemon.domain.services.PokemonFinder;
import com.ccm.pokemon.pokemon.domain.valueObjects.PokemonId;
import com.ccm.pokemon.pokemon.infrastructure.listener.RabbitMqEventListener;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class FavouritePokemonAddedNotifierUseCase {

    private final Logger LOGGER = Logger.getLogger(FavouritePokemonAddedNotifierUseCase.class);

    @Inject
    PokemonFinder pokemonFinder;
    @Inject
    FavouritePokemonCounterUpdater favouritePokemonCounterUpdater;

    public void addFavouritePokemonCounter(PokemonDto pokemon) throws PokemonNotFoundException, TimeoutException,
            NetworkConnectionException, UnknownException {
        LOGGER.info("FavouritePokemonAddedNotifierUseCase called");

        Pokemon requestPokemon = pokemonFinder.findPokemon(new PokemonId(pokemon.getPokemonId()));
        favouritePokemonCounterUpdater.execute(requestPokemon);
    }
}
