/*package com.zihai.controller.test;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/quartz")
public class QuartzTestController {
	private static Logger log = Logger.getLogger("SimpleJob");
	
	@Autowired
	private SchedulerFactoryBean bean;
	
	@RequestMapping("/show")
	public String showManager(){
		return "test/quartzManager";
	}
	@RequestMapping("/add")
	public void add(String jobname,String group,String className,String triggerName){
		log.info("��ȡ���ݣ�������������"+jobname+group+className+triggerName);
		try {
			Scheduler scheduler = bean.getScheduler();
			Class<? extends Job> jobClass = (Class<? extends Job>) Class.forName(className);
			JobDetail job = newJob(jobClass).withIdentity(jobname, group).storeDurably(true).build();
			CronTrigger trigger = newTrigger().withIdentity(triggerName, group).withSchedule(cronSchedule("2 0/2 * * * ?")).build();
			scheduler.scheduleJob(job,trigger);
			  // ����  
			if (!scheduler.isShutdown()) {  
				scheduler.start();  
			}
			
		} catch (ClassNotFoundException e) {
			log.error("û���ҵ�������");
			e.printStackTrace();
		} catch (SchedulerException e) {
			log.error("���ʧ��");
		} 
		log.info("��ӳɹ�");
	}
	@RequestMapping("/delete")
	public void delete(String jobname,String group,String triggerName){
		log.info("��ȡ���ݣ�������������"+jobname+group+triggerName);
		try {
			Scheduler scheduler = bean.getScheduler();
			scheduler.pauseTrigger(TriggerKey.triggerKey(triggerName, group));// ֹͣ������  
			scheduler.unscheduleJob(TriggerKey.triggerKey(triggerName, group));// �Ƴ�������  
			scheduler.deleteJob(JobKey.jobKey(jobname, group));// ɾ������ 
			  // ����  
			if (!scheduler.isShutdown()) {  
				scheduler.start();  
			}
		} catch (SchedulerException e) {
			log.error("��ͣʧ��");
		}
		log.info("ɾ���ɹ�");
	}
}
*/