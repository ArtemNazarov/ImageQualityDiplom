package image_handle;

import com.google.gson.Gson;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

import java.io.*;
import java.lang.reflect.Type;

public class ImageHandle {

    private static ContrastChange contrastChange;


    public ImageHandle() {
        this.contrastChange = new ContrastChange();
    }

    public static ContrastChange getContrastChange() {
        return contrastChange;
    }

    public static void createHistogram(Image image){
        writeHistToFile(buildImageHistogram(image));
    }


    public static Histogram buildImageHistogram(Image image){
        int width = (int)image.getWidth();
        int height = (int)image.getHeight();
        int size = 256;
        Histogram hist = new Histogram(size);
        PixelReader pixelReader = image.getPixelReader();
        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j++)
            {
                if (!(i == 0 || j == 0 || i == width - 1 || j == height - 1)) {
                    Color color = pixelReader.getColor(i - 1, j - 1);
                    int R = (int)color.getRed();
                    R = R * 255;
                    hist.getRed()[R] += 1;
                    int B = (int)color.getBlue();
                    B = B * 255;
                    hist.getBlue()[B] += 1;
                    int G = (int)color.getGreen();
                    G = G * 255;
                    hist.getGreen()[G] += 1;
                }
            }
        }
        return hist;
    }

    public static void writeHistToFile(Histogram hist){
        File jsonFile = new File(ImageHandle.class.getClassLoader().getResource("json/data.json").toString());
        Gson gson = new Gson();
        String json = gson.toJson(hist);
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(jsonFile));
            bw.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
