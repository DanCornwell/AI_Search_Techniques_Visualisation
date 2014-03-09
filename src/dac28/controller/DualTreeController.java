package dac28.controller;

import dac28.model.SearchAlgorithm;
import dac28.model.Tree;
import dac28.view.DualTreeDisplay;
import dac28.view.TreeDisplay;

public class DualTreeController extends TreeController {
	
	public DualTreeController(SearchAlgorithm[] searchAlgorithms, Tree tree, TreeDisplay treeDisplay) {
		
		this.treeDisplay = new DualTreeDisplay();
		
		this.treeDisplay = treeDisplay;
		this.treeDisplay.setTree(tree);
		((DualTreeDisplay) this.treeDisplay).setAlgorithms(searchAlgorithms);
		this.treeDisplay.drawTree();
		
	}	
	
}
