package co.foxdigitalst.redeactiva;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;


public class httpHandler {

  public String post(String posturl, List<NameValuePair> params){

	  try {

		  HttpClient httpclient = new DefaultHttpClient();
		  /*Creamos el objeto de HttpClient que nos permitira conectarnos mediante peticiones http*/
		 
		  HttpPost httppost = new HttpPost(posturl);
		  
		  /*Una vez añadidos los parametros actualizamos la entidad de httppost, esto quiere decir en pocas palabras anexamos los parametros al objeto para que al enviarse al servidor envien los datos que hemos añadido*/
		  httppost.setEntity(new UrlEncodedFormEntity(params));

          /*Finalmente ejecutamos enviando la info al server*/
		  HttpResponse resp = httpclient.execute(httppost);
		  
		  InputStream atomInputStream = resp.getEntity().getContent();
		  BufferedReader in = new BufferedReader(new InputStreamReader(atomInputStream));
		
	      String line;
	      String str = "";
	      while ((line = in.readLine()) != null){
	    	  str += line;
	      }

		  return str;

	  }
	  catch(Exception e) { return e.getMessage();}

	}
  
  public String get(String geturl){

	  try {

		  HttpClient httpclient = new DefaultHttpClient();
		  /*Creamos el objeto de HttpClient que nos permitira conectarnos mediante peticiones http*/
		 
		  HttpGet httpget = new HttpGet(geturl);
		 
          /*Finalmente ejecutamos enviando la info al server*/
		  HttpResponse resp = httpclient.execute(httpget);
		  
		  InputStream atomInputStream = resp.getEntity().getContent();
		  BufferedReader in = new BufferedReader(new InputStreamReader(atomInputStream));
		
	      String line;
	      String str = "";
	      while ((line = in.readLine()) != null){
	    	  str += line;
	      }

		  return str;

	  }
	  catch(Exception e) { return e.getMessage();}

	}
  
}
