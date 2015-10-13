package io.nucleos.sailsio;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luis on 10/10/15.
 */
public class RequestBody {
    private String body;
    private List<Param> params;

    public RequestBody(String value) {
        this.body = value;
        this.params = new ArrayList<>();
    }

    public void addParam(Param param) {
        this.params.add(param);
    }

    public JSONObject toJson() throws JSONException {
        JSONObject jsonBody = new JSONObject(this.body);

        for (Param param : this.params) {
            jsonBody.put(param.getKey(), param.getValue());
        }

        return jsonBody;
    }
}
