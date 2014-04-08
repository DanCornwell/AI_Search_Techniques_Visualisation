package dac28.model.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import dac28.support.TextFileReader;
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
		Node child = PowerMockito.mock(Node.class);
		
		doReturn("0").when(root).getValue();
		LinkedList<Node> list = new LinkedList<Node>();
		list.add(child);
		doReturn(list).when(root).getChildren();
		
		doReturn("1").when(child).getValue();

		try {
			PowerMockito.doReturn(true).when(root, "hasChild");
			PowerMockito.doReturn(false).when(child, "hasChild");
		} catch (Exception e) {
			e.printStackTrace();
		}

		doReturn("GOAL").when(tree).getGoal();
		doReturn(root).when(tree).getRoot();

	}

	@Test
	public void testBFSAlgorithmLogic() {

		int bfsID = TextFileReader.getAlgorithms().indexOf("BreadthFirstSearch");
		assertTrue("Algorithm was not found in list",bfsID!=-1);
		SearchAlgorithm bfs = SearchAlgorithmCreator.getInstance().getAlgorithm(bfsID, tree);
		
		List<Node> expanded = Whitebox.getInternalState(bfs, "expanded");
		List<Node> expandedSpy = PowerMockito.spy(expanded);
		Whitebox.setInternalState(bfs, "expanded", expandedSpy);
		LinkedList<Node> visited = Whitebox.getInternalState(bfs, "visited");
		LinkedList<Node> visitedSpy = PowerMockito.spy(visited);
		Whitebox.setInternalState(bfs, "visited", visitedSpy);
		Node currentNode = Whitebox.getInternalState(bfs, "currentNode");

		Node nextToExpanded = expanded.get(0);
		try {
			Whitebox.invokeMethod(bfs, "algorithmStepLogic");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals("Current node was not set to the next node",nextToExpanded,currentNode);
		verify(visitedSpy).add(currentNode);
		try {
			PowerMockito.verifyPrivate(currentNode).invoke("hasChild");
		} catch (Exception e) {
			e.printStackTrace();
		}
		verify(expandedSpy).add(currentNode.getChildren().getFirst());
	}
	
	@Test
	public void testDFSAlgorithmLogic() {
	
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
			Whitebox.invokeMethod(dfs, "algorithmStepLogic");
		}
		catch (Exception e) {
		}
		assertEquals("Current node was not set to the next node",nextToExpanded,currentNode);
		verify(visitedSpy).add(currentNode);
		try {
			PowerMockito.verifyPrivate(currentNode).invoke("hasChild");
		} catch (Exception e) {
			e.printStackTrace();
		}
		verify(expandedSpy).push(currentNode.getChildren().getFirst());

	}

	@SuppressWarnings("unchecked")
	@Test
	public void testIterativeDeepeningAlgorithmLogic() {
	
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
		Whitebox.setInternalState(deep, "iteration", 2);
		
		Node nextToExpanded = expanded.peek();
		try {
			Whitebox.invokeMethod(deep, "algorithmStepLogic");
		}
		catch (Exception e) {
		}
		assertEquals("Current node was not set to the next node",nextToExpanded,currentNode);
		verify(visitedSpy).add(currentNode);
		try {
			PowerMockito.verifyPrivate(currentNode).invoke("hasChild");
		} catch (Exception e) {
			e.printStackTrace();
		}
		verify(expandedSpy).push(currentNode.getChildren().getFirst());
		
		assertTrue("previous visited size was incorrect",((int)Whitebox.getInternalState(deep, "prevVisitedSize"))==((LinkedList<Node>) Whitebox.getInternalState(deep, "visited")).size());
		verify(expandedSpy).isEmpty();
		verify(expandedSpy).push(any(Node.class));
	}

	@Test
	public void testUniformCostSearchAlgorithmLogic() {
	
		doReturn(new LinkedList<Integer>()).when(tree).getPathCosts();
		
		int costID = TextFileReader.getAlgorithms().indexOf("UniformCostSearch");
		assertTrue("Algorithm was not found in list",costID!=-1);
		SearchAlgorithm cost = SearchAlgorithmCreator.getInstance().getAlgorithm(costID, tree);
		
		LinkedList<Node> expanded = Whitebox.getInternalState(cost, "expanded");
		LinkedList<Node> expandedSpy = PowerMockito.spy(expanded);
		Whitebox.setInternalState(cost, "expanded", expandedSpy);
		LinkedList<Node> visited = Whitebox.getInternalState(cost, "visited");
		LinkedList<Node> visitedSpy = PowerMockito.spy(visited);
		Whitebox.setInternalState(cost, "visited", visitedSpy);
		Node currentNode = Whitebox.getInternalState(cost, "currentNode");

		Node nextToExpanded = expanded.get(0);
		try {
			Whitebox.invokeMethod(cost, "algorithmStepLogic");
		}
		catch (Exception e) {
		}
		assertEquals("Current node was not set to the next node",nextToExpanded,currentNode);
		verify(visitedSpy).add(currentNode);
		try {
			PowerMockito.verifyPrivate(currentNode).invoke("hasChild");
		} catch (Exception e) {
			e.printStackTrace();
		}
		verify(expandedSpy).add(currentNode.getChildren().getFirst());
		
		verify(expandedSpy).remove(any(Node.class));
		verify(expandedSpy).add(eq(0), any(Node.class));
		
		
	}

}
