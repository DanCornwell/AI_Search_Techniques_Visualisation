package dac28.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Stack;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import dac28.model.Node;
import dac28.model.Tree;

public class TreeDisplay {

	private TreePanel treePanel;
	private int maxWidth, maxHeight;
	private Tree tree = null;

	JPanel initialiseTreePanel(int WIDTH, int HEIGHT) {

		maxWidth = WIDTH/2;
		maxHeight = HEIGHT-30;

		treePanel = new TreePanel();
		treePanel.setPreferredSize(new Dimension(maxWidth,maxHeight));
		treePanel.setBorder(BorderFactory.createLineBorder(Color.black));
		treePanel.setBackground(Color.white);

		return treePanel;
	}

	public void setTree(Tree tree) {
		this.tree = tree;
	}

	private class TreePanel extends JPanel {

		/**
		 * Stores the pixel values for each node in the tree.
		 */
		private Map<Node, Point> coordinates = new HashMap<Node, Point>();


		public void paintComponent(final Graphics g) {
			super.paintComponent(g);
			drawTree(g);
		}

		public void drawTree(Graphics g) {

			if(tree==null) {
				return;
			}

			Map<Point,Point> lineCoords = new HashMap<Point,Point>();
			Stack<Point> parentCoords = new Stack<Point>();

			final int TREE_DEPTH = tree.getTreeDepth(tree.getRoot());

			final int ROOT_X_POS = (maxWidth/2)-30;
			final int ROOT_Y_POS = 30;
			final int BOXSIZE = 40;
			g.drawRect(ROOT_X_POS, ROOT_Y_POS, BOXSIZE, BOXSIZE);
			g.drawString(String.valueOf(tree.getRoot().getValue()), ROOT_X_POS+(BOXSIZE/2), ROOT_Y_POS+(BOXSIZE/2));

			Point rootPoint = new Point(ROOT_X_POS+(BOXSIZE/2),ROOT_Y_POS+BOXSIZE);

			Stack<Node> parents = new Stack<Node>();
			LinkedList<Node> children = new LinkedList<Node>();

			parents.push(tree.getRoot());
			parentCoords.add(rootPoint);

			int counter = 1;

			while(!parents.isEmpty()) {

				while(!parents.isEmpty()) {
					children.addAll(parents.pop().getChildren());
				}
				
				if(!children.isEmpty()) {

					final int NODES_ON_LEVEL = children.size();

					for(int i=0;i<NODES_ON_LEVEL;i++) {

					//	if(!parentCoords.isEmpty()) return;
					//	Point parentCoord = parentCoords.pop();
						int xPos = (maxWidth/(NODES_ON_LEVEL+1)) + (i*(maxWidth/(NODES_ON_LEVEL+1))) - 20;
						int yPos = counter*(maxHeight/TREE_DEPTH);
						
						g.drawRect(xPos, yPos, BOXSIZE, BOXSIZE);
						g.drawString(String.valueOf(children.get(i).getValue()), xPos+(BOXSIZE/2), yPos+(BOXSIZE/2));

					}

				}

				for(Node newParents: children) {
					parents.push(newParents);
				}
				children.clear();
				counter++;
			}

		}
	}
}
