package coordinatelist;
public class coordinate {
    private int x_;
    private int y_;
    private int distance_;

    public coordinate (int x, int y, int distance) {
        x_ = x;
        y_ = y;
        distance_ = distance;
    }

    public void setX (int x) {
        x_ = x;
    }

    public void setY (int y) {
        y_ = y;
    }

    public void setDistance (int distance) {
        distance_ = distance;
    }

    public int getX () {
        return x_;
    }

    public int getY () {
        return y_;
    }

    public int getDistance () {
        return distance_;
    }
}