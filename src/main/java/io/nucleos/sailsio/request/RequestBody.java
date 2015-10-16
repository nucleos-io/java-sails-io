package io.nucleos.sailsio.request;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luis on 10/10/15.
 */
public class RequestBody {

    private JsonElement body;
    private String key;
    private List<Param> params;

    public RequestBody(JsonElement value) {
        this.body = value;
        this.params = new ArrayList<>();
        this.key = "";
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void addParam(Param param) {
        this.params.add(param);
    }

    public JsonElement toJson() {

        JsonObject jsonBody = new JsonObject();

        if (this.key.isEmpty() && !this.params.isEmpty() && this.body.isJsonArray()) {
            throw new IllegalArgumentException("For this use case the @Body annotation need a key");
        }

        if (this.key.isEmpty() && this.body.isJsonArray()) {
            return this.body;
        }

        if (!this.key.isEmpty()) {
            jsonBody.add(this.key, this.body);
        } else {
            jsonBody = this.body.getAsJsonObject();
        }

        for (Param param : this.params) {
            jsonBody.addProperty(param.getKey(), param.getValue());
        }

        return jsonBody;
    }
}
