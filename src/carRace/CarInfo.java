package carRace;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
/**
 * This is a Class to store player's global information.
 * 
 * @author Yikai Gong
 */
public class CarInfo{
	
	private float position[];
	private float direction;
	private String username;
	private int userstate=0;        //0:waiting, 1:ready, 2:allow start, 3:has start, 4:has left
	private int skinNum=0;
	private int currentLap=0;
	private int rank=0;
    private Shape shape;
    private boolean isCollide =false;
    private long connectionTime =0; //this is for server to detect player's disconnection
    private long lastConnectionTime=-1;

	public CarInfo() {
		position = new float[]{0,0};
		direction = 0;
		shape= new Rectangle(0,0,20,40);
	}
	
	public CarInfo(float[] position, float direction){
		this.position = position;
		this.direction = direction;
		shape= new Rectangle(0,0,20,40);
	}
	
	//set
	public void setPosition(float[] position, float direction){
		this.position=position;
		this.direction=direction;
	}
	public void setPosition(float x, float y){
		position[0]=x;
		position[1]=y;
	}
	public void setDirection(float direction){
		this.direction= direction;	
	}
	public void setUsername(String name){
		username = name;
	}
	public void setUserstate(int i){
		userstate = i;
	}
	public void setSkinNum(int i){
		skinNum = i;
	}
	public void setCurrentLap(boolean isFinishedOne){
		if(isFinishedOne)
			currentLap++;
		else
			currentLap--;
	}
	public void setCurrentLap(int i){
		currentLap=i;
	}
	public void setRank(int i){
		rank=i;
	}
	public void setCollide(boolean tf){
		isCollide=tf;
	}
	public void setConnectionTime(long time){
		connectionTime=time;
	}
	public void updateConnectionTime(){
		lastConnectionTime=connectionTime;
	}


    //get
	public float[] getPosition(){
		return position;
	}
	public float getDirection(){
		return direction;
	}
	public String getUsername(){
		return username;
	}
	public int getUserstate(){
		return userstate;
	}
	public int getSkinNum(){
		return skinNum;
	}
	public int getCurrentLap(){
		return currentLap;
	}
	public int getRank(){
		return rank;
	}
	public Shape getShape(){
	    Shape t = shape;
	    t= t.transform(Transform.createRotateTransform((float) Math.toRadians(direction),t.getCenterX(),t.getCenterY()));
	    t.setLocation(position[0], position[1]);
	    return t;
	}
	public boolean isCollide(){
		return isCollide;
	}
	public long getConnectionTime(){
		return connectionTime;
	}
	public long getLastConnectionTime(){
		return lastConnectionTime;
	}
}
