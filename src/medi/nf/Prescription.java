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
    private Patient patient;
    private String prescription;
    
    public Prescription(Date d, Medecin m, Patient p){
        this.date = d;
        this.patient = p;
        this.prescripteur = m;
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

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
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
    
}
