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
public abstract class User extends Object {
    private int id_user;
    private String nom;
    private String prenom;
    private String username;
    private String password;
    private int role;
    
    public User(int id_user,String nom, String prenom,String username,String password,int role){
        this.id_user=id_user;
        this.username=username;
        this.password=password;
        this.role=role;
        this.nom=nom;
        this.prenom=prenom;
    }
    

    /**
     * @return the id_user
     */
    public int getId_user() {
        return id_user;
    }

    /**
     * @return the nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * @return the prenom
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the role
     */
    public int getRole() {
        return role;
    }
}
