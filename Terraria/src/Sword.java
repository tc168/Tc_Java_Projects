import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class Sword implements Constants {
	Rectangle r = new Rectangle();

	public void draw(int num ,boolean mouseclick, Player human, Graphics2D g2) {
		// if weapon index is 2 then 
		if (num == 2){
			// set bounds depending if mouses are clicked  and facing left or right
			if (mouseclick == true && human.isRight == true) {
				r.setBounds(human.getPlayerX() + PLAYERDIAMETER,human.getPlayerY(), 50, 10);

			} else if (mouseclick == false && human.isRight == true) {
				r.setBounds(human.getPlayerX() + PLAYERDIAMETER,human.getPlayerY() - 50, 10, 50);

			} else if (mouseclick == false && human.isRight == false) {
				r.setBounds(human.getPlayerX() - 10, human.getPlayerY() - 50, 10,50);

			} else if (mouseclick == true && human.isRight == false) {
				r.setBounds(human.getPlayerX() - 50, human.getPlayerY(), 50, 10);

			}
			// draw the final result 
			g2.draw(r);
		}else{
		// if weapon is not 2 then set the size to 0
			r.setBounds(human.getPlayerX() ,human.getPlayerY(),0,0 );
		}


	}
}
