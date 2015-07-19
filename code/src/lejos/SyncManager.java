package lejos;

import java.util.Vector;

import control.Controller;

public class SyncManager {
	
	private static Object syncObject = new Object();
	
	private static Vector allowed = new Vector();
	
	private static Vector satisfyAllowed = new Vector();
	
	/*
	 * Object o asks if it is allowed to go further
	 * 
	 * null if not
	 */
	public static WaitElement allowed(Controller o)
	{
		WaitElement toBeReturned = null;
		int waitelementIndex = allowed.indexOf(o);
		if(waitelementIndex != -1)
		{
			satisfyAllowed.clear();
			
			WaitElement returnobj = (WaitElement)allowed.elementAt(waitelementIndex);
			allowed.removeElementAt(waitelementIndex);
			
			if(returnobj.hasRemove())
			{
				//System.out.println("Removing satisfy which is obsolite");
				SatisfyManager.removeSatisfy((SatisfyInterface)returnobj.getRemove());
			}
			
			toBeReturned = returnobj;
			
			//System.out.println("Chan synced: chan:" + allowed + " sat:" + satisfyAllowed);
		}
		else
		{
			waitelementIndex = satisfyAllowed.indexOf(o);
			
			if(waitelementIndex != -1)
			{
				SatisfyInterface returnobj = (SatisfyInterface)satisfyAllowed.elementAt(waitelementIndex);
				
				satisfyAllowed.clear();
				if(returnobj.hasRemove())
				{
					//System.out.println("Removing chan which is obsolite");
					allowed.removeElement(returnobj.getRemove());
					
				}
				
				SatisfyManager.removeSatisfy(returnobj);
				
				toBeReturned = returnobj;
				
				//System.out.println("Sat allowed: chan:" + allowed + " sat: " + satisfyAllowed);
			}
			
			
		}
		
		
		
		return toBeReturned;
	}

	public static void addChannelAllowed(ChannelAskGet asker, ChannelShoutSet shouter)
	{
		/*
		 * Sync between shouter and asker needed.
		 */
		shouter.setValues();
		asker.updateValues(shouter.getController());
		
		allowed.addElement(asker);
		allowed.addElement(shouter);
		
		syncObject.notifyAll();
		
		//System.out.println("new chan added - Currently allowed: " + allowed);
	}
	
	public static void addSatisfyAllowed(Vector sat)
	{
		satisfyAllowed = sat;
		
		syncObject.notifyAll();
		
		//System.out.println("new sat added - Currently allowed: " + satisfyAllowed);
	}
	
	public static Object getSyncObject() {
		return syncObject;
	}
}
