package com.xjdl.study.dateFormat;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Year;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 日期时间和字符串互转
 */
@Slf4j
public class DateFormat {
	/**
	 * SimpleDateFormat.parse()
	 * 字符串变日期时间
	 */
	@Test
	public void parseToDate() {
		try {
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
			format1.setTimeZone(TimeZone.getTimeZone("GMT-10:00"));

			SimpleDateFormat format2 = new SimpleDateFormat("yyyy/MM/dd");

			String str1 = "2023-12-09 23:15:12:333";
			String str2 = "2023/12/09";

			log.info("{}", format1.parse(str1));
			log.info("{}", format2.parse(str2));
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * SimpleDateFormat.format()
	 * 日期时间变字符串
	 */
	@Test
	public void formatToString() {
		Date date = new Date();
		log.info("{}", date);

		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
		SimpleDateFormat format2 = new SimpleDateFormat("yyyy/MM/dd");
		SimpleDateFormat format3 = new SimpleDateFormat("HH:mm:ss");

		log.info(format1.format(date));
		log.info(format2.format(date));
		log.info(format3.format(date));
	}

	/**
	 * Calendar获取的时间
	 */
	@Test
	public void getDateByCalendar() {
		SimpleDateFormat dataDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, 2); // 正数代表之后的时间
		log.info(dataDateFormat.format(calendar.getTime()));
	}

	/**
	 * DateTimeFormatter来实现互转
	 */
	@Test
	public void ofPattern() {
		// 解析日期
		String str1 = "2023年 12月 23日";
		DateTimeFormatter dtfToDate = DateTimeFormatter.ofPattern("yyyy年 MM月 dd日");
		log.info("{}", LocalDate.parse(str1, dtfToDate));

		// 日期转字符串
		LocalDateTime now = LocalDateTime.now();
		log.info("当前时间：{}", now);
		log.info("格式化后：{}", now.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
		log.info("格式化后：{}", now.format(DateTimeFormatter.ISO_LOCAL_DATE));
		log.info("格式化后：{}", now.format(DateTimeFormatter.ISO_LOCAL_TIME));
		log.info("格式化后：{}", now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")));

		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy年 MM月 dd日 hh:mm a");
		log.info(now.format(dateTimeFormatter));
	}

	/**
	 * Java8
	 * LocalDate获取时间
	 */
	@Test
	public void getDateByLocalDate() {
		// 当前精确时间
		LocalDateTime now = LocalDateTime.now();
		log.info("当前精确时间：{}", now);
		log.info("当前精确时间：{}-{}-{} {}:{}:{}", now.getYear(), now.getMonthValue(), now.getDayOfMonth(), now.getHour(), now.getMinute(), now.getSecond());

		// 获取当前日期
		LocalDate localDate = LocalDate.now();
		log.info("当前日期：{}", localDate);
		log.info("当前日期：{}-{}-{}", localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth());
		log.info("获取之前时间 = {}", localDate.minusDays(1));
		log.info("获取之后时间 = {}", localDate.minusDays(-2));

		// 获取当天时间
		LocalTime localTime = LocalTime.now();
		log.info("当天时间：{}", localTime);
		log.info("当天时间：{}:{}:{}", localTime.getHour(), localTime.getMinute(), localTime.getSecond());

		// 有时区的当前精确时间
		ZonedDateTime nowZone = LocalDateTime.now().atZone(ZoneId.systemDefault());
		log.info("当前精确时间（有时区）：{}", nowZone);
		log.info("当前精确时间 ：{}-{}-{} {}:{}:{}", nowZone.getYear(), nowZone.getMonthValue(), nowZone.getDayOfMonth(), nowZone.getHour(), nowZone.getMinute(), nowZone.getSecond());
	}

	/**
	 * Java8 LocalDate设置时间
	 */
	@Test
	public void setDateTimeByLocalDate() {
		LocalDateTime ofTime = LocalDateTime.of(2019, 10, 1, 8, 8, 8);
		log.info("当前精确时间：{}", ofTime);

		LocalDate localDate = LocalDate.of(2019, 10, 1);
		log.info("当前日期：{}", localDate);

		LocalTime localTime = LocalTime.of(12, 1, 1);
		log.info("当天时间：{}", localTime);
	}

	/**
	 * 日期转换
	 */
	@Test
	public void convertTimeTest() {
		LocalDateTime parseTime = LocalDateTime.parse("2019-10-01T22:22:22.222");
		log.info("字符串时间转换：{}", parseTime);

		LocalDate formatted = LocalDate.parse("20190101", DateTimeFormatter.BASIC_ISO_DATE);
		log.info("字符串时间转换-指定格式：{}", formatted);

		// Date 转换成 LocalDateTime
		Date date = new Date();
		ZoneId zoneId = ZoneId.systemDefault();
		log.info("Date 转换成 LocalDateTime：{}", LocalDateTime.ofInstant(date.toInstant(), zoneId));

		// LocalDateTime 转换成 Date
		LocalDateTime localDateTime = LocalDateTime.now();
		Date toDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
		log.info("LocalDateTime 转换成 Date：{}", toDate);

		// 当前时间转时间戳
		long epochMilli = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
		log.info("当前时间转时间戳：{}", epochMilli);
		// 时间戳转换成时间
		LocalDateTime epochMilliTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(epochMilli), ZoneId.systemDefault());
		log.info("时间戳转换成时间：{}", epochMilliTime);
	}

	/**
	 * 时间比较
	 */
	@Test
	public void diffTest() {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime yesterday = now.minusDays(1);
		log.info("{} 在 {} 之后吗? {}", now, yesterday, now.isAfter(yesterday));
		log.info("{} 在 {} 之后吗? {}", now, yesterday, now.isBefore(yesterday));

		// 时间差
		long day = yesterday.until(now, ChronoUnit.DAYS);
		long month = yesterday.until(now, ChronoUnit.MONTHS);
		long hours = yesterday.until(now, ChronoUnit.HOURS);
		long minutes = yesterday.until(now, ChronoUnit.MINUTES);
		log.info("相差月份{}", month);
		log.info("相差天数{}", day);
		log.info("相差小时{}", hours);
		log.info("相差分钟{}", minutes);

		LocalDate jdk14 = LocalDate.of(2020, 3, 17);
		LocalDate nowDate = LocalDate.now();
		log.info("距离JDK 14 已经：{}", nowDate.until(jdk14, ChronoUnit.DAYS) + "天");
	}

	/**
	 * 日期加减
	 */
	@Test
	public void calcTest() {
		LocalDateTime now = LocalDateTime.now();
		log.info("当前时间：{}", now);
		LocalDateTime plusTime = now.plusMonths(1).plusDays(1).plusHours(1).plusMinutes(1).plusSeconds(1);
		log.info("增加1月1天1小时1分钟1秒时间后：{}", plusTime);
		LocalDateTime minusTime = now.minusMonths(2);
		log.info("减少2个月时间后：{}", minusTime);
	}

	/**
	 * 时间方法
	 */
	@Test
	public void timeFunctionTest() {
		LocalDateTime now = LocalDateTime.now();
		log.info("当前时间：{}", now);
		// 第一天
		LocalDateTime firstDay = now.withDayOfMonth(1);
		log.info("本月第一天：{}", firstDay);
		// 当天最后一秒
		LocalDateTime lastSecondOfDay = now.withHour(23).withMinute(59).withSecond(59);
		log.info("当天最后一秒：{}", lastSecondOfDay);
		// 最后一天
		LocalDateTime lastDay = now.with(TemporalAdjusters.lastDayOfMonth());
		log.info("本月最后一天:{}", lastDay);
		// 是否闰年
		log.info("今年是否闰年：{}", Year.isLeap(now.getYear()));
	}
}
