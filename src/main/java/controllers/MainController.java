package controllers;

import dialogs.DialogManager;
import image_handle.ContrastChange;
import image_handle.Histogram;
import image_handle.ImageHandle;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.stage.Stage;
import layout.Browser;
import main.Main;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    public ChoiceBox contrastAlgoBox;
    private BarChart chart;
    private CategoryAxis xAxis;
    private NumberAxis yAxis;
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
        Histogram hist = ImageHandle.buildImageHistogram(workImage);
//        handler.createHistogram(workImage);
        Scene scene = new Scene(createContent(hist));
        scene.getStylesheets().add(MainController.class.getClassLoader().getResource("css/bar.css").toExternalForm());
//        Scene scene = new Scene(new Browser(),900,600, Color.web("#666970"));
        stage.setScene(scene);
        stage.show();
    }

    public Parent createContent(Histogram hist) {
//        String[] years = {"2007", "2008", "2009"};
        String[] indexes = new String[hist.getSize()];
        for (int i = 0; i < hist.getSize(); i++) {
            indexes[i] = Integer.toString(i);
        }
        xAxis = new CategoryAxis();
        xAxis.setCategories(FXCollections.<String>observableArrayList(indexes));
        yAxis = new NumberAxis();

        ObservableList<BarChart.Data> redData = FXCollections.observableArrayList();
        ObservableList<BarChart.Data> blueData = FXCollections.observableArrayList();
        ObservableList<BarChart.Data> greenData = FXCollections.observableArrayList();
        for (int i = 0; i < hist.getSize(); i++) {
            redData.add(new BarChart.Data(Integer.toString(i), hist.getRed()[i]));
            blueData.add(new BarChart.Data(Integer.toString(i), hist.getBlue()[i]));
            greenData.add(new BarChart.Data(Integer.toString(i), hist.getGreen()[i]));
        }

        BarChart.Series redSeries = new BarChart.Series("Red", redData);
        BarChart.Series greenSeries = new BarChart.Series("Green", greenData);
        BarChart.Series blueSeries = new BarChart.Series("Blue", blueData);

        ObservableList<BarChart.Series> barChartData = FXCollections.observableArrayList(redSeries, blueSeries, greenSeries);
//        for (BarChart.Series barChartDatum : barChartData) {
//            Node fill = barChartDatum.getNode().lookup(".chart-series-bar-fill");
//            Node line = barChartDatum.getNode().lookup(".chart-series-bar-line");
//            Color color = Color.RED; // or any other color
//
//            String rgb = String.format("%d, %d, %d",
//                    (int) (color.getRed() * 255),
//                    (int) (color.getGreen() * 255),
//                    (int) (color.getBlue() * 255));
//            fill.setStyle("-fx-fill: rgba(" + rgb + ", 0.15);");
//            line.setStyle("-fx-stroke: rgba(" + rgb + ", 1.0);");
//        }

//        for (Node n : chart.lookupAll(".default-color0.chart-bar")) {
//            n.setStyle("-fx-bar-fill: red;");
//        }
//        //second bar color
//        for(Node n:chart.lookupAll(".default-color1.chart-bar")) {
//            n.setStyle("-fx-bar-fill: green;");
//        }

        chart = new BarChart(xAxis, yAxis, barChartData, 25.0d);
        return chart;
    }


    public void aboutClick(ActionEvent actionEvent) {
        Stage stage = new Stage();
        stage.setTitle("About");
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/aboutframe.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            DialogManager.showErrorDialog(e.getMessage());
        }



    }

    public void contrastImage(ActionEvent actionEvent) {

        Image workImage = photoView.getImage();
        if (workImage != null){
            if (contrastAlgoBox.getSelectionModel().getSelectedIndex() == 0) {
                photoView.setImage(ContrastChange.adjustContrast(workImage, 0 ,255));
            } else {
                photoView.setImage(ContrastChange.equalizeHistogram(workImage));


            }
        } else {
            DialogManager.showErrorDialog("Добавьте изображение!");
        }

    }

    public void undoContrast(ActionEvent actionEvent) {
        if (ImageHandle.getSourceImage() != null) {
            photoView.setImage(ImageHandle.getSourceImage());

        }
    }

    public void initialize(URL location, ResourceBundle resources) {
        contrastAlgoBox.getItems().addAll("Линейное контрастирование", "Эквализация гистограммы");
        contrastAlgoBox.getSelectionModel().selectFirst();
    }
}
