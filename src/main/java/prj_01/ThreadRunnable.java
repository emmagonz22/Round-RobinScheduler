// Emmanuel González Morales emmanuel.gonzalez22@upr.edu
/**
 * ThreadRunnable class - In this is class is defined the behavior of the Threads
 * @author Emmanuel Gonzalez Morales
 */ 
package prj_01;

public class ThreadRunnable implements Runnable {

    private boolean doStop = false;
    private RoundRobinCLL rr = null;

    /* Constructors */
    public ThreadRunnable() {
    }
    public ThreadRunnable(RoundRobinCLL rr) {
        this.rr = rr;
    }
    /**
     * Definitions of Thread's behavior  
     */
    @Override
    public void run() {
        System.out.println("Running Thread... This is Thread " + Thread.currentThread().getName());
        if (rr==null) {
            return;
        }
        while  (!rr.stopLoop) {
            // keep doing what this thread should do.
            rr.findEmptySlot();
        }
        System.out.println("Thread " + Thread.currentThread().getName() + " Finished ... Bye Bye");
    }
}
