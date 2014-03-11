package dac28.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Collections;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AlgorithmDisplayStack extends AlgorithmDisplay {
	
	@Override
	JPanel initialiseAlgorithmPanel(final int WIDTH,final int HEIGHT) {
		
		JPanel algorithmPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
		algorithmPanel.setPreferredSize(new Dimension(WIDTH,HEIGHT));

		// Create holding panels and add items to them
		// the width and height are chosen so that the holding jpanels will fit within the algorithm display panel
		final int panelWidth = (WIDTH)-50;
		final int panelHeight = (HEIGHT-40)/5;
		// Title Panel
		JPanel titlePanel = getHoldingPanel(panelWidth+50,panelHeight-20);
		titlePanel.add(title);
		// Panel 1 - holds the expanded label and visited label
		JPanel p1 = getHoldingPanel(panelWidth+50,panelHeight-60);
		p1.setLayout(new FlowLayout(FlowLayout.LEFT,20,0));
		p1.add(expandedLabel);
		p1.add(Box.createRigidArea(new Dimension(20,0)));
		p1.add(visitedLabel);
		
		// Panel 2 - holds the expanded list
		JPanel p2 = getHoldingPanel(50,HEIGHT-100);
		p2.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
		for(int i=0;i<10;i++) {
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
		JPanel p3 = getHoldingPanel(panelWidth-10,panelHeight+BOX_SIZE.height);
		p3.setLayout(new FlowLayout(FlowLayout.LEFT,0,10));
		for(int i=0;i<18;i++) {
			JLabel label = new JLabel("",JLabel.CENTER);
			label.setOpaque(true);
			label.setPreferredSize(BOX_SIZE);
			label.setBorder(BorderFactory.createLineBorder(BOX_BORDER_COLOUR));
			label.setBackground(DEFAULT);		
			visitedList.add(label);
			p3.add(label);
		}
		// Panel 5 - holds the current node labels and at goal labels
		JPanel p4 = getHoldingPanel(panelWidth,panelHeight-20);
		p4.add(currentNode);
		p4.add(node);
		p4.add(Box.createRigidArea(new Dimension(40,0)));
		p4.add(goalLabel);
		p4.add(atGoal);
		// Panel 6 - holds step, auto and undo buttons
		JPanel p5 = getHoldingPanel(panelWidth,panelHeight-20);
		p5.add(step);
		p5.add(auto);
		p5.add(undo);
		p5.add(pause);
		// Panel 7 - holds the reset button
		JPanel p6 = getHoldingPanel(panelWidth,panelHeight-20);
		p6.add(skip);
		p6.add(reset);

		JPanel left = new JPanel();
		left.setPreferredSize(new Dimension(60,HEIGHT));
		JPanel right = new JPanel();
		right.setPreferredSize(new Dimension(WIDTH-60,HEIGHT));
		
		// Add the holding panels to the algorithm panel
		left.add(p2);
		right.add(p3);		
		right.add(p4);
		right.add(p5);
		right.add(p6);
		
		algorithmPanel.add(titlePanel);
		algorithmPanel.add(p1);
		algorithmPanel.add(left);
		algorithmPanel.add(right);
		
		return algorithmPanel;
	}

}
