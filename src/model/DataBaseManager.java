package model;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.nio.file.Files;
import java.util.*;

public class DataBaseManager
{
    private static DataBaseManager instance = new DataBaseManager();

    public static DataBaseManager getInstance()
    {
        return instance;
    }

    private File dataBaseFile = new File("DataBase.txt");

    private List<Trip> trips;
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


    public void openDataBase()
    {
        List<Trip> tempTrips = new ArrayList<>();

        if (!dataBaseFile.exists())
        {
            try
            {
                Files.createFile(dataBaseFile.toPath());
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }

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

    public void saveDataBase()
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
}
