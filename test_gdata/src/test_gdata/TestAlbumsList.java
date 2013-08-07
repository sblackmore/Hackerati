package test_gdata;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;



import java.io.File;

import com.google.gdata.client.*;
import com.google.gdata.client.photos.PicasawebService;
import com.google.gdata.data.*;
import com.google.gdata.data.media.*;
import com.google.gdata.data.photos.AlbumEntry;
import com.google.gdata.data.photos.AlbumFeed;
import com.google.gdata.data.photos.PhotoEntry;
import com.google.gdata.data.photos.UserFeed;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;


public class TestAlbumsList {
	//private static URL baseSearchUrl;

	public static void main(String[] args){
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
		myQuery.setFullTextQuery("puppy");

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

		for (PhotoEntry photo : searchResultsFeed.getPhotoEntries()) {
		    System.out.println(photo.getTitle().getPlainText());
		}		
	}
}
