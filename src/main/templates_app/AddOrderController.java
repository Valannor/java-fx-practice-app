package main.templates_app;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import operations.FileSystemController;
import main.AlertWindowClass;

public class AddOrderController
{
    @FXML
    private TextField address;

    private Stage dialogStage;
    private boolean okClicked = false;

    @FXML
    private void initialize()
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
    private void handleCreateOrder()
    {
        if (address.getText() == null || address.getText().length() == 0)
        {
            AlertWindowClass.alertWindow(Alert.AlertType.ERROR, dialogStage,
                    "Create order", "Error", "Invalid data was provided");
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
}
