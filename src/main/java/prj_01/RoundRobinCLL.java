// Emmanuel González Morales emmanuel.gonzalez22@upr.edu
/**
 * Node class - Double Linked Nodes
 * 
 * Round Robin CLL Interface 
 * 
 * Round Robin CLL class 
 * @author Emmanuel Gonzalez Morales
 */
package prj_01;
import java.util.concurrent.ThreadLocalRandom;


class Node {
    public int id;
    public Node next;
    public Node previous;
    public Boolean proccessed_flag;

    public Node (int id) {
        this.id = id;
        proccessed_flag = true;
    }
}

interface RoundRobinCLLInterface {
    abstract void findEmptySlot();
    abstract void findFilledSlot();
}

public class RoundRobinCLL implements RoundRobinCLLInterface {
    private int num_nodes = 5;
    public Node head = null;
    public Node tail = null;
    public Boolean stopLoop = false;
    private int termination_limit;
  
    /**
     * holdon is the method that will control the time(sleep) is needed for Threads to work
     * 
     * 
     */
    private void holdon() {
        try{
            Thread.currentThread().sleep(ThreadLocalRandom.current().nextInt(500, 3000));
        }
        catch(Exception e){
            System.out.println("Something went wrong.");
        }
    }
    /**
     * OverRide of toString() return a string representation of the nodes in the 
     * Circular Linked List
     * @return string representation of the nodes
     */
    @Override
    public String toString () {
        String s = new String(""+ Thread.currentThread().getName() + " ");
        Node node = head;
        s+= "(Node-1: " + node.proccessed_flag + ")";
        s+= " ==> ";

        for (int i=1; i<num_nodes; i++) {
            node = node.next;
            s+= "(Node-"+(i+1)+": "+node.proccessed_flag + ")";
            if (i<num_nodes-1)
                s+= " ==> ";
        }
        return s;
    }
   /**
    * holdRR access the node avoiding threads race (thread-safety)
    * @param node node to modify
    * @param set_slot boolean value to replace the node's preccessed_flag
    */
    private synchronized void holdRR(Node node, Boolean set_slot) {
        System.out.println("Thread " + Thread.currentThread().getName() + " Holding Resources");
        node.proccessed_flag = set_slot ;
        System.out.println("Thread " + Thread.currentThread().getName() + " Releasing Resources");
        if (set_slot) holdon();
    }
    /**
     * findEmptySlot is the Worker processes it will search in the 
     * Circular Linked List for nodes who proccesed_flag equal
     * true and change it to false.
     * 
     *@author Emmanuel Gonzalez Morales
     */
    public void findEmptySlot() {//worker
    	holdon();
        /* PUT YOUR CODE HERE TO FIND AN EMPTY SLOT */
        /* STARTING FROM THE FIRST NODE IN THE LINKED LIST */
    	Node currNode = head;
        for(int index = 0; index < this.num_nodes ; index++) {
        	//holdon();
        	if(currNode.proccessed_flag) {
        		holdRR(currNode, false); // set proccesses
        		break;
        	}
        	currNode = currNode.next;
        }
        /*** IMPORTANT:: USE THE holdRR() METHODE TO ACCESS THE LINKED LIST ***/
        /*** TO AVOID RACE CONDITION ***/
    }
    /**
     * findFilledSlot is the Main processes that will search in the 
     * Circular Linked List for nodes who proccesed_flag equal
     * false and change it to true.
     * 
     * @author Emmanuel Gonzalez Morales
     */
    public void findFilledSlot() {
        /* PUT YOUR CODE HERE TO FIND THE FILLED SLOTS */
        /* FOR THE MAIN PROCESS                        */
        /*** IMPORTANT:: USE THE holdRR() METHODE TO ACCESS THE LINKED LIST ***/
        int count = 0 ;
        Node node = head;
        while (!stopLoop) {
        	holdon();
            /* PUT YOUR CODE HERE TO FIND THE FILLED SLOTS */
            if (count>termination_limit) break;
            System.out.println("Main Move No.: " + count%num_nodes + "\t" + toString());
      
       
            while(node.id != tail.id) {
            
            	if(!node.proccessed_flag) {
            	 	
            		holdRR(node, true); // set proccesses
           
            	}
            	node = node.next;
            }
            
          	count++;  
        }
    }

    /**
     * This method will initiate the Circular Linked List with
     * desired number of nodes based to the program.
     * Head and tail are connect
     * @author Emmanuel Gonzalez Morales
     */
    private void fillRoundRubin () {
        /* PUT YOUR CODE HERE INITIATE THE CIRCULAR LINKED LIST */    	
        /* WITH DESIRED NUMBER OF NODES BASED TO THE PROGRAM   */
    	head = new Node(0);
    	tail = new Node(this.num_nodes);
    	Node currNode = head;
    	currNode.next = tail;
    	tail.previous = currNode;
 
    	Node newNode = null;

    	for(int i = 1; i < this.num_nodes; i++) {
    	
    		newNode = new Node(i);
    		newNode.next = tail; //New Node next point to the tail
    		newNode.previous = currNode; // New Node point to previous node
    	
			currNode.next = newNode; 
			tail.previous = currNode.next;
			
			currNode = currNode.next;
    		
    		
    	}
    	tail.next = head;
    }

    /**
     * RoundRobinCLL Constructor
     * @param num_nodes
     * @param termination_limit
     */
    public RoundRobinCLL(int num_nodes, int termination_limit) {
        this.num_nodes = num_nodes;
        this.termination_limit = termination_limit;
        fillRoundRubin();
    }
    /**
     * RoundRobinCLL Constructor with default termination_limit
     * @param num_nodes
     * 
     */
    public RoundRobinCLL(int num_nodes) {
        this.num_nodes = num_nodes;
        fillRoundRubin();
    }
    /**
     * RoundRobinCLL Constructor with default values 
     */
    public RoundRobinCLL() {
        fillRoundRubin();
    }

}
