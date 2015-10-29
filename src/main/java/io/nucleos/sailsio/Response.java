package io.nucleos.sailsio;

/**
 * Created by cmarcano on 16/10/15.
 */
public class Response<T> {

    private T data;
    private int statusCode;
//    private Headers headers;
    private String modelId;


    public Response(int statusCode) {
        this.statusCode = statusCode;
    }

    public Response(String modelId, T data) {
        this.modelId = modelId;
        this.data = data;
    }

    public Response(int statusCode, T data) {
        this.data = data;
        this.statusCode = statusCode;
//        this.headers = null;
    }

    public T getData() {
        return data;
    }

    public int getStatusCode() {
        return statusCode;
    }

//    public Headers getHeaders() {
//        return headers;
//    }

    public String getModelId() {
        return modelId;
    }
}
