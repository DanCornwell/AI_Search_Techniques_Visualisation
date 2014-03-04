package dac28.model.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import dac28.model.Tree;


/**
 * Tests the tree methods.
 * 
 * @author Dan Cornwell
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Tree.class)
@PowerMockIgnore("org.apache.log4j.*")
public class TreeTest {

	private Tree tree;
	
	@Before
	public void setup() {
		
		tree = PowerMockito.mock(Tree.class);
	}
	
	@Test
	public void testCallsConstruct() throws Exception {
		Whitebox.invokeMethod(tree, "construct");
		PowerMockito.verifyPrivate(tree).invoke("construct");
	}
	
	@Test
	public void testCallsGetGoal() {
		assertEquals("Tree's goal variable was not returned",Whitebox.getInternalState(tree, "GOAL"),tree.getGoal());
		Mockito.verify(tree).getGoal();
	}
	
	@Test
	public void testCallsGetRoot() {
		assertEquals("Tree's root variable was not returned",Whitebox.getInternalState(tree, "ROOT"),tree.getRoot());
		Mockito.verify(tree).getRoot();
	}
 
}
