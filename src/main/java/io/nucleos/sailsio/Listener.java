package io.nucleos.sailsio;

/**
 * Created by cmarcano on 15/10/15.
 */
public interface Listener<T> {
    void listen(Callback<T> callback);
}
