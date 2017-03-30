package main.templates_app;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Status;
import model.Trip;
import operations.FileSystemController;
import main.AlertWindowClass;
import model.lang_loader.LanguageLoader;
import model.lang_loader.WindowType;

public class EditOrderController
{
    @FXML
    private TextField address;
    @FXML
    private ChoiceBox<String> statusChoiceBox = new ChoiceBox<>();
    @FXML
    private Label addressLabel;
    @FXML
    private Label statusLabel;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;

    private Stage dialogStage;
    private Trip trip;
    private boolean okClicked = false;

    @FXML
    private void initialize()
    {
        if (!LanguageLoader.getInstance().isEnglish())
        {
            addressLabel.textProperty().setValue(elementName("addressLabel"));
            statusLabel.textProperty().setValue(elementName("statusLabel"));
            saveButton.textProperty().setValue(elementName("saveButton"));
            cancelButton.textProperty().setValue(elementName("cancelButton"));
        }
    }

    public void setDialogStage(Stage dialogStage)
    {
        this.dialogStage = dialogStage;
    }

    public void setTrip(Trip trip)
    {
        // TODO: 30.03.2017 Choice box fillers localizations
        this.trip = trip;

        this.address.setText(trip.getAddress());
        this.statusChoiceBox.getItems().addAll("New", "Done", "Cancelled");
    }

    public boolean isOkClicked()
    {
        return okClicked;
    }

    @FXML
    private void handleEditOrder()
    {
        if (isInputValid())
        {
            Status status;
            switch (statusChoiceBox.getValue())
            {
                case "New":
                    status = Status.NEW_ORDER;
                    break;
                case "Done":
                    status = Status.DONE;
                    break;
                case "Cancelled":
                    status = Status.CANCELLED;
                    break;
                default:
                    status = Status.NEW_ORDER;
                    break;
            }

            FileSystemController.editTrip(trip, status, address.getText());
            okClicked = true;
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

        if (address.getText() == null || address.getText().length() == 0)
            message += "Invalid address\r\n";
        if (statusChoiceBox.getValue() == null)
            message += "Not specified order status";

        if (message.length() == 0)
            return true;
        else
        {
            AlertWindowClass.alertWindow(Alert.AlertType.ERROR, dialogStage, "Edit order",
                    "Error", message);
            return false;
        }
    }


    /**
     * Settings
     */
    private String elementName(String elementType)
    {
        return LanguageLoader.elementName(WindowType.EDIT_ORDER, elementType);
    }
}
