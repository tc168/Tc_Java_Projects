import java.awt.Graphics2D;
import java.util.Timer;
import java.util.TimerTask;

public class Slime extends Undead {
	int EXPGAIN = 10;

	public Slime(int[][] map, Player p, int x, int y) {
		super(map, p, x, y);
		// stop Player timer 
		timer.cancel();
		//create a new  timer to allow hp recover , and to move 
		timer = new Timer();
		timer.scheduleAtFixedRate(new AI2(), 0, 20);
	}

	public void draw(Graphics2D g2) {
		//draw the enemy
		g2.drawOval(playerX, playerY, PLAYERDIAMETER, PLAYERDIAMETER);
		g2.drawString("Slime HP: " + HP, playerX, playerY - 20);
	}

	public class AI2 extends TimerTask {
		// run an AI to follow player 
		public void run() {
			// set the player to empty to check collision with other zombies
			playermap[pRow][pCol] = 0;
			// make enemy fall
			fall();
			// update hitBox
			updateBox();
			// call the Undead AI to follow left and right
			AI();

			//update  the enemy postion on map 
			pRow = (int) (Math.floor(playerY + PLAYERRADIUS) / TILESIZE);
			pCol = (int) (Math.floor(playerX + PLAYERRADIUS) / TILESIZE);

			playermap[pRow][pCol] = UNDEAD;

			
			if (human.getPlayerY() >= playerY - TILESIZE) {
				// if enemy is on same or below human then jump
				jump();
			}
			if (human.getPlayerY() < playerY) {
				// if enemy is  above huamn then fall
				air = false;

			}
			
			//decide wheater to fall or rise
			airTime();

		}
	}
}
