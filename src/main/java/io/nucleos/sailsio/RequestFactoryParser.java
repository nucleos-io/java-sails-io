package io.nucleos.sailsio;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import io.nucleos.sailsio.annotations.Body;
import io.nucleos.sailsio.annotations.DELETE;
import io.nucleos.sailsio.annotations.GET;
import io.nucleos.sailsio.annotations.POST;
import io.nucleos.sailsio.annotations.PUT;

/**
 * Created by luis on 11/10/15.
 */
public class RequestFactoryParser {

    private Method method;
    private String httpMethod;
    private String relativeUrl;
    private SailsIO io;
    private List<RequestArgument> requestArguments;

    private RequestFactoryParser(Method method, SailsIO io) {
        this.method = method;
        this.io = io;
        this.requestArguments = new ArrayList<>();
    }

    public static RequestFactory parse(Method method, SailsIO io) {

        RequestFactoryParser parser = new RequestFactoryParser(method, io);
        parser.parseMethodAnnotations();
        parser.parseArgumentAnnotations();

        return new RequestFactory(parser.httpMethod, parser.relativeUrl, parser.requestArguments);
    }

    private void parseMethodAnnotations() {
        for (Annotation annotation: method.getAnnotations()) {
            if (annotation instanceof GET) {
                setRequestParameters("GET", ((GET) annotation).value());;
            } else if (annotation instanceof POST) {
                setRequestParameters("POST", ((POST) annotation).value());
            } else if (annotation instanceof PUT) {
                setRequestParameters("PUT", ((PUT) annotation).value());
            } else if (annotation instanceof DELETE) {
                setRequestParameters("DELETE", ((DELETE) annotation).value());
            }
            // TODO: ON annotation
        }
    }

    private void parseArgumentAnnotations() {
        boolean hasBody = false;

        Annotation[][] annotations = method.getParameterAnnotations();
        for (Annotation[] parameterAnnotations : annotations) {
            for (Annotation annotation : parameterAnnotations) {
                if (annotation instanceof Body) {
                    if (hasBody) {
                        throw new IllegalArgumentException("The method should have only one @Body annotation");
                    }
                    hasBody = true;
                    Converter<?, RequestBody> converter = this.io.requestConverter();
                    requestArguments.add(new RequestArgument.Body<>(converter));
                }
            }
        }
    }

    private void setRequestParameters(String httpMethod, String relativeUrl) {
        if (httpMethod == null) {
            throw new IllegalArgumentException("The httpMethod should be different to null");
        }

        if (this.httpMethod != null) {
            throw new IllegalArgumentException("The method should have only one Http method annotation");
        }

        this.httpMethod = httpMethod;
        this.relativeUrl = relativeUrl;
    }
}
