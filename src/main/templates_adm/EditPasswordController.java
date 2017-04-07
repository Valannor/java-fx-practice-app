package main.templates_adm;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import main.Controller;
import model.lang_loader.LanguageLoader;
import operations.UserSystemController;
import main.AlertWindowClass;
import model.lang_loader.WindowType;

public class EditPasswordController implements Controller
{
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField checkMistakeField;
    @FXML
    private Label passwordLabel;
    @FXML
    private Label repeatPasswordLabel;
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
            passwordLabel.textProperty().setValue(elementName("passwordLabel"));
            repeatPasswordLabel.textProperty().setValue(elementName("repeatPasswordLabel"));
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
    private void handleEditPassword()
    {
        if (isInputValid())
        {
            UserSystemController.editPassword(passwordField.getText());
            okClicked = true;

            AlertWindowClass.alertWindow(Alert.AlertType.CONFIRMATION, dialogStage,
                    elementName("confirmTitle"), elementName("confirmHeader"),
                    elementName("confirmMessagePattern"));

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

        if (passwordField.getText() == null || passwordField.getText().length() == 0
                || checkMistakeField.getText() == null || checkMistakeField.getText().length() == 0)
        {
            message += elementName("errorMessage_1");
        } else
        {
            if (!passwordField.getText().equals(checkMistakeField.getText()))
                message += elementName("errorMessage_2");

            if (passwordField.getText().contains(" ") || checkMistakeField.getText().contains(" "))
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
        return LanguageLoader.elementName(WindowType.EDIT_PASSWORD, elementType);
    }
}
