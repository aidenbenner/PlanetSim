package gravity;

import java.awt.Graphics;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JFrame;

public class Universe {
	

	public static void main(String args[]) throws InterruptedException{
		//get input from user
		Scanner s = new Scanner(System.in);
		
		System.out.println("Input the number of small bodies to generate : ");
		int randSBod = s.nextInt();

		System.out.println("Input the  number of medium bodies to generate : ");
		int randMBod = s.nextInt();

		System.out.println("Input the number of large bodies to generate : ");
		int randLBod = s.nextInt();
		//ARRAY array for holding the total entities
		Entity[] bodies = new Entity[randSBod + randMBod + randLBod];
		
		System.out.println("Input the value of the gravitational constant");
		Gravity.GRAV_CONST = s.nextDouble();
		
		System.out.println("Add a central body? (Y or N))");
		String input = s.next();
		//central body
		int startBod = 0;
		//deciscion making, if else checking whether the user wants a central body
		if(input.equalsIgnoreCase("Y")){
			bodies[0]= new Entity(0,0,5000,0,0,0,true); //central body
			startBod = 1;
		}
		//loop statements to generate different sizes of bodies
		for(int i = startBod; i<randSBod; i++){
			//bodies[i] = createOrbitingBody(bodies[0],75,50,i);//new Entity(50,0,10, i,0,getOrbitalVelocity(50,10,50));
			bodies[i] = createRandomBody(500,500,i,20,5,2);
		}
		for(int i = randSBod-1; i<randMBod + randSBod; i++){
			//bodies[i] = createOrbitingBody(bodies[0],1000,500,i);//new Entity(50,0,10, i,0,getOrbitalVelocity(50,10,50));
			bodies[i] = createRandomBody(200,200,i,1000,100,1);
		}
		for(int i = randMBod + randSBod -1; i < bodies.length; i++){
			//bodies[i] = createOrbitingBody(bodies[0],5000,4000,i);//new Entity(50,0,10, i,0,getOrbitalVelocity(50,10,50));
			bodies[i] = createRandomBody(200,200,i,3000,1000,1);
		}

		//loops to link all the bodies
		//link the bodies
		for(int i = 0; i<bodies.length; i++){
			bodies[i].setLinkedBodies(bodies);
		}
		

		//gui initialization
		JFrame jf = new JFrame();
		jf.setSize(1280, 720);
		jf.setVisible(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Painter p = new Painter();
		//send the bodies to the painter
		p.setPaintBodies(bodies);
		jf.add(p);
		//loop for 5000 ticks
		for(int i = 0; i<5000; i++){
		Thread.sleep(20);
			//tick each for for loop
			for(int j = 0; j<bodies.length; j++){
				bodies[j].tick();
			}
			//give the bodies back to the painter so they can be outputted
			p.setPaintBodies(bodies);
		}
	}
	
	//method to create a rando/m entity
	static Entity createRandomBody(int xLim, int yLim, int id, int massLim,int massMin, int velLim){
		Random r = new Random();
		return new Entity(r.nextInt(xLim),r.nextInt(yLim),r.nextInt(massLim)+massMin, id, r.nextInt(velLim),r.nextInt(velLim));
	}
	//method to create a random orbiting entity using kepler's laws
	static Entity createOrbitingBody(Entity o, int radLim, int radMin, int id){
		Random r = new Random();
		double orbRad = r.nextInt(radLim+radMin)-radMin + 1;
		double orbPhase = r.nextDouble() * 2*Math.PI;
		if(orbPhase == 0 || orbPhase == 2*Math.PI){
			orbPhase = Math.PI;
		}
		double mass = r.nextInt(20)+10;
		double orbx = orbRad * Math.cos(orbPhase);

		double orby = orbRad * Math.sin(orbPhase);
		double vel = Math.sqrt((o.mass * Gravity.GRAV_CONST) / orbRad);
		double velx = -vel * Math.sin(orbPhase);
		double vely = vel * Math.cos(orbPhase);
		return new Entity(orbx,orby,mass,id,velx,vely);
		
	}
	public static double getOrbitalVelocity(double M1,double M2, double R){
		return Math.sqrt((Gravity.GRAV_CONST * (M1 + M2)) / R);
	}
	
}
