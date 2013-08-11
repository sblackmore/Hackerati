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
	private final BlockingQueue<PhotoEntry> queue;
	// private final HashMap<Integer, Object> photos;
	private final ArrayList<String> photoMetaList;

	Consumer(BlockingQueue<PhotoEntry> q, ArrayList<String> photoMetaList){ 
		queue = q; 
		this.photoMetaList = photoMetaList;
	}



	public void run() {
		try {
			while (true) 
			{ consume(queue.take()); }
		}
		catch (InterruptedException ex) {}
	}

	// consume method
	void consume(PhotoEntry photoInfo) {
		// create connection to Google API, get photofeed for the photo ID
/*
		//PicasawebService myService = new PicasawebService("exampleCo-exampleApp-1");
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
		*/
		// get the metadata and put it in the photos list
		synchronized(photoMetaList){
			StringBuilder data = new StringBuilder();
			data.append("<new entry>");
			data.append("\n");
			//data.append("Album ID: " + photoInfo.getAlbumId());
			//data.append("\n");
			
			data.append("GPhoto ID: " + photoInfo.getGphotoId());
			/*data.append("\n");
		//	data.append("Plain Text Content: " + photoInfo.getPlainTextContent().trim());
			data.append("\n");
			try {
				data.append("Size: " + photoInfo.getSize().toString());
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			data.append("\n");
			//data.append("Album ID: " + photoInfo.getFeed(kinds));
			data.append("GeoLocation: " + photoInfo.getGeoLocation().toString());
			*/
			data.append("\n");
			data.append("</new entry>");
			
			photoMetaList.add(data.toString());
		}
	}
}
