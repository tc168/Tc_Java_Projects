package bullettype;

public class bullet {
    private double x_;
    private double y_;
    private int numCollisions_;

    public bullet (double x, double y, int numCollisions) {
        x_ = x;
        y_ = y;
        numCollisions_ = numCollisions;
    }

    public void setX (double x) {
        x_ = x;
    }

    public void setY (double y) {
        y_ = y;
    }

    public void setnumCollisions (int numCollisions) {
        numCollisions_ = numCollisions;
    }

    public double getX () {
        return x_;
    }

    public double getY () {
        return y_;
    }

    public int getnumCollisions () {
        return numCollisions_;
    }
}