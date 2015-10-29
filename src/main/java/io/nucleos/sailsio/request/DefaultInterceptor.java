package io.nucleos.sailsio.request;

/**
 * Created by cmarcano on 12/10/15.
 */
public class DefaultInterceptor implements Interceptor {
    @Override
    public Request onRequest(Request.Builder builder) {
        return builder.build();
    }

}
