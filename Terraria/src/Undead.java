import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Timer;
import java.util.TimerTask;

public class Undead extends Player {
	Player human;
	Undead undead;
	Timer timer;

	int EXPGAIN = 5;


	public Undead(int[][] map, Player p, int x, int y) {
		super(map);
		playerX = x;
		playerY = y;
		human = p;
		undead = this;
	
		timer = new Timer();
		timer.scheduleAtFixedRate(new AI(), 0, 20);
		// TODO Auto-generated constructor stub

	}

	public void pausetimer() {
		// stop timers 
		timer.cancel();
		hptimer.cancel();
	}

	public void resumetimer() {
		// resume timer
		timer = new Timer();
		hptimer = new Timer();
		hptimer.scheduleAtFixedRate(new HPrecover(), 0, 1000);
		timer.scheduleAtFixedRate(new AI(), 0, 20);
	}

	public void updateBox() {
// if players sword is on the Undead then minus the HP
		if (box.intersects(human.s.r)) {

			HP = HP - human.getAtk();
		}

	}



	public void draw(Graphics2D g2) {
		// Draw the Undead
		g2.drawRect(playerX, playerY, PLAYERDIAMETER, PLAYERDIAMETER);
		g2.drawString("Undead HP: " + HP, playerX, playerY - 20);
	}

	public void AI() {
		// if enemy is same spot human then 
		if (human.getPlayerX() == undead.getPlayerX()) {
			if (box.intersects(human.box)) {
				// minus the human hp 
				human.HP = human.HP - 10;
				if (human.isRight == true&& checkCollision(human.getPlayerX() - TILESIZE,human.getPlayerY(), PLAYERDIAMETER, WALL) == false) {
					
					// set the knockback if human right  then move left  
					human.setPlayerX(human.getPlayerX() - TILESIZE);
					
				} else if (human.isRight == false&& checkCollision(human.getPlayerX() + TILESIZE,human.getPlayerY(), PLAYERDIAMETER, WALL) == false) {
					
					// set the knockback if human leftt  then move right 
					human.setPlayerX(human.getPlayerX() + TILESIZE);
				}
			}
		} else {

			// if undead x is less than human x move right and avoiding colliding other zombies
 			if (human.getPlayerX() > undead.playerX) {
				if (checkCollision((int) (playerX + TILESIZE / 1.5), (playerY),PLAYERDIAMETER, UNDEAD) == false) {
					right();
				} 

				// if undead x is move than human x move left and avoiding colliding other zombies
			} else if (human.getPlayerX() < undead.playerX) {
				if (checkCollision((int) (playerX - TILESIZE / 1.5), (playerY),PLAYERDIAMETER, UNDEAD) == false) {
					left();
				} 
			}

		}

	}

	@Override
	public void fall() {
		
		if (air == false) {
			// fall down while avoiding colliding other zombies
			if (checkCollision(playerX, playerY + 2, PLAYERDIAMETER, WALL) == false) {
				if (checkCollision(playerX, (int) (playerY + TILESIZE / 1.5),
						PLAYERDIAMETER, UNDEAD) == false) {
					playerY = playerY + 2;
				}

			}

			else if (checkCollision(playerX, playerY + 2, PLAYERDIAMETER, WALL) == true) {
				//reset jump
				setjump = 0;
				air = false;
				jump = 0;
			}

		}

	}

	public class AI extends TimerTask {

		@Override
		public void run() {

			// TODO Auto-generated method stub
			// set player to 0 to avoid collding itself
			playermap[pRow][pCol] = 0;

			updateBox();
			AI();
			fall();
			
			// update position
			pRow = (int) (Math.floor(playerY + PLAYERRADIUS) / TILESIZE);
			pCol = (int) (Math.floor(playerX + PLAYERRADIUS) / TILESIZE);

			playermap[pRow][pCol] = UNDEAD;
		}
	}
}
