package dac28.model.search_algorithm;

import static org.junit.Assert.*;

import org.junit.Test;

import dac28.model.search_algorithm.concrete_algorithm.BreadthFirstSearchCreator;
import dac28.model.search_algorithm.concrete_algorithm.DepthFirstSearchCreator;
import dac28.model.tree.Tree;
import dac28.model.tree.Tree124Creator;
import dac28.model.tree.TreeCreator;


public class SearchAlgorithmCreatorTest {

	@Test
	public void testSearchAlgorithmCreator() {

		TreeCreator treeCreator = new Tree124Creator();
		Tree tree = treeCreator.getTree(5);
		//BFS creator test
		SearchAlgorithmCreator bfsCreator = new BreadthFirstSearchCreator();
		SearchAlgorithm bfs = bfsCreator.getSearchAlgorithm(tree);	
		assertEquals("Root nodes did not match", tree.getRoot(), bfs.ROOT);
		bfs.auto();
		assertEquals("BFS did not find the goal node",5,bfs.currentNode.getValue());
		//DFS creator test
		SearchAlgorithmCreator dfsCreator = new DepthFirstSearchCreator();
		SearchAlgorithm dfs = dfsCreator.getSearchAlgorithm(tree);
		assertEquals("Root nodes did not match", tree.getRoot(), dfs.ROOT);
		dfs.auto();
		assertEquals("BFS did not find the goal node",5,dfs.currentNode.getValue());
		
	}

}
