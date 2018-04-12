package bicing;

import java.util.Date;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import data.*;
import server.BFSN;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class JobGetStations implements Job {

	Stations stations = BFSN.getStationList();		
	
	//Acceso a la API de Bicing y guardamos datos en la variable station de BFSN
	public void execute(JobExecutionContext context) throws JobExecutionException {
		 
		Client client = ClientBuilder.newClient();
		WebTarget targetGetAll = client.target("http://wservice.viabicing.cat/").path("v2/stations");
		try {
		stations = targetGetAll.request(MediaType.APPLICATION_JSON_TYPE).get(new GenericType<Stations>() {});	
		} catch (Exception e) {
			
		}
		
		BFSN.setStationList(stations);
		System.out.println(new Date().toString() + " - Bicing data acquired" );
	}
}