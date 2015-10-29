package io.nucleos.sailsio.adapters;

import java.lang.reflect.Type;

import io.nucleos.sailsio.CallAdapter;

/**
 * Created by cmarcano on 07/10/15.
 */
public class DefaultCallAdapter implements CallAdapter<io.nucleos.sailsio.Call<?>> {

    private final Type responseType;

    public DefaultCallAdapter(Type responseType) {
        this.responseType = responseType;
    }

    @Override
    public Type responseType() {
        return responseType;
    }

    @Override
    public io.nucleos.sailsio.Call<?> adapt(io.nucleos.sailsio.Call call) {
        return call;
    }
}
