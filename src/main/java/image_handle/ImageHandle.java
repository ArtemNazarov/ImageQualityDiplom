package image_handle;

import com.google.gson.Gson;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ImageHandle {

    private ContrastChange contrastChange;


    public ImageHandle() {
        this.contrastChange = new ContrastChange();
    }

    public ContrastChange getContrastChange() {
        return contrastChange;
    }

    public void createHistogram(Image image){
        writeHistToFile(buildImageHistogram(image));
    }

    public  Histogram buildImageHistogram(Image image) {
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
                    int R = (int)(color.getRed() * 255);
                    hist.getRed()[R] += 1;
                    int B = (int)(color.getBlue() * 255);
                    hist.getBlue()[B] += 1;
                    int G = (int)(color.getGreen() * 255);
                    hist.getGreen()[G] += 1;
                }
            }
        }
        return hist;
    }

    public void writeHistToFile(Histogram hist) {
//        File jsonFile = new File(getClass().getClassLoader().getResource("json/data.json").toString());
        Gson gson = new Gson();
        String json = gson.toJson(hist);
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(getClass().getClassLoader().getResource("json/data.json").getPath()));
            bw.write(json);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
