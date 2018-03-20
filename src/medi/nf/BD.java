/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medi.nf;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.sql.Date;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author CRISTANTE
 */
public class BD {

    private Connection con;
    private ArrayList<Patient> lp;
    private Patient p;
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
    private int portable;
    private int fixe;
    private String mail;
    private ArrayList<DM> dm;
    private Medecin m;
    private int lit;
    private int chambre;
    private boolean fenetre;

    // connexion a la base de donnÃ©es
    public BD() {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bd", "root", "");

        } catch (Exception ex) {
            System.out.println("error : " + ex);

        }

    }

    //connexion sur le logiciel en fonction du mdp et de l'id, retourne l'utilisateur qui se connecte
    public User connection(String username, String password) {
        int res_co = 0;
        ResultSet rsConnection;
        int i = 0;
        User user = null;
        String u = null;
        String p = null;
        String r = null;
        try {

            Statement st = con.createStatement();
            //on récupère les informations liés à l'identifiant de connection
            String query = "SELECT * FROM `users` WHERE `username` = '" + username + " ' ";
            rsConnection = st.executeQuery(query);
            while (rsConnection.next()) {
                u = rsConnection.getString("username");
                p = rsConnection.getString("password");
                r = rsConnection.getString("role");
                i = rsConnection.getInt("ID_user");
                //On regarde si l'user est un médecin ou une secrétaire médicale
                if (!username.isEmpty() && p.equals(password) && r.equals("1")) {
                    res_co = 1;
                } else if (!username.isEmpty() && p.equals(password) && r.equals("2")) {
                    res_co = 2;
                } else {
                    res_co = 0;
                }
            }
            if (res_co == 1) {
                //On récupère les informations du médecin
                query = "SELECT * FROM `praticien` WHERE ID_user = " + i;
                ResultSet qs = st.executeQuery(query);
                while (qs.next()) {
                    user = new Medecin(qs.getString("nom"), qs.getString("prenom"), i, u, p, qs.getInt("telephone"), qs.getString("specialite"), qs.getString("service"));
                }
                qs.close();
            } else if (res_co == 2) {
                //On récupère les informations de la secrétaire
                query = "SELECT * FROM `secretaire` WHERE ID_user = " + i;
                rsConnection = st.executeQuery(query);
                while (rsConnection.next()) {
                    user = new SecretaireAdmin(rsConnection.getString("nom"), rsConnection.getString("prenom"), i, u, p, rsConnection.getInt("telephone"));
                }
            }
            rsConnection.close();
            st.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return user;
    }

    // Fonction d'ajout d'utilisateur du logiciel pour qu'il puisse se connecter (doit être utilisé en parallèle avec la fonction "ajouterMedecin" ou "ajouterSA"
    public void ajouterLogin(int id_user, String username, String password, int role) {
        //roles : 1=medecin, 2=SA
        String sql = "insert into users(id_user,username,password,role) values (?,?,?,?)";

        try {
            PreparedStatement pstmAjouterLogin = con.prepareStatement(sql);

            pstmAjouterLogin.setInt(1, id_user);
            pstmAjouterLogin.setString(2, username);
            pstmAjouterLogin.setString(3, password);
            pstmAjouterLogin.setInt(4, role);
            pstmAjouterLogin.executeUpdate();
            pstmAjouterLogin.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    //Modifie le mot de passe d'un utilisateur
    public void modifierMotDePasse(int id_user, String password) {
        String sql = "update users set password = ? where id_user = ?";
        try {
            PreparedStatement pstmChangerMDP = con.prepareStatement(sql);
            pstmChangerMDP.setString(1, password);
            pstmChangerMDP.setInt(2, id_user);
            pstmChangerMDP.executeUpdate();
            pstmChangerMDP.close();
            javax.swing.JOptionPane.showMessageDialog(null, "Mot de passe modifié.", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    //On crée un nouveau médecin dans la BD
    public void ajouterMedecin(int ID_user, String nom, String prenom, int telephone, String specialite, String service) {
        String sql = "insert into praticien(ID_user,nom,prenom,telephone,specialite,service) values (?,?,?,?,?,?)";

        try {
            PreparedStatement pstmAjouterMedecin = con.prepareStatement(sql);

            pstmAjouterMedecin.setInt(1, ID_user);
            pstmAjouterMedecin.setString(2, nom);
            pstmAjouterMedecin.setString(3, prenom);
            pstmAjouterMedecin.setInt(4, telephone);
            pstmAjouterMedecin.setString(5, specialite);
            pstmAjouterMedecin.setString(6, service);
            pstmAjouterMedecin.executeUpdate();
            pstmAjouterMedecin.close();

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    // Ajout d'une sercrétaire administrative dans la base de données
    public void ajouterSA(int ID_user, String nom, String prenom, int telephone) {
        String sql = "insert into secretaire(ID_user,nom,prenom,telephone) values (?,?,?,?)";

        try {
            PreparedStatement pstmAjouterSA = con.prepareStatement(sql);

            pstmAjouterSA.setInt(1, ID_user);
            pstmAjouterSA.setString(2, nom);
            pstmAjouterSA.setString(3, prenom);
            pstmAjouterSA.setInt(4, telephone);
            pstmAjouterSA.executeUpdate();
            pstmAjouterSA.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    // Ajout d'un DMA dans la bdd, paramètres étant les mêmes que pour un objet DMA
    public void ajouterDMA(String nom, String prenom, Date dateNaissance, String lieuNaissance, String sexe, int numeroVoie, String typeVoie, String complement, int codePostal, String ville, String pays, int portable, int fixe, String mail) {

        String sql = "insert into d_m_a(IPP, nom, prenom, dateNaissance, lieuNaissance, sexe, numeroVoie, typeVoie, complement, codePostal, ville, pays, portable, fixe, mail) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        int ipp = this.genererIPP();
        try {
            PreparedStatement pstmAjouterDMA = con.prepareStatement(sql);

            pstmAjouterDMA.setInt(1, ipp);
            pstmAjouterDMA.setString(2, nom);
            pstmAjouterDMA.setString(3, prenom);
            pstmAjouterDMA.setDate(4, dateNaissance);
            pstmAjouterDMA.setString(5, lieuNaissance);
            pstmAjouterDMA.setString(6, sexe);
            pstmAjouterDMA.setInt(7, numeroVoie);
            pstmAjouterDMA.setString(8, typeVoie);
            pstmAjouterDMA.setString(9, complement);
            pstmAjouterDMA.setInt(10, codePostal);
            pstmAjouterDMA.setString(11, ville);
            pstmAjouterDMA.setString(12, pays);
            pstmAjouterDMA.setInt(13, portable);
            pstmAjouterDMA.setInt(14, fixe);
            pstmAjouterDMA.setString(15, mail);

            pstmAjouterDMA.executeUpdate();
            pstmAjouterDMA.close();
            javax.swing.JOptionPane.showMessageDialog(null, "Dossier Médico-Administratif ajouté à  la base de données", "Patient ajouté", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            System.out.println(ex);
            javax.swing.JOptionPane.showMessageDialog(null, "Informations incorrectes", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Recherche dans la BD si le patient existe, et le crée avec les informations entrées si ce n'est pas le cas
    public boolean ajouterDMAUrgences(String nom, String prenom, Date dateNaissance, String lieuNaissance, String sexe) {
        boolean existeDeja = false;
        String sql = "insert into d_m_a(IPP, nom, prenom, dateNaissance, lieuNaissance, sexe) values (?,?,?,?,?,?)";
        int ipp = this.genererIPP();
        Patient x = new Patient(null, null, null);
        try {
            Patient x2 = this.recherchePatientsNomPrenomDate(nom, prenom, dateNaissance);
            if (x.getIPP() == x2.getIPP()) {
                PreparedStatement pstmAjouterDMA = con.prepareStatement(sql);

                pstmAjouterDMA.setInt(1, ipp);
                pstmAjouterDMA.setString(2, nom);
                pstmAjouterDMA.setString(3, prenom);
                pstmAjouterDMA.setDate(4, dateNaissance);
                pstmAjouterDMA.setString(5, lieuNaissance);
                pstmAjouterDMA.setString(6, sexe);

                pstmAjouterDMA.executeUpdate();
                pstmAjouterDMA.close();
                existeDeja = false;
                javax.swing.JOptionPane.showMessageDialog(null, "Dossier Médico-Administratif ajouté à  la base de données", "Patient ajouté", JOptionPane.INFORMATION_MESSAGE);
            } else {
                existeDeja = true;
            }
        } catch (Exception ex) {
            System.out.println(ex);
            javax.swing.JOptionPane.showMessageDialog(null, "Informations incorrectes", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
        return existeDeja;
    }

    //Méthode d'ajout d'une prescrtpion à  la bdd
    public void ajouterPrescription(String id, int PH, String prescription, int iddm) {

        String sql = "insert into prescriptions(id_prescription, PH, prescription, date, id_dm) values (?,?,?,?,?)";
        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        try {
            PreparedStatement pstmAjouterPrescription = con.prepareStatement(sql);

            pstmAjouterPrescription.setString(1, id);
            pstmAjouterPrescription.setInt(2, PH);
            pstmAjouterPrescription.setString(3, prescription);
            pstmAjouterPrescription.setDate(4, date);
            pstmAjouterPrescription.setInt(5, iddm);

            pstmAjouterPrescription.executeUpdate();
            pstmAjouterPrescription.close();
        } catch (Exception ex) {
            System.out.println(ex);
            javax.swing.JOptionPane.showMessageDialog(null, "Informations incorrectes", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    //MÃ©thode d'ajout d'une opÃ©ration infirmiÃ¨re à  la base de donnÃ©es, prend en 
    //paramÃ¨tre les mÃªmes paramÃ¨tres que l'obet op Infirmière
    public void ajouterOpeInf(String id, int PH, String op, int iddm) {

        String sql = "insert into op_inf(id_op, date, id_dm, PH, op_inf) values (?,?,?,?,?)";
        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        try {
            PreparedStatement pstmAJouterOpeInf = con.prepareStatement(sql);

            pstmAJouterOpeInf.setString(1, id);
            pstmAJouterOpeInf.setDate(2, date);
            pstmAJouterOpeInf.setInt(3, iddm);
            pstmAJouterOpeInf.setInt(4, PH);
            pstmAJouterOpeInf.setString(5, op);

            pstmAJouterOpeInf.executeUpdate();
            pstmAJouterOpeInf.close();
        } catch (Exception ex) {
            System.out.println(ex);
            javax.swing.JOptionPane.showMessageDialog(null, "Informations incorrectes", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Méthode d'ajout d'une lettre de sortie sur le DM d'id : iddm, dans la bdd
    public void ajouterLettreSortie(String lettre, int iddm) {

        String sql = "update d_m set lettre_Sortie= ? where id_dm = ?";
        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        try {
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, lettre);
            pstmt.setInt(2, iddm);
            pstmt.executeUpdate();

        } catch (Exception ex) {
            System.out.println(ex);
            javax.swing.JOptionPane.showMessageDialog(null, "Informations incorrectes", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Méthode d'ajout d'un résultat dans un DM d'id : iddm 
    public void ajouterResultat(int PH, String resultat, String idres) {

        String sql = "update resultats set date = ?, PH = ?, resultat = ? where id_res='"+idres +"'";
        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        try {
            PreparedStatement pstmAjouterResultat = con.prepareStatement(sql);

            pstmAjouterResultat.setDate(1, date);
            pstmAjouterResultat.setInt(2, PH);
            pstmAjouterResultat.setString(3, resultat);

            pstmAjouterResultat.executeUpdate();
            pstmAjouterResultat.close();
        } catch (Exception ex) {
            System.out.println(ex);
            javax.swing.JOptionPane.showMessageDialog(null, "Informations incorrectes", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
        public void demandeExamenMT(String demande, int PH, DM dm) {

        String sql = "insert into resultats(demande, dateDemande, PH_demande, id_dm, id_res) values (?,?,?,?,?)";
        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());        
        try {
            PreparedStatement pstmAjouterResultat = con.prepareStatement(sql);

            pstmAjouterResultat.setString(1, demande);
            pstmAjouterResultat.setDate(2, date);
            pstmAjouterResultat.setInt(3, PH);
            pstmAjouterResultat.setInt(4, dm.getIddm());
            pstmAjouterResultat.setString(5, this.genererIDRes(dm));
            

            pstmAjouterResultat.executeUpdate();
            pstmAjouterResultat.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    //MÃ©thode d'ajout d'une observatin pour un DM d'id : iddm    
    public void ajouterObs(String id, int PH, String obs, int iddm) {

        String sql = "insert into observations(id_obs, date, id_dm, PH, observation) values (?,?,?,?,?)";
        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        try {
            PreparedStatement pstmAjouterObs = con.prepareStatement(sql);

            pstmAjouterObs.setString(1, id);
            pstmAjouterObs.setDate(2, date);
            pstmAjouterObs.setInt(3, iddm);
            pstmAjouterObs.setInt(4, PH);
            pstmAjouterObs.setString(5, obs);

            pstmAjouterObs.executeUpdate();
            pstmAjouterObs.close();
        } catch (Exception ex) {
            System.out.println(ex);
            javax.swing.JOptionPane.showMessageDialog(null, "Informations incorrectes", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Méthode de modification des infos patients dans la bdd    
    public void modifierPatient(Patient p) {
        PreparedStatement pstmModifierPatient = null;
        String query = "update d_m_a set numeroVoie = ?, "
                + " typeVoie = ?, "
                + " complement = ?, "
                + " codePostal = ?, "
                + " ville = ?, "
                + " pays = ?, "
                + " portable = ?, "
                + " fixe = ?, "
                + " mail = ? "
                + " where IPP = ?";
        try {
            pstmModifierPatient = con.prepareStatement(query);
            pstmModifierPatient.setInt(1, p.getNumeroVoie());
            pstmModifierPatient.setString(2, p.getTypeVoie());
            pstmModifierPatient.setString(3, p.getComplement());
            pstmModifierPatient.setInt(4, p.getCodePostal());
            pstmModifierPatient.setString(5, p.getVille());
            pstmModifierPatient.setString(6, p.getPays());
            pstmModifierPatient.setInt(7, p.getTel());
            pstmModifierPatient.setInt(8, p.getFixe());
            pstmModifierPatient.setString(9, p.getMail());
            pstmModifierPatient.setInt(10, p.getIPP());
            pstmModifierPatient.executeUpdate();
            pstmModifierPatient.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    //Méthode d'archivage du patient
    //Retrait de la table d_m_a pour transférer le dossier dans la table archive_dma 
    public void archiverPatient(Patient p, Date d, String obs) {
        PreparedStatement pstmArchiverPatient = null;
        PreparedStatement pstmArchiverPatient2 = null;
        String sql = "insert into archive_dma(IPP, nom, prenom, dateNaissance, lieuNaissance, sexe, numeroVoie, typeVoie, complement, codePostal, ville, pays, portable, fixe, mail, dateDeces, observation) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        String query = "delete from d_m_a where IPP = " + p.getIPP();
        try {
            pstmArchiverPatient = con.prepareStatement(sql);
            pstmArchiverPatient2 = con.prepareStatement(query);
            pstmArchiverPatient.setInt(1, p.getIPP());
            pstmArchiverPatient.setString(2, p.getNom());
            pstmArchiverPatient.setString(3, p.getPrenom());
            pstmArchiverPatient.setDate(4, p.getDate());
            pstmArchiverPatient.setString(5, p.getLieuNaissance());
            pstmArchiverPatient.setString(6, p.getSexe());
            pstmArchiverPatient.setInt(7, p.getNumeroVoie());
            pstmArchiverPatient.setString(8, p.getTypeVoie());
            pstmArchiverPatient.setString(9, p.getComplement());
            pstmArchiverPatient.setInt(10, p.getCodePostal());
            pstmArchiverPatient.setString(11, p.getVille());
            pstmArchiverPatient.setString(12, p.getPays());
            pstmArchiverPatient.setInt(13, p.getTel());
            pstmArchiverPatient.setInt(14, p.getFixe());
            pstmArchiverPatient.setString(15, p.getMail());
            pstmArchiverPatient.setDate(16, d);
            pstmArchiverPatient.setString(17, obs);
            pstmArchiverPatient.executeUpdate();
            pstmArchiverPatient2.executeUpdate();
            pstmArchiverPatient.close();
            pstmArchiverPatient2.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    //Méthode retournant la liste des DM qui n'ont pas de lettre de sortie
    //et qui sont donc encore en cours
    public ArrayList<DM> sejoursEnCours() {
        int ipp = 0;
        int ph = 0;
        String lettre = null;
        int iddm = 0;
        String nom;
        String prenom;
        Date dateN;
        Date date = null;
        ArrayList<DM> dms = new ArrayList();
        String l;
        Lit lit;

        ResultSet rsSejourEnCours;

        try {
            Statement st1 = con.createStatement();
            String query = "select * from d_m where lettre_Sortie IS NULL";
            rsSejourEnCours = st1.executeQuery(query);
            while (rsSejourEnCours.next()) {
                ipp = rsSejourEnCours.getInt("IPP");
                ph = rsSejourEnCours.getInt("PH");
                date = rsSejourEnCours.getDate("date");
                lettre = rsSejourEnCours.getString("lettre_Sortie");
                iddm = rsSejourEnCours.getInt("id_dm");
                l = rsSejourEnCours.getString("lit");
                lit = this.rechercherLit(l);

                Patient pc = this.recherchePatientsIPP(ipp);

                DM dm = new DM(pc, m, lettre, iddm, date, lit);
                dms.add(dm);

            }
            rsSejourEnCours.close();
            st1.close();

        } catch (Exception ex) {
            System.out.println(ex);
        }

        return dms;

    }

    //Méthode permettant d'ajouter un DM dans la base de données à partir d'un objet DM
    public void ajouterDM(DM d) {

        int ipp = d.getP().getIPP();
        String let = d.getLet();
        int idmed = d.getMedref().getId_user();
        Date date = d.getDate();
        Lit l = d.getLit();

        String sql = "insert into d_m(IPP, date, PH, lettre_Sortie, id_dm,lit) values (?,?,?,?,?,?)";
        try {
            PreparedStatement pstmAjouterDM = con.prepareStatement(sql);

            pstmAjouterDM.setInt(1, ipp);
            pstmAjouterDM.setDate(2, date);
            pstmAjouterDM.setInt(3, idmed);
            pstmAjouterDM.setString(4, let);
            pstmAjouterDM.setInt(5, this.genererIDDM());
            pstmAjouterDM.setString(6, l.getNum());
            pstmAjouterDM.executeUpdate();
            javax.swing.JOptionPane.showMessageDialog(null, "Dossier Médical créé.", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
            pstmAjouterDM.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    //Méthode permettant de générer automatiquement un IDDM en 
    //fonction des IDDM déjà présent dans la bdd
    public int genererIDDM() {
        ResultSet rsGenererIDDM;
        int iddm = 0;
        DateFormat dateFormat = new SimpleDateFormat("yyMM");
        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        String annee = dateFormat.format(date);
        try {
            Statement st = con.createStatement();
            String query = "select max(id_dm) from d_m where substring(id_dm,1,4)=" + Integer.parseInt(annee);
            rsGenererIDDM = st.executeQuery(query);
            rsGenererIDDM.next();
            if (rsGenererIDDM.getInt("max(id_dm)") != 0) {
                iddm = rsGenererIDDM.getInt("max(id_dm)") + 1;
            } else {
                iddm = Integer.parseInt(annee) * 100000;
            }
            rsGenererIDDM.close();
            st.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return iddm;
    }

    //Méthode permettannt de générer un id de prescription automatiquement
    //en fonction des ID déjà présents dans la bdd selon le modèle : iddmPxxx avec xxx un compteur sur 3 positions
    public String genererIDPrescription(DM dm) {
        ResultSet rsGenererIDPrescription;
        String idp = null;
        String iddm = String.valueOf(dm.getIddm());
        try {
            Statement st = con.createStatement();
            String query = "select max(substring(id_prescription,11,3)) from prescriptions where substring(id_prescription,1,9)=" + iddm;
            rsGenererIDPrescription = st.executeQuery(query);
            rsGenererIDPrescription.next();
            if (rsGenererIDPrescription.getInt("max(substring(id_prescription,11,3))") != 0) {
                int id = rsGenererIDPrescription.getInt("max(substring(id_prescription,11,3))") + 1;
                idp = iddm + "P" + String.format("%03d", id);
            } else {
                idp = iddm + "P001";
            }
            rsGenererIDPrescription.close();
            st.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return idp;
    }

    //méthode permettant de générer les ID des observations automatiquement pour un DM donné selon le modèle : iddmOxxx avec xxx un compteur sur 3 positions
    public String genererIDObs(DM dm) {
        ResultSet rsGenererIDObs;
        String idp = null;
        String iddm = String.valueOf(dm.getIddm());
        try {
            Statement st = con.createStatement();
            String query = "select max(substring(id_obs,11,3)) from observations where substring(id_obs,1,9)=" + iddm;
            rsGenererIDObs = st.executeQuery(query);
            rsGenererIDObs.next();
            if (rsGenererIDObs.getInt("max(substring(id_obs,11,3))") != 0) {
                int id = rsGenererIDObs.getInt("max(substring(id_obs,11,3))") + 1;
                idp = iddm + "O" + String.format("%03d", id);
            } else {
                idp = iddm + "O001";
            }
            rsGenererIDObs.close();
            st.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return idp;
    }

    //MÃ©thode gÃ©nÃ©rant les ID automatiquement des opÃ©rations infirmiÃ¨res pour un DM donnÃ©e selon le modèle : iddmIxxx avec xxx un compteur sur 3 positions
    public String genererIDOpe(DM dm) {
        ResultSet rsGenererIDOpe;
        String idp = null;
        String iddm = String.valueOf(dm.getIddm());
        try {
            Statement st = con.createStatement();
            String query = "select max(substring(id_op,11,3)) from op_inf where substring(id_op,1,9)=" + iddm;
            rsGenererIDOpe = st.executeQuery(query);
            rsGenererIDOpe.next();
            if (rsGenererIDOpe.getInt("max(substring(id_op,11,3))") != 0) {
                int id = rsGenererIDOpe.getInt("max(substring(id_op,11,3))") + 1;
                idp = iddm + "I" + String.format("%03d", id);
            } else {
                idp = iddm + "I001";
            }
            rsGenererIDOpe.close();
            st.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return idp;
    }

    //Méthode permettant de générer les ID des resultats de manière automatique pour un DM donné selon le modèle : iddmRxxx avec xxx un compteur sur 3 positions
    public String genererIDRes(DM dm) {
        ResultSet rsGenererIDRes;
        String idp = null;
        String iddm = String.valueOf(dm.getIddm());
        try {
            Statement st = con.createStatement();
            String query = "select max(substring(id_res,11,3)) from resultats where substring(id_res,1,9)=" + iddm;
            rsGenererIDRes = st.executeQuery(query);
            rsGenererIDRes.next();
            if (rsGenererIDRes.getInt("max(substring(id_res,11,3))") != 0) {
                int id = rsGenererIDRes.getInt("max(substring(id_res,11,3))") + 1;
                idp = iddm + "R" + String.format("%03d", id);
            } else {
                idp = iddm + "R001";
            }

            rsGenererIDRes.close();
            st.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return idp;
    }

    //Méthode générant les ID des prescription de manière automatique pendant la création d'un DM
    public String genererIDPrescription() {
        ResultSet rsGenererIDPrescription;
        String idp = null;
        String iddm = String.valueOf(this.genererIDDM());
        try {
            Statement st = con.createStatement();
            String query = "select max(substring(id_prescription,11,3)) from prescriptions where substring(id_prescription,1,9)=" + iddm;
            rsGenererIDPrescription = st.executeQuery(query);
            rsGenererIDPrescription.next();
            if (rsGenererIDPrescription.getInt("max(substring(id_prescription,11,3))") != 0) {
                int id = rsGenererIDPrescription.getInt("max(substring(id_prescription,11,3))") + 1;
                idp = iddm + "P" + String.format("%03d", id);
            } else {
                idp = iddm + "P001";
            }
            rsGenererIDPrescription.close();
            st.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return idp;
    }

    //méthode permettant de générer les ID des observations automatiquement pendant la création d'un DM
    public String genererIDObs() {
        ResultSet rsGenererIDObs;
        String idp = null;
        String iddm = String.valueOf(this.genererIDDM());
        try {
            Statement st = con.createStatement();
            String query = "select max(substring(id_obs,11,3)) from observations where substring(id_obs,1,9)=" + iddm;
            rsGenererIDObs = st.executeQuery(query);
            rsGenererIDObs.next();
            if (rsGenererIDObs.getInt("max(substring(id_obs,11,3))") != 0) {
                int id = rsGenererIDObs.getInt("max(substring(id_obs,11,3))") + 1;
                idp = iddm + "O" + String.format("%03d", id);
            } else {
                idp = iddm + "O001";
            }
            rsGenererIDObs.close();
            st.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return idp;
    }

    //mÃ©thode permettant de gÃ©nÃ©rer les ID des opÃ©rations infirmiÃ¨res automatiquement pendant la création d'un DM
    public String genererIDOpe() {
        ResultSet rsGenererIDOpe;
        String idp = null;
        String iddm = String.valueOf(this.genererIDDM());
        try {
            Statement st = con.createStatement();
            String query = "select max(substring(id_op,11,3)) from op_inf where substring(id_op,1,9)=" + iddm;
            rsGenererIDOpe = st.executeQuery(query);
            rsGenererIDOpe.next();
            if (rsGenererIDOpe.getInt("max(substring(id_op,11,3))") != 0) {
                int id = rsGenererIDOpe.getInt("max(substring(id_op,11,3))") + 1;
                idp = iddm + "I" + String.format("%03d", id);
            } else {
                idp = iddm + "I001";
            }
            rsGenererIDOpe.close();
            st.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return idp;
    }

    //mÃ©thode permettant de gÃ©nÃ©rer les ID des rÃ©sultats automatiquement pendant la création d'un DM
    public String genererIDRes() {
        ResultSet rsGenererIDRes;
        String idp = null;
        String iddm = String.valueOf(this.genererIDDM());
        try {
            Statement st = con.createStatement();
            String query = "select max(substring(id_res,11,3)) from resultats where substring(id_res,1,9)=" + iddm;
            rsGenererIDRes = st.executeQuery(query);
            rsGenererIDRes.next();
            if (rsGenererIDRes.getInt("max(substring(id_res,11,3))") != 0) {
                int id = rsGenererIDRes.getInt("max(substring(id_res,11,3))") + 1;
                idp = iddm + "R" + String.format("%03", id);
            } else {
                idp = iddm + "R001";
            }
            rsGenererIDRes.close();
            st.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return idp;
    }

    //MÃ©thode retournant tout les DM ayant pour mÃ©decin refÃ©rent le mÃ©decin entrÃ© en paramÃ¨tre
    public ArrayList getDM(Medecin m) {
        ResultSet rsGetDMMedecin = null, rs1, rs2 = null;
        int ipp = 0;
        int ph = 0;
        String lettre = null;
        int iddm = 0;
        String nom;
        String prenom;
        Date dateN;
        Date date = null;
        ArrayList<DM> dms = new ArrayList();
        String ser = m.getService();
        Lit l;
        String lit;

        try {
            Statement st = con.createStatement();
            Statement st1 = con.createStatement();
            Statement st2 = con.createStatement();

            String query = "select * from praticien WHERE service = '" + ser + "'";
            rs1 = st1.executeQuery(query);
            while (rs1.next()) {
                nom = rs1.getString("nom");
                prenom = rs1.getString("prenom");
                int tel = rs1.getInt("telephone");
                String spe = rs1.getString("specialite");
                int id = rs1.getInt("id_user");

                query = "select * from d_m where PH = " + id;
                rsGetDMMedecin = st.executeQuery(query);
                while (rsGetDMMedecin.next()) {
                    ipp = rsGetDMMedecin.getInt("IPP");
                    ph = rsGetDMMedecin.getInt("PH");
                    date = rsGetDMMedecin.getDate("date");
                    lettre = rsGetDMMedecin.getString("lettre_Sortie");
                    iddm = rsGetDMMedecin.getInt("id_dm");
                    lit = rsGetDMMedecin.getString("lit");
                    l = this.rechercherLit(lit);

                    String query2 = "select * from users WHERE ID_user = " + ph;
                    rs2 = st2.executeQuery(query2);
                    while (rs2.next()) {
                        String username = rs2.getString("username");
                        String mdp = rs2.getString("password");
                        m = new Medecin(nom, prenom, ph, username, mdp, tel, spe, ser);

                    }
                    rs2.close();

                    p = this.recherchePatientsIPP(ipp);
                    DM dm = new DM(p, m, lettre, iddm, date, l);
                    dms.add(dm);
                    /*query = "select * from d_m_a WHERE IPP = " + ipp;
                    rs1 = st1.executeQuery(query);
                    while (rs1.next()) {
                        nom = rs1.getString("nom");
                        prenom = rs1.getString("prenom");
                        dateN = rs1.getDate("dateNaissance");
                        DM dm = new DM(new Patient(nom, prenom, dateN, ipp), m, lettre, iddm, date, l);
                        dms.add(dm);
                    }*/

                }

            }
            rsGetDMMedecin.close();
            st.close();
            rs1.close();
            st1.close();
            st2.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return dms;
    }
    
    

    //Méthode pour récupérer la totalité des DM
    public ArrayList getDM() {
        ResultSet rsGetDMMedecin = null, rs1, rs2 = null;
        int ipp = 0;
        int ph = 0;
        String lettre = null;
        int iddm = 0;
        String nom;
        String prenom;
        Date dateN;
        Date date = null;
        ArrayList<DM> dms = new ArrayList();
        String ser = m.getService();
        Lit l;
        String lit;

        try {
            Statement st = con.createStatement();
            Statement st1 = con.createStatement();
            Statement st2 = con.createStatement();

            String query = "select * from praticien";
            rs1 = st1.executeQuery(query);
            while (rs1.next()) {
                nom = rs1.getString("nom");
                prenom = rs1.getString("prenom");
                int tel = rs1.getInt("telephone");
                String spe = rs1.getString("specialite");
                int id = rs1.getInt("id_user");

                query = "select * from d_m where PH = " + id;
                rsGetDMMedecin = st.executeQuery(query);
                while (rsGetDMMedecin.next()) {
                    ipp = rsGetDMMedecin.getInt("IPP");
                    ph = rsGetDMMedecin.getInt("PH");
                    date = rsGetDMMedecin.getDate("date");
                    lettre = rsGetDMMedecin.getString("lettre_Sortie");
                    iddm = rsGetDMMedecin.getInt("id_dm");
                    lit = rsGetDMMedecin.getString("lit");
                    l = this.rechercherLit(lit);

                    String query2 = "select * from users WHERE ID_user = " + ph;
                    rs2 = st2.executeQuery(query2);
                    while (rs2.next()) {
                        String username = rs2.getString("username");
                        String mdp = rs2.getString("password");
                        m = new Medecin(nom, prenom, ph, username, mdp, tel, spe, ser);

                    }
                    rs2.close();

                    p = this.recherchePatientsIPP(ipp);
                    DM dm = new DM(p, m, lettre, iddm, date, l);
                    dms.add(dm);
                    /*query = "select * from d_m_a WHERE IPP = " + ipp;
                    rs1 = st1.executeQuery(query);
                    while (rs1.next()) {
                        nom = rs1.getString("nom");
                        prenom = rs1.getString("prenom");
                        dateN = rs1.getDate("dateNaissance");
                        DM dm = new DM(new Patient(nom, prenom, dateN, ipp), m, lettre, iddm, date, l);
                        dms.add(dm);
                    }*/

                }

            }
            rsGetDMMedecin.close();
            st.close();
            rs1.close();
            st1.close();
            st2.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return dms;
    }

    //MÃ©thode retournant le DM dont l'iddm est celui entrÃ© en paramÃ¨tre
    public DM getDM(int iddm) {
        ResultSet rsGetDMIDDM = null, rs1 = null, rs2 = null;
        int ph = 0;
        int ipp = 0;
        String lettre = null;

        String nom;
        String prenom;
        Date dateN;
        Date date;
        DM dm = null;
        String l;
        Lit lit;

        try {
            Statement st = con.createStatement();
            Statement st1 = con.createStatement();
            Statement st2 = con.createStatement();

            String query = "select * from d_m where id_dm = " + iddm;
            rsGetDMIDDM = st.executeQuery(query);
            while (rsGetDMIDDM.next()) {
                ipp = rsGetDMIDDM.getInt("IPP");
                ph = rsGetDMIDDM.getInt("PH");
                date = rsGetDMIDDM.getDate("date");
                lettre = rsGetDMIDDM.getString("lettre_Sortie");
                iddm = rsGetDMIDDM.getInt("id_dm");
                l = rsGetDMIDDM.getString("lit");
                lit = this.rechercherLit(l);

                query = "select * from praticien WHERE ID_user = " + ph;
                rs1 = st1.executeQuery(query);
                while (rs1.next()) {
                    nom = rs1.getString("nom");
                    prenom = rs1.getString("prenom");
                    int tel = rs1.getInt("telephone");
                    String spe = rs1.getString("specialite");
                    String ser = rs1.getString("service");

                    String query2 = "select * from users WHERE ID_user = " + ph;
                    rs2 = st2.executeQuery(query2);
                    while (rs2.next()) {
                        String username = rs2.getString("username");
                        String mdp = rs2.getString("password");
                        m = new Medecin(nom, prenom, ph, username, mdp, tel, spe, ser);
                    }
                }
                p = this.recherchePatientsIPP(ipp);

                dm = new DM(p, m, lettre, iddm, date, lit);
            }
            rsGetDMIDDM.close();
            rs1.close();
            rs2.close();
            st1.close();
            st2.close();
            st.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return dm;
    }

    //MÃ©thode retournant toutes les prescriptions associées au DM dont l'iddm est entrÃ© en paramÃ¨tre 
    public ArrayList<Prescription> getPrescription(int iddm) {
        ArrayList<Prescription> prescription = new ArrayList<>();
        int ph = 0;
        String pres = null;
        Date date = null;
        try {
            String query = "select * from prescriptions where id_dm = " + Integer.toString(iddm);
            Statement statement = con.createStatement();
            ResultSet prescriptionsSet = statement.executeQuery(query);

            while (prescriptionsSet.next()) {
                ph = prescriptionsSet.getInt("PH");
                date = prescriptionsSet.getDate("date");
                pres = prescriptionsSet.getString("prescription");
                m = this.rechercheMedecin(ph);
                Prescription pr = new Prescription(date, m, iddm, pres);
                prescription.add(pr);
            }

            prescriptionsSet.close();
            statement.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return prescription;
    }

    //MÃ©thode retournant toutes les observations du DM dont l'iddm est entrÃ© en paramÃ¨tre
    public ArrayList<Observation> getObservation(int iddm) {
        ResultSet rsGetObservationiddm;
        ArrayList<Observation> obs = new ArrayList<>();
        int ph = 0;
        String observation = null;
        Date date = null;
        try {
            Statement st = con.createStatement();
            String query = "select * from observations where id_dm = " + iddm;
            rsGetObservationiddm = st.executeQuery(query);
            while (rsGetObservationiddm.next()) {
                ph = rsGetObservationiddm.getInt("PH");
                date = rsGetObservationiddm.getDate("date");
                iddm = rsGetObservationiddm.getInt("id_dm");
                observation = rsGetObservationiddm.getString("observation");
                Medecin m = this.rechercheMedecin(ph);
                obs.add(new Observation(date, m, iddm, observation));
            }
            rsGetObservationiddm.close();
            st.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }

        return obs;
    }

    //MÃ©thode retournant tous les resultats du DM dont l'iddm est entrÃ© en paramÃ¨tre
    public ArrayList<Resultat> getResultat(int iddm) {
        ResultSet rsGetResultatiddm;
        ArrayList<Resultat> res = new ArrayList<>();
        int ph = 0;
        String idres = null;
        String resultat = null;
        Date date = null;
        int mt = 0;
        String demande = null;
        Date dd = null;
        try {
            Statement st = con.createStatement();
            String query = "select * from resultats where id_dm = " + iddm;
            rsGetResultatiddm = st.executeQuery(query);
            while (rsGetResultatiddm.next()) {
                ph = rsGetResultatiddm.getInt("PH");
                date = rsGetResultatiddm.getDate("date");
                iddm = rsGetResultatiddm.getInt("id_dm");
                resultat = rsGetResultatiddm.getString("resultat");
                mt = rsGetResultatiddm.getInt("PH_demande");
                demande = rsGetResultatiddm.getString("demande");
                dd = rsGetResultatiddm.getDate("dateDemande");
                idres = rsGetResultatiddm.getString("id_res");
                
                Medecin m = this.rechercheMedecin(ph);
                Medecin m2 = this.rechercheMedecin(mt);
                res.add(new Resultat(date, m, iddm, resultat,dd,m2,demande,idres));
            }
            rsGetResultatiddm.close();
            st.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }

        return res;
    }
    
        public ArrayList<Resultat> getDemandesResultat() {
        ResultSet rsGetResultatiddm;
        ArrayList<Resultat> res = new ArrayList<>();
        int ph = 0;
        int iddm = 0;
        String idres;
        String resultat = null;
        Date date = null;
        int mt = 0;
        String demande = null;
        Date dd = null;
        try {
            Statement st = con.createStatement();
            String query = "select * from resultats where resultat IS NULL";
            rsGetResultatiddm = st.executeQuery(query);
            while (rsGetResultatiddm.next()) {
                ph = rsGetResultatiddm.getInt("PH_demande");
                date = rsGetResultatiddm.getDate("date");
                iddm = rsGetResultatiddm.getInt("id_dm");
                resultat = rsGetResultatiddm.getString("resultat");
                mt = rsGetResultatiddm.getInt("PH");
                demande = rsGetResultatiddm.getString("demande");
                dd = rsGetResultatiddm.getDate("dateDemande");
                idres = rsGetResultatiddm.getString("id_res");
                
                Medecin m = this.rechercheMedecin(ph);
                res.add(new Resultat(date, m, iddm, resultat,dd,null,demande,idres));
            }
            rsGetResultatiddm.close();
            st.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }

        return res;
    }

    //MÃ©thode retournant toutes les opérations InfirmiÃ¨res du DM dont l'iddm est entrÃ© en paramÃ¨tre
    public ArrayList<OperationInfirmiere> getOperationInfirmiere(int iddm) {
        ResultSet rsGetOperationInfirmiereiddm;
        ArrayList<OperationInfirmiere> opinf = new ArrayList<>();
        int ph = 0;
        String operation = null;
        Date date = null;
        try {
            Statement st = con.createStatement();
            String query = "select * from op_inf where id_dm = " + iddm;
            rsGetOperationInfirmiereiddm = st.executeQuery(query);
            while (rsGetOperationInfirmiereiddm.next()) {
                ph = rsGetOperationInfirmiereiddm.getInt("PH");
                date = rsGetOperationInfirmiereiddm.getDate("date");
                iddm = rsGetOperationInfirmiereiddm.getInt("id_dm");
                operation = rsGetOperationInfirmiereiddm.getString("op_inf");
                Medecin m = this.rechercheMedecin(ph);
                opinf.add(new OperationInfirmiere(date, m, iddm, operation));
            }
            rsGetOperationInfirmiereiddm.close();
            st.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }

        return opinf;
    }

    //MÃ©thode retournant tous les DM du patient dont l'ipp est entrÃ© en paramÃ¨tre
    public ArrayList<DM> getDMPatient(int ipp) {
        ResultSet rsGetDMPatientipp;

        int ph = 0;
        String lettre = null;
        int iddm = 0;
        String obs = null;
        String prescription = null;
        String resultat = null;
        String opeInf = null;
        String nom;
        String prenom;
        Date dateN;
        Date date;
        ArrayList<DM> dms = new ArrayList();
        String l;

        try {
            Statement st = con.createStatement();
            Statement st1 = con.createStatement();
            ResultSet rs1;
            Statement st2 = con.createStatement();
            ResultSet rs2;

            //On cherche les informations sur le patient
            String query = "select * from d_m_a WHERE IPP = " + ipp;
            rs1 = st1.executeQuery(query);
            while (rs1.next()) {
                nom = rs1.getString("nom");
                prenom = rs1.getString("prenom");
                dateN = rs1.getDate("dateNaissance");
                p = new Patient(nom, prenom, dateN);
            }

            //On cherche les DM associés à ce patient
            query = "select * from d_m where IPP = " + ipp;
            rsGetDMPatientipp = st.executeQuery(query);
            while (rsGetDMPatientipp.next()) {
                ph = rsGetDMPatientipp.getInt("PH");
                date = rsGetDMPatientipp.getDate("date");
                lettre = rsGetDMPatientipp.getString("lettre_Sortie");
                iddm = rsGetDMPatientipp.getInt("id_dm");
                l = rsGetDMPatientipp.getString("lit");
                Lit lit = this.rechercherLit(l);

                //On retourne le médecin référent pour chaque DM
                query = "select * from praticien WHERE ID_user = " + ph;
                rs1 = st1.executeQuery(query);
                while (rs1.next()) {
                    nom = rs1.getString("nom");
                    prenom = rs1.getString("prenom");
                    int tel = rs1.getInt("telephone");
                    String spe = rs1.getString("specialite");
                    String ser = rs1.getString("service");

                    //On retourne les informations d'utilisateur du médecin référent pour chaque DM
                    String query2 = "select * from users WHERE ID_user = " + ph;
                    rs2 = st2.executeQuery(query2);
                    while (rs2.next()) {
                        String username = rs2.getString("username");
                        String mdp = rs2.getString("password");
                        m = new Medecin(nom, prenom, ph, username, mdp, tel, spe, ser);
                    }
                    rs2.close();
                }
                DM dm = new DM(p, m, lettre, iddm, date, lit);
                dms.add(dm);
            }
            rsGetDMPatientipp.close();
            st.close();
            rs1.close();
            st1.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return dms;
    }

    // MÃ©thode permettant de gÃ©nÃ©rer automatiquement un IPP patient
    public int genererIPP() {
        ResultSet rsGenererIPP;
        int ipp = 0;
        DateFormat dateFormat = new SimpleDateFormat("yy");
        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        String annee = dateFormat.format(date);
        try {
            Statement st = con.createStatement();
            String query = "select max(IPP) from bd.d_m_a where substring(ipp,1,2)=" + Integer.parseInt(annee);
            rsGenererIPP = st.executeQuery(query);
            rsGenererIPP.next();
            if (rsGenererIPP.getInt("max(IPP)") != 0) {
                ipp = rsGenererIPP.getInt("max(IPP)") + 1;
            } else {
                ipp = Integer.parseInt(annee) * 10000000;
            }
            rsGenererIPP.close();
            st.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return ipp;
    }

    //MÃ©thode retournant tout les patients ayant un d_m_a dans la base de donnÃ©es
    public ArrayList<Patient> getPatients() {
        ResultSet rsGetPatients;
        lp = new ArrayList<Patient>();
        try {
            Statement st = con.createStatement();
            String query = "select * from bd.d_m_a";
            rsGetPatients = st.executeQuery(query);
            while (rsGetPatients.next()) {
                ipp = rsGetPatients.getInt("IPP");// pour avoir accÃ¨s a la colonne de ma table 
                nom = rsGetPatients.getString("nom");
                prenom = rsGetPatients.getString("prenom");
                dateNaissance = rsGetPatients.getDate("dateNaissance");
                lieuNaissance = rsGetPatients.getString("lieuNaissance");
                sexe = rsGetPatients.getString("sexe");
                Patient p = new Patient(nom, prenom, ipp, dateNaissance, lieuNaissance, sexe);
                lp.add(p);
            }
            rsGetPatients.close();
            st.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return lp;
    }

    //Méthode retournant le médecin dont l'id est entré en paramètre
    public Medecin rechercheMedecin(int id) {
        ResultSet rsRechercheMedecinID;
        String username = null;
        String mdp = null;
        int tel = 0;
        String spe = null;
        String ser = null;
        try {
            //On recherche les informations sur le médecin
            Statement st = con.createStatement();
            String query = "select * from praticien where ID_user='" + id + "'";
            rsRechercheMedecinID = st.executeQuery(query);
            while (rsRechercheMedecinID.next()) {
                nom = rsRechercheMedecinID.getString("nom");
                prenom = rsRechercheMedecinID.getString("prenom");
                tel = rsRechercheMedecinID.getInt("telephone");
                spe = rsRechercheMedecinID.getString("specialite");
                ser = rsRechercheMedecinID.getString("service");
            }

            //On recherche les informations de connection du médecin
            query = "select * from users WHERE ID_user = " + id;
            rsRechercheMedecinID = st.executeQuery(query);
            while (rsRechercheMedecinID.next()) {
                username = rsRechercheMedecinID.getString("username");
                mdp = rsRechercheMedecinID.getString("password");
            }

            m = new Medecin(nom, prenom, id, username, mdp, tel, spe, ser);
            rsRechercheMedecinID.close();
            st.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return m;
    }

    //MÃ©thode retournant tout les mÃ©decins enregistrÃ©s dans la bdd
    public ArrayList<Medecin> getMedecins() {
        ResultSet rsGetMedecins, rsGetMedecins2 = null;
        String username = null;
        String mdp = null;
        int tel = 0;
        int id = 0;
        String name = null;
        String surname = null;
        String spe = null;
        String ser = null;
        Medecin m1 = null;
        ArrayList<Medecin> lm = new ArrayList<>();

        try {
            Statement st = con.createStatement();
            Statement st2 = con.createStatement();
            String query = "select * from praticien";
            rsGetMedecins = st.executeQuery(query);
            while (rsGetMedecins.next()) {
                name = rsGetMedecins.getString("nom");
                surname = rsGetMedecins.getString("prenom");
                tel = rsGetMedecins.getInt("telephone");
                spe = rsGetMedecins.getString("specialite");
                ser = rsGetMedecins.getString("service");
                id = rsGetMedecins.getInt("ID_user");

                String query2 = "select * from users WHERE ID_user = " + id;
                rsGetMedecins2 = st2.executeQuery(query2);
                while (rsGetMedecins2.next()) {
                    username = rsGetMedecins2.getString("username");
                    mdp = rsGetMedecins2.getString("password");
                    m1 = new Medecin(name, surname, id, username, mdp, tel, spe, ser);
                    lm.add(m1);
                }
            }

            rsGetMedecins2.close();
            rsGetMedecins.close();
            st.close();
            st2.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return lm;
    }

    //Methode retrouvant un patient enregistrÃ© dans la bdd par son nom
    public ArrayList<Patient> recherchePatientsNom(String name) {
        ResultSet rsRecherchePatientsNom;
        lp = new ArrayList<Patient>();
        try {
            Statement st = con.createStatement();
            String query = "select * from bd.d_m_a where nom='" + name + "'";
            rsRecherchePatientsNom = st.executeQuery(query);
            while (rsRecherchePatientsNom.next()) {
                ipp = rsRecherchePatientsNom.getInt("IPP");// pour avoir accÃ¨s a la colonne de ma table 
                nom = rsRecherchePatientsNom.getString("nom");
                prenom = rsRecherchePatientsNom.getString("prenom");
                dateNaissance = rsRecherchePatientsNom.getDate("dateNaissance");
                lieuNaissance = rsRecherchePatientsNom.getString("lieuNaissance");
                sexe = rsRecherchePatientsNom.getString("sexe");
                Patient p = new Patient(nom, prenom, ipp, dateNaissance, lieuNaissance, sexe);
                lp.add(p);
            }
            rsRecherchePatientsNom.close();
            st.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return lp;
    }

    //Methode retrouvant un patient enregistrÃ© dans la bdd par son prÃ©nom
    public ArrayList<Patient> recherchePatientsPrenom(String surname) {
        ResultSet rsRecherchePatientsPrenom;
        lp = new ArrayList<Patient>();
        try {
            Statement st = con.createStatement();
            String query = "select * from bd.d_m_a where prenom='" + surname + "'";
            rsRecherchePatientsPrenom = st.executeQuery(query);
            while (rsRecherchePatientsPrenom.next()) {
                ipp = rsRecherchePatientsPrenom.getInt("IPP");// pour avoir accÃ¨s a la colonne de ma table 
                nom = rsRecherchePatientsPrenom.getString("nom");
                prenom = rsRecherchePatientsPrenom.getString("prenom");
                dateNaissance = rsRecherchePatientsPrenom.getDate("dateNaissance");
                lieuNaissance = rsRecherchePatientsPrenom.getString("lieuNaissance");
                sexe = rsRecherchePatientsPrenom.getString("sexe");
                Patient p = new Patient(nom, prenom, ipp, dateNaissance, lieuNaissance, sexe);
                lp.add(p);
            }
            rsRecherchePatientsPrenom.close();
            st.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return lp;
    }

    //Methode retrouvant un patient enregistrÃ© dans la bdd par son ipp
    public Patient recherchePatientsIPP(int id) {
        ResultSet rsRecherchePAtientIPP;
        int identifiant = id;
        try {
            Statement st = con.createStatement();
            String query = "select * from d_m_a where IPP='" + identifiant + "'";
            rsRecherchePAtientIPP = st.executeQuery(query);
            while (rsRecherchePAtientIPP.next()) {
                ipp = rsRecherchePAtientIPP.getInt("IPP");// pour avoir accÃ¨s a la colonne de ma table 
                nom = rsRecherchePAtientIPP.getString("nom");
                prenom = rsRecherchePAtientIPP.getString("prenom");
                dateNaissance = rsRecherchePAtientIPP.getDate("dateNaissance");
                lieuNaissance = rsRecherchePAtientIPP.getString("lieuNaissance");
                sexe = rsRecherchePAtientIPP.getString("sexe");
                p = new Patient(nom, prenom, ipp, dateNaissance, lieuNaissance, sexe);
            }
            rsRecherchePAtientIPP.close();
            st.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return p;
    }

    //Methode retrouvant un patient enregistrÃ© dans la bdd par son nom et son prÃ©nom
    public ArrayList<Patient> recherchePatientsNomPrenom(String name, String surname) {
        ResultSet rsrecherchePatientsNomPrenom;
        lp = new ArrayList<Patient>();
        try {
            Statement st = con.createStatement();
            String query = "select * from bd.d_m_a where nom='" + name + "' AND prenom='" + surname + "'";
            rsrecherchePatientsNomPrenom = st.executeQuery(query);
            while (rsrecherchePatientsNomPrenom.next()) {
                ipp = rsrecherchePatientsNomPrenom.getInt("IPP");// pour avoir accÃ¨s a la colonne de ma table 
                nom = rsrecherchePatientsNomPrenom.getString("nom");
                prenom = rsrecherchePatientsNomPrenom.getString("prenom");
                dateNaissance = rsrecherchePatientsNomPrenom.getDate("dateNaissance");
                lieuNaissance = rsrecherchePatientsNomPrenom.getString("lieuNaissance");
                sexe = rsrecherchePatientsNomPrenom.getString("sexe");
                Patient p = new Patient(nom, prenom, ipp, dateNaissance, lieuNaissance, sexe);
                lp.add(p);
            }
            rsrecherchePatientsNomPrenom.close();
            st.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return lp;
    }

    //Methode retrouvant un patient enregistrÃ© dans la bdd par son nom, son prÃ©nom et son ipp
    public ArrayList<Patient> recherchePatientsNomPrenomIPP(String name, String surname, int identifiant) {
        ResultSet rsrecherchePatientsNomPrenomIPP;
        lp = new ArrayList<Patient>();
        try {
            Statement st = con.createStatement();
            String query = "select * from bd.d_m_a where nom='" + name + "' AND prenom='" + surname + "' AND IPP='" + identifiant + "'";
            rsrecherchePatientsNomPrenomIPP = st.executeQuery(query);
            while (rsrecherchePatientsNomPrenomIPP.next()) {
                ipp = rsrecherchePatientsNomPrenomIPP.getInt("IPP");// pour avoir accÃ¨s a la colonne de ma table 
                nom = rsrecherchePatientsNomPrenomIPP.getString("nom");
                prenom = rsrecherchePatientsNomPrenomIPP.getString("prenom");
                dateNaissance = rsrecherchePatientsNomPrenomIPP.getDate("dateNaissance");
                lieuNaissance = rsrecherchePatientsNomPrenomIPP.getString("lieuNaissance");
                sexe = rsrecherchePatientsNomPrenomIPP.getString("sexe");
                Patient p = new Patient(nom, prenom, ipp, dateNaissance, lieuNaissance, sexe);
                lp.add(p);
            }
            rsrecherchePatientsNomPrenomIPP.close();
            st.close();

        } catch (Exception ex) {
            System.out.println(ex);
        }
        return lp;
    }

    //Methode retrouvant un patient enregistrÃ© dans la bdd par son nom et son ipp
    public ArrayList<Patient> recherchePatientsNomIPP(String name, int identifiant) {
        ResultSet rsrecherchePatientsNomIPP;
        lp = new ArrayList<Patient>();
        try {
            Statement st = con.createStatement();
            String query = "select * from bd.d_m_a where nom='" + name + "' AND IPP='" + identifiant + "'";
            rsrecherchePatientsNomIPP = st.executeQuery(query);
            while (rsrecherchePatientsNomIPP.next()) {
                ipp = rsrecherchePatientsNomIPP.getInt("IPP");// pour avoir accÃ¨s a la colonne de ma table 
                nom = rsrecherchePatientsNomIPP.getString("nom");
                prenom = rsrecherchePatientsNomIPP.getString("prenom");
                dateNaissance = rsrecherchePatientsNomIPP.getDate("dateNaissance");
                lieuNaissance = rsrecherchePatientsNomIPP.getString("lieuNaissance");
                sexe = rsrecherchePatientsNomIPP.getString("sexe");
                Patient p = new Patient(nom, prenom, ipp, dateNaissance, lieuNaissance, sexe);
                lp.add(p);
            }
            rsrecherchePatientsNomIPP.close();
            st.close();

        } catch (Exception ex) {
            System.out.println(ex);
        }
        return lp;
    }

    //Methode retrouvant un patient enregistrÃ© dans la bdd par son prÃ©nom et son ipp
    public ArrayList<Patient> recherchePatientsPrenomIPP(String surname, int identifiant) {
        ResultSet rsrecherchePatientsPrenomIPP;
        lp = new ArrayList<Patient>();
        try {
            Statement st = con.createStatement();
            String query = "select * from bd.d_m_a where prenom='" + surname + "' AND IPP='" + identifiant + "'";
            rsrecherchePatientsPrenomIPP = st.executeQuery(query);
            while (rsrecherchePatientsPrenomIPP.next()) {
                ipp = rsrecherchePatientsPrenomIPP.getInt("IPP");// pour avoir accÃ¨s a la colonne de ma table 
                nom = rsrecherchePatientsPrenomIPP.getString("nom");
                prenom = rsrecherchePatientsPrenomIPP.getString("prenom");
                dateNaissance = rsrecherchePatientsPrenomIPP.getDate("dateNaissance");
                lieuNaissance = rsrecherchePatientsPrenomIPP.getString("lieuNaissance");
                sexe = rsrecherchePatientsPrenomIPP.getString("sexe");
                numeroVoie = rsrecherchePatientsPrenomIPP.getInt("numeroVoie");
                typeVoie = rsrecherchePatientsPrenomIPP.getString("typeVoie");
                complement = rsrecherchePatientsPrenomIPP.getString("complement");
                codePostal = rsrecherchePatientsPrenomIPP.getInt("codePostal");
                ville = rsrecherchePatientsPrenomIPP.getString("ville");
                pays = rsrecherchePatientsPrenomIPP.getString("pays");
                portable = rsrecherchePatientsPrenomIPP.getInt("portable");
                fixe = rsrecherchePatientsPrenomIPP.getInt("fixe");
                mail = rsrecherchePatientsPrenomIPP.getString("mail");
                p = new Patient(nom, prenom, ipp, dateNaissance, lieuNaissance, sexe, numeroVoie, typeVoie, complement, codePostal, ville, pays, portable, fixe, mail);
                lp.add(p);
            }
            rsrecherchePatientsPrenomIPP.close();
            st.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return lp;
    }

    //Methode retrouvant un patient enregistrÃ© dans la bdd par son nom, son prÃ©nom et sa date de naissance
    public Patient recherchePatientsNomPrenomDate(String name, String surname, Date date) {
        ResultSet rsrecherchePatientsNomPrenomDate;
        p = new Patient(null, null, null);
        try {
            Statement st = con.createStatement();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String d = format.format(date);
            String query = "select * from bd.d_m_a where nom='" + name + "' AND prenom='" + surname + "' AND dateNaissance='" + format.format(date) + "'";
            rsrecherchePatientsNomPrenomDate = st.executeQuery(query);
            while (rsrecherchePatientsNomPrenomDate.next()) {
                ipp = rsrecherchePatientsNomPrenomDate.getInt("IPP");// pour avoir accÃ¨s a la colonne de ma table 
                nom = rsrecherchePatientsNomPrenomDate.getString("nom");
                prenom = rsrecherchePatientsNomPrenomDate.getString("prenom");
                dateNaissance = rsrecherchePatientsNomPrenomDate.getDate("dateNaissance");
                lieuNaissance = rsrecherchePatientsNomPrenomDate.getString("lieuNaissance");
                sexe = rsrecherchePatientsNomPrenomDate.getString("sexe");
                numeroVoie = rsrecherchePatientsNomPrenomDate.getInt("numeroVoie");
                typeVoie = rsrecherchePatientsNomPrenomDate.getString("typeVoie");
                complement = rsrecherchePatientsNomPrenomDate.getString("complement");
                codePostal = rsrecherchePatientsNomPrenomDate.getInt("codePostal");
                ville = rsrecherchePatientsNomPrenomDate.getString("ville");
                pays = rsrecherchePatientsNomPrenomDate.getString("pays");
                portable = rsrecherchePatientsNomPrenomDate.getInt("portable");
                fixe = rsrecherchePatientsNomPrenomDate.getInt("fixe");
                mail = rsrecherchePatientsNomPrenomDate.getString("mail");
                p = new Patient(nom, prenom, ipp, dateNaissance, lieuNaissance, sexe, numeroVoie, typeVoie, complement, codePostal, ville, pays, portable, fixe, mail);
            }
            rsrecherchePatientsNomPrenomDate.close();
            st.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return p;
    }

    //MÃ©thode retournant le lit dont le numÃ©ro de lit est entrÃ© en paramÃ¨tre
    public Lit rechercherLit(String l) {
        int chambre;
        boolean f;
        Lit lit = null;

        try {
            ResultSet rs1;
            Statement st1 = con.createStatement();

            String query = "select * from lit where lit='" + l + "'";

            rs1 = st1.executeQuery(query);
            while (rs1.next()) {

                chambre = rs1.getInt("chambre");
                f = rs1.getBoolean("fenetre");

                lit = new Lit(l, chambre, f);

            }
            rs1.close();
            st1.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }

        return lit;
    }

    //MÃ©thode retournant tout les lits de l'hopital
    public ArrayList<Lit> getLits() {
        String num;
        ArrayList<Lit> lits = new ArrayList();
        int chambre;
        boolean f;
        Lit lit = null;

        try {
            ResultSet rs1;
            Statement st1 = con.createStatement();

            String query = "select * from Lit";

            rs1 = st1.executeQuery(query);
            while (rs1.next()) {
                num = rs1.getString("lit");
                chambre = rs1.getInt("chambre");
                f = rs1.getBoolean("fenetre");

                lit = new Lit(num, chambre, f);
                lits.add(lit);
            }
            rs1.close();
            st1.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return lits;

    }

    //MÃ©thode ajoutant un lit à  l'hopital
    public void ajouterLit(int num, int chambre, boolean f) {
        this.lit = num;
        this.chambre = chambre;
        this.fenetre = f;
        String sql = "insert into Lit(lit, chambre, fenetre) values (?,?,?)";
        try {
            PreparedStatement pstm = con.prepareStatement(sql);

            pstm.setInt(1, lit);
            pstm.setInt(2, this.chambre);
            pstm.setBoolean(3, this.fenetre);
            pstm.executeUpdate();

            pstm.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    //mÃ©thode retournant les lits de la chambre dont le numÃ©ro est entrÃ© en paramÃ¨tre
    public ArrayList<Lit> rechercherChambre(int chambre) {
        ArrayList<Lit> lits = new ArrayList();
        String l;
        boolean f;
        Lit lit;

        try {
            ResultSet rs1;
            Statement st1 = con.createStatement();

            String query = "select * from Lit where chambre=" + chambre;

            rs1 = st1.executeQuery(query);
            while (rs1.next()) {

                l = rs1.getString("lit");
                f = rs1.getBoolean("fenetre");

                lit = new Lit(l, chambre, f);
                lits.add(lit);
            }
            rs1.close();
            st1.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return lits;
    }

    //MÃ©thode permettant de modifier le lit d'un DM pour un patient
    public void insererLitDeManiereFurtive(int num, int iddm) {
        this.lit = num;

        String sql = "update d_m set lit =" + num + "  where id_dm=" + iddm;
        try {

            Statement stm = con.createStatement();
            boolean re;
            re = stm.execute(sql);

            stm.close();

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    //MÃ©thode retournant tout les lits d'un DM en cours
    public ArrayList<Lit> litDesDms() throws SQLException {
        ArrayList<DM> dms = new ArrayList();
        ArrayList<Lit> lits = new ArrayList();
        dms = this.getDmEnCours();

        for (DM d : dms) {
            lits.add(d.getLit());
        }

        return lits;

    }

    //MÃ©thode retournant tous les lits vacants
    public ArrayList<Lit> rechercheLitVacant() throws SQLException {
        ArrayList<Lit> lits = new ArrayList();
        ArrayList<Lit> litDM = new ArrayList();
        ArrayList<Lit> litVacant = new ArrayList();

        litDM = this.litDesDms();
        lits = this.getLits();
        int cpt = 0;
        for (Lit l1 : lits) {
            for (Lit l2 : litDM) {
                if (l1.getNum().equals(l2.getNum())) {
                    cpt++;
                }
            }

            if (cpt == 0) {
                litVacant.add(l1);
            } else {
                cpt = 0;
            }
        }
        return litVacant;

    }

    //MÃ©thode retournant tout les lits occupÃ©s
    public ArrayList<Lit> rechercheLitOccupe() throws SQLException {
        ArrayList<Lit> lits = new ArrayList();
        ArrayList<Lit> litDM = new ArrayList();
        ArrayList<Lit> litPris = new ArrayList();

        litDM = this.litDesDms();
        lits = this.getLits();
        int cpt = 0;
        for (Lit l1 : lits) {
            for (Lit l2 : litDM) {
                if (!l1.getNum().equals(l2.getNum())) {
                    cpt++;
                }
            }

            if (cpt == 0) {
                litPris.add(l1);
            } else {
                cpt = 0;
            }
        }
        return litPris;

    }

    //MÃ©thode retournant tous les DM n'ayant pas de lettre de sortie, soit tous les dm en cours
    public ArrayList<DM> getDmEnCours() throws SQLException {

        int iddm;
        String let;
        Date date;
        int ph;
        int ipp;
        String l;

        ArrayList<DM> dms = new ArrayList();

        try {
            ResultSet rs1;
            Statement st1 = con.createStatement();
            String query = "select * from d_m";
            rs1 = st1.executeQuery(query);
            while (rs1.next()) {

                iddm = rs1.getInt("id_dm");
                let = rs1.getString("lettre_Sortie");
                date = rs1.getDate("date");
                ipp = rs1.getInt("IPP");
                ph = rs1.getInt("PH");
                l = rs1.getString("lit");

                Patient pa = this.recherchePatientsIPP(ipp);
                Medecin me = this.rechercheMedecin(ph);
                Lit lit = this.rechercherLit(l);

                DM dem = new DM(pa, me, let, iddm, date, lit);
                if (let == null) {
                    dms.add(dem);
                }

            }
            rs1.close();
            st1.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return dms;
    }

    //MÃ©thode retournant le DM dont le patient utilise le lit dont le numÃ©ro est entrÃ© en paramÃ¨tre
    public DM GetDMLit(String lit) throws SQLException {

        ArrayList<DM> dms = new ArrayList();
        DM DmTheGoodOne = null;
        dms = this.getDmEnCours();

        for (DM dm : dms) {
            if (dm.getLit().getNum().equals(lit)) {
                DmTheGoodOne = dm;
            }
        }

        return DmTheGoodOne;

    }

    public int getAge(Date d) {
        Calendar curr = Calendar.getInstance();
        Calendar birth = Calendar.getInstance();
        birth.setTime(d);
        int yeardiff = curr.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
        curr.add(Calendar.YEAR, -yeardiff);
        if (birth.after(curr)) {
            yeardiff = yeardiff - 1;
        }
        return yeardiff;
    }

    //Recherche une pathologie dans les lettres de sortie des DM, les observations et les résultats
    public boolean getPatho(String patho, int ipp) {
        ArrayList<Observation> obs = new ArrayList();
        ArrayList<Resultat> res = new ArrayList();
        ArrayList<DM> dm = this.getDM();
        boolean pathoPresente = false;
        for (int i = 0; i < dm.size(); i++) {
            if (dm.get(i).getP().getIPP() == ipp && dm.get(i).getLet() != null) {
                String ls = dm.get(i).getLet();
                int pos = ls.indexOf(patho);
                if (pos >= 0) {
                    pathoPresente = true;
                    
                }
            }
        }
        if (!pathoPresente) {
            for (int i = 0; i < dm.size(); i++) {
                if (dm.get(i).getP().getIPP() == ipp) {
                    obs = this.getObservation(dm.get(i).getIddm());
                    for (int j = 0; j < obs.size(); j++) {
                        String s = obs.get(j).getObservation();
                        int pos = s.indexOf(patho);
                        if (pos >= 0) {
                            pathoPresente = true;
                        }
                    }
                }
            }
            if (!pathoPresente) {
                for (int i = 0; i < dm.size(); i++) {
                    if (dm.get(i).getP().getIPP() == ipp) {
                        res = this.getResultat(dm.get(i).getIddm());
                        for (int j = 0; j < res.size(); j++) {
                            String s = res.get(j).getResultat();
                            int pos = s.indexOf(patho);
                            if (pos >= 0) {
                                pathoPresente = true;
                            }
                        }
                    }
                }
            }
        }
        return pathoPresente;
    }

    public boolean isSain(Patient p){
        boolean sain = true;
        ArrayList<DM> ldm = this.getDMPatient(p.getIPP());
        for(int i =0;i<ldm.size();i++){
            if(ldm.get(i).getLet()==null){
                sain=false;
            }
        }
        return sain;
    }
}
