package dac28.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class SearchAlgorithmCreatorTest {

	@Test
	public void testSearchAlgorithmCreator() {

		TreeCreator treeCreator = new Tree124Creator();
		Tree tree = treeCreator.getTree(5);
		SearchAlgorithmCreator bfsCreator = new BreadthFirstSearchCreator();
		SearchAlgorithm bfs = bfsCreator.getSearchAlgorithm(tree);
		
		assertEquals("Root nodes did not match", tree.ROOT, bfs.ROOT);
		bfs.auto();
		assertEquals("BFS did not find the goal node",5,bfs.currentNode.getValue());
		
	}

}
