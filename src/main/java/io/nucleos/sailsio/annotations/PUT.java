package io.nucleos.sailsio.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by cmarcano on 07/10/15.
 */
@Target(METHOD)
@Retention(RUNTIME)
public @interface PUT {
    String value() default "";
}
