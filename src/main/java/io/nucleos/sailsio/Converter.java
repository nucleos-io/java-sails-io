package io.nucleos.sailsio;

import org.json.JSONObject;

import java.lang.reflect.Type;

/**
 * Created by luis on 10/10/15.
 */
public interface Converter<F,T> {

    T convert(F value) throws Exception;

    abstract class Factory {
        public  abstract Converter<?, RequestBody> toRequestBody();
        public abstract Converter<JSONObject, ResponseRequest> toResponseRequest(Type type);
        public abstract Converter<JSONObject, ResponseListener> toResponseListener(Type type);
    }
}
