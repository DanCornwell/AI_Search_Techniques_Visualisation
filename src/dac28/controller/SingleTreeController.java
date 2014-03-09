package dac28.controller;

import dac28.model.SearchAlgorithm;
import dac28.model.Tree;
import dac28.view.TreeDisplay;

public class SingleTreeController extends TreeController {

	public SingleTreeController(SearchAlgorithm searchAlgorithm, Tree tree, TreeDisplay treeDisplay) {
		
		this.treeDisplay = treeDisplay;
		this.treeDisplay.setTree(tree);
		this.treeDisplay.setAlgorithm(searchAlgorithm);
		this.treeDisplay.drawTree();
		
	}
	
}
