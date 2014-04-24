package dac28.model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.LinkedList;

/**
 * File reader class. 
 * Reads the files containing the algorithms and trees.
 * 
 * @author Dan Cornwell
 *
 */
public class TextFileReader {

	/**
	 * Returns a list of the available algorithm names the application can use.
	 * If the algorithm.txt file does not exist then the default known algorithms are used.
	 * 
	 * @return string list of search algorithm's names
	 * @throws IOException 
	 */
	private final static LinkedList<String> readAlgorithms() throws IOException {	

		LinkedList<String> algorithms = new LinkedList<String>();

		try {
			BufferedReader algorithmFile = 
					new BufferedReader(new InputStreamReader(TextFileReader.class.getClassLoader().getResourceAsStream("algorithms.txt")));
			String line;
			while((line=algorithmFile.readLine()) != null) {
				line = line.trim();
				if(!line.startsWith("#") && !line.isEmpty()) 
					algorithms.add(line);
			}
			algorithmFile.close();
		}
		catch(NullPointerException e) {
			algorithms.add("BreadthFirstSearch");
			algorithms.add("DepthFirstSearch");
			algorithms.add("IterativeDeepeningSearch");
			algorithms.add("UniformCostSearch");
		}

		return algorithms;
	}

	/**
	 * Public access to the readAlgorithms method.
	 * 
	 * @return string list of the search algorithm's names
	 */
	public static final LinkedList<String> getAlgorithms() {
		try {
			return readAlgorithms();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Returns a list of the available tree names the application can use.
	 * If the trees.txt file does not exist then the default known trees are used.
	 * 
	 * @return string list of tree names
	 * @throws IOException 
	 */
	private final static LinkedList<String> readTrees() throws IOException {	

		LinkedList<String> trees = new LinkedList<String>();

		try {
			BufferedReader treeFile = 
					new BufferedReader(new InputStreamReader(TextFileReader.class.getClassLoader().getResourceAsStream("trees.txt")));
			String line;
			while((line=treeFile.readLine()) != null) {
				line = line.trim();
				if(!line.startsWith("#") && !line.isEmpty()) 
					trees.add(line);
			}
			treeFile.close();

		}
		catch(NullPointerException e) {
			trees.add("Tree124");
			trees.add("Tree1355");
			trees.add("Tree112111");
			trees.add("Tree1834");
			trees.add("Tree14326234");
			trees.add("TreeRandom");
		}

		return trees;
	}

	/**
	 * Public access to the readTrees method
	 * 
	 * @return string list of tree names
	 */
	public static final LinkedList<String> getTrees() {
		try {
			return readTrees();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
