package dac28.model.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import dac28.model.Node;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Node.class})
@PowerMockIgnore("org.apache.log4j.*")
public class NodeTest {

	private Node spy;
	
	@Before
	public void setup() {
		try {
			Node node = Whitebox.invokeConstructor(Node.class, 4);
			spy = PowerMockito.spy(node);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetValueReturnsCorrectValue() {
		assertEquals("Incorrect value was returned",4,spy.getValue());
		Mockito.verify(spy).getValue();
	}
	
	@Test
	public void testAddingChildrenAndHasChildAndGetChildren() {
		Node n = Mockito.mock(Node.class);
		try {
			assertFalse("Child incorrectly in children list",(boolean)Whitebox.invokeMethod(spy, "hasChild"));
			Whitebox.invokeMethod(spy, "addChild", n);
		} catch (Exception e) {
			e.printStackTrace();
		}
		LinkedList<Node> list = new LinkedList<Node>();
		list.add(n);
		assertTrue("Lists were not equals",spy.getChildren().equals(list));
		assertTrue("New node was not added",spy.getChildren().contains(n));
		try {
			assertTrue("No child was in children list",(boolean)Whitebox.invokeMethod(spy, "hasChild"));
			PowerMockito.verifyPrivate(spy).invoke("addChild", n);
			PowerMockito.verifyPrivate(spy,times(2)).invoke("hasChild");
		} catch (Exception e) {
			e.printStackTrace();
		}
		Mockito.verify(spy,times(2)).getChildren();
	}
	
	

}
