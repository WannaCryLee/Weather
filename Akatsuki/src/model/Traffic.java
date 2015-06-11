package model;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

public class Traffic {

	private String city;
	private List<String> trafficFlowKey;
	private List<String> trafficFlowValue;
	private List<String> travelTimeKey;
	private List<String> travelTimeValue;
	
	public Traffic(String theCity) {
		city = theCity;
		trafficFlowKey = new ArrayList<String>();
		travelTimeKey = new ArrayList<String>();
		trafficFlowValue = new ArrayList<String>();
		travelTimeValue = new ArrayList<String>();
	}
	
	
	List<Integer> whichNotWideOpen(){
		List<Integer> notOpen = new ArrayList<Integer>();
		int trafficCounter = 0;
		for (String s : trafficFlowKey) {
			if (!s.equals("WideOpen"))
				notOpen.add(trafficCounter);
			trafficCounter++;
		}
		return notOpen;
	}
	
	String getCityTravelTime(String aCity) {
		if (!travelTimeKey.contains(aCity))
			return null;
		int counter = 0;
		for (String s : travelTimeKey) {
			if (s.contains(aCity)) {
				if (s.indexOf(city) < s.indexOf(aCity))
					return travelTimeValue.get(counter);
			}
			counter++;
		}
		return null;
	}
	
	/**
	 * Gets the Street on given index
	 * @param index and int that represents wideopen or not
	 * @return String of street address
	 */
	String getTrafficFlowStreet(int index) {
		return trafficFlowValue.get(index);
	}
	
	void populateData() {
		populateTravelTime();
		populateTrafficFlow();
	}
	
	private void populateTrafficFlow() {
		SyndFeed traffic = getTrafficFlow();
		
		@SuppressWarnings("unchecked")
		List<SyndEntry> trafficEntries = (List<SyndEntry>) traffic.getEntries();
		
		for (SyndEntry entry : trafficEntries) {
			String title = entry.getTitleEx().getValue();
			String time = parseTime(entry.getDescription().getValue());
			
			if (time != null && !title.equals("Unknown")) {
				trafficFlowKey.add(time);
				trafficFlowValue.add(title);
			}
		}
	}
	
	private String parseTime(String data) {
		int index = data.indexOf("milepost");
		String milePost = data.substring(index + 9, index + 12);
		for (int i = 1; i < milePost.length(); i++) {
			if (milePost.charAt(i) > 57 || milePost.charAt(i) < 48)
				milePost = milePost.substring(0, i);
		}
		int post = Integer.valueOf(milePost);
		if (post < 130 || post > 147)
			return null;
		
		index = data.indexOf("Reading: ");
		String read = data.substring(index + 9);
		index = read.indexOf('<');
		read = read.substring(0, index);
		if (read.equals("Unknown"))
			return null;
		return read;
	}
	
	private void populateTravelTime() {
		SyndFeed travelTime = getTravelTimes();
		@SuppressWarnings("unchecked")
		List<SyndEntry> travelEntries =  (List<SyndEntry>) travelTime.getEntries();
		
		for (SyndEntry entry : travelEntries) {
			String title = importantTitle(entry.getTitleEx().getValue());
			String time = importantDesc(entry.getDescription().getValue());

			if (title != null) {
				travelTimeKey.add(title);
				travelTimeValue.add(time);
			}
		}
	}
	
	private String importantTitle(String data) {
		if (data.contains(city))
			return data;
		
		return null;
	}
	//130 Tacoma to 147 Federal Way
	private String importantDesc(String data) {
		String returnData = null;
		
		int index = data.indexOf("Travel Time: ");
		returnData = data.substring(index + 13, index + 15);
		if (returnData.charAt(returnData.length() - 1) == '<')
			returnData = returnData.substring(0, returnData.length() - 1);
		
		return returnData;
	}
	
	private SyndFeed getTravelTimes() {
		SyndFeed feed = null;
		try {
			URL feedUrl = new URL("http://www.wsdot.wa.gov/traffic/api/TravelTimes/rss.aspx");
			SyndFeedInput input = new SyndFeedInput();
			feed = input.build(new XmlReader(feedUrl));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (FeedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return feed;
	}
	
	private SyndFeed getTrafficFlow() {
		SyndFeed feed = null;
		try {
			URL feedUrl = new URL("http://www.wsdot.wa.gov/traffic/api/TrafficFlow/rss.aspx");
			SyndFeedInput input = new SyndFeedInput();
			feed = input.build(new XmlReader(feedUrl));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (FeedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return feed;
	}
	
	@Override
	public String toString() {
		String everything = null;
		everything = "Traffic Flow\n";
		for (int i = 0; i < trafficFlowKey.size(); i++) 
			everything = everything + "Title: " + trafficFlowValue.get(i) + "\nCondition: " + trafficFlowKey.get(i) + "\n\n";
		everything = everything + "Travel Time\n";
		for (int i = 0; i < travelTimeKey.size(); i++) 
			everything = everything + "Title: " + travelTimeKey.get(i) + "\nTime: " + travelTimeValue.get(i) + "\n\n";
		return everything;
	}
}









