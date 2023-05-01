package com.xjdl.study.generic;

import java.io.Serializable;

/**
 * 在对象创建或者调用方法的时候才明确具体的类型
 * 增加组件的通用性
 * 操作集合的时候用的较多
 * 多个边界使用 & 连接
 * <E super HttpServlet> 接收 HttpServlet 或者 HttpServlet 父类型的对象
 * List<String> 不是 List<Object> 的子类，是 List<?> 的子类
 * 可以看成 ? 是集合，是任何类型
 * 可以看成 T 是集合里的元素
 * <p>
 * E Element
 * T Type
 * K Key
 * V Value
 * N Number
 * ? 不确定的Java类型
 */
public class GenericMethod<E, V extends Object & Serializable> { // 只能传 Object 或者 Object 的子类
	public E thingToPrint;
	public V thing2Print;

	public GenericMethod(E thingToPrint, V thing2Print) {
		this.thingToPrint = thingToPrint;
		this.thing2Print = thing2Print;
	}

	public GenericMethod() {
	}

	public <T extends E, V> T print(E thingToPrint, V thing2Print) {
		return (T) (thingToPrint + " | " + thing2Print);
	}

	public E printE(E thingToPrint) {
		return thingToPrint;
	}

	public V printV(V thing2Print) {
		return thing2Print;
	}
}
