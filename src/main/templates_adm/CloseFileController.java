package main.templates_adm;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import main.Controller;
import model.lang_loader.LanguageLoader;
import operations.UserSystemController;
import model.lang_loader.WindowType;

public class CloseFileController implements Controller
{
    @FXML
    private Label question;
    @FXML
    private Button yesButton;
    @FXML
    private Button noButton;

    private Stage dialogStage;
    private boolean okClicked = false;

    @FXML
    public void initialize()
    {
        if (!LanguageLoader.getInstance().isEnglish())
        {
            question.textProperty().setValue(elementName("question"));
            yesButton.textProperty().setValue(elementName("yesButton"));
            noButton.textProperty().setValue(elementName("noButton"));
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
    private void handleClosingWind()
    {
        UserSystemController.logOut();
        okClicked = true;

        dialogStage.close();
    }

    @FXML
    private void handleCancel()
    {
        dialogStage.close();
    }


    /**
     * Settings
     */
    private String elementName(String elementType)
    {
        return LanguageLoader.elementName(WindowType.CLOSE_FILE, elementType);
    }
}
