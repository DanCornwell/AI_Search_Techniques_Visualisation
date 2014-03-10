package dac28.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import dac28.model.Node;
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
public class DualTreeDisplay extends TreeDisplay {

	// The tree panel instance.
	private TreePanel treePanel;

	private SearchAlgorithm searchAlgorithm1;

	private SearchAlgorithm searchAlgorithm2;
	// The max width and max height of the display.
	private int maxWidth, maxHeight;
	// The tree the display will use.
	private Tree tree = null;

	JPanel initialiseTreePanel(int WIDTH, int HEIGHT) {

		maxWidth = WIDTH;
		maxHeight = HEIGHT;

		treePanel = new DualTreePanel();
		treePanel.setPreferredSize(new Dimension(maxWidth,maxHeight));
		treePanel.setBorder(BorderFactory.createLineBorder(Color.black));
		treePanel.setBackground(Color.white);

		return treePanel;
	}

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
	 * @param searchAlgorithm - list of search algorithms to use.
	 */
	public void setAlgorithms(SearchAlgorithm[] searchAlgorithms) {
		if(searchAlgorithms.length != 2) return;
		this.searchAlgorithm1 = searchAlgorithms[0];
		this.searchAlgorithm2 = searchAlgorithms[1];
	}

	/**
	 * The tree panel class.
	 * Defines the method to draw the tree within the tree display.
	 * 
	 * @author Dan Cornwell
	 *
	 */
	private class DualTreePanel extends TreePanel {

		/**
		 * Random serial number.
		 */
		private static final long serialVersionUID = 8970803915887926117L;

		/**
		 * Draws the tree that the JPanel has an instance variable of.
		 * Will draw each node in a box in its correct place within a tree structure,
		 * and draw lines between parents and children.
		 * 
		 * @param g - Graphics instance
		 */
		@Override
		public void drawTree(Graphics g) {

			// If we have no tree or algorithms return.
			if(tree==null || searchAlgorithm1==null || searchAlgorithm2==null) {
				return;
			}

			// HashMap holding with a point key and point value. Used to draw lines between these 2 points.
			Map<Point,Point> lineCoords = new HashMap<Point,Point>();
			// Holds the line connection coordinates of parent nodes.
			LinkedList<Point> parentCoords = new LinkedList<Point>();

			// The maximum depth of the tree.
			final int TREE_DEPTH = tree.getTreeDepth(tree.getRoot());
			// The size of all the nodes on the tree. These are squares.
			final int BOXSIZE = 40;
			// Colour of the current node for algorithm 1.
			final Color CURRENT_NODE_1 = Color.blue;
			// Colour of the current node for algorithm 2.
			final Color CURRENT_NODE_2 = Color.orange;
			// Colour of the goal node for the algorithms.
			final Color GOAL_NODE = Color.yellow;
			// Default colour.
			final Color DEFAULT = Color.black;

			// Draw title
			g.drawString("Tree Data", (maxWidth/4)-20, 20);
			g.drawLine((maxWidth/4)-20, 22, (maxWidth/4)+28, 22);

			// The x position of the root node.
			final int ROOT_X_POS = (maxWidth/2)-(BOXSIZE/2);
			// The y position of the root node.
			final int ROOT_Y_POS = (maxHeight/TREE_DEPTH)/4;

			// Draws the root node, with its values inside it. 
			// Will also colour the box the appropriate colour.
			g.drawRect(ROOT_X_POS, ROOT_Y_POS, BOXSIZE, BOXSIZE);
			// If root is the goal colour box red.
			if(tree.getRoot().getValue() == tree.getGoal()) {
				g.setColor(GOAL_NODE);
				g.fillRect(ROOT_X_POS+1, ROOT_Y_POS+1, BOXSIZE-1, BOXSIZE-1);
				g.setColor(DEFAULT);
			}
			// Both algorithms at same node
			if(tree.getRoot().getValue() == searchAlgorithm1.getCurrentNode().getValue() && tree.getRoot().getValue() == searchAlgorithm2.getCurrentNode().getValue()) {
				g.setColor(CURRENT_NODE_1);
				g.fillRect(ROOT_X_POS+1, ROOT_Y_POS+1, (BOXSIZE/2)-1, BOXSIZE-1);
				g.setColor(CURRENT_NODE_2);
				g.fillRect(ROOT_X_POS+1+(BOXSIZE/2), ROOT_Y_POS+1, (BOXSIZE/2)-1, BOXSIZE-1);
				g.setColor(DEFAULT);
			}
			else {
				if(tree.getRoot().getValue() == searchAlgorithm2.getCurrentNode().getValue()) {
					g.setColor(CURRENT_NODE_2);
					g.fillRect(ROOT_X_POS+1, ROOT_Y_POS+1, BOXSIZE-1, BOXSIZE-1);
					g.setColor(DEFAULT);
				}
				if(tree.getRoot().getValue() == searchAlgorithm1.getCurrentNode().getValue()) {
					g.setColor(CURRENT_NODE_1);
					g.fillRect(ROOT_X_POS+1, ROOT_Y_POS+1, BOXSIZE-1, BOXSIZE-1);
					g.setColor(DEFAULT);
				}
			}
			g.drawString(String.valueOf(tree.getRoot().getValue()), ROOT_X_POS+(BOXSIZE/2), ROOT_Y_POS+(BOXSIZE/2));

			// The line connection point of the root node. This will be the bottom centre of the box.
			Point rootPoint = new Point(ROOT_X_POS+(BOXSIZE/2),ROOT_Y_POS+BOXSIZE);

			// A list of nodes representing parents.
			LinkedList<Node> parents = new LinkedList<Node>();
			// A list of nodes representing children.
			LinkedList<Node> children = new LinkedList<Node>();

			// Adds the root node to the parent list.
			parents.add(tree.getRoot());
			// Adds the root node line connection point n times, where n is the size of its children list.
			for(int i=0;i<tree.getRoot().getChildren().size();i++) {
				parentCoords.add(rootPoint);
			}

			// Integer representing the node level we are on. Root is considered to be level 0.
			int nodeLevel = 1;

			// While elements exist within parents list.
			while(!parents.isEmpty()) {

				// Remove all elements from parent list and add their children to the children list.
				while(!parents.isEmpty()) {
					children.addAll(parents.remove().getChildren());
				}

				// If the children list is not empty (i.e parents were not all leaf nodes).
				if(!children.isEmpty()) {

					// Get the number of nodes that will be on this level, given by the size of the children list.
					final int NODES_ON_LEVEL = children.size();

					// For all the children on this level.
					for(int i=0;i<NODES_ON_LEVEL;i++) {

						// Gives the nodes x position, using math to give visually pleasing spacing.
						int xPos = (maxWidth/(NODES_ON_LEVEL+1)) + (i*(maxWidth/(NODES_ON_LEVEL+1))) - (BOXSIZE/2);
						// Get the nodes y position by multiplying the node level with the 
						// amount of space each level takes in relation to the max height.
						int yPos = nodeLevel*(maxHeight/TREE_DEPTH);
						// Draw the nodes with their values inside them.
						g.drawRect(xPos, yPos, BOXSIZE, BOXSIZE);
						// If the node is the goal colour it red.
						if(children.get(i).getValue() == tree.getGoal()) {
							g.setColor(GOAL_NODE);
							g.fillRect(xPos+1, yPos+1, BOXSIZE-1, BOXSIZE-1);
							g.setColor(DEFAULT);
						}
						// Both algorithms at same node
						if(children.get(i).getValue() == searchAlgorithm1.getCurrentNode().getValue() && children.get(i).getValue() == searchAlgorithm2.getCurrentNode().getValue()) {
							g.setColor(CURRENT_NODE_1);
							g.fillRect(xPos+1, yPos+1, (BOXSIZE/2)-1, BOXSIZE-1);
							g.setColor(CURRENT_NODE_2);
							g.fillRect(xPos+1+(BOXSIZE/2), yPos+1, (BOXSIZE/2)-1, BOXSIZE-1);
							g.setColor(DEFAULT);
						}
						else {
							if(children.get(i).getValue() == searchAlgorithm2.getCurrentNode().getValue()) {
								g.setColor(CURRENT_NODE_2);
								g.fillRect(xPos+1, yPos+1, BOXSIZE-1, BOXSIZE-1);
								g.setColor(DEFAULT);
							}
							if(children.get(i).getValue() == searchAlgorithm1.getCurrentNode().getValue()) {
								g.setColor(CURRENT_NODE_1);
								g.fillRect(xPos+1, yPos+1, BOXSIZE-1, BOXSIZE-1);
								g.setColor(DEFAULT);
							}
						}
						g.drawString(String.valueOf(children.get(i).getValue()), xPos+(BOXSIZE/2), yPos+(BOXSIZE/2));

						// The child line connection point. This will be the top middle of the box.
						Point childCoord = new Point(xPos+(BOXSIZE/2),yPos);

						// If there are parent coordinates in the list
						if(!parentCoords.isEmpty()) {
							// Get the first parent coordinate.
							Point parentCoord = parentCoords.remove();
							// Add this coordinate with the child's coordinate to the line coordinates HashMap.
							// Note that childCoord is the key, since a parent can have many children but
							// a child can only have one parent.
							lineCoords.put(childCoord, parentCoord);
							// For the size of this child's children list, add its parent coordinate to the 
							// parent coordinates list.
							for(int j=0;j<children.get(i).getChildren().size();j++) {
								// Parent coordinate is its child coordinate but with the box size added to the 
								// y coordinate. AKA the top centre point.
								Point newParentCoord = new Point(childCoord.x,childCoord.y+BOXSIZE);
								parentCoords.add(newParentCoord);
							}
						}	
					}
				}

				// Add all the children to the parents list.
				for(Node newParents: children) {
					parents.add(newParents);
				}
				// Clear the children list.
				children.clear();
				// Increment the node level.
				nodeLevel++;
			}

			// Draw all the lines within the line coordinates HashMap
			for(Entry<Point, Point> lines: lineCoords.entrySet()) {
				g.drawLine(lines.getKey().x, lines.getKey().y, lines.getValue().x, lines.getValue().y);
			}

		}
	}
}

