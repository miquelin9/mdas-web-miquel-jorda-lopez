package com.ccm.user.user.infrastructure.converter;

import com.ccm.user.user.domain.interfaces.SimpleConverter;
import com.google.gson.Gson;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.util.Objects;

@ApplicationScoped
@Named("JsonString")
public class ObjectToJsonStringConverter implements SimpleConverter<Object, String> {

    Gson gson = new Gson();

    @Override
    public String convert(Object from) {
        if (Objects.isNull(from)) {
            return "";
        }
        return gson.toJson(from);
    }
}
