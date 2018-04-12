package server;

import java.util.List;

import javax.ws.rs.Consumes;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;


import data.Stations;
import data.User;
import telegram.JobTelegramNotifications;

@Path("/telegram")
public class TelegramServices {
	
	Stations stations = BFSN.getStationList();
	List<User> users = BFSN.getUsers();
	Client client = ClientBuilder.newClient();
	
	//Notifica a los usuarios de las estaciones con free slots
	@POST
	@Path("/notify")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void notifyTelegramUsers() throws SchedulerException {
		
		JobDetail jobTelegramNotificationsOD = JobBuilder.newJob(JobTelegramNotifications.class).withIdentity("telegramJobsOD").build();
		Trigger triggerTelegramNotificationsOD = TriggerBuilder.newTrigger()
				.withSchedule(SimpleScheduleBuilder.simpleSchedule()).build();
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler sched = sf.getScheduler();
		sched.start();
		sched.scheduleJob(jobTelegramNotificationsOD, triggerTelegramNotificationsOD);
								
	}
}
