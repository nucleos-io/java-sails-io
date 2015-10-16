package io.nucleos.sailsio;

import org.json.JSONObject;

import java.lang.reflect.Type;

import io.socket.emitter.Emitter;

/**
 * Created by cmarcano on 15/10/15.
 */
public class SocketListener<T> implements Listener<T> {

    private SailsIO io;
    private ListenerFactory listenerFactory;
    private Type responseType;

    public SocketListener(SailsIO io, ListenerFactory listenerFactory, Type responseType) {
        this.io = io;
        this.listenerFactory = listenerFactory;
        this.responseType = responseType;
    }

    @Override
    public void listen(final Callback<T> callback) {

        System.out.println("Listen event: "+ listenerFactory.getEventName());

        this.io.getSocket().on(listenerFactory.getEventName(), new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("Event received");
                for (Object obj:args) {
                    System.out.println("arg: " + obj);
                }

                JSONObject responseJson  = (JSONObject) args[0];
                ResponseRequest<T> responseRequest = null;
                try {
                    responseRequest = io.responseConverter(responseType).convert(responseJson);
                    callback.onResponse(responseRequest);
                } catch (Exception e) {
                    callback.onFailure(e);
                    e.printStackTrace();
                }

            }
        });

    }
}
