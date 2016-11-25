package com.zihai.test.controller;

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
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/quartz")
public class QuartzTestController {
	private static Logger log = Logger.getLogger("SimpleJob");
	
	/*@Autowired
	private SchedulerFactoryBean bean;*/
	private SchedulerFactoryBean bean = new SchedulerFactoryBean();
	@RequestMapping("/show")
	public String showManager(){
		return "test/quartzManager";
	}
	@RequestMapping("/add")
	public void add(String jobname,String group,String className,String triggerName){
		log.info("收到数据"+jobname+group+className+triggerName);
		try {
			Scheduler scheduler = bean.getScheduler();
			Class<? extends Job> jobClass = (Class<? extends Job>) Class.forName(className);
			JobDetail job = newJob(jobClass).withIdentity(jobname, group).storeDurably(true).build();
			CronTrigger trigger = newTrigger().withIdentity(triggerName, group).withSchedule(cronSchedule("2 0/2 * * * ?")).build();
			scheduler.scheduleJob(job,trigger);
			  // 锟斤拷锟斤拷  
			if (!scheduler.isShutdown()) {  
				scheduler.start();  
			}
			
		} catch (ClassNotFoundException e) {
			log.error("没找到任务类");
			e.printStackTrace();
		} catch (SchedulerException e) {
			log.error("任务添加失败");
		} 
		log.info("任务添加成功");
	}
	@RequestMapping("/delete")
	public void delete(String jobname,String group,String triggerName){
		log.info("收到数据"+jobname+group+triggerName);
		try {
			Scheduler scheduler = bean.getScheduler();
			scheduler.pauseTrigger(TriggerKey.triggerKey(triggerName, group));// 停止锟斤拷锟斤拷锟斤拷  
			scheduler.unscheduleJob(TriggerKey.triggerKey(triggerName, group));// 锟狡筹拷锟斤拷锟斤拷锟斤拷  
			scheduler.deleteJob(JobKey.jobKey(jobname, group));// 删锟斤拷锟斤拷锟斤拷 
			  // 锟斤拷锟斤拷  
			if (!scheduler.isShutdown()) {  
				scheduler.start();  
			}
		} catch (SchedulerException e) {
			log.error("失败");
		}
		log.info("成功");
	}
}