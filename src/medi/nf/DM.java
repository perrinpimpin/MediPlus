/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medi.nf;

import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author Victor
 */
public class DM {

    /**
     * @param date the date to set
     */
    private int iddm;
    private Medecin medref;
    private String lettre;
    private Date date;
    private Patient p;
 private Lit l;

    //nb correspond au nombre de DM qu'il y a dans le DPI, il devra etre
    // entré en paramètre par le length de la liste contenue dans DPI 
    public DM(Patient p, Medecin medref, String lettre, int iddm, Date date,Lit l) {
        this.p = p;
        this.medref = medref;
        this.lettre = lettre;
        this.iddm = iddm;
        this.date = date;
        this.l = l;        
    }
    
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return the observation
     */

    public int getIddm() {
        return iddm;
    }

    public void setIddm(int iddm) {
        this.iddm = iddm;
    }

    public Medecin getMedref() {
        return medref;
    }

    public void setMedref(Medecin medref) {
        this.medref = medref;
    }



    public String getLet() {
        /*if (this.let == null || this.let.isEmpty()) {
            this.let = "Aucune lettre de sortie";
        }*/
        return lettre;
    }

    public void setLet(String lettre) {
        this.lettre = lettre;
    }

    public Patient getP() {
        return p;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }
    
      public Lit getLit() {
        return l;
    }

    public void setLit(Lit l) {
        this.l = l;
    }
    


}
