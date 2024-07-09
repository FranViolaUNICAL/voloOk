package org.clientSide.clientGUI;

import org.clientSide.Client;
import user.UserServices;

import javax.swing.*;

public class ChangeBookingForm extends JFrame {
    private JTextField textField;
    private JComboBox<String> day;
    private JComboBox<String> month;
    private JComboBox<String> year;
    private JComboBox<String> hour;
    private JComboBox<String> minutes;
    private JButton selectNewFlightToButton;
    private JLabel dateLabel;
    private JLabel bookingIDlabel;
    private JScrollPane scrollPane;

    private JComboBox<String> newFlightIdBox;
    private JLabel newFlightLabel;
    private JButton changeBookingDateButton;
    private JLabel errorLabel;
    private JTextArea availableFlightsTextArea;
    private JLabel priceLabel;
    private JTextField cardNumberField;
    private JLabel cardLabel;
    private JLabel errorOnPaymentLabel;
    private JPanel contentPane;


    private Client c;

    int cost = 0;

    public ChangeBookingForm(Client c){
        setContentPane(contentPane);
        this.c = c;
        setTitle("VoloOk Client");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        populateDateComboBoxes();

        month.addActionListener(e -> updateDays());
        selectNewFlightToButton.addActionListener( e -> searchAvailableFlights());
        changeBookingDateButton.addActionListener( e -> changeBookingDate());

        setVisible(true);

    }

    private void populateDateComboBoxes(){
        day.addItem("Select Day");
        month.addItem("Select Month");
        year.addItem("Select Year");
        hour.addItem("Select Hour");
        minutes.addItem("Select Minutes");
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
            this.hour.addItem(hour);
        }
        for(int i = 0; i <= 45; i+=15){
            if(i == 0){
                minutes.addItem("00");
            }else{
                minutes.addItem(String.valueOf(i));
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

    private void searchAvailableFlights(){
        String bookingId = textField.getText();
        String month = (String) this.month.getSelectedItem();
        String year = (String) this.year.getSelectedItem();
        String day = (String) this.day.getSelectedItem();
        StringBuilder sb = new StringBuilder();
        sb.append(day).append("/").append(month).append("/").append(year).append(" ");
        String hour = (String) this.hour.getSelectedItem();
        String minutes = (String) this.minutes.getSelectedItem();
        sb.append(hour).append(":").append(minutes).append(":00");
        String date = sb.toString();
        UserServices.ChangeFlightDateResponse response = c.changeFlightDate(bookingId,date);
        if(response.getFlightsCount() > 0){
            this.changeBookingDateButton.setVisible(true);
            this.scrollPane.setVisible(true);
            this.newFlightIdBox.setVisible(true);
            this.newFlightLabel.setVisible(true);
            this.availableFlightsTextArea.setVisible(true);
            this.cardLabel.setVisible(true);
            this.cardNumberField.setVisible(true);
            for(UserServices.Flight f : response.getFlightsList()){
                StringBuilder info = new StringBuilder();
                info.append(f.getFlightId()).append(" ").append("Departs from: ").append(f.getOrigin()).append(" Arrives at: ").append(f.getDestination())
                        .append(" Departure Time: ").append(f.getDepartureTime()).append(" Flight ID: ").append(f.getFlightId()).append(" Price: ")
                        .append(f.getPrice()).append('\n');
                availableFlightsTextArea.append(info.toString());
                newFlightIdBox.addItem(f.getFlightId());
                priceLabel.setText("This operation will cost you " + f.getPrice() * 0.1);
                cost = (int) (f.getPrice() * 0.1);
            }
            selectNewFlightToButton.setEnabled(false);
        }else{
            this.errorLabel.setVisible(true);
        }
    }

    public void changeBookingDate(){
        if(c.chooseFlightToChangeDate(textField.getText(), (String) newFlightIdBox.getSelectedItem(),  cost, cardNumberField.getText()).getSuccess() && !cardNumberField.getText().isBlank()){
            this.setVisible(false);
        }else{
            errorOnPaymentLabel.setVisible(true);
        }
    }
}
