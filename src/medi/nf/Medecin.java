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
public class Medecin extends User {

    private String nom;
    private String prenom;
    private String id;
    private String mdp;
    private int telephone;
    private String specialite;
    private String service;

    public Medecin(String nom, String prenom, int id, String username, String mdp, int telephone, String specialite, String service) {
        super(id, nom, prenom, username, mdp, 1);
        this.service = service;
        this.telephone = telephone;
        this.specialite = specialite;
    }

    public int getTelephone() {
        return telephone;
    }

    public void setTelephone(int telephone) {
        this.telephone = telephone;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }


    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

}
