package models;

public class ClientSignUpListener extends Listener{
	
	public static final int PORTA = Main.CLIENT_SIGNUP_LISTENER_PORT;

	public ClientSignUpListener() {
		super(PORTA);
	}

}
