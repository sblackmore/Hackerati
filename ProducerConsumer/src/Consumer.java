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


	void consume(PhotoEntry photoInfo)  {

		synchronized(photoMetaList){
			StringBuilder data = new StringBuilder();
			data.append("<new entry>");
			data.append("\n");
			data.append("<GPhotoID>GPhoto ID: " + photoInfo.getGphotoId() +"</GPhotoID>");
			if(photoInfo.getAlbumId() != null){
				data.append("\n");
				data.append("<AlbumID>Album ID: " + photoInfo.getAlbumId() + "</AlbumID>");
			}
			data.append("\n");
			data.append("</new entry>");
			
			photoMetaList.add(data.toString());
		}
	}
}
