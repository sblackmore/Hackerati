// The following is modified from:
// http://docs.oracle.com/javase/6/docs/api/java/util/concurrent/BlockingQueue.html
// https://developers.google.com/picasa-web/docs/2.0/developers_guide_java?csw=1

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.net.URISyntaxException;
import java.io.*;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;

import com.google.gdata.data.photos.PhotoEntry;
import com.google.gdata.data.photos.PhotoFeed;


@SuppressWarnings("unused")
public class ProducerConsumer  {
	public static void main(String[] args) throws InterruptedException, URISyntaxException{
		BlockingQueue<PhotoEntry> q = new ArrayBlockingQueue<PhotoEntry>(50);
		Producer p = new Producer(q);
		ArrayList<String> photoList = new ArrayList<String>();
		Consumer c = new Consumer(q,photoList);
		new Thread(p).start();
		new Thread(c).start();

		try{
			Tomcat tomcat = new Tomcat();
			tomcat.setBaseDir(".");
			tomcat.setPort(9090);

			File baseDir = new File(".");
						
			Context ctx = tomcat.addContext("",baseDir.getAbsolutePath());

			Tomcat.addServlet(ctx,"IndexServlet", new IndexServlet());
			ctx.addServletMapping("/", "IndexServlet");
			
			Tomcat.addServlet(ctx,"PhotoServlet", new PhotoServlet(photoList));
			ctx.addServletMapping("/photos", "PhotoServlet");

			tomcat.start();tomcat.getServer().await();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}



