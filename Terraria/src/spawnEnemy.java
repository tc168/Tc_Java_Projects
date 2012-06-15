import java.util.ArrayList;
import java.util.TimerTask;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

public class spawnEnemy extends TimerTask implements Constants {
	Player human;
	int[][] m;
	CopyOnWriteArrayList<Undead> enemy;

	public spawnEnemy(int[][] map, Player human2,CopyOnWriteArrayList<Undead> enemy2) {
		m = map;
		human = human2;
		enemy = enemy2;
	}

	@Override
	public void run() {
	// intialize the varabile 
		int x;
		int y;
		// keep picking numbers till a enemy succfessly spawn
		while (true) {
			// pick a random row and col 
			y = (int) (Math.random() * (m.length));

			x = (int) (Math.random() * (m[0].length));

			// if the postion is not a wall and undead spot  and also is a nonwall then
			if (human.checkCollision(x * TILESIZE + (human.playerX / 2), y* TILESIZE, PLAYERDIAMETER, WALL) == false) {
				
				if (human.checkCollision(x * TILESIZE + (human.playerX / 2), y * TILESIZE,PLAYERDIAMETER, UNDEAD) == false) {
					
					if (m[(int) Math.floor(x)][(int) Math.floor(y)] == 0) {
						// pick a random number up to 2  
						int chance = (int) (Math.random() * (2));

						if (chance == 0) {
							//spawn Undead if 0 and add to list
							Undead tmp = new Undead(m, human,(x * TILESIZE) + (human.playerX / 2), y* TILESIZE);
							enemy.add(tmp);
							
						} else if (chance == 1) {
							//spawn Slime if 0 and add to list
							Slime tmp = new Slime(m, human, (x * TILESIZE)+ (human.playerX / 2), y * TILESIZE);
							enemy.add(tmp);
							
						}
						break;
					}
				}
			}
		}
		// TODO Auto-generated method stub

	}
}

