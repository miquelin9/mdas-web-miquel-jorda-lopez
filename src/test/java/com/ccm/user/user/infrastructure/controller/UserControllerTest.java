package com.ccm.user.user.infrastructure.controller;

import io.quarkus.test.junit.QuarkusTest;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class UserControllerTest {

    @Test
    public void shouldAddUser() {
        given()
            .queryParam("name", "tipoDeIncógnito")
            .queryParam("userId", 123456789)
            .post("/user/add")
            .then()
            .assertThat()
            .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void shouldGetHttpCode403WhenAddingANewUser() {
        given()
            .queryParam("name", "tipoDeIncógnito")
            .queryParam("userId", 1)
            .post("/user/add");

        given()
            .queryParam("name", "tipoDeIncógnito")
            .queryParam("userId", 1)
            .post("/user/add")
            .then()
            .assertThat()
            .statusCode(HttpStatus.SC_FORBIDDEN);
    }

    @Test
    public void shouldAddFavouritePokemon() {
        given()
            .queryParam("name", "tipoDeIncógnito")
            .queryParam("userId", 1)
            .post("/user/add");

        given()
            .header("id", 1)
            .queryParam("id", 1234)
            .post("/user/favouritepokemon/add")
            .then()
            .assertThat()
            .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void shouldGetHttpCode409WhenAddingANewFavouritePokemon() {
        given()
            .queryParam("name", "tipoDeIncógnito")
            .queryParam("userId", 1)
            .post("/user/add");

        given()
            .header("id", 1)
            .queryParam("id", 123)
            .post("/user/favouritepokemon/add");

        given()
            .header("id", 1)
            .queryParam("id", 123)
            .post("/user/favouritepokemon/add")
            .then()
            .assertThat()
            .statusCode(HttpStatus.SC_CONFLICT);
    }

    @Test
    public void shouldGetHttpCode403WhenAddingANewFavouritePokemon() {
        given()
            .header("id", 9)
            .queryParam("id", 123)
            .post("/user/favouritepokemon/add")
            .then()
            .assertThat()
            .statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
