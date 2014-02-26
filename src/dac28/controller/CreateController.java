package dac28.controller;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import dac28.model.BreadthFirstSearchCreator;
import dac28.model.DepthFirstSearchCreator;
import dac28.model.SearchAlgorithmCreator;
import dac28.model.Tree124Creator;
import dac28.model.Tree1354Creator;
import dac28.model.TreeCreator;

public class CreateController {
	
	private JRadioButton tree124,tree1354;
	private JRadioButton bfs,dfs;
	private JTextField goal;
	
	public CreateController() {
		
		tree124 = new JRadioButton("1-2-4 Tree");
		tree124.setSelected(true);	
		tree1354 = new JRadioButton("1-3-5-4 Tree");
		tree1354.setSelected(false);
	    
	    bfs = new JRadioButton("Breadth First Search");
		bfs.setSelected(true);
		dfs = new JRadioButton("Depth First Search");
		dfs.setSelected(false);	
	    
	    goal = new JTextField("");
		
	}
	
	public JPanel getCreateDialog() {

		JPanel panel = new JPanel();
		
		final int WIDTH = 400;
		final int HEIGHT = 500;
		
		panel.setPreferredSize(new Dimension(WIDTH,HEIGHT));
		
		JPanel treeChoices = new JPanel();
		treeChoices.setPreferredSize(new Dimension((WIDTH/2)-10,HEIGHT/2));
		treeChoices.setBorder(BorderFactory.createLineBorder(Color.black));
		
		treeChoices.add(new JLabel("Enter value of the goal node: "));
		goal.setPreferredSize(new Dimension(30,20));
		treeChoices.add(goal);
	    treeChoices.add(tree124);
	    treeChoices.add(tree1354);
	    
	    ButtonGroup treeGroup = new ButtonGroup();
	    treeGroup.add(tree124);
	    treeGroup.add(tree1354);
	    
		JPanel searchChoices = new JPanel();
		searchChoices.setPreferredSize(new Dimension((WIDTH/2)-10,HEIGHT/2));
		searchChoices.setBorder(BorderFactory.createLineBorder(Color.black));

	    searchChoices.add(bfs);
	    searchChoices.add(dfs);
	    
	    ButtonGroup algorithmGroup = new ButtonGroup();
	    algorithmGroup.add(bfs);
	    algorithmGroup.add(dfs);
		
		JPanel treeDiagram = new JPanel();
		treeDiagram.setPreferredSize(new Dimension(WIDTH-20,(HEIGHT/2)-20));
		treeDiagram.setBorder(BorderFactory.createLineBorder(Color.black));
		
		panel.add(treeChoices);
		panel.add(searchChoices);
		panel.add(treeDiagram);
		
		return panel;
		
	}
	
	public TreeCreator getTreeCreator() {
		
		if(tree124.isSelected()) {
			return new Tree124Creator();
		}
		else if(tree1354.isSelected()) {
			return new Tree1354Creator();
		}
		
		return new Tree124Creator();
	}
	
	public SearchAlgorithmCreator getAlgorithmCreator() {
		
		if(dfs.isSelected()) {
			return new DepthFirstSearchCreator();
		}
		else if(bfs.isSelected()) {
			return new BreadthFirstSearchCreator();
		}
		
		return new DepthFirstSearchCreator();
	}
	
	public String getGoal() {
		return goal.getText();
	}
}
