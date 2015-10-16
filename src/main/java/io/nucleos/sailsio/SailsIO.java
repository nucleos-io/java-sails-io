package io.nucleos.sailsio;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class SailsIO {
    private static final String TAG = SailsIO.class.getSimpleName();


    private String baseUrl;
    private Socket socket;
    private CallAdapter.Factory callAdapterFactory;
    private ListenerAdapter.Factory listenerAdapterFactory;
    private Interceptor interceptor;
    private Converter.Factory converterFactory;
    private RawEvent rawEvent;

    private SailsIO(
            String baseUrl,
            Options options,
            boolean autoConnect,
            CallAdapter.Factory callAdapterFactory,
            ListenerAdapter.Factory listenerAdapterFactory,
            Interceptor interceptor) throws URISyntaxException {

        this.baseUrl = baseUrl;
        this.callAdapterFactory = callAdapterFactory;
        this.listenerAdapterFactory = listenerAdapterFactory;
        this.interceptor = interceptor;

        //
        this.socket = IO.socket(this.baseUrl, options);
        this.rawEvent = new RawEvent(this.socket);

        // TODO
        // Por los momentos solo se utilizara el GSONConverter
        this.converterFactory = new GsonConverterFactory();

        if (autoConnect) {
            connect();
        }
    }

    /**
     *
     * @param service
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T create(Class<T> service) {
        return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[] {service},
        new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return loadMethodHandler(method).invoke(args);
            }
        });
    }

    private MethodHandler<?> loadMethodHandler(Method method) {
        return MethodHandler.create(method, this);
    }

    public CallAdapter callAdapter(Type returnType) {
        Utils.checkNotNull(returnType, "The returnType not should be null");
        return callAdapterFactory.get(returnType);
    }

    public ListenerAdapter listenerAdapter(Type returnType) {
        return listenerAdapterFactory.get(returnType);
    }

    public <T> Converter<T, RequestBody> requestConverter() {
        // noinspection unchecked
        return (Converter<T, RequestBody>) this.converterFactory.toRequestBody();
    }

    public Converter<JSONObject, ResponseRequest> responseConverter(Type type) {
        // noinspection unchecked
        return this.converterFactory.toResponseRequest(type);
    }

    public Interceptor getInterceptor() {
        return this.interceptor;
    }

    public Socket getSocket() {
        return this.socket;
    }

    public void connect() {
        socket.connect();
   }

    public void onConnect(RawEvent.OnConnect onConnect) {
        this.rawEvent.setOnConnect(onConnect);
    }

    public void onDisconnect(RawEvent.OnDisconnect onDisconnect) {
        this.rawEvent.setOnDisconnect(onDisconnect);
    }

    public void disconnect() {
        socket.disconnect();
    }

    @Override
    public String toString() {
        JSONObject json = new JSONObject();
        try {
            json.put("baseUrl", baseUrl);
            json.put("socket", socket.toString());
            json.put("callAdapterFactory", callAdapterFactory.toString());
            json.put("interceptor", interceptor.toString());
            json.put("converterFactory", converterFactory.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json.toString();
    }

    /*
     * ---------------------------------------------------------------------------------------------
     * ---------------------------------------------------------------------------------------------
     * Builder class
     * ---------------------------------------------------------------------------------------------
     * ---------------------------------------------------------------------------------------------
     */
    public static final class Builder {
        private String baseUrl;
        private boolean autoConnect;
        private CallAdapter.Factory callAdapterFactory;
        private ListenerAdapter.Factory listenerAdapterFactory;
        private Interceptor interceptor;
        private Options options;

        public Builder() {
            this.callAdapterFactory = null;
            this.interceptor = null;
            this.baseUrl =  null;
            this.autoConnect = true;
            this.options = new Options();
        }

        public Builder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public  Builder autoConnect(boolean autoConnect) {
            this.autoConnect = autoConnect;
            return this;
        }

        public Builder options(Options options) {
            this.options = options;
            return this;
        }

        public Builder callAdapterFactory(CallAdapter.Factory callAdapterFactory) {
            this.callAdapterFactory = callAdapterFactory;
            return this;
        }

        public Builder listenerAdapterFactory(ListenerAdapter.Factory listenerAdapterFactory) {
            this.listenerAdapterFactory = listenerAdapterFactory;
            return this;
        }

        public Builder interceptor(Interceptor interceptor) {
            this.interceptor = interceptor;
            return this;
        }

        public SailsIO build() throws URISyntaxException {
            if (this.baseUrl == null) {
                throw new NullPointerException("The base url not should be null");
            }

            if (this.callAdapterFactory == null) {
                this.callAdapterFactory = new DefaultCallAdapterFactory();
            }

            if (this.listenerAdapterFactory == null) {
                this.listenerAdapterFactory = new DefaultListenerAdapterFactory();
            }

            if (this.interceptor == null) {
                this.interceptor = new DefaultInterceptor();
            }

            return new SailsIO(
                    this.baseUrl,
                    this.options,
                    this.autoConnect,
                    this.callAdapterFactory,
                    this.listenerAdapterFactory,
                    this.interceptor);
        }
    }
}
