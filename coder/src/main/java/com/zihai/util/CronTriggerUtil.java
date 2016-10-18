package com.zihai.util;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

/**
 * ������cron���ʽ�����������
 * @author liuyizhi
 * */
public class CronTriggerUtil {
	Logger log = Logger.getLogger(CronTriggerUtil.class);
	
	/**
	 * @param My_Job
	 * @author liuyizhi
	 * @throws SchedulerException 
	 * @throws ClassNotFoundException 
	 */
	@SuppressWarnings("unchecked")
	public void doJobs(My_Job[] My_Jobs) throws SchedulerException, ClassNotFoundException{
		SchedulerFactory sf = new StdSchedulerFactory();
	    Scheduler sched = sf.getScheduler();
		JobDetail job;
		CronTrigger trigger;
		for(My_Job my_job :My_Jobs){
			Class<? extends Job> jobClass = (Class<? extends Job>) Class.forName(my_job.getClassName());
			 job = newJob(jobClass).withIdentity(my_job.getJobName(), my_job.getGroup()).build();
			 trigger = newTrigger().withIdentity(my_job.getTrigger(), my_job.getGroup()).withSchedule(cronSchedule(my_job.getCronExpression()))
				        .build();
			 Date ft = sched.scheduleJob(job, trigger);
			 log.info(job.getKey() + " has been scheduled to run at: " + ft + " and repeat based on expression: "
			             + trigger.getCronExpression());
		}
		sched.start();
	}
    

   
}
class My_Job{
	private String jobName;
	private String cronExpression;
	private String group;
	private String className;
	private String trigger;
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getCronExpression() {
		return cronExpression;
	}
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getTrigger() {
		return trigger;
	}
	public void setTrigger(String trigger) {
		this.trigger = trigger;
	}
	
}
class Task implements Job{

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		//do something
		
	}
	
}