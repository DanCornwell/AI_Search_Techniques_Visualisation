package dac28.controller.test;

import static org.junit.Assert.*;

import org.junit.Test;

import dac28.controller.DualCreateController;

public class DualCreateControllerTest {

	@Test
	public void testGetAlgorithm2UID() {

		DualCreateController create = new DualCreateController();
		
		assertTrue("valid number not returned",0<=create.getAlgorithm2UID());

	}

}
