package image_handle;

public class Histogram {

    private double[] red;
    private double[] green;
    private double[] blue;


    public Histogram(int size){
        this.red = new double[size];
        this.green = new double[size];
        this.blue = new double[size];

    }

    public double[] getRed() {
        return red;
    }

    public double[] getGreen() {
        return green;
    }

    public double[] getBlue() {
        return blue;
    }
}
