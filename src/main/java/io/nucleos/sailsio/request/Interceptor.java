package io.nucleos.sailsio.request;

import io.nucleos.sailsio.request.ResponseRequest;

/**
 * Created by cmarcano on 12/10/15.
 */
public interface Interceptor {
    public io.nucleos.sailsio.request.Request onRequest(io.nucleos.sailsio.request.Request.Builder builder);
    public io.nucleos.sailsio.request.ResponseRequest onResponse(ResponseRequest.Builder builder);
}
