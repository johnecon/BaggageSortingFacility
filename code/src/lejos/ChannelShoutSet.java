package lejos;

import control.Controller;

public abstract class ChannelShoutSet extends WaitElement
{
	public ChannelShoutSet(Controller c) {
		super(c);
	}

	public abstract void setValues();

	public void setRemove(SatisfyInterface secondQ) {
		toRemove = secondQ;
	}
}
