package com.xjdl.study.springboot.banner;

import org.springframework.boot.ResourceBanner;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.boot.ansi.AnsiStyle;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertyResolver;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

/**
 * 优先级低于 banner.txt
 * <p>
 * 允许使用 banner.txt 适用的所有占位符
 */
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
	private static final String DEFAULT_SPRING_BOOT_EXTRA = " :: Spring Boot :: ";
	static final int STRAP_LINE_SIZE = 42;
	public static final String MY_BANNER_LOCATION = "mybanner.txt";
	public static final String MY_BANNER_LOCATION_PROPERTY = "spring.banner.location";
	Resource resource;

	public MyBanner(Resource resource) {
		super(resource);
		this.resource = resource;
	}


	@Override
	public void printBanner(Environment environment, Class<?> sourceClass, PrintStream printStream) {
		try {
			String banner = StreamUtils.copyToString(resource.getInputStream(),
					environment.getProperty("spring.banner.charset", Charset.class, StandardCharsets.UTF_8));
			List<PropertyResolver> propertyResolvers = getPropertyResolvers(environment, sourceClass);
			for (PropertyResolver resolver : propertyResolvers) {
				banner = resolver.resolvePlaceholders(banner);
			}
			printBannerMain(printStream, banner);
			printBannerExtra(printStream);
		} catch (IOException e) {
			PrintStyle printStyle = new PrintStyle() {
				@Override
				public void printBannerMain(PrintStream printStream, String[] banner) {
					for (String line : banner) {
						printStream.println(line);
					}
				}

				@Override
				public void printBannerExtra(PrintStream printStream) {
					String version = SpringBootVersion.getVersion();
					version = (version != null) ? " (v" + version + ")" : "";
					StringBuilder padding = new StringBuilder();
					while (padding.length() < STRAP_LINE_SIZE - (version.length() + DEFAULT_SPRING_BOOT_EXTRA.length())) {
						padding.append(" ");
					}

					printStream.println(AnsiOutput.toString(AnsiColor.GREEN, DEFAULT_SPRING_BOOT_EXTRA, AnsiColor.DEFAULT, padding.toString(),
							AnsiStyle.FAINT, version));
					printStream.println();
				}
			};
			printStyle.printBannerMain(printStream, SPRING_BOOT_BANNER_0);
			printStyle.printBannerExtra(printStream);
		}
	}


	private void printBanner(PrintStream printStream) {
		List<String> content;
		try {
			Path bannerPath = Paths.get(this.getClass().getClassLoader().getResource(MY_BANNER_LOCATION).toURI());
			content = Files.readAllLines(bannerPath);
		} catch (URISyntaxException | IOException e) {
//			content = Arrays.stream(SPRING_BOOT_BANNER_0).collect(Collectors.toList());
			content = Collections.singletonList(SPRING_BOOT_BANNER_1);
		}
		for (String line : content) {
			printBannerMain(printStream, line);
		}
		printBannerExtra(printStream);
	}
}
