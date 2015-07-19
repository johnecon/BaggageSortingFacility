package lejos;

public class Variable
{
	private int var;
	
	public Variable(int startValue)
	{
		this.var = startValue;
	}
	
	public void incr()
	{
		this.var++;
		
		SatisfyManager.newValueToCheck();
	}
	
	public void decr()
	{
		this.var--;
		
		SatisfyManager.newValueToCheck();
	}
	
	public void set(int newValue)
	{
		this.var = newValue;
		
		SatisfyManager.newValueToCheck();
	}

	public int getValue() {
		return var;
	}
}
