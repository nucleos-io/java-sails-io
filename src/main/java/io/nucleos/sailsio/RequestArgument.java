package io.nucleos.sailsio;

/**
 * Created by cmarcano on 13/10/15.
 */
public abstract class RequestArgument<T> {
    abstract void perform(Request.Builder requestBuilder, T value);


    public static class Body<T> extends RequestArgument<T> {
        private Converter<T, RequestBody> requestConverter;
        private String key;

        public Body(Converter<T, RequestBody> requestConverter, String key) {
            this.requestConverter = requestConverter;
            this.key = key;
        }

        @Override
        void perform(Request.Builder requestBuilder, T value) {
            RequestBody body = null;
            try {
                body = this.requestConverter.convert(value);
                body.setKey(this.key);
            } catch (Exception e) {
                e.printStackTrace();
            }
            requestBuilder.body(body);
        }
    }

}
