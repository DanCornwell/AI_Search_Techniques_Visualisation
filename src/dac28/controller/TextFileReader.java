package dac28.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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

		// create directory if it doesn't exist
		File dir = new File("loader_files");
		if(!dir.exists() || !dir.isDirectory()) {
			if(!dir.mkdir()) return null;
		}

		// create algorithm text file if it doesn't exist, and write known algorithms to it
		File alg = new File(ALGORITHMS_TXT);
		if(!alg.exists() || alg.isDirectory()) {
			if(!alg.createNewFile()) return null;
			final String ALGORITHMS = "BreadthFirstSearch\nDepthFirstSearch";
			FileWriter fw = new FileWriter(alg.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(ALGORITHMS);
			bw.close();
		}

		LinkedList<String> algorithms = new LinkedList<String>();
		BufferedReader reader = new BufferedReader(new FileReader(ALGORITHMS_TXT));
		String line = null;
		while ((line = reader.readLine()) != null) {
			line.trim();
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

		// create directory if it doesn't exist
		File dir = new File("loader_files");
		if(!dir.exists() || !dir.isDirectory()) {
			if(!dir.mkdir()) return null;
		}

		// create tree text file if it doesn't exist, and write known trees to it
		File treeFile = new File(TREES_TXT);
		if(!treeFile.exists() || treeFile.isDirectory()) {
			if(!treeFile.createNewFile()) return null;
			final String TREES = "Tree124\nTree1355\nTree112111";
			FileWriter fw = new FileWriter(treeFile.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(TREES);
			bw.close();
		}

		LinkedList<String> trees = new LinkedList<String>();
		BufferedReader reader = new BufferedReader(new FileReader(TREES_TXT));
		String line = null;
		while ((line = reader.readLine()) != null) {
			line.trim();
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
