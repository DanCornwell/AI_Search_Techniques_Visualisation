package dac28.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import dac28.model.SearchAlgorithm;
import dac28.model.Tree;

/**
 * The tree display superclass.
 * Defines a method to return a TreePanel instance, set the tree being used and algorithms being used on the tree,
 * and method to draw the tree onto the panel.
 * 
 * @author Dan Cornwell
 *
 */
public abstract class TreeDisplay {

	/**
	 * The tree panel instance.
	 */
	protected TreePanel treePanel;
	/**
	 * The max width and height of the tree panel instance.
	 */
	protected int maxWidth, maxHeight;
	/**
	 * The tree being displayed onto the tree panel instance. 
	 */
	protected Tree tree = null;
	/**
	 * Colour of the goal node.
	 */
	protected final Color GOAL_NODE = Color.green;
	/**
	 * Colour used for drawing lines and borders. 
	 */
	protected final Color DEFAULT = Color.black;
	/**
	 * The size of the boxes displayed in the tree.
	 */
	protected final int BOXSIZE = 40;
	
	/**
	 * Initialises a tree panel instance.
	 * 
	 * @param WIDTH - the maximum width of the tree panel.
	 * @param HEIGHT - the maximum height of the tree panel.
	 * @return a tree panel instance
	 */
	JPanel initialiseTreePanel(final int WIDTH,final int HEIGHT) {

		maxWidth = WIDTH;
		maxHeight = HEIGHT;

		treePanel = getTreePanel();
		treePanel.setPreferredSize(new Dimension(maxWidth,maxHeight));
		treePanel.setBorder(BorderFactory.createLineBorder(Color.black));
		treePanel.setBackground(Color.white);

		return treePanel;
	}

	/**
	 * Returns a tree panel subclass, defined by subclasses of tree display.
	 * 
	 * @return a tree panel subclass
	 */
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
	 * Assigns the algorithms being used on the tree display.
	 * Subclasses of tree display decide on how many they should use.
	 * E.g single should only accept an array of length one, dual two etc...
	 * 
	 * @param searchAlgorithms - array of search algorithms
	 */
	public abstract void setAlgorithm(SearchAlgorithm[] searchAlgorithms);

	/**
	 * The tree panel superclass.
	 * Defines the method to draw the tree within the tree display,
	 * deferring actual drawing to its subclasses.
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
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			drawTree(g);
		}

		/**
		 * Draws the tree onto the tree panel, as defined by the subclass.
		 * 
		 * @param g - graphics instance
		 */
		abstract void drawTree(Graphics g);

	}
}
