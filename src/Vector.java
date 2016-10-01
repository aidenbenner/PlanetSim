package gravity.src;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Vector {
	double magnitude = 0;
	double direction = 0;
	int quadrant = 1;
	
	public Vector(double mz, double direction2){
		magnitude = mz;
		direction = direction2;
	}
	public Vector(){}
	//add vector method by adding the compponents and finding the direction
	public static Vector addVection(Vector a, Vector b){
		Vector n = new Vector();
		double netX = a.getXComp() + b.getXComp();
		double netY = a.getYComp() + b.getYComp();
		n.magnitude = Math.sqrt(netX * netX + netY * netY);
		n.direction = Math.toDegrees(Math.atan2(netY, netX));
		return n;
	}
	void checkQuadrant(){
		//add the components of the vector
		if(direction > 180){
			if(direction > 270){
				quadrant = 4;
			}
			else{
				quadrant = 3;
			}
		}
		else{
			if(direction > 90){
				quadrant = 2;
			}
			else{
				quadrant = 1;
			}
		}
	}
	//return the x component of the vector
	double getXComp(){
		checkQuadrant();

		return Math.cos(Math.toRadians(direction)) * magnitude;
	}
	//return the y component of the vector
	double getYComp(){
		checkQuadrant();
		return Math.sin(Math.toRadians(direction)) * magnitude;
	}
	//use an arraylist to add up and return a list of vectors
	public static Vector netForce(ArrayList<Vector> v){
		Vector net = new Vector();
		for(Vector vec:v){
			net = Vector.addVection(net, vec);
		}
		return net;
	}
}
