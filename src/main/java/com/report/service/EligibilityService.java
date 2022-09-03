package com.report.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.report.request.SearchRequest;
import com.report.response.SearchResponse;



public interface EligibilityService {
	
	public List<String> getDistinctPlans();
	
	public List<String> getAllStatus();
	
	public List<SearchResponse> search(SearchRequest sr);
	
	public void generateExecl(HttpServletResponse resp) throws Exception;
	
	public void generatePdf(HttpServletResponse  resp) throws Exception;

}
