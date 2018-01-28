package models;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import DAO.MongoDAO;
import DTO.DatingBirthday;
import DTO.Point;
import DTO.User;
import DTO.UserIdentifier;
import log.Log;

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
			Log.getInstance().writeOnLog("Não foi possível enviar a posição atual", e.getStackTrace());
		}
	}

	private User receiveNewClient() {
		
		try {
			User newUser = new User();
			inStream = new ObjectInputStream(clientSocket.getInputStream());
			newUser = (User)inStream.readObject();
			return newUser;
			
		} catch (IOException e) {
			Log.getInstance().writeOnLog("Não foi possível receber a classe do cliente", e.getStackTrace());
			return null;
		} catch (ClassNotFoundException e) {
			Log.getInstance().writeOnLog("Não foi encontrada uma classe User comaptível com o objeto recebido", e.getStackTrace());
			return null;
		}
	}

	private Object receivingObject() {

		try {
			Object object = new Object();
			inStream = new ObjectInputStream(clientSocket.getInputStream());
			object = (Object)inStream.readObject();
			return object;
			
		} catch (IOException e) {
			Log.getInstance().writeOnLog("Objeto da rede não recebido", e.getStackTrace());
			return null;
		} catch (ClassNotFoundException e) {
			Log.getInstance().writeOnLog("Não foi encontrada uma classe Object comaptível com o objeto recebido", e.getStackTrace());
			return null;
		}
	}
	
	private String receiveRequest() {
		try {
			String request = new String();
			inStream = new ObjectInputStream(clientSocket.getInputStream());
			request = (String)inStream.readObject();
			return request;
			
		} catch (IOException e) {
			Log.getInstance().writeOnLog("Não foi possível receber a requisição do usuário", e.getStackTrace());
			return null;
		} catch (ClassNotFoundException e) {
			Log.getInstance().writeOnLog("Classe recebida não compatível com String", e.getStackTrace());
			return null;
		}
		
	}
	
}
