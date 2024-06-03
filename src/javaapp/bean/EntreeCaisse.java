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
public class EntreeCaisse {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    public int getIdSourceReference() {
        return idSourceReference;
    }

    public void setIdSourceReference(int idSourceReference) {
        this.idSourceReference = idSourceReference;
    }

    public float getMontant() {
        return montant;
    }

    public void setMontant(float montant) {
        this.montant = montant;
    }

    public String getDevise() {
        return devise;
    }

    public void setDevise(String devise) {
        this.devise = devise;
    }

    public int getIdCreateur() {
        return idCreateur;
    }

    public void setIdCreateur(int idCreateur) {
        this.idCreateur = idCreateur;
    }

    public int getIdExercice() {
        return idExercice;
    }

    public void setIdExercice(int idExercice) {
        this.idExercice = idExercice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
        public String getDateEntree() {
        return dateEntree;
    }

    public void setDateEntree(String dateEntree) {
        this.dateEntree = dateEntree;
    }
    
    private int id = 0;
    private String dateEntree = "";
    private int idType = 0;
    private TypeMouvement typeMouvement ;
    private int idSourceReference = 0;
    private float montant = 0.0f;
    private String devise = "";
    private int idCreateur = 0;
    private User createur ;
    private int idExercice  = 0;
    private String description = "";

    
    public EntreeCaisse(int id, String dateEntree, int idType, int idSourceReference, float montant, String devise, int idCreateur, int idExercice, String description) {
        this.id = id;
        this.dateEntree = dateEntree;
        this.idType = idType;
        this.idSourceReference = idSourceReference;
        this.montant = montant;
        this.devise = devise;
        this.idCreateur = idCreateur;
        this.idExercice = idExercice;
        this.description = description;
    }
    
    public EntreeCaisse(){}

    public User getCreateur() {
        return createur;
    }

    public void setCreateur(User createur) {
        this.createur = createur;
    }

    public TypeMouvement getTypeMouvement() {
        return typeMouvement;
    }

    public void setTypeMouvement(TypeMouvement typeMouvement) {
        this.typeMouvement = typeMouvement;
    }

}
