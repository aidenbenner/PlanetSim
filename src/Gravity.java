package gravity.src;

import java.util.HashMap;
import java.util.Map;

public class Gravity {
	static double GRAV_CONST = 0.5;//6.673e-11; //6.673×10−11 N
	public static HashMap<EntPair,Vector> gravTable = new HashMap<EntPair,Vector>();
	public static Vector getForce(Entity a, Entity b){
		//memotable to save computation time 
		if(gravTable.containsKey(new EntPair(a,b))){
			return gravTable.get(new EntPair(a,b));
		}
		//newton's law of gravitation to calculat the magnitude
		double result = (GRAV_CONST * a.mass * b.mass)/(Math.pow(getDistance(a,b),2));
		//if the locations are exactly the same sometimes we can get NaN returned as the magnitude make sure we check for that
		if(Double.isNaN(result)){
			result = 30;
		}
		//direction of the accelerations
		Vector v = new Vector();
		//max speed for the vectors
		int limit = 20;
		if(Math.abs(result) > limit){
			result = result > 0 ? limit : limit * -1;
		}
		v.magnitude = result;
		v.direction = getGravDirection(a,b);
		//if cords are the same sometimes we get NaN
		if(Double.isNaN(v.direction)){
			v.direction = 10;
			v.magnitude = 1;
		}
		//find the quadrant
		v.quadrant = getQuadrant(v.direction);
		//save to memotable
		gravTable.put(new EntPair(a,b), v);
		Vector reversed = new Vector();
		reversed.magnitude = v.magnitude;
		reversed.direction = (v.direction + 180) % 360; //flip direction and memo the reciprocal
		//reversed.direction = reversed.direction + 180)%360;
		gravTable.put(new EntPair(b,a), reversed);
		return v;
	}
	
	private static int getQuadrant(double theta) {
		
		if(theta < 90){
			return 1;
		}
		if(theta > 90){
			if(theta > 180){
				if(theta > 270){
					return 4;
				}
				return 3;
			}
			return 2;
		}
		return 2;

	}

	public static double getGravDirection(Entity a, Entity b){
		//x2 - x1 = delta X
		//y2 - y1 = delta Y
		double DX = b.x - a.x;
		double DY = b.y - a.y;
		double angle = Math.toDegrees(Math.atan2(DY,DX));
		//find quadrant
		if(angle < 0){
	        angle += 360;
	    }
		
		
		return  angle;

		
	}
	
	public static double getQuadrant(double x, double y){
		if(y > 0){
			//either quadrant 1 or 2
			if( x > 0){
				return 1;
			}
			else{
				return 2;
			}
		}
		else{
			if( x < 0){
				return 3;
			}
			else{
				return 4;
			}
		}
	}
	
	public static double getDistance(Entity a, Entity b){
		double DX = a.x - b.x;
		double DY = a.y - b.y;
		// c^2 = a^2 + b^2
		return Math.sqrt((DX * DX) + (DY * DY));
	}
	
}


