package models;

import java.io.File;

public class Main {
	
	public static int TRACKED_LISTENER_PORT = 5001;
	public static int CLIENT_LISTENER_PORT = 5002;
	public static int CLIENT_SIGNUP_LISTENER_PORT = 5003;
	
	public static void main(String[] args) {
		TRACKED_LISTENER_PORT = (args.length >= 1) ? Integer.parseInt(args[0]) : 5001;
		CLIENT_LISTENER_PORT = (args.length >= 2) ? Integer.parseInt(args[1]) : 5002;
		CLIENT_SIGNUP_LISTENER_PORT = (args.length >= 3) ? Integer.parseInt(args[2]) : 5003;
		
		File logDirectory = new File("log");
		if(!logDirectory.exists()) {
			System.out.println("Log folder created");
			logDirectory.mkdirs();
		}
		TrackedListener trackedListener = new TrackedListener();
		ClientListener clientListener = new  ClientListener();
		ClientSignUpListener signUpListener = new ClientSignUpListener();
		DetectivesManager detectiveManager = new DetectivesManager();
		
		
		
		startRunnable(trackedListener);
		startRunnable(clientListener);
		startRunnable(signUpListener);
		startRunnable(detectiveManager);
		
	}
	
	public static void startRunnable(Runnable runnable) {
		Thread threadObj = new Thread(runnable);
		threadObj.start();
	}
}
