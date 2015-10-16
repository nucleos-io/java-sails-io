package io.nucleos.sailsio.event;

import java.lang.reflect.Type;

import io.nucleos.sailsio.*;

/**
 * Created by cmarcano on 15/10/15.
 */
public class DefaultListenerAdapterFactory implements ListenerAdapter.Factory {

    @Override
    public ListenerAdapter<?> get(Type returnType) {
        if (Utils.getRawType(returnType) != io.nucleos.sailsio.event.Listener.class) {
            return null;
        }
        Type responseType = Utils.getResponseType(returnType);
        return new io.nucleos.sailsio.event.DefaultListenerAdapter(responseType);
    }

}
