package main.templates_adm;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import operations.UserSystemController;
import main.AlertWindowClass;

public class UserSignInController
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
    private void handleSignInUser()
    {
        if (isInputValid())
        {
            UserSystemController.logIn(loginField.getText(), passwordField.getText());
            okClicked = true;

            AlertWindowClass.alertWindow(Alert.AlertType.CONFIRMATION, dialogStage,
                    "Sign in", "Done", "Sign in is successful");

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
            message += "Not all data for authentication was provided";
        } else if (!UserSystemController.isUserDataCorrect(loginField.getText(), passwordField.getText()))
        {
            message += "You entered incorrect username or password";
        }

        if (message.length() == 0)
        {
            return true;
        } else
        {
            AlertWindowClass.alertWindow(Alert.AlertType.WARNING, dialogStage, "Sign in",
                    "Error", message);
            return false;
        }
    }
}
