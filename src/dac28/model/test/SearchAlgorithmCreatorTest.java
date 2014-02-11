package dac28.model.test;

import static org.junit.Assert.*;

import org.junit.Test;

import dac28.model.BreadthFirstSearchCreator;
import dac28.model.DepthFirstSearchCreator;
import dac28.model.SearchAlgorithm;
import dac28.model.Tree124Creator;


public class SearchAlgorithmCreatorTest {

	@Test
	public void testSearchAlgorithmCreator() {

		//BFS creator test
		SearchAlgorithm bfs = new BreadthFirstSearchCreator().getSearchAlgorithm(new Tree124Creator().getTree(5));	
		assertEquals("Starting node was not ROOT node", 0, bfs.getCurrentNode().getValue());
		bfs.auto();
		assertEquals("BFS did not find the goal node",5,bfs.getCurrentNode().getValue());
		//DFS creator test
		SearchAlgorithm dfs = new DepthFirstSearchCreator().getSearchAlgorithm(new Tree124Creator().getTree(5));
		assertEquals("Root nodes did not match", 0, dfs.getCurrentNode().getValue());
		dfs.auto();
		assertEquals("BFS did not find the goal node",5,dfs.getCurrentNode().getValue());
		
	}

}
