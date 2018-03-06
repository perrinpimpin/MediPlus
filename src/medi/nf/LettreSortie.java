/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medi.nf;

/**
 *
 * @author Victor
 */
public class LettreSortie {
    
    private Medecin medref;
    private String resultat;
    
    public LettreSortie(Medecin med, String res){
        this.medref = med;
        this.resultat = res;
    }

    public Medecin getMedref() {
        return medref;
    }

    public void setMedref(Medecin medref) {
        this.medref = medref;
    }

    public String getResultat() {
        return resultat;
    }

    public void setResultat(String resultat) {
        this.resultat = resultat;
    }
    
    
}
