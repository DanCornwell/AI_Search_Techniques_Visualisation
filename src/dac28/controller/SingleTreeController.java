package dac28.controller;

import dac28.model.SearchAlgorithm;
import dac28.model.Tree;
import dac28.view.TreeDisplay;

public class SingleTreeController extends TreeController {

	public SingleTreeController(SearchAlgorithm[] searchAlgorithms, Tree tree,TreeDisplay treeDisplay) {
		super(searchAlgorithms, tree, treeDisplay);
	}

	@Override
	void setAlgorithms(SearchAlgorithm[] searchAlgorithms) {
		treeDisplay.setAlgorithm(searchAlgorithms);

	}

	
	
}
