package com.ccm.pokemon.pokemon.infrastructure.controller;

import com.ccm.pokemon.pokemon.application.dto.PokemonDto;
import com.ccm.pokemon.pokemon.application.useCases.FavouritePokemonAddedNotifierUseCase;
import com.ccm.pokemon.pokemon.application.useCases.GetPokemonUseCase;
import com.ccm.pokemon.pokemon.domain.aggregate.Pokemon;
import com.ccm.pokemon.pokemon.domain.exceptions.NetworkConnectionException;
import com.ccm.pokemon.pokemon.domain.exceptions.TimeoutException;
import com.ccm.pokemon.pokemon.infrastructure.parsers.PokemonToJsonParser;
import com.ccm.pokemon.pokemon.domain.exceptions.PokemonNotFoundException;
import org.jboss.logging.Logger;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Model
@Path("/pokemon")
public class PokemonController {
    private final Logger LOGGER = Logger.getLogger(PokemonController.class);

    @Inject
    GetPokemonUseCase getPokemonUseCase;
    @Inject
    PokemonToJsonParser pokemonToJsonParser;

    @GET
    @Path("/get/{id}")
    public Response getPokemon(@PathParam("id") int id) {
        LOGGER.info("PokemonController Get Pokemon called with pokemonId:" + id);

        try {
            Pokemon result = getPokemonUseCase.getPokemonByPokemonId(new PokemonDto(id));

            return Response.status(200).entity(pokemonToJsonParser.parse(result)).build();
        } catch (PokemonNotFoundException e) {
            return Response.status(404).entity(e.getMessage()).build();
        } catch (TimeoutException e) {
            return Response.status(408).entity(e.getMessage()).build();
        } catch (NetworkConnectionException e) {
            return Response.status(503).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(500).entity("Unexpected error. " + e.getMessage()).build();
        }
    }
}