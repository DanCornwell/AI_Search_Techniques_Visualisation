package dac28.model.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import dac28.model.Node;
import dac28.model.SearchAlgorithm;
import dac28.model.Tree;

/**
 * Tests the implemented search algorithm methods.
 * 
 * @author Dan Cornwell
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Tree.class,Node.class})
@PowerMockIgnore("org.apache.log4j.*")
public class ConcreteSearchAlgorithmTest {

	private SearchAlgorithm bfs,dfs;

	@Before
	public void setup() {
		Tree tree = PowerMockito.mock(Tree.class);
		Node root = PowerMockito.mock(Node.class);
		Node left = PowerMockito.mock(Node.class);
		Node right = PowerMockito.mock(Node.class);
		Node left_left = PowerMockito.mock(Node.class);

		doReturn(0).when(root).getValue();
		doReturn(new LinkedList<Node>(Arrays.asList(left,right))).when(root).getChildren();
		doReturn(1).when(left).getValue();
		doReturn(new LinkedList<Node>(Arrays.asList(left_left))).when(left).getChildren();
		doReturn(2).when(right).getValue();
		doReturn(new LinkedList<Node>()).when(right).getChildren();
		doReturn(3).when(left_left).getValue();
		doReturn(new LinkedList<Node>()).when(left_left).getChildren();

		try {
			PowerMockito.doReturn(true).when(root, "hasChild");
			PowerMockito.doReturn(true).when(left, "hasChild");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		doReturn(3).when(tree).getGoal();
		doReturn(root).when(tree).getRoot();


	}

	@Test
	public void testBFSAlgorithmLogic() {

		LinkedList<Node> expanded = Whitebox.getInternalState(bfs, "expanded");
		LinkedList<Node> expandedSpy = PowerMockito.spy(expanded);
		Whitebox.setInternalState(bfs, "expanded", expandedSpy);
		LinkedList<Node> visited = Whitebox.getInternalState(bfs, "visited");
		LinkedList<Node> visitedSpy = PowerMockito.spy(visited);
		Whitebox.setInternalState(bfs, "visited", visitedSpy);
		Node currentNode = Whitebox.getInternalState(bfs, "currentNode");

		Node nextToExpanded = expanded.peek();
		try {
			Whitebox.invokeMethod(bfs, "algorithmLogic");
		}
		catch (Exception e) {
		}
		assertEquals("Current node was not set to the next node",nextToExpanded,currentNode);
		verify(visitedSpy).add(any(Node.class));
		try {
			PowerMockito.verifyPrivate(currentNode).invoke("hasChild");
		} catch (Exception e) {
			e.printStackTrace();
		}
		verify(expandedSpy,atLeast(1)).add(any(Node.class));
	}
	
	public void testDFSAlgorithmLogic() {
		
		LinkedList<Node> expanded = Whitebox.getInternalState(dfs, "expanded");
		LinkedList<Node> expandedSpy = PowerMockito.spy(expanded);
		Whitebox.setInternalState(dfs, "expanded", expandedSpy);
		LinkedList<Node> visited = Whitebox.getInternalState(dfs, "visited");
		LinkedList<Node> visitedSpy = PowerMockito.spy(visited);
		Whitebox.setInternalState(dfs, "visited", visitedSpy);
		Node currentNode = Whitebox.getInternalState(dfs, "currentNode");

		Node nextToExpanded = expanded.peek();
		try {
			Whitebox.invokeMethod(dfs, "algorithmLogic");
		}
		catch (Exception e) {
		}
		assertEquals("Current node was not set to the next node",nextToExpanded,currentNode);
		verify(visitedSpy).add(any(Node.class));
		try {
			PowerMockito.verifyPrivate(currentNode).invoke("hasChild");
		} catch (Exception e) {
			e.printStackTrace();
		}
		verify(expandedSpy,atLeast(1)).add(any(Node.class));
		
	}

	@Test
	public void testBFSFinalExpandedAndVisitedLists() {

		bfs.auto();
		int[] expandedFinal = {};
		int[] visitedFinal = {0,1,2,3};
		for(int i=0;i<bfs.getExpanded().size();i++) {
			assertTrue("An expanded value did not match",bfs.getExpanded().get(i).getValue() == expandedFinal[i]);
		}
		for(int j=0;j<bfs.getVisited().size();j++) {
			assertTrue("An visited value did not match",bfs.getVisited().get(j).getValue() == visitedFinal[j]);
		}
		
	}
	
	@Test
	public void testDFSFinalExpandedAndVisitedLists() {
		
		dfs.auto();
		int[] expandedFinal = {2};
		int[] visitedFinal = {0,1,3};
		for(int i=0;i<dfs.getExpanded().size();i++) {
			assertTrue("An expanded value did not match",dfs.getExpanded().get(i).getValue() == expandedFinal[i]);
		}
		for(int j=0;j<dfs.getVisited().size();j++) {
			assertTrue("An visited value did not match",dfs.getVisited().get(j).getValue() == visitedFinal[j]);
		}
		
	}

}
