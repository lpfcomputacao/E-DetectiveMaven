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
		
		TrackedListener trackedListener = new TrackedListener();
//		ClientListener clientListener = new  ClientListener();
//		DetectivesManager detectiveManager = new DetectivesManager();
		
		
		
		startRunnable(trackedListener);
//		startRunnable(clientListener);
//		startRunnable(detectiveManager);
		
	}
	
	public static void startRunnable(Runnable runnable) {
		Thread threadObj = new Thread(runnable);
		threadObj.start();
	}
}
