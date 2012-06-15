import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class Boggle {
	int numDimensions = 3; // dimension of board

	private char[][] board; // the board (a-z)
	private boolean[][] visited; // for dfs
	public Trie dictionary; // list of words
	private int numWords;
	private ArrayList<String> computerFoundWords = new ArrayList<String>();

	// create random numDimensions-by-numDimensions board
	public Boggle(int dim) throws FileNotFoundException {
		// initalize varabiles
		numDimensions = dim;
		this.dictionary = dictionary;
		visited = new boolean[numDimensions][numDimensions];
		board = new char[numDimensions][numDimensions];

		// generate random board
		for (int i = 0; i < numDimensions; i++)
			for (int j = 0; j < numDimensions; j++)
				board[i][j] = (char) (Math.random() * 26 + 'a');

		// read in the list of words
		dictionary = new Trie();

		Scanner scanner = new Scanner(new File("dictionary.txt"));

		while (scanner.hasNextLine())
			dictionary.add(scanner.nextLine());

		// System.out.println(scanner.nextLine());
		showWords();
	}

	// show all words, starting from each possible starting place
	public void showWords() {
		for (int i = 0; i < numDimensions; i++) {
			for (int j = 0; j < numDimensions; j++) {
				dfs("", i, j);
			}
		}
	}

	// run depth first search starting at cell (i, j)
	private void dfs(String prefix, int i, int j) {

		// checks boundries
		if (i < 0 || j < 0 || i >= numDimensions || j >= numDimensions)
			return;

		// can't visited a cell more than once
		if (visited[i][j])
			return;

		// not allowed to reuse a letter
		visited[i][j] = true;

		// found a word
		prefix = prefix + board[i][j];
		System.out.println(prefix);
		
		if (dictionary.isEntry(prefix)) {
			// System.out.println(prefix);
			numWords++;
			computerFoundWords.add(prefix);
		}

		// consider all neighbors
		if (dictionary.suggest(prefix).length > 1) {
			for (int ii = -1; ii <= 1; ii++)
				for (int jj = -1; jj <= 1; jj++)
					dfs(prefix, i + ii, j + jj);
		}

		visited[i][j] = false;

	}

	// just the board
	public String toString() {
		String s = "";
		for (int i = 0; i < numDimensions; i++) {
			for (int j = 0; j < numDimensions; j++) {
				s = s + board[i][j] + " ";
			}
			s = s + "\n";
		}
		return s;
	}

	public boolean hasWord(String word) {
		for (String words : computerFoundWords) {
			if (words.equals(word))
				return true;
		}
		return false;
	}

	public String getChar(int row, int col) {
		return board[row][col] + "";
	}

	public ArrayList<String> getAllWords() {
		return computerFoundWords;
	}

	public int getNumDimensions() {
		return numDimensions;
	}

	public void setNumDimensions(int numDimensions) {
		this.numDimensions = numDimensions;
	}
}