package main;

import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.lang_loader.LanguageLoader;
import model.lang_loader.WindowType;

public class AlertWindowClass
{
    //For DEFAULT messages
    public static void alertWindow(Alert.AlertType type, Stage dialogStage, WindowType windowType)
    {
        Alert alert = new Alert(type);
        alert.initOwner(dialogStage);

        alert.setTitle(LanguageLoader.elementName(windowType, "title"));
        alert.setHeaderText(LanguageLoader.elementName(windowType, "header"));
        alert.setContentText(LanguageLoader.elementName(windowType, "message"));

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(AlertWindowClass.class.getResource("styles/AlertBlueTheme.css").toExternalForm());
        dialogPane.getStyleClass().add("AlertBlueTheme");

        Stage stage = (Stage) dialogPane.getScene().getWindow();
        stage.getIcons().add(new Image("main/resources/images/puzzle_img.png"));

        alert.showAndWait();
    }

    //For ERROR messages
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

    //For CONFIRM messages
    public static void alertWindow(Alert.AlertType type, Stage dialogStage, String title, String header, String messagePattern, String... parameters)
    {
        Alert alert = new Alert(type);
        alert.initOwner(dialogStage);

        alert.setTitle(title);
        alert.setHeaderText(header);
        // TODO: 31.03.2017 Interesting. Is it ok to use this kind of construction with varargs?
        alert.setContentText(String.format(messagePattern, parameters));

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(AlertWindowClass.class.getResource("styles/AlertBlueTheme.css").toExternalForm());
        dialogPane.getStyleClass().add("AlertBlueTheme");

        Stage stage = (Stage) dialogPane.getScene().getWindow();
        stage.getIcons().add(new Image("main/resources/images/puzzle_img.png"));

        alert.showAndWait();
    }
}
