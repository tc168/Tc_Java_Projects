import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.*;

public class MapEditor extends Frame implements ActionListener {
	public static int Irow = 0;
	public static int Icol = 0;

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
// Define and add two drop down menu to the menubar
		Menu FileMenu = new Menu("File");
		MenuBar menuBar = new MenuBar();
		menuBar.add(FileMenu);
		SaveMenu.addActionListener(this);
		setMenuBar(menuBar);
		FileMenu.add(SaveMenu);

		

		Panel p1 = new Panel();
//cycle the map and make top half 0's(no walls) and bottom half 1's (walls)
		for (int row = 0; row < Irow; ++row) {
			for (int col = 0; col < Icol; ++col) {
				if (row >= (Irow/2)) {
					array[row][col]= 1;
				}
				else {
					array[row][col]=0;
				}
			}
		}

		add(p1);

		setSize(325, 400);
	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Save")) {
			Writefile();
		}
	}

	public void Writefile() {
	// Write file into the computer
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
					out.write(array[row][col] + " ");
				}
				out.newLine();
			}
			out.close();
		} catch (IOException e) {
		}

	}
}