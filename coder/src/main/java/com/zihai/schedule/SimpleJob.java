 
package com.zihai.schedule;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.springframework.scheduling.quartz.QuartzJobBean;


 
public class SimpleJob extends QuartzJobBean {

    private static Logger _log = Logger.getLogger("SimpleJob");

   
    public SimpleJob() {
    }
    
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		 JobKey jobKey = context.getJobDetail().getKey();
	        _log.info("SimpleJob says: " + jobKey + " executing at " + new Date());
		
	}

}
