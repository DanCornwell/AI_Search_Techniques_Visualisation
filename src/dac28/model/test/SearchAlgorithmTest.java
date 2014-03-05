package dac28.model.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

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
import dac28.model.SearchAlgorithm;

@RunWith(PowerMockRunner.class)
@PrepareForTest({SearchAlgorithm.class,Node.class})
@PowerMockIgnore("org.apache.log4j.*")
public class SearchAlgorithmTest {

	private SearchAlgorithm algorithm;

	@Before
	public void setup() {
		algorithm = PowerMockito.mock(SearchAlgorithm.class);
	}

	@Test
	public void testGetCurrentNodeReturnsCurrentNode() {
		// Initialise the current node
		Node node = Mockito.mock(Node.class);
		Whitebox.setInternalState(node, "VALUE", 5);
		Whitebox.setInternalState(algorithm, "currentNode", node);
		
		Mockito.when(algorithm.getCurrentNode()).thenCallRealMethod();
		// Checks that getCurrentNode returns a node object
		assertEquals("getCurrentNode did not return a node",node.getValue(),algorithm.getCurrentNode().getValue());

		// Checks algorithm calls getCurrentNode
		Mockito.verify(algorithm).getCurrentNode();
	}

	@Test
	public void testAtGoalReturnsTrueAtGoalFalseOtherwise() {
		// Initialise new current node
		Node node = Mockito.mock(Node.class);
		// Initialise goal value
		Whitebox.setInternalState(algorithm, "GOAL", 4);
		// Set current node to a non goal value
		Whitebox.setInternalState(node, "VALUE", 2);
		Whitebox.setInternalState(algorithm, "currentNode", node);
		
		Mockito.when(algorithm.atGoal()).thenCallRealMethod();
		// Checks incorrect node is not the goal
		assertFalse("At goal node when shouldn't be",algorithm.atGoal());
		
		// Sets current node to goal value
		Whitebox.setInternalState(node, "VALUE", 4);
		// Checks incorrect node is not the goal
		assertTrue("Was not at goal node when we should be",algorithm.atGoal());

		// Checks algorithm calls atGoal
		Mockito.verify(algorithm,times(2)).atGoal();
	}

	@Test
	public void testGetVisitedReturnsVisitedList() {
		// Initialise visited
		Whitebox.setInternalState(algorithm, "visited", new LinkedList<Node>());
		
		Mockito.when(algorithm.getVisited()).thenCallRealMethod();
		// Checks getVisited returns a linked list with the visited list elements
		assertTrue("Did not return the visited linked list",algorithm.getVisited().equals(Whitebox.getInternalState(algorithm, "visited")));

		// Checks algorithm calls getVisited
		Mockito.verify(algorithm).getVisited();
	}

	@Test
	public void testAutoCallsStep() {
				
		// Causes atGoal to return false, false, true - i.e 2 loops 
		PowerMockito.when(algorithm.atGoal()).thenReturn(false,false,true);
		// Initialise expanded - curiously expanded.isEmpty() returns false, despite its size being 0
		Whitebox.setInternalState(algorithm, "expanded", Mockito.mock(List.class));
		
		Mockito.doCallRealMethod().when(algorithm).auto();
		algorithm.auto();
		// Checks algorithm calls auto
		Mockito.verify(algorithm).auto(); 
		// Checks algorithm calls step twice
		Mockito.verify(algorithm,times(2)).step();
	}
	
	@Test
	public void testGetExpandedReturnsExpandedList() {
		// Initialise expanded
		Whitebox.setInternalState(algorithm, "expanded", Mockito.mock(List.class));
		
		Mockito.when(algorithm.getExpanded()).thenCallRealMethod();
		// Checks getVisited returns a linked list with the visited list elements
		assertTrue("Did not return the expanded list",algorithm.getExpanded().equals(Whitebox.getInternalState(algorithm, "expanded")));

		// Checks algorithm calls getVisited
		Mockito.verify(algorithm).getExpanded();
	}

	@Test
	public void testResetClearsListAndSetsRoot() {
		// Initialise variables
		Node node = Mockito.mock(Node.class);
		Whitebox.setInternalState(node, "VALUE", 5);
		Whitebox.setInternalState(algorithm, "ROOT", node);
		@SuppressWarnings("unchecked")
		LinkedList<Node> visited = Mockito.mock(LinkedList.class);
		@SuppressWarnings("unchecked")
		List<Node> expanded = Mockito.mock(List.class);
		@SuppressWarnings("unchecked")
		Stack<Node> memento = Mockito.mock(Stack.class);
		Whitebox.setInternalState(algorithm, "visited", visited);
		Whitebox.setInternalState(algorithm, "mementos", memento);
		Whitebox.setInternalState(algorithm, "expanded", expanded);
		
		// Set mock to call real method, then call reset
		Mockito.when(algorithm.getCurrentNode()).thenCallRealMethod();
		Mockito.doCallRealMethod().when(algorithm).reset();
		algorithm.reset();
		
		// Verify reset method calls list methods and sets the current node to the root
		Mockito.verify(visited).clear();
		Mockito.verify(expanded).clear();
		Mockito.verify(expanded).add(node);
		Mockito.verify(memento).clear();
		assertTrue("Current node was not the root",algorithm.getCurrentNode().getValue() == node.getValue());
		
		// Verify reset is called
		verify(algorithm).reset();
	}

}
