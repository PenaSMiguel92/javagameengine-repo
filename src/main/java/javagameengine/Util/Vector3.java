package javagameengine.Util;

public class Vector3 extends Vector {
    public Vector3() {
        
    }
    public Vector3(double x, double y, double z) {
        data = new double[3];
        data[0] = x;
        data[1] = y;
        data[2] = z;
    }

    public double getX() {
        return data[0];
    }

    public double getY() {
        return data[1];
    }

    public double getZ() {
        return data[2];
    }

}
