/**
 * DTU, Course 02224, Real-Time Systems
 * 
 * Simulator for Baggage Sorting System  --- RCX Sensor
 *
 * @author hhl
 * @version 1.0 April 26, 2006
 * 
 */

package josx.platform.rcx;

public class Sensor {

  public static Sensor S1 = new Sensor(1);
  public static Sensor S2 = new Sensor(2);
  public static Sensor S3 = new Sensor(3);

  int no;

  boolean active = false;
  int     value  = 75;

  Sensor(int _no) { no = _no; }

  public void activate() {
	    active = true;
	  }

  public void passivate() {
	    active = false;
	  }

  public synchronized int readValue() {
    return value;
  }

  public synchronized void setValue(int val) {
    if (val!= value) Poll.change(1<<(no-1));
    value = val;
  }

  /* Extra inspection methods for simulation */
  
  public synchronized boolean isActivated() {
	  return active;
  }
  
  
}






