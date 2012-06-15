import java.awt.Color;

import java.awt.Button;
import java.awt.Event;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Timer;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class TPanel extends JPanel implements KeyListener, MouseListener,Constants {
	
	// boolean to check the keys pressed
	boolean key_right, key_left, key_down, key_up, key_space;

	private  int[][] world;
	private Player human;

	private Inventory I;
	// position of mouse 
	private int mRow;
	private int mCol;
	
	// ArrayList to store enemy 
	public CopyOnWriteArrayList<Undead> enemy = new CopyOnWriteArrayList<Undead>();

	// Index to incidate what weapon human is holding
	private int index = 0;
	
	//Timer to use for spawning enemy
	private Timer timer;

	boolean mouseclick = false;
	boolean pause = false;


	public TPanel(Terraria t, int[][] map) {

		//initalize varabiles
		
		setWorld(map);
		setHuman(new Player(getWorld()));
		setVisible(true);
		setFocusable(true); 
		setSize(getWorld()[0].length * TILESIZE, getWorld().length * TILESIZE);

		I = new Inventory(this);
		addMouseListener(this);
		addKeyListener(this);

		//spawn the enemy at certain time
		timer = new Timer();
		timer.scheduleAtFixedRate(new spawnEnemy(world, getHuman(), enemy), 0,1000);
	}

	public void paint(Graphics g) {

		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		// scroll the screen 
		g2.translate(-getHuman().playerX / 2, (-getHuman().playerY / 2)- (3 * TILESIZE));
		// draw the sword
		getHuman().s.draw(index, mouseclick, getHuman(), g2);
		
		// Set background color
		setBackground(Color.white);
		//draw players image
		g2.drawImage(getHuman().i, getHuman().getPlayerX(), getHuman().getPlayerY() - 20, PLAYERDIAMETER, PLAYERDIAMETER + 20, this);
		//draw player status above him
		g2.drawString("Human HP is " + getHuman().HP + "/" + getHuman().MAXHP,getHuman().getPlayerX(), getHuman().getPlayerY() - 20);
		g2.drawString("Level: " + getHuman().getLevel() + " Atk: " + getHuman().getAtk()+ "Exp to Level " + getHuman().getNEEDEXP(),getHuman().getPlayerX(), getHuman().getPlayerY() - 40);
		g2.drawString("Blocks " + human.getBlockctr(), getHuman().getPlayerX(),getHuman().getPlayerY() - 60);
		
		//cycle thorugh  the map
		for (int row = 0; row < getWorld().length; row++) {
			for (int col = 0; col < getWorld()[0].length; col++) {
				// Draw a black square for a wall
				if (getWorld()[row][col] == WALL) {
					
					g2.setColor(Color.black);
					//Draw a black square
					g2.fillRect(TILESIZE * col, TILESIZE * row, TILESIZE,TILESIZE);
					// Draw a blank square for a wall
				} else if (getWorld()[row][col] == NOWALL) {
					g2.setColor(Color.black);

				} else if (getWorld()[row][col] == UNDEAD) {
					g2.setColor(Color.black);
				} else {
					// update the map 
					getWorld()[row][col] = NOWALL;
				}
			}
		}
		for (Undead u : enemy) {
			// draw the undead
			u.draw(g2);
			// if Undead is dead
			if (u.HP <= 0) {
			// Human  gains Exp
				getHuman().setEXPBAR(getHuman().getEXPBAR() + u.EXPGAIN);
			//Destory the Undead Ai timer
				u.timer.cancel();
			//Set the Map postion to 0
				world[u.getpRow()][u.getpCol()] = 0;
			//remove undead from array list
				enemy.remove(u);
			}

		}

		setBackground(Color.white);
//if player is less 0 
		if (human.HP < 0) {
			// pause the game and exit the program
			pause();
			I.exit();
			System.exit(0);
			
		}
		//check if key right is pressed
		if (key_right) {
			// Move the player to the right
			getHuman().right();

		}
		
		//check if keyleft is pressed
		if (key_left) {
			// Move the player to the left
			getHuman().left();
		}
		
		// check if space is pressed and only once
		if (key_space && getHuman().isJumprelease() == false) {
			// jump once and set to be able to jump
			getHuman().jump();
			getHuman().setJumprelease(true);
		}
		
		
		if (pause == false) {
			//Method to make player jump
			getHuman().airTime();
		}
		
		repaint();
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void pause() {
		// stop the Timer of all classes
		for (Undead u : enemy) {
			u.pausetimer();

		}

		human.hptimer.cancel();
		timer.cancel();
	}
// resume timer of all class
	public void resume() {
	
		for (Undead u : enemy) {
			u.resumetimer();
		}
		timer = new Timer();
		timer.scheduleAtFixedRate(new spawnEnemy(world, getHuman(), enemy), 0,
				1000);
		human.hptimer = new Timer();
		human.hptimer.scheduleAtFixedRate(human.new HPrecover(), 0, 1000);
	}

	public void keyReleased(KeyEvent e) {
		// Check using getKeyCode for all four directional keys!
		if (e.getKeyCode() == e.VK_S) 
			key_down = false;
		if (e.getKeyCode() == e.VK_W) 
			key_up = false;
		if (e.getKeyCode() == e.VK_D) 
			key_right = false;
			
		
		if (e.getKeyCode() == e.VK_A) 
			key_left = false;
			
		

		if (e.getKeyCode() == e.VK_SPACE) {
			getHuman().setJumprelease(false);
			key_space = false;
			getHuman().finaljump = getHuman().setjump;
			System.out.print(getHuman().setjump);

		}
	}

	public void keyPressed(KeyEvent e) {
		getHuman().pRow = (int) (Math.floor(getHuman().playerY + PLAYERRADIUS) / TILESIZE);
		getHuman().pCol = (int) (Math.floor(getHuman().playerX + PLAYERRADIUS) / TILESIZE);

		getHuman().playermap[getHuman().pRow][getHuman().pCol] = PLAYER;
		// Check using getKeyCode for all four directional keys!

		if (e.getKeyCode() == e.VK_S) // If down is pressed...
			key_down = true;
		if (e.getKeyCode() == e.VK_W) // If up is pressed...
			key_up = true;
		if (e.getKeyCode() == e.VK_D) 
			key_right = true;

		
		if (e.getKeyCode() == e.VK_A) 
			key_left = true;

		
		if (e.getKeyCode() == e.VK_SPACE) {
			if (getHuman().isJumprelease() == false) {
				key_space = true;

			}

			if (getHuman().setjump < (150)) {
				getHuman().setjump = getHuman().setjump + 20;
			}
			if (getHuman().setjump >= (150)) {
				getHuman().finaljump = getHuman().setjump;
				key_space = false;

			}
		}
		
	// if I is pressed bring up inventory system
		if (e.getKeyCode() == e.VK_I) {
			key_right = false;
			key_left = false;
			key_down = false;

			key_up = false;
			key_space = false;
			pause();
			I.toFront();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
// tell mouse click is true
		mouseclick = true;
		if (index == 1) {
			// update Mouse Postion on map
			mRow = (int) (Math.floor(((e.getY() + getHuman().playerY / 2) + (3 * TILESIZE))/ TILESIZE));
			mCol = (int) (Math.floor((e.getX() + getHuman().playerX / 2)/ TILESIZE));

			// check which mouse button is pressed
			switch (e.getModifiers()) {
			//if left button
			case InputEvent.BUTTON1_MASK: {

				//if wall then change into a no wall and add to block coutner
				if (getWorld()[mRow][mCol] == WALL) {
					getWorld()[mRow][mCol] = NOWALL;
					human.setBlockctr(human.getBlockctr() + 1);
				}
				break;
			}
			
			//if right button
			case InputEvent.BUTTON3_MASK: {
				// as long more then 0 blocks then 
				if (human.getBlockctr() > 0) {
					//if nowall then change into a  wall and minus to block coutner
					if (getWorld()[mRow][mCol] == NOWALL) {
						getWorld()[mRow][mCol] = WALL;
						human.setBlockctr(human.getBlockctr() - 1);
					}
				}
				break;
			}
			}
		}

		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		mouseclick = false;
	}

	// TODO Auto-generated method stub

	public void setWorld(int[][] world) {
		this.world = world;
	}

	public int[][] getWorld() {
		return world;
	}

	public void setHuman(Player human) {
		this.human = human;
	}

	public Player getHuman() {
		return human;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}
}
