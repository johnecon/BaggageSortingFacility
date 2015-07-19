package lejos;

public abstract class SensorFilter {
	
	public static final int BLACK = 0;
	public static final int YELLOW = 1;
	public static final int CLEAR = 2;
	public static final int UNKNOWN = 3;
	
	protected int sensedColor;
	
	public abstract boolean checkSensorValue(int sensorValue);
	
	public int getSensedColor()
	{
		return sensedColor;
	}
}
