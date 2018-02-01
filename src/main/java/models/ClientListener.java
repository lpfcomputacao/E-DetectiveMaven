package models;

public class ClientListener extends Listener{

	public static final int PORTA = Main.CLIENT_LISTENER_PORT;
	
	public ClientListener() {
		super(PORTA);
	}

}
