package com.ccm.pokemon.pokemon.domain.interfaces;

import com.ccm.pokemon.pokemon.domain.aggregate.Pokemon;
import com.ccm.pokemon.pokemon.domain.vo.PokemonId;

public interface PokemonRepository {

    Pokemon find(PokemonId pokemonId);

    Boolean exists(PokemonId pokemonId);

    void create(Pokemon pokemon);

    Pokemon update(Pokemon pokemon);
}
