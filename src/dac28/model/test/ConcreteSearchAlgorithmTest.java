package dac28.model.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import dac28.model.BreadthFirstSearchCreator;
import dac28.model.DepthFirstSearchCreator;
import dac28.model.SearchAlgorithm;
import dac28.model.Tree124Creator;

/**
 * Tests the implemented search algorithm methods.
 * 
 * @author Dan Cornwell
 *
 */
public class ConcreteSearchAlgorithmTest {

	private SearchAlgorithm bfs,dfs;

	@Before
	public void setup() {
		bfs = new BreadthFirstSearchCreator().getSearchAlgorithm(new Tree124Creator().getTree(4));
		dfs = new DepthFirstSearchCreator().getSearchAlgorithm(new Tree124Creator().getTree(4));
		
	}

	@Test
	public void testAlgorithmsStopWhenNoNodesLeftAndAtGoalReturnsFalse() {
		//BFS test
		SearchAlgorithm other_bfs = new BreadthFirstSearchCreator().getSearchAlgorithm(new Tree124Creator().getTree(10));
		other_bfs.auto();
		assertFalse("Current node was goal node",other_bfs.atGoal());
		//DFS test
		SearchAlgorithm other_dfs =  new DepthFirstSearchCreator().getSearchAlgorithm(new Tree124Creator().getTree(10));
		other_dfs.auto();
		assertFalse("Current node was goal node",other_dfs.atGoal());
	}

	@Test
	public void testStep() {
		//BFS test
		bfs.step();
		assertEquals("Node value did not match expected node value",0,bfs.getCurrentNode().getValue());
		bfs.step();
		assertEquals("Node value did not match expected node value",1,bfs.getCurrentNode().getValue());
		//DFS test
		dfs.step();
		assertEquals("Node value did not match expected node value",0,dfs.getCurrentNode().getValue());
		dfs.step();
		assertEquals("Node value did not match expected node value",1,dfs.getCurrentNode().getValue());
	}

	@Test
	public void testAutoAndStepReachesGoalAndAtGoalReturnsTrue() {
		// As auto repeatedly calls step(), passing this test verifies both methods reach the goal

		//BFS test
		bfs.auto();
		assertEquals("Node value did not match expected node value",4,bfs.getCurrentNode().getValue());
		assertTrue("Correct node was not the goal node",bfs.atGoal());
		//DFS test
		dfs.auto();
		assertEquals("Node value did not match expected node value",4,dfs.getCurrentNode().getValue());
		assertTrue("Correct node was not the goal node",dfs.atGoal());
	}

	@Test
	public void testStepDoesNotGoPassGoal() {
		//BFS test
		bfs.auto();
		assertEquals("Node value did not match expected node value",4,bfs.getCurrentNode().getValue());
		assertTrue("Correct node was not the goal node",bfs.atGoal());
		bfs.step();
		assertEquals("Step moved from the goal node",4,bfs.getCurrentNode().getValue());
		assertTrue("No longer at the goal node",bfs.atGoal());
		//DFS test
		dfs.auto();
		assertEquals("Node value did not match expected node value",4,dfs.getCurrentNode().getValue());
		assertTrue("Correct node was not the goal node",dfs.atGoal());
		dfs.step();
		assertEquals("Step moved from the goal node",4,dfs.getCurrentNode().getValue());
		assertTrue("No longer at the goal node",dfs.atGoal());
	}

	@Test
	public void testFinalExpandedAndVisitedLists() {
		//BFS test
		bfs.auto();
		assertEquals("Goal node not reached",4,bfs.getCurrentNode().getValue());
		int[] bfsExpVisited = {0,1,2,3};
		int[] bfsExpExpanded = {5,6};
		assertEquals("Visited list was not the expected size",bfsExpVisited.length,bfs.getVisited().size());
		assertEquals("Expected list was not the expected size",bfsExpExpanded.length,bfs.getExpanded().size());
		//safety check
		if(bfsExpVisited.length == bfs.getVisited().size()) {
			for(int i=0;i<bfsExpVisited.length;i++) {
				assertEquals("List element did not match",bfsExpVisited[i],bfs.getVisited().get(i).getValue());
			}
		}
		if(bfsExpExpanded.length == bfs.getExpanded().size()) {
			for(int i=0;i<bfsExpExpanded.length;i++) {
				assertEquals("List element did not match",bfsExpExpanded[i],bfs.getExpanded().get(i).getValue());
			}
		}
		//DFS test
		dfs.auto();
		assertEquals("Goal node not reached",4,dfs.getCurrentNode().getValue());
		int[] dfsExpVisited = {0,1,3};
		int[] dfsExpExpanded = {2};
		assertEquals("Visited list was not the expected size",dfsExpVisited.length,dfs.getVisited().size());
		assertEquals("Expected list was not the expected size",dfsExpExpanded.length,dfs.getExpanded().size());
		//safety check
		if(dfsExpVisited.length == dfs.getVisited().size()) {
			for(int i=0;i<dfsExpVisited.length;i++) {
				assertEquals("List element did not match",dfsExpVisited[i],dfs.getVisited().get(i).getValue());
			}
		}
		if(dfsExpExpanded.length == dfs.getExpanded().size()) {
			for(int i=0;i<dfsExpExpanded.length;i++) {
				assertEquals("List element did not match",dfsExpExpanded[i],dfs.getExpanded().get(i).getValue());
			}
		}
	}

	@Test
	public void testReset() {
		//BFS test
		bfs.auto();
		assertTrue("Goal node not reached",bfs.atGoal());
		assertEquals("Goal node value did not match expected node value",4,bfs.getCurrentNode().getValue());
		assertFalse("Expanded list was empty",bfs.getExpanded().isEmpty());
		assertFalse("Visited list was empty",bfs.getVisited().isEmpty());
		bfs.reset();
		assertFalse("Current node did not reset",bfs.atGoal());
		assertEquals("Root node value did not match expected node value",0,bfs.getCurrentNode().getValue());
		assertTrue("Expanded list did not contain root",bfs.getExpanded().size() == 1);
		assertTrue("Visited list was not empty",bfs.getVisited().isEmpty());
		bfs.auto();
		assertTrue("Goal node not reached",bfs.atGoal());
		assertEquals("Goal node value did not match expected node value",4,bfs.getCurrentNode().getValue());
		assertFalse("Expanded list was empty",bfs.getExpanded().isEmpty());
		assertFalse("Visited list was empty",bfs.getVisited().isEmpty());
		//DFS test
		dfs.auto();
		assertTrue("Goal node not reached",dfs.atGoal());
		assertEquals("Goal node value did not match expected node value",4,dfs.getCurrentNode().getValue());
		assertFalse("Expanded list was empty",dfs.getExpanded().isEmpty());
		assertFalse("Visited list was empty",dfs.getVisited().isEmpty());
		dfs.reset();
		assertFalse("Current node did not reset",dfs.atGoal());
		assertEquals("Root node value did not match expected node value",0,dfs.getCurrentNode().getValue());
		assertTrue("Expanded list did not contain root",dfs.getExpanded().size() == 1);
		assertTrue("Visited list was not empty",dfs.getVisited().isEmpty());
		dfs.auto();
		assertTrue("Goal node not reached",dfs.atGoal());
		assertEquals("Goal node value did not match expected node value",4,dfs.getCurrentNode().getValue());
		assertFalse("Expanded list was empty",dfs.getExpanded().isEmpty());
		assertFalse("Visited list was empty",dfs.getVisited().isEmpty());
	}
	
	@Test
	public void testUndoAndMemento() {
		//BFS test
		bfs.step();
		bfs.step();
		bfs.undo();
		assertTrue("Not back at goal node",bfs.getCurrentNode().getValue() == 0);
		bfs.undo();
		assertTrue("Undo at root node doesn't do anything",bfs.getCurrentNode().getValue() == 0);
		bfs.auto();
		assertTrue("Goal node was not reached",bfs.atGoal());
		bfs.undo();
		assertFalse("Did not undo",bfs.atGoal());
		bfs.step();
		assertTrue("Step did not go to goal node",bfs.atGoal());
		for(int i=0;i<10;i++) {
			bfs.undo();
		}
		assertTrue("undo did not go back to root node",bfs.getCurrentNode().getValue()==0);
		//DFS test
		dfs.step();
		dfs.step();
		dfs.undo();
		assertTrue("Not back at goal node",dfs.getCurrentNode().getValue() == 0);
		dfs.undo();
		assertTrue("Undo at root node doesn't do anything",dfs.getCurrentNode().getValue() == 0);
		dfs.auto();
		assertTrue("Goal node was not reached",dfs.atGoal());
		dfs.undo();
		assertFalse("Did not undo",dfs.atGoal());
		dfs.step();
		assertTrue("Step did not go to goal node",dfs.atGoal());
		for(int i=0;i<10;i++) {
			dfs.undo();
		}
		assertTrue("undo did not go back to root node",dfs.getCurrentNode().getValue()==0);
	}
	
}
