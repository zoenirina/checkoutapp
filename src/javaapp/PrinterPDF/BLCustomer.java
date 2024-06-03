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
import javaapp.bean.LivraisonDetail;
import javaapp.bean.Personne;
import javaapp.component.DateConverter;
import javaapp.component.FormatUtils;
import javaapp.component.Toast;
import javaapp.dao.CommandeDetailDAOImpl;
import javaapp.factory.DAOFactory;
import javax.swing.JPanel;

/**
 *
 * @author ASUS
 */
public class BLCustomer {
    public static void printPDF( JPanel pan, javaapp.bean.Livraison livraison, CommandeC comm ) {
        CommandeDetailDAOImpl commDetImplDAO = DAOFactory.getCommandeDetailDAOImpl();
        List <LivraisonDetail> livDets = DAOFactory.getLivraisonDetailDAO().select(livraison.getId());
        
        FormatUtils formater = new FormatUtils();
        Personne fournis = comm.getClient();
        String filePath = "C:/Users/ASUS/BL-"+DateConverter.getCurrentDHSM()+".pdf"; //chemin
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
            document.add( new Phrase("Bon de livraison n° "+livraison.getId(), new Font(Font.FontFamily.TIMES_ROMAN, 28,  Font.BOLD, new BaseColor( 102,102,255 ))));
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
            
            PdfPTable t = new PdfPTable(4);
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
            
            invoiceInfoCell0 = new PdfPCell();
            invoiceInfoCell0.addElement(new Phrase("Référence BL: ", boldBlue)); 
            invoiceInfoCell0.addElement(new Phrase(livraison.getRefLivraison())); 
            invoiceInfoCell0.setBorder(PdfPCell.NO_BORDER);
            t.addCell(invoiceInfoCell0);
            
            invoiceInfoCell2 = new PdfPCell();
            invoiceInfoCell2.addElement(new Phrase("Date prévue de livraison: ", boldBlue)); 
            invoiceInfoCell2.addElement(new Phrase(DateConverter.convertToDate(livraison.getDateLivraison()))); 
            invoiceInfoCell2.setBorder(PdfPCell.NO_BORDER);
            t.addCell(invoiceInfoCell2);
            
            // Ajout du tableau en haut du document
            document.add(t);

            // Ajouter un saut de ligne
            document.add(new Paragraph("\n\n"));

            PdfPTable table = new PdfPTable(8);
            table.setWidthPercentage(100);
            PdfPCell cell;

            String[] headers = {"Produit", "PU HT","TVA (%)","Rem. (%)", "Qté comm","Qté livrée","Qté colis","Total TTC"};
            for (String header : headers) {
                cell = new PdfPCell(new Phrase(header, boldWhiteFont));
                cell.setBackgroundColor(darkBlue);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_JUSTIFIED);
                cell.setBorderColor(darkGray);
                cell.setFixedHeight(30); 
                table.addCell(cell);
            }

            BaseColor evenColor = BaseColor.WHITE;  // Couleur pour lignes paires (blanc)
            BaseColor oddColor = new BaseColor(240, 240, 240); 
            for (int i = 0; i < livDets.size(); i++) {
            float tva = livDets.get(i).getProduit().getTVA();
            int qte = livDets.get(i).getCommandeDetail().getQuantite();
            float PUHT = livDets.get(i).getProduit().getPUHT();    
            float PUTTC = PUHT + (PUHT*tva)/100;  
            
            String[] data = {
                livDets.get(i).getProduit().getDesignation(),
                formater.formatFloat(livDets.get(i).getProduit().getPUHT()) + " Ar", 
                String.valueOf(tva),
                String.valueOf(livDets.get(i).getCommandeDetail().getRemise()),
                String.valueOf(qte),
                String.valueOf(commDetImplDAO.findById(livDets.get(i).getIdCommandeDetail()).getQuantiteLivree()),
//                String.valueOf(commDetImplDAO.findById(livDets.get(i).getIdCommandeDetail()).getQuantiteRestante()),
                String.valueOf(livDets.get(i).getQuantiteRecu()),
//                formater.formatFloat(PUHT*qte) + " Ar",
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
            
            for (int i = 0; i < livDets.size(); i++) {
            //                Facture.Item item = items.get(i);

            float tva = livDets.get(i).getProduit().getTVA();
            int qte = livDets.get(i).getCommandeDetail().getQuantite();
            float PUHT = livDets.get(i).getProduit().getPUHT();
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
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setFixedHeight(30);
                cell.setBorder(PdfPCell.NO_BORDER);
                totalsTable.addCell(cell);

                cell = new PdfPCell(new Phrase(values[i], boldFont));
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setBorder(PdfPCell.NO_BORDER);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                totalsTable.addCell(cell);
            }

            document.add(totalsTable);
                        document.add(new Paragraph("\n\n"));

            document.close();
            
            Toast toast = new Toast("Bon de livraison imprimée!", pan.getLocationOnScreen().x+pan.getWidth()-320, pan.getLocationOnScreen().y+7);
            toast.showtoast();
            
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }  
}
