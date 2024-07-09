package org.serverSide.serverGUI;

import org.serverSide.components.singletonLists.PromoList;
import org.serverSide.components.units.Promo;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class AddPromoPage extends JFrame {
    private JTextField description;
    private JTextField code;
    private JTextField endDate;
    private JTextField departure;
    private JTextField arrival;

    private JTextField fidelityOnly;
    private JTextField discount;
    private JButton createPromoButton;
    private JLabel errorLabel;
    private JPanel contentPane;


    public AddPromoPage(){
        setContentPane(contentPane);
        setTitle("VoloOk Server Interface");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();

        createPromoButton.addActionListener(e -> addPromo());

        setVisible(true);
    }

    private void addPromo(){
        String descr = description.getText();
        String c = code.getText();
        String eD = endDate.getText();
        String a = arrival.getText();
        String d = departure.getText();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy' 'HH:mm:ss");
        boolean parsable = true;
        try{
            sdf.parse(eD);
        }catch (ParseException e){
            parsable = false;
        }

        boolean fidOnly = false;
        if(!fidelityOnly.getText().isBlank()){
            fidOnly = fidelityOnly.getText().equals("Y");
        }
        double df = 1;
        if(!discount.getText().isBlank()){
            df = Double.parseDouble(discount.getText());
        }
        if(descr.isBlank() || c.isBlank() || eD.isBlank() || a.isBlank() || d.isBlank() || fidelityOnly.getText().isEmpty() || discount.getText().isEmpty() || !parsable){
            errorLabel.setVisible(true);
        }
        else{
            PromoList.getInstance().add(new Promo(descr,c,eD,d,a,fidOnly,df));
            setVisible(false);
        }
    }
}
