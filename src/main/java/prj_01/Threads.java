// Emmanuel González Morales emmanuel.gonzalez22@upr.edu
/**
 * Thread class - In this class Threads are created
 * @author Emmanuel Gonzalez Morales
 */
package prj_01;
import java.util.*;
public class Threads {
    public ArrayList<Thread> threads = new ArrayList<Thread>();
    /**
     * Create a selected number of Threads and add then to the threads ArrayList
     * @param noThreads - numbers of Threads
     */
    public Threads(int noThreads){
       for (int i=0; i<noThreads; i++){
           ThreadRunnable runnable = new ThreadRunnable();
           System.out.println("Creating Thread " + (i+1));
           threads.add(new Thread(runnable, ""+i));
       }
    }
    /**
     * Add a number of already created to the threads ArrayList
     * @param noThreads - numbers of Threads
     * @param runnable - add an all ready created thread to the threads ArrayList
     */
    public Threads(int noThreads, ThreadRunnable runnable){
        for (int i=0; i<noThreads; i++){
            System.out.println("Creating Thread " + (i+1));
            threads.add(new Thread(runnable, ""+i));
        }
    }
}
