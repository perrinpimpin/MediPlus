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
public class Observation {
    private Date date;
    private Medecin prescripteur;
    private int iddm;
    private String obs;
    
    public Observation(Date d, Medecin m, int iddm, String obs){
        this.date = d;
        this.iddm = iddm;
        this.prescripteur = m;
        this.obs = obs;
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

    public String getObservation() {
        return obs;
    }

    public void setObservation(String obs) {
        this.obs = obs;
    }
    
    // ajouter une prescription
    
    public void rajouterPres(String p){
        this.obs += p;
    }
    
    public int getIddm() {
        return iddm;
    }
    
}
