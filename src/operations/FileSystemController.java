package operations;

import model.DataBaseManager;
import model.Status;
import model.Trip;

import java.util.ArrayList;
import java.util.List;

public class FileSystemController
{
    private static DataBaseManager dataBaseManager = DataBaseManager.getInstance();

    public static void openDataBase()
    {
        dataBaseManager.openDataBase();
    }

    public static void save()
    {
        dataBaseManager.saveDataBase();
    }


    /**
     * Create/edit order operations
     */

    public static void create(String address)
    {
        dataBaseManager.addTrip(address);
    }

    public static void editTrip(Trip trip, Status status, String address)
    {
        trip.setStatus(status);
        trip.setAddress(address);
    }

    public static void remove(Trip trip)
    {
        dataBaseManager.removeTrip(trip);
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
}
