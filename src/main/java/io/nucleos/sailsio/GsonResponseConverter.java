package io.nucleos.sailsio;

import org.json.JSONObject;

/**
 * Created by luis on 10/10/15.
 */
public class GsonResponseConverter<T> implements Converter<JSONObject,T> {

    @Override
    public T convert(JSONObject value) {
        return null;
    }
}
