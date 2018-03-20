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
    private Date dateResultat;
    private Medecin prescripteur;
    private int iddm;
    private String res;
    private Date dateDemande;
    private Medecin mt;
    private String demande;
    private String idres;
    
    
    public Resultat(Date d, Medecin m, int iddm, String res, Date dd, Medecin mt, String demande, String idres){
        this.dateResultat = d;
        this.iddm = iddm;
        this.prescripteur = m;
        this.res = res;
        this.dateDemande=dd;
        this.mt = mt;
        this.demande = demande;
        this.idres=idres;
    }

    public Date getDateResultat() {
        return dateResultat;
    }

    public void setDateResultat(Date date) {
        this.dateResultat = date;
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

    /**
     * @return the dateDemande
     */
    public Date getDateDemande() {
        return dateDemande;
    }

    /**
     * @param dateDemande the dateDemande to set
     */
    public void setDateDemande(Date dateDemande) {
        this.dateDemande = dateDemande;
    }

    /**
     * @return the mt
     */
    public Medecin getMt() {
        return mt;
    }

    /**
     * @param mt the mt to set
     */
    public void setMt(Medecin mt) {
        this.mt = mt;
    }

    /**
     * @return the demande
     */
    public String getDemande() {
        return demande;
    }

    /**
     * @param demande the demande to set
     */
    public void setDemande(String demande) {
        this.demande = demande;
    }

    /**
     * @return the idres
     */
    public String getIdres() {
        return idres;
    }
    
}
