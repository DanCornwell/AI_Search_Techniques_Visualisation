package dac28.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

class AlgorithmDisplay {

	private JButton step,auto,reset,undo;
	private JLabel node,atGoal;
	private LinkedList<ListCell> expandedList, visitedList;

	JPanel initialiseAlgorithm(int WIDTH, int HEIGHT) {

		JPanel algorithmPanel = new JPanel();
		algorithmPanel.setPreferredSize(new Dimension(WIDTH/2,HEIGHT-30));
	//	algorithmPanel.setBorder(BorderFactory.createLineBorder(Color.black));

		final JLabel expandedLabel = new JLabel("Expanded");
		final JLabel visitedLabel = new JLabel("Visited");
		final JLabel currentNode = new JLabel("Current Node: ");
		final JLabel goalLabel = new JLabel("At Goal? : ");
		node = new JLabel("");
		atGoal = new JLabel("");
		atGoal.setPreferredSize(new Dimension(30,30));
		node.setBorder(BorderFactory.createLineBorder(Color.black));
		node.setPreferredSize(new Dimension(30,30));
		expandedList = new LinkedList<ListCell>();
		visitedList = new LinkedList<ListCell>();

		// Buttons and their listener
		ButtonListener bListener = this.new ButtonListener();
		step = new JButton("Step");
		step.addActionListener(bListener);
		auto = new JButton("Auto");
		auto.addActionListener(bListener);
		reset = new JButton("Reset");
		reset.addActionListener(bListener);
		undo = new JButton("Undo");
		undo.addActionListener(bListener);
		
		// Create holding panels and add items to them
		final int panelWidth = (WIDTH/2)-20;
		final int panelHeight = (HEIGHT-40)/7;
		// Panel 1 - holds the expanded label
		JPanel p1 = getHoldingPanel(panelWidth,panelHeight-20);
		p1.setLayout(new FlowLayout(FlowLayout.LEFT));
		p1.add(expandedLabel);
		// Panel 2 - holds the expanded list
		JPanel p2 = getHoldingPanel(panelWidth,panelHeight);
		p2.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
		for(int i=0;i<10;i++) {
			ListCell listCell = this.new ListCell();
			expandedList.add(listCell);
			p2.add(listCell.getListCell());
		}
		// Panel 3 - holds the visited label
		JPanel p3 = getHoldingPanel(panelWidth,panelHeight-20);
		p3.setLayout(new FlowLayout(FlowLayout.LEFT));
		p3.add(visitedLabel);
		// Panel 4 - holds the visited list
		JPanel p4 = getHoldingPanel(panelWidth,panelHeight);
		p4.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
		for(int i=0;i<10;i++) {
			ListCell listCell = this.new ListCell();
			visitedList.add(listCell);
			p4.add(listCell.getListCell());
		}
		// Panel 5 - holds the current node labels and at goal labels
		JPanel p5 = getHoldingPanel(panelWidth,panelHeight);
		p5.add(currentNode);
		p5.add(node);
		p5.add(Box.createRigidArea(new Dimension(20,0)));
		p5.add(goalLabel);
		p5.add(atGoal);
		// Panel 6 - holds step, auto and undo buttons
		JPanel p6 = getHoldingPanel(panelWidth,panelHeight);
		p6.add(step);
		p6.add(auto);
		p6.add(undo);
		// Panel 7 - holds the reset button
		JPanel p7 = getHoldingPanel(panelWidth,panelHeight);
		p7.add(reset);
		
		// Add the holding panels to the algorithm panel
		algorithmPanel.add(p1);
		algorithmPanel.add(p2);
		algorithmPanel.add(p3);
		algorithmPanel.add(p4);
		algorithmPanel.add(p5);
		algorithmPanel.add(p6);
		algorithmPanel.add(p7);

		return algorithmPanel;

	}
	
	void setListLabels(List<Integer> list,LinkedList<ListCell> targetList) {

		LinkedList<String> newElements = new LinkedList<String>();
		LinkedList<String> listValues = new LinkedList<String>();
		
		for(ListCell cell: targetList) {
			listValues.add(cell.value.getText().toString());
			System.out.println(cell.value.getText().toString());
		}
		
		for(int k=0;k<list.size();k++) {
			if(!listValues.contains(list.get(k).toString())) {
				newElements.add(list.get(k).toString());
			}
		}		
		for(ListCell cell: targetList) {
			cell.setLabelValue("");
		}
		for(int i=0;i<list.size();i++) {
			targetList.get(i).setLabelValue(list.get(i).toString());
			if(newElements.contains(list.get(i).toString())) {
				targetList.get(i).setCellBackground(Color.yellow);
			}
			else {
				targetList.get(i).setCellBackground(Color.white);
			}
		}
	}
	
	private JPanel getHoldingPanel(int width,int height) {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(width,height));
		return panel;
	}

	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == step) {
				System.out.println("this is step");
			}
			else if(e.getSource() == auto) {

			}
			else if(e.getSource() == reset) {

			}
			else if(e.getSource() == undo) {

			}
		}

	}
	
	private class ListCell {

		private JLabel value;
		private JPanel panel;
		
		ListCell() {
			value = new JLabel("");
			panel = new JPanel(new BorderLayout());
		}
		
		JPanel getListCell() {
			value.setPreferredSize(new Dimension(10,10));
			panel.setBackground(Color.white);
			panel.setPreferredSize(new Dimension(30,30));
			panel.setBorder(BorderFactory.createLineBorder(Color.black));
			panel.add(value,BorderLayout.EAST);
			return panel;
			
		}
		
		void setLabelValue(String value) {
			this.value.setText(value);
		}
		
		void setCellBackground(Color color) {
			panel.setBackground(color);
		}
		
	}


}
