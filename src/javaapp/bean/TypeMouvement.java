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
    public class TypeMouvement {
    private int codeType;
    private String type;
    private String description;

    // Constructeurs
    public TypeMouvement() {}

    public TypeMouvement(int codeType, String type, String description) {
        this.codeType = codeType;
        this.type = type;
        this.description = description;
    }

    // Getters et Setters
    public int getCodeType() {
        return codeType;
    }

    public void setCodeType(int codeType) {
        this.codeType = codeType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}


