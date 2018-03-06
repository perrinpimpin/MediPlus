/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medi.nf;

import java.util.ArrayList;

/**
 *
 * @author victor
 */
public class DPI {
    private ArrayList<DM> dossiermeicaux;
    private Patient pat;
    public DPI(Patient pat){
        this.pat = pat;
    }
    
    //Ajout d'un dossier m�dical au dossier patient
    
    public void addDM(DM dm){
        this.dossiermeicaux.add(dm);
    }

    public ArrayList<DM> getDossiermeicaux() {
        return dossiermeicaux;
    }

    public void setDossiermeicaux(ArrayList<DM> dossiermeicaux) {
        this.dossiermeicaux = dossiermeicaux;
    }

    public Patient getPat() {
        return pat;
    }

    public void setPat(Patient pat) {
        this.pat = pat;
    }
    
    
    
}
