/**
 * DTU, Course 02224, Real-Time Systems
 * 
 * Simulator for Baggage Sorting System --- RCX Button
 *
 * @author hhl
 * @version 1.0 April 26, 2006
 * 
 */

package josx.platform.rcx;

/**
 * Abstraction for an RCX button.
 * Example:<p>
 * <code><pre>
 *    Button.RUN.waitForPressAndRelease();
 *    Sound.playTone (1000, 1);
 * </pre></code>
 */
public class Button //implements ListenerCaller
{
  /**
   * The Run button.
   */
  public static final Button RUN = new Button (0x01);
  /**
   * The View button.
   */
  public static final Button VIEW = new Button (0x02);
  /**
   * The Prgm button.
   */
  public static final Button PRGM = new Button (0x04);
  
  /**
   * Array containing VIEW, PRGM and RUN, in that order.
   */
  public static final Button[] BUTTONS = { Button.RUN, Button.VIEW, Button.PRGM };
  
  private int iCode;
  
  private Button (int aCode)
  {
    iCode = aCode;
  }

  /**
   * Return the ID of the button. One of 1, 2 or 4.
   */
  public final int getId()
  {
    return iCode;
  }
    
  /**
   * @return <code>true</code> if button is pressed, <code>false</code> otherwise.
   */
  public final boolean isPressed()
  {
  //  return (readButtons() & iCode) != 0;
  return false;
  }

 // static Poll poller = new Poll();

  /**
   * Wait until the button is released.
   */
  public synchronized final void waitForPressAndRelease() throws InterruptedException
  {
    wait();
/*    do {
        poller.poll(iCode << Poll.BUTTON_MASK_SHIFT, 0);
    } while (isPressed());
 */ }

  /**
   * Adds a listener of button events. Each button can serve at most
   * 4 listeners.
   */
 /* public synchronized void addButtonListener (ButtonListener aListener)
  {
    if (iListeners == null)
    {
      iListeners = new ButtonListener[4];
    }
    iListeners[iNumListeners++] = aListener;
    ListenerThread.get().addButtonToMask(iCode, this);
  }

 */ /**
   * <i>Low-level API</i> that reads status of buttons.
   * @return An integer with possibly some bits set: 0x02 (view button pressed)
   * 0x04 (prgm button pressed), 0x01 (run button pressed). If all buttons 
   * are released, this method returns 0.
   */
/*  public static int readButtons()
  {
    synchronized (Memory.MONITOR)
    {
      int pAddr = Memory.iAuxDataAddr;
      ROM.call ((short) 0x1fb6, (short) 0x3000, (short) pAddr);
      return Memory.readShort (pAddr);
    }
  }
*/
/*  public synchronized void callListeners()
  {
    for( int i = 0; i < iNumListeners; i++) {
      if( isPressed())
        iListeners[i].buttonPressed( this);
      else
        iListeners[i].buttonReleased( this);
    }
  }
*/

}

