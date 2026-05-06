package com.example.usercrud.Service;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.ByteArrayOutputStream;

import org.springframework.stereotype.Service;

@Service
public class PdfService {

    public byte[] generateOrderPdf(String username) {

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("Order Summary"));
            document.add(new Paragraph("User: " + username));
            document.add(new Paragraph("Item: Laptop"));
            document.add(new Paragraph("Price: 50000"));
            document.add(new Paragraph("Total: 50000"));

            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return out.toByteArray(); 
    }
}