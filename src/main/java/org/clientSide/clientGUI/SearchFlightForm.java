package org.clientSide.clientGUI;

import org.clientSide.Client;
import user.UserServices;

import javax.swing.*;

public class SearchFlightForm extends JFrame {
    private JTextField origin;
    private JTextField destination;
    private JComboBox<String> year;
    private JComboBox<String> month;
    private JComboBox<String> day;
    private JComboBox<String> Hour;
    private JComboBox<String> Minutes;
    private JButton searchFlightsButton;

    private JPanel contentPane;

    private Client c;
    private ClientGUI gui;

    public SearchFlightForm(Client c, ClientGUI gui){
        setTitle("VoloOk Client");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(contentPane);
        pack();
        this.c = c;
        this.gui = gui;
        populateDateComboBoxes();

        searchFlightsButton.addActionListener(e -> searchFlights());
        month.addActionListener(e -> updateDays());
        year.addActionListener(e -> updateDays());

        setVisible(true);
    }

    private void populateDateComboBoxes(){
        day.addItem("Select Day");
        month.addItem("Select Month");
        year.addItem("Select Year");
        Hour.addItem("Select Hour");
        Minutes.addItem("Select Minutes");
        year.addItem("2024");
        year.addItem("2025");
        year.addItem("2026");
        for(int i = 1; i <= 12; i++){
            String month = "";
            if(i < 10){
                month += "0";
                month += String.valueOf(i);
            }else{
                month = String.valueOf(i);
            }
            this.month.addItem(month);
        }
        for(int i = 1; i <= 24; i++){
            String hour = "0";
            if(i < 10){
                hour += String.valueOf(i);
            }else{
                hour = String.valueOf(i);
            }
            this.Hour.addItem(hour);
        }
        for(int i = 0; i <= 45; i+=15){
            if(i == 0){
                Minutes.addItem("00");
            }else{
                Minutes.addItem(String.valueOf(i));
            }
        }
        for(int i = 1; i <= 31; i++){
            String day = "0";
            if(i < 10){
                day += String.valueOf(i);
            }
            else{
                day = String.valueOf(i);
            }
            this.day.addItem(day);
        }
    }

    private void searchFlights(){
        String origin = this.origin.getText();
        String destination = this.destination.getText();
        String month = (String) this.month.getSelectedItem();
        String year = (String) this.year.getSelectedItem();
        String day = (String) this.day.getSelectedItem();
        StringBuilder sb = new StringBuilder();
        sb.append(day).append("/").append(month).append("/").append(year).append(" ");
        String hour = (String) Hour.getSelectedItem();
        String minutes = (String) Minutes.getSelectedItem();
        sb.append(hour).append(":").append(minutes).append(":00");
        String date = sb.toString();
        for(UserServices.Flight f : c.checkFlightAvailability(origin,destination,date).getFlightsList()){
            StringBuilder info = new StringBuilder();
            info.append(f.getFlightId()).append(" ").append("Departs from: ").append(f.getOrigin()).append(" Arrives at: ").append(f.getDestination())
                            .append(" Departure Time: ").append(f.getDepartureTime()).append(" Flight ID: ").append(f.getFlightId()).append(" Price: ")
                    .append(f.getPrice()).append('\n');
            gui.textArea.append(info.toString());
        }
        setVisible(false);
    }

    private void updateDays(){
        this.day.removeAllItems();
        String month = (String) this.month.getSelectedItem();
        System.out.println(month);
        int maxDays = 31;
        if(month.equals("04")  ||month.equals("06") || month.equals("09") || month.equals("11")){
            maxDays = 30;
        }
        else if(month.equals("02")){
            String year = (String) this.year.getSelectedItem();
            int yearInt = Integer.parseInt(year);
            if(yearInt % 4 == 0 || (yearInt % 400 == 0)){
                maxDays = 29;
            }else{
                maxDays = 28;
            }
        }
            for(int i = 1; i <= maxDays; i++){
                String day = "0";
                if(i < 10){
                    day += String.valueOf(i);
                }
                else{
                    day = String.valueOf(i);
                }
                this.day.addItem(day);

        }
    }


}
