package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Weather {

	 String url = "http://api.openweathermap.org/data/2.5/weather?zip=98422,us&units=imperial";
	 int timeout = 100000;
	 int myZip;
	
	public Weather(int theZip) {
		myZip = theZip;
		changeUrl(myZip);
	}
	
	 String usingURL() {
			HttpURLConnection c = null;
		    try {
		        URL u = new URL(url);
		        c = (HttpURLConnection) u.openConnection();
		        c.setRequestMethod("GET");
		        c.setRequestProperty("Content-length", "0");
		        c.setUseCaches(false);
		        c.setAllowUserInteraction(false);
		        c.setConnectTimeout(timeout);
		        c.setReadTimeout(timeout);
		        c.connect();
		        int status = c.getResponseCode();

		        switch (status) {
		            case 200:
		            case 201:
		                BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
		                StringBuilder sb = new StringBuilder();
		                String line;
		                while ((line = br.readLine()) != null) {
		                    sb.append(line+"\n");
		                }
		                br.close();
		                return sb.toString();
		        }

		    } catch (MalformedURLException ex) {
		        Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
		    } catch (IOException ex) {
		        Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
		    } finally {
		       if (c != null) {
		          try {
		              c.disconnect();
		          } catch (Exception ex) {
		             Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
		          }
		       }
		    }
		    return null;
	}
	 
	 void setZip(int theZip) {
		 myZip = theZip;
		 changeUrl(myZip);
	 }
	 
	 void changeUrl(int theZip) {
		 String oldUrl = url;
		 String first = oldUrl.substring(0, 51);
		 String second = oldUrl.substring(56);
		 url = first + theZip + second;
	 }
	 
	 int getZip() {
		 return myZip;
	 }
	 
}
