/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medi.nf;

/**
 *
 * @author victor
 */
public class Lit {
    private String num;
    private int chambre;
    private boolean fenetre;
    
    public Lit(String n, int c, boolean f){
        this.num = n;
        this.chambre = c;
        this.fenetre= f;
    }

    public String getNum() {
        return num;
    }

    public void String(String num) {
        this.num = num;
    }

    public int getChambre() {
        return chambre;
    }

    public void setChambre(int chambre) {
        this.chambre = chambre;
    }

    public boolean isFenetre() {
        return fenetre;
    }

    public void setFenetre(boolean fenetre) {
        this.fenetre = fenetre;
    }
    
    
    
    
    
}
