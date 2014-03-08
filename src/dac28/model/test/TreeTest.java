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
import dac28.model.Tree;


/**
 * Tests the tree methods via mocking.
 * 
 * @author Dan Cornwell
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Tree.class,Node.class})
@PowerMockIgnore("org.apache.log4j.*")
public class TreeTest {

	private Tree tree;

	@Before
	public void setup() {
		tree = PowerMockito.mock(Tree.class,Mockito.CALLS_REAL_METHODS);
	}

	@Test
	public void testGetGoal() {
		Whitebox.setInternalState(tree, "GOAL", 4);
		// Checks GOAL matches assigned GOAL (as it would be in a constructor)
		assertEquals("Goal node was not set to 4",4,tree.getGoal());
		// Checks that getGoal returns the tree's GOAL variable
		assertEquals("Tree's goal variable was not returned",Whitebox.getInternalState(tree, "GOAL"),tree.getGoal());
		// Checks calls getGoal
		verify(tree,times(2)).getGoal();
	}

	@Test
	public void testGetRoot() {
		Node root = mock(Node.class);
		Whitebox.setInternalState(tree, "ROOT", root);
		// Checks ROOT matches assigned ROOT (as it would be in a constructor)
		assertEquals("Root did not equal assigned value",root,tree.getRoot());
		// Check that getRoot returns the tree's ROOT variable
		assertEquals("Tree's root variable was not returned",Whitebox.getInternalState(tree, "ROOT"),tree.getRoot());
		// Checks calls getRoot
		verify(tree,times(2)).getRoot();
	}

	@Test
	public void testGetTreeDepth() {
		Node root = PowerMockito.mock(Node.class);
		Whitebox.setInternalState(tree, "ROOT", root);
		when(root.getChildren()).thenReturn(new LinkedList<Node>());
		// Check that getTreeDepth returns a positive integer
		assertTrue("Get tree depth returns a positive integer",tree.getTreeDepth(tree.getRoot()) >= 0);
		// Checks calls getGetTreeDepth with some node
		verify(tree).getTreeDepth(any(Node.class));
	}

}
