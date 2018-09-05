package com.webrelativeonedemo.biosocketdemo.finalsocketserverandclient;

import java.util.*;

/**
 * 使用一个Map来保存聊天室所有用户和对应Socket的输出流之间的映射关系。
 * 这里相当于自定义了一个数据结构~
 */
public class CrazyitMap<K, V> {

    //创建一个线程安全的HashMap
    public Map<K, V> map = Collections.synchronizedMap(new HashMap<>());

    //根据value来删除指定项的方法，需要线程安全
    public synchronized void removeByValue(Object value) {
        for (Object key : map.keySet()) {
            if (map.get(key) == value) {
                map.remove(key);
                break;
            }
        }
    }

    //获取所有value组成的set集合(set集合可以去重复)
    public synchronized Set<V> valueSet() {
        Set<V> result = new HashSet<V>();

        //将map中的所有value添加到result集合中
        map.forEach((key, value) -> result.add(value));
        return result;
    }

    //根据value查找key
    public synchronized K getKeyByValue(V val) {
        //遍历所有key组成的集合
        for (K key : map.keySet()) {
            //如果指定key对应的value与被搜索的value相同，则返回对应的key
            if (map.get(key) == val || map.get(key).equals(val)) {
                return key;
            }
        }
        return null;
    }

    //实现put()方法，该方法不允许value重复
    public synchronized V put(K key, V value) {
        //遍历所有value组成的集合
        for (V val : valueSet()) {
            //如果某个value与试图放入集合的value相同
            //则抛出一个RuntimeException异常
            if (val.equals(value) && val.hashCode() == value.hashCode()) {
                throw new RuntimeException("MyMap实例中不允许有重复value！");
            }
        }
        return map.put(key, value);
    }
}
