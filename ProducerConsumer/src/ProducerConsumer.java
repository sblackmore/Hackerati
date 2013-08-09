// The following is modified from:
// http://docs.oracle.com/javase/6/docs/api/java/util/concurrent/BlockingQueue.html
// https://developers.google.com/picasa-web/docs/2.0/developers_guide_java?csw=1

import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.io.*;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import com.google.gdata.client.Query;
import com.google.gdata.client.photos.PicasawebService;
import com.google.gdata.data.photos.AlbumFeed;
import com.google.gdata.data.photos.PhotoEntry;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;


public class ProducerConsumer  {
	   public static void main(String[] args) throws InterruptedException, URISyntaxException{
	     BlockingQueue<Object> q = new ArrayBlockingQueue<Object>(50);
	     Producer p = new Producer(q);
	     HashMap photoURL = new HashMap();
	     Consumer c2 = new Consumer(q,photoURL);
	     new Thread(p).start();
	     new Thread(c2).start();
	    	     
	     // get tomcat running
	     // This is the minimal tomcat instance we need for embedding
         Tomcat tomcat = new Tomcat();
         // set http listen port for the default connector we get out-of-the-box
         // (there's a lot more you can customize, see the javadoc)
         tomcat.setPort(9090);
         // set up context, 
         //  "" indicates the path of the ROOT context
         //  tmpdir is used as docbase because we are not serving any files in this example
         File base = new File(".");
         // create foo.html in working dir.  It will contain the web 2.0 stuff.  put it in the first arg.
         Context rootCtx = tomcat.addContext("foo.html", base.getAbsolutePath());
         
         // Add the 'killer switch' servlet (used to shut down the server) to the context
         Tomcat.addServlet(rootCtx, "Hackerati", new PhotoServlet(photoURL));
         rootCtx.addServletMapping("/shutdown", "Hackerati");
         
         // ..and we are good to go
         try {
			tomcat.start();
		} catch (LifecycleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     
	   }
}



 