package models;

import java.util.ArrayList;

import DTO.Point;
import DTO.User;

public class Detective implements Runnable {
	private User user;
	private ArrayList<String> notificacoes;
	public Detective(User user) {
		this.user = user;
	}
	@Override
	public void run() {
		while(true){
			verificarDistanciaDoMarcador();
			updateUsuario();
		}
	}
	private void updateUsuario() {
		//procura a informações desse usuário (ou puxa tudo de novo ou puxa só o marcador e posição)
	}
	private void verificarDistanciaDoMarcador() {
		Point currentPosition = user.getCurrentPosition();
		Point marker = user.getMarkerPosition();
		double distancia = distanciaEuclidiana(currentPosition, marker);
		if( distancia < 50.0) {
			notificacoes.add("Seu rastreado está a "+distancia+" do maracador ("+marker.getX()+", "+marker.getY()+")");
		}
	}
	public ArrayList<String> getNotificacoes() {
		return notificacoes;
	}
	private double distanciaEuclidiana(Point p1, Point p2) {
		return Math.sqrt(Math.pow((p1.getX() - p2.getX()), 2) + Math.pow((p1.getY() - p2.getY()),2));
	}

}
