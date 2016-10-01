package gravity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

//JPanel needed to paint
public class Painter extends JPanel {
	
	Entity[] bodies;
	//method we'll use to pass in the bodies to draw
	public void setPaintBodies(Entity[] n){
		bodies = n;
		this.repaint();
	}
	//this toggles the saving of datpoints on in the universe class to output the trails
	boolean trails = true;
	//this is what is being looped
	public void paintComponent(Graphics g){
		super.setOpaque(true);
		setBackground(Color.black);
		super.paintComponent(g);
		g.setColor(Color.GRAY);
		//to offset where the grid starts to we can test every quadrant
		g.drawLine(0, 720/2, 1280, 720/2);
		g.drawLine(1280/2, 0, 1280/2, 720);
		g.setColor(Color.blue);
		for(Entity e : bodies){
			//if the mass is greater than a certain threshold scale it down and make it red
			if(e.mass > 300){
				g.setColor(Color.RED);
				drawCenteredCircle(g, 640+((int)e.x), 360+((int)e.y * -1),(int)(2+(int)e.mass)/(100) );
				g.setColor(Color.BLUE);
			}
			else{
				//draw the mass as a blue centered circle
				drawCenteredCircle(g, 640+((int)e.x), 360+((int)e.y * -1),(2+(int)e.mass)/2 );
				if(trails){
				//if the trails are enabled output the past history of points saved by each body
				for(int k = 0; k<e.pastLoco.size()-1; k++){
					g.drawLine(640+e.pastLoco.get(k).x, 360+(e.pastLoco.get(k).y * -1), 640+e.pastLoco.get(k+1).x, 360+(e.pastLoco.get(k+1).y * -1));
				}
				}
			}
		}
	}
	//draw centered circle by placing the circle at x-(r/2) and y - (r/2)
	public void drawCenteredCircle(Graphics g, int x, int y, double r) {
		  x = (int) (x-(r/2));
		  y = (int) (y-(r/2));
		  g.fillOval(x,y,(int)r,(int)r);
	}
}
