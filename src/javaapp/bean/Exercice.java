package javaapp.bean;


public class Exercice {
    private int id;
    private boolean status;
    private String description;
    private int moisDebut;
    private int anneeDebut;
    private int moisFin;
    private int anneeFin;
    private float soldeInitiale;
    private String dateCreation;
    private int idCreateur;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public boolean isStatus() { return status; }
    public void setStatus(boolean status) { this.status = status; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getMoisDebut() { return moisDebut; }
    public void setMoisDebut(int moisDebut) { this.moisDebut = moisDebut; }

    public int getAnneeDebut() { return anneeDebut; }
    public void setAnneeDebut(int anneeDebut) { this.anneeDebut = anneeDebut; }

    public int getMoisFin() { return moisFin; }
    public void setMoisFin(int moisFin) { this.moisFin = moisFin; }

    public int getAnneeFin() { return anneeFin; }
    public void setAnneeFin(int anneeFin) { this.anneeFin = anneeFin; }

    public float getSoldeInitiale() { return soldeInitiale; }
    public void setSoldeInitiale(float soldeInitiale) { this.soldeInitiale = soldeInitiale; }

    public String getDateCreation() { return dateCreation; }
    public void setDateCreation(String dateCreation) { this.dateCreation = dateCreation; }

    public int getIdCreateur() { return idCreateur; }
    public void setIdCreateur(int idCreateur) { this.idCreateur = idCreateur; }
}
