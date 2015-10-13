package io.nucleos.sailsio;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * Created by cmarcano on 13/10/15.
 */
public class MethodHandler<T> {

    private SailsIO io;
    private RequestFactory requestFactory;
    private CallAdapter callAdapter;

    public MethodHandler(SailsIO io, RequestFactory requestFactory, CallAdapter callAdapter) {
        this.io = io;
        this.requestFactory = requestFactory;
        this.callAdapter = callAdapter;
    }

    public static MethodHandler<?> create(Method method, SailsIO io) {
        RequestFactory requestFactory = RequestFactoryParser.parse(method, io);
        CallAdapter callAdapter = createCallAdapter(method, io);
        return new MethodHandler<>(io, requestFactory, callAdapter);
    }

    private static CallAdapter createCallAdapter(Method method, SailsIO io) {
        Type returnType = method.getGenericReturnType();
        return io.callAdapter(returnType);
    }

    public Object invoke(Object... args) {
        return this.callAdapter.adapt(new SocketCall<>(this.io, this.requestFactory, args));
    }

}
