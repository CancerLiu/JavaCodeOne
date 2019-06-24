package com.annotationonedemo;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(RepeatableAnnotation.class)
public @interface RepeatableElement {
    public String testOne() default "haha";
}
