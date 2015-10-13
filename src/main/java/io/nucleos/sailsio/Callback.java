package io.nucleos.sailsio;

/**
 *
 * @param <T> expected response type
 */
public interface Callback<T> {
  /** Successful HTTP response. */
  void onResponse(Response<T> response);

  /** Invoked when a network or unexpected exception occurred during the HTTP request. */
  void onFailure(Throwable t);
}
