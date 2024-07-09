package org.clientSide.clientGUI;

import org.clientSide.Client;

import javax.swing.*;

public class RegisterForm extends JFrame {
    private JTextField emailField;
    private JTextField surnameField;
    private JTextField cityBirthField;
    private JTextField dateField;
    private JTextField regionField;
    private JTextField passwordField;
    private JTextField nameField;

    private JButton registerButton;

    private Client c;
    private JPanel contentPane;


    public RegisterForm(Client c){
        setContentPane(contentPane);
        setTitle("VoloOk Client");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        this.c = c;

        registerButton.addActionListener(e -> register());

        setVisible(true);
    }

    private void register(){
        String email = emailField.getText();
        String password = passwordField.getText();
        String name = nameField.getText();
        String surname = surnameField.getText();
        String dateofBirth = dateField.getText();
        String placeBirth = cityBirthField.getText();
        String region = regionField.getText();

        if(c.register(email,name,surname,placeBirth,region,dateofBirth,password)){
            setVisible(false);
        }else{
            emailField.setText("Email already taken. Try logging in.");
        }

    }
}
