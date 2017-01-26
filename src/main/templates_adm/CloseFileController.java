package main.templates_adm;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import operations.UserSystemController;

public class CloseFileController
{
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
}
