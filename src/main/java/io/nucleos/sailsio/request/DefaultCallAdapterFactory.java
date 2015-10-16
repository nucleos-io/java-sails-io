package io.nucleos.sailsio.request;

import java.lang.reflect.Type;

import io.nucleos.sailsio.*;

/**
 * Created by cmarcano on 07/10/15.
 */
public class DefaultCallAdapterFactory implements CallAdapter.Factory {
    @Override
    public CallAdapter<?> get(Type returnType) {
        if (Utils.getRawType(returnType) != io.nucleos.sailsio.request.Call.class) {
            return null;
        }
        Type responseType = Utils.getResponseType(returnType);
        return new io.nucleos.sailsio.request.DefaultCallAdapter(responseType);
    }


}
