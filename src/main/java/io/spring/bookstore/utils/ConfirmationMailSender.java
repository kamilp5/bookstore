package io.spring.bookstore.utils;

import io.spring.bookstore.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ConfirmationMailSender {

    private JavaMailSender mailSender;

    @Autowired
    public ConfirmationMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void send(Order order) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setSubject("Potwierdzenie zamówienia");
        msg.setTo(order.getUser().getEmail());
        List<String> books = order.getBooksQuantities().stream()
                .map(q -> q.getBook().getTitle() + " - " + q.getQuantity().setScale(1,RoundingMode.HALF_DOWN) + "x")
                .collect(Collectors.toList());
        StringBuilder message = new StringBuilder();
        message.append("Numer twojego zamowiania: ");
        message.append(order.getId());
        message.append("\n");
        message.append("\n");
        message.append("Zamowione książki");
        for(String s: books){
            message.append(s);
            message.append("\n");
        }
        message.append("\n");
        message.append("Koszt zamówienia: ");
        message.append(order.getPrice().setScale(2,RoundingMode.HALF_DOWN));
        message.append(" zł");

        msg.setText(message.toString());
        try{
            this.mailSender.send(msg);
        }
        catch (MailException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
