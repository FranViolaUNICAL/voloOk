package org.serverSide.serverGUI;

import io.grpc.Server;
import org.serverSide.components.singletonLists.AdministratorList;
import org.serverSide.components.units.Administrator;
import org.serverSide.components.units.Unit;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.util.List;

public class LoginPage extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel passwordLabel;
    private JLabel usernameLabel;
    private JLabel wrongCredentialsLabel;
    private JPanel contentPane;


    public LoginPage(){
        setTitle("VoloOk Administrator Login");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(contentPane);
        pack();
        wrongCredentialsLabel.setVisible(false);
        loginButton.addActionListener(e -> login());
        setVisible(true);
    }

    private void login(){
        char[] password = passwordField.getPassword();
        String username = usernameField.getText();
        List<Unit> administratorList = AdministratorList.getInstance().getAdministratorsList();
        StringBuilder passwordString = new StringBuilder();
        for(int i = 0; i < password.length; i++){
            passwordString.append(password[i]);
        }
        Administrator check = new Administrator(username,passwordString.toString());
        for(Unit uA : administratorList){
            Administrator a = (Administrator) uA;
            if(a.equals(check)){
                new ServerGUI();
                setVisible(false);
            }
        }
        wrongCredentialsLabel.setVisible(true);
        passwordField.setText("");
        usernameField.setText("");
    }
}
