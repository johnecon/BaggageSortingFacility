package lejos;

import control.Controller;

public abstract class WaitElement
{
	private Controller c;
	
	protected WaitElement toRemove = null;
	
	public WaitElement(Controller c)
	{
		this.c = c;
	}
	
	public Controller getController() {
		return c;
	}

	public boolean equals(Object obj) {
		if(obj instanceof Controller)
		{
			return c.equals(obj);
		}
		else if(obj instanceof WaitElement)
		{
			WaitElement objE = (WaitElement)obj;
			return c.equals(objE.c);
		}
		
		return false;
	}

	public boolean hasRemove() {
		return toRemove == null ? false : true;
	}

	public WaitElement getRemove() {
		return toRemove;
	}
	
	public String toString()
	{
		return c.getRealName();
	}
}
