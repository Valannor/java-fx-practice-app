package main.templates_app;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Controller;
import operations.FileSystemController;
import main.AlertWindowClass;
import model.lang_loader.LanguageLoader;
import model.lang_loader.WindowType;

public class AddOrderController implements Controller
{
    @FXML
    private TextField address;
    @FXML
    private Label addressLabel;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;

    private Stage dialogStage;
    private boolean okClicked = false;

    @FXML
    private void initialize()
    {
        if (!LanguageLoader.getInstance().isEnglish())
        {
            addressLabel.textProperty().setValue(elementName("addressLabel"));
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
    private void handleCreateOrder()
    {
        if (address.getText() == null || address.getText().length() == 0)
        {
            AlertWindowClass.alertWindow(Alert.AlertType.ERROR, dialogStage,elementName("errorTitle"),
                    elementName("errorHeader"), elementName("errorMessage_1"));
        } else
        {
            FileSystemController.create(address.getText());
            okClicked = true;
            dialogStage.close();
        }
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
        return LanguageLoader.elementName(WindowType.ADD_ORDER, elementType);
    }
}
