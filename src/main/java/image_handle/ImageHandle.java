package image_handle;

import com.google.gson.Gson;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ImageHandle {

    private ContrastChange contrastChange;
    private static Image sourceImage;

    public ImageHandle() {
        this.contrastChange = new ContrastChange();
    }

    public ContrastChange getContrastChange() {
        return contrastChange;
    }

    public void createHistogram(Image image){
//        writeHistToFile(buildImageHistogram(image));
    }

    public static Histogram buildImageHistogram(Image image) {
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


    public static Image getSourceImage(){
        return sourceImage;
    }

//    public void writeHistToFile(Histogram hist) {
////        File jsonFile = new File(getClass().getClassLoader().getResource("json/data.json").toString());
//        Gson gson = new Gson();
//        String blueJson = gson.toJson(hist.getBlue());
//        String redJson = gson.toJson(hist.getRed());
//        String greenJson = gson.toJson(hist.getGreen());
//
//        blueJson = "var blueData = " + blueJson + ";";
//        redJson = "var redData = " + redJson + ";";
//        greenJson = "var greenData = " + greenJson + ";";
//
//        try {
//            BufferedWriter bw = new BufferedWriter(new FileWriter(getClass().getClassLoader().getResource("js/data.js").getPath()));
//            bw.write(blueJson);
//            bw.write(redJson);
//            bw.write(greenJson);
//            bw.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }

    /**
     * copy the given image to a writeable image
     * @param image
     * @return a writeable image
     */
    public static WritableImage copyImage(Image image) {
        int height=(int)image.getHeight();
        int width=(int)image.getWidth();
        PixelReader pixelReader=image.getPixelReader();
        WritableImage writableImage = new WritableImage(width,height);
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        for (int y = 0; y < height; y++){
            for (int x = 0; x < width; x++){
                Color color = pixelReader.getColor(x, y);
                pixelWriter.setColor(x, y, color);
            }
        }
        return writableImage;
    }


}
