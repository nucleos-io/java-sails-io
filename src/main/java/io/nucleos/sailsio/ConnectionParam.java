package io.nucleos.sailsio;

import java.util.Arrays;

/**
 * Created by cmarcano on 07/10/15.
 */
public class ConnectionParam extends Param {

    private static final String[] ALLOWED_PARAMS = {
            "sails_version",
            "access_token"
    };

    public ConnectionParam(String key, String value) {
        super(key, value);
    }

    @Override
    public boolean checkAllowedPrams(String key) {
        return Arrays.asList(ALLOWED_PARAMS).contains(key);
    }
}
