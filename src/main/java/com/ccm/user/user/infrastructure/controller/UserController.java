package com.ccm.user.user.infrastructure.controller;

import com.ccm.pokemon.pokemon.application.useCases.FavouritePokemonAddedNotifierUseCase;
import com.ccm.user.user.application.usecases.AddFavouritePokemonUseCase;
import com.ccm.user.user.application.usecases.AddUserUseCase;
import com.ccm.user.user.application.dto.UserDTO;
import com.ccm.user.user.application.dto.UserFavouritePokemonDTO;
import com.ccm.user.user.domain.aggregate.User;
import com.ccm.user.user.domain.exceptions.FavouritePokemonAlreadyExistsException;
import com.ccm.user.user.domain.exceptions.UserAlreadyExistsException;
import com.ccm.user.user.domain.exceptions.UserNotFoundException;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import org.jboss.logging.Logger;

@Model
@Path("/user")
public class UserController {

    private final Logger LOGGER = Logger.getLogger(UserController.class);

    @Inject
    AddFavouritePokemonUseCase addFavouritePokemonUseCase;
    @Inject
    AddUserUseCase addUserUseCase;

    @POST
    @Path("/favouritepokemon/add")
    public Response addFavouritePokemon(@HeaderParam ("id") int userId, @QueryParam("id") int pokemonId) {
        LOGGER.info("Add Favourite Pokemon with id:" + pokemonId + " to user with id:" + userId + " called");

        try {
            addFavouritePokemonUseCase.addFavouritePokemon(new UserFavouritePokemonDTO(
                pokemonId, userId)
            );
            return Response.status(200).build();
        } catch (FavouritePokemonAlreadyExistsException e) {
            return Response.status(409).entity(e.getMessage()).build();
        } catch (UserNotFoundException e) {
            return Response.status(403).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(500).entity("Unexpected error. " + e.getMessage()).build();
        }
    }

    @POST
    @Path("/add")
    public Response addUser(@QueryParam("name") String name, @QueryParam("userId") int userId) {
        LOGGER.info("Add User with name:" + name + " and userId:" + userId + " called");
        try {
            addUserUseCase.createUser(new UserDTO(name, userId));
            return Response.status(200).build();
        } catch (UserAlreadyExistsException e) {
            return Response.status(403).entity(e.getMessage()).build();
        }
    }
}
