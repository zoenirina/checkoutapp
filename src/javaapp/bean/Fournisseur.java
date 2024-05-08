/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapp.bean;

/**
 *
 * @author ASUS
 */
public class Fournisseur extends Client{
    public Fournisseur(){
    super();
    }
    public Fournisseur(int id, String nom, String prenom, String email1, String email2, String tel1, String tel2, String  tel3, String  adresse, String  NIF, String  stat , String  status){
    super(id, nom, prenom, email1, email2, tel1, tel2,  tel3, adresse, NIF, stat , status);
    }

}
