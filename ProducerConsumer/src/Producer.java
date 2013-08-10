import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.BlockingQueue;

import com.google.gdata.client.Query;
import com.google.gdata.client.photos.PicasawebService;
import com.google.gdata.data.photos.AlbumFeed;
import com.google.gdata.data.photos.PhotoEntry;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

public class Producer implements Runnable{
	
   private final BlockingQueue<Object> queue;
   
   Producer(BlockingQueue<Object> q) {
	   this.queue = q;
   }
   
   public void run() {
     try {
    	 // set base time, then use that to compare each time the new photos are added
    	 long rightNow = System.currentTimeMillis();
    	 
    	 while(!Thread.interrupted()){
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
   		myQuery.setStringCustomParameter("kind", "photo");
   		myQuery.setMaxResults(10);
   		//myQuery.setFullTextQuery("wedding");

   		AlbumFeed searchResultsFeed = null;
   		try {
   			searchResultsFeed = myService.query(myQuery, AlbumFeed.class);
   		} catch (IOException e) {
   			// TODO Auto-generated catch block
   			e.printStackTrace();
   		} catch (ServiceException e) {
   			// TODO Auto-generated catch block
   			e.printStackTrace();
   		}
   		
   		// detect new photos
   		for (PhotoEntry photo : searchResultsFeed.getPhotoEntries()){
   				long temp = photo.getPublished().getValue();
   				if(temp > rightNow)
   					queue.put(photo);
   				
   		}
   		// call put for any new entries into the queue
   		rightNow = System.currentTimeMillis();
   		Thread.sleep(10000);
   		
       }
     } 
     catch (InterruptedException ex) 
     { 
    	 ex.printStackTrace();
     }
   }
}

/*   
   // produce method based off picasa example
   Object produce(){
				/*
		for (PhotoEntry photo : searchResultsFeed.getPhotoEntries()) {
		    System.out.println(photo.getTitle().getPlainText());
		    System.out.println("Title: " + photo.getTitle().getPlainText());
		    System.out.println("Description: " + photo.getDescription().getPlainText());
		  //  System.out.println("ID: " + photo.getId());
		   // System.out.println("Camera Model: " + photo.getExifTags().getCameraModel());
		   // System.out.println("Geo Location: " + photo.getGeoLocation());
		   // System.out.println("Media Thumbnail: " + photo.getMediaThumbnails().get(0).getUrl());
		}
		*/	
