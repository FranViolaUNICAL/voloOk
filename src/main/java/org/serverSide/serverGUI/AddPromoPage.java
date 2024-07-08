package org.serverSide.serverGUI;

import org.serverSide.components.singletonLists.PromoList;
import org.serverSide.components.units.Promo;

import javax.swing.*;

public class AddPromoPage extends JFrame {
    private JTextField description;
    private JTextField code;
    private JTextField endDate;
    private JTextField departure;
    private JTextField arrival;
    private JTextField fidelityOnly;
    private JTextField discount;
    private JButton createPromoButton;
    private JPanel contentPane;

    public AddPromoPage(){
        setTitle("VoloOk Server Interface");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(contentPane);
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
        boolean fidOnly = fidelityOnly.getText().equals("Y");
        double df = Double.parseDouble(discount.getText());
        PromoList.getInstance().add(new Promo(descr,c,eD,d,a,fidOnly,df));
        setVisible(false);
    }
}
