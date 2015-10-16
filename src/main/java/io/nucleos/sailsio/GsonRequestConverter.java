package io.nucleos.sailsio;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * Created by luis on 10/10/15.
 */
public class GsonRequestConverter<T> implements Converter<T,RequestBody> {

    private Gson gson;

    public GsonRequestConverter(Gson gson) {
        this.gson = gson;
    }

    @Override
    public RequestBody convert(T value) {
        Type type = new TypeToken<T>(){}.getType();
        return new RequestBody(gson.toJsonTree(value, type));
    }

}
