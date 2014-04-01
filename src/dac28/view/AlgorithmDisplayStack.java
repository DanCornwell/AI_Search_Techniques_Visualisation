package dac28.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.util.Collections;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * A class used for displaying the algorithm display when a stack is used. Extends algorithmDisplay.
 * Overrides the initialiseAlgorithmPanel method from algorithm display to do this.
 * 
 * @author Dan Cornwell
 *
 */
public class AlgorithmDisplayStack extends AlgorithmDisplay {

	@Override
	JPanel initialiseAlgorithmPanel(final int WIDTH,final int HEIGHT) {

		JPanel algorithmPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
		algorithmPanel.setPreferredSize(new Dimension(WIDTH,HEIGHT));

		// Create holding panels and add items to them
		// the width and height are chosen so that the holding JPanels will fit within the algorithm display panel
		final int panelWidth = (WIDTH)-50;
		final int panelHeight = (HEIGHT-40)/6;
		// The max number of nodes the tree has
		final int MAX_NUMBER_NODES = 30;
		// Title Panel
		JPanel titlePanel = getHoldingPanel(panelWidth+50,panelHeight-20);
		titlePanel.add(title);
		// Panel 1 - holds the expanded label and visited label
		JPanel p1 = getHoldingPanel(panelWidth+50,panelHeight-40);
		p1.setLayout(new FlowLayout(FlowLayout.LEFT,20,0));
		p1.add(expandedLabel);
		p1.add(Box.createRigidArea(new Dimension(20,0)));
		p1.add(visitedLabel);

		// Panel 2 - holds the expanded list
		JPanel p2 = getHoldingPanel(BOX_SIZE.width+5,BOX_SIZE.height*MAX_NUMBER_NODES);
		p2.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
		for(int i=0;i<MAX_NUMBER_NODES;i++) {
			JLabel label = new JLabel("",JLabel.CENTER);
			label.setOpaque(true);
			label.setPreferredSize(BOX_SIZE);
			label.setBorder(BorderFactory.createLineBorder(BOX_BORDER_COLOUR));
			label.setBackground(DEFAULT);
			expandedList.add(label);
			p2.add(label);
		}
		// Reverse list to give the 'stack' effect
		Collections.reverse(expandedList);
		// Panel 3 - holds the visited list
		JPanel p3 = getHoldingPanel(BOX_SIZE.width*MAX_NUMBER_NODES,BOX_SIZE.height+5);
		p3.setLayout(new FlowLayout(FlowLayout.LEFT,0,5));
		for(int i=0;i<MAX_NUMBER_NODES;i++) {
			JLabel label = new JLabel("",JLabel.CENTER);
			label.setOpaque(true);
			label.setPreferredSize(BOX_SIZE);
			label.setBorder(BorderFactory.createLineBorder(BOX_BORDER_COLOUR));
			label.setBackground(DEFAULT);		
			visitedList.add(label);
			p3.add(label);
		}
		// Panel 4 - holds the current node labels and at goal labels
		JPanel p4 = getHoldingPanel(panelWidth,panelHeight-20);
		p4.add(currentNodeLabel);
		p4.add(node);
		p4.add(Box.createRigidArea(new Dimension(40,0)));
		p4.add(goalLabel);
		p4.add(atGoal);
		// Panel 5 - holds step, auto and undo buttons
		JPanel p5 = getHoldingPanel(panelWidth,panelHeight-20);
		p5.add(step);
		p5.add(auto);
		p5.add(undo);
		p5.add(pause);
		// Panel 6 - holds the reset button
		JPanel p6 = getHoldingPanel(panelWidth,panelHeight-20);
		p6.add(skip);
		p6.add(reset);
		// Panel 7 - holds expanded list size and iteration size
		JPanel p7 = getHoldingPanel(panelWidth,panelHeight);
		p7.add(new JLabel("Maximum size of expanded list: "));
		p7.add(expandedListSize);
		p7.add(new JLabel("Number of iterations: "));
		p7.add(iterationNumber);

		// Create a left and right panel allow stack to appear horizontally
		JPanel left = new JPanel();
		left.setPreferredSize(new Dimension(80,HEIGHT));
		JPanel right = new JPanel();
		right.setPreferredSize(new Dimension(WIDTH-80,HEIGHT));

		// Add the visited list to a scroll pane and add that scroll pane
		JScrollPane p2Scroller = new JScrollPane(p2);
		p2Scroller.setBorder(null);
		p2Scroller.setHorizontalScrollBarPolicy((JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
		p2Scroller.setPreferredSize(new Dimension(BOX_SIZE.width+30,HEIGHT-200));
		p2.scrollRectToVisible(new Rectangle(0,BOX_SIZE.height*MAX_NUMBER_NODES,10,10));
		// Add holding panels to the left and right
		left.add(p2Scroller);
		// Add the visited list to a scroll pane and add that scroll pane
		JScrollPane p3Scroller = new JScrollPane(p3);
		p3Scroller.setBorder(null);
		p3Scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		p3Scroller.setPreferredSize(new Dimension(panelWidth-50,panelHeight-10));
		right.add(p3Scroller);
		right.add(p4);
		right.add(p5);
		right.add(p6);
		right.add(p7);

		// Add other holding panels plus left and right to the algorithm panel
		algorithmPanel.add(titlePanel);
		algorithmPanel.add(p1);
		algorithmPanel.add(left);
		algorithmPanel.add(right);

		return algorithmPanel;
	}

}
