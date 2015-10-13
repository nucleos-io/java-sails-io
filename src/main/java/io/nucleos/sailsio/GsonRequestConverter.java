package io.nucleos.sailsio;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * Created by luis on 10/10/15.
 */
public class GsonRequestConverter<T> implements Converter<T,RequestBody> {

    private Gson gson;


    public GsonRequestConverter() {
        this.gson = new Gson();
    }

    @Override
    public RequestBody convert(T value) {
        return new RequestBody(gson.toJson(value));
    }

}
