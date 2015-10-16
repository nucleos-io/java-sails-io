package io.nucleos.sailsio.request;

/**
 * TODO
 */
public final class ResponseRequest<T> {

    private T body;
    private Headers headers;
    private int statusCode;

    private ResponseRequest(T body, Headers headers, int statusCode) {
        this.body = body;
        this.headers = headers;
        this.statusCode = statusCode;
    }

    public T getBody() {
        return body;
    }

    public Headers getHeaders() {
        return headers;
    }

    public int getStatusCode() {
        return statusCode;
    }

    @Override
    public String toString() {
        return "ResponseRequest{" +
                "body=" + body +
                ", headers=" + headers +
                ", statusCode=" + statusCode +
                '}';
    }

    public final static class Builder<T> {

        private T body;
        private Headers headers;
        private int statusCode;

        public Builder body(T body) {
            this.body = body;
            return this;
        }

        public Builder headers(Headers headers) {
            this.headers = headers;
            return this;
        }

        public Builder statusCode(int statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public ResponseRequest build() {
            return new ResponseRequest<>(this.body, this.headers, this.statusCode);
        }
    }
}
