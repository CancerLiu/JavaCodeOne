package com.lambdaonedemo.optionalandstreamdemo;

import com.cpsdb.base.mapper.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OptionalAndStreamDemo {

    private final static Logger logger = LoggerFactory.getLogger(OptionalAndStreamDemo.class);

    private void testOne() {
        User user1 = new User().setName("liuchao").setBirthday(new Date()).setHeight(178);
        User user = null;

        Optional<User> userOptional = Optional.ofNullable(user1);
//        logger.info(userOptional.isPresent()+"");
//        logger.info(userOptional.get().getName());
        logger.info(JsonMapper.buildNonNullMapper().toJson(userOptional.map(s -> s.setName("xx")).get()));
        logger.info(JsonMapper.buildNonNullMapper().toJson(userOptional.flatMap(s -> Optional.ofNullable(s.setName("xx"))).get()));
    }

    private void testTwo() {
        List<Integer> lists = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            lists.add(i);
        }
        lists.add(2);
        logger.info(JsonMapper.buildNonNullMapper().toJson(lists));
        Stream<Integer> stream = lists.stream();
        List<Integer> listsStream = stream.distinct().collect(Collectors.toList());
        listsStream.stream().forEach(System.out::println);
        logger.info(JsonMapper.buildNonNullMapper().toJson(listsStream));
    }

    private void testThree() {
        List<Integer> lists = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            lists.add(i);
        }
        lists.stream().sorted((p1, p2) -> p2.compareTo(p1)).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        OptionalAndStreamDemo optionalAndStreamDemo = new OptionalAndStreamDemo();
//        optionalAndStreamDemo.testOne();
        optionalAndStreamDemo.testTwo();
    }

}
