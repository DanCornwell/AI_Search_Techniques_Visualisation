package dac28.model.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.LinkedList;
import java.util.Queue;

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
 * Tests the public interface of the tree class.
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
		Whitebox.setInternalState(tree, "goal", "value");
		assertEquals("Tree's goal variable was not returned",Whitebox.getInternalState(tree, "goal"),tree.getGoal());
	}

	@Test
	public void testGetRoot() {	
		Whitebox.setInternalState(tree, "root", mock(Node.class));
		assertEquals("Tree's root variable was not returned",Whitebox.getInternalState(tree,"root"),tree.getRoot());
	}

	@Test
	public void testGetTreeDepth() {
		
		Node one = PowerMockito.mock(Node.class);
		Node two = PowerMockito.mock(Node.class);
		LinkedList<Node> oneChildren = new LinkedList<Node>();
		oneChildren.add(two);
		Node three = PowerMockito.mock(Node.class);
		LinkedList<Node> twoChildren = new LinkedList<Node>();
		twoChildren.add(three);
		
		doReturn(oneChildren).when(one).getChildren();
		doReturn(twoChildren).when(two).getChildren();
		doReturn(new LinkedList<Node>()).when(three).getChildren();
		
		Whitebox.setInternalState(tree, "root", one);
		
		assertTrue("Incorrect tree depth was returned",tree.getTreeDepth()==3);
		verify(tree).getTreeDepth();
	}
	
	
	@Test 
	public void testGetTreeWidth() {
		
		Node one = PowerMockito.mock(Node.class);
		Node two = PowerMockito.mock(Node.class);
		Node three = PowerMockito.mock(Node.class);
		Node four = PowerMockito.mock(Node.class);
		LinkedList<Node> oneChildren = new LinkedList<Node>();
		oneChildren.add(two);
		oneChildren.add(three);
		oneChildren.add(four);
		
		doReturn(oneChildren).when(one).getChildren();
		doReturn(new LinkedList<Node>()).when(two).getChildren();
		doReturn(new LinkedList<Node>()).when(three).getChildren();
		doReturn(new LinkedList<Node>()).when(four).getChildren();
		
		Whitebox.setInternalState(tree, "root", one);
		
		assertTrue("Incorrect tree width was returned",tree.getTreeWidth()==3);
		verify(tree).getTreeWidth();
		
	}
	
	@Test
	public void testSetAndGetPathCosts() {
		Whitebox.setInternalState(tree, "pathCosts", new LinkedList<Node>());

		Queue<Integer> list = new LinkedList<Integer>();
		for(int i=0;i<5;i++) {
			list.add(i);
		}
		tree.setPathCosts(list);
		assertEquals("Path costs list size was incorrect",list.size(),tree.getPathCosts().size());
		assertTrue("Path costs list was not equal to the set list",list.equals(Whitebox.getInternalState(tree, "pathCosts")));
		
	}
	
}
