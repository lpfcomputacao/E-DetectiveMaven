package models;

import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;

import DAO.MongoDAO;
import DTO.User;

public class DetectivesManager implements Runnable {

	public static HashMap<User, Detective> detectives = new HashMap<>();
	private ArrayList<User> userList = new ArrayList<>();
	private User newUser;
	
	ServerSocket serverSocket;
	
	public DetectivesManager() {}

	@Override
	public void run() {
		
		observingNewUser();
	}
	
	
	
	private void observingNewUser() {
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				adderCurrentDBSituation();
				
				/*
				while (true) {
					try {
						Thread.sleep(10000);
						
						if(listComparator()) {
							creatDetective(newUser);
						}
						
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				*/
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
			
			public Detective getDetectiveByEmail (String email) {
				return detectives.get(MongoDAO.getInstance().getUser(email));
			}

			private void adderCurrentDBSituation() {
				
				userList = MongoDAO.getInstance().returnDBUsers();
				
				for (int i = 0; i < userList.size(); i++) {
					System.out.println(userList.get(i).getEmail());
					creatDetective(userList.get(i));
				}
				
			}
			private void creatDetective(User user) {
				Detective detective = new Detective(user);
				detectives.put(user, detective);
				Thread thread = new Thread(detective);
				thread.start();
			}
			
		}).start();
	}
	
	

}
