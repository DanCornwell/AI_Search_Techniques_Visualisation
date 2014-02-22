package dac28.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
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

		public void paintComponent(final Graphics g) {
			super.paintComponent(g);
			drawTree(g);
		}

		public void drawTree(Graphics g) {

			if(tree==null) {
				return;
			}

			Map<Point,Point> lineCoords = new HashMap<Point,Point>();
			LinkedList<Point> parentCoords = new LinkedList<Point>();

			final int TREE_DEPTH = tree.getTreeDepth(tree.getRoot());

			final int BOXSIZE = 40;
			final int ROOT_X_POS = (maxWidth/2)-(BOXSIZE/2);
			final int ROOT_Y_POS = 30;
			g.drawRect(ROOT_X_POS, ROOT_Y_POS, BOXSIZE, BOXSIZE);
			g.drawString(String.valueOf(tree.getRoot().getValue()), ROOT_X_POS+(BOXSIZE/2), ROOT_Y_POS+(BOXSIZE/2));

			Point rootPoint = new Point(ROOT_X_POS+(BOXSIZE/2),ROOT_Y_POS+BOXSIZE);

			LinkedList<Node> parents = new LinkedList<Node>();
			LinkedList<Node> children = new LinkedList<Node>();

			parents.add(tree.getRoot());

			for(int i=0;i<tree.getRoot().getChildren().size();i++) {
				parentCoords.add(rootPoint);
			}

			int counter = 1;

			while(!parents.isEmpty()) {

				while(!parents.isEmpty()) {
					children.addAll(parents.remove().getChildren());
				}

				if(!children.isEmpty()) {

					final int NODES_ON_LEVEL = children.size();

					for(int i=0;i<NODES_ON_LEVEL;i++) {

						int xPos = (maxWidth/(NODES_ON_LEVEL+1)) + (i*(maxWidth/(NODES_ON_LEVEL+1))) - (BOXSIZE/2);
						int yPos = counter*(maxHeight/TREE_DEPTH);

						g.drawRect(xPos, yPos, BOXSIZE, BOXSIZE);
						g.drawString(String.valueOf(children.get(i).getValue()), xPos+(BOXSIZE/2), yPos+(BOXSIZE/2));
						Point childCoord = new Point(xPos+(BOXSIZE/2),yPos);
						
						if(!parentCoords.isEmpty()) {
							Point parentCoord = parentCoords.remove();
							lineCoords.put(childCoord, parentCoord);
							for(int j=0;j<children.get(i).getChildren().size();j++) {
								Point newParentCoord = new Point(childCoord.x,childCoord.y+BOXSIZE);
								parentCoords.add(newParentCoord);
							}
						}
						
					}

				}
				
				for(Node newParents: children) {
					parents.add(newParents);
				}

				children.clear();
				counter++;
			}

			for(Entry<Point, Point> lines: lineCoords.entrySet()) {
				g.drawLine(lines.getKey().x, lines.getKey().y, lines.getValue().x, lines.getValue().y);
			}
			
		}
	}
}
