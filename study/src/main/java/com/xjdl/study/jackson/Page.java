package com.xjdl.study.jackson;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@Data
public class Page implements Serializable {

	@JsonProperty("content")
	private List<ContentDTO> content;
	@JsonProperty("empty")
	private boolean empty;
	@JsonProperty("first")
	private boolean first;
	@JsonProperty("last")
	private boolean last;
	@JsonProperty("number")
	private int number;
	@JsonProperty("number_of_elements")
	private int numberOfElements;
	@JsonProperty("pageable")
	private PageableDTO pageable;
	@JsonProperty("size")
	private int size;
	@JsonProperty("sort")
	private SortDTO sort;
	@JsonProperty("total_elements")
	private int totalElements;
	@JsonProperty("total_pages")
	private int totalPages;

	@NoArgsConstructor
	@Data
	public static class PageableDTO {
		@JsonProperty("offset")
		private int offset;
		@JsonProperty("page_number")
		private int pageNumber;
		@JsonProperty("page_size")
		private int pageSize;
		@JsonProperty("paged")
		private boolean paged;
		@JsonProperty("sort")
		private SortDTO sort;
		@JsonProperty("unpaged")
		private boolean unpaged;

		@NoArgsConstructor
		@Data
		public static class SortDTO {
			@JsonProperty("empty")
			private boolean empty;
			@JsonProperty("sorted")
			private boolean sorted;
			@JsonProperty("unsorted")
			private boolean unsorted;
		}
	}

	@NoArgsConstructor
	@Data
	public static class SortDTO {
		@JsonProperty("empty")
		private boolean empty;
		@JsonProperty("sorted")
		private boolean sorted;
		@JsonProperty("unsorted")
		private boolean unsorted;
	}

	@NoArgsConstructor
	@Data
	public static class ContentDTO {
		@JsonProperty("id")
		private Long id;
		@JsonProperty("name")
		private String name;
	}
}
