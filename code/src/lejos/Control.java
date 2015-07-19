package lejos;

import josx.platform.rcx.Button;
import control.BagController;
import control.DistController;
import control.FeedStarter;
import control.SensorController;

public class Control {

	public static final int NUMBEROFBAGCONTROLLERS = 4;

	public static void main(String[] args)
	{
		Channel newBag = new Channel("newBag");
		Channel distFree = new Channel("distFree");
		Channel requestDist = new Channel("requestDist");
		
		PhysicalInterface.setUpPhysical();
		
		for(int i = 0; i < NUMBEROFBAGCONTROLLERS; i++)
		{
			new BagController("BagC " + i, newBag, requestDist, distFree).start();
		}
		
		new DistController(requestDist, distFree).start();

		for(int i = 0; i < 2; i++)
		{
			new SensorController(newBag, i).start();
		}
		
		/*
		 * Maybe the feedStarter should wait for a signal from sensorController
		 * so that they are ready to listen before the motors are started.
		 */
		for(int i = 0; i < 2; i++)
		{
			new FeedStarter(i).start();
		}
		
	    try
	    {
	    	Button.RUN.waitForPressAndRelease();
	    } catch (Exception e) { }
	    
	    System.exit(0);
	}
}