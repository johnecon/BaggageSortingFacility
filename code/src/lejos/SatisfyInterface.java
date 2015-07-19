package lejos;

import control.Controller;

public abstract class SatisfyInterface extends WaitElement {
	
	public SatisfyInterface(Controller c) {
		super(c);
		// TODO Auto-generated constructor stub
	}

	public abstract boolean satisfied();
}
