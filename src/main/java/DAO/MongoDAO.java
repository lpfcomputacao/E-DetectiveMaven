package DAO;

import java.util.ArrayList;

import org.bson.Document;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import DTO.User;
import DTO.UserIdentifier;
import DTO.DatingBirthday;
import DTO.Point;

public class MongoDAO {

	private MongoDAO() {}
	
	private MongoClient mongo;
	private MongoDatabase database;
	private MongoCollection users;
	
	private void initializeMongoDB() {
		mongo = new MongoClient( "localhost" , 27017 );
        database = mongo.getDatabase("Detective");
        users = database.getCollection("Users");
	}
	
	public boolean userValidator(UserIdentifier userIdentifier) {
		
		initializeMongoDB();
        
        FindIterable<Document> resultadoDaBusca = users.find();
        User objTeste = new User();
        
        String password = userIdentifier.getPassword();
        String email = userIdentifier.getUserEmail();
        
        	
        for (Document document : resultadoDaBusca) {
            objTeste = new Gson().fromJson(document.toJson(), User.class);
            if(objTeste.getEmail().equals(email) && objTeste.getPassword().equals(password)) {
            	mongo.close();
            	return true;
            }
        }
		mongo.close();
		return false;
	}
	
	public DatingBirthday getDatingBirthdayDB(String userEmail) {
		
		initializeMongoDB();
        
        FindIterable<Document> resultadoDaBusca = users.find();
        User objTeste = new User();
 	
        for (Document document : resultadoDaBusca) {
            objTeste = new Gson().fromJson(document.toJson(), User.class);
            if(objTeste.getEmail().equals(userEmail)) {
            	mongo.close();
            	return objTeste.getDatingDate();
            }
        }
 
        mongo.close();
        return null;
		
	}
	
	

	public void saveNewClient(User newUser) {
		
		initializeMongoDB();
        
		
		Document User = new Document();
		User.put("name", newUser.getName());
		User.put("password", newUser.getPassword());
		User.put("email", newUser.getEmail());
		
		BasicDBObject datingDate = new BasicDBObject();
		datingDate.put("year", newUser.getDatingDate().getYear());
		datingDate.put("month", newUser.getDatingDate().getMonth());
		datingDate.put("day", newUser.getDatingDate().getDay());
		
		BasicDBObject markerPoint = new BasicDBObject();
		markerPoint.put("latitude", newUser.getMarkerPosition().getX());
		markerPoint.put("longitude", newUser.getMarkerPosition().getY());
		
		BasicDBObject currentPoint = new BasicDBObject();
		currentPoint.put("latitude", newUser.getCurrentPosition().getX());
		currentPoint.put("longitude", newUser.getCurrentPosition().getY());

		User.put("datingDate", datingDate);
		User.put("markerPosition", markerPoint);
		User.put("currentPosition",currentPoint);
//		
//		System.out.println(newUser.getName());
//		System.out.println(newUser.getPassword());
//		System.out.println(newUser.getEmail());
//		System.out.println(newUser.getCurrentPosition().getX());
//		System.out.println(newUser.getCurrentPosition().getY());
//		System.out.println(newUser.getMarkerPosition().getX());
//		System.out.println(newUser.getMarkerPosition().getY());
//		System.out.println(newUser.getDatingDate().getYear());
//		System.out.println(newUser.getDatingDate().getDay());
//		System.out.println(newUser.getDatingDate().getMonth());
//		
		
		users.insertOne(User);
		mongo.close();
		
	}

	public void setUserPoint(String userEmail, Point point) {
		
		initializeMongoDB();

        FindIterable<Document> resultadoDaBusca = users.find();
        
        User objTeste = new User();
        	
        for (Document document : resultadoDaBusca) {
            objTeste = new Gson().fromJson(document.toJson(), User.class);
            if(objTeste.getEmail().equals(userEmail)) {
            	
            	BasicDBObject key = new BasicDBObject("email", userEmail);
                BasicDBObject latitude = new BasicDBObject("currentPosition.latitude", Double.toString(point.getX()));
                BasicDBObject longitude = new BasicDBObject("currentPosition.longitude", Double.toString(point.getY()));
                
                BasicDBObject newPoint = new BasicDBObject("$set", latitude);
                users.updateOne(key,newPoint);
                newPoint = new BasicDBObject("$set", longitude);
                users.updateOne(key, newPoint);
                
            	mongo.close();
            	return;
            	
            }
        }
        mongo.close();
	}
	
	public static MongoDAO getInstance() {
		return MongoDAOHolder.INSTANCE;
	}
	
	private static class MongoDAOHolder{
		private static final MongoDAO INSTANCE = new MongoDAO();
	}

	public Point getcurrentPosition(String userEmail) {
		initializeMongoDB();
        
        FindIterable<Document> resultadoDaBusca = users.find();
        User objTeste = new User();
 	
        for (Document document : resultadoDaBusca) {
            objTeste = new Gson().fromJson(document.toJson(), User.class);
            if(objTeste.getEmail().equals(userEmail)) {
            	mongo.close();
            	return objTeste.getCurrentPosition();
            }
        }
 
        mongo.close();
        return null;
	}

	public User getUser(String email) {
		initializeMongoDB();
        
        FindIterable<Document> resultadoDaBusca = users.find();
        User objTeste = new User();
 	
        for (Document document : resultadoDaBusca) {
            objTeste = new Gson().fromJson(document.toJson(), User.class);
            if(objTeste.getEmail().equals(email)) {
            	mongo.close();
            	return objTeste;
            }
        }
 
        mongo.close();
        return null;
	}

	public ArrayList<User> returnDBUsers() {
		initializeMongoDB();
        
        FindIterable<Document> resultadoDaBusca = users.find();
        User objTeste = new User();
        ArrayList<User> userList = new ArrayList<>();
 	
        for (Document document : resultadoDaBusca) {
            objTeste = new Gson().fromJson(document.toJson(), User.class);
            userList.add(objTeste);
        }
 
        mongo.close();
        return userList;
	}

}
