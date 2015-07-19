package lejos;

import bagsortsim.BagSortSim;

/*
 * Simulates the physical system to test the bag control system.
 */
public class SimStarter
{
	public static void main(String[] args)
	{
		BagSortSim sim = BagSortSim.getSimulator();
		sim.start();
		
		//new BagSender(sim).start(); 
		
		Control.main(new String[]{});
	}
}

/*
 * Sends bags randomly.
 */
class BagSender extends Thread {
	
	BagSortSim sim;
	
	public BagSender (BagSortSim sim) {
		this.sim = sim;
		sim.setFeedCheck(true);
	}
	
	public void run() {
		try {
			sleep((int) (Math.random()*10000));
			while (true) {
				sleep((int) (Math.random()*2000));
				int counter = (Math.random() < 0.5 ? 1 : 2);
				int color   = (Math.random() < 0.5 ? BagSortSim.YELLOW  : BagSortSim.BLACK);
				sim.checkin(counter,color);
			}
		} catch (InterruptedException e) {}
	}
	
}


