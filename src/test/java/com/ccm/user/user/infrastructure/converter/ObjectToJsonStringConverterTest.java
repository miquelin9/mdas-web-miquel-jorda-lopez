package com.ccm.user.user.infrastructure.converter;

import com.ccm.user.user.domain.entities.Message;
import com.ccm.user.user.domain.vo.FavouritePokemonId;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

@QuarkusTest
public class ObjectToJsonStringConverterTest {

    @Inject
    ObjectToJsonStringConverter tested;

    @Test
    public void verify_convert_returnsJsonObjectStringified_whenFavouritePokemonIdIsPassed () {
        FavouritePokemonId favouritePokemonId = new FavouritePokemonId(1);

        String result = tested.convert(favouritePokemonId);
        Assert.assertEquals(result, "{\"pokemonId\":1}");
    }

    @Test
    public void verify_convert_returnsEmptyString_whenNullIsPassed () {
        String result = tested.convert(null);
        Assert.assertEquals(result, "");
    }
}
