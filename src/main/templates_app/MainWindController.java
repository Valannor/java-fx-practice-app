package main.templates_app;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import model.Status;
import model.Trip;
import model.lang_loader.LanguageLoader;
import model.lang_loader.WindowType;
import operations.FileSystemController;
import main.AlertWindowClass;
import main.MainApp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

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

    @FXML
    private Button allOrders;
    @FXML
    private Button newOrders;
    @FXML
    private Button doneOrders;
    @FXML
    private Button cancelledOrders;

    @FXML
    private Button createOrders;
    @FXML
    private Button editOrders;
    @FXML
    private Button removeOrders;

    @FXML
    private Label searchLabel;


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
                    DateFormat dateFormat = new SimpleDateFormat(FileSystemController.getDatePattern(), FileSystemController.getLocale());
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
        Trip trip = getSelectedTrip();

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
                        errorTitle(), errorHeader(), elementName("editOrder_notAvailable"));
            }
        } else
        {
            AlertWindowClass.alertWindow(Alert.AlertType.WARNING, mainApp.getPrimaryStage(),
                    errorTitle(), errorHeader(), elementName("editOrder_notSelected"));
        }
    }

    @FXML
    private void removeOrder()
    {
        Trip trip = getSelectedTrip();

        if (trip != null)
        {
            FileSystemController.remove(trip);
            showAllOrders();
        } else
        {
            AlertWindowClass.alertWindow(Alert.AlertType.WARNING, mainApp.getPrimaryStage(),
                    errorTitle(), errorHeader(), elementName("removeOrder_notSelected"));
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


    /**
     * Settings
     */
    public Trip getSelectedTrip()
    {
        return tripTableView.getSelectionModel().getSelectedItem();
    }

    private String elementName(String elementType)
    {
        return LanguageLoader.elementName(WindowType.MAIN, elementType);
    }

    private String errorTitle()
    {
        return elementName("errorTitle");
    }

    private String errorHeader()
    {
        return elementName("errorHeader");
    }

    public void applyInterface()
    {
        addressOfOrder.textProperty().setValue(elementName("addressOfOrder"));
        statusOfOrder.textProperty().setValue(elementName("statusOfOrder"));
        dateOfOrder.textProperty().setValue(elementName("dateOfOrder"));

        allOrders.textProperty().setValue(elementName("allOrders"));
        newOrders.textProperty().setValue(elementName("newOrders"));
        doneOrders.textProperty().setValue(elementName("doneOrders"));
        cancelledOrders.textProperty().setValue(elementName("cancelledOrders"));

        createOrders.textProperty().setValue(elementName("createOrders"));
        editOrders.textProperty().setValue(elementName("editOrders"));
        removeOrders.textProperty().setValue(elementName("removeOrders"));

        searchLabel.textProperty().setValue(elementName("searchLabel"));
    }
}
