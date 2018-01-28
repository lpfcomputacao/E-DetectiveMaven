package models;

import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;

import DAO.MongoDAO;
import DTO.User;

public class DetectivesManager implements Runnable {

	public static HashMap<User, Detective> detectives;
	private ArrayList<User> userList;
	private User newUser;
	
	ServerSocket serverSocket;
	
	public DetectivesManager() {}

	@Override
	public void run() {

		
		observingNewUser();
	}
	private Detective getDetectiveByUser(User user) {
		return detectives.get(user);
	}
	private void observingNewUser() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				
				adderCurrentDBSituation();
				
				
				while (true) {
					try {
						Thread.sleep(30000);
						
						if(listComparator()) {
							creatDetective(newUser);
						}
						
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

			private boolean listComparator() {
				ArrayList<User> listAux;
				listAux = MongoDAO.getInstance().returnDBUsers();
				
				if(listAux.size() == userList.size()) {
					return false;
				}else {
					for (int i = 0; i < listAux.size(); i++) {
						if(!userList.contains(listAux.get(i))) {
							newUser = listAux.get(i);
							return true;
						}
					}
				}
				return false;
			}

			private void adderCurrentDBSituation() {
				
				userList = MongoDAO.getInstance().returnDBUsers();
				
				for (int i = 0; i < userList.size(); i++) {
					creatDetective(userList.get(i));
				}
				
			}
			
			
		});
	}

	private void creatDetective(User user) {
		Detective detective = new Detective(user);
		detectives.put(user, detective);
		new Thread(detective).start();
	}

}
