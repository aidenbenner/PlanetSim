package gravity.src;

import java.awt.List;
import java.util.ArrayList;
import java.util.LinkedList;

public class Entity {
	double mass;
	int velocity; 
	double x;
	double y;
	int id;
	boolean stationary = false;
	double xVel = 0;
	double yVel = 0;
	double forces[] = new double[50]; //max entities = 50
	Vector[] gravVecs = new Vector[50];
	ArrayList<Vector> allForces = new ArrayList<Vector>();
	ArrayList<Point> pastLoco = new ArrayList<Point>();
	Entity[] linkedBodies;
	
	void setStationary(boolean stat){
		stationary = stat;
	}
	
	public void setLinkedBodies(Entity[] ent){
		linkedBodies = ent;
	}
	
	public Entity(int zx,int zy,double maxValue, int zid){
		x = zx;
		y = zy;
		mass = maxValue;
		id = zid;
	}
	public Entity(int zx,int zy,double maxValue, int zid, double iniXVel, double iniYVel){
		x = zx;
		y = zy;
		mass = maxValue;
		id = zid;
		xVel = iniXVel;
		yVel = iniYVel;
	}
	public Entity(int zx,int zy,double maxValue, int zid, double iniXVel, double iniYVel, boolean stat){
		x = zx;
		y = zy;
		mass = maxValue;
		id = zid;
		xVel = iniXVel;
		yVel = iniYVel;
		stationary = stat;
	}
	public Entity(int zx,int zy,int zm, Entity[] linkedB){
		x = zx;
		y = zy;
		mass = zm;
		linkedBodies = linkedB;
	}
	
	public Entity(double zx, double zy
			, double zmass, int zid, double zvelx,
			double zvely) {
		x = zx;
		y = zy;
		mass = zmass;
		id = zid;
		xVel = zvelx;
		yVel = zvely;
		// TODO Auto-generated constructor stub
	}

	public void addEffect(Entity n){
		allForces.add(Gravity.getForce(this,n));
	}
	
	public void addEffects(Entity[] n){
		for(int i = 0; i<n.length; i++){
			allForces.add(Gravity.getForce(this,n[i]));
		}
	}
	
	//Newton's law of gravitation, 
	public void addVector(Vector v){
	}
	
	public Vector getAccel(){
		Vector net = Vector.netForce(allForces);
		return new Vector((net.magnitude / mass), (net.direction));
	}
	

	public double getNextXPos(double time){
		//d = vi * t + (1/2)at^2 
		double result = xVel * time + (1/2) * this.getAccel().getXComp() * time * time;
		//System.out.println("Result : " + result  + " Accel Vector " + this.getAccel().getXComp());
		xVel += this.getAccel().getXComp() * time;
		return result;
	}
	public double getNextYPos(double time){
		//d = vi * t + (1/2)at^2 
		double result = yVel * time + (1/2) * this.getAccel().getYComp() * time * time;
		//System.out.println("Result Y: " + result  + " Accel Vector " + this.getAccel().getYComp());
		yVel += this.getAccel().getYComp() * time;
		return result;
	}
	void tick(){
		if(!stationary){
			
		allForces.clear();
		if(Double.isNaN(x) || Double.isNaN(y)){
			x = 0;
			y = 0;
		}
		if(Double.isNaN(xVel) || Double.isNaN(yVel)){
			xVel = 0;
			yVel = 0;
		}
		for(int i = 0; i<linkedBodies.length; i++){
			if(linkedBodies[i].id != id){
				Vector gravVec = Gravity.getForce(this, linkedBodies[i]);
				if(((gravVec.direction != Double.NaN) && gravVec.magnitude != Double.POSITIVE_INFINITY) && gravVec.magnitude != Double.NaN){
					allForces.add(gravVec);
				}
			}
		}
		
		x += getNextXPos(1);
		y += getNextYPos(1);
		pastLoco.add(new Point(x,y));
		if(pastLoco.size() > 50){
			pastLoco.remove(0);
		}
		if(Math.abs(x) > 640){
			x = x > 0 ? 640 : 640 * -1;
			xVel = -xVel/2;
			yVel = yVel/2;
		}
		if(Math.abs(y) > 360){
			y = y > 0 ? 360 : 360 * -1;
			xVel = xVel/2;
			yVel = -yVel/2;
		}
		}
	}
}
