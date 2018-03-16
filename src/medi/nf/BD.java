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

    public BD() {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bd", "root", "");
           
        } catch (Exception ex) {
            System.out.println("error : " + ex);

        }

    }

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
            String query = "SELECT * FROM `users` WHERE `username` = '" + username + " ' ";
            rsConnection = st.executeQuery(query);
            while (rsConnection.next()) {
                u = rsConnection.getString("username");
                p = rsConnection.getString("password");
                r = rsConnection.getString("role");
                i = rsConnection.getInt("ID_user");
                if (!username.isEmpty() && p.equals(password) && r.equals("1")) {
                    res_co = 1;
                } else if (!username.isEmpty() && p.equals(password) && r.equals("2")) {
                    res_co = 2;
                } else {
                    res_co = 0;
                }
            }
            if (res_co == 1) {
                query = "SELECT * FROM `praticien` WHERE ID_user = " + i;
                ResultSet qs = st.executeQuery(query);
                while (qs.next()) {
                    user = new Medecin(qs.getString("nom"), qs.getString("prenom"), i, u, p, qs.getInt("telephone"), qs.getString("specialite"), qs.getString("service"));
                }
                qs.close();
            } else if (res_co == 2) {
                query = "SELECT * FROM `secretaire` WHERE ID_user = " + i;
                rsConnection = st.executeQuery(query);
                while (rsConnection.next()) {
                    user = new SecretaireAdmin(rsConnection.getString("nom"), rsConnection.getString("prenom"), i, u, p, rsConnection.getInt("telephone"));
                }
            }
            rsConnection.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return user;
    }

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
            javax.swing.JOptionPane.showMessageDialog(null, "Dossier Médico-Administratif ajouté à la base de données", "Patient ajouté", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            System.out.println(ex);
            javax.swing.JOptionPane.showMessageDialog(null, "Informations incorrectes", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

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

    public void ajouterResultat(String id, int PH, String resultat, int iddm) {

        String sql = "insert into resultats(id_res, date, id_dm, PH, resultat) values (?,?,?,?,?)";
        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        try {
            PreparedStatement pstmAjouterResultat = con.prepareStatement(sql);

            pstmAjouterResultat.setString(1, id);
            pstmAjouterResultat.setDate(2, date);
            pstmAjouterResultat.setInt(3, iddm);
            pstmAjouterResultat.setInt(4, PH);
            pstmAjouterResultat.setString(5, resultat);

            pstmAjouterResultat.executeUpdate();
            pstmAjouterResultat.close();
        } catch (Exception ex) {
            System.out.println(ex);
            javax.swing.JOptionPane.showMessageDialog(null, "Informations incorrectes", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

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

                Patient pc = this.recherchePatientsIPP(ipp);

                DM dm = new DM(pc, m, lettre, iddm, date);
                dms.add(dm);

            }
            rsSejourEnCours.close();

        } catch (Exception ex) {
            System.out.println(ex);
        }

        return dms;

    }

    public void ajouterDM(DM d) {

        int ipp = d.getP().getIPP();
        String let = d.getLet();
        int idmed = d.getMedref().getId_user();
        Date date = d.getDate();

        String sql = "insert into d_m(IPP, date, PH, lettre_Sortie, id_dm) values (?,?,?,?,?)";
        try {
            PreparedStatement pstmAjouterDM = con.prepareStatement(sql);

            pstmAjouterDM.setInt(1, ipp);
            pstmAjouterDM.setDate(2, date);
            pstmAjouterDM.setInt(3, idmed);
            pstmAjouterDM.setString(4, let);
            pstmAjouterDM.setInt(5, this.genererIDDM());
            pstmAjouterDM.executeUpdate();
            javax.swing.JOptionPane.showMessageDialog(null, "Dossier Médical créé.", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
            pstmAjouterDM.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

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
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return iddm;
    }

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
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return idp;
    }

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
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return idp;
    }

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
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return idp;
    }

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
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return idp;
    }

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
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return idp;
    }

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
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return idp;
    }

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
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return idp;
    }

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
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return idp;
    }

    public ArrayList getDM(Medecin m) {
        ResultSet rsGetDMMedecin = null, rs1, rs2;
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
                    ipp = rsGetDMMedecin.getInt("IPP");// pour avoir accÃ¨s a la colonne de ma table 
                    ph = rsGetDMMedecin.getInt("PH");
                    date = rsGetDMMedecin.getDate("date");
                    lettre = rsGetDMMedecin.getString("lettre_Sortie");
                    iddm = rsGetDMMedecin.getInt("id_dm");

                    String query2 = "select * from users WHERE ID_user = " + ph;
                    rs2 = st2.executeQuery(query2);
                    while (rs2.next()) {
                        String username = rs2.getString("username");
                        String mdp = rs2.getString("password");
                        m = new Medecin(nom, prenom, ph, username, mdp, tel, spe, ser);

                    }
                    rs2.close();

                    query = "select * from d_m_a WHERE IPP = " + ipp;
                    rs1 = st1.executeQuery(query);
                    while (rs1.next()) {
                        nom = rs1.getString("nom");
                        prenom = rs1.getString("prenom");
                        dateN = rs1.getDate("dateNaissance");
                        DM dm = new DM(new Patient(nom, prenom, dateN, ipp), m, lettre, iddm, date);
                        dms.add(dm);
                    }

                }

            }
            rsGetDMMedecin.close();
            rs1.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return dms;
    }

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

                dm = new DM(p, m, lettre, iddm, date);
            }
            rsGetDMIDDM.close();
            rs1.close();
            rs2.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return dm;
    }

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
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return prescription;
    }

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
        } catch (Exception ex) {
            System.out.println(ex);
        }

        return obs;
    }

    public ArrayList<Resultat> getResultat(int iddm) {
        ResultSet rsGetResultatiddm;
        ArrayList<Resultat> res = new ArrayList<>();
        int ph = 0;
        String resultat = null;
        Date date = null;
        try {
            Statement st = con.createStatement();
            String query = "select * from resultats where id_dm = " + iddm;
            rsGetResultatiddm = st.executeQuery(query);
            while (rsGetResultatiddm.next()) {
                ph = rsGetResultatiddm.getInt("PH");
                date = rsGetResultatiddm.getDate("date");
                iddm = rsGetResultatiddm.getInt("id_dm");
                resultat = rsGetResultatiddm.getString("resultat");
                Medecin m = this.rechercheMedecin(ph);
                res.add(new Resultat(date, m, iddm, resultat));
            }
            rsGetResultatiddm.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }

        return res;
    }

    public ArrayList<OperationInfirmiere> getOperationInfirmiere(int iddm) {
        ResultSet rsGetOperationInfirmiereiddm;
        ArrayList<OperationInfirmiere> opinf = new ArrayList<>();
        int ph = 0;
        String operation = null;
        Date date = null;
        try {
            Statement st = con.createStatement();
            String query = "select * from resultats where id_dm = " + iddm;
            rsGetOperationInfirmiereiddm = st.executeQuery(query);
            while (rsGetOperationInfirmiereiddm.next()) {
                ph = rsGetOperationInfirmiereiddm.getInt("PH");
                date = rsGetOperationInfirmiereiddm.getDate("date");
                iddm = rsGetOperationInfirmiereiddm.getInt("id_dm");
                operation = rsGetOperationInfirmiereiddm.getString("resultat");
                Medecin m = this.rechercheMedecin(ph);
                opinf.add(new OperationInfirmiere(date, m, iddm, operation));
            }
            rsGetOperationInfirmiereiddm.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }

        return opinf;
    }

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

        try {
            Statement st = con.createStatement();
            Statement st1 = con.createStatement();
            ResultSet rs1;
            Statement st2 = con.createStatement();
            ResultSet rs2;

            String query = "select * from d_m_a WHERE IPP = " + ipp;
            rs1 = st1.executeQuery(query);
            while (rs1.next()) {
                nom = rs1.getString("nom");
                prenom = rs1.getString("prenom");
                dateN = rs1.getDate("dateNaissance");
                p = new Patient(nom, prenom, dateN);
            }

            query = "select * from d_m where IPP = " + ipp;
            rsGetDMPatientipp = st.executeQuery(query);
            while (rsGetDMPatientipp.next()) {
                ph = rsGetDMPatientipp.getInt("PH");
                date = rsGetDMPatientipp.getDate("date");
                lettre = rsGetDMPatientipp.getString("lettre_Sortie");
                iddm = rsGetDMPatientipp.getInt("id_dm");

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
                    rs2.close();
                }
                DM dm = new DM(p, m, lettre, iddm, date);
                dms.add(dm);
            }
            rsGetDMPatientipp.close();
            rs1.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return dms;
    }

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
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return ipp;
    }

    public ArrayList<Patient> getPatients() {
        ResultSet rsGetPatients;
        lp = new ArrayList<Patient>();
        try {
            Statement st = con.createStatement();
            String query = "select * from bd.d_m_a";
            rsGetPatients = st.executeQuery(query);
            while (rsGetPatients.next()) {
                ipp = rsGetPatients.getInt("IPP");// pour avoir accès a la colonne de ma table 
                nom = rsGetPatients.getString("nom");
                prenom = rsGetPatients.getString("prenom");
                dateNaissance = rsGetPatients.getDate("dateNaissance");
                lieuNaissance = rsGetPatients.getString("lieuNaissance");
                sexe = rsGetPatients.getString("sexe");
                Patient p = new Patient(nom, prenom, ipp, dateNaissance, lieuNaissance, sexe);
                lp.add(p);
            }
            rsGetPatients.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return lp;
    }

    public Medecin rechercheMedecin(int id) {
        ResultSet rsRechercheMedecinID;
        String username = null;
        String mdp = null;
        int tel = 0;
        String spe = null;
        String ser = null;
        try {
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

            query = "select * from users WHERE ID_user = " + id;
            rsRechercheMedecinID = st.executeQuery(query);
            while (rsRechercheMedecinID.next()) {
                username = rsRechercheMedecinID.getString("username");
                mdp = rsRechercheMedecinID.getString("password");
            }

            m = new Medecin(nom, prenom, id, username, mdp, tel, spe, ser);
            rsRechercheMedecinID.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return m;
    }

    public ArrayList<Medecin> getMedecins() {
        ResultSet rsGetMedecins, rsGetMedecins2;
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
                }
                rsGetMedecins2.close();
                rsGetMedecins.close();
                lm.add(m1);
            }

        } catch (Exception ex) {
            System.out.println(ex);
        }
        return lm;
    }

    public ArrayList<Patient> recherchePatientsNom(String name) {
        ResultSet rsRecherchePatientsNom;
        lp = new ArrayList<Patient>();
        try {
            Statement st = con.createStatement();
            String query = "select * from bd.d_m_a where nom='" + name + "'";
            rsRecherchePatientsNom = st.executeQuery(query);
            while (rsRecherchePatientsNom.next()) {
                ipp = rsRecherchePatientsNom.getInt("IPP");// pour avoir accès a la colonne de ma table 
                nom = rsRecherchePatientsNom.getString("nom");
                prenom = rsRecherchePatientsNom.getString("prenom");
                dateNaissance = rsRecherchePatientsNom.getDate("dateNaissance");
                lieuNaissance = rsRecherchePatientsNom.getString("lieuNaissance");
                sexe = rsRecherchePatientsNom.getString("sexe");
                Patient p = new Patient(nom, prenom, ipp, dateNaissance, lieuNaissance, sexe);
                lp.add(p);
            }
            rsRecherchePatientsNom.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return lp;
    }

    public ArrayList<Patient> recherchePatientsPrenom(String surname) {
        ResultSet rsRecherchePatientsPrenom;
        lp = new ArrayList<Patient>();
        try {
            Statement st = con.createStatement();
            String query = "select * from bd.d_m_a where prenom='" + surname + "'";
            rsRecherchePatientsPrenom = st.executeQuery(query);
            while (rsRecherchePatientsPrenom.next()) {
                ipp = rsRecherchePatientsPrenom.getInt("IPP");// pour avoir accès a la colonne de ma table 
                nom = rsRecherchePatientsPrenom.getString("nom");
                prenom = rsRecherchePatientsPrenom.getString("prenom");
                dateNaissance = rsRecherchePatientsPrenom.getDate("dateNaissance");
                lieuNaissance = rsRecherchePatientsPrenom.getString("lieuNaissance");
                sexe = rsRecherchePatientsPrenom.getString("sexe");
                Patient p = new Patient(nom, prenom, ipp, dateNaissance, lieuNaissance, sexe);
                lp.add(p);
            }
            rsRecherchePatientsPrenom.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return lp;
    }

    public Patient recherchePatientsIPP(int id) {
        ResultSet rsRecherchePAtientIPP;
        int identifiant = id;
        try {
            Statement st = con.createStatement();
            String query = "select * from d_m_a where IPP='" + identifiant + "'";
            rsRecherchePAtientIPP = st.executeQuery(query);
            while (rsRecherchePAtientIPP.next()) {
                ipp = rsRecherchePAtientIPP.getInt("IPP");// pour avoir accès a la colonne de ma table 
                nom = rsRecherchePAtientIPP.getString("nom");
                prenom = rsRecherchePAtientIPP.getString("prenom");
                dateNaissance = rsRecherchePAtientIPP.getDate("dateNaissance");
                lieuNaissance = rsRecherchePAtientIPP.getString("lieuNaissance");
                sexe = rsRecherchePAtientIPP.getString("sexe");
                p = new Patient(nom, prenom, ipp, dateNaissance, lieuNaissance, sexe);
            }
            rsRecherchePAtientIPP.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return p;
    }

    public ArrayList<Patient> recherchePatientsNomPrenom(String name, String surname) {
        ResultSet rsrecherchePatientsNomPrenom;
        lp = new ArrayList<Patient>();
        try {
            Statement st = con.createStatement();
            String query = "select * from bd.d_m_a where nom='" + name + "' AND prenom='" + surname + "'";
            rsrecherchePatientsNomPrenom = st.executeQuery(query);
            while (rsrecherchePatientsNomPrenom.next()) {
                ipp = rsrecherchePatientsNomPrenom.getInt("IPP");// pour avoir accès a la colonne de ma table 
                nom = rsrecherchePatientsNomPrenom.getString("nom");
                prenom = rsrecherchePatientsNomPrenom.getString("prenom");
                dateNaissance = rsrecherchePatientsNomPrenom.getDate("dateNaissance");
                lieuNaissance = rsrecherchePatientsNomPrenom.getString("lieuNaissance");
                sexe = rsrecherchePatientsNomPrenom.getString("sexe");
                Patient p = new Patient(nom, prenom, ipp, dateNaissance, lieuNaissance, sexe);
                lp.add(p);
            }
            rsrecherchePatientsNomPrenom.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return lp;
    }

    public ArrayList<Patient> recherchePatientsNomPrenomIPP(String name, String surname, int identifiant) {
        ResultSet rsrecherchePatientsNomPrenomIPP;
        lp = new ArrayList<Patient>();
        try {
            Statement st = con.createStatement();
            String query = "select * from bd.d_m_a where nom='" + name + "' AND prenom='" + surname + "' AND IPP='" + identifiant + "'";
            rsrecherchePatientsNomPrenomIPP = st.executeQuery(query);
            while (rsrecherchePatientsNomPrenomIPP.next()) {
                ipp = rsrecherchePatientsNomPrenomIPP.getInt("IPP");// pour avoir accès a la colonne de ma table 
                nom = rsrecherchePatientsNomPrenomIPP.getString("nom");
                prenom = rsrecherchePatientsNomPrenomIPP.getString("prenom");
                dateNaissance = rsrecherchePatientsNomPrenomIPP.getDate("dateNaissance");
                lieuNaissance = rsrecherchePatientsNomPrenomIPP.getString("lieuNaissance");
                sexe = rsrecherchePatientsNomPrenomIPP.getString("sexe");
                Patient p = new Patient(nom, prenom, ipp, dateNaissance, lieuNaissance, sexe);
                lp.add(p);
            }
            rsrecherchePatientsNomPrenomIPP.close();
            
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return lp;
    }

    public ArrayList<Patient> recherchePatientsNomIPP(String name, int identifiant) {
        ResultSet rsrecherchePatientsNomIPP;
        lp = new ArrayList<Patient>();
        try {
            Statement st = con.createStatement();
            String query = "select * from bd.d_m_a where nom='" + name + "' AND IPP='" + identifiant + "'";
            rsrecherchePatientsNomIPP = st.executeQuery(query);
            while (rsrecherchePatientsNomIPP.next()) {
                ipp = rsrecherchePatientsNomIPP.getInt("IPP");// pour avoir accès a la colonne de ma table 
                nom = rsrecherchePatientsNomIPP.getString("nom");
                prenom = rsrecherchePatientsNomIPP.getString("prenom");
                dateNaissance = rsrecherchePatientsNomIPP.getDate("dateNaissance");
                lieuNaissance = rsrecherchePatientsNomIPP.getString("lieuNaissance");
                sexe = rsrecherchePatientsNomIPP.getString("sexe");
                Patient p = new Patient(nom, prenom, ipp, dateNaissance, lieuNaissance, sexe);
                lp.add(p);
            }
            rsrecherchePatientsNomIPP.close();
            
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return lp;
    }

    public ArrayList<Patient> recherchePatientsPrenomIPP(String surname, int identifiant) {
        ResultSet rsrecherchePatientsPrenomIPP;
        lp = new ArrayList<Patient>();
        try {
            Statement st = con.createStatement();
            String query = "select * from bd.d_m_a where prenom='" + surname + "' AND IPP='" + identifiant + "'";
            rsrecherchePatientsPrenomIPP = st.executeQuery(query);
            while (rsrecherchePatientsPrenomIPP.next()) {
                ipp = rsrecherchePatientsPrenomIPP.getInt("IPP");// pour avoir accès a la colonne de ma table 
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
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return lp;
    }

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
                ipp = rsrecherchePatientsNomPrenomDate.getInt("IPP");// pour avoir accès a la colonne de ma table 
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
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return p;
    }

}
