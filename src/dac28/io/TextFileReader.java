package dac28.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

/**
 * File reader class.
 * 
 * @author Dan Cornwell
 *
 */
public class TextFileReader {

	/**
	 * String representation of the path for the algorithms file.
	 */
	private static final String ALGORITHMS_TXT = "loader_files/algorithms.txt";
	/**
	 * String representation of the path for the trees file.
	 */
	private static final String TREES_TXT = "loader_files/trees.txt";
	
	/**
	 * Returns a list of the available algorithm names the application can use.
	 * 
	 * @return string list of search algorithm's names
	 * @throws IOException 
	 */
	private final static LinkedList<String> readAlgorithms() throws IOException {	
		if(!new File(ALGORITHMS_TXT).exists()) throw new FileNotFoundException();
		
		LinkedList<String> algorithms = new LinkedList<String>();
		BufferedReader reader = new BufferedReader(new FileReader(ALGORITHMS_TXT));
		String line = null;
		while ((line = reader.readLine()) != null) {
		    algorithms.add(line);
		}
		reader.close();
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
	 * 
	 * @return string list of tree names
	 * @throws IOException 
	 */
	private final static LinkedList<String> readTrees() throws IOException {	
		if(!new File(TREES_TXT).exists()) throw new FileNotFoundException();
		
		LinkedList<String> trees = new LinkedList<String>();
		BufferedReader reader = new BufferedReader(new FileReader(TREES_TXT));
		String line = null;
		while ((line = reader.readLine()) != null) {
		    trees.add(line);
		}
		reader.close();
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
