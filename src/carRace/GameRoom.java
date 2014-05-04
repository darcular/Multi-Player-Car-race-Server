package carRace;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import org.newdawn.slick.geom.Shape;

/**
 * This is Game room class.
 * It maintain a list of player names and a hash map to manage their car information.
 * A 'run()' method is used to do judgment according to its current state. 
 * 
 * @author Yikai Gong
 */
public class GameRoom extends UnicastRemoteObject implements GameRoomInterface, Runnable {

	private int size = 0;
	private int roomState=0;
	private String roomName;
	private ArrayList<String> playerNameList = new ArrayList<String>();
	private ArrayList<float[]> startPosition = new ArrayList<float[]>();
	private HashMap<String, CarInfo> carMap = new HashMap<String, CarInfo>();
	private int lap=1;
	private int rank =1;
	private boolean isNew=true;
	
	//constructor
    protected GameRoom() throws RemoteException {
		super();
		startPosition.add(new float[]{-515+60,692});
		startPosition.add(new float[]{-567+60,736});
		startPosition.add(new float[]{-620+60,692});
		startPosition.add(new float[]{-674+60,736});
		startPosition.add(new float[]{-725+60,692});
		startPosition.add(new float[]{-779+60,736});
	}
    protected GameRoom(String roomName, int size) throws RemoteException {
		super();
		this.roomName =roomName;
		this.size = size;
		startPosition.add(new float[]{-515+60,692});
		startPosition.add(new float[]{-567+60,736});
		startPosition.add(new float[]{-620+60,692});
		startPosition.add(new float[]{-674+60,736});
		startPosition.add(new float[]{-725+60,692});
		startPosition.add(new float[]{-779+60,736});
	}
    public void inite(){
    	size = 0;
    	roomState=0;
    	roomName=null;
    	playerNameList = new ArrayList<String>();
    	carMap.clear();
    	lap=1;
    	rank =1;
    	isNew=true;
    	startPosition = new ArrayList<float[]>();
		startPosition.add(new float[]{-515+60,692});
		startPosition.add(new float[]{-567+60,736});
		startPosition.add(new float[]{-620+60,692});
		startPosition.add(new float[]{-674+60,736});
		startPosition.add(new float[]{-725+60,692});
		startPosition.add(new float[]{-779+60,736});
    }
	
	//set
	public boolean setSize(int newSize) throws RemoteException {
		if(playerNameList.size()==0&&roomState==0)
		{
			size=newSize;
			carMap = new HashMap<String, CarInfo>(); 
			playerNameList = new ArrayList<String>();
			return true;
		}
		else
			return false;	
	}
	public void setName(String name) throws RemoteException {
		this.roomName=name;
	}
	public String join(String playerName) throws RemoteException {
		if(playerName.equals(""))
			playerName="player";
		if(playerNameList.size()<size&&roomState==0)
		{
			for(int i=0;i<playerNameList.size();i++)
			{
				if(playerName.equals(playerNameList.get(i)))
					playerName = playerName+"*";
			}
			playerNameList.add(playerName);
			CarInfo player = new CarInfo();
			player.setUsername(playerName);
			carMap.put(playerName, player);
			return playerName;
		}
		else
			return null;
	}
	public void quit(String playerName) throws RemoteException {
		if(playerNameList.size()>0&&roomState==0)
		{
			int index = -1;
			for(int i=0;i<playerNameList.size();i++)
				if(playerName.equals(playerNameList.get(i)))
				{
					index = i;
					break;
				}
			if(index!=-1)
			{
				carMap.remove(playerName);
				playerNameList.remove(index);
			}		
		}
	}
	
	public void setUserState(String username, int state) throws RemoteException {
		CarInfo car = carMap.get(username);
		car.setUserstate(state);
	}	
	public void setUserPosition(String username, float x, float y)throws RemoteException{
		CarInfo car = carMap.get(username);
		car.setPosition(x, y);
	}
	public void setUserDirection(String username, float direction)throws RemoteException{
		CarInfo car = carMap.get(username);
		car.setDirection(direction);
	}
	public void setUserMapInfo(String username, float x, float y, float direction)throws RemoteException{
		CarInfo car = carMap.get(username);
		car.setPosition(x, y);
		car.setDirection(direction);
	}
	public void setUserSkinNum(String username, int i)throws RemoteException{
		CarInfo car = carMap.get(username);
		car.setSkinNum(i);
	}
	public void setUserCurrentLap(String username, boolean isFinishedOne)throws RemoteException{
		CarInfo car = carMap.get(username);
		car.setCurrentLap(isFinishedOne);
	}
	public void setUserCurrentLap(String username, int i)throws RemoteException{
		CarInfo car = carMap.get(username);
		car.setCurrentLap(i);
	}
	public void setLap(int i)throws RemoteException{
		lap=i;
	}
	public void setUserConnectionTime(String username, long time)throws RemoteException{
		CarInfo car = carMap.get(username);
		car.setConnectionTime(time);
	}
	//get
	public int getSize() throws RemoteException {
		return size;
	}
	public String getName() throws RemoteException {
		return roomName;
	}
	public ArrayList<String> getPlayerNameList()throws RemoteException{
		return playerNameList;
	}
	public ArrayList<String> getPlayerState()throws RemoteException{
		ArrayList<String> playerstates = new ArrayList<String>();
		for(int i=0; i<playerNameList.size();i++)
		{
			String playerstate = playerNameList.get(i)+","+carMap.get(playerNameList.get(i)).getUserstate();
			playerstates.add(playerstate);
		}
		return playerstates;
	}
	public int getUserState(String username) throws RemoteException {
		CarInfo car = carMap.get(username);
		return car.getUserstate();
	}
	public float[] getUserPosition(String username)throws RemoteException{
		CarInfo car = carMap.get(username);
		return car.getPosition();
	}
	public float getUserDirection(String username)throws RemoteException{
		CarInfo car = carMap.get(username);
		return car.getDirection();
	}
	public float[] getUserMapInfo(String username)throws RemoteException{
		CarInfo car = carMap.get(username);
		float[] mapInfo = new float[]{car.getPosition()[0],car.getPosition()[1],car.getDirection()};
		return mapInfo;
	}
	public int getUserSkinNum(String username) throws RemoteException {
		CarInfo car = carMap.get(username);
		return car.getSkinNum();
	}
	public int getUserCurrentLap(String username) throws RemoteException{
		CarInfo car = carMap.get(username);
		return car.getCurrentLap();
	}
	public int getLap() throws RemoteException{
		return lap;
	}
	public int getUserRank(String username) throws RemoteException{
		CarInfo car = carMap.get(username);
		return car.getRank();
	}
	public int getRoomState() throws RemoteException{
		return roomState;
	}
	
	public boolean getUserCollision(String username)throws RemoteException{
		CarInfo car = carMap.get(username);
		return car.isCollide();
	}
	
	//check if the time stamp has been changed
	public void checkConnection() throws Exception
	{	
		if(playerNameList.size()>0)
		{
			for(int i =0;i<playerNameList.size();i++)
			{
				CarInfo car = carMap.get(playerNameList.get(i));
				if(car.getConnectionTime()==car.getLastConnectionTime())
				{
					car.setUserstate(4);
					System.out.println("Mark up disconnection player: "+car.getUsername());
				}
				else
					car.updateConnectionTime();
			}
		}	
	}
	
	@Override
	public synchronized void run(){
		//start a thread used for Disconnection detecting
		DisconnectDetect detection = new DisconnectDetect(this);
		detection.start();
		System.out.println("Disconnection detecting start.");
		while(true)
		{
			try{
				if(roomState==0&&playerNameList.size()!=0) 
				{
					boolean start=true;
					for(int i =0; i<playerNameList.size();i++)
						if(carMap.get(playerNameList.get(i)).getUserstate()==0)
						{
							start=false;
							break;
						}
					if(size>playerNameList.size()||size==0)
						start=false;
					else if(start)
					{
						isNew=false;
						int order=0;
						for(int i =0; i<playerNameList.size();i++)
						{
							CarInfo car = carMap.get(playerNameList.get(i));
							car.setPosition(startPosition.get(order), -90);
							order++;
						}
						for(int i =0; i<playerNameList.size();i++)
							carMap.get(playerNameList.get(i)).setUserstate(2);
		         		roomState=1;   		
					}
				}
				else if(roomState==1)   //wait to ensure all users have entered the track
				{
					boolean start=true;
					for(int i =0; i<playerNameList.size();i++)
						if(carMap.get(playerNameList.get(i)).getUserstate()!=3)
						{
							start=false;
							break;
						}
					if(start)
						roomState=2;
				}
				else if(roomState==2)  //playing state, do game rules judgment
				{
					for(int i=0; i<playerNameList.size();i++)
					{
						CarInfo car = carMap.get(playerNameList.get(i));
						if(car.getCurrentLap()>=(lap+1)&&car.getRank()==0)
						{
							car.setRank(rank);
							rank++;
						}
					}
					//do collision detection
					boolean[] collisions = new boolean[playerNameList.size()];
					for(int i=0; i<collisions.length; i++)
						collisions[i]=false;
					for(int i=0; i<playerNameList.size()-1;i++)
					{
						CarInfo car1 = carMap.get(playerNameList.get(i));
						Shape shape1 = car1.getShape();		
						for(int j=i+1; j<playerNameList.size(); j++)
						{
							CarInfo car2 = carMap.get(playerNameList.get(j));
							Shape shape2 = car2.getShape();
							if(shape1.intersects(shape2))
							{
								collisions[i]=true;
								collisions[j]=true;
							}
						}
					}
					for(int i=0; i<playerNameList.size();i++)
					{
						CarInfo car = carMap.get(playerNameList.get(i));
						car.setCollide(collisions[i]);
					}			
				}
				
				//to remove disconnect player
				int index = -1;
				for(int i =0; i<playerNameList.size(); i++)
				{
					CarInfo car = carMap.get(playerNameList.get(i));
					if (car.getUserstate()==4)
					{
						index=i;
					}	
				}
				if(index!=-1)
				{
					carMap.remove(playerNameList.get(index));
					System.out.println("remove: "+playerNameList.get(index));
					playerNameList.remove(index);
				}
				//to reuse the game room if every one has left the game room
				if((!isNew)&&playerNameList.size()==0)
				{
					inite();
					System.out.println("GameRoom closed");
				}			
				this.wait(100);
			}
			catch (Exception e){e.getStackTrace();}			
		}			
	}
}
