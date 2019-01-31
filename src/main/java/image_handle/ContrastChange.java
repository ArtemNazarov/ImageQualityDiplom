package image_handle;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import static image_handle.ImageHandle.buildImageHistogram;
import static image_handle.ImageHandle.copyImage;

public class ContrastChange {

    public static Image equalizeHistogram(Image image) {
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        int size = 256;
        WritableImage newImage = copyImage(image);
        PixelReader imageReader = image.getPixelReader();
        PixelWriter newImageWriter = newImage.getPixelWriter();
        Histogram hist = buildImageHistogram(image);
        for (int i = 0; i < size; i++)
        {
            hist.getRed()[i] /= (width * height);
            hist.getGreen()[i] /= (width * height);
            hist.getBlue()[i] /= (width * height);
        }
        for (int i = 1; i < size; i++)
        {
            hist.getRed()[i] = hist.getRed()[i - 1] + hist.getRed()[i];
            hist.getGreen()[i] = hist.getGreen()[i - 1] + hist.getGreen()[i];
            hist.getBlue()[i] = hist.getBlue()[i - 1] + hist.getBlue()[i];
        }

        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j++)
            {
                if (!(i == 0 || j == 0 || i == width - 1 || j == height - 1)) {
                    Color medium = imageReader.getColor(i - 1, j - 1);
                    int indexR = (int) (medium.getRed() * 255f);
                    int indexG = (int) (medium.getGreen() * 255f);
                    int indexB = (int) (medium.getBlue() * 255f);
                    int red = (int)(hist.getRed()[indexR] * size);
                    int green = (int)(hist.getGreen()[indexG] * size);
                    int blue = (int)(hist.getBlue()[indexB] * size);
                    newImageWriter.setColor(i, j, Color.rgb(red, green, blue));
                }
                else {
                    Color medium = imageReader.getColor(i, j);
                    int indexR = (int) (medium.getRed() * 255f);
                    int indexG = (int) (medium.getGreen() * 255f);
                    int indexB = (int) (medium.getBlue() * 255f);
                    newImageWriter.setColor(i, j, Color.rgb(indexR, indexG, indexB));
                }
            }
        }
        return image;
    }

    public static Image adjustContrast(Image image, int minValue, int maxValue) {
        WritableImage newImage = copyImage(image);
        PixelReader imageReader = image.getPixelReader();
        PixelWriter newImageWriter = newImage.getPixelWriter();
        int height = (int)image.getHeight() ;
        int width = (int)image.getWidth();
        double minBrightness = 0;
        double maxBrightness = 1000;
        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j++)
            {
                if (!(i == 0 || j == 0 || i == width - 1 || j == height - 1)) {
                    double brightness = imageReader.getColor(i,j).getBrightness();
                    if (maxBrightness < brightness)
                        maxBrightness = brightness;
                    if (minBrightness > brightness)
                        minBrightness = brightness;
                }
            }
        }
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++) {
                if (i == 0 || j == 0 || i == width - 1 || j == height - 1) {
                    newImageWriter.setColor(i,j, imageReader.getColor(i, j));
                }
                else {
                    int R = 0;
                    int G = 0;
                    int B = 0;
                    Color medium = imageReader.getColor(i - 1,j - 1);


                    double Red = medium.getRed();
                    double Green = medium.getGreen() ;
                    double Blue = medium.getBlue() ;
                    double maxVal = maxValue / 255.0f;
                    double minVal = minValue / 255.0f;
                    double a = (maxVal - minVal) / (maxBrightness - minBrightness);
                    double b = (minVal * maxBrightness - maxVal * minBrightness) / (maxBrightness - minBrightness);
                    Red = (a * Red + b) * 255.0f;
                    Green = (a * Green + b) * 255.0f;
                    Blue = (a * Blue + b) * 255.0f;

                    int iR = (int) Red;
                    iR = iR > 255 ? 255 : iR;
                    iR = iR < 0 ? 0 : iR;
                    int iG = (int) Green;
                    iG = iG > 255 ? 255 : iG;
                    iG = iG < 0 ? 0 : iG;
                    int iB = (int) Blue;
                    iB = iB > 255 ? 255 : iB;
                    iB = iB < 0 ? 0 : iB;
                    newImageWriter.setColor(i,j, Color.rgb(iR,iG,iB));
//                    Bar->Value++;
                }
            }
        return newImage;

    }


}
