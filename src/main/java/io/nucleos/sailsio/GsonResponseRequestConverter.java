package io.nucleos.sailsio;

import com.google.gson.TypeAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by luis on 10/10/15.
 */
public class GsonResponseRequestConverter implements Converter<JSONObject,ResponseRequest> {

    private final TypeAdapter<?> typeAdapter;

    public GsonResponseRequestConverter(TypeAdapter<?> typeAdapter) {
        this.typeAdapter = typeAdapter;
    }

    @Override
    public ResponseRequest convert(JSONObject value) throws IOException, JSONException {

        ResponseRequest.Builder builder = new ResponseRequest.Builder();

        try {
            builder.statusCode((int) value.get("statusCode"));
            builder.body(this.typeAdapter.fromJson(value.get("body").toString()));

        } catch (JSONException | IOException e) {
            throw e;
        }

        return builder.build();
    }
}
