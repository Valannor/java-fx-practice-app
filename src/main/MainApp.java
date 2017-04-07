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
import model.lang_loader.LanguageLoader;
import model.lang_loader.WindowType;

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

            controller.applyInterface();
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
            Stage dialogStage = new Stage();
            AddOrderController controller = (AddOrderController) createController("templates_app/AddOrder.fxml",
                    "new_order", "main/resources/images/folder_img.png", dialogStage, false);

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
            Stage dialogStage = new Stage();
            EditOrderController controller = (EditOrderController) createController("templates_app/EditOrder.fxml",
                    "edit_order", "main/resources/images/folder_img.png", dialogStage, false);

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
     * Profile menu windows
     */
    public boolean showSignInDialog()
    {
        try
        {
            Stage dialogStage = new Stage();
            UserSignInController controller = (UserSignInController) createController("templates_adm/UserSignIn.fxml",
                    "sign_in", "main/resources/images/user_img.png", dialogStage, false);

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
            Stage dialogStage = new Stage();
            CloseFileController controller = (CloseFileController) createController("templates_adm/CloseFile.fxml",
                    "sign_out", "main/resources/images/user_img.png", dialogStage, false);

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
            Stage dialogStage = new Stage();
            AddUserController controller = (AddUserController) createController("templates_adm/AddUser.fxml",
                    "registration", "main/resources/images/user_img.png", dialogStage,false);

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
            Stage dialogStage = new Stage();
            DeleteAccountController controller = (DeleteAccountController) createController("templates_adm/DeleteAccount.fxml",
                    "delete_account", "main/resources/images/user_img.png", dialogStage, false);

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
            Stage dialogStage = new Stage();
            EditPasswordController controller = (EditPasswordController) createController("templates_adm/EditPassword.fxml",
                    "change_password", "main/resources/images/user_img.png", dialogStage, false);

            controller.setDialogStage(dialogStage);
            dialogStage.showAndWait();
            return controller.isOkClicked();

        } catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * Details menu windows
     */
    public void showDetailsWindow(Trip trip)
    {
        try
        {
            Stage dialogStage = new Stage();
            OrderDetailsController controller = (OrderDetailsController) createController("templates_adm/OrderDetails.fxml",
                    "orderDetails", "main/resources/images/user_img.png", dialogStage, true);

            controller.setTrip(trip);
            dialogStage.showAndWait();

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void showStatistics()
    {
        try
        {
            Stage dialogStage = new Stage();
            StatisticsController controller = (StatisticsController) createController("templates_adm/Statistics.fxml",
                    "statistics", "main/resources/images/user_img.png", dialogStage, true);

            controller.setOrders(ordersToShow);
            dialogStage.showAndWait();

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }



    private String elementName(String elementType)
    {
        return LanguageLoader.elementName(WindowType.MAIN_APP, elementType);
    }

    private Controller createController(String loaderResource, String titleElement,
                                        String imageLocation, Stage dialogStage, boolean isResizable) throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(loaderResource));
        AnchorPane pane = loader.load();

        dialogStage.setTitle(elementName(titleElement));

        dialogStage.setResizable(isResizable);
        dialogStage.initModality(Modality.WINDOW_MODAL);

        dialogStage.initOwner(primaryStage);
        Scene scene = new Scene(pane);
        dialogStage.setScene(scene);
        dialogStage.getIcons().add(new Image(imageLocation));

        return loader.getController();
    }
}
