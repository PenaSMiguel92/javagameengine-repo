package javagameengine.Util;

public class Color extends Vector {
    public Color() {

    }

    public Color(double r, double g, double b, double a) {
        data = new double[4];
        data[0] = r;
        data[1] = g;
        data[2] = b;
        data[3] = a;
    }

    public double getR() {
        return data[0];
    }

    public double getG() {
        return data[1];
    }

    public double getB() {
        return data[2];
    }

    public double getA() {
        return data[3];
    }
}
