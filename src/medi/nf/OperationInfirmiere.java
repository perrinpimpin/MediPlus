/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medi.nf;

import java.sql.Date;

/**
 *
 * @author victor
 */
public class OperationInfirmiere {
    private Date date;
    private Medecin prescripteur;
    private int iddm;
    private String opinf;
    
    public OperationInfirmiere(Date d, Medecin m, int iddm, String opinf){
        this.date = d;
        this.iddm = iddm;
        this.prescripteur = m;
        this.opinf = opinf;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Medecin getPrescripteur() {
        return prescripteur;
    }

    public void setPrescripteur(Medecin prescripteur) {
        this.prescripteur = prescripteur;
    }

    public String getOperationInfirmiere() {
        return opinf;
    }

    public void setOperationInfirmiere(String obs) {
        this.opinf = obs;
    }
    
    // ajouter une prescription
    
    public void rajouterPres(String p){
        this.opinf += p;
    }
    
    public int getIddm() {
        return iddm;
    }
    
}
