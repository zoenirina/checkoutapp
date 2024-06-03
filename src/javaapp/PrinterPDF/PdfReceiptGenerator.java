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
import javaapp.bean.Facture;
import javaapp.bean.Livraison;
import javaapp.bean.LivraisonDetail;
import javaapp.bean.Personne;
import javaapp.component.DateConverter;
import javaapp.component.FormatUtils;
import javaapp.page.PageList.Client;

public class PdfReceiptGenerator {
//private Client client;   
    FormatUtils formater = new FormatUtils();
public PdfReceiptGenerator() {
   
    }
    public void printPDF( Facture fac, CommandeC comm,  Livraison liv ) {
        Personne client = comm.getClient();
        String filePath = "C:/Users/ASUS/FAC-"+DateConverter.getCurrentDHSM()+".pdf"; //chemin
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
            document.add( new Phrase("Facture n° "+fac.getId(), new Font(Font.FontFamily.TIMES_ROMAN, 28,  Font.BOLD, new BaseColor( 102,102,255 ))));
            document.add(new Paragraph("\n\n"));
            // Identification du vendeur
            PdfPCell vendorCell = new PdfPCell();
            vendorCell.addElement(new Phrase("Entreprise", boldBlue));
            vendorCell.addElement(new Phrase("Nom du Entreprise\nAdresse du Entreprise\nTel: 0123456789\nEmail: email@Entreprise.com"));//entreprise info  
            vendorCell.setBorder(PdfPCell.NO_BORDER);
            headerTable.addCell(vendorCell);

            // Identification du client
            PdfPCell clientCell = new PdfPCell();
            clientCell.addElement(new Phrase("Identification du Client", boldBlue));
            clientCell.addElement(new Phrase(
                    client.getNom()+" "+client.getPrenom() +
                    "\nAdresse: " + client.getAdresse() +
                    "\nTel: " + client.getTel1()+
                    "\nNIF: " + client.getNIF() + 
                    "\nStat: " + client.getStat() + "\n\n\n"));
            
            clientCell.setBorder(PdfPCell.NO_BORDER);
            headerTable.addCell(clientCell);
            
            document.add(headerTable);
            
            PdfPTable t = new PdfPTable(4);
            t.setWidthPercentage(100);
            // Ligne pour numéro de facture et date
            PdfPCell invoiceInfoCell0 = new PdfPCell();
            invoiceInfoCell0.addElement(new Phrase("Référence facture: ", boldBlue)); 
            invoiceInfoCell0.addElement(new Phrase(fac.getRefFacture())); 
            invoiceInfoCell0.setBorder(PdfPCell.NO_BORDER);
            t.addCell(invoiceInfoCell0);
            
            PdfPCell invoiceInfoCell = new PdfPCell();
            invoiceInfoCell.addElement(new Phrase("Date de facturation: ", boldBlue)); 
            invoiceInfoCell.addElement(new Phrase(DateConverter.convertToDate(fac.getDateFacture()))); 
            invoiceInfoCell.setBorder(PdfPCell.NO_BORDER);
            t.addCell(invoiceInfoCell);
            
            PdfPCell invoiceInfoCell1 = new PdfPCell();
            invoiceInfoCell1.addElement(new Phrase("Commande n° ", boldBlue));     
            invoiceInfoCell1.addElement(new Phrase(comm.getRefCommande())); 
            invoiceInfoCell1.setBorder(PdfPCell.NO_BORDER);
            t.addCell(invoiceInfoCell1);
            
            PdfPCell invoiceInfoCell2 = new PdfPCell();
            invoiceInfoCell2.addElement(new Phrase("Date de la commande: ", boldBlue)); 
            invoiceInfoCell2.addElement(new Phrase(DateConverter.convertToDate(comm.getDateCommande()))); 
            invoiceInfoCell2.setBorder(PdfPCell.NO_BORDER);
            t.addCell(invoiceInfoCell2);
            
            PdfPCell invoiceInfoCell3 = new PdfPCell();
            invoiceInfoCell3.addElement(new Phrase("Date de livraison: ", boldBlue)); 
            invoiceInfoCell3.addElement(new Phrase(DateConverter.convertToDate(liv.getDateLivraison()))); 
            invoiceInfoCell3.setBorder(PdfPCell.NO_BORDER);
            t.addCell(invoiceInfoCell3);
//            new Phrase("Commande n°"+comm.getId()+" #"+comm.getRefCommande()+
//                    " \nDate de la commande: "+DateConverter.convertToDate(comm.getDateCommande())+
//                    " \n\nDate de livraison: "+DateConverter.convertToDate(liv.getDateLivraison())
//                    )); // info sur client
//            invoiceInfoCell.setColspan(2);
//            invoiceInfoCell.setBorder(PdfPCell.NO_BORDER);
//            headerTable.addCell(invoiceInfoCell);
            
            // Ajout du tableau en haut du document
            document.add(t);

            // Ajouter un saut de ligne
            document.add(new Paragraph("\n\n"));

            PdfPTable table = new PdfPTable(7);
            table.setWidthPercentage(100);
            PdfPCell cell;

            String[] headers = {"Produit", "Quantité", "PU HT","TVA (%)", "Remise (%)","Total HT","Total TTC"};
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
            
            List<LivraisonDetail> livDet = liv.getLivraisonDetails();
            for (int i = 0; i < livDet.size(); i++) {
            float tva = livDet.get(i).getProduit().getTVA();
            int qte = livDet.get(i).getQuantiteRecu();
            float PUHT = livDet.get(i).getProduit().getPUHT();
            float remise = livDet.get(i).getCommandeDetail().getRemise();
            float PUHTremise = PUHT - (PUHT*remise)/100;    
            float PUTTC = PUHTremise + (PUHTremise*tva)/100;  
            
                String[] data = {
                    livDet.get(i).getProduit().getDesignation(),
                    String.valueOf(qte), 
                    formater.formatFloat(livDet.get(i).getProduit().getPUHT()) + " Ar", 
                    String.valueOf(tva),
                    String.valueOf(livDet.get(i).getCommandeDetail().getRemise()),
                    formater.formatFloat(PUHTremise*qte) + " Ar",
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
            String[] labels = {"TOTAL HT:", "FRAIS:","TOTAL TTC:", "NET A PAYER:"};
            
            float ttHT= 0.0f;
            float ttREMISE= 0.0f;
            float ttTTC= 0.0f;
            
            for (int i = 0; i < livDet.size(); i++) {
            //                Facture.Item item = items.get(i);

            float tva = livDet.get(i).getProduit().getTVA();
            int qte = livDet.get(i).getQuantiteRecu();
            float PUHT = livDet.get(i).getProduit().getPUHT();
            float remise = livDet.get(i).getCommandeDetail().getRemise();
            float PUHTremise = PUHT - (PUHT*remise)/100;    
            float PUTTC = PUHTremise + (PUHTremise*tva)/100;
            
            ttHT += (PUHTremise*qte);
            ttTTC += (PUTTC*qte);
            }
            
            String[] values = {
                formater.formatFloat(ttHT),
                String.valueOf(liv.getFrais()), 
                formater.formatFloat(ttTTC),
                formater.formatFloat(ttTTC+liv.getFrais())
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

            PdfPTable paymentTable = new PdfPTable(2);
            paymentTable.setWidthPercentage(100);
            //paymentTable.setWidths(new float[]{2, 1});  // Configuration des largeurs relatives des colonnes

            // Modalités et conditions de règlement avec bordure
            PdfPCell paymentCell = new PdfPCell();
            paymentCell.addElement(new Phrase("Modalités et condition de règlement:", boldFont));
            paymentCell.addElement(new Phrase("Lorem ipsum dolor sit amet consectetur adipisicing elit. Repellendus animi quam voluptatum qui alias."
                    + "\n\nDate d'échéance: ...../..../....."));
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
            document.close();
            
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

