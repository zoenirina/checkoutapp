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
public class Personne {
    protected int id = 0;
    protected String nom = "";
    protected String prenom = "";
    protected String email1= "";
    protected String email2 = "";
    protected String tel1 = "";
    protected String tel2 = "";
    protected String tel3 = "";
    protected String adresse= "";
    protected String NIF = "";
    protected String stat = "";
    protected String status = "";
    
     
  public Personne(int id, String nom, String prenom, String email1, String email2, String tel1, String tel2, String  tel3, String  adresse, String  NIF, String  stat , String  status) {
    this.id = id;
    this.nom = nom;
    this.prenom = prenom;
    this.email1 = email1;
    this.email2 = email2;
    this.tel1 = tel1;
    this.tel2 = tel2;
    this.tel3 = tel3;
    this.adresse = adresse;
    this.NIF = NIF;
    this.stat = stat;
    this.status = status;
  }
  
  public Personne(){};
    public Personne(int id){this.id= id;};
      
  public int getId() {
    return id;
  }
 
  public void setId(int id) {
    this.id = id;
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
  
    public String getEmail1() {
        return email1;
    }

    public void setEmail1(String email1) {
        this.email1 = email1;
    }

    public String getEmail2() {
        return email2;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    public String getTel1() {
        return tel1;
    }

    public void setTel1(String tel1) {
        this.tel1 = tel1;
    }

    public String getTel2() {
        return tel2;
    }

    public void setTel2(String tel2) {
        this.tel2 = tel2;
    }

    public String getTel3() {
        return tel3;
    }

    public void setTel3(String tel3) {
        this.tel3 = tel3;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getNIF() {
        return NIF;
    }

    public void setNIF(String NIF) {
        this.NIF = NIF;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
