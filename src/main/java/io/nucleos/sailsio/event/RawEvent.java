package io.nucleos.sailsio.event;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * Created by cmarcano on 15/10/15.
 */
public class RawEvent {

    private Socket socket;
    private OnConnect onConnect;
    private OnDisconnect onDisconnect;
    private OnReconnect onReconnect;
    private OnReconnectFailed onReconnectFailed;
    private OnReconnectAttempt onReconnectAttempt;

    public RawEvent(Socket socket) {
        this.socket = socket;
    }

    public void setOnConnect(OnConnect onConnect) {
        this.onConnect = onConnect;
        onConnectEvent();
    }

    public void setOnDisconnect(OnDisconnect onDisconnect) {
        this.onDisconnect = onDisconnect;
        onDisconnectEvent();
    }

    public void setOnReconnect(OnReconnect onReconnect) {
        this.onReconnect = onReconnect;
        onReconnect();
    }

    public void setOnReconnectFailed(OnReconnectFailed onReconnectFailed) {
        this.onReconnectFailed = onReconnectFailed;
        onReconnectFailed();
    }

    public void setOnReconnectAttempt(OnReconnectAttempt onReconnectAttemp) {
        this.onReconnectAttempt = onReconnectAttemp;
        onReconnectAttemp();
    }

    /*
     * ---------------------------------------------------------------------------------------------
     * ---------------------------------------------------------------------------------------------
     * private methods
     * ---------------------------------------------------------------------------------------------
     * ---------------------------------------------------------------------------------------------
     */

    private void onDisconnectEvent() {
        this.socket.on(Socket.EVENT_DISCONNECT, new RawEmitterListener(this.onDisconnect, Socket.EVENT_DISCONNECT));
    }

    private void onConnectEvent() {
        this.socket.on(Socket.EVENT_CONNECT, new RawEmitterListener(this.onConnect, Socket.EVENT_CONNECT));
    }

    private void onReconnect() {
        this.socket.on(Socket.EVENT_RECONNECT, new RawEmitterListener(this.onReconnect, Socket.EVENT_RECONNECT));
    }

    private void onReconnectFailed() {
        this.socket.on(Socket.EVENT_RECONNECT_FAILED, new RawEmitterListener(this.onReconnectFailed, Socket.EVENT_RECONNECT_FAILED));
    }

    private void onReconnectAttemp() {
        this.socket.on(Socket.EVENT_RECONNECT_ATTEMPT, new RawEmitterListener(this.onReconnectAttempt, Socket.EVENT_RECONNECT_ATTEMPT));
    }

    /*
     * ---------------------------------------------------------------------------------------------
     * ---------------------------------------------------------------------------------------------
     * Classes
     * ---------------------------------------------------------------------------------------------
     * ---------------------------------------------------------------------------------------------
     */

    private class RawEmitterListener implements Emitter.Listener {
        OnRawEvent event;
        String eventName;

        public RawEmitterListener(OnRawEvent event, String eventName) {
            this.event = event;
            this.eventName = eventName;
        }

        @Override
        public void call(Object... args) {
            if (event != null) {
                event.onEvent(args);
            }
        }

        @Override
        public String toString() {
            return "RawEmitterListener{" +
                    "event=" + event + '\'' +
                    ", eventName='" + eventName + '\'' +
                    '}';
        }
    }

    /*
     * ---------------------------------------------------------------------------------------------
     * ---------------------------------------------------------------------------------------------
     * Interfaces
     * ---------------------------------------------------------------------------------------------
     * ---------------------------------------------------------------------------------------------
     */
    private interface OnRawEvent {
        void onEvent(Object... args);
    }

    public interface OnConnect extends OnRawEvent {}

    public interface OnDisconnect extends OnRawEvent {}

    public interface OnReconnect extends OnRawEvent {}

    public interface OnReconnectFailed extends OnRawEvent {}

    public interface OnReconnectAttempt extends OnRawEvent {}

}
