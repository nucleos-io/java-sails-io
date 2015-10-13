package io.nucleos.sailsio;

/**
 * Created by cmarcano on 07/10/15.
 */
public abstract class Param {

    private String key;
    private String value;

    public Param(String key, String value) {

        if (!checkAllowedPrams(key)) {
            throw new IllegalArgumentException("The key " + key + " is not allowed");
        }

        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }

    public abstract boolean checkAllowedPrams(String key);
}
