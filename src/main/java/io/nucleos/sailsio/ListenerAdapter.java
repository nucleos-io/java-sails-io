package io.nucleos.sailsio;

import java.lang.reflect.Type;

/**
 * Created by cmarcano on 15/10/15.
 */
public interface ListenerAdapter<T> extends Adapter<Listener, T> {

    @Override
    Type responseType();

    /** Returns an instance of the {@code T} which adapts the execution of {@code call}. */
    @Override
    T adapt(Listener listener);

    interface Factory extends Adapter.Factory{
        @Override
        ListenerAdapter<?> get(Type returnType);
    }
}
