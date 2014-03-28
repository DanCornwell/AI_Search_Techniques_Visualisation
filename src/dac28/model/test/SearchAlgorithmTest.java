package dac28.model.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
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
		algorithm = PowerMockito.mock(SearchAlgorithm.class,Mockito.CALLS_REAL_METHODS);
	}

	@Test
	public void testGetCurrentNodeReturnsCurrentNode() {
		// Initialise the current node
		Node currentNode = PowerMockito.mock(Node.class);
		when(currentNode.getValue()).thenReturn("5");
		Whitebox.setInternalState(algorithm, "currentNode", currentNode);

		assertEquals("getCurrentNode did not return the currentNode","5",algorithm.getCurrentNode().getValue());

		// Checks algorithm calls getCurrentNode
		verify(algorithm).getCurrentNode();
	}

	@Test
	public void testAtGoalReturnsTrueAtGoalFalseOtherwise() {
		// Initialise new current node
		Node node = PowerMockito.mock(Node.class);
		// Initialise goal value
		Whitebox.setInternalState(algorithm, "GOAL", "4");
		// Set current node to a non goal value
		when(node.getValue()).thenReturn("2");
		Whitebox.setInternalState(algorithm, "currentNode", node);
		// Checks incorrect node is not the goal
		assertFalse("At goal node when shouldn't be",algorithm.atGoal());

		// Sets current node to goal value
		when(node.getValue()).thenReturn("4");
		// Checks incorrect node is not the goal
		assertTrue("Was not at goal node when we should be",algorithm.atGoal());

		// Checks algorithm calls atGoal
		verify(algorithm,times(2)).atGoal();
	}

	@Test
	public void testGetVisitedReturnsVisitedList() {
		// Initialise visited
		Whitebox.setInternalState(algorithm, "visited", new LinkedList<Node>());

		// Checks getVisited returns a linked list with the visited list elements
		assertTrue("Did not return the visited linked list",algorithm.getVisited().equals(Whitebox.getInternalState(algorithm, "visited")));

		// Checks algorithm calls getVisited
		verify(algorithm).getVisited();
	}

	@Test
	public void testGetExpandedReturnsExpandedList() {
		// Initialise expanded
		Whitebox.setInternalState(algorithm, "expanded", Mockito.mock(List.class));

		when(algorithm.getExpanded()).thenCallRealMethod();
		// Checks getVisited returns a linked list with the visited list elements
		assertTrue("Did not return the expanded list",algorithm.getExpanded().equals(Whitebox.getInternalState(algorithm, "expanded")));

		// Checks algorithm calls getVisited
		verify(algorithm).getExpanded();
	}

	@Test
	public void testResetClearsListAndSetsRoot() {
		// Initialise variables
		Node node = PowerMockito.mock(Node.class);
		when(node.getValue()).thenReturn("5");
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

		algorithm.reset();

		// Verify reset method calls list methods and sets the current node to the root
		verify(visited).clear();
		verify(expanded).clear();
		verify(expanded).add(node);
		verify(memento).clear();
		assertTrue("Current node was not the root",algorithm.getCurrentNode().getValue() == node.getValue());

		// Verify reset is called
		verify(algorithm).reset();
	}

	@Test
	public void testCanUndoCallsIsEmpty() {
		// Initialise memento
		@SuppressWarnings("unchecked")
		Stack<Node> memento = Mockito.mock(Stack.class);
		Whitebox.setInternalState(algorithm, "mementos", memento);

		algorithm.canUndo();

		// Verify call to canUndo and to the memento stack isEmpty
		verify(algorithm).canUndo();
		verify(memento).isEmpty();
	}

	@Test
	public void testUndoPopsMementoStack() {
		// Initialise memento
		@SuppressWarnings( "rawtypes" )
		Stack memento = Mockito.mock(Stack.class);
		Whitebox.setInternalState(algorithm, "mementos", memento);
		@SuppressWarnings("unchecked")
		List<Node> expanded = mock(List.class);
		Whitebox.setInternalState(algorithm, "expanded", expanded);

		// Get the memento inner class
		@SuppressWarnings("rawtypes")
		Class inner = null;
		try {
			inner = Whitebox.getInnerClassType(SearchAlgorithm.class, "Memento");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Mocks the inner class and makes the memento pop this mock
		@SuppressWarnings("unchecked")
		Object o = PowerMockito.mock(inner);
		LinkedList<Node> list = new LinkedList<Node>();
		list.add(mock(Node.class));
		Whitebox.setInternalState(o, "STATE_EXPANDED", list);
		Node currentNode = mock(Node.class);
		Whitebox.setInternalState(o, "STATE_CURRENT_NODE", currentNode);
		LinkedList<Node> visited = new LinkedList<Node>();
		Whitebox.setInternalState(o, "STATE_VISITED", visited);
		
		when(memento.pop()).thenReturn(o);
		when(memento.isEmpty()).thenReturn(false);
		
		// Call undo
		algorithm.undo();
		
		// Verify undo is called, and verify list methods and reassignment is done within undo
		verify(memento).pop();
		verify(expanded).clear();
		ArgumentCaptor<Node> arg = ArgumentCaptor.forClass(Node.class);
		verify(expanded).add(arg.capture());
		assertEquals("Incorrect argument was added",list.getFirst(),arg.getValue());
		assertEquals("Undo did not reassign currentNode",Whitebox.getInternalState(algorithm, "currentNode"),currentNode);
		assertEquals("Undo did not reassign visited list",Whitebox.getInternalState(algorithm, "visited"),visited);
		
		verify(algorithm).undo();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testStepPushesMementoAndCallsAlgorithmLogic() {
		// Initialise memento
		@SuppressWarnings( "rawtypes" )
		Stack memento = Mockito.mock(Stack.class);
		Whitebox.setInternalState(algorithm, "mementos", memento);
		List<Node> expanded = mock(List.class);
		Whitebox.setInternalState(algorithm, "expanded", expanded);
		Node node = PowerMockito.mock(Node.class);
		// Initialise goal value
		Whitebox.setInternalState(algorithm, "GOAL", "4");
		// Set current node to a non goal value
		when(node.getValue()).thenReturn("2");
		Whitebox.setInternalState(algorithm, "currentNode", node);
		doReturn(new LinkedList<Node>()).when(algorithm).getVisited();
		
		// make expanded list say it is not empty, allow call to the abstract method 
		when(expanded.isEmpty()).thenReturn(false);
		try {
			PowerMockito.doNothing().when(algorithm,"algorithmLogic");
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		algorithm.step();
		
		// verify something is pushed to the memento
		verify(memento).push(any(Object.class));
		// verify step is called
		verify(algorithm).step();
		try {
			// verify algorithmLogic is called
			PowerMockito.verifyPrivate(algorithm).invoke("algorithmLogic");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testStepDoesntWorkWhenConditionsNotMet() {

		@SuppressWarnings( "rawtypes" )
		Stack memento = Mockito.mock(Stack.class);
		Whitebox.setInternalState(algorithm, "mementos", memento);
		when(memento.push(any(Object.class))).thenReturn(null);
		List<Node> expanded = mock(List.class);
		Whitebox.setInternalState(algorithm, "expanded", expanded);
		try {
			PowerMockito.doNothing().when(algorithm,"algorithmLogic");
		} catch (Exception e) {
			e.printStackTrace();
		}
		doReturn(true).when(algorithm).atGoal();
		when(expanded.isEmpty()).thenReturn(false);	
		doReturn(new LinkedList<Node>()).when(algorithm).getVisited();
		// verify a call to step calls mementos push and algorithmLogic
		algorithm.step();
		verify(memento,never()).push(any(Object.class));
		try {
			// verify algorithmLogic is called
			PowerMockito.verifyPrivate(algorithm,never()).invoke("algorithmLogic");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testStepWorksWhenConditionsMet() {

		@SuppressWarnings( "rawtypes" )
		Stack memento = Mockito.mock(Stack.class);
		Whitebox.setInternalState(algorithm, "mementos", memento);
		when(memento.push(any(Object.class))).thenReturn(null);
		List<Node> expanded = mock(List.class);
		Whitebox.setInternalState(algorithm, "expanded", expanded);
		try {
			PowerMockito.doNothing().when(algorithm,"algorithmLogic");
		} catch (Exception e) {
			e.printStackTrace();
		}
		doReturn(false).when(algorithm).atGoal();
		when(expanded.isEmpty()).thenReturn(false);	
		doReturn(new LinkedList<Node>()).when(algorithm).getVisited();
		// verify a call to step calls mementos push and algorithmLogic
		algorithm.step();
		algorithm.step();
		verify(memento,times(2)).push(any(Object.class));
		try {
			// verify algorithmLogic is called
			PowerMockito.verifyPrivate(algorithm,times(2)).invoke("algorithmLogic");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
