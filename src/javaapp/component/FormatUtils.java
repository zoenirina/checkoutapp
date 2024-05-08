/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapp.component;

import java.text.DecimalFormat;

/**
 *
 * @author ZOENIRINA
 */

public class FormatUtils {
    String formatType;
    public FormatUtils(String format){
        
    }

    public FormatUtils() {
    
    }
    
    public String formatFloat(float number) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        return decimalFormat.format(number);
    }

    public float parseFloat(String formattedNumber) {
        try {
            DecimalFormat decimalFormat = new DecimalFormat("#,###");
            Number parsedNumber = decimalFormat.parse(formattedNumber);
            return parsedNumber.floatValue();
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0f; // En cas d'erreur, retourne 0
        }
    }
}
