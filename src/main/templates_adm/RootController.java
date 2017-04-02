package main.templates_adm;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import model.Trip;
import model.lang_loader.Language;
import operations.FileSystemController;
import model.lang_loader.LanguageLoader;
import operations.UserSystemController;
import main.AlertWindowClass;
import main.MainApp;
import model.lang_loader.WindowType;

import java.io.File;
import java.util.Locale;

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

    @FXML
    private void initialize()
    {
        if (!LanguageLoader.getInstance().isEnglish())
            applyInterface();
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

            refreshTabView();
        }
    }

    @FXML
    private void handleCreate()
    {
        if (FileSystemController.isChanged())
            handleCloseFile();

        FileSystemController.createDataBase();

        AlertWindowClass.alertWindow(Alert.AlertType.CONFIRMATION, mainApp.getPrimaryStage(),
                WindowType.CREATE_BASE);

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
     * "Profile" menu button operations
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
     * "Details" menu button operations
     */
    @FXML
    private void handleOrderDetails()
    {
        Trip trip = mainApp.getMainWindController().getSelectedTrip();

        mainApp.showDetailsWindow(trip);
    }


    /**
     * "Settings" menu button operations
     */
    @FXML
    private Menu file;
    @FXML
    private MenuItem openFileButton, createFileButton, saveButton,
            saveAsButton, closeFileButton;

    @FXML
    private Menu profile;
    @FXML
    private MenuItem logInButton, logOutButton, addUserButton,
            removeUserButton, editPasswordButton, closeApp;

    @FXML
    private Menu details;
    @FXML
    private MenuItem orderDetails, statistics;

    @FXML
    private Menu settings;
    @FXML
    private Menu dateTimeView;
    @FXML
    private MenuItem defaultDateMode, onlyDateMode, onlyTimeMode;
    @FXML
    private Menu language;
    @FXML
    private MenuItem engInterface, rusInterface;

    @FXML
    private Menu about;
    @FXML
    private MenuItem info, instructions;


    @FXML
    private void handleDefaultDateMode()
    {
        FileSystemController.setDatePattern("dd.MM.yyyy\thh:mm:ss");

        defaultDateMode.setDisable(true);
        onlyDateMode.setDisable(false);
        onlyTimeMode.setDisable(false);

        refreshTabView();
    }

    @FXML
    private void handleOnlyDateMode()
    {
        FileSystemController.setDatePattern("dd.MM.yyyy\tEEE");

        defaultDateMode.setDisable(false);
        onlyDateMode.setDisable(true);
        onlyTimeMode.setDisable(false);

        refreshTabView();
    }

    @FXML
    private void handleOnlyTimeMode()
    {
        FileSystemController.setDatePattern("hh:mm:ss");

        defaultDateMode.setDisable(false);
        onlyDateMode.setDisable(false);
        onlyTimeMode.setDisable(true);

        refreshTabView();
    }

    @FXML
    private void handleEngInterface()
    {
        // TODO: 19.03.2017
        FileSystemController.setLocale(Locale.ENGLISH);

        LanguageLoader.setConfig(Language.ENGLISH);
        applyInterface();

        mainApp.getMainWindController().applyInterface();

        engInterface.setVisible(false);
        rusInterface.setVisible(true);
    }

    @FXML
    private void handleRusInterface()
    {
        // TODO: 19.03.2017
        FileSystemController.setLocale(new Locale("ru", "RU"));

        LanguageLoader.setConfig(Language.RUSSIAN);
        applyInterface();

        mainApp.getMainWindController().applyInterface();

        engInterface.setVisible(true);
        rusInterface.setVisible(false);
    }

    private void applyInterface()
    {
        file.textProperty().setValue(elementName("fileMenu"));
        openFileButton.textProperty().setValue(elementName("openFile"));
        createFileButton.textProperty().setValue(elementName("createFile"));
        saveButton.textProperty().setValue(elementName("saveButton"));
        saveAsButton.textProperty().setValue(elementName("saveAsButton"));
        closeFileButton.textProperty().setValue(elementName("closeFile"));

        profile.textProperty().setValue(elementName("profileMenu"));
        logInButton.textProperty().setValue(elementName("logIn"));
        logOutButton.textProperty().setValue(elementName("logOut"));
        addUserButton.textProperty().setValue(elementName("addUser"));
        removeUserButton.textProperty().setValue(elementName("removeUser"));
        editPasswordButton.textProperty().setValue(elementName("editPassword"));
        closeApp.textProperty().setValue(elementName("closeApp"));

        details.setText(elementName("detailsMenu"));
        orderDetails.setText(elementName("orderDetails"));
        statistics.setText(elementName("statistics"));

        settings.textProperty().setValue(elementName("settingsMenu"));
        dateTimeView.textProperty().setValue(elementName("dateTimeMenu"));
        defaultDateMode.textProperty().setValue(elementName("defaultTab"));
        onlyDateMode.textProperty().setValue(elementName("onlyDate"));
        onlyTimeMode.textProperty().setValue(elementName("onlyTime"));
        language.textProperty().setValue(elementName("languageMenu"));

        about.textProperty().setValue(elementName("aboutMenu"));
        info.textProperty().setValue(elementName("infoButton"));
        instructions.textProperty().setValue(elementName("instructButton"));
    }

    private String elementName(String elementType)
    {
        return LanguageLoader.elementName(WindowType.ROOT, elementType);
    }


    /**
     * "About" menu button operations
     */
    @FXML
    private void handleAboutDeveloper()
    {
        AlertWindowClass.alertWindow(Alert.AlertType.INFORMATION, null, WindowType.INFO);
    }

    @FXML
    private void handleInstruction()
    {
        AlertWindowClass.alertWindow(Alert.AlertType.INFORMATION, null, WindowType.INSTRUCTION);
    }


    /**
     * Support operations
     */
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

    private void refreshTabView()
    {
        try
        {
            mainApp.setOrdersToShow(FileSystemController.showAll());
        } catch (RuntimeException e)
        {
            //TODO - Empty list
        }
    }
}
