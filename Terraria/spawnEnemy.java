import java.util.ArrayList;
import java.util.TimerTask;
import java.util.Vector;

public class spawnEnemy extends TimerTask implements Constants {
	Player human;
	int[][] m;
	ArrayList<Undead> enemy;

	public spawnEnemy(int[][] map, Player human2, ArrayList<Undead> enemy2) {
		m = map;
		human = human2;
		enemy = enemy2;
	}

	@Override
	public void run() {
		int x;
		int y;
		while (true) {
			y = (int) (Math.random() * (m.length * TILESIZE));
			x = (int) (Math.random() * (m[0].length * TILESIZE));
			if (human.checkCollision(x, y, PLAYERDIAMETER, WALL) == false) {
				if (m[(int) Math.floor(x / TILESIZE)][(int) Math
						.floor(y / TILESIZE)] == 0) {

					Undead tmp = new Undead(m, human, x, y);
					enemy.add(tmp);

					break;
				}
			}
		}
		// TODO Auto-generated method stub

	}
}
