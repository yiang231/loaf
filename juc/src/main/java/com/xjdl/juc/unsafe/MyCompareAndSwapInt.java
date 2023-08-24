package com.xjdl.juc.unsafe;

import org.junit.jupiter.api.Test;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

// 自定义比较并交换的类,使用其修改其他类的属性值
public class MyCompareAndSwapInt {
	//	AtomicInteger num = new AtomicInteger(0); //无法修改属性
//	private Integer num = 0; //无法修改属性
	private int num = 0; // 不能被final修饰

	@Test
	void test() {
		MyCompareAndSwapInt myCompareAndSwapInt = new MyCompareAndSwapInt();
		System.out.println("方法执行前\t" + myCompareAndSwapInt.num);
		myCompareAndSwapInt.myCAS(0, 999);
		System.out.println("方法执行后\t" + myCompareAndSwapInt.num);
	}

	public void myCAS(Integer oldValue, Integer newValue) {
		try {
			Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
			theUnsafe.setAccessible(true);
			Unsafe unsafe = (Unsafe) theUnsafe.get(null);

			Field numField = MyCompareAndSwapInt.class.getDeclaredField("num");
			long fieldOffset = unsafe.objectFieldOffset(numField);

			unsafe.compareAndSwapInt(this, fieldOffset, oldValue, newValue);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
