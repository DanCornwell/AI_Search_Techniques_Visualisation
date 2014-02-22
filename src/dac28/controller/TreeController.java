package dac28.controller;

import dac28.model.Tree;
import dac28.view.TreeDisplay;

public class TreeController {

	TreeDisplay treeDisplay;
	
	public TreeController(Tree tree, TreeDisplay treeDisplay) {
		
		this.treeDisplay = treeDisplay;
		this.treeDisplay.setTree(tree);
		
	}

}
