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
		System.out.println("Detetive: " + user.getEmail());
		while(true){
			
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			verificarDistanciaDoMarcador();
			updateUsuario();
		}
	}
	
	private void updateUsuario() {
		user = MongoDAO.getInstance().getUser(user.getEmail());
	}
	
	private void verificarDistanciaDoMarcador() {
		
		Point currentPosition = user.getCurrentPosition();
		Point marker = user.getMarkerPosition();
		
		double distancia = distanciaEuclidiana(currentPosition, marker);
		
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
