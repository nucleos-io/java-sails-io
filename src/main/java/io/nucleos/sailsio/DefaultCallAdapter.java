package io.nucleos.sailsio;

import java.lang.reflect.Type;

/**
 * Created by cmarcano on 07/10/15.
 */
public class DefaultCallAdapter implements CallAdapter<Call<?>> {

    private final Type responseType;

    public DefaultCallAdapter(Type responseType) {
        this.responseType = responseType;
    }

    @Override
    public Type responseType() {
        return responseType;
    }

    @Override
    public Call<?> adapt(Call call) {
        return call;
    }
}
