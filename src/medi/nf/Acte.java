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
public class Acte {
    private Code code;
    private int coeff;
    private String nom;
    private double cout;
    
    public Acte(Code code, int coeff){
        this.nom = code.getLibelle();
        this.code = code;
        this.cout = code.getCout();
        this.coeff = coeff;
    }

    public Code getCode() {
        return code;
    }

    public void setCode(Code code) {
        this.code = code;
    }

    public int getCoeff() {
        return coeff;
    }

    public void setCoeff(int coeff) {
        this.coeff = coeff;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getCout() {
        return cout;
    }

    public void setCout(double cout) {
        this.cout = cout;
    }
    
    
    
    
}
