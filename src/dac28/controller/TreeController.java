package dac28.controller;

import dac28.view.TreeDisplay;

public abstract class TreeController {

	/**
	 * The tree display being used.
	 */
	TreeDisplay treeDisplay;
	
	/**
	 * Draws the tree onto the tree display.
	 */
	void drawTree() {
		treeDisplay.drawTree();
	}

}
