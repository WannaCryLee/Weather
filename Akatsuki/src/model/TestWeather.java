package model;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestWeather {
	
	Weather weather = new Weather(98404);

	@Test
	public void setup() {
		assertEquals(weather.getZip(), 98404);
	}
	
	@Test
	public void testZipChanged() {
		weather.changeUrl(98422);
		assertEquals(weather.getZip(), 98422);
	}

}
