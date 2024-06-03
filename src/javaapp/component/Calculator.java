/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapp.component;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class Calculator {
    private static float puht = 0.0f;
    private static float remise= 0.0f;
    private static float tva= 0.0f;
    private static int qte= 0;
    private static float totalHTAfterRemise = 0.0f;
        private static float tvaAmount = 0.0f;
        private static float totalTTCPerItem  = 0.0f;
//    public Calculator(float puht, float remise, float tva, int qte){
//        this.puht=puht;
//        this.remise=remise;Calculator
//        this.tva=tva;
//        this.qte=qte;
//        
//        float totalHTPerItem = puht * qte;
//        float remiseAmount = totalHTPerItem * (remise / 100);
//         this.totalHTAfterRemise = totalHTPerItem - remiseAmount;
//         this.tvaAmount = totalHTAfterRemise * (tva / 100);
//         this.totalTTCPerItem = totalHTAfterRemise + tvaAmount;
//    }
//    
    public static float getTotalTTC(float puht, float remise, float tva, int qte){
        float totalHTPerItem = puht * qte;
        float remiseAmount = totalHTPerItem * (remise / 100);
        float totalHTAfterRemise = totalHTPerItem - remiseAmount;
        float tvaAmount = totalHTAfterRemise * (tva / 100);
        float totalTTCPerItem = totalHTAfterRemise + tvaAmount;
        return  totalTTCPerItem*qte;
    
    }
        public static float getTotalHT(float puht, float remise, float tva, int qte){
        float totalHTPerItem = puht * qte;
        float remiseAmount = totalHTPerItem * (remise / 100);
        float totalHTAfterRemise = totalHTPerItem - remiseAmount;
        return  totalHTAfterRemise*qte;
    }
    public static float getTotalHT(){
    return  totalHTAfterRemise*qte;
    }
    public static float getTotalTTC(){
        
        return 0;
    
    }
}
