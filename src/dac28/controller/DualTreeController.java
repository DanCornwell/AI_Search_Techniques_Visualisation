package dac28.controller;

import dac28.model.SearchAlgorithm;
import dac28.model.Tree;
import dac28.view.DualTreeDisplay;
import dac28.view.TreeDisplay;

public class DualTreeController extends TreeController {

	public DualTreeController(SearchAlgorithm[] searchAlgorithms, Tree tree,TreeDisplay treeDisplay) {
		super(searchAlgorithms, tree, treeDisplay);
	}

	@Override
	void setAlgorithms(SearchAlgorithm[] searchAlgorithms) {
		((DualTreeDisplay) treeDisplay).setAlgorithms(searchAlgorithms);
	}
	

	
}
