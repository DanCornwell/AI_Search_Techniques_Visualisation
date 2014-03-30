package dac28.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;

import dac28.model.Node;
import dac28.model.SearchAlgorithm;

/**
 * Single tree display class, a subclass of tree display.
 * Defines how to draw a tree with a single algorithm searching on it.
 * 
 * @author Dan Cornwell
 *
 */
public class TreeDisplaySingleAlgorithm extends TreeDisplay {

	@Override
	TreePanel getTreePanel() {
		return new SingleTreePanel();
	}

	@Override
	public void setAlgorithm(SearchAlgorithm[] searchAlgorithms) {
		if(searchAlgorithms.length < 1) return;
		this.searchAlgorithm = searchAlgorithms[0];
	}

	@Override
	public void setCurrentNodeColor(Color[] colors) {
		if(colors.length < 1) return;
		this.currentNode = colors[0];
	}
	
	/**
	 * Single tree panel class, a subclass of tree panel.
	 * Defines the drawTree method for a tree with a single algorithm.
	 * 
	 * @author Dan Cornwell
	 *
	 */
	private class SingleTreePanel extends TreePanel {

		/**
		 * Random serial number.
		 */
		private static final long serialVersionUID = -9056497863601289548L;

		private int count = 0;

		@Override
		public void drawTree(Graphics g) {

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

			// The font size
			int fontSize = 12;

			// The shrink ratio for the font size
			final double shrinkRatio = 7.0/8;	

			// While the boxes are too big either horizontally or vertically, shrink the box size
			if(TREE_DEPTH != 0 && tree.getTreeWidth() != 0) {
				while((this.getHeight()/TREE_DEPTH)-10 < boxsize) {
					boxsize -= 5;
					fontSize = (int)Math.round(fontSize * (shrinkRatio));
				}
				while((this.getWidth()/tree.getTreeWidth())-10 < boxsize) {
					boxsize -=5;
					fontSize = (int)Math.round(fontSize * (shrinkRatio));
				}
				g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, fontSize));
			}
			// The x position of the root node.
			final int ROOT_X_POS = (maxWidth/2)-(boxsize/2);
			// The y position of the root node.
			final int ROOT_Y_POS = (maxHeight/TREE_DEPTH)/4;

			// Draws the root node, with its values inside it. 
			// Will also colour the box the appropriate colour.
			g.drawRect(ROOT_X_POS, ROOT_Y_POS, boxsize, boxsize);
			// If root is the goal colour box goal node colour.
			if(tree.getRoot().getValue().equals(tree.getGoal())) {
				g.setColor(GOAL_NODE);
				g.fillRect(ROOT_X_POS+1, ROOT_Y_POS+1, boxsize-1, boxsize-1);
				g.setColor(DEFAULT);
			}
			// If the root is the current node colour is current node colour.
			if(tree.getRoot().getValue().equals(searchAlgorithm.getCurrentNode().getValue()) && count!=0) {
				g.setColor(currentNode);
				g.fillRect(ROOT_X_POS+1, ROOT_Y_POS+1, boxsize-1, boxsize-1);
				g.setColor(DEFAULT);
			}
			g.drawString(String.valueOf(tree.getRoot().getValue()), ROOT_X_POS+(boxsize/2), ROOT_Y_POS+(boxsize/2));

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

						// Gives the nodes x position, using math to give visually pleasing spacing.
						int xPos = (maxWidth/(NODES_ON_LEVEL+1)) + (i*(maxWidth/(NODES_ON_LEVEL+1))) - (boxsize/2);
						// Get the nodes y position by multiplying the node level with the 
						// amount of space each level takes in relation to the max height.
						int yPos = nodeLevel*(maxHeight/TREE_DEPTH);
						// Draw the nodes with their values inside them.
						g.drawRect(xPos, yPos, boxsize, boxsize);
						// If the node is the goal colour it goal node colour.
						if(children.get(i).getValue().equals(tree.getGoal())) {
							g.setColor(GOAL_NODE);
							g.fillRect(xPos+1, yPos+1, boxsize-1, boxsize-1);
							g.setColor(DEFAULT);
						}
						// If the node is the current node colour it current node colour.
						if(children.get(i).getValue().equals(searchAlgorithm.getCurrentNode().getValue())) {
							g.setColor(currentNode);
							g.fillRect(xPos+1, yPos+1, boxsize-1, boxsize-1);
							g.setColor(DEFAULT);
						}
						g.drawString(String.valueOf(children.get(i).getValue()), xPos+(boxsize/2), yPos+(boxsize/2));

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

			count++;
		}

	}
	
}
