package io.nucleos.sailsio.request;

/**
 * Created by cmarcano on 12/10/15.
 */
public interface Interceptor {
    public io.nucleos.sailsio.request.Request onRequest(io.nucleos.sailsio.request.Request.Builder builder);
}
