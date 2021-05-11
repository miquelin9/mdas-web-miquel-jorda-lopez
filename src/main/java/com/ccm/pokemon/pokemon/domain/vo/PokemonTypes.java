package com.ccm.pokemon.pokemon.domain.vo;

import java.util.ArrayList;
import java.util.List;

public class PokemonTypes {
    private List<PokemonType> pokemonTypes;

    public PokemonTypes() {
        this.pokemonTypes = new ArrayList<>();
    }

    public List<PokemonType> getPokemonTypes() {
        return this.pokemonTypes;
    }

    public void addType(PokemonType pokemonType) {
        pokemonTypes.add(pokemonType);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PokemonTypes that = (PokemonTypes) o;
        return pokemonTypes.equals(that.pokemonTypes);
    }
}
