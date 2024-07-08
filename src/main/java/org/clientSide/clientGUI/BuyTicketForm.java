package org.clientSide.clientGUI;

import org.clientSide.Client;

import javax.swing.*;

public class BuyTicketForm extends JFrame{
    private JTextField flightID;
    private JTextField promoCode;
    private JTextField creditCardNumber;
    private JButton purchaseFlightTicketButton;
    private JPanel contentPane;
    private JTextField email;
    private JTextField surnameField;
    private JTextField nameField;
    private JLabel errorLabel;
    private JButton applyDiscountButton;
    private JLabel priceLabel;
    private Client c;

    public BuyTicketForm(Client c){
        this.c = c;
        setTitle("VoloOk Client");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(contentPane);
        pack();

        purchaseFlightTicketButton.addActionListener( e -> purchaseTicket());
        applyDiscountButton.addActionListener( e -> applyDiscount());

        setVisible(true);
    }

    private void purchaseTicket(){
        if(purchaseFlightTicketButton.getText().equals("Calculate Price")){
            int price = c.fetchFlightPrice(flightID.getText());
            if(price > 0){
                priceLabel.setText(String.valueOf(price));
                purchaseFlightTicketButton.setText("Purchase Flight");
                errorLabel.setVisible(false);
            }else{
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
            if(c.purchaseTicket(flightID,name,surname,email,cardNumber,promoCode).getSuccess()){
                setVisible(false);
            }else{
                errorLabel.setVisible(true);
            }
        }
    }

    private void applyDiscount(){
        int price = c.fetchFlightPrice(flightID.getText());
        double discountFactor = c.fetchDiscountFactor(promoCode.getText(),flightID.getText());
        priceLabel.setText(String.valueOf(price * discountFactor));
    }

}
