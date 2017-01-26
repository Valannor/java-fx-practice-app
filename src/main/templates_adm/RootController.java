package main.templates_adm;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import operations.FileSystemController;
import operations.UserSystemController;
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
            boolean isOkClicked = mainApp.showSignOutDialog();
            if (isOkClicked)
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
     * "Administration" menu button operations
     */
    @FXML
    private void logIn()
    {
        boolean isOkClicked = mainApp.showSignInDialog();
        if (isOkClicked)
        {
            enableMenuButtons();

            if (UserSystemController.isUserAdmin())
            {
                addUserButton.setDisable(false);
                removeUserButton.setDisable(false);
            } else
            {
                addUserButton.setDisable(true);
                removeUserButton.setDisable(true);
            }
        }
    }

    @FXML
    private void logOut()
    {
        UserSystemController.logOut();
        disableMenuButtons();
        handleCloseFile();
    }

    @FXML
    private void registryUser()
    {
        mainApp.showAddUser();
    }

    @FXML
    private void editPassword()
    {
        mainApp.showEditPassword();
    }

    @FXML
    private void removeUser()
    {
        mainApp.showRemoveUser();
    }

    @FXML
    private void exitApplication()
    {
        handleCloseFile();
        System.exit(0);
    }


    /**
     * "About" menu button operations
     */
    @FXML
    private void handleAboutDeveloper()
    {
        AlertWindowClass.alertWindow(Alert.AlertType.INFORMATION, null,
                "Info", "Developer",
                "Author: Rustam Kurdov\r\nWebsite: https://vk.com/rustam_gurd");
    }

    @FXML
    private void handleInstruction()
    {
        AlertWindowClass.alertWindow(Alert.AlertType.INFORMATION, null,
                "Instructions", "Getting started",
                "1) Press menu button \"Profile\"\r\n" +
                        "2) Sign in, using your username & password\r\n" +
                        "3) Press menu button \"File\"\r\n" +
                        "4) Create or open existing database\r\n" +
                        "5) Using workspace buttons, create\\edit orders\r\n" +
                        "6) Close database after saving changes\r\n" +
                        "7) Sign out\r\n\r\n" +
                        "P.S.:\r\n" +
                        "a) Only \"admin\" account can add\\remove users\r\n" +
                        "b) Any user can change his own password only\r\n" +
                        "\r\nDefault password for admin account - \"admin\"");
    }


    /**
     * Support operations
     */
    @FXML
    private MenuItem openFileButton;
    @FXML
    private MenuItem createFileButton;
    @FXML
    private MenuItem saveButton;
    @FXML
    private MenuItem saveAsButton;
    @FXML
    private MenuItem closeFileButton;

    @FXML
    private MenuItem logInButton;
    @FXML
    private MenuItem logOutButton;
    @FXML
    private MenuItem addUserButton;
    @FXML
    private MenuItem removeUserButton;
    @FXML
    private MenuItem editPasswordButton;

    private void disableMenuButtons()
    {
        openFileButton.setDisable(true);
        createFileButton.setDisable(true);
        saveButton.setDisable(true);
        saveAsButton.setDisable(true);
        closeFileButton.setDisable(true);

        logInButton.setDisable(false);
        logOutButton.setDisable(true);
        editPasswordButton.setDisable(true);
        addUserButton.setDisable(true);
        removeUserButton.setDisable(true);
    }

    private void enableMenuButtons()
    {
        openFileButton.setDisable(false);
        createFileButton.setDisable(false);

        logInButton.setDisable(true);
        logOutButton.setDisable(false);
        editPasswordButton.setDisable(false);
        addUserButton.setDisable(false);
        removeUserButton.setDisable(false);
    }
}
