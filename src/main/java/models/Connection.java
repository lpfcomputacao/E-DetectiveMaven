package models;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import DTO.UserIdentifier;
import log.Log;

public abstract class Connection implements Runnable{
	
	protected Socket clientSocket;
	protected ObjectInputStream inStream;
	protected ObjectOutputStream outputStream;
	
	protected UserIdentifier userIdentifier;

	public Connection(Socket socket) {
		this.clientSocket = socket;
	}
	
	
	protected void userNotFound(){
		try {
			outputStream =  new ObjectOutputStream(clientSocket.getOutputStream());
			outputStream.writeObject("Usuario nao cadastrado");
		} catch (IOException e) {
			Log.getInstance().writeOnLog("Não foi possivel avisar ao usuário que não está cadastrado", e.getStackTrace());
		}
	}
	protected void userFound(){
		try {
			outputStream =  new ObjectOutputStream(clientSocket.getOutputStream());
			outputStream.writeObject("Usuario cadastrado");
		} catch (IOException e) {
			Log.getInstance().writeOnLog("Não foi possivel avisar ao usuário que não está cadastrado", e.getStackTrace());
		}
	}
	
	
	
}
