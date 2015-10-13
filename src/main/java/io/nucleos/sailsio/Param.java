package io.nucleos.sailsio;

/**
 * Created by cmarcano on 07/10/15.
 */
public class Param {

    private String key;
    private String value;

    public Param(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }
}
