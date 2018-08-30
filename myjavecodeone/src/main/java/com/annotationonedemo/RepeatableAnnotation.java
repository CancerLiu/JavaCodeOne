package com.annotationonedemo;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)

public @interface RepeatableAnnotation {


    RepeatableElement[] value();

}
