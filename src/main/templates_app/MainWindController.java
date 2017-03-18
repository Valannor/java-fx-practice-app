package main.templates_app;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import model.Status;
import model.Trip;
import operations.FileSystemController;
import main.AlertWindowClass;
import main.MainApp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class MainWindController
{
    /**
     * Table elements
     */
    @FXML
    private TableView<Trip> tripTableView;
    @FXML
    private TableColumn<Trip, String> numberOfOrder;
    @FXML
    private TableColumn<Trip, String> addressOfOrder;
    @FXML
    private TableColumn<Trip, String> statusOfOrder;
    @FXML
    private TableColumn<Trip, String> dateOfOrder;

    private MainApp mainApp;

    public MainWindController()
    {
    }

    public void setMainApp(MainApp mainApp)
    {
        this.mainApp = mainApp;
        tripTableView.setItems(mainApp.getOrdersToShow());
    }

    @FXML
    private void initialize()
    {
        numberOfOrder.setCellValueFactory(cellData -> cellData.getValue().orderNumberProp());
        addressOfOrder.setCellValueFactory(cellData -> cellData.getValue().orderAddressProp());
        statusOfOrder.setCellValueFactory(cellData -> cellData.getValue().orderStatusProp());

        dateOfOrder.setCellValueFactory(
                cellData ->
                {
                    SimpleStringProperty property = new SimpleStringProperty();
                    DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy  hh:mm:ss", Locale.ENGLISH);
                    property.setValue(dateFormat.format(cellData.getValue().getOrderDate()));
                    return property;
                }
        );
    }


    /**
     * Operations with order
     */
    @FXML
    private void createOrder()
    {
        boolean okClicked = mainApp.showAddOrderDialog();
        if (okClicked)
        {
            showNewOrders();
        }
    }

    @FXML
    private void editOrder()
    {
        Trip trip = tripTableView.getSelectionModel().getSelectedItem();

        if (trip != null)
        {
            if (trip.getStatus().equals(Status.NEW_ORDER))
            {
                boolean okClicked = mainApp.showEditOrderDialog(trip);
                if (okClicked)
                {
                    mainApp.getOrdersToShow().clear();
                    mainApp.getOrdersToShow().addAll(FileSystemController.showByStatus(trip.getStatus()));
                }
            } else
            {
                AlertWindowClass.alertWindow(Alert.AlertType.WARNING, mainApp.getPrimaryStage(),
                        "Error", "Processing error", "Changing this order is no longer available");
            }
        } else
        {
            AlertWindowClass.alertWindow(Alert.AlertType.WARNING, mainApp.getPrimaryStage(),
                    "Error", "Processing error", "Order is not selected");
        }
    }

    @FXML
    private void removeOrder()
    {
        Trip trip = tripTableView.getSelectionModel().getSelectedItem();

        if (trip != null)
        {
            FileSystemController.remove(trip);
            showAllOrders();
        } else
        {
            AlertWindowClass.alertWindow(Alert.AlertType.WARNING, mainApp.getPrimaryStage(),
                    "Error", "Processing error", "Order is not selected");
        }
    }


    /**
     * Data to show in table
     */
    @FXML
    private void showAllOrders()
    {
        mainApp.getOrdersToShow().clear();
        mainApp.getOrdersToShow().addAll(FileSystemController.showAll());
    }

    @FXML
    private void showNewOrders()
    {
        mainApp.getOrdersToShow().clear();
        mainApp.getOrdersToShow().addAll(FileSystemController.showNewOrders());
    }

    @FXML
    private void showDoneOrders()
    {
        mainApp.getOrdersToShow().clear();
        mainApp.getOrdersToShow().addAll(FileSystemController.showDoneOrders());
    }

    @FXML
    private void showCancelledOrders()
    {
        mainApp.getOrdersToShow().clear();
        mainApp.getOrdersToShow().addAll(FileSystemController.showCancelledOrders());
    }

    @FXML
    private TextField askAddress;

    @FXML
    private void findByAddress()
    {
        mainApp.getOrdersToShow().clear();
        mainApp.getOrdersToShow().addAll(FileSystemController.findTripsByAddress(askAddress.getText()));
    }


    /**
     * Support operations
     */

    //Lock-unlock main window buttons
    @FXML
    private AnchorPane mainWindow;

    public void disableMainWindowButtons()
    {
        mainWindow.setDisable(true);
    }

    public void enableMainWindowButtons()
    {
        mainWindow.setDisable(false);
    }
}
