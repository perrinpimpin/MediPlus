/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medi.nf;

import java.util.Date;

import library.interfaces.ServeurHL7;
import library.interfaces.ClientHL7;
import library.interfaces.MessageInterface;

/**
 *
 * @author victor
 */
public class LancerServeurHL7 {

    public LancerServeurHL7() {
        Thread t = new Thread(new Runnable() {
            @Override

            public void run() {
                ServeurHL7 serveur = new ServeurHL7();
                Integer port = 6502;
                serveur.connection(port);
                while (true) {
                    serveur.ecoute();
                    System.out.println("client connexté");
                    String message = serveur.protocole();
                    serveur.fermer();
                 
                }

            }

        });
        t.start();

    }
    
    public void admiPatientHL7(Integer id,String nom,String prenom,String sexe, Date dNaissance, Date dAdmin){
        
        char classe;
        classe='N';
      
        
        String n = nom;
        ClientHL7 client = new ClientHL7();
        client.connexion("localhost", 6502);
        
        
        
        library.interfaces.Patient patient = new library.interfaces.Patient(id,nom,classe);
           char sex;
        
        patient.setBirth(dNaissance);
        patient.setDateDicharge(dAdmin);
        
        if(sexe.equals("Homme")){
           
            sex='M';
            patient.setSex(sex);
        }
        else{
            sex='F';
            patient.setSex(sex);
        }
        patient.setFirstName(prenom);
        
       
        
        client.admit(patient);
        
        MessageInterface messageack = client.getMsg();
      
        System.out.println(messageack.getType());
        
        
        
    }

}
