import java.util.ArrayList;

public class Trie {
	public final static char DELIMITER = '\u0001';

	public Trie() {
		root = new Node('r');
		size = 0;
	}

	public boolean add(String word) {
		if (add(root, word + DELIMITER, 0)) {
			size++;
			int n = word.length();
			if (n > maxDepth)
				maxDepth = n;
			return true;
		}
		return false;
	}

	private boolean add(Node root, String word, int offset) {
		if (offset == word.length())
			return false;
		int c = word.charAt(offset);

		// Search for node to add to
		Node last = null, next = root.firstChild;
		while (next != null) {
			if (next.value < c) {
				// Not found yet, continue searching
				last = next;
				next = next.nextSibling;
			} else if (next.value == c) {
				// Match found, add remaining word to this node
				return add(next, word, offset + 1);
			}
			// Because of the ordering of the list getting here means we won't
			// find a match
			else
				break;
		}

		// No match found, create a new node and insert
		Node node = new Node(c);
		if (last == null) {
			// Insert node at the beginning of the list (Works for next == null
			// too)
			root.firstChild = node;
			node.nextSibling = next;
		} else {
			// Insert between last and next
			last.nextSibling = node;
			node.nextSibling = next;
		}

		// Add remaining letters
		for (int i = offset + 1; i < word.length(); i++) {
			node.firstChild = new Node(word.charAt(i));
			node = node.firstChild;
		}
		return true;
	}

	public boolean isEntry(String word) {
		if (word.length() == 0)
			throw new IllegalArgumentException("Word can't be empty");
		return isEntry(root, word + DELIMITER, 0);
	}

	private boolean isEntry(Node root, String word, int offset) {
		if (offset == word.length())
			return true;
		int c = word.charAt(offset);

		// Search for node to add to
		Node next = root.firstChild;
		while (next != null) {
			if (next.value < c)
				next = next.nextSibling;
			else if (next.value == c)
				return isEntry(next, word, offset + 1);
			else
				return false;
		}
		return false;
	}

	public int size() {
		return size;
	}

//	public String[] suggest(String prefix) {
//		return suggest(root, prefix, 0);
//	}
//
//	/*
//	 * Recursive function for finding all words starting with the given prefix
//	 */
//	private String[] suggest(Node root, String word, int offset) {
//		if (offset == word.length()) {
//			ArrayList<String> words = new ArrayList<String>(size);
//			char[] chars = new char[maxDepth];
//			for (int i = 0; i < offset; i++)
//				chars[i] = word.charAt(i);
//			getAll(root, words, chars, offset);
//			return words.toArray(new String[words.size()]);
//		}
//		int c = word.charAt(offset);
//
//		// Search for node to add to
//		Node next = root.firstChild;
//		while (next != null) {
//			if (next.value < c)
//				next = next.nextSibling;
//			else if (next.value == c)
//				return suggest(next, word, offset + 1);
//			else
//				break;
//		}
//		return new String[] { word };
//	}
	
	public String[] suggest(String prefix) {
		return suggest(root, prefix, 0);
	}

	/*
	 * Recursive function for finding all words starting with the given prefix
	 */
	private String[] suggest(Node root, String word, int offset) {
		if (offset == word.length()) {
			ArrayList<String> words = new ArrayList<String>(size);
			char[] chars = new char[maxDepth];
			for (int i = 0; i < offset; i++)
				chars[i] = word.charAt(i);
			getAll(root, words, chars, offset);
			return words.toArray(new String[words.size()]);
		}
		int c = word.charAt(offset);

		// Search for node to add to
		Node next = root.firstChild;
		while (next != null) {
			if (next.value < c)
				next = next.nextSibling;
			else if (next.value == c)
				return suggest(next, word, offset + 1);
			else
				break;
		}
		return new String[] { word };
	}

	public String[] getAll() {
		ArrayList<String> words = new ArrayList<String>(size);
		char[] chars = new char[maxDepth];
		getAll(root, words, chars, 0);
		return words.toArray(new String[size]);
	}

	/*
	 * Adds any words found in this branch to the array
	 */
	private void getAll(Node root, ArrayList<String> words, char[] chars,
			int pointer) {
		Node n = root.firstChild;
		while (n != null) {
			if (n.firstChild == null) {
				words.add(new String(chars, 0, pointer));
			} else {
				chars[pointer] = (char) n.value;
				getAll(n, words, chars, pointer + 1);
			}
			n = n.nextSibling;
		}
	}

	public class Node {
		public int value;
		public Node firstChild;
		public Node nextSibling;

		public Node(int value) {
			this.value = value;
			firstChild = null;
			nextSibling = null;
		}
	}

	private Node root;
	private int size;
	private int maxDepth; // Not exact, but bounding for the maximum
}