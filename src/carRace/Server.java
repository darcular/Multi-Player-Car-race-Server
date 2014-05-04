package carRace;

import java.io.FileInputStream;
import java.io.InputStream;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Properties;

/**
 * This is the 'main' Class of carRace Server
 * 
 * @author Yikai Gong
 */
public class Server {
	private static GameRoom room;
	private static Properties prop;
	public static String ip;
	public static void main(String[] args)
	{
		try
		{
			Registry reg = LocateRegistry.createRegistry(1099);	
			InputStream is = new FileInputStream("Config.properties");
			prop = new Properties();
			prop.load(is);
			ip = prop.getProperty("ip");
			//create a game room, register it and start a thread for it.
			room = new GameRoom();
			Naming.rebind ("rmi://"+ip+"/room", room);
			Thread thread = new Thread(room);
			thread.start();
			System.out.println ("Car Server is ready!");
		}
		catch(Exception e)
		{
			e.getStackTrace();
			e.printStackTrace();
		}
	}				
}
