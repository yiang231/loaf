package com.dl.test.LocalCache;

import java.util.HashMap;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
//		try {
//			LocalCache localCache = LocalCache.getInstance();
//			localCache.put("第一次", "随便", 1, TimeUnit.SECONDS);
//
//			System.out.println(localCache.get("第一次").getValue());
//			long first = System.currentTimeMillis();
//			System.out.println(first);
//			Thread.sleep(1020);
//			System.out.println(localCache.get("第一次").getValue());
//			long second = System.currentTimeMillis();
//			System.out.println(second - first);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}

        Map<String, String> map = new HashMap<>();
        map.put("1", "1");
    }
}
