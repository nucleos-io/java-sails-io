package io.nucleos.sailsio;

import org.json.JSONObject;

import java.lang.reflect.Type;

/**
 * Created by luis on 10/10/15.
 */
public class GsonConverterFactory extends Converter.Factory {
    @Override
    public Converter<?, RequestBody> toRequestBody() {
        return new GsonRequestConverter<>();
    }

    @Override
    public Converter<JSONObject, ?> toResponseBody() {
        return null;
    }
}
