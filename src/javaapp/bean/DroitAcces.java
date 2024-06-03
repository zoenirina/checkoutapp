/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapp.bean;

public class DroitAcces {
    private int id;
    private String niveauDroit;

    // Constructors
    public DroitAcces() {}

    public DroitAcces(int id, String niveauDroit) {
        this.id = id;
        this.niveauDroit = niveauDroit;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNiveauDroit() {
        return niveauDroit;
    }

    public void setNiveauDroit(String niveauDroit) {
        this.niveauDroit = niveauDroit;
    }
}

