import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.*;

public class MapEditor extends Frame implements ActionListener {
	public static int Irow = 0;
	public static int Icol = 0;
	public Button button[][] = new Button[Irow][Icol];

	public MenuItem SaveMenu = new MenuItem("Save");
	public static int array[][];

	public static void main(String args[]) {
		String Srow = JOptionPane.showInputDialog("Enter a Row value", 10);
		String Scol = JOptionPane.showInputDialog("Enter a Column value", 10);
		Irow = Integer.parseInt(Srow);
		Icol = Integer.parseInt(Scol);
		array = new int[Irow][Icol];
		MapEditor e = new MapEditor();
		e.setVisible(true);
	}

	public MapEditor() {
		// Creates a menubar for a JFrame
		Menu FileMenu = new Menu("File");
		MenuBar menuBar = new MenuBar();
		menuBar.add(FileMenu);
		SaveMenu.addActionListener(this);
		setMenuBar(menuBar);
		FileMenu.add(SaveMenu);

		// Define and add two drop down menu to the menubar

		Panel p1 = new Panel();

		p1.setLayout(new GridLayout(Irow, Icol, Irow, Icol));

		for (int i = 0; i < button.length; i++) {
			for (int a = 0; a < button[0].length; ++a) {

				button[a][i] = new Button();
				button[a][i].addActionListener(this);
				if (array[a][i] == 0) {
					button[a][i].setBackground(Color.WHITE);
				} else if (array[a][i] == 1) {
					button[a][i].setBackground(Color.BLUE);
				}
				p1.add(button[a][i]);
			}
		}

		add(p1);

		setSize(525, 490);
		setTitle("Testing button");
		setVisible(true);

	}

	public void update() {
		for (int i = 0; i < button.length; i++) {
			for (int a = 0; a < button[0].length; ++a) {
				if (array[a][i] == 0) {
					button[a][i].setBackground(Color.WHITE);
				} else if (array[a][i] == 1) {
					button[a][i].setBackground(Color.BLUE);
				}
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals("Save")) {
			Writefile();
		} else {
			for (int ctr1 = 0; ctr1 < Irow; ++ctr1) {
				for (int ctr2 = 0; ctr2 < Icol; ++ctr2) {
					if (e.getSource() == button[ctr2][ctr1]) {
						if (array[ctr2][ctr1] == 0) {
							array[ctr2][ctr1] = 1;

						} else if (array[ctr2][ctr1] == 1) {
							array[ctr2][ctr1] = 0;
						}
						update();
					}
				}

			}

		}
	}

	public void Writefile() {
		String name = JOptionPane.showInputDialog("input desired file name", 1);

		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(name
					+ ".txt"));
			out.write(Irow + "");
			out.newLine();
			out.write(Icol + "");
			out.newLine();
			for (int row = 0; row < Irow; ++row) {
				for (int col = 0; col < Icol; ++col) {
					out.write(array[col][row] + " ");
				}
				out.newLine();
			}
			out.close();
		} catch (IOException e) {
		}

	}
}