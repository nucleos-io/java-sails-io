package io.nucleos.sailsio.request;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.nucleos.sailsio.Utils;

/**
 * Created by cmarcano on 12/10/15.
 */
public class Request {

    private String relativeUrl;
    private String method;
    private RequestBody requestBody;

    public Request(String relativeUrl, String method, RequestBody requestBody) {
        this.relativeUrl = relativeUrl;
        this.method = method;
        this.requestBody = requestBody;
    }

    public String getMethod() {
        return this.method.toLowerCase();
    }

    public JSONObject getBody() throws JSONException {

        JSONObject json = new JSONObject();
        JsonElement params = this.requestBody.toJson();
        Gson gson = new Gson();
        String paramStr = gson.toJson(params);

        json.put("url", this.relativeUrl);

        if (params.isJsonArray()) {
            JSONArray jsonArray = new JSONArray(paramStr);
            json.put("param", jsonArray);

        } else if (params.isJsonObject()) {
            JSONObject jsonObject = new JSONObject(paramStr);
            json.put("params", jsonObject);
        }

        return json;
    }

    /**
     *
     */
    public final static class Builder {

        private String relativeUrl;
        private RequestBody body;
        private String method;


        public Builder() {
            this.body = new RequestBody(new JsonObject());
        }

        public Builder relativeUrl(String relativeUrl) {
            this.relativeUrl = relativeUrl;
            return this;
        }

        public Builder body(RequestBody body) {
            Utils.checkNotNull(body, "The body not should be null");
            this.body = body;
            return this;
        }

        public Builder method(String method) {
            this.method = method;
            return this;
        }

        public Builder addBodyParam(Param param) {
            this.body.addParam(param);
            return this;
        }

        public Request build() {
            return new Request(this.relativeUrl, this.method, this.body);
        }
    }
}
