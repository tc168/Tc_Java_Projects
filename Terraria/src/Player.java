import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.Timer;
import java.util.TimerTask;

public class Player  implements Constants, Runnable {
// Picute of player
	Image i;
	
// Coordinates of player 
	protected int playerX = 60;
	protected int playerY = 150;
	
// HP MAXHP of player 
	protected int MAXHP = 100;
	protected int HP = MAXHP;

// The weapon that player is holding
	private int index = 0;

// Location of player in the map
	protected int pRow = (int) (Math.floor(playerY + PLAYERRADIUS) / TILESIZE);
	protected int pCol = (int) (Math.floor(playerX + PLAYERRADIUS) / TILESIZE);
	
// Timer to enable player to recover hp over period of time
	Timer hptimer;

// Inidcate in player is midair
	protected boolean air = false;
//inidcate if Space was pressed once
	protected boolean jumprelease = false;
//Counter to see how much pixels were jumped
	protected int jump = 0;
//Counter to set the max jump
	protected int setjump = 0;
//Final jump of number
	protected int finaljump = 150;
//Store the map into player
	protected int playermap[][];
//Inidicate the direction
	protected boolean isRight = true;

//hitbox of player
	protected Rectangle box = new Rectangle();
//create the sword
	Sword s;
//counter to see how many block were collected 
	private int Blockctr;
	
//Level  , amount of exp , Attack , and amount of next level of player
	private int Level = 0;
	private int EXPBAR = 0;
	private int Atk = 1;
	private int NEEDEXP;

//initalize varabile and start HP timer
	public Player(int[][] map) {
		playermap = map;
		s = new Sword();
		// pause = false;
		hptimer = new Timer();
		hptimer.scheduleAtFixedRate(new HPrecover(), 0, 1000);
	}

	public boolean checkCollision(int x, int y, int h, int OBJECT) {
		boolean collision = false;
		updatebox();
		int col = 0, row = 0;
		// set function as false
		collision = false;

		// go though a for loop to go create a hitbox for the object
		for (int ctr1 = 0; ctr1 <= h; ctr1 = ctr1 + h) {
			for (int ctr2 = 0; ctr2 <= h; ctr2 = ctr2 + h) {
				// if collision is still false
				if (collision == false) {
					// divide the x and y by tile size to get the position on the array
				
					col = (int) Math.round((x + ctr1) / TILESIZE);
					row = (int) Math.round((y + ctr2) / TILESIZE);

					// if its a certain object then it has collided with it

					if (row < 0 || col < 0 || row >= playermap.length|| col >= playermap[0].length) {
						collision = true;
					} else if (playermap[row][col] == OBJECT) {
						collision = true;

					}

				}
			}

		}
		return collision;
	}

	public void updatebox() {
	// Update the player status	
		setLevel(getEXPBAR() / 100);
		MAXHP = 100 + (getLevel() * 20);
		setAtk(1 + (getLevel()));
		if (getEXPBAR() % 100 == 0) {
			setNEEDEXP(100);

		} else {
			setNEEDEXP(100 - (getEXPBAR() % 100));
		}
	// Update the player hitbox
		box.setBounds(playerX, playerY, 3 * PLAYERDIAMETER, 3 * PLAYERDIAMETER);

	}

	public void right() {
	// change image to the right image
		i = Toolkit.getDefaultToolkit().getImage("characterRIGHT.png");
		isRight = true;
	//check 1 pixel ahead and if no collision then move right
		if (checkCollision(playerX + 1, (playerY), PLAYERDIAMETER, WALL) == false) {

			++playerX;

		}

	}

	public void left() {
	//change iamge to left iamge
		i = Toolkit.getDefaultToolkit().getImage("characterLEFT.png");
		isRight = false;
	//check 1 pixel ahead and if no collision then move left
		if (checkCollision(playerX - 1, (playerY), PLAYERDIAMETER, WALL) == false) {
			if (playerX > 0) {
				--playerX;
			}
		}
	}

	public void jump() {
		air = true;
	}

	public void airTime() {

		if (air == true) {

			if (jump >= finaljump) {
				// reset jump counters 
				setjump = 0;
				finaljump = 0;
				air = false;

			} else {
				//increase the player jump counter 
				++jump;
				if (checkCollision(playerX, playerY - 2, PLAYERDIAMETER, WALL) == false) {
				// move player when there is no walls above
					playerY = playerY - 2;

				} else if (checkCollision(playerX, playerY - 2, PLAYERDIAMETER,WALL) == true) {
				//tell jump to stop  when player hit a wall
					jump = finaljump;
				}

			}

		}

		fall();
	}

	public void fall() {
		if (air == false) {

			if (checkCollision(playerX, playerY + 2, PLAYERDIAMETER, WALL) == false) {
				// check if player still hasn't hit wall if so move down
				playerY = playerY + 2;

			} else if (checkCollision(playerX, playerY + 2, PLAYERDIAMETER,WALL) == true) {
				//reset the jump counter when player landed
				setjump = 0;
				finaljump = 150;
				air = false;
				jump = 0;
			}
		}

	}

	@Override
	public void run() {

	}

	public void setPlayerX(int playerX) {
		this.playerX = playerX;
	}

	public int getPlayerY() {
		return playerY;
	}

	public void setPlayerY(int playerY) {
		this.playerY = playerY;
	}

	public int getPlayerX() {
		return playerX;
	}

	public int getpRow() {
		return pRow;
	}

	public void setpRow(int pRow) {
		this.pRow = pRow;
	}

	int getpCol() {
		return pCol;
	}

	public void setpCol(int pCol) {
		this.pCol = pCol;
	}

	public void setJumprelease(boolean jumprelease) {
		this.jumprelease = jumprelease;
	}

	public boolean isJumprelease() {
		return jumprelease;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	public void setNEEDEXP(int nEEDEXP) {
		NEEDEXP = nEEDEXP;
	}

	public int getNEEDEXP() {
		return NEEDEXP;
	}

	public void setAtk(int atk) {
		Atk = atk;
	}

	public int getAtk() {
		return Atk;
	}

	public void setBlockctr(int blockctr) {
		Blockctr = blockctr;
	}

	public int getBlockctr() {
		return Blockctr;
	}

	public void setLevel(int level) {
		Level = level;
	}

	public int getLevel() {
		return Level;
	}

	public void setEXPBAR(int eXPBAR) {
		EXPBAR = eXPBAR;
	}

	public int getEXPBAR() {
		return EXPBAR;
	}

	public class HPrecover extends TimerTask {

		@Override
		public void run() {
			// TODO Auto-generated method stub
		// increase the player hp as long not above of max hp
			if (HP < MAXHP) {
				HP++;
			}
		}
	}

}
