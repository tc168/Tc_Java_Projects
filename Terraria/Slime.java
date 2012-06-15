public class Slime extends Undead {

	public Slime(int[][] map, Player p) {
		super(map, p);

	}

	public void run() {

		super.run();

		if (human.getPlayerY() > this.playerY) {
			
		} else if (human.getPlayerY() < this.playerY) {
			
			
				
		
			}

	finaljump = TILESIZE;
jump();
		airTime();
	}
}
