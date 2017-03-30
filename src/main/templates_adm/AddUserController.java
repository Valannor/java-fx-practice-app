package main.templates_adm;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.lang_loader.LanguageLoader;
import operations.UserSystemController;
import main.AlertWindowClass;
import model.lang_loader.WindowType;

public class AddUserController
{
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;

    private Stage dialogStage;
    private boolean okClicked = false;

    @FXML
    public void initialize()
    {
        if (!LanguageLoader.getInstance().isEnglish())
        {
            usernameLabel.textProperty().setValue(elementName("usernameLabel"));
            passwordLabel.textProperty().setValue(elementName("passwordLabel"));
            saveButton.textProperty().setValue(elementName("saveButton"));
            cancelButton.textProperty().setValue(elementName("cancelButton"));
        }
    }

    public void setDialogStage(Stage dialogStage)
    {
        this.dialogStage = dialogStage;
    }

    public boolean isOkClicked()
    {
        return okClicked;
    }

    @FXML
    private void handleAddUser()
    {
        if (isInputValid())
        {
            UserSystemController.registryUser(loginField.getText(), passwordField.getText());
            okClicked = true;

            AlertWindowClass.alertWindow(Alert.AlertType.CONFIRMATION, dialogStage,
                    elementName("confirmTitle"), elementName("confirmHeader"),
                    elementName("confirmMessagePattern"),
                            loginField.getText(), passwordField.getText());

            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel()
    {
        dialogStage.close();
    }

    private boolean isInputValid()
    {
        String message = "";

        if (loginField.getText() == null || loginField.getText().length() == 0
                || passwordField.getText() == null || passwordField.getText().length() == 0)
        {
            message += elementName("errorMessage_1");
        } else if (UserSystemController.isUserExist(loginField.getText()))
        {
            message += elementName("errorMessage_2");
        }
        else if (loginField.getText().contains(" ") || passwordField.getText().contains(" "))
        {
            message += elementName("errorMessage_3");
        }

        if (message.length() == 0)
        {
            return true;
        } else
        {
            AlertWindowClass.alertWindow(Alert.AlertType.WARNING, dialogStage, elementName("errorTitle"),
                    elementName("errorHeader"), message);
            return false;
        }
    }


    /**
     * Settings
     */
    private String elementName(String elementType)
    {
        return LanguageLoader.elementName(WindowType.ADD_USER, elementType);
    }
}
