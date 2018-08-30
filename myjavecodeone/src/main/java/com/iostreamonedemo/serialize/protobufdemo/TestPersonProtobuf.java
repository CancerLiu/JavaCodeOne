package com.iostreamonedemo.serialize.protobufdemo;

import com.cpsdb.base.mapper.JsonMapper;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestPersonProtobuf {

    private final static Logger logger = LoggerFactory.getLogger(TestPersonProtobuf.class);

    /**
     * 序列化为相应二进制字节数组
     *
     * @return
     */
    private byte[] toSerialize() {
        //序列化
        FirstProtobuf.Person.Builder builderProto = FirstProtobuf.Person.newBuilder();
        builderProto.setId(1);
        builderProto.setEmail("1124661130@qq.com");
        builderProto.setName("liuchao");

        FirstProtobuf.Person info = builderProto.build();

        byte[] result = info.toByteArray();

        return result;
    }

    private Object fromSerialize(byte[] result) throws InvalidProtocolBufferException {
        return FirstProtobuf.Person.parseFrom(result);
    }

    public static void main(String[] args) throws InvalidProtocolBufferException {
        TestPersonProtobuf testProtobuf = new TestPersonProtobuf();
        byte[] result = testProtobuf.toSerialize();

        logger.info(JsonMapper.buildNonNullMapper().toJson(result));

        FirstProtobuf.Person person = ((FirstProtobuf.Person) testProtobuf.fromSerialize(result));
        logger.info(person.getEmail() + "     " + person.getName() + "     " + person.getId());
    }

}
