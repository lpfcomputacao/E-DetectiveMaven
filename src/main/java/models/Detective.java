package models;

import java.util.ArrayList;

import DAO.MongoDAO;
import DTO.Point;
import DTO.User;

public class Detective implements Runnable {
	private User user;
	private ArrayList<String> notificacoes = new ArrayList<>();
	
	public Detective(User user) {
		this.user = user;
	}
	
	@Override
	public void run() {
		
		while(true){
			
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Detetive: " + user.getName());
			verificarDistanciaDoMarcador();
			updateUsuario();
			
			
		}
	}
	
	private void updateUsuario() {
		user.setCurrentPosition(MongoDAO.getInstance().getcurrentPosition(user.getEmail()));
	}
	
	private void verificarDistanciaDoMarcador() {
		
		Point currentPosition = user.getCurrentPosition();
		Point marker = user.getMarkerPosition();
		
		double distancia = distanciaEuclidiana(currentPosition, marker);
		System.out.println(distancia + "\n");
		if( distancia < 50.0) {
			notificacoes.add("Seu rastreado está a "+distancia+" do marcador ("+marker.getX()+", "+marker.getY()+")");
		}
	}
	
	public ArrayList<String> getNotificacoes() {
		return notificacoes;
	}
	
	private double distanciaEuclidiana(Point p1, Point p2) {
		return Math.sqrt(Math.pow((p1.getX() - p2.getX()), 2) + Math.pow((p1.getY() - p2.getY()),2));
	}

}
