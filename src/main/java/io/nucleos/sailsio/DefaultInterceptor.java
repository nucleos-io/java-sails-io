package io.nucleos.sailsio;

/**
 * Created by cmarcano on 12/10/15.
 */
public class DefaultInterceptor implements Interceptor {
    @Override
    public Request onRequest(Request.Builder builder) {
        Param accessToken = new Param("access_token", "acce");
        builder.addBodyParam(accessToken);
        return builder.build();
    }

    @Override
    public Response onResponse(Response.Builder builder) {
        return builder.build();
    }
}
