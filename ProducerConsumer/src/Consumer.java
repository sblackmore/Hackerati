import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

import com.google.gdata.client.Query;
import com.google.gdata.client.photos.PicasawebService;
import com.google.gdata.data.photos.AlbumFeed;
import com.google.gdata.data.photos.PhotoEntry;
import com.google.gdata.data.photos.PhotoFeed;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;


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
	  // PhotoFeed gPhotoFeed = 
			   
			   // create connection to Google API, get photofeed for the photo ID
	   
	   // look up design patterns
	   
	   PicasawebService myService = new PicasawebService("exampleCo-exampleApp-1");
  		try {
  			myService.setUserCredentials("hackerattest@gmail.com", "testPassword");
  		} catch (AuthenticationException e1) {
  			// TODO Auto-generated catch block
  			e1.printStackTrace();
  		}
	   
  		URL baseSearchUrl = null;
   		try {
   			baseSearchUrl = new URL("https://picasaweb.google.com/data/feed/api/all");
   		} catch (MalformedURLException e) {
   			// TODO Auto-generated catch block
   			e.printStackTrace();
   		}
  		
   		Query myQuery = new Query(baseSearchUrl);
    		
   		PhotoFeed gPhotoFeed = null;
   		try {
   			gPhotoFeed = myService.query(myQuery, PhotoFeed.class);
   		} catch (IOException e) {
   			// TODO Auto-generated catch block
   			e.printStackTrace();
   		} catch (ServiceException e) {
   			// TODO Auto-generated catch block
   			e.printStackTrace();
   		}
	   // get the metadata and put it in the photos list
	   synchronized(photoList){
	   	photoList.add(gPhotoFeed);
	   }
   }
}
