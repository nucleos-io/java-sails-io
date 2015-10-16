package io.nucleos.sailsio;

import java.lang.reflect.Type;

/**
 * Created by cmarcano on 15/10/15.
 */
public interface Adapter<A, T> {

    Type responseType();
    T adapt(A a);

    interface Factory {
        Adapter<?, ?> get(Type returnType);
    }
}
