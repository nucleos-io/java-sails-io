package io.nucleos.sailsio;

import org.json.JSONException;
import org.json.JSONObject;

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
        return this.method;
    }

    public JSONObject getBody() throws JSONException {
        JSONObject json = new JSONObject();

        json.put("param", this.requestBody.toJson());
        json.put("url", this.relativeUrl);

        return json;
    }

    /**
     *
     */
    final static class Builder {

        private String relativeUrl;
        private RequestBody body;
        private String method;

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
            Utils.checkNotNull(this.body, "The body not should be null");
            this.body.addParam(param);
            return this;
        }

        public Request build() {
            return new Request(this.relativeUrl, this.method, this.body);
        }
    }
}
