package com.report.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.report.request.SearchRequest;
import com.report.response.SearchResponse;
import com.report.service.EligibilityService;

@RestController
public class ReportController {
	
	  @Autowired
       private EligibilityService eservice;
	  
	  @GetMapping("/plans")
	  public ResponseEntity<List<String>> getAllPlans(){
		 List<String> distinctPlans = eservice.getDistinctPlans();
		 return new ResponseEntity<List<String>>(distinctPlans, HttpStatus.OK);
	  }
	  
	  @GetMapping("/status")
	  public ResponseEntity<List<String>> getAllStatuses(){
		 List<String> allStatus = eservice.getAllStatus(); 
		  return new ResponseEntity<List<String>>(allStatus, HttpStatus.OK);
	  }
	  
	  @PostMapping("/search")
	  public ResponseEntity<List<SearchResponse>> search(@RequestBody SearchRequest request){
		  List<SearchResponse> search = eservice.search(request);
		  return new ResponseEntity<List<SearchResponse>>(search, HttpStatus.OK);
	  }
	  
	  @GetMapping("/excel")
	  public void execlReport(HttpServletResponse response) throws Exception{
		  response.setContentType("application/octet-stream");
		  
		  String headerKey = "Content-Disposition";
		  String headerValue = "attachment; filename=report.xls";
		  
		  response.setHeader(headerKey, headerValue);
		  
		  eservice.generateExecl(response);
	  }
	  
	  @GetMapping("/pdf")
	  public void pdfReport(HttpServletResponse response) throws Exception{
		  response.setContentType("application/pdf");
		  
		  String headerKey = "Content-Disposition";
		  String headerValue = "attachment; filename=report.pdf";
		  
		  response.setHeader(headerKey, headerValue);
		  
		  eservice.generatePdf(response);
	  }

}
