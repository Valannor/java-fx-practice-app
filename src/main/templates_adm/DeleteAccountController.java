package main.templates_adm;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import operations.UserSystemController;
import main.AlertWindowClass;

public class DeleteAccountController
{
    @FXML
    private TextField loginField;

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
    private void handleRemoveUser()
    {
        if (isInputValid())
        {
            UserSystemController.deleteUser(loginField.getText());
            okClicked = true;

            AlertWindowClass.alertWindow(Alert.AlertType.CONFIRMATION, dialogStage,
                    "Delete account", "Done", "Account was successfully deleted");

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

        if (loginField.getText() == null || loginField.getText().length() == 0)
        {
            message += "You've provided invalid data";
        } else if (!UserSystemController.isUserExist(loginField.getText()))
        {
            message += "This user doesn't exist";
        } else if (loginField.getText().equals("admin"))
        {
            message += "You can not delete this account";
        }

        if (message.length() == 0)
        {
            return true;
        } else
        {
            AlertWindowClass.alertWindow(Alert.AlertType.WARNING, dialogStage, "Delete account", "Error", message);
            return false;
        }
    }
}
