package dialogs;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class DialogManager {
    public static void showErrorDialog(String text){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("\u041E\u0448\u0438\u0431\u043A\u0430!");
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(DialogManager.class.getClassLoader().getResource("icons/warning.png").toExternalForm()));
        alert.setContentText(text);
        alert.setHeaderText("");
        alert.showAndWait();
    }

    public static void showInformationDialog(String text){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Информация");
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        alert.setContentText(text);
        alert.setHeaderText("");
        alert.showAndWait();
    }
}
