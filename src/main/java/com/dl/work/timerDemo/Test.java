package com.dl.work.timerDemo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {
    public void print() {
        Map<Object, Object> map = new HashMap() {{
            put("=", "=");
            put("1", "1");
            put("2", "2");
        }};
        System.out.println("无序存储 map = " + map);
        Map<Object, Object> map1 = new HashMap() {{
            put("=", "=");
        }};
        Map<Object, Object> map2 = new HashMap() {{
            put("1", "1");
        }};
        Map<Object, Object> map3 = new HashMap() {{
            put("2", "2");
        }};
        Map<Object, Object> map4 = new HashMap();
        map4.put("a", "a");
        map4.put("b", "b");
        map4.put("c", "c");
        map4.put("d", "d");
        System.out.println("有序存储 map4 = " + map4);
//        System.out.println(map.keySet()); // [1, 2, =]
//        System.out.println(map.values()); // [1, 2, =]
//        System.out.println(map.entrySet()); // [1=1, 2=2, ===]

//        List list = Arrays.asList(map1, map2, map3);
//        List list = Arrays.asList(map);
        List list = Arrays.asList(map4);
        for (Object item : list) {
            System.out.println(item); // {1=1, 2=2, ===}
        }
    }
}
