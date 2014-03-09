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
public class DualCreateController {

	/**
	 * Radio buttons relating to the trees the user can choose.
	 */
	private JRadioButton tree124,tree1354,tree112111;
	/**
	 * Radio buttons relating to the searches the user can choose.
	 */
	private JRadioButton bfs1,dfs1,bfs2,dfs2;
	/**
	 * The goal field the user can enter their goal value in.
	 */
	private JTextField goal;

	private ButtonGroup algorithmGroup2;

	/**
	 * Initialises the radio buttons and goal field.
	 */
	public DualCreateController() {

		tree124 = new JRadioButton("1-2-4 Tree");
		tree124.setSelected(true);	
		tree1354 = new JRadioButton("1-3-5-4 Tree");
		tree1354.setSelected(false);
		tree112111 = new JRadioButton("1-1-2-1-1-1 Tree");
		tree112111.setSelected(false);

		bfs1 = new JRadioButton("Breadth First Search");
		bfs1.setSelected(true);
		dfs1 = new JRadioButton("Depth First Search");
		dfs1.setSelected(false);	
		bfs2 = new JRadioButton("Breadth First Search");
		bfs2.setSelected(false);
		dfs2 = new JRadioButton("Depth First Search");
		dfs2.setSelected(true);	

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
		final int WIDTH = 600;
		// height of the window
		final int HEIGHT = 500;

		panel.setPreferredSize(new Dimension(WIDTH,HEIGHT));

		JPanel treeChoices = new JPanel(new GridLayout(0,1));
		treeChoices.setPreferredSize(new Dimension((WIDTH/3)-10,HEIGHT/2));
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

		JPanel searchChoices1 = new JPanel(new GridLayout(0,1));
		searchChoices1.setPreferredSize(new Dimension((WIDTH/3)-10,HEIGHT/2));
		searchChoices1.setBorder(BorderFactory.createLineBorder(Color.black));

		searchChoices1.add(bfs1);
		searchChoices1.add(dfs1);

		ButtonGroup algorithmGroup1 = new ButtonGroup();
		algorithmGroup1.add(bfs1);
		algorithmGroup1.add(dfs1);

		JPanel searchChoices2 = new JPanel(new GridLayout(0,1));
		searchChoices2.setPreferredSize(new Dimension((WIDTH/3)-10,HEIGHT/2));
		searchChoices2.setBorder(BorderFactory.createLineBorder(Color.black));

		searchChoices2.add(bfs2);
		searchChoices2.add(dfs2);

		algorithmGroup2 = new ButtonGroup();
		algorithmGroup2.add(bfs2);
		algorithmGroup2.add(dfs2);

		JPanel treeDiagram = new JPanel();
		treeDiagram.setPreferredSize(new Dimension(WIDTH-20,(HEIGHT/2)-20));
		treeDiagram.setBorder(BorderFactory.createLineBorder(Color.black));

		panel.add(treeChoices);
		panel.add(searchChoices1);
		panel.add(searchChoices2);
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
	public SearchAlgorithmCreator getAlgorithm1Creator() {

		if(dfs1.isSelected()) {
			return new DepthFirstSearchCreator();
		}
		else if(bfs1.isSelected()) {
			return new BreadthFirstSearchCreator();
		}

		return new DepthFirstSearchCreator();
	}

	/**
	 * Returns a search algorithm creator, depending on which button is selected.
	 * 
	 * @return a search algorithm creator
	 */
	public SearchAlgorithmCreator getAlgorithm2Creator() {

		if(dfs2.isSelected()) {
			return new DepthFirstSearchCreator();
		}
		else if(bfs2.isSelected()) {
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

