package team05assignment02;

public class TimerMonitor {
	private boolean startTimer = false;
	private int delay;

	public void waitStartTimer() {
		synchronized (this) {
			while (!startTimer) {
				try {
					wait();
				} catch (InterruptedException e) {
//					LCD.showNumber(99); LCD.refresh();
				}
			}
			startTimer = false;
		}
	}

	public void startTimer(int delay) {
		synchronized (this) {
			this.delay = delay;
			startTimer = true;
			notifyAll();
		}
	}

	public int getDelay()
	{
		return this.delay;
	}
}