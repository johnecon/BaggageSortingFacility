package team05assignment02;

import java.lang.System;

import josx.platform.rcx.*;

public class MultiSort {

	static final int BELT_SPEED = 3;
	static final int LEFT = 1;
	static final int RIGHT = 2;

	public static void main (String[] arg) {

		TimerMonitor timerMonitor = new TimerMonitor();
		Shared shared = new Shared();
		ThreadTimer threadTimer = new ThreadTimer(timerMonitor, shared);
		threadTimer.start();

		Motor.A.setPower(BELT_SPEED);  Motor.C.forward();
		Motor.B.setPower(BELT_SPEED);
		Motor.C.setPower(BELT_SPEED);
		shared.setDistDirA(true);

		Thread fL = new FeedBeltController(timerMonitor, shared);
		((FeedBeltController) fL).setBelt(LEFT);
		fL.start();
		Thread fR = new FeedBeltController(timerMonitor, shared);
		((FeedBeltController) fR).setBelt(RIGHT);
		fR.start();

		try{ Button.RUN.waitForPressAndRelease();} catch (Exception e) {}
		System.exit(0);
	}
}




