package javagameengine.Util;

public abstract class Vector {
    protected double[] data;

    public double magnitude() {
        double mag = 0;
        for (int index = 0; index < data.length; index++) {
            mag += data[index] * data[index];
        }
        return Math.sqrt(mag);
    }

    

}
