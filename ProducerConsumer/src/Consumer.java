import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;


 public class Consumer implements Runnable {
   private final BlockingQueue<Object> queue;
  // private final HashMap<Integer, Object> photos;
   private final ArrayList<Object> photoList;
   
   Consumer(BlockingQueue<Object> q, ArrayList<Object> photoList){ 
	   queue = q; 
	   this.photoList = photoList;
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
	   
	   // get the metadata and put it in the photos list
	   synchronized(photoList){
		   photoList.add(x);
	   }
   }
}
