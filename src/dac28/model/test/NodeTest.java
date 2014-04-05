package dac28.model.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import org.powermock.reflect.Whitebox;

import dac28.model.Node;

/**
 * Tests the public interface for the node class.
 * 
 * @author Dan Cornwell
 *
 */
public class NodeTest {

	private Node node;
	
	@Before
	public void setup() {
		try {
			node = Whitebox.invokeConstructor(Node.class,new Class[] { String.class }, new Object[] { "4" });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetValueReturnsCorrectValue() {
		assertEquals("Incorrect value was returned","4",node.getValue());
		assertEquals("Incorrect value was returned",Whitebox.getInternalState(node, "VALUE"),node.getValue());
	}
	
	@Test
	public void testGetChildrenReturnsChildrenList() {
		assertEquals("Incorrect value was returned",Whitebox.getInternalState(node, "children"),node.getChildren());
		assertTrue("List is empty at construction",node.getChildren().size()==0);
	}

}
