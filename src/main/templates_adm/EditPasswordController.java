package main.templates_adm;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import operations.UserSystemController;
import main.AlertWindowClass;

public class EditPasswordController
{
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField checkMistakeField;

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
    private void handleEditPassword()
    {
        if (isInputValid())
        {
            UserSystemController.editPassword(passwordField.getText());
            okClicked = true;

            AlertWindowClass.alertWindow(Alert.AlertType.CONFIRMATION, dialogStage,
                    "Change Password", "Done", "The password was successfully changed");

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
            message += "You've provided not full data";
        } else
        {
            if (!passwordField.getText().equals(checkMistakeField.getText()))
                message += "The input data does not match";

            if (passwordField.getText().contains(" ") || checkMistakeField.getText().contains(" "))
                message += "Password can not contain spaces";
        }

        if (message.length() == 0)
        {
            return true;
        } else
        {
            AlertWindowClass.alertWindow(Alert.AlertType.WARNING, dialogStage, "Change Password", "Error", message);
            return false;
        }
    }
}
