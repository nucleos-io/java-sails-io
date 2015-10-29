package io.nucleos.sailsio;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.LinkedHashMap;
import java.util.Map;

import io.nucleos.sailsio.converters.gson.GsonConverterFactory;
import io.nucleos.sailsio.event.RawEvent;
import io.nucleos.sailsio.adapters.DefaultCallAdapterFactory;
import io.nucleos.sailsio.request.DefaultInterceptor;
import io.socket.client.IO;
import io.socket.client.Socket;

public class SailsIO {
    private static final String TAG = SailsIO.class.getSimpleName();


    private String baseUrl;
    private Socket socket;
    private CallAdapter.Factory callAdapterFactory;
    private io.nucleos.sailsio.request.Interceptor interceptor;
    private Converter.Factory converterFactory;
    private io.nucleos.sailsio.event.RawEvent rawEvent;
    private final Map<Method, MethodHandler> methodMethodHandlerCache;

    private SailsIO(
            String baseUrl,
            Options options,
            boolean autoConnect,
            CallAdapter.Factory callAdapterFactory,
            io.nucleos.sailsio.request.Interceptor interceptor) throws URISyntaxException {

        this.baseUrl = baseUrl;
        this.callAdapterFactory = callAdapterFactory;
        this.interceptor = interceptor;

        //
        this.socket = IO.socket(this.baseUrl, options);
        this.rawEvent = new io.nucleos.sailsio.event.RawEvent(this.socket);

        // TODO
        // Por los momentos solo se utilizara el GSONConverter
        this.converterFactory = new GsonConverterFactory();

        if (autoConnect) {
            connect();
        }
        methodMethodHandlerCache = new LinkedHashMap<>();
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
        MethodHandler handler = methodMethodHandlerCache.get(method);
        if (handler == null) {
            handler = MethodHandler.create(method,this);
        }
        return handler;
    }

    public CallAdapter callAdapter(Type returnType) {
        Utils.checkNotNull(returnType, "The returnType not should be null");
        return callAdapterFactory.get(returnType);
    }


    public <T> Converter<T, io.nucleos.sailsio.request.RequestBody> requestConverter() {
        // noinspection unchecked
        return (Converter<T, io.nucleos.sailsio.request.RequestBody>) this.converterFactory.toRequestBody();
    }

    public Converter<JSONObject, Response> responseConverter(Type type) {
        // noinspection unchecked
        return this.converterFactory.toResponse(type);
    }

    public io.nucleos.sailsio.request.Interceptor getInterceptor() {
        return this.interceptor;
    }

    public Socket getSocket() {
        return this.socket;
    }

    public void connect() {
        socket.connect();
   }

    public void onConnect(io.nucleos.sailsio.event.RawEvent.OnConnect onConnect) {
        this.rawEvent.setOnConnect(onConnect);
    }

    public void onDisconnect(RawEvent.OnDisconnect onDisconnect) {
        this.rawEvent.setOnDisconnect(onDisconnect);
    }

    public void onReconnect(io.nucleos.sailsio.event.RawEvent.OnReconnect onReconnect) {
        this.rawEvent.setOnReconnect(onReconnect);
    }

    public void onReconnectFailed(io.nucleos.sailsio.event.RawEvent.OnReconnectFailed onReconnectFailed) {
        this.rawEvent.setOnReconnectFailed(onReconnectFailed);
    }

    public void onReconnectAttempt(io.nucleos.sailsio.event.RawEvent.OnReconnectAttempt onReconnectAttempt) {
        this.rawEvent.setOnReconnectAttempt(onReconnectAttempt);
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
        private io.nucleos.sailsio.request.Interceptor interceptor;
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

        public Builder interceptor(io.nucleos.sailsio.request.Interceptor interceptor) {
            this.interceptor = interceptor;
            return this;
        }

        public SailsIO build()  {
            if (this.baseUrl == null) {
                throw new NullPointerException("The base url not should be null");
            }

            if (this.callAdapterFactory == null) {
                this.callAdapterFactory = new DefaultCallAdapterFactory();
            }

            if (this.interceptor == null) {
                this.interceptor = new DefaultInterceptor();
            }

            try {
                return new SailsIO(
                        this.baseUrl,
                        this.options,
                        this.autoConnect,
                        this.callAdapterFactory,
                        this.interceptor);
            } catch (URISyntaxException e) {
                throw new IllegalStateException("Problems creating the SailsIO object");
            }
        }
    }
}
