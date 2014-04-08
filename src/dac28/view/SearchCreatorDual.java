package dac28.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Controller to create a dual search.
 * 
 * @author Dan Cornwell
 *
 */
class SearchCreatorDual extends SearchCreator {

	/**
	 * Combo box relating to the 2nd algorithm.
	 */
	private JComboBox<String> dualAlgorithmOptions = new JComboBox<String>(ALGORITHMS);

	@Override
	protected JPanel addSearchChoices() {

		// Adds the first search choices and label
		JPanel searchChoices = new JPanel();
		// Set layout to grid bag layout, define the constraints
		searchChoices.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);

		searchChoices.setPreferredSize(new Dimension(WIDTH/4,2*(HEIGHT/15)));
		searchChoices.add(new JLabel("Select First Search Algorithm: ",JLabel.RIGHT),gbc);
		gbc.gridx++;
		searchChoices.add(algorithmOptions,gbc);
		gbc.gridx = 0;
		gbc.gridy++;
		algorithmOptions.setSelectedIndex(0);
		algorithmOptions.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// If the algorithm options chosen are the same, change them
				if(algorithmOptions.getSelectedIndex() == dualAlgorithmOptions.getSelectedIndex()) {
					if(dualAlgorithmOptions.getSelectedIndex() == 0) {
						dualAlgorithmOptions.setSelectedIndex(1);
					}
					else dualAlgorithmOptions.setSelectedIndex(0);
				}
				if(algorithmOptions.getSelectedIndex() != Arrays.asList(ALGORITHMS).indexOf("UniformCostSearch")) {
					// Clear any path values
					pathValues.clear();
					// Remove text fields from tree diagram
					treeDiagram.removeAll();
				}
				treeDiagram.repaint();
			}
		});

		// Adds the second search choices and label
		searchChoices.add(new JLabel("Select Second Search Algorithm: ",JLabel.RIGHT),gbc);
		gbc.gridx++;
		searchChoices.add(dualAlgorithmOptions,gbc);
		dualAlgorithmOptions.setSelectedIndex(1);
		dualAlgorithmOptions.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// If the algorithm options chosen are the same, change them
				if(algorithmOptions.getSelectedIndex() == dualAlgorithmOptions.getSelectedIndex()) {
					if(algorithmOptions.getSelectedIndex() == 0) {
						algorithmOptions.setSelectedIndex(1);
					}
					else algorithmOptions.setSelectedIndex(0);
				}
				if(dualAlgorithmOptions.getSelectedIndex() != Arrays.asList(ALGORITHMS).indexOf("UniformCostSearch")) {
					// Clear any path values
					pathValues.clear();
					// Remove text fields from tree diagram
					treeDiagram.removeAll();
				}
				treeDiagram.repaint();
			}
		});

		return searchChoices;
	}

	/**
	 * Returns the unique id of the 2nd selected algorithm, which is its index within the button group.
	 * 
	 * @return Integer representing the algorithm's ID
	 */
	public int getAlgorithm2UID() {

		return dualAlgorithmOptions.getSelectedIndex();

	}

	@Override
	protected boolean usingUniformCostSearch() {
		return (algorithmOptions.getSelectedItem().equals("UniformCostSearch")
				|| dualAlgorithmOptions.getSelectedItem().equals("UniformCostSearch"));
	}
	
	/**
	 * Returns whether the 2nd algorithm uses a stack.
	 * See algorithmUsingStack in the SearchCreator class for the full JavaDoc.
	 * 
	 * @return true if the selected search matches one that uses a stack, false otherwise
	 */
	protected final boolean algorithm2UsingStack() {
		
		return dualAlgorithmOptions.getSelectedItem().equals("DepthFirstSearch") 
				|| dualAlgorithmOptions.getSelectedItem().equals("IterativeDeepeningSearch");
	}

}

