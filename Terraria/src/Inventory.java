import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Inventory extends JFrame implements ActionListener, Constants,KeyListener {
	// create buttons
	public Button button[] = new Button[2];
	// reference object to previous class
	public TPanel tpanel;
	
	//Constructor
	public Inventory(TPanel tp) {

		setVisible(true);
	// set name of buttons
		button[0] = new Button("Sword");
		button[1] = new Button("Pickaxe");

	// set the size of frame
		setBounds(0, 0, 300, 200);
	// set the layout into grid
		setLayout(new GridLayout(button.length, 1));
	// add key listener
		addKeyListener(this);
		tpanel = tp;
	//cycle the buttons to add action listener and onto the frame
		for (int i = 0; i < 2; i++) {
	
			button[i].addActionListener(this);
			add(button[i]);
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
// Set index to a number to identify which weapon player is holding
		if (e.getActionCommand().equals("Pickaxe")) {
			tpanel.setIndex(1);

		} else if (e.getActionCommand().equals("Sword")) {
			tpanel.setIndex(2);

		} 
// if any buttons pressed then resume game and back to screen
		tpanel.resume();
		tpanel.pause = false;
		this.toBack();
	}

	@Override
	public void keyPressed(KeyEvent e) {
// if I is pressed then resume game and back to screen
		if (e.getKeyCode() == e.VK_I) {
			tpanel.resume();
			tpanel.pause = false;
			this.toBack();
		}
	}

	public void exit() {
// exits the Inventory Frame when called
		System.exit(0);
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}
