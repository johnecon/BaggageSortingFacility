package control;

import lejos.SyncManager;
import lejos.WaitElement;

public abstract class Controller extends Thread {
	
	private boolean running;
	private String name;
	
	public Controller(String name)
	{
		this.name = name;
		running = true;
	}

	public boolean isRunning() {
		return running;
	}

	public void stopRunning() {
		this.running = false;
	}
	
	/*
	 * Dont interrupt this one, then it will sleep again for a whole
	 * new round.
	 */
	public void sleepHere(int milliseconds)
	{
		while(true)
		{
			try {
				sleep(milliseconds);
				break;
			} catch (InterruptedException e) {
			}
		}
	}
	
	public WaitElement waitHere()
	{
		while(true)
		{
			WaitElement element = SyncManager.allowed(this);
			if(element != null)
			{
				return element;
			}
			
			try {
				SyncManager.getSyncObject().wait();
			} catch (InterruptedException e) {}
		}
	}
	
	public boolean equals(Object o)
	{
		if(o instanceof Controller)
		{
			return this == o;
		}
		else if(o instanceof WaitElement)
		{
			WaitElement w = (WaitElement)o;
			return this == w.getController();
		}
		
		return false;
	}

	public String getRealName() {
		return name;
	}
}