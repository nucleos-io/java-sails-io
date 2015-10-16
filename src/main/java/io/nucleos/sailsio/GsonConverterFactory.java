package io.nucleos.sailsio;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;

/**
 * Created by luis on 10/10/15.
 */
public class GsonConverterFactory extends Converter.Factory {

    private Gson gson;

    public GsonConverterFactory () {
        this.gson = new Gson();
    }

    @Override
    public Converter<?, RequestBody> toRequestBody() {
        return new GsonRequestConverter<>(this.gson);
    }

    @Override
    public Converter<JSONObject, ResponseRequest> toResponseRequest(Type type) {
        TypeAdapter<?> adapter = this.gson.getAdapter(TypeToken.get(type));
        return new GsonResponseRequestConverter(adapter);
    }

    @Override
    public Converter<JSONObject, ResponseListener> toResponseListener(Type type) {
        TypeAdapter<?> adapter = this.gson.getAdapter(TypeToken.get(type));
        return new GsonResponseListenerConverter(adapter);
    }
}
