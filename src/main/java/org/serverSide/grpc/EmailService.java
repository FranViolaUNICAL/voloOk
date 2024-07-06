package org.serverSide.grpc;

import java.io.File;
import java.net.URI;
import java.util.Properties;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.Multipart;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

public class EmailService {

    private String username;
    private String password;
    private final Properties prop;

    public EmailService(String host, int port, String username, String password) {
        prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", host);
        prop.put("mail.smtp.port", port);
        prop.put("mail.smtp.ssl.trust", host);

        this.username = username;
        this.password = password;
    }

    public EmailService(String host, int port) {
        prop = new Properties();
        prop.put("mail.smtp.host", host);
        prop.put("mail.smtp.port", port);
    }

    public void sendPromoMail(String address, String body) throws Exception {

        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("vlifnc03m13g791z@studenti.unical.it"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(address));
        message.setSubject("Nuove Promozioni da VoloOK!");

        String msg = body;

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(msg, "text/html; charset=utf-8");
    }

    public void sendUnloyaltyEmail(String address) throws Exception{
        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("vlifnc03m13g791z@studenti.unical.it"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(address));
        message.setSubject("Attenzione! Il tuo account fedeltá potrebbe essere cancellato.");

        String msg = "Fra 24 ore, dedurremo dal tuo account i tuoi punti fedeltá accumulati. Effettua un acquisto entro 24 ore per evitare di perderli!";

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(msg, "text/html; charset=utf-8");
    }

    public void sendBookingEmail(String address, String bookingCode) throws Exception{
        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("vlifnc03m13g791z@studenti.unical.it"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(address));
        message.setSubject("Attenzione! La tua prenotazione scadrá fra 24 ore.");

        String msg = "Fra 24 ore, la tua prenotazione " + bookingCode + " scadrá. Acquista il tuo biglietto!";

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(msg, "text/html; charset=utf-8");
    }

}
