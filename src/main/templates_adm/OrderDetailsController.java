package main.templates_adm;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import main.Controller;
import model.Trip;
import model.lang_loader.LanguageLoader;
import model.lang_loader.WindowType;

import java.text.SimpleDateFormat;

public class OrderDetailsController implements Controller
{
    @FXML
    private Label addressLabel;
    @FXML
    private Label addressDetails;
    @FXML
    private Label statusLabel;
    @FXML
    private Label statusDetails;
    @FXML
    private Label dateLabel;
    @FXML
    private Label dateDetails;


    private Trip trip;

    @FXML
    private void initialize()
    {
        addressLabel.textProperty().setValue(elementName("addressOfOrder"));
        statusLabel.textProperty().setValue(elementName("statusOfOrder"));
        dateLabel.textProperty().setValue(elementName("dateOfOrder"));

        if (trip != null)
        {
            addressDetails.setText(trip.getAddress());
            statusDetails.setText(trip.orderStatusProp().getValue());
            dateDetails.setText(new SimpleDateFormat("dd.MM.yyyy\tEEE\thh:mm:ss").format(trip.getOrderDate()));
        }
        else
        {
            addressDetails.setText("<-No data->");
            statusDetails.setText("<-No data->");
            dateDetails.setText("<-No data->");
        }
    }

    public void setTrip(Trip trip)
    {
        this.trip = trip;
        initialize();
    }


    /**
     * Settings
     */
    private String elementName(String elementType)
    {
        return LanguageLoader.elementName(WindowType.ORDER_DETAILS, elementType);
    }
}
