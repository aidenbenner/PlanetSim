package gravity;

public class EntPair {

	public double ax;
	public double ay;
	public double bx;
	public double by;
	public double amass;
	public double bmass;
	
	
	public EntPair(Entity a, Entity b){
		ax = a.x;
		ay = a.y;
		bx = b.x;
		by = b.y;
		amass = a.mass;
		bmass = a.mass;
	}



}
