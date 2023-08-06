package com.xjdl.study.designPatterns.creationalPatterns.prototype;

import com.xjdl.study.designPatterns.creationalPatterns.prototype.item.Circle;
import com.xjdl.study.designPatterns.creationalPatterns.prototype.item.Rectangle;
import com.xjdl.study.designPatterns.creationalPatterns.prototype.item.Shape;
import com.xjdl.study.designPatterns.creationalPatterns.prototype.item.Square;

import java.util.Hashtable;

public class ShapeCache {
	private static Hashtable<String, Shape> shapeMap = new Hashtable<>();

	/**
	 * 使用时返回对象的克隆
	 */
	public static Shape getShape(String shapeId) {
		Shape cachedShape = shapeMap.get(shapeId);
		return (Shape) cachedShape.clone();
	}

	/**
	 * 初始化加载对象
	 */
	public static void loadCache() {
		Circle circle = new Circle();
		circle.setId("c");
		shapeMap.put(circle.getId(), circle);

		Square square = new Square();
		square.setId("s");
		shapeMap.put(square.getId(), square);

		Rectangle rectangle = new Rectangle();
		rectangle.setId("r");
		shapeMap.put(rectangle.getId(), rectangle);
	}
}
