import java.util.HashMap;
import java.util.concurrent.BlockingQueue;


   
 public class Consumer implements Runnable {
   private final BlockingQueue<Object> queue;
   private final HashMap photos;
   
   Consumer(BlockingQueue<Object> q, HashMap photos){ 
	   queue = q; 
	   this.photos = photos;
   }
   
   public void run() {
     try {
       while (true) 
       { consume(queue.take()); }
     }
     catch (InterruptedException ex) {}
   }
   
   // consume method
   void consume(Object x) {
	   
	   // get the metadata and put it in the photos map
	   synchronized(photos){
		   
	   }
   }
}
