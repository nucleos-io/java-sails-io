package io.nucleos.sailsio;

import io.nucleos.sailsio.request.Interceptor;
import io.nucleos.sailsio.request.Request;

/**
 * Created by cmarcano on 12/10/15.
 */
public class DefaultInterceptor implements Interceptor {
    @Override
    public Request onRequest(Request.Builder builder) {
        return builder.build();
    }

}
