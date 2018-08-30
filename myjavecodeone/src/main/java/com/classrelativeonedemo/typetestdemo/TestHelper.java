package com.classrelativeonedemo.typetestdemo;

import com.cpsdb.base.mapper.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.*;

public class TestHelper {

    private final static Logger logger = LoggerFactory.getLogger(TestHelper.class);

    public static void testParameterizedType() {
        Field f = null;
        try {
            Field[] fields = ParameterizedTypeBean.class.getDeclaredFields();
            // 打印出所有的 Field 的 TYpe 是否属于 ParameterizedType
            for (int i = 0; i < fields.length; i++) {
                f = fields[i];
                logger.info(f.getName() + "     getGenericType() instanceof ParameterizedType       " + (f.getGenericType() instanceof ParameterizedType));
//                 logger.info(" getGenericType "+f.getGenericType()+"     getType    "+f.getType());
            }
            getParameterizedTypeMes("map");
            getParameterizedTypeMes("entry");


        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private static void getParameterizedTypeMes(String fieldName) throws NoSuchFieldException {
        Field f;
        f = ParameterizedTypeBean.class.getDeclaredField(fieldName);
        f.setAccessible(true);
        logger.info(f.getGenericType() + "");
        boolean b = f.getGenericType() instanceof ParameterizedType;
        logger.info(b + "");
        if (b) {
            ParameterizedType pType = (ParameterizedType) f.getGenericType();
            logger.info(pType.getRawType() + "");
            for (Type type : pType.getActualTypeArguments()) {
                logger.info(type + "");
            }
            logger.info(pType.getOwnerType() + "");
        }
    }

    public static void getParameterizedTypeRelationParam(String fieldName) throws NoSuchFieldException {
        Field f;
        f = ParameterizedTypeBean.class.getDeclaredField(fieldName);
        f.setAccessible(true);
        boolean b = f.getGenericType() instanceof ParameterizedType;
        logger.info(b + "");
        if (b) {
            ParameterizedType pType = (ParameterizedType) f.getGenericType();
            logger.info(pType.getRawType() + "");
            logger.info(pType.getOwnerType() + "");
            logger.info(JsonMapper.buildNonNullTimeFormatMapper().toJson(pType.getActualTypeArguments()));
            logger.info(pType.getTypeName());
        }
    }

    public static void getTypeVariableParam(String fieldName) throws NoSuchFieldException {
        Field f;
        f = TypeVariableBean.class.getDeclaredField(fieldName);
        f.setAccessible(true);
        TypeVariable eyType = (TypeVariable) f.getGenericType();
        logger.info(eyType.getName());
        logger.info(eyType.getGenericDeclaration() + "");
        logger.info(JsonMapper.buildNonNullMapper().toJson(eyType.getBounds()));
    }


    public static void getArrayTypeParam(String fieldArrayName) throws NoSuchFieldException {
//      1.  用于判断是否是GenericArrayType的代码
//        Field[] fieldArray;
//        fieldArray = GenericArrayTypeBean.class.getDeclaredFields();
//        for(Field field:fieldArray){
//            if(field.getGenericType() instanceof GenericArrayType){
//                logger.info("true");
//            }else{
//                logger.info("false");
//            }
//        }
//      2.试验唯一方法
        Field field = GenericArrayTypeBean.class.getDeclaredField(fieldArrayName);
        field.setAccessible(true);
        if (field.getGenericType() instanceof GenericArrayType) {
            logger.info(JsonMapper.buildNonNullMapper().toJson(((GenericArrayType) field.getGenericType()).getGenericComponentType()/*.getTypeName()*/));
        }
    }

    public static void getWildCardParam() {
        //通配符类型是属于泛型类型参数里面的一种，所以基本的逻辑是先判断属性field是不是属于ParameterizedType这个泛型类型中，
        //如果属于再通过方法得到泛型类中的泛型参数类型数组，再遍历这个数组看其中的元素是不是属于WildCard，属于强转为WildCardType后再调用方法
        //extends 用来指定上边界，没有指定的话上边界默认是 Object， super 用来指定下边界，没有指定的话为 null。
        Field[] fArray;
        fArray = WildcardTypeBean.class.getDeclaredFields();
        for (Field f : fArray) {
            Type type = f.getGenericType();
            String nameStr = type.getTypeName();
            if (!(type instanceof ParameterizedType)) {
                logger.info(nameStr + " 属性不是泛型属性");
                continue;
            }
            //先转化为ParameterizedType
            ParameterizedType parameterizedType = (ParameterizedType) type;
            //可能不止一个泛型类型参数，所以返回的是一个泛型类型参数的数组
            Type[] paramType = parameterizedType.getActualTypeArguments();
            for (Type t : paramType) {
                if (!(t instanceof WildcardType)) {
                    logger.info(nameStr + " 属性不是拥有通配符的泛型属性");
                    continue;
                }
                //确认是有通配符的泛型属性之后，再通过通配符泛型Type的相应方法得到相应信息
                WildcardType wildcardType = (WildcardType) t;
                Type[] lowerTypes = wildcardType.getLowerBounds();
                //因为通配符可能同时实现、继承了几个接口，所以返回的是一个数组
                if (lowerTypes != null) {
                    logger.info("属性" + nameStr + "通配符的下限是" + JsonMapper.buildNonNullMapper().toJson(lowerTypes));
                }
                Type[] upperTypes = wildcardType.getUpperBounds();
                if (upperTypes != null) {
                    logger.info("属性" + nameStr + "通配符的上限是" + JsonMapper.buildNonNullMapper().toJson(upperTypes));
                }
            }
        }
    }

    public static void main(String[] args) throws NoSuchFieldException, NoSuchMethodException {
//        TestHelper.getParameterizedTypeRelationParam("map");
        TestHelper.getTypeVariableParam("studentUser");
//        TestHelper.getArrayTypeParam("pTypeArray");
//        TestHelper.getWildCardParam();
    }
}
