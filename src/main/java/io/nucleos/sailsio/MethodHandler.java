package io.nucleos.sailsio;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * Created by cmarcano on 13/10/15.
 */
public class MethodHandler<T> {

    private SailsIO io;
    private Method method;
    private FactoryParser factoryParser;
    private Adapter<?,?> adapter;
    private Type responseType;

    /*
     * ---------------------------------------------------------------------------------------------
     * ---------------------------------------------------------------------------------------------
     * public methods
     * ---------------------------------------------------------------------------------------------
     * ---------------------------------------------------------------------------------------------
     */

    /* statics */

    /**
     *
     * @param method
     * @param io
     * @return
     */
    public static MethodHandler<?> create(Method method, SailsIO io) {
        FactoryParser factoryParser = FactoryParser.parse(method,io);
        return new MethodHandler<>(method, factoryParser, io);
    }

    /* end statics */


    /**
     *
     * @param args
     * @return
     */
    public Object invoke(Object... args) {
        if (this.factoryParser.isListener()) {

            if (args != null && args.length > 0) {
                throw new IllegalArgumentException("The @On annotation method not allow arguments");
            }

            return ((ListenerAdapter)this.adapter).adapt(new SocketListener<>(
                    this.io,
                    this.factoryParser.toListenerFactory(),
                    this.responseType));

        } else {

            return ((CallAdapter)this.adapter).adapt(new SocketCall<>(
                    this.io,
                    this.factoryParser.toRequestFactory(),
                    this.responseType,
                    args));

        }
    }

    /*
     * ---------------------------------------------------------------------------------------------
     * ---------------------------------------------------------------------------------------------
     * Private methods
     * ---------------------------------------------------------------------------------------------
     * ---------------------------------------------------------------------------------------------
     */

    /**
     * Constructor
     *
     * @param io
     * @param factoryParser
     */
    private MethodHandler(Method method, FactoryParser factoryParser, SailsIO io) {

        this.method = method;
        this.io = io;
        this.factoryParser = factoryParser;

        if (factoryParser.isListener()) {
            this.adapter = createListenerAdapter();
        } else {
            this.adapter = createCallAdapter();
        }

        this.responseType = this.adapter.responseType();
    }

    /**
     *
     * @return
     */
    private CallAdapter createCallAdapter() {
        Type returnType = this.method.getGenericReturnType();
        return this.io.callAdapter(returnType);
    }

    /**
     *
     * @return
     */
    private ListenerAdapter createListenerAdapter() {
        Type returnType = this.method.getGenericReturnType();
        return this.io.listenerAdapter(returnType);
    }
}
