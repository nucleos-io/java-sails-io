package io.nucleos.sailsio;

import io.socket.client.Socket;

/**
 * Created by cmarcano on 07/10/15.
 */
public class SocketCall<T> implements Call<T> {

    private Socket io;

    public SocketCall(Socket io) {
        this.io = io;
    }

    @Override
    public void enqueue(Callback<T> callback) {

    }

    @Override
    public void cancel() {

    }

    @Override
    public Call<T> clone() {
        return null;
    }
}
