package data;

import java.util.ArrayList;

public class User {
	
	//ATTRIBUTES
	private String phone;
	private String token;
	private ArrayList<String> list;
	
	//CONSTRUCTORS
	public User(String phone, String token, ArrayList<String> list) {
		super();
		this.phone = phone;
		this.token = token;
		this.list = list;
	}
	
	public User() {		
	}
	
	//GETTERS AND SETTERS
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public ArrayList<String> getList() {
		return list;
	}
	public void setList(ArrayList<String> list) {
		this.list = list;
	}
	
	@Override
	public String toString() {
		return "User [phone=" + phone + ", token=" + token + ", list=" + list + "]";
	}
	
	//Suscribir usuario a una estación
	public void subscribeToStation(String id) {
		list.add(id);
	}
}
