/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medi.nf;

import java.sql.Date;

/**
 *
 * @author Victor
 */
public class Patient {

    private String nom;
    private String prenom;
    private Date dateNaissance;
    private int ipp;
    private String lieuNaissance;
    private String sexe;
    private int numeroVoie;
    private String typeVoie;
    private String complement;
    private int codePostal;
    private String ville;
    private String pays;
    private int tel;
    private int fixe;
    private String mail;
    // l'ID sera le numero du patient, soit le nb
    // de patient deja enregistr�, comme ca aucun n'aura
    // le meme id

        public Patient(String nom, String prenom, Date date) {
        this.dateNaissance = date;
        this.nom = nom;
        this.prenom = prenom;
    }
    
    public Patient(String nom, String prenom, int id, Date date, String lieuNaissance, String sexe) {
        this.dateNaissance = date;
        this.ipp = id;
        this.nom = nom;
        this.prenom = prenom;
        this.lieuNaissance = lieuNaissance;
        this.sexe = sexe;
    }

    public Patient(String nom, String prenom, int id, Date date, String lieuNaissance, String sexe, int numeroVoie, String typeVoie, String complement, int codePostal, String ville, String pays, int tel, int fixe, String mail) {
        this.dateNaissance = date;
        this.ipp = id;
        this.nom = nom;
        this.prenom = prenom;
        this.lieuNaissance = lieuNaissance;
        this.sexe = sexe;
        this.numeroVoie = numeroVoie;
        this.typeVoie = typeVoie;
        this.complement = complement;
        this.codePostal = codePostal;
        this.ville = ville;
        this.pays = pays;
        this.tel = tel;
        this.fixe = fixe;
        this.mail = mail;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Date getDate() {
        return dateNaissance;
    }

    public void setDate(Date date) {
        this.dateNaissance = date;
    }

    public int getIPP() {
        return ipp;
    }

    public void getIPP(int ipp) {
        this.ipp = ipp;
    }

    /**
     * @return the lieuNaissance
     */
    public String getLieuNaissance() {
        return lieuNaissance;
    }

    /**
     * @param lieuNaissance the lieuNaissance to set
     */
    public void setLieuNaissance(String lieuNaissance) {
        this.lieuNaissance = lieuNaissance;
    }

    /**
     * @return the sexe
     */
    public String getSexe() {
        return sexe;
    }

    /**
     * @param sexe the sexe to set
     */
    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    /**
     * @return the numeroVoie
     */
    public int getNumeroVoie() {
        return numeroVoie;
    }

    /**
     * @param numeroVoie the numeroVoie to set
     */
    public void setNumeroVoie(int numeroVoie) {
        this.numeroVoie = numeroVoie;
    }

    /**
     * @return the typeVoie
     */
    public String getTypeVoie() {
        return typeVoie;
    }

    /**
     * @param typeVoie the typeVoie to set
     */
    public void setTypeVoie(String typeVoie) {
        this.typeVoie = typeVoie;
    }

    /**
     * @return the complement
     */
    public String getComplement() {
        return complement;
    }

    /**
     * @param complement the complement to set
     */
    public void setComplement(String complement) {
        this.complement = complement;
    }

    /**
     * @return the codePostal
     */
    public int getCodePostal() {
        return codePostal;
    }

    /**
     * @param codePostal the codePostal to set
     */
    public void setCodePostal(int codePostal) {
        this.codePostal = codePostal;
    }

    /**
     * @return the ville
     */
    public String getVille() {
        return ville;
    }

    /**
     * @param ville the ville to set
     */
    public void setVille(String ville) {
        this.ville = ville;
    }

    /**
     * @return the pays
     */
    public String getPays() {
        return pays;
    }

    /**
     * @param pays the pays to set
     */
    public void setPays(String pays) {
        this.pays = pays;
    }

    /**
     * @return the tel
     */
    public int getTel() {
        return tel;
    }

    /**
     * @param tel the tel to set
     */
    public void setTel(int tel) {
        this.tel = tel;
    }

    /**
     * @return the fixe
     */
    public int getFixe() {
        return fixe;
    }

    /**
     * @param fixe the fixe to set
     */
    public void setFixe(int fixe) {
        this.fixe = fixe;
    }

    /**
     * @return the mail
     */
    public String getMail() {
        return mail;
    }

    /**
     * @param mail the mail to set
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

}
