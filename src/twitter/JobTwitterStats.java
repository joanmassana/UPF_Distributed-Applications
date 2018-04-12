package twitter;

import java.util.Date;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class JobTwitterStats implements Job {		
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		Client client = ClientBuilder.newClient();		
		WebTarget targetGetStats = client.target("http://localhost:15000").path("/stations/getStats");
		String statsMessage = targetGetStats.request(MediaType.APPLICATION_JSON_TYPE).get(new GenericType<String>() {});
		
				
		//TWITTER Set-Up
		TwitterFactory factory = new TwitterFactory();
		Twitter twitter = factory.getInstance();
		twitter.setOAuthConsumer("2UMXTbezky1nQZyV2d6XABs6s", "L4B6qmWcMgTPmD2QzInFgC2V6cpssvDEnYUIxPywe4MK5XEjHB");
		twitter.setOAuthAccessToken(new AccessToken("932563538333356032-fRejMcwBrqmsy1EczbmvPei0NYFsO8H","r9qoyoqrFeW4RIfCI4iunlYv1MI7NPWxmI4CZoe0Do6SQ"));	
		
		try {
			Status status = twitter.updateStatus(statsMessage);
			System.out.println("Twitter status updated - " + new Date().toString());
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		
	}
}