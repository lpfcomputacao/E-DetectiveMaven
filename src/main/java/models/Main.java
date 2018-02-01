package models;

public class Main {
	
	public static void main(String[] args) {
		
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
