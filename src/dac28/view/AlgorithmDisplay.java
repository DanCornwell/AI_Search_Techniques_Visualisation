package dac28.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

class AlgorithmDisplay {

	private JButton step,auto,reset,undo;
	private JLabel node;

	JPanel initialiseAlgorithm(int WIDTH, int HEIGHT) {

		JPanel algorithmPanel = new JPanel(new GridBagLayout());
		algorithmPanel.setPreferredSize(new Dimension(WIDTH/2,HEIGHT-30));
		algorithmPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		algorithmPanel.setBackground(Color.orange);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.insets = new Insets(10,0,10,0);
		gbc.gridx = 0;
		gbc.gridy = 0;

		final JLabel expandedLabel = new JLabel("Expanded");
		final JLabel visitedLabel = new JLabel("Visited");
		final JLabel currentNode = new JLabel("Current Node: ");
		node = new JLabel("");
		node.setBorder(BorderFactory.createLineBorder(Color.black));
		node.setPreferredSize(new Dimension(30,30));

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
		
		algorithmPanel.add(expandedLabel,gbc);
		gbc.gridy++;
		algorithmPanel.add(visitedLabel,gbc);
		gbc.gridy++;
		algorithmPanel.add(currentNode,gbc);
		gbc.gridx++;
		algorithmPanel.add(node,gbc);
		gbc.gridy++;
		gbc.gridx--;
		algorithmPanel.add(step,gbc);
		gbc.gridx++;
		algorithmPanel.add(auto,gbc);
		gbc.gridx++;
		algorithmPanel.add(undo,gbc);
		gbc.gridx=1;
		gbc.gridy++;
		algorithmPanel.add(reset,gbc);

		return algorithmPanel;

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

}
