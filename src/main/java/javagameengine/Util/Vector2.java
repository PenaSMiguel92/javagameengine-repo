package javagameengine.Util;

public class Vector2 extends Vector {
    public Vector2() {
        
    }
    public Vector2(double x, double y) {
        data = new double[2];
        data[0] = x;
        data[1] = y;
    }

    public double getX() {
        return data[0];
    }

    public double getY() {
        return data[1];
    }

}
