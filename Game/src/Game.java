import java.awt.event.*; // Use events
import java.awt.*; // Use graphics
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.TimerTask;
import java.util.Timer;

import javax.swing.*; // Use swing stuff (GUI)
import bullettype.bullet;
import java.util.Vector;
import Zombie.*;

public class Game extends JApplet {
	// Constant numbers
	public final int NOWALL = 0;
	public final int WALL = 1;
	public final int PLAYER = 2;
	public final int ZOMBIE = 3;
	public final int TILESIZE = 41;
	public final int PLAYERDIAMETER = 40;
	public final int PLAYERRADIUS = PLAYERDIAMETER / 2;
	public final int BULLETDIAMETER = 10;
	public final int BULLETRADIUS = BULLETDIAMETER / 2;
	public final int BULLETLIMIT = 4;
	Timer timer; // Create a timer
	Toolkit toolkit;

	// The player's current position in the screen
	public static int playerX = 60;
	public static int playerY = 60;
	public static Vector<Zombie> Zombies = new Vector<Zombie>();

	// Size of Diameter and Radius of Player

	// Player Postion on the Row / Column System
	public int pRow = (int) (Math.floor(playerY + PLAYERRADIUS) / TILESIZE);
	public int pCol = (int) (Math.floor(playerX + PLAYERRADIUS) / TILESIZE);
	// Player's HP
	public static int playerhp = 10000;
	// If Dead or alive for player
	public static boolean dead = false;
	// Score for the Player
	public static int Score = 0;

	// The mouse current position in the screen
	public int mouseX, mouseY;
	// Create a bullet class
	public static Vector<bullet> BulletList = new Vector<bullet>(); // c ?
	// List of Shift for each Bullet
	public static Vector<Double> changex = new Vector<Double>();
	public static Vector<Double> changey = new Vector<Double>();
	// A Counter to see how many Bullets there are
	public static int bulletindex = -1;

	// Layout of the Map
	public static int arrayMap[][] = {
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
			{ 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
			{ 1, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 1, 0, 1 },
			{ 1, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1 },
			{ 1, 0, 1, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1 },
			{ 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 } };

	// Size of the Row /Column
	public static int tileRow = 14;
	public static int tileCol = 15;
	// Size of a tile
	public static int hpctr = 0;

	// Speed of Bullet
	public static double speed = 5;

	public Game() {
		// Read file
		filereader();
	}

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

	public class DemoPanel extends JPanel {
		// public Timer timer = new Timer(zombieDelay, this);

		boolean key_right, key_left, key_down, key_up; // Input booleans

		public void gameInit() {
			// testImage = getImage(getDocumentBase(),"testimg.gif"); // Load
			// the test image
			setFocusable(true); // IMPORTANT IMPORTANT! This allows the
			// KeyListener to work!!!
			// timer.start();
			timer = new Timer();
			toolkit = Toolkit.getDefaultToolkit();
			
			timer.scheduleAtFixedRate(new spawnZombies(), 1000, Integer.parseInt(JOptionPane.showInputDialog(null, "enter the speed of zombie spawn")));
			GameInput game_input = new GameInput(); // Make a new video game
			// add KeyListener to Applet
			addKeyListener(game_input);
			// add Mouse Motion Listner to Applet
			addMouseMotionListener(game_input);
			// add MouseListener to Applet
			addMouseListener(game_input);
		}

		public void paintComponent(Graphics page) {
			super.paintComponent(page);
			// Set background color
			setBackground(Color.white);
			// Cycle into the whole 2d array and print out matching squares
			for (int row = 0; row < tileRow; row++) {
				for (int col = 0; col < tileCol; col++) {
					// Draw a black square for a wall
					if (arrayMap[row][col] == WALL) {
						page.setColor(Color.black);
						page.fillRect(TILESIZE * col, TILESIZE * row, TILESIZE,
								TILESIZE);
						// Draw a blank square for a wall
					} else if (arrayMap[row][col] == NOWALL) {
						page.setColor(Color.black);
						page.drawRect(TILESIZE * col, TILESIZE * row, TILESIZE,
								TILESIZE);
						// Draw a green square for a wall
						// } else if (arrayMap[row][col] == ZOMBIE) {
						// page.setColor(Color.green);
						// page.fillRect(TILESIZE * col, TILESIZE * row,
						// TILESIZE,
						// TILESIZE);
						// Draw a red circle for a player
					} else if (arrayMap[row][col] == PLAYER) {
						if (pRow == row && pCol == col) {
							if (dead == false) {
								page.setColor(Color.RED);
								page.fillRoundRect(playerX, playerY, TILESIZE,
										TILESIZE, TILESIZE, TILESIZE);

								arrayMap[row][col] = PLAYER;
								// If the postion is not the player then set
								// back to
								// no wall
							}
						} else {
							arrayMap[row][col] = NOWALL;
						}
					}
			
				}
			}
		
			for (int x = 0; x < Zombies.size(); x++) {
				if (dead == false){
				page.setColor(Color.GREEN);
				page.fillOval((int) Zombies.get(x).getX(), (int) Zombies.get(x)
						.getY(), PLAYERDIAMETER, PLAYERDIAMETER);
				//Zombies.get(x).move(pRow,pCol);
				int Zrow = (int) Math.floor(Zombies.get(x).getY() / 41);
				int Zcol = (int) Math.floor(Zombies.get(x).getX()/ 41);
				int differencec = pCol - Zcol;
				int differencer = pRow - Zrow;

				if (differencer > 0) {
					if (checkCollision(Zombies.get(x).getX(), Zombies.get(x).getY()+2, PLAYERDIAMETER-5, 3)== false)
					Zombies.get(x).setY(Math.floor(Zombies.get(x).getY()+2));
				} else if (differencer < 0) {
					if (checkCollision(Zombies.get(x).getX(), Zombies.get(x).getY()-2, PLAYERDIAMETER-5, 3)== false)
					Zombies.get(x).setY(Math.floor(Zombies.get(x).getY()-2));
				} else {
					if (checkCollision(Zombies.get(x).getX(), Zombies.get(x).getY()-4, PLAYERDIAMETER-5, 3)== false)
					Zombies.get(x).setY(Math.floor(Zombies.get(x).getY()-4));
				}
				if (differencec > 0) {
					if (checkCollision(Zombies.get(x).getX()+2, Zombies.get(x).getY(), PLAYERDIAMETER-5, 3)== false)
					Zombies.get(x).setX(Math.floor(Zombies.get(x).getX()+2));
				} else if (differencec < 0) {
					if (checkCollision(Zombies.get(x).getX()-2, Zombies.get(x).getY(), PLAYERDIAMETER-5, 3)== false)
					Zombies.get(x).setX(Math.floor(Zombies.get(x).getX()-2));
				} else {
					if (checkCollision(Zombies.get(x).getX()-3, Zombies.get(x).getY(), PLAYERDIAMETER, 3)== false)
					Zombies.get(x).setX(Math.floor(Zombies.get(x).getX()-3));
				}
			}
			}
			// Set back default color
			page.setColor(Color.black);
			if (dead == false) {
				// ++Score;
				// checkCollision(0,0,0);
				// Cycle all the bullets made
				for (int ctr = 0; ctr <= bulletindex; ++ctr) {
					// Boolean to check Collision
					boolean bullethit = checkCollision(BulletList.get(ctr)
							.getX()
							+ changex.get(ctr), BulletList.get(ctr).getY()
							+ changey.get(ctr), BULLETDIAMETER, 2);
					// if the Collision is false then
					if (bullethit == false) {
						// Draw the bullet
						page.fillOval((int) BulletList.get(ctr).getX(),
								(int) BulletList.get(ctr).getY(),
								BULLETDIAMETER, BULLETDIAMETER);
						bullet tmp = BulletList.get(ctr);
						// Change the postion of bullet
						tmp.setX(tmp.getX() + changex.get(ctr));
						tmp.setY(tmp.getY() + changey.get(ctr));
						BulletList.set(ctr, tmp);
					}
					// if the Collision is true then
					else if (bullethit == true) {
						if (BulletList.size() == 0)
							break;
						bullet tmp = BulletList.get(ctr);
						// Check how many times it Collided
						if (tmp.getnumCollisions() == 2) {
							// Remove the bullet
							--bulletindex;
							BulletList.remove(ctr);
							changex.remove(ctr);
							changey.remove(ctr);

						} else {
							// get a base angle and calucate the new angles
							double rad = Math.pow(Math
									.sin(1.0003 * Math.sin(1) / 1.333), -1)
									* (Math.PI / 180);

							// changex.set(ctr, Math.cos(rad) * speed);
							// changey.set(ctr, Math.sin(rad) * speed);

							// Create random numbers to check all condition for
							// changex and changey
							int rndy = (int) ((Math.random() * 3) + 1);
							int rndx = (int) ((Math.random() * 2) + 1);

							if (rndx == 1)
								// Turn changex to postive increment to bounce
								// right
								changex.set(ctr, Math.cos(rad) * speed);
							else if (rndx == 2)
								// Turn changex to negative increment to bounce
								// left
								changex.set(ctr, Math.cos(rad) * -speed);

							if (rndy == 1)
								// Turn changey to postive to bounce up
								changey.set(ctr, Math.sin(rad - 90) * speed);
							// else if (rndy == 2
							// changey.set(ctr, Math.sin(rad - 180) * speed);

							else if (rndy == 2)
								// Turn changey to negative to bounce down
								changey.set(ctr, Math.sin(rad - 270) * speed);
							else if (rndy == 3) {

								// Increase counter of collisions
								tmp
										.setnumCollisions(tmp
												.getnumCollisions() + 1);
								BulletList.set(ctr, tmp);
								//System.out.println(tmp.getnumCollisions());
							}
						}
					}
				}

				checkCollision(0, 0, 10, 1);
				for (int zombie = 0; zombie < Zombies.size(); ++zombie) {
					int zombiecolumn = (int) Math.floor((Zombies.get(zombie)
							.getX() + PLAYERRADIUS)
							/ TILESIZE);
					int zombierow = (int) Math.floor((Zombies.get(zombie)
							.getY() + PLAYERRADIUS)
							/ TILESIZE);
					if (zombierow == pRow && zombiecolumn == pCol) {

						page.setColor(Color.YELLOW);
						page.fillRoundRect(playerX, playerY, TILESIZE,
								TILESIZE, TILESIZE, TILESIZE);
						--playerhp;
						// hpctr = 0;
					}

				}
				if (playerhp > 0) {
					page.setColor(Color.blue);
					// Draw the Player

					page.drawOval(playerX, playerY, PLAYERDIAMETER,
							PLAYERDIAMETER);
					// Draw Name , HP , Score , Bullet left
					page.drawString("Player HP: " + playerhp, playerX, playerY);
					page.drawString("Score:" + Score, playerX, playerY - 11);
					page.drawString("Bullets Left:"
							+ (BULLETLIMIT - bulletindex), playerX,
							playerY - 22);
				} else if (playerhp < 0) {
					// Kill the player if HP is 0
					System.out.print("Score is "+Score + " by " + "Player");
					dead = true;
				}

				// Draw the Line from the Center of Player to Mouse
				page.drawLine(playerX + PLAYERRADIUS, playerY + PLAYERRADIUS,
						mouseX, mouseY);

				if (key_down) // Move the player down and Check Collision
					if (checkCollision(playerX, (playerY + 1), PLAYERDIAMETER,
							1) == false)
						++playerY;
				if (key_up) // Move the player up and Check Collision
					if (checkCollision(playerX, (playerY - 1), PLAYERDIAMETER,
							1) == false)
						--playerY;
				if (key_right) // Move the player to the right and Check
					// Collision
					if (checkCollision((playerX + 1), playerY, PLAYERDIAMETER,
							1) == false)
						++playerX;
				if (key_left) // Move the player to the left and Check
					// Collision
					if (checkCollision((playerX - 1), playerY, PLAYERDIAMETER,
							1) == false)
						--playerX;

				for (int index = 0; index < 500000000; index++) {
				} // Slow the Applet down to comfortable speeds
			}

			repaint(); // next frame...
		}

		// id 1 = human , 2 bullet , 3 zombie
		public boolean checkCollision(double d, double y, int h, int id) {
			pRow = (int) (Math.floor(playerY + PLAYERRADIUS) / TILESIZE);
			pCol = (int) (Math.floor(playerX + PLAYERRADIUS) / TILESIZE);
			arrayMap[pRow][pCol] = PLAYER;
			for (int zombies = 0; zombies < Zombies.size(); zombies++) {
				Zombies.get(zombies).setArray(arrayMap);
			}
			int col = 0, row = 0;
			// set function as false
			boolean collision = false;
			// go though a for loop to go create a hitbox for the object
			for (int ctr1 = 0; ctr1 <= h; ctr1 = ctr1 + h) {
				for (int ctr2 = 0; ctr2 <= h; ctr2 = ctr2 + h) {
					// if collision is still false
					if (collision == false) {
						// divide the x and y by tile size to get the position on
						// the array
						row = (int) Math.floor((y + ctr2) / TILESIZE);
						col = (int) Math.floor((d + ctr1) / TILESIZE);
						// if its a wall then it has collided with it

						for (int bullet = 0; bullet < BulletList.size(); bullet++) {
							for (int zombie = 0; zombie < Zombies.size(); zombie++) {
								int bulletrow = (int) Math.floor(BulletList
										.get(bullet).getX()
										/ TILESIZE);
								int bulletcolumn = (int) Math.floor(BulletList
										.get(bullet).getY()
										/ TILESIZE);
								int zombierow = (int) Math.floor((Zombies.get(
										zombie).getX() + PLAYERRADIUS)
										/ TILESIZE);
								int zombiecolumn = (int) Math.floor((Zombies
										.get(zombie).getY() + PLAYERRADIUS)
										/ TILESIZE);

								if (zombierow == bulletrow
										&& zombiecolumn == bulletcolumn
										&& id == 2) {
									// --bulletindex;
									Score = Score + 100;
									Zombies.remove(zombie);
									// BulletList.remove(bullet);
									// changex.remove(bullet);
									// changey.remove(bullet);
									BulletList.get(bullet).setnumCollisions(2);
									collision = true;

									if (BulletList.size() == 0)
										break;
								}

							}
							if (BulletList.size() == 0)
								break;
						}

						if (arrayMap[row][col] == WALL) {
							collision = true;
						} else if (arrayMap[row][col] == PLAYER && id == 2) {
							//--playerhp;
						}
					}
				}

			}
			return collision;
		}

		public class spawnZombies extends TimerTask {
			public void run() {
				int zombiex;
				int zombiey;
				while (true) {
					zombiey = (int) (Math.random() * (tileRow * TILESIZE));
					zombiex = (int) (Math.random() * (tileCol * TILESIZE));

					// System.out.println(zombiex + "   " + zombiey);
					if (checkCollision(zombiex, zombiey, PLAYERDIAMETER, ZOMBIE) == false) {
						if (arrayMap[(int) Math.floor(zombiex / TILESIZE)][(int) Math
								.floor(zombiey / TILESIZE)] == 0) {
							Zombie tmp = new Zombie(zombiex, zombiey, 100,
									arrayMap);
							Zombies.add(tmp);
							//toolkit.beep();
							break;
						}
					}
				}
			}
		}

		private class GameInput implements KeyListener, MouseMotionListener,
				MouseListener {

			@Override
			public void keyReleased(KeyEvent e) {
				// Check using getKeyCode for all four directional keys!
				if (e.getKeyCode() == e.VK_S) // If down is released...
					key_down = false;
				if (e.getKeyCode() == e.VK_W) // If up is released...
					key_up = false;
				if (e.getKeyCode() == e.VK_D)
					key_right = false;
				if (e.getKeyCode() == e.VK_A)
					key_left = false;
			}

			public void keyPressed(KeyEvent e) {
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
					if (speed > 4)
						speed = 2;
					else if (speed < 4)
						speed = 4;
				}
			
			}

			public void mouseMoved(MouseEvent me) {
				// Relocate the MouseX , MouseY every time move Mouse
				mouseX = (int) me.getPoint().getX();
				mouseY = (int) me.getPoint().getY();
			}

			public void mousePressed(MouseEvent arg0) {
				// Limit the amount of bullet to shoot on screen
				if (bulletindex < BULLETLIMIT) {
					// Keep track of how many bullet is on screen
					++bulletindex;
					// Initalize the Bullet and center of player and with 0
					// collision hits
					bullet tmp = new bullet(
							(playerX + PLAYERRADIUS - BULLETRADIUS), (playerY
									+ PLAYERRADIUS - BULLETRADIUS), 0);
					// Calculate the amount of shift to do the next frame
					shift(tmp.getX(), tmp.getY());
					// Add the bullet to the List
					BulletList.add(tmp);
					// playerhp = playerhp+30;

				}
			}

			public void shift(double ax, double ay) {
				// Get a angle from the line between the Mouse and Center of
				// Player
				double angle = Math.atan2(mouseY
						- (playerY + PLAYERRADIUS - BULLETRADIUS), mouseX
						- (playerX + PLAYERRADIUS - BULLETRADIUS))
						* (180 / Math.PI);
				//System.out.println(angle);
				// Covert the angle to radian
				double rad = (angle) * (Math.PI / 180);
				// Calculate the Shift by getting cos / sin ratio and
				// multiplying by a speed
				changex.add(bulletindex, (Math.cos(rad) * speed));
				changey.add(bulletindex, (Math.sin(rad) * speed));
			}

			// ///////////////////EMPTY CLASS//////////////////////////////////
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub

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
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			public void keyTyped(KeyEvent e) {
			}

			public void mouseDragged(MouseEvent arg0) {
			}
		} 
	}

	public void init() {

		// Set the size of Screen
		setSize(arrayMap[0].length * TILESIZE, arrayMap.length * TILESIZE);
		DemoPanel demopanel = new DemoPanel(); // Make a new JPanel for the
		// video demo
		demopanel.gameInit(); // Call the game initialization function before
		// adding the JPanel
		getContentPane().add(demopanel); // Add the JPanel
	}
}