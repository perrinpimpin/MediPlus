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
public class Prescription {
    private Date date;
    private Medecin prescripteur;
    private int iddm;
    private String prescription;
    
    public Prescription(Date d, Medecin m, int iddm, String prescription){
        this.date = d;
        this.iddm = iddm;
        this.prescripteur = m;
        this.prescription = prescription;
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

    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }
    
    // ajouter une prescription
    
    public void rajouterPres(String p){
        this.prescription += p;
    }
    
    public int getIddm() {
        return iddm;
    }
    
}
