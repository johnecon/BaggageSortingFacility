package team05assignment02;

import josx.platform.rcx.*;

public class ThreadTimer extends Thread {

	private TimerMonitor timerMonitor;
	private Shared shared;

	public ThreadTimer(TimerMonitor timerMonitor, Shared shared) {
		this.timerMonitor = timerMonitor;
		this.shared = shared;
	}

	public void run() {
		while (true) {
			timerMonitor.waitStartTimer();
			try {
				Thread.sleep(timerMonitor.getDelay());
			} catch (InterruptedException ie) {
				LCD.showNumber(98); LCD.refresh();
			}
			shared.leave();
		}
	}
}
