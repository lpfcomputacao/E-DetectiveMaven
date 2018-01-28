package models;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import DAO.MongoDAO;
import DTO.DatingBirthday;
import DTO.Point;
import DTO.UserIdentifier;
import log.Log;


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
            Log.getInstance().writeOnLog("Erro ao enviar a data de aniverssário.", e.getStackTrace());
        }

	}

	private void receivePoints() {
		while (true) {
			try {

				inStream = new ObjectInputStream(clientSocket.getInputStream());
				point = (Point) inStream.readObject();
				MongoDAO.getInstance().setUserPoint(userIdentifier.getUserEmail(), point);
				

			} catch (IOException e) {
                Log.getInstance().writeOnLog("Erro ao receber os pontos do tracked - I/O Exception.", e.getStackTrace());
                break;
            } catch (ClassNotFoundException e) {
                Log.getInstance().writeOnLog("Não foi encontrada uma classe Point para receber os pontos do tracked.", e.getStackTrace());
                break;
            }
		}
	}
	
	private UserIdentifier receiveUserIdentifier() {
		
		try {
			inStream = new ObjectInputStream(clientSocket.getInputStream());
			UserIdentifier userIdentifier = new UserIdentifier();
			userIdentifier = (UserIdentifier) inStream.readObject();
			System.out.println(userIdentifier.getUserEmail());
			return userIdentifier;

		} catch (IOException e) {
            Log.getInstance().writeOnLog("Não foi possível receber dados do cliente.", e.getStackTrace());
            return null;
        } catch (ClassNotFoundException e) {
            Log.getInstance().writeOnLog("Não existe uma classe compatível com UserIdentifier.", e.getStackTrace());
            return null;
        }
	}
	
}
