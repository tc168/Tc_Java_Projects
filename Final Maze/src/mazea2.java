//Author: Daniel Paek , Thomas Chang 
//Due Date : November 29th 
//Name: Maze Solver
//Description : A Program that will solve a maze by going though many method 
//				to move , configure the maze. to move the mouse toward the cheese
//Major skills : array  , method , paint and class usage

import java.awt.*;
import java.io.*;
import java.util.StringTokenizer;
import javax.swing.*;

public class mazea2 extends Frame {

	public static int maze[][];
	public static int row, col, mousex, mousey, movesetx[], movesety[];
	public static int notfound = -1;
	public static int index = -1;
	public static int size = 15;
	public static boolean cheesefound = false;
	public static boolean found = false;
	public static boolean replace = true;
	Image img = Toolkit.getDefaultToolkit().getImage("mouse.jpg");

	// set the name on top of frame
	public mazea2() {
		super("The Maze");

	}

	// method to set the screen of the frame
	public void windows() {

		setBackground(Color.white);
		setSize(1000, 1000);
		setVisible(true);
	}

	// method to move the mouse
	public void mouse(int id) {

		int movex = 0, movey = 0;
		while (found == false) {
			movex = movey = 0;
			// right column check for cheese
			if (mousey + 1 < col && maze[mousex][mousey + 1] == 9) {
				movex = 0;
				movey = 1;
			}
			// downwards row check for cheese
			if (mousex + 1 < row && maze[mousex + 1][mousey] == 9) {
				movex = 1;
				movey = 0;
			}
			// left column check for cheese
			if (mousey - 1 >= 0 && maze[mousex][mousey - 1] == 9) {
				movex = 0;
				movey = -1;
			}
			// upwards row check for cheese
			if (mousex - 1 >= 0 && maze[mousex - 1][mousey] == 9) {
				movex = -1;
				movey = 0;
			}
			// if found cheese, found = true
			if (maze[mousex + movex][mousey + movey] == 9) {
				found = true;
				if (id == 1) {
					JOptionPane.showMessageDialog(null, "Found Cheese");
				}

			} else { // if cheese was not found
				// first right column movement check
				if (mousey + 1 < col && maze[mousex][mousey + 1] == 0)
					movey = 1;
				// first down column movement check
				else if (mousex + 1 < row && maze[mousex + 1][mousey] == 0)
					movex = 1;
				// first left movement check
				else if (mousey - 1 >= 0 && maze[mousex][mousey - 1] == 0)
					movey = -1;
				// first upwards movement check
				else if (mousex - 1 >= 0 && maze[mousex - 1][mousey] == 0)
					movex = -1;
				// if mouse already moved there then backtrack rightwards
				else if (mousey + 1 < col && maze[mousex][mousey + 1] == 2)
					movey = 1;
				// if mouse already moved there then backtrack downwards
				else if (mousex + 1 < row && maze[mousex + 1][mousey] == 2)
					movex = 1;
				// if mouse already moved there then backtrack leftwards
				else if (mousey - 1 >= 0 && maze[mousex][mousey - 1] == 2)
					movey = -1;
				// if mouse already moved there then backtrack upwards
				else if (mousex - 1 >= 0 && maze[mousex - 1][mousey] == 2)
					movex = -1;
				// if the row value is in range and mousex-1 is not a wall then
				// move up
				else if (mousex - 1 >= 0 && maze[mousex - 1][mousey] != 1)
					movex = -1;
				// if column value is in range and mousey-1 is not a wall then
				// move left
				else if (mousey - 1 >= 0 && maze[mousex][mousey - 1] != 1)
					movey = -1;
				// if row value is in range and mousex+1 is not a wall then move
				// downwards
				else if (mousex + 1 < row && maze[mousex + 1][mousey] != 1)
					movex = 1;
				// if column value is in range and mousey+1 is not a wall then
				// move rightwards
				else if (mousey + 1 < col && maze[mousex][mousey + 1] != 1)
					movey = 1;

			}

			if (found == false) { // if its still not found, movement

				mousex = mousex + movex;
				mousey = mousey + movey;
				++index;
				movesetx[index] = movex;
				movesety[index] = movey;
				if (maze[mousex][mousey] == 0)
					maze[mousex][mousey] = 2;
				else if (maze[mousex][mousey] == 2)
					maze[mousex - movex][mousey - movey] = 4;

			}
			if (id == 1) {
				try {
					Thread.sleep(100);
					repaint();
				} catch (final InterruptedException e) {
					System.err.print("Interrupted");
				}
			}
		}
	}

	public void paint(final Graphics g) {
		for (int x = 0; x < row; x++) {
			for (int y = 0; y < col; y++) {

				if (maze[x][y] == 2) {
					g.drawImage(img, 200 + size * y, 150 + size * x, size,
							size, null);
					g.drawRect(200 + size * y, 150 + size * x, size, size);
					g.setColor(Color.black);
				} else if (maze[x][y] == 0) {

					g.drawRect(200 + size * y, 150 + size * x, size, size);
					g.setColor(Color.black);
				} else if (maze[x][y] == 1) {
					g.drawRect(200 + size * y, 150 + size * x, size, size);
					g.fillRect(200 + size * y, 150 + size * x, size, size);
				} else if (maze[x][y] == 3) {
					g.drawRect(200 + size * y, 150 + size * x, size, size);
					g.fillRect(200 + size * y, 150 + size * x, size, size);
				} else if (maze[x][y] == 9) {
					g.drawRect(200 + size * y, 150 + size * x, size, size);
					g.setColor(Color.yellow);
					g.fillRect(200 + size * y, 150 + size * x, size, size);
					g.setColor(Color.black);
				}

				else if (maze[x][y] == 4) {
					g.drawRect(200 + size * y, 150 + size * x, size, size);
					g.setColor(Color.red);
					g.fillRect(200 + size * y, 150 + size * x, size, size);
					g.setColor(Color.black);
				}

			}

		}

	}

	// method to read the txt file
	public static void filereader() {
		try {
			final FileReader fr = new FileReader("maze2.txt");
			final BufferedReader br = new BufferedReader(fr);
			String line = br.readLine();

			// read first two lines to set rows and columns
			StringTokenizer token = new StringTokenizer(line);
			row = Integer.parseInt(token.nextToken());
			line = br.readLine();
			token = new StringTokenizer(line);
			col = Integer.parseInt(token.nextToken());
			line = br.readLine();
			maze = new int[row][col];

			// read rest of the file into 2-d maze
			int x = 0, y = 0;
			while (line != null) {
				token = new StringTokenizer(line);
				while (token.hasMoreTokens()) {
					maze[x][y] = Integer.parseInt(token.nextToken());
					++y;
				}
				line = br.readLine();
				++x;
				y = 0;
			}
			maze[0][0] = 2;
		} catch (final FileNotFoundException e) {
			System.err.println("file not found");
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		}

	}

	// method to fill in all the dead ends in the maze
	public void deadfiller() {
		while (replace != false) {
			replace = false;
			// check the top right corner for walls
			cornerchecker(0, col - 1, +1, -1);
			// check bottom left corner for walls
			cornerchecker(row - 1, 0, -1, +1);
			// check bottom right corner for walls
			cornerchecker(row - 1, col - 1, -1, -1);
			// check top side for walls
			sidechecker(col - 1, 0, 1, 0);
			// check bottom side for walls
			sidechecker(col - 1, row - 1, -1, 0);
			// check right side for walls
			sidechecker(row - 1, 0, 1, 1);
			// check right side for walls
			sidechecker(row - 1, col - 1, -1, 1);
			// check the center for walls
			centerchecker();
		}

	}

	// method to check corner (mousex value you want to check , mousey value you
	// want to check , increment mousex by this amount ,increment mousey by
	// this amount
	public void cornerchecker(final int targetx, final int targety,
			final int xchange, final int ychange) {
		// start for loop
		for (int crnx = 0; crnx < row + 1; crnx = crnx + row - 1) {
			// start nested loop
			for (int crny = 0; crny < col + 1; crny = crny + col - 1) {
				// reset counter
				int cornercount = 0;
				// check if the select(mousex,mousey)is a 0
				if (maze[targetx][targety] == 0) {

					// if selected place is a wall
					if (maze[targetx + xchange][targety] == 1) {

						++cornercount; // +1 to counter
						// if selected place is a wall
					} else if (maze[targetx][targety + ychange] == 1) {

						++cornercount;// +1 to counter
					}
					if (cornercount >= 1) {// if the adjacent side have 1 wall
						// then
						maze[targetx][targety] = 1; // change the middle to 1
						replace = true;// notify that there is a change
					}

				}
			}
		}
	}

	// method to check sides (maze length , value for 1 of the component ,
	// value to increment the mousex or mousey, id to identify to switch
	// inverse coordinates
	public void sidechecker(final int lArray, final int xloop,
			final int xchange, final int id) {
		for (int ctr = 1; ctr < lArray; ++ctr) {
			int sidecounter = 0;
			if (id == 0) {
				// check if the select mousex,mousey is a 0
				if (maze[xloop][ctr] == 0) {

					// check the right side for a wall
					if (maze[xloop][ctr + 1] == 1) {

						++sidecounter;// +1 to counter
					}
					// check the left side for a wall
					if (maze[xloop][ctr - 1] == 1) {

						++sidecounter;// +1 to counter
					}// check etheir the top or bottom side for a wall
					if (maze[xloop + xchange][ctr] == 1) {

						++sidecounter;// +1 to counter

					}
					// if 2/3 sides are wall then itsdeemed as a dead end
					if (sidecounter >= 2) {

						maze[xloop][ctr] = 1; // set the center to a wall
						replace = true; // notify that there is a change
					}
				}
			}

			else if (id == 1) {
				// check if the select mousex,mousey is a 0
				if (maze[ctr][xloop] == 0) {
					// check the right side for a wall
					if (maze[ctr + 1][xloop] == 1) {
						// 
						++sidecounter;// +1 to counter
					}
					// check the left side for a wall
					if (maze[ctr - 1][xloop] == 1) {

						++sidecounter;// +1 to counter
					}
					// check ethier the top or bottom side for a wall
					if (maze[ctr][xloop + xchange] == 1) {

						++sidecounter;// +1 to counter

					}// if 2/3 sides are wall then its deemed as a dead end
					if (sidecounter >= 2) {

						maze[ctr][xloop] = 1; // set the center to a wall
						replace = true;// notify that there is a change
					}
				}

			}
		}
	}

	// method to check centre
	public void centerchecker() {
		// start for loop
		for (int mousex = 1; mousex < row - 1; ++mousex) {
			// start nested for loop
			for (int mousey = 1; mousey < col - 1; ++mousey) {
				// reset counter
				int wallcount = 0;
				if (maze[mousex][mousey] == 0) {// check if the select
					// mousex,mousey is a 0

					if (maze[mousex + 1][mousey] == 1) {// if the bottom side is
						// wall
						++wallcount;// +1 to counter
					}
					if (maze[mousex - 1][mousey] == 1) {// if the top side is
						// wall
						++wallcount;
					}
					if (maze[mousex][mousey + 1] == 1) {// if the right side is
						// wall
						++wallcount;// +1 to counter
					}
					if (maze[mousex][mousey - 1] == 1) {// if the left side is
						// wall
						++wallcount;// +1 to counter
					}

				}
				if (wallcount >= 3) {// if counter is 3/4 then
					maze[mousex][mousey] = 1;// change the center to a wall
					replace = true;// notify there is a change
				}

			}
		}
	}

	public static void main(final String args[]) {
		filereader();

		final mazea2 m = new mazea2();
		m.windows();
		m.cheesefinder();
		// if there is a cheese solve the maze
		if (cheesefound == true) {
			// Initialise the memory array to be used in run 2
			movesetx = new int[row * col];
			movesety = new int[row * col];
			// moves the mouse
			m.mouse(1);
			// rest the global variable
			m.reset();
			// modify the maze
			m.deadfiller();
			// move the mouse
			m.mouse(2);
			// reset the global variable
			m.reset();
			// move mouse faster
			m.run2();
			// if there is no cheese found using cheese found state there is no
			// cheese
		} else if (cheesefound == false) {
			JOptionPane.showMessageDialog(null, "There is no soultion");
			System.exit(0);
		}

	}

	// method to reset global variable necessary for 2nd run
	public void reset() {
		filereader();
		index = -1;
		found = false;
		mousex = 0;
		mousey = 0;
	}

	// method to move mouse with less step
	public void run2() {

		for (int movecounter = 0; movecounter < row * col; ++movecounter) {
			if (mousey + 1 < col && maze[mousex][mousey + 1] == 9) {
				movesetx[movecounter] = 0;
				movesety[movecounter] = 1;
			}
			// downwards row check for cheese
			if (mousex + 1 < row && maze[mousex + 1][mousey] == 9) {
				movesetx[movecounter] = 1;
				movesety[movecounter] = 0;
			}
			// left column check for cheese
			if (mousey - 1 >= 0 && maze[mousex][mousey - 1] == 9) {
				movesetx[movecounter] = 0;
				movesety[movecounter] = -1;
			}
			// upwards row check for cheese
			if (mousex - 1 >= 0 && maze[mousex - 1][mousey] == 9) {
				movesetx[movecounter] = -1;
				movesety[movecounter] = 0;
			}
			// if found cheese, found = true
			if (maze[mousex + movesetx[movecounter]][mousey
					+ movesety[movecounter]] == 9) {
				if (found == false) {
					found = true;
					JOptionPane.showMessageDialog(null,
							"Mouse has found cheese in shorter way");
					System.exit(0);
				}

			}

			mousex = mousex + movesetx[movecounter];
			mousey = mousey + movesety[movecounter];
			if (maze[mousex][mousey] == 0)
				maze[mousex][mousey] = 2;
			else if (maze[mousex][mousey] == 2)
				maze[mousex][mousey] = 4;

			try {
				Thread.sleep(100);
				repaint();
			} catch (final InterruptedException e) {
				System.err.print("Interrupted");
			}

		}
	}

	// method to see there is a cheese on the maze
	public void cheesefinder() {
		for (int searchrow = 0; searchrow < row; ++searchrow) {
			for (int searchcol = 0; searchcol < col; ++searchcol) {
				if (maze[searchrow][searchcol] == 9) {
					// change the boolean to found cheese
					cheesefound = true;
				}

			}
		}
	}
}
