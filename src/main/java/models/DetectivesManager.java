package models;

import java.util.HashMap;

public class DetectivesManager implements Runnable {

	public static HashMap<String, Runnable> detectivesHashMap;
	
	
	public DetectivesManager () {}
	
	@Override
	public void run() {
		
		//detectivesHashMap
		// iniciar número de threads igual ao número de usuários 
		
		// Todas as threads vão ficar responsáveis por um usuário 
		
		// haverá um listener nesta classe responsável em ser o intermediário entre a requisição do usuário e o conteúdo das threads 
		
	}

}
