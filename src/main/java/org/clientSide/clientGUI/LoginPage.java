package org.clientSide.clientGUI;

import org.clientSide.Client;

import javax.swing.*;

public class LoginPage extends JFrame {

    private Client c;
    private ClientGUI cGUI;


    private JTextField usernameField;
    private JButton loginButton;
    private JPasswordField passwordField;
    private JButton registerButton;
    private JButton continueAsGuestButton;
    private JLabel wrongCredentialsLabel;
    private JPanel content;


    public LoginPage(Client c, ClientGUI cGUI){
        setContentPane(content);
        setTitle("VoloOk Client");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        this.c = c;
        this.cGUI = cGUI;
        loginButton.addActionListener(e -> {login();});
        continueAsGuestButton.addActionListener(e -> setVisible(false));
        registerButton.addActionListener(e ->{new RegisterForm(this.c); setVisible(false);});
        setVisible(true);
    }

    private void login(){
        String email = usernameField.getText();
        char[] password = passwordField.getPassword();
        StringBuilder pS = new StringBuilder();
        for(int i = 0; i < password.length; i++){
            pS.append(password[i]);
        }
        if(c.login(email,pS.toString())){
            setVisible(false);
            cGUI.loginButton.setText("Logout");
            cGUI.registerButton.setVisible(false);
            cGUI.myBookingsButton.setEnabled(true);
            cGUI.myTicketsButton.setEnabled(true);
            cGUI.changeBookingDateButton.setEnabled(true);
            cGUI.cancelBookingButton.setEnabled(true);
            cGUI.myFidelityPointsButton.setEnabled(true);
            c.sendNotifications(true);
        }else{
            wrongCredentialsLabel.setVisible(true);
            usernameField.setText("");
            passwordField.setText("");
        }

    }

}
