package operations;

import model.DataBaseManager;
import model.Status;
import model.Trip;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FileSystemController
{
    private static DataBaseManager dataBaseManager = DataBaseManager.getInstance();
    private static boolean changed = false;

    /**
     * "File" menu button elements
     */

    public static void openDataBase(File file)
    {
        dataBaseManager.openDataBase(file);
        dataBaseManager.setDataBaseFile(file);
    }

    public static void createDataBase()
    {
        dataBaseManager.createNewDataBase();
        changed = true;
    }

    public static void save(File filePath)
    {
        dataBaseManager.saveDataBase(filePath);
        changed = false;
    }

    public static void closeDataBase()
    {
        dataBaseManager.closeDataBase();
        changed = false;
    }

    //Database status method
    public static File getDataBaseFile()
    {
        return dataBaseManager.getDataBaseFile();
    }


    /**
     * Create/edit order operations
     */

    public static void create(String address)
    {
        dataBaseManager.addTrip(address);
        changed = true;
    }

    public static void editTrip(Trip trip, Status status, String address)
    {
        trip.setStatus(status);
        trip.setAddress(address);
        changed = true;
    }

    public static void remove(Trip trip)
    {
        dataBaseManager.removeTrip(trip);
        changed = true;
    }


    /**
     * Show orders operations
     */

    public static List<Trip> showByStatus(Status status)
    {
        List<Trip> trips = new ArrayList<>();

        for (Trip trip : dataBaseManager.getTrips())
        {
            if (trip.getStatus().equals(status))
                trips.add(trip);
        }

        return trips;
    }

    public static List<Trip> showAll()
    {
        return dataBaseManager.getTrips();
    }

    public static List<Trip> showNewOrders()
    {
        return showByStatus(Status.NEW_ORDER);
    }

    public static List<Trip> showDoneOrders()
    {
        return showByStatus(Status.DONE);
    }

    public static List<Trip> showCancelledOrders()
    {
        return showByStatus(Status.CANCELLED);
    }

    public static List<Trip> findTripsByAddress(String address)
    {
        List<Trip> trips = new ArrayList<>();

        for (Trip trip : dataBaseManager.getTrips())
        {
            if (trip.getAddress().contains(address))
                trips.add(trip);
        }

        return trips;
    }


    /**
     * Database status method
     */

    public static boolean isChanged()
    {
        return changed;
    }


    /**
     * Date & language representation modes
     */
    private static String datePattern = "dd.MM.yyyy\thh:mm:ss ";
    public static Locale locale;

    public static String getDatePattern()
    {
        return datePattern;
    }

    public static Locale getLocale()
    {
        return locale;
    }

    public static void setDatePattern(String newPattern)
    {
        datePattern = newPattern;
    }

    public static void setLocale(Locale newLocale)
    {
        locale = newLocale;
    }
}
