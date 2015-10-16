package io.nucleos.sailsio;

import java.util.List;

/**
 * Created by cmarcano on 13/10/15.
 */
public class RequestFactory {

    private String httpMethod;
    private String relativeUrl;
    private List<RequestArgument> requestArguments;

    public RequestFactory(String httpMethod, String relativeUrl, List<RequestArgument> requestArguments) {
        this.httpMethod = httpMethod;
        this.relativeUrl = relativeUrl;
        this.requestArguments = requestArguments;
    }

    public Request.Builder create(Object ... args) {

        Request.Builder builder = new Request.Builder()
                .relativeUrl(this.relativeUrl)
                .method(this.httpMethod);

        if (args != null) {
            if (requestArguments.size() != args.length) {
                throw new IllegalArgumentException("Argument count ("
                        + args.length
                        + ") doesn't match action count ("
                        + requestArguments.size()
                        + ")");
            }

            for (int i = 0, count = args.length; i < count; i++) {
                this.requestArguments.get(i).perform(builder, args[i]);
            }
        }

        return builder;
    }

}
