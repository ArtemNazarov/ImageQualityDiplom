package image_handle;

public class Reading {
    private int x;
    private double value;

    public Reading(int index, double value) {
        this.x = index;
        this.value = value;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
