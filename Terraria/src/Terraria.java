import java.awt.Container;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Terraria extends JFrame implements Constants, ActionListener {
	// Set the defualt map Size
	int tileRow = 14;
	int tileCol = 15;
	// Set the map
	private int arrayMap[][] = {
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0 },
			{ 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0 },
			{ 1, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0 },
			{ 1, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0 },
			{ 1, 0, 1, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0 },
			{ 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0 } };
	// The main class that handles the Graphics
	private TPanel tp;
	// Save the map
	public MenuItem SaveMenu = new MenuItem("Save");

	// Constructor to initalize the game and menus
	public Terraria() {
		super("Final Project Game");

		Menu FileMenu = new Menu("File");
		MenuBar menuBar = new MenuBar();
		menuBar.add(FileMenu);
		SaveMenu.addActionListener(this);
		setMenuBar(menuBar);
		FileMenu.add(SaveMenu);
		filereader();
		Container c = getContentPane(); // default BorderLayout used
		tp = new TPanel(this, arrayMap);

		c.add(tp, "Center");

		// pack();
		setSize(arrayMap[0].length * TILESIZE, arrayMap.length * TILESIZE);

		setVisible(true);

	}

	// Read the map file and store in the game
	public void filereader() {

		String name = JOptionPane.showInputDialog("input desired file name", 1);
		try {
			// Get the File then Buffer it
			final FileReader fr = new FileReader(name + ".txt");
			final BufferedReader br = new BufferedReader(fr);
			String line = br.readLine();

			// Read first two lines to set rows and columns
			StringTokenizer token = new StringTokenizer(line);
			tileRow = Integer.parseInt(token.nextToken());
			line = br.readLine();
			token = new StringTokenizer(line);
			tileCol = Integer.parseInt(token.nextToken());
			line = br.readLine();
			arrayMap = new int[tileCol][tileRow];

			// Read rest of the file into 2-d maze
			int x = 0, y = 0;
			while (line != null) {
				token = new StringTokenizer(line);
				while (token.hasMoreTokens()) {
					arrayMap[x][y] = Integer.parseInt(token.nextToken());
					++y;
				}
				line = br.readLine();
				++x;
				y = 0;
			}

		} catch (final FileNotFoundException e) {
			System.err.println("file not found");
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		new Terraria();
	}

	@Override
	// Save the map file into computer when menu save is clicked
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Save")) {
			Writefile();
		}

	}

	// Write the map file into the computer
	public void Writefile() {
		String name = JOptionPane.showInputDialog("input desired file name", 1);

		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(name
					+ ".txt"));
			out.write(tileRow + "");
			out.newLine();
			out.write(tileCol + "");
			out.newLine();
			for (int row = 0; row < tileRow; ++row) {
				for (int col = 0; col < tileCol; ++col) {
					out.write(arrayMap[row][col] + " ");
				}
				out.newLine();
			}
			out.close();
		} catch (IOException e) {
		}

	}

}
