package com.xjdl.study.springboot.banner;

import com.xjdl.study.util.MyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ResourceBanner;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertyResolver;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 优先级低于 banner.txt
 * <p>
 * 允许使用 banner.txt 适用的所有占位符
 */
@Slf4j
public class MyBanner extends ResourceBanner implements PrintStyle {

	private static final String[] SPRING_BOOT_BANNER_0 = {
			"",
			"  .   ____          _            __ _ _",
			" /\\\\ / ___'_ __ _ _(_)_ __  __ _ \\ \\ \\ \\",
			"( ( )\\___ | '_ | '_| | '_ \\/ _` | \\ \\ \\ \\",
			" \\\\/  ___)| |_)| | | | | || (_| |  ) ) ) )",
			"  '  |____| .__|_| |_|_| |_\\__, | / / / /",
			" =========|_|==============|___/=/_/_/_/"
	};
	private static final String SPRING_BOOT_BANNER_1 =
			"  .   ____          _            __ _ _\n" +
					" /\\\\ / ___'_ __ _ _(_)_ __  __ _ \\ \\ \\ \\\n" +
					"( ( )\\___ | '_ | '_| | '_ \\/ _` | \\ \\ \\ \\\n" +
					" \\\\/  ___)| |_)| | | | | || (_| |  ) ) ) )\n" +
					"  '  |____| .__|_| |_|_| |_\\__, | / / / /\n" +
					" =========|_|==============|___/=/_/_/_/";
	static final String SPRING_BOOT_EXTRA = "==> Spring Boot ";
	static final int STRAP_LINE_SIZE = 42;
	/**
	 * 自定义banner默认文件名
	 */
	public static final String MY_BANNER_LOCATION = "xjdl_banner.txt";
	/**
	 * 设置自定义banner的位置配置
	 */
	public static final String MY_BANNER_LOCATION_PROPERTY = "xjdl.banner.location";
	Resource resource;

	public MyBanner(Resource resource) {
		super(resource);
		this.resource = resource;
	}

	/**
	 * banner高级定制
	 */
	@Override
	public void printBanner(Environment environment, Class<?> sourceClass, PrintStream printStream) {
		try {
//			printBanner(printStream);
			printBannerPro(environment, sourceClass, printStream);
		} catch (Exception e) {
			printBannerDefault(environment, sourceClass, printStream);
		}
	}

	/**
	 * 默认banner样式
	 * <p>
	 * default 修饰符反射实例化时，构造器也需设置允许访问·
	 */
	private void printBannerDefault(Environment environment, Class<?> sourceClass, PrintStream printStream) {
		try {
			Class<?> springBootBanner = Class.forName("org.springframework.boot.SpringBootBanner");
//			ReflectionUtils.doWithMethods(springBootBanner, method -> {
//				try {
//					method.invoke(
//							ReflectionUtils.accessibleConstructor(springBootBanner).newInstance(), environment, sourceClass, printStream
//					);
//				} catch (InvocationTargetException | InstantiationException | NoSuchMethodException e) {
//					throw new RuntimeException(e);
//				}
//			}, method -> {
//				if (method.getName().equals("printBanner")) {
//					method.setAccessible(true);
//					return true;
//				}
//				return false;
//			});

			Constructor<?> constructor = springBootBanner.getDeclaredConstructor();
			constructor.setAccessible(true);

			Method printBanner = springBootBanner.getDeclaredMethod("printBanner", Environment.class, Class.class, PrintStream.class);
			printBanner.setAccessible(true);

			printBanner.invoke(constructor.newInstance(), environment, sourceClass, printStream);
		} catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException |
				 InstantiationException ignored) {
//		} catch (ClassNotFoundException ignored) {
			// 忽略异常信息
		}
	}

	/**
	 * 高级定制化banner
	 */
	private void printBannerPro(Environment environment, Class<?> sourceClass, PrintStream printStream) throws IOException {
		String banner = StreamUtils.copyToString(resource.getInputStream(),
				environment.getProperty("spring.banner.charset", Charset.class, StandardCharsets.UTF_8));
		List<PropertyResolver> propertyResolvers = getPropertyResolvers(environment, sourceClass);
		for (PropertyResolver resolver : propertyResolvers) {
			banner = resolver.resolvePlaceholders(banner);
		}
		printBannerMain(printStream, banner);
		printBannerExtra(printStream);
	}

	/**
	 * 输出banner的简单实现
	 */
	private void printBanner(PrintStream printStream) {
		List<String> content;
		try {
			Path bannerPath = Paths.get(MyUtils.getResourcePath(MY_BANNER_LOCATION));
			content = Files.readAllLines(bannerPath);
		} catch (IOException e) {
			content = Arrays.stream(SPRING_BOOT_BANNER_0).collect(Collectors.toList());
//			content = Collections.singletonList(SPRING_BOOT_BANNER_1);
		}
		for (String line : content) {
			printBannerMain(printStream, line);
		}
		printBannerExtra(printStream);
	}
}
