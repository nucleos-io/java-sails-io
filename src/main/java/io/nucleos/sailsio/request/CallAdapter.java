package io.nucleos.sailsio.request;

import java.lang.reflect.Type;

import io.nucleos.sailsio.Adapter;


/** Adapts a {@link Call} into the type of {@code T}. */
public interface CallAdapter<T> extends Adapter<Call, T> {
    /**
     * Returns the value type that this adapter uses when converting the HTTP response body to a Java
     * object. For example, the response type for {@code Call<Repo>} is {@code Repo}. This type
     * is used to prepare the {@code call} passed to {@code #adapt}.
     *
     * <p>Note that this is typically not the same type as the {@code returnType} provided to
     * this call adapter's factory.
     */
    @Override
    Type responseType();

    /** Returns an instance of the {@code T} which adapts the execution of {@code call}. */
    @Override
    T adapt(Call call);

    interface Factory extends Adapter.Factory{
        /**
         * Returns a call adapter for interface methods that return {@code returnType}, or null if this
         * factory doesn't adapt that type.
         */
        @Override
        CallAdapter<?> get(Type returnType);
    }
}
