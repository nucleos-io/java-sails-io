package io.nucleos.sailsio;

import java.lang.reflect.Type;

/**
 * Created by cmarcano on 07/10/15.
 */
public class DefaultAdapter implements CallAdapter<Call<?>> {

    private final Type responseType;

    public DefaultAdapter(Type responseType) {
        this.responseType = responseType;
    }

    @Override
    public Type responseType() {
        return responseType;
    }

    @Override
    public <R> Call<R> adapt(Call<R> call) {
        return call;
    }
}
