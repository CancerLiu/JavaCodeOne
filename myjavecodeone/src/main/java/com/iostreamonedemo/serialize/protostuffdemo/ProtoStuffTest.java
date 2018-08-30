package com.iostreamonedemo.serialize.protostuffdemo;

import com.cpsdb.base.common.CPSDateUtils;
import com.cpsdb.base.mapper.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class ProtoStuffTest {

    private final static Logger logger = LoggerFactory.getLogger(ProtoStuffTest.class);

    private byte[] toSerialize() {
        User user = new User().setAge(28).setBirthday(new Date()).setCellphone("13730885730").setName("liuchao");
        byte[] bytes = ProtoStuffUtil.toSerialize(user);
        logger.info(JsonMapper.buildNonNullMapper().toJson(bytes));
        return bytes;
    }

    private Object fromSerialize(byte[] bytes) throws InstantiationException, IllegalAccessException {
        return ProtoStuffUtil.fromSerialize(bytes, User.class);
    }


    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        ProtoStuffTest protoStuffTest = new ProtoStuffTest();
        byte[] bytes = protoStuffTest.toSerialize();
        User user = (User) protoStuffTest.fromSerialize(bytes);

        logger.info(user.getCellphone() + "       " + user.getName() + "        " + user.getAge() + "       " + CPSDateUtils.toString(user.getBirthday(), "yyyy-MM-dd"));
    }
}
