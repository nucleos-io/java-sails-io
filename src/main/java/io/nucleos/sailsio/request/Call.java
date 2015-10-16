package io.nucleos.sailsio.request;

import io.nucleos.sailsio.Callback;

/**
 * An invocation of a Retrofit method that sends a request to a webserver and returns a response.
 * Each call yields its own HTTP request and response pair. Use {@link #clone} to make multiple
 * calls with the same parameters to the same webserver; this may be used to implement polling or
 * to retry a failed call.
 *
 */
public interface Call<T> extends Cloneable {
  void enqueue(Callback<T> callback);
  void cancel();
  Call<T> clone();
}
