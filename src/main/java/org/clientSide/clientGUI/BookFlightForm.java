package org.clientSide.clientGUI;

import org.clientSide.Client;

import javax.swing.*;

public class BookFlightForm extends JFrame {
    private JTextField nameField;
    private JTextField surnameField;
    private JTextField flightId;
    private JTextField emailField;
    private JSpinner numSeats;

    private JButton bookYourFlightButton;
    private JPanel contentPane;

    private JLabel errorLabel;

    private Client c;

    public BookFlightForm(Client c){
        setContentPane(contentPane);
        this.c = c;
        setTitle("VoloOk Client");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        SpinnerNumberModel model = new SpinnerNumberModel(0,0,Integer.MAX_VALUE, 1);
        bookYourFlightButton.addActionListener(e -> bookFlight());
        setVisible(true);

    }

    private void bookFlight(){
        String name = nameField.getText();
        String surname = surnameField.getText();
        String email = emailField.getText();
        String flightId = this.flightId.getText();
        int numSeats = (Integer) this.numSeats.getValue();
        if(name.isEmpty() || surname.isEmpty() || email.isEmpty() || flightId.isEmpty()){
            errorLabel.setVisible(true);
        }
        else{
            if(c.bookFlight(name,surname,flightId,email,numSeats).getSuccess()){
                setVisible(false);
            }else{
                errorLabel.setVisible(true);
            }
        }
    }
}
