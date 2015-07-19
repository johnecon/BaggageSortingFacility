package control;

import lejos.PhysicalInterface;

public class FeedStarter extends Controller
{
	private int beltnumber;
	
	public FeedStarter(int beltnumber)
	{
		super("Feed " + beltnumber);
		this.beltnumber = beltnumber;
	}
	
	public void run()
	{
		/*
		 * FeedStopped --> FeedStarted
		 */
		PhysicalInterface.setFeedMotor(beltnumber, 1);
	}
}
