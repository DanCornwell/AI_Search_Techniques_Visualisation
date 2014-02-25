package dac28.model.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import dac28.model.Tree;
import dac28.model.Tree124Creator;


/**
 * Tests the tree methods.
 * 
 * @author Dan Cornwell
 *
 */
public class TreeTest {

	private Tree tree124;
	private final int GOAL = 4;
	
	@Before
	public void setup() {
		
		tree124 = new Tree124Creator().getTree(GOAL);
		
	}
	
	@Test
	public void testConstructorAssignsGoalValueAndGetRoot() {

		assertEquals("Goal value and expected value did not match",4,tree124.getGoal());
		assertEquals("Root node value and expected node value did not match",0,tree124.getRoot().getValue());
		
	}
	
	@Test 
	public void testTreeDepth() {
		
		assertEquals("Tree depth did not match expected value",3,tree124.getTreeDepth(tree124.getRoot()));
		
	}
 
}
