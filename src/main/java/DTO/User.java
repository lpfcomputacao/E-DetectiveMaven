package DTO;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String name;
    private String password;
    private String email;
    private DatingBirthday datingDate;

    private Point markerPosition;
    private Point currentPosition;


    public User() {}

    public User(String name, String password, String email, DatingBirthday datingDate, Point markerPosition, Point currentPosition) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.datingDate = datingDate;
        this.markerPosition = markerPosition;
        this.setCurrentPosition(currentPosition);

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

    public Point getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(Point currentPosition) {
        this.currentPosition = currentPosition;
    }
    @Override
    public int hashCode() {
    	return Objects.hash(email, password);
    }
    @Override
    public boolean equals(Object obj) {
    	if(this.email.equals(((User)obj).getEmail())) {
    		return true;
    	}
    	return false;
    }
}
