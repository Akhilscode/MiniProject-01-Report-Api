package com.report.service;

import java.awt.Color;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.report.entity.EligibilityEntity;
import com.report.repository.EligiblityRepo;
import com.report.request.SearchRequest;
import com.report.response.SearchResponse;

@Service
public class EligibilityServiceImpl implements EligibilityService {
   
	@Autowired
	private EligiblityRepo erepo;
	
	
	@Override
	public List<String> getDistinctPlans() {
		  List<String> distinctPlanNames = erepo.getDistinctPlanNames();
		return distinctPlanNames;
	}

	@Override
	public List<String> getAllStatus() {
		List<String> allStatus = erepo.getAllStatus();
		return allStatus;
	}

	@Override
	public List<SearchResponse> search(SearchRequest sr) {
		  
		 List<SearchResponse> resp = new ArrayList<>();
		 
		 //create entity class object
		 EligibilityEntity queryBuilder = new EligibilityEntity();
		 
		 String planName = sr.getPlanName();
		 if(planName != null && !planName.equals("")) {
			 queryBuilder.setPlanName(planName);
		 }
		 
		 String planStatus = sr.getPlanStatus();
		 if(planStatus != null && !planStatus.equals("")) {
			 queryBuilder.setPlanStatus(planStatus);
		 }
		 
		 LocalDate startDate = sr.getStartDate();
		 if(startDate != null) {
			 queryBuilder.setStartDate(startDate);
		 }
		 
		  LocalDate endDate = sr.getEndDate();
		  if(endDate != null) {
			  queryBuilder.setEndDate(endDate);
		  }
		  
		  Example<EligibilityEntity> example = Example.of(queryBuilder);
		  
		 List<EligibilityEntity> allEntities = erepo.findAll(example);
		 for(EligibilityEntity entity: allEntities) {
			 SearchResponse sresp = new SearchResponse();
		     BeanUtils.copyProperties(entity, sresp);
		     resp.add(sresp);
		 }
		return resp;
	}

	@Override
	public void generateExecl(HttpServletResponse resp) throws Exception {
		
		int row = 1;
		  
		List<EligibilityEntity> allEntities = erepo.findAll();
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();
		HSSFRow headerRow = sheet.createRow(0);
		
		//create header rows
		headerRow.createCell(0).setCellValue("Name");
		headerRow.createCell(1).setCellValue("Mobile");
		headerRow.createCell(2).setCellValue("Email");
		headerRow.createCell(3).setCellValue("SSN");
		headerRow.createCell(4).setCellValue("Gender");
		
		
		//creating data rows
		for(EligibilityEntity entity : allEntities) {
			
			HSSFRow dataRow = sheet.createRow(row);
			dataRow.createCell(0).setCellValue(entity.getName());
			dataRow.createCell(1).setCellValue(entity.getMobNumber());
			dataRow.createCell(2).setCellValue(entity.getEmail());
			dataRow.createCell(3).setCellValue(entity.getSsn());
			dataRow.createCell(4).setCellValue(String.valueOf(entity.getGender()));
			
			row++;
		}
		
		ServletOutputStream outputStream = resp.getOutputStream();
		workbook.write(outputStream);
		workbook.close();
		outputStream.close();
		
	}
	

	@Override
	public void generatePdf(HttpServletResponse  resp) throws Exception {
		Document document = new Document(PageSize.A4);
		 PdfWriter.getInstance(document, resp.getOutputStream());
         
	        document.open();
	        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
	        font.setSize(18);
	        font.setColor(Color.BLUE);
	         
	        Paragraph p = new Paragraph("Search Report", font);
	        p.setAlignment(Paragraph.ALIGN_CENTER);
	         
	        document.add(p);
	         
	        PdfPTable table = new PdfPTable(5);
	        table.setWidthPercentage(100f);
	        table.setWidths(new float[] {1.5f, 3.5f, 3.0f, 3.0f, 1.5f});
	        table.setSpacingBefore(10);
	        
	        PdfPCell cell = new PdfPCell();
	        cell.setBackgroundColor(Color.BLUE);
	        cell.setPadding(5);
	         
	        Font font1 = FontFactory.getFont(FontFactory.HELVETICA);
	        font1.setColor(Color.WHITE);
	         
	        cell.setPhrase(new Phrase("Name", font1));
	         
	        table.addCell(cell);
	        
	         
	        cell.setPhrase(new Phrase("Mobile", font1));
	        table.addCell(cell);
	         
	        cell.setPhrase(new Phrase("Email", font1));
	        table.addCell(cell);
	         
	        cell.setPhrase(new Phrase("SSN", font1));
	        table.addCell(cell);
	         
	        cell.setPhrase(new Phrase("Gender", font1));
	        table.addCell(cell);       
	        
	        List<EligibilityEntity> allEntities = erepo.findAll();
	        
	        for(EligibilityEntity entity : allEntities) {
	        	table.addCell(entity.getName());
	        	table.addCell(String.valueOf(entity.getMobNumber()));
	        	table.addCell(entity.getEmail());
	        	table.addCell(String.valueOf(entity.getSsn()));
	        	table.addCell(String.valueOf(entity.getGender()));
	        }
	        
	        
	        document.add(table);
	        document.close();
	}

}
