package com.applicationregistration.request;

import lombok.Data;
import java.time.LocalDate;

@Data
public class SearchRequest {
	
	private String planName;
	private String planStatus;
	private LocalDate planStartData;
	private LocalDate planEndData;

}
