package image_handle;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class ContrastChange {

    public static Image equalizeHistogram(Image image){
        int width = (int)image.getWidth();
        int height = (int)image.getHeight();
        int size = 256;
        Double[] red = new Double[size];
        Double[] green = new Double[size];
        Double[] blue = new Double[size];

        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j++)
            {
                if (!(i == 0 || j == 0 || i == width - 1 || j == height - 1)) {
                    Color color = image.getPixelReader().getColor(i - 1, j - 1);
                    int R = (int)color.getRed();
                    R = R * 255;
                    red[R] += 1;
                    int B = (int)color.getBlue();
                    B = B * 255;
                    blue[R] += 1;
                    int G = (int)color.getGreen();
                    G = G * 255;
                    green[G] += 1;
                }
            }
        }
        return image;
    }
}
