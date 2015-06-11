package model;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class TestTraffic {

	private Traffic aTraffic;
	
	@Before
	public void setUp() throws Exception {
		aTraffic = new Traffic("Tacoma");
		aTraffic.populateData();
	}

	@Test
	public void testWhichNotOpen() {
		List<Integer> notOpen = (List<Integer>) aTraffic.whichNotWideOpen();
		
		assertEquals(notOpen.size(), 0);
	}
	
	@Test
	public void testGetTraveledTime() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testTrafficFlowStreet() {
		fail("Not yet implemented");
	}
	

}
