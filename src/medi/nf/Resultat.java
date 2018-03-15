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
public class Resultat {
    private Date date;
    private Medecin prescripteur;
    private int iddm;
    private String res;
    
    public Resultat(Date d, Medecin m, int iddm, String res){
        this.date = d;
        this.iddm = iddm;
        this.prescripteur = m;
        this.res = res;
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

    public String getResultat() {
        return res;
    }

    public void setResultat(String obs) {
        this.res = obs;
    }
    
    // ajouter une prescription
    
    public void rajouterPres(String p){
        this.res += p;
    }
    
    public int getIddm() {
        return iddm;
    }
    
}
