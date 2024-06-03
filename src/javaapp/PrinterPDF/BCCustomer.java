/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapp.PrinterPDF;

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
import java.util.List;
import javaapp.bean.CommandeC;
import javaapp.bean.CommandeDetail;
import javaapp.bean.Personne;
import javaapp.component.DateConverter;
import javaapp.component.FormatUtils;
import javaapp.component.Toast;
import javax.swing.JPanel;

/**
 *
 * @author ASUS
 */
public class BCCustomer {
          public static void printPDF( JPanel pan, CommandeC comm ) {
            
        FormatUtils formater = new FormatUtils();
        Personne fournis = comm.getClient();
        String filePath = "C:/Users/ASUS/BC-"+DateConverter.getCurrentDHSM()+".pdf"; //chemin
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            Font boldFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            Font boldWhiteFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE);
            BaseColor darkBlue = new BaseColor(102,102,255); // Custom dark blue color #000d1a
            BaseColor darkGray = new BaseColor( 248,248,248); // Dark gray for borders
//            BaseColor boldBlue = new BaseColor(48,4,114);
            Font boldBlue = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, new BaseColor(102,102,255));
            
            PdfPTable headerTable = new PdfPTable(2);
            headerTable.setWidthPercentage(100);
            headerTable.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
            document.add( new Phrase("Bon de commande n° "+comm.getId(), new Font(Font.FontFamily.TIMES_ROMAN, 28,  Font.BOLD, new BaseColor( 102,102,255 ))));
            document.add(new Paragraph("\n\n"));
            // Identification du vendeur
            PdfPCell vendorCell = new PdfPCell();
            vendorCell.addElement(new Phrase("Entreprise", boldBlue));
            vendorCell.addElement(new Phrase("Nom du Entreprise\nAdresse du Entreprise\nTel: 0123456789\nEmail: email@Entreprise.com"));//entreprise info  
            vendorCell.setBorder(PdfPCell.NO_BORDER);
            headerTable.addCell(vendorCell);

            // Identification du client
            PdfPCell clientCell = new PdfPCell();
            clientCell.addElement(new Phrase("Client", boldBlue));
            clientCell.addElement(new Phrase(
                    fournis.getNom()+" "+fournis.getPrenom() +
                    "\nAdresse: " + fournis.getAdresse() +
                    "\nTel: " + fournis.getTel1()+
                    "\nNIF: " + fournis.getNIF() + 
                    "\nStat: " + fournis.getStat() + "\n\n\n"));
            
            clientCell.setBorder(PdfPCell.NO_BORDER);
            headerTable.addCell(clientCell);
            
            document.add(headerTable);
            
            PdfPTable t = new PdfPTable(3);
            t.setWidthPercentage(100);
            // Ligne pour numéro de facture et date
            PdfPCell invoiceInfoCell0 = new PdfPCell();
            invoiceInfoCell0.addElement(new Phrase("Référence: ", boldBlue)); 
            invoiceInfoCell0.addElement(new Phrase(comm.getRefCommande())); 
            invoiceInfoCell0.setBorder(PdfPCell.NO_BORDER);
            t.addCell(invoiceInfoCell0);
            
            PdfPCell invoiceInfoCell2 = new PdfPCell();
            invoiceInfoCell2.addElement(new Phrase("Date de la commande: ", boldBlue)); 
            invoiceInfoCell2.addElement(new Phrase(DateConverter.convertToDate(comm.getDateCommande()))); 
            invoiceInfoCell2.setBorder(PdfPCell.NO_BORDER);
            t.addCell(invoiceInfoCell2);
            
            PdfPCell invoiceInfoCell3 = new PdfPCell();
//            invoiceInfoCell3.addElement(new Phrase("Date de livraison: ", boldBlue)); 
//            invoiceInfoCell3.addElement(new Phrase(DateConverter.convertToDate(comm.()))); 
            invoiceInfoCell3.setBorder(PdfPCell.NO_BORDER);
            t.addCell(invoiceInfoCell3);
            
            // Ajout du tableau en haut du document
            document.add(t);

            // Ajouter un saut de ligne
            document.add(new Paragraph("\n\n"));

            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(100);
            PdfPCell cell;

            String[] headers = {"Produit", "Quantité", "PU HT","TVA (%)","Total HT","Total TTC"};
            for (String header : headers) {
                cell = new PdfPCell(new Phrase(header, boldWhiteFont));
                cell.setBackgroundColor(darkBlue);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_JUSTIFIED);
                cell.setBorderColor(darkGray);
                cell.setFixedHeight(30); // Setting height of header rows
                table.addCell(cell);
            }

            BaseColor evenColor = BaseColor.WHITE;  // Couleur pour lignes paires (blanc)
            BaseColor oddColor = new BaseColor(240, 240, 240); // Gris clair pour lignes impaires
            
            List<CommandeDetail> recepDet = comm.getCommandeDetails();
            for (int i = 0; i < recepDet.size(); i++) {
            float tva = recepDet.get(i).getProduit().getTVA();
            int qte = recepDet.get(i).getQuantite();
            float PUHT = recepDet.get(i).getProduit().getPUHT();
//            float remise = recepDet.get(i).getCommandeDetail().getRemise();
//            float PUHTremise = PUHT - (PUHT*remise)/100;    
            float PUTTC = PUHT + (PUHT*tva)/100;  
            
                String[] data = {
                    recepDet.get(i).getProduit().getDesignation(),
                    String.valueOf(qte), 
                    formater.formatFloat(recepDet.get(i).getProduit().getPUHT()) + " Ar", 
                    String.valueOf(tva),
//                    String.valueOf(recepDet.get(i).getCommandeDetail().getRemise()),
                    formater.formatFloat(PUHT*qte) + " Ar",
                    formater.formatFloat(PUTTC*qte) + " Ar"
                };
                
                for (int j = 0; j < data.length; j++) {
                    cell = new PdfPCell(new Phrase(data[j]));
                    cell.setBorderColor(darkGray); // Dark gray borders
                    cell.setFixedHeight(30); // Setting height of data rows
                    if (i % 2 == 0) {
                        cell.setBackgroundColor(evenColor);
                    } else {
                        cell.setBackgroundColor(oddColor);
                    }
                    if(j == 0){
                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    } else {
                        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    }
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    table.addCell(cell);
                }
            }

            document.add(table);
            document.add(new Paragraph("\n"));
            // Petit tableau pour totals avec styles spéciaux pour certaines cellules
            PdfPTable totalsTable = new PdfPTable(2);
            totalsTable.setWidthPercentage(50);
            totalsTable.setHorizontalAlignment(Element.ALIGN_RIGHT);
            totalsTable.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
            
            
            // Ajout des cellules avec styles spéciaux
            String[] labels = {"TOTAL HT:", "TOTAL TTC:"};
            
            float ttHT= 0.0f;
            float ttREMISE= 0.0f;
            float ttTTC= 0.0f;
            
            for (int i = 0; i < recepDet.size(); i++) {
            //                Facture.Item item = items.get(i);

            float tva = recepDet.get(i).getProduit().getTVA();
            int qte = recepDet.get(i).getQuantite();
            float PUHT = recepDet.get(i).getProduit().getPUHT();
//            float remise = recepDet.get(i).getCommandeDetail().getRemise();
//            float PUHTremise = PUHT - (PUHT*remise)/100;    
            float PUTTC = PUHT + (PUHT*tva)/100;
            
            ttHT += (PUHT*qte);
            ttTTC += (PUTTC*qte);
            }
            
            String[] values = {
                formater.formatFloat(ttHT),
//                String.valueOf(recep.getFrais()), 
                formater.formatFloat(ttTTC),
                formater.formatFloat(ttTTC)
            };
            for (int i = 0; i < labels.length; i++) {
                cell = new PdfPCell(new Phrase(labels[i],  boldFont));
//                if ( i == 3) {  // Style spécial pour 'Montant' et 'Net à payer'
//                    cell.setBackgroundColor(darkBlue);
//                } else {
//                    cell.setBorder(PdfPCell.NO_BORDER);
//                }
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setFixedHeight(30);
                cell.setBorder(PdfPCell.NO_BORDER);
                totalsTable.addCell(cell);

                cell = new PdfPCell(new Phrase(values[i], boldFont));
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setBorder(PdfPCell.NO_BORDER);
//                if (i == 3) {
//                    cell.setBackgroundColor(BaseColor.WHITE); // Blanc pour les valeurs
//                }
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                totalsTable.addCell(cell);
            }

            document.add(totalsTable);
                        document.add(new Paragraph("\n\n"));

            document.close();
            
            Toast toast = new Toast("Bon de commandé imprimée!", pan.getLocationOnScreen().x+pan.getWidth()-320, pan.getLocationOnScreen().y+7);
            toast.showtoast();
            
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }  
}
