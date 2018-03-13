/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medi.ui;

import java.util.Calendar;
import medi.nf.BD;
import medi.nf.DM;
import medi.nf.Medecin;
import medi.nf.Patient;

/**
 *
 * @author CRISTANTE
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        BD connect = new BD();
        Patient p = connect.recherchePatientsIPP(180000000);
        Medecin m = connect.rechercheMedecin(123487);
        DM d = new DM(p,m,"",connect.genererIDDM(),new java.sql.Date(Calendar.getInstance().getTime().getTime()));
        connect.ajouterDM(d);
    }
    
}
