package io.nucleos.sailsio.adapters;

import java.lang.reflect.Type;

import io.nucleos.sailsio.*;
import io.nucleos.sailsio.CallAdapter;

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
