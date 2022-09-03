package com.report.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.report.entity.EligibilityEntity;
import com.report.repository.EligiblityRepo;

@Component
public class AppRunner implements ApplicationRunner {
	
	@Autowired
	private EligiblityRepo erepo;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		 EligibilityEntity entity1 = new EligibilityEntity();
		 entity1.setEligId(1);
		 entity1.setName("John");
		 entity1.setEmail("john@gmail.com");
		 entity1.setPlanName("SNAP");
		 entity1.setMobNumber(9834841598l);
		 entity1.setGender('M');
		 entity1.setSsn(5564555241232L);
		 entity1.setPlanStatus("Approved");
		 erepo.save(entity1);
		 
		 EligibilityEntity entity2 = new EligibilityEntity();
		 entity2.setEligId(2);
		 entity2.setName("Smith");
		 entity2.setEmail("smith@gmail.com");
		 entity2.setPlanName("CCAP");
		 entity2.setMobNumber(9834841556l);
		 entity2.setGender('M');
		 entity2.setSsn(5564455241232L);
		 entity2.setPlanStatus("Denied");
		 erepo.save( entity2);
		 
		 EligibilityEntity entity3 = new EligibilityEntity();
		 entity3.setEligId(3);
		 entity3.setName("Roy");
		 entity3.setEmail("roy@gmail.com");
		 entity3.setPlanName("Medicate");
		 entity3.setMobNumber(9834841485l);
		 entity3.setGender('M');
		 entity3.setSsn(5563355241232L);
		 entity3.setPlanStatus("Close");
		 erepo.save( entity3);
		 
	}

}
