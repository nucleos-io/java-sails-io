package io.nucleos.sailsio;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

import io.socket.client.Ack;

/**
 * Created by cmarcano on 07/10/15.
 */
public class SocketCall<T> implements Call<T> {

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

        Request.Builder requestBuilder = this.requestFactory.create(this.args);
        Request request = this.io.getInterceptor().onRequest(requestBuilder);

        try {

            this.io.getSocket().emit(request.getMethod().toLowerCase(), request.getBody(), new Ack() {
                @Override
                public void call(Object... args) {
                    try {

                        JSONObject responseJson  = (JSONObject) args[0];
                        // noinspection unchecked
                        ResponseRequest<T> responseRequest = io.responseConverter(responseType).convert(responseJson);
                        callback.onResponse(responseRequest);

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
    public void cancel() {

    }

    @Override
    public Call<T> clone() {
        return null;
    }
}
