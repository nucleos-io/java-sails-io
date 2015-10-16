package io.nucleos.sailsio;

import com.google.gson.TypeAdapter;

import org.json.JSONObject;

/**
 * Created by cmarcano on 15/10/15.
 */
public class GsonResponseListenerConverter implements Converter<JSONObject,ResponseListener> {

    private final TypeAdapter<?> typeAdapter;

    public GsonResponseListenerConverter(TypeAdapter<?> typeAdapter) {
        this.typeAdapter = typeAdapter;
    }

    @Override
    public ResponseListener convert(JSONObject value) throws Exception {

        System.out.println("Event: " + value);

        return null;
    }
}
