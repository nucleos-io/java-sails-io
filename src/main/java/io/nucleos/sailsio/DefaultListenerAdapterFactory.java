package io.nucleos.sailsio;

import java.lang.reflect.Type;

/**
 * Created by cmarcano on 15/10/15.
 */
public class DefaultListenerAdapterFactory implements ListenerAdapter.Factory {

    @Override
    public ListenerAdapter<?> get(Type returnType) {
        if (Utils.getRawType(returnType) != Listener.class) {
            return null;
        }
        Type responseType = Utils.getResponseType(returnType);
        return new DefaultListenerAdapter(responseType);
    }

}
