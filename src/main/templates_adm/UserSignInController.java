package main.templates_adm;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.lang_loader.LanguageLoader;
import operations.UserSystemController;
import main.AlertWindowClass;
import model.lang_loader.WindowType;

public class UserSignInController
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
    private Button signInButton;
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
            signInButton.textProperty().setValue(elementName("signInButton"));
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
    private void handleSignInUser()
    {
        if (isInputValid())
        {
            UserSystemController.logIn(loginField.getText(), passwordField.getText());
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

        if (loginField.getText() == null || loginField.getText().length() == 0
                || passwordField.getText() == null || passwordField.getText().length() == 0)
        {
            message += elementName("errorMessage_1");
        } else if (!UserSystemController.isUserDataCorrect(loginField.getText(), passwordField.getText()))
        {
            message += elementName("errorMessage_2");
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
        return LanguageLoader.elementName(WindowType.USER_SIGN_IN, elementType);
    }
}
