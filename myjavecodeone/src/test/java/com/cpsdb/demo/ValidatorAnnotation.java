package com.cpsdb.demo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ValidatorAnnotation {

    /**
     * 指定校验的字段的字符长度，默认为200(只对String类型的字段长度进行校验)
     */
    int fieldLength() default 200;

    /**
     * 指定校验的字段是否进行非空校验，默认进行
     */
    boolean notNull() default true;

}
