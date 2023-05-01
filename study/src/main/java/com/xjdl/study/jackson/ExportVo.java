package com.xjdl.study.jackson;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Date;
import java.util.List;

/**
 * 如果不添加任何有关属性顺序的配置，默认按照属性在实体类中的顺序格式化；如果在全局设置对象中属性转 JSON 中字段按照字典顺序排序，则格式化后的字段按照字典顺序排序。如果有特别需求使用注解@JsonPropertyOrder 自定义排序字段排序顺序。
 */
@JsonPropertyOrder({"resName", "checkStatus", "buildExportVo"})
public class ExportVo {
	private String resName;
	private Integer checkStatus;
	private List<String> buildExportVo;
}

/**
 * 当字段易属性变化时，比如重命名操作，更优雅的解决方式如下
 */
class ExportVoPro {
	@JsonProperty(index = 1)
	private String resName;
	@JsonProperty(index = 2)
	private Integer checkStatus;
	@JsonProperty(index = 3)
	private List<String> buildExportVoPro;
	/**
	 * 如果在全局配置时间日期格式为yyyy-MM-dd HH:mm:ss，特殊场合下需要yyyy-MM-dd格式的日期[DateTimeFormatter.ISO_DATE]，通过如下注解即可实现。添加在字段上的注解优先级比全局配置要高
	 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date birthDay;
}
