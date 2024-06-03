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
public class Config {

    public Config(int idConfig, String DBuserName, String DBpassWord, String DBpath, String codeRecuperation, String DBName, String pathSavePDF, String devise) {
    this.idConfig = idConfig;
    this.DBuserName = DBuserName;
    this.DBpassWord = DBpassWord;
    this.DBpath = DBpath;
    this.codeRecuperation =codeRecuperation;
    this.DBName = DBName;
    this.pathSavePDF = pathSavePDF;
    this.devise = devise; 
    }

    public Config() {}

    public int getIdConfig() {
        return idConfig;
    }

    public void setIdConfig(int idConfig) {
        this.idConfig = idConfig;
    }

    public String getDBuserName() {
        return DBuserName;
    }
    
    public void setDBuserName(String DBuserName) {
        this.DBuserName = DBuserName;
    }

    public String getDBpassWord() {
        return DBpassWord;
    }

    public void setDBpassWord(String DBpassWord) {
        this.DBpassWord = DBpassWord;
    }

    public String getDBpath() {
        return DBpath;
    }

    public void setDBpath(String DBpath) {
        this.DBpath = DBpath;
    }

    public String getCodeRecuperation() {
        return codeRecuperation;
    }

    public void setCodeRecuperation(String codeRecuperation) {
        this.codeRecuperation = codeRecuperation;
    }

    public String getDBName() {
        return DBName;
    }

    public void setDBName(String DBName) {
        this.DBName = DBName;
    }

    public String getPathSavePDF() {
        return pathSavePDF;
    }

    public void setPathSavePDF(String pathSavePDF) {
        this.pathSavePDF = pathSavePDF;
    }

    public String getDevise() {
        return devise;
    }

    public void setDevise(String devise) {
        this.devise = devise;
    }
    private int idConfig = 0;
    private String DBuserName = "";
    private String DBpassWord = "";
    private String DBpath = "";
    private String codeRecuperation = "";
    private String DBName = "";
    private String pathSavePDF = "";
    private String devise = "";
    
}
