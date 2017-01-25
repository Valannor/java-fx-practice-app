package main;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Trip;
import main.templates_app.*;

import java.io.IOException;
import java.util.List;

public class MainApp extends Application
{
    private ObservableList<Trip> ordersToShow = FXCollections.observableArrayList();

    public MainApp()
    {

    }

    public ObservableList<Trip> getOrdersToShow()
    {
        return ordersToShow;
    }

    public void setOrdersToShow(List<Trip> toShow)
    {
        ordersToShow.addAll(toShow);
    }

    private BorderPane root;
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Taxi App");

        this.primaryStage.getIcons().add(new Image("main/resources/images/taxi_img.png"));

        initRoot();
        initMainWindow();
    }

    private void initRoot()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("templates_app/Root.fxml"));
            root = loader.load();

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);

            RootController controller = loader.getController();
            controller.setMainApp(this);

            //Window min size
            primaryStage.setMinHeight(300);
            primaryStage.setMinWidth(691);

            primaryStage.show();

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void initMainWindow()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("templates_app/MainWind.fxml"));
            AnchorPane mainWind = loader.load();

            root.setCenter(mainWind);

            MainWindController controller = loader.getController();
            setMainWindController(controller);
            controller.setMainApp(this);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public Stage getPrimaryStage()
    {
        return primaryStage;
    }

    public static void main(String[] args)
    {
        launch(args);
    }


    /**
     * Add and edit orders windows
     */
    public boolean showAddOrderDialog()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("templates_app/AddOrder.fxml"));
            AnchorPane addPane = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("New order");
            dialogStage.setResizable(false);
            dialogStage.initModality(Modality.WINDOW_MODAL);

            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(addPane);
            dialogStage.setScene(scene);
            dialogStage.getIcons().add(new Image("main/resources/images/folder_img.png"));

            AddOrderController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            dialogStage.showAndWait();
            return controller.isOkClicked();

        } catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showEditOrderDialog(Trip trip)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("templates_app/EditOrder.fxml"));
            AnchorPane editPane = loader.load();


            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit order");
            dialogStage.setResizable(false);
            dialogStage.initModality(Modality.WINDOW_MODAL);

            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(editPane);
            dialogStage.setScene(scene);
            dialogStage.getIcons().add(new Image("main/resources/images/folder_img.png"));

            EditOrderController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setTrip(trip);

            dialogStage.showAndWait();
            return controller.isOkClicked();

        } catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    //Support operations
    private MainWindController controller = null;

    private void setMainWindController(MainWindController controller)
    {
        this.controller = controller;
    }

    public MainWindController getMainWindController()
    {
        return controller;
    }
}
