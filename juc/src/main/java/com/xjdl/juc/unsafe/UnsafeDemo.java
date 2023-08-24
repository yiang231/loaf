package com.xjdl.juc.unsafe;

import org.junit.jupiter.api.Test;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

// 获取unsafe对象,使用其修改其他类的属性值
public class UnsafeDemo {
	@Test
	void test() throws NoSuchFieldException, IllegalAccessException {
		// @CallerSensitive有这个注解在,直接拿不到对象
//		Unsafe unsafe = Unsafe.getUnsafe();
		Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
		theUnsafe.setAccessible(true);
		Unsafe unsafe = (Unsafe) theUnsafe.get(null);

		Person person = new Person();
		System.out.println("修改前的\t" + person);
		// 获取对象中属性的偏移量
		Field usernameField = Person.class.getDeclaredField("username");
		long fieldOffset = unsafe.objectFieldOffset(usernameField);
		System.out.println("修改前username的属性偏移量为\t" + fieldOffset);

		unsafe.compareAndSwapObject(person, fieldOffset, "zhangsan", "lisi"); // 比较之后相同,会修改

		Field usernameField1 = Person.class.getDeclaredField("username");
		long fieldOffset1 = unsafe.objectFieldOffset(usernameField1);
		System.out.println("修改第一次username的属性偏移量为\t" + fieldOffset1);

		unsafe.compareAndSwapObject(person, fieldOffset, "王五", "玉米");
		unsafe.compareAndSwapObject(person, fieldOffset, "lisi", "狗子"); // 比较之后不同,不会修改

		Field usernameField2 = Person.class.getDeclaredField("username");
		long fieldOffset2 = unsafe.objectFieldOffset(usernameField2);
		System.out.println("修改第二次username的属性偏移量为\t" + fieldOffset2);

		System.out.println("修改后的\t" + person);

		System.out.println("unsafe对象\t" + unsafe);
	}
}
