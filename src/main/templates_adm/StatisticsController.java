package main.templates_adm;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import main.Controller;
import model.Trip;
import model.lang_loader.LanguageLoader;
import model.lang_loader.WindowType;
import operations.FileSystemController;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.*;

public class StatisticsController implements Controller
{
    @FXML
    private BarChart barChart;
    @FXML
    private CategoryAxis axis;
    @FXML
    private Button months;
    @FXML
    private Button weekdays;
    @FXML
    private Button hours;

    private ObservableList timePeriods = FXCollections.observableArrayList();
    private List<Trip> orders;

    @FXML
    private void initialize()
    {
        months.setText(elementName("months"));
        weekdays.setText(elementName("weekdays"));
        hours.setText(elementName("hours"));

//        setMonthData();
    }

    public void setOrders(List<Trip> orders)
    {
        this.orders = orders;
    }


    @FXML
    private void setMonthData()
    {
        String[] months = DateFormatSymbols.getInstance(FileSystemController.getLocale()).getMonths();
        chartInitModel(months);

        int[] monthCounter = new int[12];
        for (Trip trip : orders)
        {
            int month = trip.getOrderDate().getMonth();
            monthCounter[month]++;
        }

        XYChart.Series series = new XYChart.Series();
        for (int i = 0; i < monthCounter.length; i++)
        {
            series.getData().add(new XYChart.Data<>(timePeriods.get(i), monthCounter[i]));
        }

        barChart.getData().clear();
        barChart.getData().add(series);
    }

    @FXML
    private void setWeekdaysData()
    {
        String[] weekdays = DateFormatSymbols.getInstance(FileSystemController.getLocale()).getWeekdays();
        chartInitModel(weekdays);

        int[] weekdayCounter = new int[7];
        for (Trip trip : orders)
        {
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(trip.getOrderDate());
            int weekday = calendar.get(Calendar.DAY_OF_WEEK);
            weekdayCounter[weekday]++;
        }

        XYChart.Series series = new XYChart.Series();
        for (int i = 0; i < weekdayCounter.length; i++)
        {
            series.getData().add(new XYChart.Data<>(timePeriods.get(i), weekdayCounter[i]));
        }

        barChart.getData().clear();
        barChart.getData().add(series);
    }

    @FXML
    private void setHoursData()
    {
        String[] hours = new String[24];
        for (int i = 0; i < 24; i++)
        {
            if (i < 10) hours[i] = "0" + i + ":00";
            else hours[i] = i + ":00";
        }

        chartInitModel(hours);

        int[] hourCounter = new int[24];
        for (Trip trip : orders)
        {
            int hour = trip.getOrderDate().getHours();
            hourCounter[hour]++;
        }

        XYChart.Series series = new XYChart.Series();
        for (int i = 0; i < hourCounter.length; i++)
        {
            series.getData().add(new XYChart.Data<>(timePeriods.get(i), hourCounter[i]));
        }

        barChart.getData().clear();
        barChart.getData().add(series);
    }



    private void chartInitModel(String[] timePattern)
    {
        timePeriods.clear();
        timePeriods.addAll(Arrays.asList(timePattern));
        axis.setCategories(timePeriods);
    }

    private String elementName(String elementType)
    {
        return LanguageLoader.elementName(WindowType.STATISTICS, elementType);
    }
}
