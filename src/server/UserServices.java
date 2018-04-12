package server;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import data.User;

@Path("/users")
public class UserServices {
	
	static List<User> users = BFSN.getUsers();
	
	//Añade un usuario
	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(User user) {
		for(User userlocal : users) {
			if(userlocal.getPhone().toString().equalsIgnoreCase(user.getPhone().toString())){
				return Response.status(409).entity("User already exists").build();
			}
		}
		users.add(user);
		BFSN.setUsers(users);
		return Response.status(200).entity(user).build();
	}
	
	//Devuelve un usuario dado un telefono
	@GET
	@Path("/get/{phone}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserByPhone(@PathParam("phone") String phone) {
		for(User user : users) {
			if(user.getPhone().equalsIgnoreCase(phone)) {
				return Response.status(200).entity(user).build();
			}			
		}
		return Response.status(404).entity("User Not Found").build();
	}
	
	//Devuelve todos los usuarios
	@GET
	@Path("/getAll")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsers() {
		return Response.status(200).entity(users).build();
	}
	
	//Suscribe un usuario a una estacion
	@PUT
	@Path("/subscribeTo/{id}/{phone}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response subscribe(@PathParam("id") String id, @PathParam("phone") String phone) {
		for(User user : users) {
			if(user.getPhone().equalsIgnoreCase(phone)) {
				if(user.getList().contains(id)) {
					return Response.status(409).entity("User has already been subscribed to Station " + id.toString()).build();
				}
				user.subscribeToStation(id);				
			}
		}
		return Response.status(200).entity("User subscribed to Station " + id.toString()).build();
	}
	
	//Actualiza el token de un usuario dado el telefono
	@PUT
	@Path("/updateToken/{phone}/{token}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateToken(@PathParam("phone") String phone, @PathParam("token") String token) {
		for(User user : users) {
			if(user.getPhone().equalsIgnoreCase(phone)) {
				user.setToken(token);
				return Response.status(200).entity("").build();
			}
		}
		return Response.status(404).entity("User not found").build();
	}
	
}
