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
 * Tests the tree methods via mocking.
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
	public void testGetGoal() {
		// Checks that getGoal returns the tree's GOAL variable
		assertEquals("Tree's goal variable was not returned",Whitebox.getInternalState(tree, "GOAL"),tree.getGoal());
		// Checks calls getGoal
		Mockito.verify(tree).getGoal();
	}

	@Test
	public void testGetRoot() {
		// Check that getRoot returns the tree's ROOT variable
		assertEquals("Tree's root variable was not returned",Whitebox.getInternalState(tree, "ROOT"),tree.getRoot());
		// Checks calls getRoot
		Mockito.verify(tree).getRoot();
	}

	@Test
	public void testGetTreeDepth() {
		// Check that getTreeDepth returns a positive integer
		assertTrue("Get tree depth returns a positive integer",tree.getTreeDepth(tree.getRoot()) >= 0);
		// Checks calls getGetTreeDepth
		Mockito.verify(tree).getTreeDepth(tree.getRoot());
	}

}
