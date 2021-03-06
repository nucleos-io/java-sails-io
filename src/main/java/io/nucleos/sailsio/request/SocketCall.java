package io.nucleos.sailsio.request;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

import io.nucleos.sailsio.*;
import io.nucleos.sailsio.Call;
import io.socket.client.Ack;

/**
 * Created by cmarcano on 07/10/15.
 */
public class SocketCall<T> implements io.nucleos.sailsio.Call<T> {

    private SailsIO io;
    private RequestFactory requestFactory;
    private Object[] args;
    private Type responseType;

    public SocketCall(SailsIO io, RequestFactory requestFactory, Type responseType, Object... args) {
        this.io = io;
        this.responseType = responseType;
        this.requestFactory = requestFactory;
        this.args = args;
    }

    @Override
    public void enqueue(final Callback<T> callback) {

        io.nucleos.sailsio.request.Request.Builder requestBuilder = this.requestFactory.create(this.args);
        io.nucleos.sailsio.request.Request request = this.io.getInterceptor().onRequest(requestBuilder);

        try {

            this.io.getSocket().emit(request.getMethod(), request.getBody(), new Ack() {
                @Override
                public void call(Object... args) {
                    try {

                        JSONObject responseJson  = (JSONObject) args[0];
                        // noinspection unchecked
                        Response<T> response = io.responseConverter(responseType).convert(responseJson);
                        callback.onResponse(response);

                    } catch (Exception e) {
                        callback.onFailure(e);
                    }
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
            callback.onFailure(e);
        }


    }


    @Override
    public void listen(Callback<T> callback) {
        throw new IllegalStateException("The method listen is not allowed for this object please use the method enqueue instead of this");

    }

    @Override
    public Call<T> clone() {
        return null;
    }
}
