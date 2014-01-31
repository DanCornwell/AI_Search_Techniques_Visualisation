package dac28.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class TreeCreatorTest {

	@Test
	public void testTreeCreator() {
		
		TreeCreator tree124Creator = new Tree124Creator();
		Tree tree124 = tree124Creator.factoryMethod();
		assertEquals("Tree goals did not match",tree124.getGoal(),new Tree124(4).getGoal());
		
	}

}
