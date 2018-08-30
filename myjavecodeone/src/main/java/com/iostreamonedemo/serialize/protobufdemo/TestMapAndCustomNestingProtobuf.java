package com.iostreamonedemo.serialize.protobufdemo;

import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class TestMapAndCustomNestingProtobuf {


    private final static Logger logger = LoggerFactory.getLogger(TestMapAndCustomNestingProtobuf.class);

    private byte[] toMapsSerialize() {
        MapAndCustomNesting.Maps.Builder builder = MapAndCustomNesting.Maps.newBuilder();
        builder.putMaps("第一个键", MapAndCustomNesting.Person.newBuilder().setAge(1).setName("one").build());
        builder.putMaps("第二个键", MapAndCustomNesting.Person.newBuilder().setAge(2).setName("two").build());
        builder.putMaps("第三个键", MapAndCustomNesting.Person.newBuilder().setAge(3).setName("three").build());
        return builder.build().toByteArray();
    }

    private byte[] toListsSerialize() {
        MapAndCustomNesting.Lists.Builder builder = MapAndCustomNesting.Lists.newBuilder();
        builder.addSubject(MapAndCustomNesting.Person.newBuilder().setName("one").setAge(1).build())
                .addSubject(MapAndCustomNesting.Person.newBuilder().setName("two").setAge(2).build())
                .addSubject(MapAndCustomNesting.Person.newBuilder().setName("three").setAge(3).build());
        return builder.build().toByteArray();
    }

    private void fromSerialize(byte[] lists, byte[] maps) throws InvalidProtocolBufferException {
        //proto文件中的List
        MapAndCustomNesting.Lists list = MapAndCustomNesting.Lists.parseFrom(lists);
        List<MapAndCustomNesting.Person> personList = list.getSubjectList();
        for (MapAndCustomNesting.Person person : personList) {
            logger.info(person.getName() + "      " +   person.getAge());
        }

        MapAndCustomNesting.Maps map = MapAndCustomNesting.Maps.parseFrom(maps);
        Map<String, MapAndCustomNesting.Person> personMap = map.getMapsMap();
        for (Map.Entry<String, MapAndCustomNesting.Person> person : personMap.entrySet()) {
            logger.info(person.getKey() + "       " + person.getValue());
        }
    }

    public static void main(String[] args) throws InvalidProtocolBufferException {
        TestMapAndCustomNestingProtobuf testMapAndCustomNestingProtobuf = new TestMapAndCustomNestingProtobuf();
        byte[] lists = testMapAndCustomNestingProtobuf.toListsSerialize();
        byte[] maps = testMapAndCustomNestingProtobuf.toMapsSerialize();

        testMapAndCustomNestingProtobuf.fromSerialize(lists, maps);
    }
}
