package lejos;

import java.util.Vector;

public class SatisfyManager
{
	private static Vector toSatisfy = new Vector();
	
	public static void newValueToCheck()
	{
		Object[] os = toSatisfy.toArray();
		Vector sat = new Vector();
		
		for(int i = 0; i < os.length; i++)
		{
			SatisfyInterface si = (SatisfyInterface)os[i];
			
			if(si.satisfied())
			{
				sat.addElement(si);
			}
		}
		
		if(!sat.isEmpty())
		{
			SyncManager.addSatisfyAllowed(sat);
		}
	}
	
	public static void addSatisfy(SatisfyInterface s) {
		toSatisfy.addElement(s);
		
		newValueToCheck();
	}	
	
	public static void removeSatisfy(SatisfyInterface element) {
		toSatisfy.removeElement(element);
	}
}
