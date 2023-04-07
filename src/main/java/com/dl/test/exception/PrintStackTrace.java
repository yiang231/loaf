package com.dl.test.exception;

/**
 * 模拟异常信息打印
 */
public class PrintStackTrace {
	public static String errorTrackSpace(Exception e) {
		StringBuilder stringBuffer = new StringBuilder();
		if (e != null) {
			stringBuffer.append("\r\n").append(e).append(": ").append(e.getMessage());
			for (StackTraceElement element : e.getStackTrace()) {
				stringBuffer.append("\r\n\t").append("at ").append(element);
			}
		}
		return stringBuffer.length() == 0 ? null : stringBuffer.toString();
	}
}
