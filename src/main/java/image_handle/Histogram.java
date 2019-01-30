package image_handle;

public class Histogram {

    private Reading[] red;
    private Reading[] green;
    private Reading[] blue;


    public Histogram(int size){
        this.red = new Reading[size];
        this.green = new Reading[size];
        this.blue = new Reading[size];

    }

    public Reading[] getRed() {
        return red;
    }

    public Reading[] getGreen() {
        return green;
    }

    public Reading[] getBlue() {
        return blue;
    }
}
