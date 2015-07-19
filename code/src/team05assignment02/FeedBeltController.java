package team05assignment02;

import josx.platform.rcx.Motor;
import josx.platform.rcx.Poll;
import josx.platform.rcx.Sensor;

class FeedBeltController extends Thread {

	static final int BLOCKED = 65;
	static final int YELLOW  = 60;
	static final int BLACK   = 45;
	static final int SHORTPATH   = 6000;
	static final int LONGPATH   = 11000;

	private int belt;
	private Sensor sensor;
	private Motor motor;
	private short sensorMask;
	private boolean destA;   // Required destination is A
	private TimerMonitor timerMonitor;
	private Shared shared;

	public FeedBeltController(TimerMonitor timerMonitor, Shared shared) {
		this.timerMonitor = timerMonitor;
		this.shared = shared;
	}

	public void setBelt(int belt) {
		this.belt = belt;
	}

	public int getBelt() {
		return belt;
	}

	public void run() {
		try {
			Poll e = new Poll();
			if (this.belt == MultiSort.LEFT) {
				sensor = Sensor.S1;
				sensorMask = Poll.SENSOR1_MASK;
				motor = Motor.A;
			} else {
				sensor = Sensor.S2;
				sensorMask = Poll.SENSOR2_MASK;
				motor = Motor.B;
			}
			sensor.activate();
			motor.forward();
			int delay;
			while (true) {
				// Await arrival of a bag
				while(sensor.readValue() > BLOCKED) { e.poll(sensorMask,0); } //Baggage arrived at sensor
				Thread.sleep(800); // wait for proper sensor value
				destA = sensor.readValue() > BLACK;   // determine destination

				Thread.sleep(2000); // advance sensor

				shared.enter(motor); // enter mutex area
				if (shared.getDistDirA() != destA) {  // Decide whether to reverse distribution belt direction or not
					Motor.C.reverseDirection();
					shared.setDistDirA(destA);
				}
				delay = (belt == MultiSort.LEFT && destA) ||
						(belt == MultiSort.RIGHT && !destA) ? SHORTPATH : LONGPATH;
				timerMonitor.startTimer(delay);
			}
		} catch (Exception e) { }
	}
}