package models;

import java.net.ServerSocket;
import java.util.HashMap;

import DTO.User;

public class DetectivesManager implements Runnable {

	public static HashMap<User, Detective> detectives;
	ServerSocket serverSocket;
	public DetectivesManager() {
	}

	@Override
	public void run() {

		// detectivesHashMap
		// iniciar número de threads igual ao número de usuários

		// Todas as threads vão ficar responsáveis por um usuário

		// haverá um listener nesta classe responsável em ser o intermediário entre a
		// requisição do usuário e o conteúdo das threads
		observingNewUser();
	}
	private Detective getDetectiveByUser(User user) {
		return detectives.get(user);
	}
	private void observingNewUser() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					User user = null;
					try {
						Thread.sleep(30000);
						// Aqui o Lucas fica procurando os novos usuários no banco de dados
						//if(achou usuário){
							creatDetective(user);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
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
