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
import main.templates_adm.*;
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
        if (!ordersToShow.isEmpty())
            ordersToShow.clear();

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
            loader.setLocation(getClass().getResource("templates_adm/Root.fxml"));
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


    /**
     * Administration menu windows
     */
    public boolean showSignInDialog()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("templates_adm/UserSignIn.fxml"));
            AnchorPane signInPane = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Sign in");

            dialogStage.setResizable(false);
            dialogStage.initModality(Modality.WINDOW_MODAL);

            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(signInPane);
            dialogStage.setScene(scene);
            dialogStage.getIcons().add(new Image("main/resources/images/user_img.png"));

            UserSignInController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            dialogStage.showAndWait();
            return controller.isOkClicked();

        } catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showSignOutDialog()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("templates_adm/CloseFile.fxml"));
            AnchorPane signOutPane = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Sign out");
            dialogStage.setResizable(false);
            dialogStage.initModality(Modality.WINDOW_MODAL);

            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(signOutPane);
            dialogStage.setScene(scene);
            dialogStage.getIcons().add(new Image("main/resources/images/user_img.png"));

            CloseFileController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            dialogStage.showAndWait();
            return controller.isOkClicked();

        } catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showAddUser()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("templates_adm/AddUser.fxml"));
            AnchorPane addUserPane = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("User registration");

            dialogStage.setResizable(false);
            dialogStage.initModality(Modality.WINDOW_MODAL);

            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(addUserPane);
            dialogStage.setScene(scene);
            dialogStage.getIcons().add(new Image("main/resources/images/user_img.png"));

            AddUserController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            dialogStage.showAndWait();
            return controller.isOkClicked();

        } catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showRemoveUser()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("templates_adm/DeleteAccount.fxml"));
            AnchorPane removeUserPane = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Delete account");

            dialogStage.setResizable(false);
            dialogStage.initModality(Modality.WINDOW_MODAL);

            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(removeUserPane);
            dialogStage.setScene(scene);
            dialogStage.getIcons().add(new Image("main/resources/images/user_img.png"));

            DeleteAccountController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            dialogStage.showAndWait();
            return controller.isOkClicked();

        } catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showEditPassword()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("templates_adm/EditPassword.fxml"));
            AnchorPane editPasswordPane = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Change Password");

            dialogStage.setResizable(false);
            dialogStage.initModality(Modality.WINDOW_MODAL);

            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(editPasswordPane);
            dialogStage.setScene(scene);
            dialogStage.getIcons().add(new Image("main/resources/images/user_img.png"));

            EditPasswordController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            dialogStage.showAndWait();
            return controller.isOkClicked();

        } catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
    }
}
