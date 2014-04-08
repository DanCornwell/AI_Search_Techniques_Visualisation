package dac28.controller.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;

import dac28.controller.TextFileReader;

public class TextFileReaderTest {

	@Test
	public void testGetTreesAndGetAlgorithms() {
		PowerMockito.verifyStatic();
		assertTrue("List returned was empty, should have had at least 3 elements",TextFileReader.getTrees().size() >= 3);
		assertTrue("List returned was empty, should have had at least 4 elements",TextFileReader.getAlgorithms().size() >= 4);
	}

}
