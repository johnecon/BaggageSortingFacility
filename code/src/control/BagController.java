package control;

import lejos.ChannelAskGet;
import lejos.Channel;
import lejos.ChannelShoutSet;
import lejos.EmptyChannelShoutSet;
import lejos.PhysicalInterface;
import lejos.SatisfyInterface;
import lejos.SatisfyManager;
import lejos.SyncManager;
import lejos.Variable;
import lejos.WaitElement;


public class BagController extends Controller
{	
	private static Variable nextBelt = new Variable(0);
	private static Variable numWait = new Variable(0);
	private static Variable collision = new Variable(0);
	private static Variable iCount = new Variable(0);
	
	private ChannelAskGet setIdent = new SetIdent(this);
	
	private ChannelShoutSet empty = new EmptyChannelShoutSet(this);
	private ChannelShoutSet removeSatisfy = new RemoveSatisfy(this);
	
	private SatisfyInterface firstQ = new FirstQ(this);
	private SatisfyInterface secondQ = new SecondQ(this, removeSatisfy);	
	
	private Channel newBag;
	private Channel requestDist;
	private Channel distFree;
	
	/*
	 * Private variables
	 */
	private int color;
	private int beltnumber;

	private String name;
	
	public BagController(String name, Channel newBag, Channel requestDist, Channel distFree)
	{
		super(name);
		
		this.name = name;
		
		this.newBag = newBag;
		this.requestDist = requestDist;
		this.distFree = distFree;
		
		removeSatisfy.setRemove(secondQ);
	}
	
	public void run()
	{
		while(isRunning())
		{
			boolean shorttrip;
			
			synchronized (SyncManager.getSyncObject())
			{
				/*
				 * WaitingForBag --> WaitingForDist
				 */
				//System.out.println(this + " BagC: waiting for new bag");
				newBag.ask(setIdent);
				waitHere();
				numWait.incr();
				PhysicalInterface.setFeedMotor(beltnumber, 0);
				// doneWaiting is not needed in java implementation.
				
				
				/*
				 * WaitingForDist --> FirstPosition
				 */
				SatisfyManager.addSatisfy(firstQ);
				//System.out.println(this + " Bag color: " + color + ", belt: " + beltnumber + " - waiting in line");
				waitHere();
				nextBelt.set(1 - beltnumber);
				
				/*
				 * FirstPosition --> BeingDelivered (and on)
				 */
				//System.out.println(this + " Bag color: " + color + ", belt: " + beltnumber + " - First in line!");
				requestDist.shout(removeSatisfy);
				SatisfyManager.addSatisfy(secondQ);
				WaitElement res = waitHere();
				
				// Hack...
				if(res == secondQ)
				{
					requestDist.removeShout(removeSatisfy);
				}
				
				iCount.incr();
				numWait.decr();
				PhysicalInterface.setFeedMotor(beltnumber, 1);
				
				if(color == beltnumber)
				{
					/*
					 * ShortTrip
					 */
					//System.out.println(this + " Bag color: " + color + ", belt: " + beltnumber + " - Short trip!");
					shorttrip = true;
				}
				else // color != beltnumber
				{
					/*
					 * LongTrip
					 */
					//System.out.println(this + " Bag color: " + color + ", belt: " + beltnumber + " - Long trip!");
					shorttrip = false;
				}
			}
			
			if(shorttrip)
			{
				sleepHere(5806); // 3 milliseconds unøjagtighed.
			}
			else
			{
				sleepHere(1994); // 20   3 ms unøjagtighed
				
				synchronized (SyncManager.getSyncObject()) {
					collision.incr();
				}
				
				sleepHere(6106); // 81
				
				synchronized (SyncManager.getSyncObject()) {
					collision.decr();
				}
				
				sleepHere(2906); // 110
			}
			
			synchronized (SyncManager.getSyncObject()) {
				iCount.decr();
				
				if(iCount.getValue() == 0)
				{
					//System.out.println("Bag color: " + color + ", belt: " + beltnumber + " - I'm the last one!");
					distFree.shout(empty);
					waitHere();
				}
				
				//System.out.println("Bag color: " + color + ", belt: " + beltnumber + " - is done!");
				//System.out.println("===============================================================");
			
			}	
		}
	}

	public int getDistColor() {
		return color;
	}
	
	public String toString()
	{
		return name + " - controlling " + color + " " + beltnumber;
	}
	
	private class FirstQ extends SatisfyInterface
	{
		public FirstQ(Controller c) {
			super(c);
		}

		public boolean satisfied() {
			return (nextBelt.getValue() == beltnumber) || (numWait.getValue() == 1);
		}

		public void update() {
			numWait.incr();
			PhysicalInterface.setFeedMotor(beltnumber, 0);
		}
	}
	
	private class SecondQ extends SatisfyInterface
	{
		public SecondQ(Controller c, WaitElement empty) {
			super(c);
			
			toRemove = empty;
		}

		public boolean satisfied() {
			return iCount.getValue() != 0 &&
				   PhysicalInterface.getDistDirection() == (color+1) && 
				   (collision.getValue() == 0 || color != beltnumber);
		}
	}
	
	private class SetIdent extends ChannelAskGet
	{
		public SetIdent(Controller c) {
			super(c);
		}

		public void updateValues(Controller c) {
			SensorController s = ((SensorController)c);
			
			color = s.getBagColor();
			beltnumber = s.getBeltNum();
		}
	}
	
	private class RemoveSatisfy extends ChannelShoutSet
	{
		public RemoveSatisfy(Controller c) {
			super(c);
		}

		public void setValues() {
		}
	}
}