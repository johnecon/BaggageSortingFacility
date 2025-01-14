package josx.util;

/**
 * Timer object, with some similar functionality to java.Swing.Timer.
 * 
 * @author <a href="mailto:rvbijl39<at>calvin<dot>edu">Ryan VanderBijl</a> 
 */
public class Timer
{
    private TimerListener myListener;
    private Thread        myThread  ;
    private int           delay     ;
    private boolean       running   ;

    /**
     * Create a Timer object. Every theDelay milliseconds
     * the el.timedOut() function is called. You may
     * change the delay with setDelay(int). You need
     * to call start() explicitly.
     */
    public Timer(int theDelay, TimerListener el) 
    {
	running    = false;
	delay      = theDelay;
	myListener = el;

	myThread   = new Thread() {
	    public void run() {
		int     d;
		while(true) {
		   synchronized(Timer.this)
		   {
		       while (!running) {
			   try {
				   System.out.println("waiting...");
			       wait();
		           } catch (InterruptedException e) {};
                       }
		       d = delay;
		   }
		   try 
		       {
			   System.out.println("sleeping for: " +d);
			   Thread.sleep (d);
		       } 
		   catch (InterruptedException e) 
		       {
			   // ignore      
		       }
		       myListener.timedOut();
		}
	    }
	};
    }

    /**
     * access how man milliseconds between timedOut() messages.
     */
    public synchronized int getDelay() {
	return delay;
    }
    /**
     * Change the delay between timedOut messages. Safe to call
     * while start()ed. Time in milli-seconds.
     */
    public synchronized void setDelay(int newDelay) {
    	System.out.println("Setting Delay: " + newDelay);
    	delay = newDelay;
    }

    /**
     * Stops the timer. timedOut() messages are not sent.
     */
    public synchronized void stop() {
	running = false;
    }

    /**
     * Starts the timer, telling it to send timeOut() methods
     * to the TimerListener.
     */
    public synchronized void start() {
	running = true;
	if (!myThread.isAlive())
	  myThread.start();
	notify();
    }
}
