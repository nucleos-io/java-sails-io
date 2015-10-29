package io.nucleos.sailsio.converters.gson;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;

import io.nucleos.sailsio.Converter;
import io.nucleos.sailsio.Response;

/**
 * Created by luis on 10/10/15.
 */
public class GsonConverterFactory extends Converter.Factory {

    private Gson gson;

    public GsonConverterFactory () {
        this.gson = new Gson();
    }

    @Override
    public Converter<?, io.nucleos.sailsio.request.RequestBody> toRequestBody() {
        return new GsonRequestConverter<>(this.gson);
    }

    @Override
    public Converter<JSONObject, Response> toResponse(Type type) {
        TypeAdapter<?> adapter = this.gson.getAdapter(TypeToken.get(type));
        return new GsonResponseConverter(adapter);
    }

}
