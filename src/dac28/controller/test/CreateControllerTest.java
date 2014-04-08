package dac28.controller.test;

import static org.junit.Assert.*;

import java.util.LinkedList;

import javax.swing.JTextField;
import javax.swing.JPanel;

import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;

import dac28.controller.CreateController;

public class CreateControllerTest {

	private CreateController controller;
	
	@Before
	public void setup() {
		
		controller = new CreateController();

	}
	
	@Test
	public void testGetCreateDialog() {

		JPanel returnedPanel = controller.getCreateDialog();
		
		assertTrue("getCreateDialog did not return a JPanel",returnedPanel.getClass().equals(JPanel.class));
		assertTrue("Returned panel didn't have correct number of components",returnedPanel.getComponentCount()==5);
		
	}
	
	@Test
	public void testGetTreeUID() {
		assertTrue("number returned was not valid",0<=controller.getTreeUID());
	}
	
	@Test
	public void testGetAlgorithmUID() {
		assertTrue("number returned was not valid",0<=controller.getAlgorithmUID());
	}

	@Test
	public void testGetGoal() {
		assertTrue("Goal value was not returned",controller.getGoal().equals(((JTextField) Whitebox.getInternalState(controller, "goal")).getText()));
	}
	
	@Test
	public void testGetNodeValues() {
		assertTrue("Queue was not returned",controller.getNodeValues().getClass().equals(LinkedList.class));
	}
	
	@Test
	public void testGetPathValues() {
		assertTrue("Queue was not returned",controller.getPathValues().getClass().equals(LinkedList.class));
	}
}
