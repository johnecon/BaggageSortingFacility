package control;

import lejos.Channel;
import lejos.EmptyChannelShoutSet;
import lejos.PhysicalInterface;
import lejos.SensorFilter;
import lejos.SyncManager;

public class SensorController extends Controller
{
	private static SensorFilter waitForBag = new WaitForBag();
	private static SensorFilter waitForOK = new WaitForOK();
	private static SensorFilter waitForClear = new WaitForClear();
	
	private EmptyChannelShoutSet empty = new EmptyChannelShoutSet(this);
	
	private Channel newBag;
	
	/*
	 * Private variables
	 */
	private int sensorNumber;
	private int newColor;

	public SensorController(Channel newBag, int sensorNumber)
	{
		super("Sensor " + sensorNumber);
		this.newBag = newBag;
		this.sensorNumber = sensorNumber;
	}
	
	public void run()
	{
		while(isRunning())
		{
			/*
			 * WaitForBag --> WaitForOKValue
			 */
			//System.out.println("Sensor" + sensorNumber + ": waiting for new bag");
			PhysicalInterface.waitForSensor(sensorNumber, waitForBag);
			
			/*
			 * WaitForOKValue --> GetOKValue
			 */
			sleepHere(203); // 3 mere pga unøjagtighed
			
			/*
			 * GetOKValue --> WaitForStopPos
			 */
			//System.out.println("Sensor" + sensorNumber + ": ready to get new color");
			int color = PhysicalInterface.waitForSensor(sensorNumber, waitForOK);
			newColor = color;
			
			/*
			 * WaitForStopPos --> WaitBeforeNoValue
			 */
			sleepHere(1400);
			
			synchronized (SyncManager.getSyncObject())
			{
				//System.out.println("Sensor" + sensorNumber + ": giving color to bag controller");
				newBag.shout(empty);
				waitHere();
			}
			
			/*
			 * WaitBeforeNoValue --> WaitForBag
			 */
			PhysicalInterface.waitForSensor(sensorNumber, waitForClear);
		}
	}

	public int getBagColor() {
		return newColor;
	}

	public int getBeltNum() {
		return sensorNumber;
	}
}

class WaitForBag extends SensorFilter
{
	public boolean checkSensorValue(int sensorValue)
	{
		sensedColor = UNKNOWN;
		
		return sensorValue <= 70;
	}
}

class WaitForOK extends SensorFilter
{
	public boolean checkSensorValue(int sensorValue)
	{
		if(50 <= sensorValue && sensorValue <= 60)
		{
			sensedColor = YELLOW;
			return true;
		}
		else if(35 <= sensorValue && sensorValue <= 45)
		{
			sensedColor = BLACK;
			return true;
		}

		return false;
	}
}

class WaitForClear extends SensorFilter
{
	public boolean checkSensorValue(int sensorValue)
	{
		sensedColor = CLEAR;
		
		return sensorValue > 70;
	}
}
