package server;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import data.Station;
import data.Stations;

@Path("/stations")
public class StationsServices {
	
	static Stations stations = BFSN.getStationList();

	//Devuelve una estación dado un id
	@GET
	@Path("/get/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStationById(@PathParam("id") String id) {
		Station station = stations.getStationById(id);
		if(station == null) {
			return Response.status(404).entity(station).build();
		}		
		return Response.status(200).entity(station).build();
	}
	
	//Devuelve todas las estaciones
	@GET
	@Path("/getAll")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStations() {
		if(stations == null) {
			return Response.status(404).entity(stations).build();
		}
		return Response.status(200).entity(stations).build();
	}
	
	//Devuelve las estadisticas
	@GET
	@Path("/getStats")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStats() {
		String stats = stations.getHourlyStatistics();
		return Response.status(200).entity(stats).build();
	}
}
