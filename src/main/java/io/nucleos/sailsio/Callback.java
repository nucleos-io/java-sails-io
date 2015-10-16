package io.nucleos.sailsio;

/**
 *
 * @param <T> expected response type
 */
public interface Callback<T> {
  /** Successful HTTP . */
  void onResponse(ResponseRequest<T> responseRequest);

  /** Invoked when a network or unexpected exception occurred during the HTTP . */
  void onFailure(Throwable t);
}
