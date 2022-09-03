package com.report.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.report.entity.EligibilityEntity;

public interface EligiblityRepo extends JpaRepository<EligibilityEntity, Integer> {
	
	//in query table and variable names much match with entity class
	
	@Query("select distinct(planName) from EligibilityEntity")
	public List<String> getDistinctPlanNames();
	
	@Query("select distinct(planStatus) from EligibilityEntity")
	public List<String> getAllStatus();

}
