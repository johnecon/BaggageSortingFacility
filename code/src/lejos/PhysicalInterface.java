package lejos;

import josx.platform.rcx.Motor;
import josx.platform.rcx.Poll;
import josx.platform.rcx.Sensor;

public class PhysicalInterface
{
	private static final int BELT_SPEED = 3;
	 
	private static final Motor[] feedBelt = new Motor[]{Motor.A, Motor.B};
	private static final Motor distBelt = Motor.C;
	

	public static void setUpPhysical()
	{
		Motor.A.setPower(BELT_SPEED);
	    Motor.B.setPower(BELT_SPEED);
	    Motor.C.setPower(BELT_SPEED);
		
		Sensor.S1.activate();
		Sensor.S2.activate();
	}
	
	public static void setDistMotor(int direction)
	{
		if(direction == 0)
		{
			distBelt.stop();
		}
		else if(direction == 1)
		{
			distBelt.forward();
		}
		else if(direction == 2)
		{
			distBelt.backward();
		}
	}
	
	public static void setFeedMotor(int motorNum, int direction)
	{
		if(direction == 0)
		{
			feedBelt[motorNum].stop();
		}
		else if(direction == 1)
		{
			feedBelt[motorNum].forward();
		}
		else if(direction == 2)
		{
			feedBelt[motorNum].backward();
		}
	}
	
	public static int waitForSensor(int sensorNumber, SensorFilter sf)
	{
		short mask;
		Sensor sensor;
		
		if(sensorNumber == 0)
		{
			mask = Poll.SENSOR1_MASK;
			sensor = Sensor.S1;
		}
		else if(sensorNumber == 1)
		{
			mask = Poll.SENSOR2_MASK;
			sensor = Sensor.S2;
		}
		else
		{
			throw new IllegalArgumentException();
		}
		
		while(!sf.checkSensorValue(sensor.readValue()))
		{ 
			
			while(true)
			{
				try {
					Poll e = new Poll();
					e.poll(mask, 0);
					break;
				} catch (InterruptedException e) {
				}
			}
		}

		return sf.getSensedColor();
	}

	public static int getDistDirection() {

		if(distBelt.isForward())
		{
			return 1;
		}
		else if(distBelt.isBackward())
		{
			return 2;
		}
		
		return 0;
	}
}