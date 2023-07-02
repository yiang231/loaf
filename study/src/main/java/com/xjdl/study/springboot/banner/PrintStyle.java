package com.xjdl.study.springboot.banner;

import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.boot.ansi.AnsiStyle;

import java.io.PrintStream;

import static com.xjdl.study.springboot.banner.MyBanner.SPRING_BOOT_EXTRA;
import static com.xjdl.study.springboot.banner.MyBanner.STRAP_LINE_SIZE;

public interface PrintStyle {
	default void printBannerMain(PrintStream printStream, String... banner) {
		for (String line : banner) {
			printStream.println(AnsiOutput.toString(AnsiColor.BRIGHT_GREEN, AnsiStyle.ITALIC, AnsiStyle.BOLD, line));
		}
	}

	default void printBannerExtra(PrintStream printStream) {
		String version = SpringBootVersion.getVersion();
		version = (version != null) ? "> (v" + version + ")" : "";
		StringBuilder padding = new StringBuilder();
		while (padding.length() < STRAP_LINE_SIZE - (version.length() + SPRING_BOOT_EXTRA.length())) {
			padding.append("=");
		}
		printStream.println(AnsiOutput.toString(AnsiColor.BRIGHT_MAGENTA, SPRING_BOOT_EXTRA, padding.toString(), version));
		printStream.println();
	}
}
