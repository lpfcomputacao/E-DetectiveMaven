package models;

import org.bson.Document;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import DTO.User;

public class Main {
	
	public static void main(String[] args) {
		
//		TrackedListener trackedListener = new TrackedListener();
//		ClientListener clientListener = new  ClientListener();
//		DetectivesManager detectiveManager = new DetectivesManager();
		
		
		MongoClient mongo = new MongoClient( "localhost" , 27017 );
        MongoDatabase database = mongo.getDatabase("Detective");
        MongoCollection users = database.getCollection("Users");
        
        FindIterable<Document> resultadoDaBusca = users.find();
        
        User objTeste = new User();

      
        for (Document document : resultadoDaBusca) {
            objTeste = new Gson().fromJson(document.toJson(), User.class);
            if(objTeste.getEmail().equals("sarah@gmail.com")) {
            	
            	BasicDBObject key = new BasicDBObject("email", "sarah@gmail.com");
                BasicDBObject latitude = new BasicDBObject("currentPosition.latitude", Double.toString(22.222));
                BasicDBObject longitude = new BasicDBObject("currentPosition.longitude", Double.toString(22.222));
                
                BasicDBObject newPoint = new BasicDBObject("$set", latitude);
                users.updateOne(key, newPoint);
                newPoint = new BasicDBObject("$set", longitude);
                users.updateOne(key,newPoint);
               
            	
            }
        }
        mongo.close();
       
		
//		startRunnable(trackedListener);
//		startRunnable(clientListener);
//		startRunnable(detectiveManager);
		
	}
	
	public static void startRunnable(Runnable runnable) {
		Thread threadObj = new Thread(runnable);
		threadObj.start();
	}
}