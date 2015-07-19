package control;

import lejos.ChannelAskGet;
import lejos.Channel;
import lejos.PhysicalInterface;
import lejos.SyncManager;

/*
 * Implements the ControlDist template
 */
public class DistController extends Controller
{	
	private Channel requestDist;
	private Channel distFree;
	
	private SetColor setColor = new SetColor(this);
	private Nothing nothing = new Nothing(this);
	
	/*
	 * Private variables
	 */
	private int color;
	
	public DistController(Channel requestDist, Channel distFree)
	{
		super("Dist");
		
		this.requestDist = requestDist;
		this.distFree = distFree;
	}
	
	public void run()
	{
		synchronized (SyncManager.getSyncObject())
		{
			while(isRunning())
			{
				/*
				 * WaitingForBags --> MovingBag
				 */
				//System.out.println("Dist: waiting for request");
				requestDist.ask(setColor);
				waitHere();
				//System.out.println("Dist: New direction: " + (1 + color));
				PhysicalInterface.setDistMotor(1 + color);
				
				/*
				 * MovingBag --> WaitingForBags
				 */
				//System.out.println("Dist: waiting for bags to get out aka. dist to be free");
				distFree.ask(nothing);
				waitHere();
				//System.out.println("Dist: dist is free!");
			}
		}
	}
	
	private class Nothing extends ChannelAskGet
	{
		public Nothing(Controller c) {
			super(c);
		}

		public void updateValues(Controller c) {

		}
	}
	
	private class SetColor extends ChannelAskGet
	{
		public SetColor(Controller c) {
			super(c);
		}

		public void updateValues(Controller con) {
			color = ((BagController)con).getDistColor();
		}
	}
}