package io.nucleos.sailsio;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.LinkedHashSet;
import java.util.Set;

import io.socket.client.IO;
import io.socket.client.Socket;

public class SailsIO {

    private String baseUrl;
    private Socket socket;
    private Set<ConnectionParam> connectionParams;
    private CallAdapter.Factory callAdapterFactory;
    private Interceptor interceptor;

    /**
     *
     * @param baseUrl
     * @param connectionParams
     */
    private SailsIO(
            String baseUrl,
            Set<ConnectionParam> connectionParams,
            boolean autoConnect,
            CallAdapter.Factory callAdapterFactory,
            Interceptor interceptor) throws URISyntaxException {

        this.baseUrl = baseUrl;
        this.connectionParams = connectionParams;
        this.callAdapterFactory = callAdapterFactory;
        this.interceptor = interceptor;

        IO.Options options = new IO.Options();
        socket = IO.socket(this.baseUrl, options);

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
    public <T> T cretate(Class<T> service) {
        return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[] {service},
        new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return null;
            }
        });
    }

    public CallAdapter callAdapter(Type returnType) {
        Utils.checkNotNull(returnType, "The returnType not should be null");
        return callAdapterFactory.get(returnType);
    }

    public void connect() {
        socket.connect();
    }

    public void disconnect() {
        socket.disconnect();
    }

    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------
    // Builder class
    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------
    /**
     *
     *
     */
    public static final class Builder {
        private String baseUrl;
        private Set<ConnectionParam> connectionParams;
        private boolean autoConnect;
        private CallAdapter.Factory callAdapterFactory;
        private Interceptor interceptor;

        public Builder() {
            this.callAdapterFactory = null;
            this.interceptor = null;
            this.baseUrl =  null;
            this.connectionParams = new LinkedHashSet<ConnectionParam>();
            this.autoConnect = true;
        }

        public Builder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public  Builder autoConnect(boolean autoConnect) {
            this.autoConnect = autoConnect;
            return this;
        }

        public Builder connectionParam(ConnectionParam param) {
            this.connectionParams.add(param);
            return this;
        }

        public Builder connectionParams(Set<ConnectionParam> params) {
            this.connectionParams.addAll(params);
            return this;

        }

        public Builder callAdapterFactory(CallAdapter.Factory callAdapterFactory) {
            this.callAdapterFactory = callAdapterFactory;
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

            if (this.interceptor == null) {
                this.interceptor = new DefaultInterceptor();
            }

            return  new SailsIO(
                    this.baseUrl,
                    this.connectionParams,
                    this.autoConnect,
                    this.callAdapterFactory,
                    this.interceptor);
        }
    }
}
