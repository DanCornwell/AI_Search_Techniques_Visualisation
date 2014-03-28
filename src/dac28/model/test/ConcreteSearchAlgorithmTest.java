package dac28.model.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Stack;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import dac28.controller.TextFileReader;
import dac28.model.Node;
import dac28.model.SearchAlgorithm;
import dac28.model.SearchAlgorithmCreator;
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

	private Tree tree;

	@Before
	public void setup() {
		tree = PowerMockito.mock(Tree.class);
		Node root = PowerMockito.mock(Node.class);
		Node left = PowerMockito.mock(Node.class);
		Node right = PowerMockito.mock(Node.class);
		Node left_left = PowerMockito.mock(Node.class);

		doReturn("0").when(root).getValue();
		doReturn(new LinkedList<Node>(Arrays.asList(left,right))).when(root).getChildren();
		doReturn("1").when(left).getValue();
		doReturn(new LinkedList<Node>(Arrays.asList(left_left))).when(left).getChildren();
		doReturn("2").when(right).getValue();
		doReturn(new LinkedList<Node>()).when(right).getChildren();
		doReturn("3").when(left_left).getValue();
		doReturn(new LinkedList<Node>()).when(left_left).getChildren();

		try {
			PowerMockito.doReturn(true).when(root, "hasChild");
			PowerMockito.doReturn(true).when(left, "hasChild");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		doReturn("3").when(tree).getGoal();
		doReturn(root).when(tree).getRoot();
		
	}

	@Test
	public void testBFSAlgorithmLogic() {
		
		// have to use the search algorithm creator here as concrete implementations are hidden
		// also needs use of the text file reader to avoid static ids
		int bfsID = TextFileReader.getAlgorithms().indexOf("BreadthFirstSearch");
		assertTrue("Algorithm was not found in list",bfsID!=-1);
		SearchAlgorithm bfs = SearchAlgorithmCreator.getInstance().getAlgorithm(bfsID, tree);
		
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
	
	@Test
	public void testDFSAlgorithmLogic() {
		
		// have to use the search algorithm creator here as concrete implementations are hidden
		// also needs use of the text file reader to avoid static ids		
		int dfsID = TextFileReader.getAlgorithms().indexOf("DepthFirstSearch");
		assertTrue("Algorithm was not found in list",dfsID!=-1);
		SearchAlgorithm dfs = SearchAlgorithmCreator.getInstance().getAlgorithm(dfsID, tree);
		
		Stack<Node> expanded = Whitebox.getInternalState(dfs, "expanded");
		Stack<Node> expandedSpy = PowerMockito.spy(expanded);
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
		verify(expandedSpy,atLeast(1)).push(any(Node.class));
		
	}

	@Test
	public void testIterativeDeepeningAlgorithmLogic() {
		
		// have to use the search algorithm creator here as concrete implementations are hidden
		// also needs use of the text file reader to avoid static ids		
		int deepID = TextFileReader.getAlgorithms().indexOf("IterativeDeepeningSearch");
		assertTrue("Algorithm was not found in list",deepID!=-1);
		SearchAlgorithm deep = SearchAlgorithmCreator.getInstance().getAlgorithm(deepID, tree);
		
		Stack<Node> expanded = Whitebox.getInternalState(deep, "expanded");
		Stack<Node> expandedSpy = PowerMockito.spy(expanded);
		Whitebox.setInternalState(deep, "expanded", expandedSpy);
		LinkedList<Node> visited = Whitebox.getInternalState(deep, "visited");
		LinkedList<Node> visitedSpy = PowerMockito.spy(visited);
		Whitebox.setInternalState(deep, "visited", visitedSpy);
		Node currentNode = Whitebox.getInternalState(deep, "currentNode");		
		
		Node nextToExpanded = expanded.peek();
		try {
			Whitebox.invokeMethod(deep, "algorithmLogic");
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
		verify(expandedSpy,atLeast(1)).add(any(Integer.class),any(Node.class));
		
	}
	
	@Test
	public void testBFSFinalExpandedAndVisitedLists() {

		int bfsID = TextFileReader.getAlgorithms().indexOf("BreadthFirstSearch");
		assertTrue("Algorithm was not found in list",bfsID!=-1);
		SearchAlgorithm bfs = SearchAlgorithmCreator.getInstance().getAlgorithm(bfsID, tree);
		
		while(!bfs.atGoal() && !bfs.getExpanded().isEmpty()) bfs.step();
		String[] expandedFinal = {};
		String[] visitedFinal = {"0","1","2","3"};
		for(int i=0;i<bfs.getExpanded().size();i++) {
			assertTrue("An expanded value did not match",bfs.getExpanded().get(i).getValue().equals(expandedFinal[i]));
		}
		for(int j=0;j<bfs.getVisited().size();j++) {
			assertTrue("An visited value did not match",bfs.getVisited().get(j).getValue().equals(visitedFinal[j]));
		}
		
	}
	
	@Test
	public void testDFSFinalExpandedAndVisitedLists() {
		
		int dfsID = TextFileReader.getAlgorithms().indexOf("DepthFirstSearch");
		assertTrue("Algorithm was not found in list",dfsID!=-1);
		SearchAlgorithm dfs = SearchAlgorithmCreator.getInstance().getAlgorithm(dfsID, tree);
		
		while(!dfs.atGoal() && !dfs.getExpanded().isEmpty()) dfs.step();
		String[] expandedFinal = {"2"};
		String[] visitedFinal = {"0","1","3"};
		for(int i=0;i<dfs.getExpanded().size();i++) {
			assertTrue("An expanded value did not match",dfs.getExpanded().get(i).getValue().equals(expandedFinal[i]));
		}
		for(int j=0;j<dfs.getVisited().size();j++) {
			assertTrue("An visited value did not match",dfs.getVisited().get(j).getValue().equals(visitedFinal[j]));
		}
		
	}

}
