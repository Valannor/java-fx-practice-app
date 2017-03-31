package main.templates_adm;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.lang_loader.LanguageLoader;
import operations.UserSystemController;
import main.AlertWindowClass;
import model.lang_loader.WindowType;

public class DeleteAccountController
{
    @FXML
    private TextField loginField;
    @FXML
    private Label usernameLabel;
    @FXML
    private Button deleteButton;
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
            deleteButton.textProperty().setValue(elementName("deleteButton"));
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
    private void handleRemoveUser()
    {
        if (isInputValid())
        {
            UserSystemController.deleteUser(loginField.getText());
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

        if (loginField.getText() == null || loginField.getText().length() == 0)
        {
            message += elementName("errorMessage_1");
        } else if (!UserSystemController.isUserExist(loginField.getText()))
        {
            message += elementName("errorMessage_2");
        } else if (loginField.getText().equals("admin"))
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
        return LanguageLoader.elementName(WindowType.DELETE_ACCOUNT, elementType);
    }
}
