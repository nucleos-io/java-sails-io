package io.nucleos.sailsio;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by cmarcano on 07/10/15.
 */
public class DefaultCallAdapterFactory implements CallAdapter.Factory {
    @Override
    public CallAdapter<?> get(Type returnType) {
        if (Utils.getRawType(returnType) != Call.class) {
            return null;
        }
        Type responseType = Utils.getResponseType(returnType);
        return new DefaultCallAdapter(responseType);
    }


}
