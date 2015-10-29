package io.nucleos.sailsio.converters.gson;

import com.google.gson.TypeAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import io.nucleos.sailsio.Converter;
import io.nucleos.sailsio.Response;

/**
 * Created by luis on 10/10/15.
 */
public class GsonResponseConverter implements Converter<JSONObject, Response> {

    private final TypeAdapter<?> typeAdapter;

    public GsonResponseConverter(TypeAdapter<?> typeAdapter) {
        this.typeAdapter = typeAdapter;
    }

    @Override
    public Response convert(JSONObject value) throws IOException, JSONException {
        Response response = null;
        if (value.has("statusCode") && value.has("headers")) {
            response = new Response<>(
                    (int) value.get("statusCode"),
                    this.typeAdapter.fromJson(value.get("body").toString())
            );
        } else if (value.has("id")) {
            response = new Response<>(
                    value.get("id").toString(),
                    this.typeAdapter.fromJson(value.get("data").toString())
            );
        }
        return response;
    }
}
