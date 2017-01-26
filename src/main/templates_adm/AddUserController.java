package main.templates_adm;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import operations.UserSystemController;
import main.AlertWindowClass;

public class AddUserController
{
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;

    private Stage dialogStage;
    private boolean okClicked = false;

    @FXML
    public void initialize()
    {

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
                    "User registration", "Done",
                    String.format("New user was created successfully\r\n\r\nUsername: %s\r\nPassword: %s",
                            loginField.getText(), passwordField.getText()));

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
            message += "You've provided not full data";
        } else if (UserSystemController.isUserExist(loginField.getText()))
        {
            message += "This user already exist";
        }
        else if (loginField.getText().contains(" ") || passwordField.getText().contains(" "))
        {
            message += "Username and password can not contain spaces";
        }

        if (message.length() == 0)
        {
            return true;
        } else
        {
            AlertWindowClass.alertWindow(Alert.AlertType.WARNING, dialogStage, "User registration",
                    "Error", message);
            return false;
        }
    }
}
