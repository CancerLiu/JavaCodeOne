package com.cpsdb.demo;

import com.cpsdb.base.common.AssertUtils;
import com.cpsdb.base.exception.CustomException;
import org.springframework.beans.factory.InitializingBean;

import java.lang.reflect.Field;

public class ValidatorHandler {

    public static void  validatorHandler(Object obj) throws IllegalAccessException {
        Field[] fieldsArray = obj.getClass().getDeclaredFields();

        for (Field field : fieldsArray) {
            field.setAccessible(true);
            Object fieldValue = field.get(obj);
            ValidatorAnnotation annotation = field.getAnnotation(ValidatorAnnotation.class);
            if (null != annotation) {
                if (annotation.notNull()) {
                    AssertUtils.notNull(fieldValue, new CustomException("属性值  " + field.getName() + "  没有收到数据"));
                }
                if (fieldValue instanceof String) {
                    AssertUtils.isTrue(((String) fieldValue).length() <= annotation.fieldLength(), new CustomException("属性值  " + field.getName() + "  数据过长"));
                }
            }
        }
    }

}
