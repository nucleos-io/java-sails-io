package io.nucleos.sailsio;

import java.lang.reflect.Type;

/**
 * Created by cmarcano on 15/10/15.
 */
public interface Adapter<R, T> {

    Type responseType();
    T adapt(R r);

    interface Factory {
        Adapter<?, ?> get(Type returnType);
    }
}
