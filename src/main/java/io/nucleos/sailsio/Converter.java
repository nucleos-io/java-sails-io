package io.nucleos.sailsio;

import org.json.JSONObject;

import java.lang.reflect.Type;

import io.nucleos.sailsio.request.RequestBody;

/**
 * Created by luis on 10/10/15.
 */
public interface Converter<F,T> {

    T convert(F value) throws Exception;

    abstract class Factory {
        public  abstract Converter<?, RequestBody> toRequestBody();
        public abstract Converter<JSONObject, Response> toResponse(Type type);
    }
}
