package org.serverSide.serverGUI;

import org.serverSide.components.singletonLists.PromoList;

import javax.swing.*;

public class RemovePromoPage extends JFrame {
    private JTextField promoCode;

    private JButton removePromoButton;
    private JPanel contentPane;


    public RemovePromoPage(){
        setContentPane(contentPane);
        setTitle("VoloOk Server Interface");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();

        removePromoButton.addActionListener(e -> removePromo());

        setVisible(true);
    }

    private void removePromo(){
        String c = promoCode.getText();
        PromoList.getInstance().remove(c);
        setVisible(false);
    }
}
