package io.nucleos.sailsio;

import java.lang.reflect.Type;

/**
 * Created by cmarcano on 15/10/15.
 */
public class DefaultListenerAdapter implements ListenerAdapter<Listener<?>> {

    private Type responseType;

    @Override
    public Type responseType() {
        return responseType;
    }

    public DefaultListenerAdapter(Type responseType) {
        this.responseType = responseType;
    }


    @Override
    public Listener<?> adapt(Listener listener) {
        return listener;
    }


}
