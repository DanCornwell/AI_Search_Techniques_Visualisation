package dac28.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import dac28.model.SearchAlgorithm;
import dac28.model.Tree;

/**
 * The tree display class.
 * Holds an instance of tree panel inner class which it returns with
 * its initialisers. 
 * 
 * @author Dan Cornwell
 *
 */
public abstract class TreeDisplay {

	// The tree panel instance.
	protected TreePanel treePanel;
	// The max width and max height of the display.
	protected int maxWidth, maxHeight;
	// The tree the display will use.
	protected Tree tree = null;

	JPanel initialiseTreePanel(int WIDTH, int HEIGHT) {

		maxWidth = WIDTH;
		maxHeight = HEIGHT;

		treePanel = getTreePanel();
		treePanel.setPreferredSize(new Dimension(maxWidth,maxHeight));
		treePanel.setBorder(BorderFactory.createLineBorder(Color.black));
		treePanel.setBackground(Color.white);

		return treePanel;
	}
	
	abstract TreePanel getTreePanel();
	
	/**
	 * Sets the tree.
	 * 
	 * @param tree - new tree for this display.
	 */
	public void setTree(Tree tree) {
		this.tree = tree;
	}

	/**
	 * Repaints the tree panel.
	 */
	public void drawTree() {
		treePanel.repaint();
	}

	/**
	 * Sets the search algorithm being used on the tree.
	 * 
	 * @param searchAlgorithms - the search algorithm to use.
	 */
	public abstract void setAlgorithm(SearchAlgorithm[] searchAlgorithms);

	/**
	 * The tree panel class.
	 * Defines the method to draw the tree within the tree display.
	 * 
	 * @author Dan Cornwell
	 *
	 */
	protected abstract class TreePanel extends JPanel {

		/**
		 * Random serial number.
		 */
		private static final long serialVersionUID = 8970803915887926117L;

		/**
		 * Paints the JPanel. Calls the draw tree method.
		 */
		public void paintComponent(final Graphics g) {
			super.paintComponent(g);
			drawTree(g);
		}

		abstract void drawTree(Graphics g);
		
	}
}
