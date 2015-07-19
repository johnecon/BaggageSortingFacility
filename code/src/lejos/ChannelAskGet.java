package lejos;

import control.Controller;

public abstract class ChannelAskGet extends WaitElement
{
	public ChannelAskGet(Controller c) {
		super(c);
	}

	public abstract void updateValues(Controller c);
}
