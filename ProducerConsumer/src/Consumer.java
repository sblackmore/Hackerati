import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

import com.google.gdata.data.photos.PhotoEntry;
import com.google.gdata.data.photos.PhotoFeed;


 public class Consumer implements Runnable {
   private final BlockingQueue<String> queue;
  // private final HashMap<Integer, Object> photos;
   private final ArrayList<PhotoFeed> photoList;
   
   Consumer(BlockingQueue<String> q, ArrayList<PhotoFeed> photoList){ 
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
   void consume(String gPhotoID) {
	   PhotoFeed gPhotoFeed = 
			   
			   // create connection to Google API, get photofeed for the photo ID
	   
	   // look up design patterns
	   
	   
	   // get the metadata and put it in the photos list
	   synchronized(photoList)
	   	photoList.add(gPhotoFeed);
	   }
   }
}
