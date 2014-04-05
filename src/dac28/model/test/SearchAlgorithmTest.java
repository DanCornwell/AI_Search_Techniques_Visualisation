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
		algorithm = PowerMockito.mock(SearchAlgorithm.class,Mockito.CALLS_REAL_METHODS);
	}

	@Test
	public void testGetCurrentNode() {

		Node currentNode = PowerMockito.mock(Node.class);
		when(currentNode.getValue()).thenReturn("5");
		Whitebox.setInternalState(algorithm, "currentNode", currentNode);

		assertEquals("getCurrentNode did not return the correct value","5",algorithm.getCurrentNode().getValue());
		// verify node get value call
		verify(currentNode).getValue();

	}

	@Test
	public void testAtGoal() {
		
		Node currentNode = PowerMockito.mock(Node.class);
		when(currentNode.getValue()).thenReturn("GOAL");
		Whitebox.setInternalState(algorithm, "currentNode", currentNode);

		String goal = "GOAL";
		Whitebox.setInternalState(algorithm, "GOAL", goal);
		
		
		assertTrue("Node was not goal when it should have been",algorithm.atGoal());
		
		String notGoal = "NOT_GOAL";
		Whitebox.setInternalState(algorithm, "GOAL", notGoal);
		
		assertFalse("Node was goal when it should not have been",algorithm.atGoal());
	
		// verify node get value call
		verify(currentNode,times(2)).getValue();
		
	}

	@Test
	public void testGetVisited() {

		LinkedList<Node> list = new LinkedList<Node>();
		for(int i=0;i<5;i++) {
			list.add(mock(Node.class));
		}
		Whitebox.setInternalState(algorithm, "visited", list);

		assertTrue("Did not return the visited linked list",algorithm.getVisited().equals(Whitebox.getInternalState(algorithm, "visited")));
		
	}

	@Test
	public void testGetExpanded() {
		
		LinkedList<Node> list = new LinkedList<Node>();
		for(int i=0;i<5;i++) {
			list.add(mock(Node.class));
		}
		Whitebox.setInternalState(algorithm, "expanded", list);

		assertTrue("Did not return the expanded linked list",algorithm.getExpanded().equals(Whitebox.getInternalState(algorithm, "expanded")));
		
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testReset() {
		
		LinkedList<Node> visited = mock(LinkedList.class);
		LinkedList<Node> expanded = mock(LinkedList.class);
		Node currentNode = mock(Node.class);
		Node root = mock(Node.class);
		@SuppressWarnings("rawtypes")
		Stack mementos = mock(Stack.class);
		
		Whitebox.setInternalState(algorithm, "expanded", visited);
		Whitebox.setInternalState(algorithm, "visited", expanded);
		Whitebox.setInternalState(algorithm, "currentNode", currentNode);
		Whitebox.setInternalState(algorithm, "ROOT", root);
		Whitebox.setInternalState(algorithm, "mementos", mementos);

		try {
			PowerMockito.doNothing().when(algorithm, "algorithmResetLogic");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
				
		algorithm.reset();
		
		verify(visited).clear();
		verify(expanded).clear();
		assertTrue("Current node was not set to the root",Whitebox.getInternalState(algorithm, "currentNode").equals(Whitebox.getInternalState(algorithm, "ROOT")));
		((LinkedList<Node>) verify(Whitebox.getInternalState(algorithm, "expanded"))).add(root);
		verify(mementos).clear();
		try {
			PowerMockito.verifyPrivate(algorithm).invoke("algorithmResetLogic");
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

	@Test
	public void testCanUndo() {

		@SuppressWarnings("rawtypes")
		Stack mementos = mock(Stack.class);
		when(mementos.isEmpty()).thenReturn(false);
		Whitebox.setInternalState(algorithm, "mementos", mementos);
		
		assertTrue("Returned false when the list was not empty",algorithm.canUndo());
		
		when(mementos.isEmpty()).thenReturn(true);
		
		assertFalse("Returned true when the list was empty",algorithm.canUndo());
		// verify mementos is empty call
		verify(mementos,times(2)).isEmpty();
		
	}

	@Test
	public void testUndo() {
		
		@SuppressWarnings("rawtypes")
		Stack mementos = mock(Stack.class);
		Whitebox.setInternalState(algorithm, "mementos", mementos);
		Node currentNode = mock(Node.class);
		Whitebox.setInternalState(algorithm, "currentNode", currentNode);
		@SuppressWarnings("unchecked")
		List<Node> expanded = mock(LinkedList.class);
		//doNothing().when(expanded.add(any(Node.class)));
		Whitebox.setInternalState(algorithm, "expanded", expanded);
		Whitebox.setInternalState(algorithm, "visited", mock(LinkedList.class));
		
		@SuppressWarnings("rawtypes")
		Class inner = null;
		try {
			inner = Whitebox.getInnerClassType(SearchAlgorithm.class, "Memento");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		@SuppressWarnings("unchecked")
		Object memento = mock(inner);
		Node mementoNode = mock(Node.class);
		Whitebox.setInternalState(memento, "STATE_CURRENT_NODE", mementoNode);
		@SuppressWarnings("unchecked")
		List<Node> visitedMemento = mock(LinkedList.class);
		Whitebox.setInternalState(memento, "STATE_VISITED", visitedMemento);
		@SuppressWarnings("unchecked")
		LinkedList<Node> expandedMemento = mock(LinkedList.class);
		when(expandedMemento.size()).thenReturn(3);
		when(expandedMemento.get(any(int.class))).thenReturn(mock(Node.class));
		Whitebox.setInternalState(memento, "STATE_EXPANDED", expandedMemento);
		
		when(mementos.pop()).thenReturn(memento);
		
		try {
			PowerMockito.doNothing().when(algorithm, "algorithmUndoLogic");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		algorithm.undo();
		
		verify(algorithm).canUndo();
		verify(mementos).pop();
		assertEquals("Current node was not equal to the memento node",Whitebox.getInternalState(algorithm, "currentNode"),mementoNode);
		verify(expanded).clear();
		verify(expanded,times(3)).add(any(Node.class));
		verify(expandedMemento,times(3)).get(any(int.class));
		assertEquals("Visited list was not equal to the memento visited list",Whitebox.getInternalState(algorithm, "visited"),visitedMemento);
		try {
			PowerMockito.verifyPrivate(algorithm).invoke("algorithmUndoLogic");
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testStepWhenConditionsMet() {
		
		LinkedList<Node> expanded = mock(LinkedList.class);
		Whitebox.setInternalState(algorithm, "expanded", expanded);
		// Create mementos mock of an object, as we only want to check mementos.add is called
		Stack<Object> mementos = mock(Stack.class);
		Whitebox.setInternalState(algorithm, "mementos", mementos);
		try {
			PowerMockito.doNothing().when(algorithm, "algorithmStepLogic");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		doReturn(mock(Object.class)).when(mementos).push(any(Object.class));
		doReturn(mock(LinkedList.class)).when(algorithm).getVisited();
		
		doReturn(false).when(algorithm).atGoal();
		when(expanded.isEmpty()).thenReturn(false);
		
		// Test when conditions are met
		algorithm.step();
		
		verify(algorithm).atGoal();
		verify(expanded).isEmpty();
		verify(mementos).push(any(Object.class));
		try {
			PowerMockito.verifyPrivate(algorithm).invoke("algorithmStepLogic");
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		
		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testStepWhenConditionsNotMet() {
		
		LinkedList<Node> expanded = mock(LinkedList.class);
		Whitebox.setInternalState(algorithm, "expanded", expanded);
		// Create mementos mock of an object, as we only want to check mementos.add is called
		Stack<Object> mementos = mock(Stack.class);
		Whitebox.setInternalState(algorithm, "mementos", mementos);
		try {
			PowerMockito.doNothing().when(algorithm, "algorithmStepLogic");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		doReturn(mock(Object.class)).when(mementos).push(any(Object.class));
		doReturn(mock(LinkedList.class)).when(algorithm).getVisited();
		
		doReturn(true).when(algorithm).atGoal();
		when(expanded.isEmpty()).thenReturn(false);
		
		// Test when condition 1 not met
		algorithm.step();
		
		verify(algorithm,atLeast(1)).atGoal();
	
		
		doReturn(false).when(algorithm).atGoal();
		when(expanded.isEmpty()).thenReturn(true);

		// Test when condition 2 not met
		algorithm.step();
		
		verify(algorithm,atLeast(1)).atGoal();
		verify(expanded,atLeast(1)).isEmpty();
	
		
		doReturn(true).when(algorithm).atGoal();
		when(expanded.isEmpty()).thenReturn(true);
		
		// Test when both conditions not met
		algorithm.step();
		
		verify(algorithm,atLeast(1)).atGoal();
		
		
		// verify that algorithm step logic was not called in any algorithm.step
		try {
			PowerMockito.verifyPrivate(algorithm,never()).invoke("algorithmStepLogic");
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		// verify that a memento is not pushed during algorithm.step
		verify(mementos,never()).push(any(Object.class));
		
	}

}
