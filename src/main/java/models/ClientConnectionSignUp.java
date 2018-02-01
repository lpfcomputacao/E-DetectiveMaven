package models;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import DAO.MongoDAO;
import DTO.User;
import DTO.UserIdentifier;
import log.Log;

public class ClientConnectionSignUp extends Connection {

	public ClientConnectionSignUp(Socket clientSocket) {
		super(clientSocket);
	}

	public void run() {

		Object objectX = receivingObject();

		if (objectX instanceof String) {
			User newClient = receiveNewClient();
			MongoDAO.getInstance().saveNewClient(newClient);
		}

	}

	private Object receivingObject() {

		try {
			Object object = new Object();
			inStream = new ObjectInputStream(clientSocket.getInputStream());
			object = (Object) inStream.readObject();
			return object;

		} catch (IOException e) {
			Log.getInstance().writeOnLog("Objeto da rede não recebido", e.getStackTrace());
			return null;
		} catch (ClassNotFoundException e) {
			Log.getInstance().writeOnLog("Não foi encontrada uma classe Object comaptível com o objeto recebido",
					e.getStackTrace());
			return null;
		}
	}

	private User receiveNewClient() {

		try {
			User newUser = new User();
			inStream = new ObjectInputStream(clientSocket.getInputStream());
			newUser = (User) inStream.readObject();
			return newUser;

		} catch (IOException e) {
			Log.getInstance().writeOnLog("Não foi possível receber a classe do cliente", e.getStackTrace());
			return null;
		} catch (ClassNotFoundException e) {
			Log.getInstance().writeOnLog("Não foi encontrada uma classe User comaptível com o objeto recebido",
					e.getStackTrace());
			return null;
		}
	}

}
