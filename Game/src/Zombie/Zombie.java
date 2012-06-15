package Zombie;

import java.awt.Paint;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import coordinatelist.coordinate;

public class Zombie {
	private double x_;
	private double y_;
	private int health_;
	private static int tileSize = 35;
	private static int row;
	private static int col;
	private static int arrayMapZombie[][];
	private static int arrayMap2[][];
	public static Vector<coordinate> Coords = new Vector<coordinate>();
	public static Vector<Point> coords2nd = new Vector<Point>();

	public Zombie(double x, double y, int health, int array[][]) {
		x_ = x;
		y_ = y;
		health_ = health;
		arrayMap2 = array;
		arrayMapZombie = arrayMap2;
	}

	public void setX(double x) {
		x_ = x;
	}

	public void setY(double y) {
		y_ = y;
	}

	public void setArray(int array[][]) {
		arrayMapZombie = array;
	}

	public double getX() {
		return x_;
	}

	public double getY() {
		return y_;
	}

	public int[][] getArrayMap() {
		return arrayMapZombie;
	}

	public void fillMazedijAlgo(int tileRow, int tileCol, int pRow, int pCol) {
		Coords.add(new coordinate(pRow, pCol, 2));
		arrayMapZombie[pRow][pCol] = 2;

		do {
			if (Coords.get(0).getY() > 0)
				dijAlgo(Coords.get(0).getX(), Coords.get(0).getY() - 1);
			if (Coords.get(0).getY() < tileCol - 1)
				dijAlgo(Coords.get(0).getX(), Coords.get(0).getY() + 1);
			if (Coords.get(0).getX() < tileRow - 1)
				dijAlgo(Coords.get(0).getX() + 1, Coords.get(0).getY());
			if (Coords.get(0).getX() > 0)
				dijAlgo(Coords.get(0).getX() - 1, Coords.get(0).getY());
			Coords.remove(0);
		} while (Coords.isEmpty() != true);


		coords2nd = solve2ndTime((int) Math.floor(y_ / 41), (int) Math
				.floor(x_ / 41), pRow, pCol, tileRow, tileCol);
		for (int ctr = 0; ctr < coords2nd.size(); ++ctr) {
			System.out.println("X:" + coords2nd.get(ctr).getX() + " Y:"
					+ coords2nd.get(ctr).getY());
		}
	}

	public void dijAlgo(int x, int y) { // Set the places that are open based on
		// the previous place's distance
		if (arrayMapZombie[x][y] == 0) {
			Coords.add(new coordinate(x, y, Coords.get(0).getDistance() + 1));
			arrayMapZombie[x][y] = Coords.get(0).getDistance() + 1;
		}
	}

	public Vector solve2ndTime(int rows, int cols, int pRow, int pCol, int tileRow, int tileCol) {
		// from the send to the end
		Vector<Point> coords = new Vector<Point>();
		coords.add(new Point(rows-pRow, cols-pCol)); // Add the mouse row and column to be
		
		// the beginning of the list
		int coordCounter = 0;
		int ctr = 0;
		for(int x = 0; x < tileRow; x++) {
			for(int y = 0; y < tileCol; y++) {
				if(arrayMapZombie[x][y] == 1) arrayMapZombie[x][y] = 65555;
			}
		}
		for (int y = 0; y < tileRow; y++) {
			for (int z = 0; z < tileCol; z++) {
				System.out.print(arrayMapZombie[y][z] + "        ");
			}
			System.out.println("");
		}
		System.out.println("");
		do { // Check if the coordinate's neighbours are valid and if they're
			// lower, if they're lower, move to the lower value one
			if (coords.get(coordCounter).y > 0)
				if (arrayMapZombie[coords.get(coordCounter).x][coords.get(coordCounter).y - 1] < arrayMapZombie[coords.get(coordCounter).x][coords.get(coordCounter).y]) {
					if (coordCounter !=0)
					coords.add(new Point(coords.get(coordCounter).x, coords.get(coordCounter).y - 1));
					coordCounter++;
				}
			if (coords.get(coordCounter).y < cols)
				if (arrayMapZombie[coords.get(coordCounter).x][coords.get(coordCounter).y + 1] < arrayMapZombie[coords.get(coordCounter).x][coords.get(coordCounter).y]) {
					coords.add(new Point(coords.get(coordCounter).x, coords.get(coordCounter).y + 1));
					coordCounter++;
				}
			if (coords.get(coordCounter).x < rows )
				if (arrayMapZombie[coords.get(coordCounter).x][coords.get(coordCounter).y] > arrayMapZombie[coords.get(coordCounter).x + 1][coords.get(coordCounter).y]) {
					coords.add(new Point(coords.get(coordCounter).x + 1, coords.get(coordCounter).y));
					coordCounter++;
				}
			if (coords.get(coordCounter).x > 0)
				if (arrayMapZombie[coords.get(coordCounter).x][coords.get(coordCounter).y] > arrayMapZombie[coords.get(coordCounter).x - 1][coords.get(coordCounter).y]) {
					coords.add(new Point(coords.get(coordCounter).x - 1, coords.get(coordCounter).y));
					coordCounter++;

				}
			
		
		} while (arrayMapZombie[coords.get(coordCounter).x][coords.get(coordCounter).y] != 2);
		arrayMapZombie[pRow][pCol] = 2;
		for(int x = 0; x < tileRow; x++) {
			for(int y = 0; y < tileCol; y++) {
				if(arrayMapZombie[x][y] == 65555) arrayMapZombie[x][y] = 1;
			}
		}
		return coords;
	}
}