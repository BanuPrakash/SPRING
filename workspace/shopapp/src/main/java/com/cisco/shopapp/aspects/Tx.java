package com.cisco.shopapp.aspects;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// @Transactional
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Tx {
}
