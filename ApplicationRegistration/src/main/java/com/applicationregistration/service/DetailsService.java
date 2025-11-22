package com.applicationregistration.service;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import com.applicationregistration.request.SearchRequest;
import com.applicationregistration.request.SearchResponse;


public interface DetailsService {
	
	public List<String> getUniquePlanNames();
	public List<String> getUniquePlanStatus();
    public List<SearchResponse> search(SearchRequest request);
    public void generateExcel(HttpServletResponse response);
    public void generatePdf(HttpServletResponse response);
}
