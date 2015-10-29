package io.nucleos.sailsio.converters.gson;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import io.nucleos.sailsio.Converter;
import io.nucleos.sailsio.request.RequestBody;

/**
 * Created by luis on 10/10/15.
 */
public class GsonRequestConverter<T> implements Converter<T, RequestBody> {

    private Gson gson;

    public GsonRequestConverter(Gson gson) {
        this.gson = gson;
    }

    @Override
    public io.nucleos.sailsio.request.RequestBody convert(T value) {
        Type type = new TypeToken<T>(){}.getType();
        return new io.nucleos.sailsio.request.RequestBody(gson.toJsonTree(value, type));
    }

}
