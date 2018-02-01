package models;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import log.Log;

public abstract class Listener implements Runnable {

	protected final int PORTA;
	protected ServerSocket serverSocket;

	public Listener(int porta) {
		this.PORTA = porta;
	}

	@Override
	public void run() {

		creatSocket();
		listenerMethod();
		closeSocket();

	}

	protected void listenerMethod() {
		while (true) {
			connectionExpector();
		}
	}

	protected void connectionExpector() {
		try {

			Socket socket = serverSocket.accept();

			switch (serverSocket.getLocalPort()) {
			case 5001:
				TrackedConnection trackedConnection = new TrackedConnection(socket);
				startThreadConnection(trackedConnection);
				break;

			case 5002:
				ClientConnection clientConnection = new ClientConnection(socket);
				startThreadConnection(clientConnection);
				break;
			
			case 5003:
				ClientConnectionSignUp clientConnectionSignUp  = new ClientConnectionSignUp(socket);
				startThreadConnection(clientConnectionSignUp);
				break;
			}
			
		}catch (IOException e) {
			Log.getInstance().writeOnLog("não foi possivel inicializar a conexão com o cliente ou com o rastreado",
					e.getStackTrace());
		}
	}

	protected void startThreadConnection(Connection connection) {
		Thread threadConnection = new Thread(connection);
		threadConnection.start();
	}

	protected void creatSocket() {

		try {
			serverSocket = new ServerSocket(PORTA);
			System.out.println("Socket Criado com Sucesso");

		} catch (IOException e) {
			Log.getInstance().writeOnLog("Não é possível inicizalizar na porta: " + PORTA, e.getStackTrace());
			System.err.println("Não é possível inicizalizar na porta: " + PORTA + "\nDesligando...");
			System.exit(1);
		}
	}

	protected void closeSocket() {
		try {
			serverSocket.close();
		}catch (IOException e) {
			Log.getInstance().writeOnLog("Não foi possivel fechar o socket na porta " + serverSocket.getLocalPort(),
					e.getStackTrace());
		}
	}
}
