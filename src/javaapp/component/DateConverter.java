/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapp.component;

/**
 *
 * @author ASUS
 */
import com.toedter.calendar.JDateChooser;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;

public class DateConverter {

    public static String convertToLetter(String dateStr) {
        if(!dateStr.isEmpty()){
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = formatter.parse(dateStr);
            SimpleDateFormat letterFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.FRENCH);
            return letterFormat.format(date);
        } catch (java.text.ParseException e) {
            
        }
        }
        return "Aucun";
    }
    
    public static String convertToDateHour(String dateStr) {
//        if(!dateStr.isEmpty()){
//        try {
//            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            Date date = formatter.parse(dateStr);
//            SimpleDateFormat letterFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.FRENCH);
//            return letterFormat.format(date);
//        } catch (java.text.ParseException e) {
//            
//        }
//        }
        return convertToLetter(dateStr)+" "+ convertToHour(dateStr);
    }
    
    public static String convertToDate(String dateStr) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = formatter.parse(dateStr);
            SimpleDateFormat letterFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            return letterFormat.format(date);
        } catch (java.text.ParseException e) {
            return "Format de date invalide";
        }
    }
    
        public static String convertToHour(String dateStr) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = formatter.parse(dateStr);
            SimpleDateFormat letterFormat = new SimpleDateFormat("HH:mm");
            return letterFormat.format(date);
        } catch (java.text.ParseException e) {
            return "Format de date invalide";
        }
    }
        public static void initializeDate(JDateChooser dateChooser, JComboBox<String> hourComboBox, JComboBox<String> minuteComboBox, String dateTime) throws ParseException{
//        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setBounds(20, 20, 200, 30);
        Calendar cal = Calendar.getInstance();
        

        // Créer et configurer le premier JComboBox pour l'heure
//        JComboBox<String> hourComboBox = new JComboBox<>();
        hourComboBox.setBounds(20, 60, 80, 30);
        for (int i = 0; i < 24; i++) {
            hourComboBox.addItem(String.format("%02d", i));
        }
        
//        frame.add(hourComboBox);

        // Créer et configurer le deuxième JComboBox pour les minutes
//        JComboBox<String> minuteComboBox = new JComboBox<>();
        minuteComboBox.setBounds(120, 60, 80, 30);
        for (int i = 0; i < 60; i++) {
            minuteComboBox.addItem(String.format("%02d", i));
        }
        
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        if(dateTime != null){
            Date date = formatter.parse(dateTime);
            dateChooser.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateTime));
            hourComboBox.setSelectedItem(new SimpleDateFormat("HH").format(date));
            minuteComboBox.setSelectedItem(new SimpleDateFormat("mm").format(date));
        }else{
            dateChooser.setDate(cal.getTime());
            hourComboBox.setSelectedItem(String.format("%02d", cal.get(Calendar.HOUR_OF_DAY)));
            minuteComboBox.setSelectedItem(String.format("%02d", cal.get(Calendar.MINUTE)));
        }
        }
        
        public static String getCurrentDate(){
            return Calendar.getInstance().get(Calendar.YEAR)+"-"+String.format("%02d", Calendar.getInstance().get(Calendar.MONTH))+"-";
        }
         public static String getCurrentDHSM(){
            return Calendar.getInstance().get(Calendar.YEAR)+""+
                    Calendar.getInstance().get(Calendar.MONTH)+
                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH)+
                    Calendar.getInstance().get(Calendar.HOUR) +
                    Calendar.getInstance().get(Calendar.MINUTE) +
                    Calendar.getInstance().get(Calendar.MILLISECOND) 
                    ;
        }
         
        public static String getInputDate(JDateChooser dateChooser, JComboBox<String> hourComboBox, JComboBox<String> minuteComboBox) throws ParseException{

        java.util.Date date = dateChooser.getDate();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int heure =  Integer.parseInt(hourComboBox.getSelectedItem().toString());
            int minute = Integer.parseInt(minuteComboBox.getSelectedItem().toString());
            calendar.set(Calendar.HOUR_OF_DAY, heure);
            calendar.set(Calendar.MINUTE, minute);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            
            return dateFormat.format(calendar.getTime());
          
        }
        
        public static String getMonth(int i){
            String j = String.format("%02d", i);
            SimpleDateFormat formatter = new SimpleDateFormat("MM");
            Date date = null;
            try {
                date = formatter.parse(j);
            } catch (ParseException ex) {
                Logger.getLogger(DateConverter.class.getName()).log(Level.SEVERE, null, ex);
            }
            SimpleDateFormat letterFormat = new SimpleDateFormat("MMMM", Locale.FRENCH);
            return letterFormat.format(date);
//        return null;
        }
}