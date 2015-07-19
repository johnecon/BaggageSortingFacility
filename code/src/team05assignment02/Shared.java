package team05assignment02;
import josx.platform.rcx.*;

public class Shared {
	private int waiting = 0;
	private boolean free = true;
	public int delay;

	private boolean distDirA;
	private boolean priorities[] = {true, true};
	private int timestamps[] = {0, 0};
	public boolean read(Sensor s) {
		synchronized (this) {
			return s.readValue() > FeedBeltController.BLACK;
		}
	}

	public void enter(Motor motor) {
		synchronized (this) {
			int index = (motor.getId() == 'A') ? 0 : 1;
			if (!free) {
				priorities[index] = (waiting == 0);
				motor.stop();
			}
			waiting++;
			while (!(free && priorities[index])) {						//while at least a bag is using Shared
	
				try {
					timestamps[index] = (int) System.currentTimeMillis();
					wait();
					if (waiting == 2) {
						priorities[0] = timestamps[0] < timestamps[1];
						priorities[1] = timestamps[1] <= timestamps[0];
					} else {
						priorities[index] = true;
					}
				} catch (InterruptedException e) {
				}
			}
			waiting--;
			free = false;					// good morning! you are using shared
			motor.forward();
		}
	}

	public void leave() {
		synchronized(this) {
			free = true;
			notifyAll();					// notify other waiting bags
		}
	}

	public void setDistDirA(boolean destA) {
		synchronized(this) {
			distDirA = destA;
		}
	}

	public boolean getDistDirA() {
		synchronized(this) {
			return distDirA;
		}
	}
}