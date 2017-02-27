package com.tubic.testapp.di.scopes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Scope annotation for different scopes.
 */

@javax.inject.Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface Scope {
    String value() default "";
}
