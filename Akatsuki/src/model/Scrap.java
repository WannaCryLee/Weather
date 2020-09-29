package model;

import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Scrap {

	static JSONParser parser = new JSONParser();
	
	static Scanner scan;
	
	public static void main(String[] args) {

		
		
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
