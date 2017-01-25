package main.templates_app;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import operations.FileSystemController;
import main.AlertWindowClass;
import main.MainApp;

import java.io.File;

public class RootController
{
    public RootController()
    {

    }

    private MainApp mainApp;

    public void setMainApp(MainApp mainApp)
    {
        this.mainApp = mainApp;
    }


    /**
     * "File" menu button elements
     */
    @FXML
    private void handleOpen()
    {
        if (FileSystemController.isChanged())
            handleCloseFile();

        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extensionFilter =
                new FileChooser.ExtensionFilter("TAPX files (*.tapx)", "*.tapx");
        fileChooser.getExtensionFilters().add(extensionFilter);

        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

        if (file != null)
        {
            FileSystemController.openDataBase(file);

            saveButton.setDisable(false);
            saveAsButton.setDisable(false);
            closeFileButton.setDisable(false);
            mainApp.getMainWindController().enableMainWindowButtons();

            mainApp.setOrdersToShow(FileSystemController.showAll());
        }
    }

    @FXML
    private void handleCreate()
    {
        FileSystemController.createDataBase();

        AlertWindowClass.alertWindow(Alert.AlertType.CONFIRMATION, mainApp.getPrimaryStage(),
                "New file", "Done", "New database was created successfully");

        saveButton.setDisable(false);
        saveAsButton.setDisable(false);
        closeFileButton.setDisable(false);
        mainApp.getMainWindController().enableMainWindowButtons();
    }

    @FXML
    private void handleSave()
    {
        File file = FileSystemController.getDataBaseFile();

        if (file != null)
            FileSystemController.save(file);
        else handleSaveAs();
    }

    @FXML
    private void handleSaveAs()
    {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extensionFilter =
                new FileChooser.ExtensionFilter("TAPX files (*.tapx)", "*.tapx");
        fileChooser.getExtensionFilters().add(extensionFilter);

        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

        if (file != null)
        {
            if (!file.getPath().endsWith(".tapx"))
                file = new File(file.getPath() + ".tapx");

            FileSystemController.save(file);
        }
    }

    @FXML
    private void handleCloseFile()
    {
        if (FileSystemController.isChanged())
        {
            if (FileSystemController.isChanged())
            {
                handleSave();
            }
        }

        saveButton.setDisable(true);
        saveAsButton.setDisable(true);
        closeFileButton.setDisable(true);
        mainApp.getMainWindController().disableMainWindowButtons();

        FileSystemController.closeDataBase();

        mainApp.getOrdersToShow().clear();
    }


    /**
     * "About" menu button operations
     */
    @FXML
    private void handleAboutDeveloper()
    {
        AlertWindowClass.alertWindow(Alert.AlertType.INFORMATION, null,
                "Info", "Developer",
                "Author: Rustam Gurd\r\nWebsite: https://github.com/Valannor");
    }

    @FXML
    private void handleInstruction()
    {
        AlertWindowClass.alertWindow(Alert.AlertType.INFORMATION, null,
                "Instructions", "Getting started",
                "1) Press menu button \"File\"\r\n" +
                        "2) Create or open existing database\r\n" +
                        "3) Using workspace buttons, create\\edit orders\r\n" +
                        "4) Close database after saving changes");
    }


    /**
     * Support
     */
    @FXML
    private MenuItem saveButton;
    @FXML
    private MenuItem saveAsButton;
    @FXML
    private MenuItem closeFileButton;
}
