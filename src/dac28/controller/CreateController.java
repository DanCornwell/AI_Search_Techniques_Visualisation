package dac28.controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import dac28.model.BreadthFirstSearchCreator;
import dac28.model.DepthFirstSearchCreator;
import dac28.model.SearchAlgorithmCreator;
import dac28.model.Tree112111Creator;
import dac28.model.Tree124Creator;
import dac28.model.Tree1354Creator;
import dac28.model.TreeCreator;

/**
 * Controller to create new searches and trees.
 * 
 * @author Dan Cornwell
 *
 */
public class CreateController {
	
	/**
	 * Radio buttons relating to the trees the user can choose.
	 */
	private JRadioButton tree124,tree1354,tree112111;
	/**
	 * Radio buttons relating to the searches the user can choose.
	 */
	private JRadioButton bfs,dfs;
	/**
	 * The goal field the user can enter their goal value in.
	 */
	private JTextField goal;
	
	/**
	 * Initialises the radio buttons and goal field.
	 */
	public CreateController() {
		
		tree124 = new JRadioButton("1-2-4 Tree");
		tree124.setSelected(true);	
		tree1354 = new JRadioButton("1-3-5-4 Tree");
		tree1354.setSelected(false);
		tree112111 = new JRadioButton("1-1-2-1-1-1 Tree");
		tree112111.setSelected(false);
	    
	    bfs = new JRadioButton("Breadth First Search");
		bfs.setSelected(true);
		dfs = new JRadioButton("Depth First Search");
		dfs.setSelected(false);	
	    
	    goal = new JTextField("");
		
	}
	
	/**
	 * Returns a dialog window allowing the user to choose the search.
	 * 
	 * @return dialog window
	 */
	public JPanel getCreateDialog() {

		JPanel panel = new JPanel();
		
		// width of the window
		final int WIDTH = 400;
		// height of the window
		final int HEIGHT = 500;
		
		panel.setPreferredSize(new Dimension(WIDTH,HEIGHT));
		
		JPanel treeChoices = new JPanel(new GridLayout(0,1));
		treeChoices.setPreferredSize(new Dimension((WIDTH/2)-10,HEIGHT/2));
		treeChoices.setBorder(BorderFactory.createLineBorder(Color.black));
		
		treeChoices.add(new JLabel("Enter value of the goal node: "));
		goal.setPreferredSize(new Dimension(30,20));
		treeChoices.add(goal);
	    treeChoices.add(tree124);
	    treeChoices.add(tree1354);
	    treeChoices.add(tree112111);
	    
	    ButtonGroup treeGroup = new ButtonGroup();
	    treeGroup.add(tree124);
	    treeGroup.add(tree1354);
	    treeGroup.add(tree112111);
	    
		JPanel searchChoices = new JPanel(new GridLayout(0,1));
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
	
	/**
	 * Returns a tree creator, depending on which button is selected.
	 * 
	 * @return a tree creator
	 */
	public TreeCreator getTreeCreator() {
		
		if(tree124.isSelected()) {
			return new Tree124Creator();
		}
		else if(tree1354.isSelected()) {
			return new Tree1354Creator();
		}
		else if(tree112111.isSelected()) {
			return new Tree112111Creator();
		}
		
		return new Tree124Creator();
	}
	
	/**
	 * Returns a search algorithm creator, depending on which button is selected.
	 * 
	 * @return a search algorithm creator
	 */
	public SearchAlgorithmCreator getAlgorithmCreator() {
		
		if(dfs.isSelected()) {
			return new DepthFirstSearchCreator();
		}
		else if(bfs.isSelected()) {
			return new BreadthFirstSearchCreator();
		}
		
		return new DepthFirstSearchCreator();
	}
	
	/**
	 * Returns the value that is in the goal text field.
	 * 
	 * @return string representing the value entered
	 */
	public String getGoal() {
		return goal.getText();
	}
}
