package io.nucleos.sailsio;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import io.nucleos.sailsio.annotation.Body;
import io.nucleos.sailsio.annotation.DELETE;
import io.nucleos.sailsio.annotation.GET;
import io.nucleos.sailsio.annotation.ON;
import io.nucleos.sailsio.annotation.POST;
import io.nucleos.sailsio.annotation.PUT;
import io.nucleos.sailsio.request.RequestBody;
import io.nucleos.sailsio.request.RequestFactory;

/**
 * Created by luis on 11/10/15.
 */
public class FactoryParser {

    private Method method;
    private String httpMethod;
    private String relativeUrl;
    private SailsIO io;
    private List<io.nucleos.sailsio.request.RequestArgument> requestArguments;
    private String onEvent;

    private FactoryParser(Method method, SailsIO io) {
        this.method = method;
        this.io = io;
        this.requestArguments = new ArrayList<>();
        this.onEvent = "";
    }

    public static FactoryParser parse(Method method, SailsIO io) {
        FactoryParser parser = new FactoryParser(method,io);
        parser.parseMethodAnnotations();
        parser.parseArgumentAnnotations();
        return parser;
    }

    public boolean isListener() {
        return !this.onEvent.isEmpty();
    }

    public io.nucleos.sailsio.request.RequestFactory toRequestFactory() {
        return new RequestFactory(this.httpMethod, this.relativeUrl, this.requestArguments);

    }

    public io.nucleos.sailsio.event.ListenerFactory toListenerFactory() {
        return new io.nucleos.sailsio.event.ListenerFactory(onEvent);
    }

    public Method getMethod() {
        return method;
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
            } else if (annotation instanceof ON) {
                onEvent = ((ON) annotation).value();
            }
        }
    }

    private void parseArgumentAnnotations() {
        boolean hasBody = false;
        Annotation[][] annotations = method.getParameterAnnotations();
        if (annotations.length > 0 && !onEvent.isEmpty()) {
            throw new IllegalArgumentException("The @ON annotation method not allow annotations");
        }
        for (Annotation[] parameterAnnotations : annotations) {
            for (Annotation annotation : parameterAnnotations) {
                if (annotation instanceof Body) {
                    if (hasBody) {
                        throw new IllegalArgumentException("The method should have only one @Body annotation");
                    }
                    hasBody = true;
                    Converter<?, RequestBody> converter = this.io.requestConverter();
                    requestArguments.add(new io.nucleos.sailsio.request.RequestArgument.Body<>(converter, ((Body) annotation).value()));
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
