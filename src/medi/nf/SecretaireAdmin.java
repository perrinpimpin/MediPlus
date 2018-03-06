/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medi.nf;

/**
 *
 * @author CRISTANTE
 */
public class SecretaireAdmin extends User{
    private String nom;
    private String prenom;
    private String id;
    private String mdp;
    private String username = prenom + "." + nom;
    private int telephone;
    
    public SecretaireAdmin(String nom, String prenom, int id, String mdp, String username, int telephone){
        super(id, nom, prenom, username, mdp, 2);
        this.telephone=telephone;
    }
}
