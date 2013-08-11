import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

import com.google.gdata.data.photos.PhotoEntry;


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


	void consume(PhotoEntry photoInfo) {

		synchronized(photoMetaList){
			StringBuilder data = new StringBuilder();
			data.append("<new entry>");
			data.append("\n");
			//data.append("Album ID: " + photoInfo.getAlbumId());
			//data.append("\n");
			
			data.append("GPhoto ID: " + photoInfo.getGphotoId());
			/*data.append("\n");
			data.append("Plain Text Content: " + photoInfo.getPlainTextContent().trim());
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
