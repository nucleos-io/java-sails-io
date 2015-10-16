package io.nucleos.sailsio;

/**
 *
 */
public interface Callback<T> {
  void onResponse(Response<T> response);
  void onFailure(Throwable t);
}
