package dac28.model.tree;

import static org.junit.Assert.*;

import org.junit.Test;


/**
 * Tests the tree methods.
 * 
 * @author Dan Cornwell
 *
 */
public class TreeTest {
/**
	@Test
	public void testConstructorAssignsGoalValueAndGetRoot() {

		Tree tree124 = new Tree124(4);
		assertEquals("Goal value and expected value did not match",4,tree124.getGoal());
		assertEquals("Root node value and expected node value did not match",0,tree124.getRoot().getValue());
		
	}
	
	@Test
	public void testTree124Construct() {
		
		Tree tree124 = new Tree124(4);
		tree124.construct();
		
		// Check root node has children
		assertTrue("Root node did not have children",tree124.getRoot().hasChild());
		// Formality check
		if(tree124.getRoot().hasChild()) {
			assertEquals("Node value did not match expected node value",1,tree124.getRoot().getChildren().get(0).getValue());
			assertEquals("Node value did not match expected node value",2,tree124.getRoot().getChildren().get(1).getValue());
		}
		// Check one node has children
		assertTrue("Node did not have children",tree124.getRoot().getChildren().get(0).hasChild());
		// Formality check
		if(tree124.getRoot().getChildren().get(0).hasChild()) {
			assertEquals("Node value did not match expected node value",3,tree124.getRoot().getChildren().get(0).getChildren().get(0).getValue());
			assertEquals("Node value did not match expected node value",4,tree124.getRoot().getChildren().get(0).getChildren().get(1).getValue());
		}
		// Check two node has children
		assertTrue("Node did not have children",tree124.getRoot().getChildren().get(1).hasChild());
		// Formality check
		if(tree124.getRoot().getChildren().get(1).hasChild()) {
			assertEquals("Node value did not match expected node value",5,tree124.getRoot().getChildren().get(1).getChildren().get(0).getValue());
			assertEquals("Node value did not match expected node value",6,tree124.getRoot().getChildren().get(1).getChildren().get(1).getValue());
		}
		
		// Check leaf nodes do not have children
		assertFalse("Leaf node has a child",tree124.getRoot().getChildren().get(0).getChildren().get(0).hasChild());
		assertFalse("Leaf node has a child",tree124.getRoot().getChildren().get(0).getChildren().get(1).hasChild());
		assertFalse("Leaf node has a child",tree124.getRoot().getChildren().get(1).getChildren().get(0).hasChild());
		assertFalse("Leaf node has a child",tree124.getRoot().getChildren().get(1).getChildren().get(1).hasChild());
		
	}
**/
}
