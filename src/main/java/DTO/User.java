package DTO;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;

import util.DatingBirthday;

public class User implements Serializable{
	
	//private static final long serialVersionUID = -8880802417791340522L;
	private String name;
	private String password;
	private String email;
	private DatingBirthday datingDate;
	
	private Point markerPosition;

//	{
//		"name" : "Sarah",
//		"password" : "lucass2",
//		"email" : "sarah@gmail.com",
//		"datingDate" : {
//			"year" : "2014",
//			"month" : "6",
//			"day" : "1"
//		},
//		"markerPosition" : {
//			"latitude" : "42.5455",
//			"longitude" : "22.545"
//		}
//	}
		
	
	
	public User() {}
	
	public User(String name, String password, String userName, DatingBirthday datingDate, Point markersPositions) {
		this.name = name;
		this.password = password;
		this.email = userName;
		this.datingDate = datingDate;
		this.markerPosition = markerPosition;
		
	}
	
    
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String userName) {
		this.email = userName;
	}

	public DatingBirthday getDatingDate() {
		return datingDate;
	}

	public void setDatingDate(DatingBirthday datingDate) {
		this.datingDate = datingDate;
	}

	public Point getMarkerPosition() {
		return markerPosition;
	}

	public void setMarkerPosition(Point markerPosition) {
		this.markerPosition = markerPosition;
	}
}
