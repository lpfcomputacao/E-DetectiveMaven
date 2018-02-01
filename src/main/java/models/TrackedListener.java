package models;

public class TrackedListener extends Listener{

	public static final int PORTA = Main.TRACKED_LISTENER_PORT;
	
	public TrackedListener() {
		super(PORTA);
	}
}
