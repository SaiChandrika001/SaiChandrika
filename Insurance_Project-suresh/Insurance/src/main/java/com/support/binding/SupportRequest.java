package com.support.binding;

import lombok.Data;


@Data
////@NoArgsConstructor
//@AllArgsConstructor
public class SupportRequest {
	private Long userId;
	private String issueType;
	private String description;
	private String priority;
	 private String title;
	  
}
