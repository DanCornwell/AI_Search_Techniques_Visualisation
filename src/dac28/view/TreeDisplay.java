package dac28.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import dac28.model.Node;
import dac28.model.SearchAlgorithm;
import dac28.model.Tree;

/**
 * The tree display class.
 * Defines how to draw a tree with a single algorithm searching on it.
 * Displays with more than one algorithm should extend this class and add in the 
 * new functionality by overriding the draw tree box method.
 * 
 * @author Dan Cornwell
 *
 */
public class TreeDisplay {

	/**
	 * The tree panel instance.
	 */
	protected TreePanel treePanel;
	/**
	 * The scroll pane that the tree panel will be placed in.
	 */
	protected JScrollPane scroller;
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
	 * The search algorithm being used on the tree.
	 */
	protected SearchAlgorithm searchAlgorithm;
	/**
	 * Colour of the current node in the tree display.
	 */
	protected Color currentNode;
	
	/**
	 * Initialises a tree panel instance, and return it in a JScrollPane.
	 * 
	 * @param WIDTH - the maximum width of the tree panel.
	 * @param HEIGHT - the maximum height of the tree panel.
	 * @return a JScrollPane instance
	 */
	protected final JScrollPane initialiseTreePanel(final int WIDTH,final int HEIGHT) {

		maxWidth = WIDTH-20;
		maxHeight = HEIGHT-35;

		treePanel = new TreePanel();
		treePanel.setPreferredSize(new Dimension(maxWidth,maxHeight));
		treePanel.setBorder(BorderFactory.createLineBorder(Color.black));
		treePanel.setBackground(Color.white);

		scroller = new JScrollPane(treePanel);
		scroller.setPreferredSize(new Dimension(WIDTH,HEIGHT));
		scroller.setBorder(null);

		return scroller;
	}

	/**
	 * Sets the tree.
	 * 
	 * @param tree - new tree for this display.
	 */
	public final void setTree(Tree tree) {
		this.tree = tree;
	}

	/**
	 * Repaints the tree panel.
	 */
	public final void drawTree() {
		treePanel.repaint();
	}

	/**
	 * Sets the algorithms the tree is using. 
	 * Subclasses should check the supplied list for the right number of algorithms.
	 * 
	 * @param searchAlgorithms - array of search algorithms
	 */
	public void setAlgorithm(SearchAlgorithm[] searchAlgorithms) {
		if(searchAlgorithms.length < 1) return;
		this.searchAlgorithm = searchAlgorithms[0];
	}

	/**
	 * Sets the colours the tree uses.
	 * 
	 * @param colors - array of colours
	 */
	public void setCurrentNodeColor(Color[] colors) {
		if(colors.length < 1) return;
		this.currentNode = colors[0];
	}

	/**
	 * Draws the boxes in the trees. Calls the tree panel's fillBox method to set the background
	 * for the box.
	 * 
	 * @param Node - the node to draw
	 * @param g - the tree's graphics
	 * @param xPos - the x coordinate of the box (top left)
	 * @param yPos - the y coordinate of the box (top left)
	 * @param boxWidth - the width to draw the box
	 * @param boxHeight - the height to draw the box
	 */
	protected void drawTreeBox(Node node,Graphics g,int xPos, int yPos, int boxWidth, int boxHeight) {

		String value = node.getValue();
				
		g.setColor(Color.white);
		
		// if at goal change colour to goal node colour
		if(value.equals(tree.getGoal())) g.setColor(GOAL_NODE);
		// if at the current node and not at the beginning change colour to current node colour
		if(node.getUID()==searchAlgorithm.getCurrentNode().getUID() && searchAlgorithm.canUndo()) g.setColor(currentNode);

		treePanel.fillBox(g, xPos, yPos, boxWidth, boxHeight);
		
		g.setColor(Color.black);
		g.drawRect(xPos, yPos, boxWidth, boxHeight);
		g.drawString(value, xPos+(boxWidth/2), yPos+(boxHeight/2));
	}

	/**
	 * Single tree panel class, a subclass of tree panel.
	 * Defines the drawTree method for a tree with a single algorithm.
	 * 
	 * @author Dan Cornwell
	 *
	 */
	class TreePanel extends JPanel {

		/**
		 * Random serial number.
		 */
		private static final long serialVersionUID = -9056497863601289548L;

		/**
		 * Paints the JPanel. Calls the draw tree method.
		 */
		protected final void paintComponent(Graphics g) {
			super.paintComponent(g);

			// If we have no tree or algorithm return.
			if(tree==null || searchAlgorithm==null) {
				return;
			}

			// HashMap holding with a point key and point value. Used to draw lines between these 2 points.
			Map<Point,Point> lineCoords = new HashMap<Point,Point>();
			// Holds the line connection coordinates of parent nodes.
			LinkedList<Point> parentCoords = new LinkedList<Point>();

			// The maximum depth of the tree.
			final int TREE_DEPTH = tree.getTreeDepth();
			// The size of the boxes that are being drawn.
			int boxsize = 40;

			// The x position of the root node.
			final int ROOT_X_POS = (maxWidth/2)-(boxsize/2);
			// The y position of the root node.
			final int ROOT_Y_POS = (maxHeight/TREE_DEPTH)/4;
									
			drawTreeBox(tree.getRoot(),g,ROOT_X_POS,ROOT_Y_POS,boxsize,boxsize);

			// The line connection point of the root node. This will be the bottom centre of the box.
			Point rootPoint = new Point(ROOT_X_POS+(boxsize/2),ROOT_Y_POS+boxsize);

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

			// Queue containing the path costs for the tree. If this is empty no path costs will be drawn.
			Queue<Integer> costs = tree.getPathCosts();

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

						if((boxsize+((2*boxsize)/3))*NODES_ON_LEVEL > maxWidth) maxWidth = (boxsize+((2*boxsize)/3))*NODES_ON_LEVEL;
						// Gives the nodes x position, using math to give visually pleasing spacing.
						int xPos = (maxWidth/(NODES_ON_LEVEL+1)) + (i*(maxWidth/(NODES_ON_LEVEL+1))) - (boxsize/2);

						if(((2*boxsize)+(boxsize/3))*TREE_DEPTH > maxHeight) maxHeight = (boxsize+((2*boxsize)+(boxsize/3)))*TREE_DEPTH;
						// Get the nodes y position by multiplying the node level with the 
						// amount of space each level takes in relation to the max height.
						int yPos = nodeLevel*(maxHeight/TREE_DEPTH);
						
					    drawTreeBox(children.get(i),g,xPos,yPos,boxsize,boxsize);

						// The child line connection point. This will be the top middle of the box.
						Point childCoord = new Point(xPos+(boxsize/2),yPos);

						// If there are parent coordinates in the list
						if(!parentCoords.isEmpty()) {
							// Get the first parent coordinate.
							Point parentCoord = parentCoords.remove();

							// Add this coordinate with the child's coordinate to the line coordinates HashMap.
							// Note that childCoord is the key, since a parent can have many children but
							// a child can only have one parent.
							lineCoords.put(childCoord, parentCoord);

							// If path costs exist then display them on the lines
							if(!costs.isEmpty()) {
								int value = 1;
								if(costs.peek()!=null) value = costs.poll();
								g.drawString(String.valueOf(value),10+(childCoord.x+parentCoord.x)/2, 5+(childCoord.y+parentCoord.y)/2);
							}	

							// For the size of this child's children list, add its parent coordinate to the 
							// parent coordinates list.
							for(int j=0;j<children.get(i).getChildren().size();j++) {
								// Parent coordinate is its child coordinate but with the box size added to the 
								// y coordinate. AKA the top centre point.
								Point newParentCoord = new Point(childCoord.x,childCoord.y+boxsize);
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
			
			treePanel.setPreferredSize(new Dimension(maxWidth,maxHeight));
			scroller.setViewportView(treePanel);

		}

		final void fillBox(Graphics g,int xPos, int yPos, int boxWidth, int boxHeight) {

			g.fillRect(xPos+1, yPos+1, boxWidth-1, boxHeight-1);

		}
	}

}
