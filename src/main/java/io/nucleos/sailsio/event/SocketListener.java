package io.nucleos.sailsio.event;

import org.json.JSONObject;

import java.lang.reflect.Type;

import io.nucleos.sailsio.*;
import io.nucleos.sailsio.Response;
import io.nucleos.sailsio.Call;
import io.socket.emitter.Emitter;

/**
 * Created by cmarcano on 15/10/15.
 */
public class SocketListener<T> implements Call<T> {

    private SailsIO io;
    private io.nucleos.sailsio.event.ListenerFactory listenerFactory;
    private Type responseType;

    public SocketListener(SailsIO io, io.nucleos.sailsio.event.ListenerFactory listenerFactory, Type responseType) {
        this.io = io;
        this.listenerFactory = listenerFactory;
        this.responseType = responseType;
    }

    @Override
    public void listen(final Callback<T> callback) {

        this.io.getSocket().on(listenerFactory.getEventName(), new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject responseJson  = (JSONObject) args[0];
                Response<T> response;
                try {
                    // noinspection unchecked
                    response = io.responseConverter(responseType).convert(responseJson);
                    callback.onResponse(response);
                } catch (Exception e) {
                    callback.onFailure(e);
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void enqueue(Callback<T> callback) {
        throw new IllegalStateException("The method enqueue is not allowed for this object please use the method listen instead of this");
    }



    @Override
    public Call<T> clone() {
        return null;
    }
}
