package dac28.model.test;

import static org.junit.Assert.*;

import org.junit.Test;

import dac28.model.Tree;
import dac28.model.Tree124Creator;
import dac28.model.TreeCreator;



/**
 * Tests the tree creator methods.
 * 
 * @author Dan Cornwell
 *
 */
public class TreeCreatorTest {

	@Test
	public void testTreeCreator() {
		// Tree124 creator test
		TreeCreator tree124Creator = new Tree124Creator();
		Tree tree124 = tree124Creator.getTree(4);
		assertEquals("Tree was not created with goal node of 4",4,tree124.getGoal());
		Tree another = tree124Creator.getTree(5);
		assertEquals("Tree was not created with goal node of 5",5,another.getGoal());
		
	}

}
