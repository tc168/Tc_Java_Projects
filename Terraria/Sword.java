import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class Sword implements Constants {
	Rectangle r = new Rectangle();
	Image swordI;

	public void draw(boolean mouseclick, Player human, Graphics2D g2) {
		if (mouseclick == true && human.isRight == true) {
			r.setBounds(human.getPlayerX() + PLAYERDIAMETER,
					human.getPlayerY(), 50, 10);

		} else if (mouseclick == false && human.isRight == true) {
			r.setBounds(human.getPlayerX() + PLAYERDIAMETER,
					human.getPlayerY() - 50, 10, 50);

		} else if (mouseclick == false && human.isRight == false) {
			r.setBounds(human.getPlayerX() - 10, human.getPlayerY() - 50, 10,
					50);
			swordI = Toolkit.getDefaultToolkit().getImage("swordup.png");
		} else if (mouseclick == true && human.isRight == false) {
			r.setBounds(human.getPlayerX() - 50, human.getPlayerY(), 50, 10);

		}
		g2.draw(r);
	}
}
