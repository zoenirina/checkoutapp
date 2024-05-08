/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapp.bean;

/**
 *
 * @author ZOENIRINA
 */
public class Profile {

    public int getIdProfile() {
        return idProfile;
    }

    public void setIdProfile(int idProfile) {
        this.idProfile = idProfile;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getFonction() {
        return fonction;
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

    public String getDateNaiss() {
        return dateNaiss;
    }

    public void setDateNaiss(String dateNaiss) {
        this.dateNaiss = dateNaiss;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }
    
    private int idProfile = 0;
    private String nom = "";
    private String prenom = "";
    private String fonction = "";
    private String dateNaiss = "";
    private String adresse = "";
    private String tel = "";
    private int idUtilisateur = 0;
    
    public Profile(int idProfile, String nom, String prenom, String fonction, String dateNaiss, String adresse, String tel, int idUtilisateur) {
        this.idProfile = idProfile;
        this.nom = nom;
        this.prenom = prenom;
        this.fonction = fonction;
        this.dateNaiss = dateNaiss;
        this.adresse = adresse;
        this.tel = tel;
        this.idUtilisateur = idUtilisateur;
    }
    public Profile() {}
}
