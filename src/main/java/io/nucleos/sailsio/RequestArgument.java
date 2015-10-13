package io.nucleos.sailsio;

/**
 * Created by cmarcano on 13/10/15.
 */
public abstract class RequestArgument<T> {
    abstract void perform(Request.Builder requestBuilder, T value);


    public static class Body<T> extends RequestArgument<T> {
        private Converter<T, RequestBody> requestConverter;

        public Body(Converter<T, RequestBody> requestConverter) {
            this.requestConverter = requestConverter;
        }

        @Override
        void perform(Request.Builder requestBuilder, T value) {
            RequestBody body = this.requestConverter.convert(value);
            requestBuilder.body(body);
        }
    }

}
