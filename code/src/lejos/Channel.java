package lejos;

import java.util.Vector;

public class Channel
{
	private Vector shoutingObjects;
	private Vector askingObjects;
	
	private String name;
	
	public Channel(String name)
	{
		this.name = name;
		
		shoutingObjects = new Vector();
		askingObjects = new Vector();
	}
	
	public void shout(ChannelShoutSet shouter)
	{	
		if(askingObjects.size() != 0)
		{
			ChannelAskGet asker = (ChannelAskGet)askingObjects.elementAt(0);
			askingObjects.removeElementAt(0);
			
			//System.out.print(name + " ");
			SyncManager.addChannelAllowed(asker, shouter);
		}
		else
		{
			shoutingObjects.addElement(shouter);
		}
	}
	
	public void ask(ChannelAskGet asker)
	{	
		if(shoutingObjects.size() != 0)
		{
			ChannelShoutSet shouter = (ChannelShoutSet)shoutingObjects.elementAt(0);
			shoutingObjects.removeElementAt(0);
			
			//System.out.print(name + " ");
			SyncManager.addChannelAllowed(asker, shouter);
		}
		else
		{
			askingObjects.addElement(asker);
		}
	}

	public void removeShout(ChannelShoutSet removeSatisfy) {
		shoutingObjects.removeElement(removeSatisfy);
	}
}