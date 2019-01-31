package image_handle;

public class Histogram {

    private double[] red;
    private double[] green;
    private double[] blue;
    private int size;

    public Histogram(int size){
        this.red = new double[size];

        this.green = new double[size];

        this.blue = new double[size];
        this.size = size;

    }

    public double[] getRed() {
        return red;
    }

    public double[] getGreen() {
        return green;
    }

    public int getSize() {
        return size;
    }

    public double[] getBlue() {
        return blue;
    }

    public Double[] getRedObjs(){

        Double[] reds = new Double[size];
        for (int i = 0; i < size; i++){
            reds[i] = red[i];
        }
        return reds;
    }

    public Double[] getBlueObjs(){

        Double[] blues = new Double[size];
        for (int i = 0; i < size; i++){
            blues[i] = blue[i];
        }
        return blues;
    }

    public Double[] getGreenObjs(){

        Double[] greens = new Double[size];
        for (int i = 0; i < size; i++){
            greens[i] = green[i];
        }
        return greens;
    }

}
