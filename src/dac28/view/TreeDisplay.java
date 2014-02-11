package dac28.view;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

class TreeDisplay {

	TreeDisplay(int WIDTH, int HEIGHT) {
		
		initialiseTree(WIDTH,HEIGHT);
		
	}

	static JPanel initialiseTree(int WIDTH, int HEIGHT) {
		
		JPanel treePanel = new JPanel();
		treePanel.setPreferredSize(new Dimension(WIDTH/2,HEIGHT-30));
		treePanel.setBorder(BorderFactory.createLineBorder(Color.black));
		treePanel.setBackground(Color.white);
		
		return treePanel;
	}
	
}
