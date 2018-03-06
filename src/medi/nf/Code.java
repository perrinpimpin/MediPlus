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
public enum Code {
    CS("consultation au cabinet", 23.0),
    CSC("consultation cardiologie", 45.73),
    FP("forfait pediatrique", 5.0),
    KC("actes de chirurgie et de specialite", 2.09),
    KE("actes d'echographie, de doppler", 1.89),
    K("autres actes de specialite", 1.92),
    KFA("forfait A", 30.49),
    KFB("forfait B", 60.98),
    ORT("orthodontie", 2.15),
    PRO("prothese dentaire", 2.15);
    // attributs de l'enum :
    private String libelle;
    private double cout;
    
    

  private Code(String libelle, double cout) {
      this.cout=cout;
      this.libelle=libelle;
  }

    public String getLibelle() {
        return libelle;
    }


    public double getCout() {
        return cout;
    }

 

    
}
