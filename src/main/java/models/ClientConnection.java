package models;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import DAO.MongoDAO;
import DTO.Point;
import DTO.User;
import DTO.UserIdentifier;

public class ClientConnection extends Connection {

	public ClientConnection(Socket clientSocket) {
		super(clientSocket);
	}

	@Override
	public void run() {

		while(true) {
			
			Object objectX = receivingObject();
			
			//logar
			
			if(objectX instanceof UserIdentifier) {
			
				userIdentifier = (UserIdentifier) objectX;
			
				if(MongoDAO.getInstance().userValidator(userIdentifier)) {
					startComunication();
					break;
				}else {
					userNotFound();
				}
			}
			
			//cadastrar
			
			if(objectX instanceof String) {
				User newClient = receiveNewClient();
				MongoDAO.getInstance().saveNewClient(newClient);
			}
		}	
	}
	
	private void startComunication() {
		
		
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
}
