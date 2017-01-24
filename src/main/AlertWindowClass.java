package main;

import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class AlertWindowClass
{
    public static void alertWindow(Alert.AlertType type, Stage dialogStage, String title, String header, String message)
    {
        Alert alert = new Alert(type);
        alert.initOwner(dialogStage);

        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(AlertWindowClass.class.getResource("styles/AlertBlueTheme.css").toExternalForm());
        dialogPane.getStyleClass().add("AlertBlueTheme");

        Stage stage = (Stage) dialogPane.getScene().getWindow();
        stage.getIcons().add(new Image("main/resources/images/puzzle_img.png"));

        alert.showAndWait();
    }
}
