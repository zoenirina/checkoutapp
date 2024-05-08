/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapp.component;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import javaapp.page.Client;

public class PdfReceiptGenerator {
private Client client;    
public PdfReceiptGenerator() {
   
    }
    public void printPDF(int idClient, int idFacture) {
        String filePath = "C:/Users/ASUS/Facture.pdf";
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
            Font boldWhiteFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.WHITE);
            BaseColor darkBlue = new BaseColor(12, 26, 39); // Custom dark blue color #000d1a
            BaseColor darkGray = new BaseColor(234, 234, 225); // Dark gray for borders
            
            PdfPTable headerTable = new PdfPTable(2);
            headerTable.setWidthPercentage(100);
            headerTable.getDefaultCell().setBorder(PdfPCell.NO_BORDER);

            // Identification du vendeur
            PdfPCell vendorCell = new PdfPCell();
            vendorCell.addElement(new Phrase("Identification du Vendeur", boldFont));
            vendorCell.addElement(new Phrase("Nom du Vendeur\nAdresse du Vendeur\nTel: 0123456789\nEmail: email@vendeur.com"));
            vendorCell.setBorder(PdfPCell.NO_BORDER);
            headerTable.addCell(vendorCell);

            // Identification du client
            PdfPCell clientCell = new PdfPCell();
            clientCell.addElement(new Phrase("Identification du Client", boldFont));
            clientCell.addElement(new Phrase("Nom du Client\nAdresse du Client\nTel: 9876543210\nNIF: XXXXXXXXXX\nStat: XXXXXXXXXXX\n\n"));
            clientCell.setBorder(PdfPCell.NO_BORDER);
            headerTable.addCell(clientCell);

            // Ligne pour numéro de facture et date
            PdfPCell invoiceInfoCell = new PdfPCell(new Phrase("Facture n°1 00\nDate: XX/XX/XX", boldFont));
            invoiceInfoCell.setColspan(2);
            invoiceInfoCell.setBorder(PdfPCell.NO_BORDER);
            headerTable.addCell(invoiceInfoCell);

            // Ajout du tableau en haut du document
            document.add(headerTable);

            // Ajouter un saut de ligne
            document.add(new Paragraph("\n\n"));

//            Paragraph companyName = new Paragraph("Nom de l'Entreprise", boldFont);
//            companyName.setAlignment(Element.ALIGN_CENTER);
//            document.add(companyName);

//            Paragraph clientInfo = new Paragraph("Nom du Client\nTel: 0123456789\nAdresse du Client");
//            document.add(clientInfo);

            // Tableau des commandes avec bordures gris foncé
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            PdfPCell cell;

            String[] headers = {"Produit", "Quantité", "Prix Unitaire","Total"};
            for (String header : headers) {
                cell = new PdfPCell(new Phrase(header, boldWhiteFont));
                cell.setBackgroundColor(darkBlue);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorderColor(darkGray);
                cell.setFixedHeight(30); // Setting height of header rows
                table.addCell(cell);
            }

            // Données avec alternance de couleurs
            String[][] data = {
                {"Produit 1", "2", "100 €","1050 €"},
                {"Produit 2", "5", "250 €","1000 €"},
                {"Produit 2", "5", "250 €","1000 €"}
            };

            BaseColor evenColor = BaseColor.WHITE;  // Couleur pour lignes paires (blanc)
            BaseColor oddColor = new BaseColor(240, 240, 240); // Gris clair pour lignes impaires

            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < data[i].length; j++) {
                    cell = new PdfPCell(new Phrase(data[i][j]));
                    cell.setBorderColor(darkGray); // Dark gray borders
                    cell.setFixedHeight(30); // Setting height of data rows
                    if (i % 2 == 0) {
                        cell.setBackgroundColor(evenColor);
                    } else {
                        cell.setBackgroundColor(oddColor);
                    }
                    
                    if(j == 0){
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    }else{
                    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    }
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    table.addCell(cell);
                }
            }

            document.add(table);

            // Petit tableau pour totals avec styles spéciaux pour certaines cellules
            PdfPTable totalsTable = new PdfPTable(2);
            totalsTable.setWidthPercentage(50);
            totalsTable.setHorizontalAlignment(Element.ALIGN_RIGHT);
            totalsTable.getDefaultCell().setBorder(PdfPCell.NO_BORDER);

            // Ajout des cellules avec styles spéciaux
            String[] labels = {"TOTAL:", "Frais:", "NET A PAYER:"};
            String[] values = {"350 €", "10 €", "360 €"};

            for (int i = 0; i < labels.length; i++) {
                cell = new PdfPCell(new Phrase(labels[i], (i == 0 || i == 2) ? boldWhiteFont : boldFont));
                if (i == 0 || i == 2) {  // Style spécial pour 'Montant' et 'Net à payer'
                    cell.setBackgroundColor(darkBlue);
                } else {
                    cell.setBorder(PdfPCell.NO_BORDER);
                }
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setFixedHeight(30);
                totalsTable.addCell(cell);

                cell = new PdfPCell(new Phrase(values[i], boldFont));
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setBorder(PdfPCell.NO_BORDER);
                if (i == 0 || i == 2) {
                    cell.setBackgroundColor(BaseColor.WHITE); // Blanc pour les valeurs
                }
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                totalsTable.addCell(cell);
            }

            document.add(totalsTable);
                        document.add(new Paragraph("\n\n"));

            PdfPTable paymentTable = new PdfPTable(2);
paymentTable.setWidthPercentage(100);
//paymentTable.setWidths(new float[]{2, 1});  // Configuration des largeurs relatives des colonnes

// Modalités et conditions de règlement avec bordure
PdfPCell paymentCell = new PdfPCell();
paymentCell.addElement(new Phrase("Modalités et condition de règlement:", boldFont));
paymentCell.addElement(new Phrase("Lorehu zfhyageyfdtafgt ygzd'eyhvbhaze bfda\nhzeyrfhyuegzrygf zyrefhuyerzf\n\nDate d'échéance: ...../..../....."));
paymentCell.setBorderColor(BaseColor.BLACK); // Définir la couleur de la bordure
paymentCell.setBorderWidth(1); // Définir l'épaisseur de la bordure
paymentCell.setPadding(10);
paymentTable.addCell(paymentCell);

// Case pour signature sans bordure
PdfPCell signatureCell = new PdfPCell(new Phrase("Signature"));
signatureCell.setHorizontalAlignment(Element.ALIGN_CENTER);
signatureCell.setBorder(PdfPCell.NO_BORDER);
paymentTable.addCell(signatureCell);

document.add(paymentTable);
//            Paragraph thanks = new Paragraph("Merci, lorem ipsum dolor sit amet.");
//            thanks.setAlignment(Element.ALIGN_CENTER);
//            document.add(thanks);

            document.close();
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

