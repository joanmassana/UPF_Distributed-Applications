package server;

import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.quartz.DateBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.sun.net.httpserver.HttpServer;

import bicing.JobGetStations;
import data.Message;
import data.Stations;
import data.User;
import telegram.JobTelegramNotifications;
import twitter.JobTwitterStats;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class BFSN {
	
	//USERS Attribute
	static List<User> users = new ArrayList<User>();
	//SETTERS and GETTERS de USERS
	public static List<User> getUsers() {
		return users;
	}

	public static void setUsers(List<User> users) {
		BFSN.users = users;
	}
	
	//STATIONS Attribute
	private static Stations stationList;
	//GETTERS and SETTERS de STATIONS
	public static Stations getStationList() {
		return stationList;
	}

	public static void setStationList(Stations list) {
		BFSN.stationList = list;
	}

	//MAIN
	public static void main(String[] args) throws SchedulerException, TwitterException {
		
		//START SERVER
		URI baseUri = UriBuilder.fromUri("http://localhost/").port(15000).build();
		ResourceConfig config = new ResourceConfig(UserServices.class, StationsServices.class, TelegramServices.class);
		@SuppressWarnings({ "restriction", "unused" })
		HttpServer server = JdkHttpServerFactory.createHttpServer(baseUri, config);
		System.out.println("Server started...");
		
		//MONTA EL JOB QUE SE REPITE CADA X SEGUNDOS
		// Specify the job' s details..
		JobDetail jobGetStations = JobBuilder.newJob(JobGetStations.class).withIdentity("stationJobs").build();
		JobDetail jobTelegramNotifications = JobBuilder.newJob(JobTelegramNotifications.class).withIdentity("telegramJobs").build();


		// Specify the running period of the job
		//Definimos t, y t+15
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		Date start = cal.getTime();
		cal.add(Calendar.SECOND, 15);
		Date offset = cal.getTime();
		
		//Ejecutamos el jobGetStations en t=0 y jobTelegramNotifications en t+15
		Trigger triggerGetStations = TriggerBuilder.newTrigger().startAt(start)
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(10).repeatForever()).build();
		Trigger triggerTelegramNotifications = TriggerBuilder.newTrigger().startAt(offset)
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(60).repeatForever()).build();
		
		// Schedule the job
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler sched = sf.getScheduler();
		sched.start();
		sched.scheduleJob(jobGetStations, triggerGetStations);
		sched.scheduleJob(jobTelegramNotifications, triggerTelegramNotifications);

		//NEW USERS FOR TESTING
		ArrayList<String> list1 = new ArrayList<String>();
		list1.add("1");
		list1.add("9");
		list1.add("10");
		list1.add("11");
		list1.add("91");
		list1.add("101");
		list1.add("21");
		list1.add("29");
		list1.add("210");
		User user1 = new User("647217759","180544183",list1);	//Juan
		users.add(user1);
		
		ArrayList<String> list2 = new ArrayList<String>();
		list2.add("45");
		list2.add("90");
		list2.add("160");
		User user2 = new User("633502429","340580884",list2);	//Ivan
		users.add(user2);
			
	}
}
