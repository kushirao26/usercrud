package com.example.usercrud.Service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.*;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private PdfService pdfService;

    public void sendEmailWithPdf(String toEmail, String username) {

        try {
            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true);

            helper.setTo(toEmail);
            helper.setSubject("Order Details");
            helper.setText("Hello " + username +
                    ", please find your order details attached.");
            byte[] pdfBytes = pdfService.generateOrderPdf(username);

            helper.addAttachment("Order.pdf",
                    new ByteArrayResource(pdfBytes));

            mailSender.send(message);

            System.out.println("Email with PDF sent");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}