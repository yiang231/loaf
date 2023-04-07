package com.dl.work;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

//        BigDecimal ypjgz = new BigDecimal(1000);
//        String str = "日常所需-0.402";
//        String zcqk = str.substring(0, str.lastIndexOf('-'));
//        String zcje = str.substring(str.lastIndexOf('-') + 1, str.indexOf('万'));
//        System.out.println("zcje = " + zcje);
//        System.out.println("zcqk = " + zcqk);


//        String bl = str.substring(str.lastIndexOf('-') + 1, str.length());
//
//        BigDecimal divide = ypjgz.multiply(new BigDecimal(12)).multiply(new BigDecimal(bl)).divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_UP);
//        System.out.println(divide.toString());


//        BigDecimal zb1 = new BigDecimal("0.2");
//        BigDecimal zb2 = new BigDecimal("0.5");
//        BigDecimal zb3 = BigDecimal.ONE.subtract(zb1).subtract(zb2);
//        System.out.println("zb1 = " + zb1);
//        System.out.println("zb2 = " + zb2);
//        System.out.println("zb3 = " + zb3);
//        BigDecimal zb4 = new BigDecimal("0.3").divide(BigDecimal.ONE, 6, RoundingMode.HALF_UP);
//        System.out.println("zb4 = " + zb4);
//        System.out.println(zb3.compareTo(zb4) > 0.000001 ? "是" : "否");


        HashMap<String, String> map1 = new HashMap<String, String>() {{
            put("key", "ipkey");
            put("val", "ipval");
        }};
        HashMap<String, String> map2 = new HashMap<String, String>() {{
            put("key", "portkey");
            put("val", "portval");
        }};
        HashMap<String, String> map3 = new HashMap<String, String>() {{
            put("key", "namekey");
            put("val", "nameval");
        }};
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>() {{
            add(map1);
            add(map2);
            add(map3);
        }};
        HashMap<String, String> map = new HashMap<>();
        for (HashMap<String, String> item : list) {
            map.put(item.get("key"), item.get("val"));
        }
        System.out.println(map);
    }
}
