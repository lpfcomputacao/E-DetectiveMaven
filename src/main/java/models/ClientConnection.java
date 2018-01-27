package models;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import DAO.MongoDAO;
import DTO.Point;
import DTO.User;
import DTO.UserIdentifier;
import util.DatingBirthday;

public class ClientConnection extends Connection {

	public ClientConnection(Socket clientSocket) {
		super(clientSocket);
	}

	@Override
	public void run() {

		while(true) {
			
			Object objectX = receivingObject();
			
		
			if(objectX instanceof UserIdentifier) {
			
				userIdentifier = (UserIdentifier) objectX;
			
				if(MongoDAO.getInstance().userValidator(userIdentifier)) {
					startComunication();
					break;
				}else {
					userNotFound();
				}
			}
			
			
			if(objectX instanceof String) {
				User newClient = receiveNewClient();
				MongoDAO.getInstance().saveNewClient(newClient);
			}
		}	
	}
	
	private void startComunication() {
		//
		// while(true) enviando posição do usuário para o cliente
		while(true) {
			
			String request = receiveRequest();
			
			if(request.equals("currentPostion")){
				
				sendCurrentPosition();
				
			}
			if(request.equals("notification")) {
				
				//Aqui meu caro tavinho e com você e deus
				
			}
		}
	}

	private void sendCurrentPosition() {
		try {
			outputStream = new ObjectOutputStream(clientSocket.getOutputStream());

			Point currentPosition = MongoDAO.getInstance().getcurrentPosition(userIdentifier.getUserEmail());
			outputStream.writeObject(currentPosition);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private User receiveNewClient() {
		
		try {
			User newUser = new User();
			inStream = new ObjectInputStream(clientSocket.getInputStream());
			newUser = (User)inStream.readObject();
			return newUser;
			
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	private Object receivingObject() {

		try {
			Object object = new Object();
			inStream = new ObjectInputStream(clientSocket.getInputStream());
			object = (Object)inStream.readObject();
			return object;
			
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private String receiveRequest() {
		try {
			String request = new String();
			inStream = new ObjectInputStream(clientSocket.getInputStream());
			request = (String)inStream.readObject();
			return request;
			
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
}
