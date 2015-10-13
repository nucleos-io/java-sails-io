package io.nucleos.sailsio;

/**
 * TODO
 */
public final class Response<T> {

    /**
    * TODO
    */
    public static <T> Response<T> success(T body) {
      return null;
    }

    final static class Builder {
        public Response build() {
            return new Response();
        }
    }
}
