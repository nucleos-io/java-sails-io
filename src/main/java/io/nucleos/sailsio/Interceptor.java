package io.nucleos.sailsio;

/**
 * Created by cmarcano on 12/10/15.
 */
public interface Interceptor {
    public Request onRequest(Request.Builder builder);
    public Response onResponse(Response.Builder builder);
}
