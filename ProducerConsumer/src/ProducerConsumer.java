// The following is modified from:
// http://docs.oracle.com/javase/6/docs/api/java/util/concurrent/BlockingQueue.html
// https://developers.google.com/picasa-web/docs/2.0/developers_guide_java?csw=1

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.io.*;

import javax.servlet.ServletException;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.AprLifecycleListener;
import org.apache.catalina.core.StandardServer;
import org.apache.catalina.startup.Tomcat;

import com.google.gdata.client.Query;
import com.google.gdata.client.photos.PicasawebService;
import com.google.gdata.data.photos.AlbumFeed;
import com.google.gdata.data.photos.PhotoEntry;
import com.google.gdata.data.photos.PhotoFeed;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;


@SuppressWarnings("unused")
public class ProducerConsumer  {
	   public static void main(String[] args) throws InterruptedException, URISyntaxException{
	     BlockingQueue<String> q = new ArrayBlockingQueue<String>(50);
	     Producer p = new Producer(q);
	    // HashMap photoURL = new HashMap();
	     ArrayList<PhotoFeed> photoList = new ArrayList<PhotoFeed>();
	    // Consumer c2 = new Consumer(q,photoURL);
	     Consumer c = new Consumer(q,photoList);
	     new Thread(p).start();
	     new Thread(c).start();
	       
	     try{
	     Tomcat tomcat = new Tomcat();
	     tomcat.setBaseDir(".");
	     tomcat.setPort(9090);
	     
	     File docBase = new File("docBase");
	     Context ctx = tomcat.addContext("/",docBase.getAbsolutePath());
	     
	     Tomcat.addServlet(ctx,"foo", new PhotoServlet(photoList));
	     ctx.addServletMapping("/*", "foo");
	     
	     tomcat.start();tomcat.getServer().await();
	     }
	     catch(Exception e){
	    	 e.printStackTrace();
	     }
	   }
}



 