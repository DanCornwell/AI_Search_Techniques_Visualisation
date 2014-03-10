package dac28.controller;

import dac28.model.SearchAlgorithm;
import dac28.model.Tree;
import dac28.view.TreeDisplay;

public abstract class TreeController {

	/**
	 * The tree display being used.
	 */
	TreeDisplay treeDisplay;	
	
	public TreeController(SearchAlgorithm[] searchAlgorithms, Tree tree, TreeDisplay treeDisplay) {
		
		this.treeDisplay = treeDisplay;
		this.treeDisplay.setTree(tree);
		this.treeDisplay.drawTree();
		setAlgorithms(searchAlgorithms);
	}	
	
	abstract void setAlgorithms(SearchAlgorithm[] searchAlgorithms);
	
	/**
	 * Draws the tree onto the tree display.
	 */
	void drawTree() {
		treeDisplay.drawTree();
	}

}
