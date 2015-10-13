package io.nucleos.sailsio;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by cmarcano on 07/10/15.
 */
public class DefaultCallAdapterFactory implements CallAdapter.Factory {
    @Override
    public CallAdapter<?> get(Type returnType) {
        if (Utils.getRawType(returnType) != Call.class) {
            return null;
        }
        Type responseType = getCallResponseType(returnType);
        return new DefaultAdapter(responseType);
    }


    private Type getCallResponseType(Type returnType) {
        if (!(returnType instanceof ParameterizedType)) {
            throw new IllegalArgumentException(
                    "Call return type must be parameterized as Call<Foo> or Call<? extends Foo>");
        }
        return Utils.getSingleParameterUpperBound((ParameterizedType) returnType);
    }


}
