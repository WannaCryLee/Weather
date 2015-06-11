package model;

import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Scrap {

	static JSONParser parser = new JSONParser();
	
	static Scanner scan;
	
	public static void main(String[] args) {
//		scan = new Scanner(System.in);
//		System.out.println("What is your zipcode?");
//		int zipcode = scan.nextInt();
//		
//		Weather weather = new Weather(zipcode);
//		
//		String forcast = weather.usingURL();
//	
//		String description = parse(forcast, "weather");
//		description = description.substring(1, description.length() - 1);
//		String main = parse(forcast, "main");
//		String location = parse(forcast, "name");
//		String sky = parse(description, "main");
//		String temp = parse(main, "temp");
//		
//		System.out.println("Location: " + location);
//		System.out.println("Temperature: " + temp + " Sky: " + sky);
//				
//		scan.close();
		
		
		Traffic trafficData = new Traffic("Tacoma");
		
		trafficData.populateData();
		System.out.println(trafficData.toString());
		
	}
	
	static String parse(String data, String keyword) {
		String parsed = "";
		try {
			JSONObject dataObject = (JSONObject) parser.parse(data);
			parsed = (String) dataObject.get(keyword).toString();

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return parsed;
	}
}
