package org.clientSide.clientGUI;

import org.clientSide.Client;

import javax.swing.*;

public class BuyTicketForm extends JFrame{
    private JTextField flightID;
    private JTextField promoCode;
    private JTextField creditCardNumber;
    private JButton purchaseFlightTicketButton;
    private JTextField email;
    private JTextField surnameField;
    private JTextField nameField;

    private JLabel errorLabel;
    private JButton applyDiscountButton;
    private JLabel priceLabel;
    private JRadioButton useFidelityPoints;
    private JPanel contentPane;

    private Client c;

    public BuyTicketForm(Client c){
        setContentPane(contentPane);
        this.c = c;
        setTitle("VoloOk Client");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();

        purchaseFlightTicketButton.addActionListener( e -> purchaseTicket());
        applyDiscountButton.addActionListener( e -> applyDiscount());
        useFidelityPoints.addActionListener(e -> priceLabel.setEnabled(!priceLabel.isEnabled()));
        setVisible(true);
    }

    private void purchaseTicket(){
        if(purchaseFlightTicketButton.getText().equals("Calculate Price")){
            int price = c.fetchFlightPrice(flightID.getText());
            if(price > 0){
                priceLabel.setText(String.valueOf(price));
                purchaseFlightTicketButton.setText("Purchase Ticket");
                System.out.println(c.getFidelityPoints());
                System.out.println(price*100);
                if(price*100 <= c.getFidelityPoints()){
                    useFidelityPoints.setEnabled(true);
                }
                errorLabel.setVisible(false);
            }else{
                errorLabel.setText("Could not find flight.");
                errorLabel.setVisible(true);
            }
        }else{
            String name = nameField.getText();
            String surname = surnameField.getText();
            String cardNumber = creditCardNumber.getText();
            String promoCode = null;
            if(!this.promoCode.getText().isBlank()){
                promoCode = this.promoCode.getText();
            }
            String flightID = this.flightID.getText();
            String email = this.email.getText();
            if(name.isEmpty() || surname.isEmpty() || cardNumber.isEmpty() || flightID.isEmpty() || email.isEmpty()){
                errorLabel.setText("Something is empty. Please check all fields and try again.");
                errorLabel.setVisible(true);
            }
            else{
                if(c.purchaseTicket(flightID,name,surname,email,cardNumber).getSuccess()){
                    if(useFidelityPoints.isSelected()){
                        int points = (int) Math.round(Double.parseDouble(priceLabel.getText()));
                        c.deductFidelityPoints(points * 100);
                    }
                    setVisible(false);
                }else{
                    errorLabel.setText("Something went wrong during checkout. Please try again later.");
                    errorLabel.setVisible(true);
                }
            }
        }
    }

    private void applyDiscount(){
        int price = c.fetchFlightPrice(flightID.getText());
        double discountFactor = c.fetchDiscountFactor(promoCode.getText(),flightID.getText());
        priceLabel.setText(String.valueOf(price * discountFactor));
        if(price*100 <= c.getFidelityPoints()){
            useFidelityPoints.setEnabled(true);
        }
        purchaseFlightTicketButton.setText("Purchase Ticket");
    }

}
