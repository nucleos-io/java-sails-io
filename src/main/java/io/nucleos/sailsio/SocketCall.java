package io.nucleos.sailsio;

import org.json.JSONException;

import io.socket.client.Ack;
import io.socket.client.Socket;

/**
 * Created by cmarcano on 07/10/15.
 */
public class SocketCall<T> implements Call<T> {

    private SailsIO io;
    private RequestFactory requestFactory;
    private Object[] args;

    public SocketCall(SailsIO io, RequestFactory requestFactory, Object... args) {
        this.io = io;
        this.requestFactory = requestFactory;
        this.args = args;
    }

    @Override
    public void enqueue(final Callback<T> callback) {

        Request.Builder requestBuilder = this.requestFactory.create(this.args);
        Request request = this.io.getInterceptor().onRequest(requestBuilder);

        try {

            this.io.getSocket().emit(request.getMethod(), request.getBody(), new Ack() {
                @Override
                public void call(Object... args) {

                    callback.onResponse(null);
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
