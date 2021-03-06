package io.nucleos.sailsio.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by cmarcano on 07/10/15.
 */
@Retention(RUNTIME)
@Target(PARAMETER)
public @interface Body {

    public String value() default "";
}
