package model;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.*;

public class DataBaseManager
{
    private static DataBaseManager instance = new DataBaseManager();

    public static DataBaseManager getInstance()
    {
        return instance;
    }

    private List<Trip> trips;
    private File dataBaseFile = null;
    private ObjectMapper mapper;

    private DataBaseManager()
    {
        this.mapper = new ObjectMapper();
    }

    private Trip convertJsonToTrip(String json)
    {
        Trip trip = null;

        try (StringReader reader = new StringReader(json))
        {
            trip = mapper.readValue(reader, Trip.class);
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return trip;
    }

    private List<Trip> showInOrder()
    {
        List<Trip> tripsList = new ArrayList<>(trips);
        tripsList.sort(new Comparator<Trip>()
        {
            @Override
            public int compare(Trip o1, Trip o2)
            {
                int result = o1.getOrderDate().compareTo(o2.getOrderDate());
                if (result == 0)
                    result = o1.getOrderNumber() < o2.getOrderNumber() ? -1 : 1;

                return result;
            }
        });

        return tripsList;
    }

    public List<Trip> getTrips()
    {
        return trips;
    }

    public boolean addTrip(String address)
    {
        if (trips.size() > 0)
        {
            int lastOrderNumber = showInOrder().get(trips.size() - 1).getOrderNumber();
            return trips.add(new Trip(lastOrderNumber + 1, address));
        } else
            return trips.add(new Trip(1, address));
    }

    public boolean removeTrip(Trip trip)
    {
        return trips.remove(trip);
    }


    public void openDataBase(File dataBaseFile)
    {
        List<Trip> tempTrips = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(dataBaseFile)))
        {
            String s;
            while ((s = reader.readLine()) != null)
            {
                Trip trip = convertJsonToTrip(s);
                if (trip != null)
                    tempTrips.add(trip);
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        trips = tempTrips;
    }

    public void createNewDataBase()
    {
        trips = new ArrayList<>();
        dataBaseFile = null;
    }

    public void saveDataBase(File dataBaseFile)
    {
        List<Trip> tripsList = showInOrder();

        StringWriter writer;

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(dataBaseFile)))
        {
            for (Trip trip : tripsList)
            {
                writer = new StringWriter();
                mapper.writeValue(writer, trip);

                bufferedWriter.write(writer.toString() + "\r\n");
                writer.flush();
                writer.close();
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void closeDataBase()
    {
        trips = null;
    }

    public File getDataBaseFile()
    {
        return dataBaseFile;
    }

    public void setDataBaseFile(File file)
    {
        this.dataBaseFile = file;
    }
}
