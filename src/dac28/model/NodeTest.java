package dac28.model;

import static org.junit.Assert.*;

import org.junit.Test;


public class NodeTest {

	@Test
	public void testConstructorAssignsValueAndNodeGetValue() {
		Node node = new Node(0);
		assertEquals("Node value did not equal 0",0,node.getValue());
	}
	
	@Test
	public void testHasChildAndAddChildAndGetChildren() {
		
		Node root = new Node(0);
		Node one = new Node(1);
		Node two = new Node(2);
		Node three = new Node(3);
		
		assertFalse("Node had a child when it shouldn't of",root.hasChild());
		root.addChild(one);
		root.addChild(two);
		assertTrue("Node did not have a child when expect to",root.hasChild());
		
		assertFalse("Node had a child when it shouldn't of",one.hasChild());
		assertFalse("Node had a child when it shouldn't of",two.hasChild());
		two.addChild(three);
		assertFalse("Node had a child when it shouldn't of",one.hasChild());
		assertTrue("Node did not have a child when expect to",two.hasChild());
		
		assertEquals("Node did not equal expected node",one,root.getChildren().get(0));
		assertEquals("Node did not equal expected node",two,root.getChildren().get(1));
		assertEquals("Node did not equal expected node",three,two.getChildren().get(0));
	
	}

}
