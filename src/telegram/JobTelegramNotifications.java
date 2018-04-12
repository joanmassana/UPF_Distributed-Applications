package telegram;

import java.util.Date;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import data.Message;
import data.Station;
import data.Stations;
import data.User;

import server.BFSN;

public class JobTelegramNotifications implements Job {

	Stations stations = BFSN.getStationList();
	List<User> users = BFSN.getUsers();
	Client client = ClientBuilder.newClient();

	public void execute(JobExecutionContext context) throws JobExecutionException {
		for (User user : users) {
			int chatID = Integer.parseInt(user.getToken());
			String telegramMessage = "Stations with free slots: \n\n";

			for (String string : user.getList()) {
				Station station = stations.getStationById(string);
				if (!(station.getSlots().equals("0"))) {
					telegramMessage += " >  " + station.getStreetName() + "\n";

				}
			}

			Message message = new Message(chatID, telegramMessage);
			WebTarget targetSendMessage = client.target("https://api.telegram.org")
					.path("/bot401873614:AAEMTvWlj75SrGiAZgilSGZUkBA03MMsNYo/sendMessage");
			String response = targetSendMessage.request().post(Entity.entity(message, MediaType.APPLICATION_JSON_TYPE),
					String.class);

			//System.out.println(new Date().toString() + " - Telegram message sent to " + user.getPhone().toString());

		}
		System.out.println(new Date().toString() + " - Telegram messages sent to users");

	}
}