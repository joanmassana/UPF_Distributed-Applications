package data;

import java.util.Date;
import java.util.List;

public class Stations {
	
	//ATTRIBUTES
	private List<Station> stations;
	
	//CONSTRUCTORS
	public Stations(List<Station> stations) {
		super();
		this.stations = stations;
	}
	
	public Stations()
	{
		
	}
	
	//GETTERS and SETTERS
	public List<Station> getStations() {
		return stations;
	}

	public void setStations(List<Station> stations) {
		this.stations = stations;
	}

		
	@Override
	public String toString() {
		String list = "";
		for(Station t : stations) {
			list += t.toString();
		}
		return list;
	}
	
	//Devuelve una estacion dado un id
	public Station getStationById(String id) {
		for(Station station : stations) {
			if(station.getId().equalsIgnoreCase(id)) {
				return station;
			}
		}
		return null;
	}
	
	//Añade un usuario
	public void addStation(Station e) {
		stations.add(e);
	}
	
	//STATISTICS
	//Devuelve un string con ciertas estadisticas (calculadas mas abajo) sobre los datos de Bicing
	public String getHourlyStatistics() {
		return  new Date().toString() +  "\nOpen Stations: " + statsOpenStations() + " / " + stations.size() + " (" + round(((double)statsOpenStations()/(double)stations.size())*100,2) + "%)\n"
				+ "Full Stations: " + statsFullStations() + " / " + stations.size() + " (" + round(((double)statsFullStations()/(double)stations.size())*100,2) + "%)\n"
				+ "Empty Stations: " + statsEmptyStations() + " / " + stations.size() + " (" + round(((double)statsEmptyStations()/(double)stations.size())*100,2) + "%)\n";
	}
	
	//Devuelve el numero de estaciones abiertas
	private int statsOpenStations() {
		int open = 0;
		for(Station station : stations) {
			if(station.getStatus().equalsIgnoreCase("OPN")) {
				open++;
			}
		}
		return open;
	}
	
	//Devuelve elnumero de estaciones llenas (no free slots)
	private int statsFullStations() {
		int full = 0;
		for(Station station : stations) {
			if(station.getSlots().equals("0")) {
				full++;
			}
		}  
		return full;
	}
	
	//Devuelve el numero de estaciones vacias (free slots)
	private int statsEmptyStations() {
		int empty = 0;
		for(Station station : stations) {
			if(station.getBikes().equals("0")) {
				empty++;
			}
		} 
		return empty;
	}
	
	//Redondea doubles a dos decimales
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}
	
}