import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.concurrent.BlockingQueue;

import com.google.gdata.client.Query;
import com.google.gdata.client.photos.PicasawebService;
import com.google.gdata.data.photos.AlbumFeed;
import com.google.gdata.data.photos.PhotoEntry;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

public class Producer implements Runnable{

	private final BlockingQueue<PhotoEntry> queue;

	Producer(BlockingQueue<PhotoEntry> q) {
		this.queue = q;
	}

	public void run() {
		try {
			// set base time, then use that to compare each time the new photos are added
			long lastPhotoCreationTime = 0;

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

				HashSet<String> storedPhotos = new HashSet<String>();

				// detect new photos
				long newLastPhotoCreationTime = lastPhotoCreationTime;
				for (PhotoEntry photo : searchResultsFeed.getPhotoEntries()){
					long temp = photo.getPublished().getValue();
					if(temp > lastPhotoCreationTime && !storedPhotos.contains(photo.getGphotoId()))
						queue.put(photo);

					if(photo.getPublished().getValue() >newLastPhotoCreationTime){
						newLastPhotoCreationTime = photo.getPublished().getValue();
					}
					storedPhotos.add(photo.getGphotoId());	
				}
				// call put for any new entries into the queue
				lastPhotoCreationTime = newLastPhotoCreationTime; 
				Thread.sleep(10000);

			}
		} 
		catch (InterruptedException ex) 
		{ 
			ex.printStackTrace();
		}
	}
}


