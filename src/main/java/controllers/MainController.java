package controllers;

import image_handle.ImageHandle;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import layout.Browser;
import main.Main;

import java.io.File;

public class MainController {


    public Button openImageButton;
    public ImageView photoView;

    public void openImage(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(Main.getPrimaryStage());
        photoView.setImage(new Image(file.toURI().toString()));
    }

    public void closeApplication(ActionEvent actionEvent) {
        Main.getPrimaryStage().close();
    }

    public void showImageInfo(ActionEvent actionEvent) {
        Stage stage = new Stage();
        stage.setTitle("Histogram");
        Image workImage = photoView.getImage();
        ImageHandle handler = new ImageHandle();
        handler.createHistogram(workImage);
        Scene scene = new Scene(new Browser(),900,600, Color.web("#666970"));
        stage.setScene(scene);
        stage.show();
    }
}
