package models;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import DAO.MongoDAO;
import DTO.Point;
import DTO.UserIdentifier;
import util.DatingBirthday;


public class TrackedConnection extends Connection{
	
	
	private Point point;
	
	public TrackedConnection(Socket trackedSocket) {
		super(trackedSocket);
		
	}

	@Override
	public void run() {
		
		while(true) {
			userIdentifier = receiveUserIdentifier();
			
			if(userIdentifier != null) {
				if(MongoDAO.getInstance().userValidator(userIdentifier)) {
					startComunication();
					break;
				}else {
					userNotFound();
				}
				
			}else {
				break;
			}
		}
	}
	
	
	private void startComunication() {
		
		sendDatingBirthday();
		receivePoints();
	}

	private void sendDatingBirthday() {
		try {

			outputStream = new ObjectOutputStream(clientSocket.getOutputStream());

			DatingBirthday date = MongoDAO.getInstance().getDatingBirthdayDB(userIdentifier.getUserEmail());
			outputStream.writeObject(date);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void receivePoints() {
		while (true) {
			try {

				inStream = new ObjectInputStream(clientSocket.getInputStream());
				point = (Point) inStream.readObject();
				MongoDAO.getInstance().setUserPoint(userIdentifier.getUserEmail(), point);

				outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
				outputStream.writeObject("Recebido");

			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
				break;
			}
		}
	}
	
	private UserIdentifier receiveUserIdentifier() {
		
		try {
			inStream = new ObjectInputStream(clientSocket.getInputStream());
			UserIdentifier userIdentifier = new UserIdentifier();
			userIdentifier = (UserIdentifier) inStream.readObject();
			return userIdentifier;

		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
