package com.xjdl.study.rpc.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Lunardate {
	@JsonProperty("LunarYear")
	private String lunarYear;
	@JsonProperty("LunarDate")
	private String lunarDate;
}
