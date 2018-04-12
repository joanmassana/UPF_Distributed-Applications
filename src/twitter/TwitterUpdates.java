package twitter;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class TwitterUpdates {

	public static void main(String[] args) throws SchedulerException {
		
		//Job for statistics
		JobDetail jobTwitterStats = JobBuilder.newJob(JobTwitterStats.class).withIdentity("twitterJobs").build();
		
		Trigger triggerTwitterStats = TriggerBuilder.newTrigger()
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(60).repeatForever()).build();
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler sched = sf.getScheduler();
		sched.start();
		sched.scheduleJob(jobTwitterStats, triggerTwitterStats);
	}

}
