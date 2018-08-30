package com.iostreamonedemo.serialize.protobufdemo;

import com.cpsdb.base.mapper.JsonMapper;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestListingAndNestingProtobuf {

    private final static Logger logger = LoggerFactory.getLogger(TestListingAndNestingProtobuf.class);

    //对于List类型的文件，为之赋值的时候
    private byte[] toSerialize() {
        ListingAndGeneralNesting.Person.Builder build = ListingAndGeneralNesting.Person.newBuilder();
        build.setName("liuchao");
        build.setClazz(1);
        build.setObject(ListingAndGeneralNesting.NestingObject.newBuilder().setGrade(100)
                .setTitle("第一次考试").addSubject("第一题").addSubject("第二题").setSubject(1, "第三题")
        );

        return build.build().toByteArray();
    }

    private Object fromSerialize(byte[] result) throws InvalidProtocolBufferException {
        return ListingAndGeneralNesting.Person.parseFrom(result);
    }

    public static void main(String[] args) throws InvalidProtocolBufferException {
        TestListingAndNestingProtobuf testListingAndNestingProtobuf = new TestListingAndNestingProtobuf();
        byte[] bytes = testListingAndNestingProtobuf.toSerialize();

        logger.info(JsonMapper.buildNonNullMapper().toJson(bytes));

        ListingAndGeneralNesting.Person listingAndNesting = (ListingAndGeneralNesting.Person) testListingAndNestingProtobuf.fromSerialize(bytes);
        logger.info(listingAndNesting.getName() + "       " + listingAndNesting.getClazz() + "        "
                + listingAndNesting.getObject().getTitle() + "        " + listingAndNesting.getObject().getGrade());

        for (String str : listingAndNesting.getObject().getSubjectList()) {
            logger.info(str);
        }
    }

}
