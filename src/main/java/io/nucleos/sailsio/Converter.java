package io.nucleos.sailsio;

import org.json.JSONObject;

import java.lang.reflect.Type;

/**
 * Created by luis on 10/10/15.
 */
public interface Converter<F,T> {
    public T convert(F value);
    abstract class Factory {

        public  abstract Converter<?, RequestBody> toRequestBody();
        public abstract Converter<JSONObject, ?> toResponseBody();

    }
}
