/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medi.nf;

import java.sql.Date;
import java.util.ArrayList;
import medi.nf.Acte;
import medi.nf.Medecin;
import medi.nf.Patient;

/**
 *
 * @author Victor
 */
public class DM {
    
    
    private int ipp;
    private int iddm;
    private Patient pat;
    private Medecin medref;
    private ArrayList<Acte> actes;
    private String let;
    private Date date;
    
    private ArrayList<String> observation;
    private ArrayList<String> resultats;
    private ArrayList<String> pres;
    private ArrayList<String> opInf;
    
    
    //nb correspond au nombre de DM qu'il y a dans le DPI, il devra etre
   // entré en paramètre par le length de la liste contenue dans DPI 

    
     public DM(){
            this.observation = new ArrayList();
        this.pres = new ArrayList();
        this.resultats = new ArrayList();
        this.opInf = new ArrayList();
        
    }
     
     

    public int getIddm() {
        return iddm;
    }

    public void setIddm(int iddm) {
        this.iddm = iddm;
    }

    public Patient getPat() {
        return pat;
    }

    public void setPat(Patient pat) {
        this.pat = pat;
    }

    public Medecin getMedref() {
        return medref;
    }

    public void setMedref(Medecin medref) {
        this.medref = medref;
    }

    public ArrayList<Acte> getActes() {
        return actes;
    }

    public void setActes(ArrayList<Acte> actes) {
        this.actes = actes;
    }

    public String getLet() {
        if (this.let == null || this.let.isEmpty()){
            this.let = "Aucune lettre de sortie";
        }
        return let;
    }

    public void setLet(String let) {
        this.let = let;
    }
    
    // ajouter un acte
    
    public void addActe(Acte acte){
        this.actes.add(acte);
    }
    
    //Ajouter une observation ou un resultat
    
    public void ajouterObs(String obs){
        if(!obs.isEmpty())
        this.observation.add(obs);
    }
    
    public void ajouterRes(String res){
        if(!res.isEmpty())
        this.resultats.add(res);
    }

    public int getIpp() {
        return ipp;
    }

    public void setIpp(int ipp) {
        this.ipp = ipp;
    }
    
    
    
    public String getObs(){
        String s = "";
        for(String i : this.observation){
        s +="\n"+"-------------"+"\n"+ i+"\n"+"-------------"+"\n";
                }
        if (s == ""){
            s = "Aucune observation";
        }
        return s;
        
    }
    public String getRes(){
        String s = "";
        for(String i : this.resultats){
        s += "\n"+"-------------"+"\n"+i+"\n"+"-------------"+"\n";
                }
        if (s.isEmpty()){
            s = "Aucun résulat";
        }
        return s;
    }
    public String getopInf(){
        String s = "";
        for(String i : this.opInf){
        s += "\n"+"-------------"+"\n"+i +"\n"+"-------------"+"\n";
                }
        if (s.isEmpty()){
            s = "\n"+"-------------"+"\n"+"Aucune opération infirmière" +"\n"+"-------------"+"\n";
        }
        return s;
    }
        public String getPres(){
        String s = "";
        for(String i : this.pres){
        s += "\n"+"-------------"+"\n"+i+"\n"+"-------------"+"\n";
                }
        if (s.isEmpty()){
            s = "Aucune prescription";
        }
        
        
        return s;
        
    }
   
    

    
    // Ajouter une prescription$
    
    public void ajouterPrescription(String prescri){
        if(!prescri.isEmpty())
        this.pres.add(prescri);
    }
    
    public void ajouterOpInf(String opInf){
        if(!opInf.isEmpty())
        this.opInf.add(opInf);
    }
    
    
    public String afficherDM(){
        String s = "---------DM------------"
                +"IPP : "+this.getIpp()+"\n"
                + "id _ dm : "+this.getIddm() + "\n"
                + "Observations : "+this.getObs()+"\n"
                + "Prescriptions : "+this.getPres()+"\n"
                + "Resultats : "+this.getRes()+"\n"
                + "Operations Infirmieres"+this.getopInf()+"\n"
                + "--------------FIN DU DM-------------";
        
        return s;
    }
    
    
    
}
