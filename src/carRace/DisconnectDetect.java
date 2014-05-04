package carRace;
/**
 * This is a thread used to do  Disconnect Detection.
 * In practice, it check if the player's time stamp has been changed in every 3 seconds 
 * 
 * @author Yikai Gong
 */
public class DisconnectDetect extends Thread{
	private GameRoom gameroom;
	public DisconnectDetect(GameRoom gameroom){
		super();
		this.gameroom=gameroom;
	}
	public synchronized void run(){
		while(true){
			try {
				gameroom.checkConnection();
				wait(3000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}		
	}
}
