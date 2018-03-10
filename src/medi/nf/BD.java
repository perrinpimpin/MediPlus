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
    private Statement st;
    private ResultSet rs;
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
            st = con.createStatement();

        } catch (Exception ex) {
            System.out.println("error : " + ex);

        }

    }

    public User connection(String username, String password) {
        int res_co = 0;
        int i = 0;
        User user = null;
        String u = null;
        String p = null;
        String r = null;
        try {
            String query = "SELECT * FROM `users` WHERE `username` = '" + username + " ' ";
            rs = st.executeQuery(query);
            while (rs.next()) {
                u = rs.getString("username");
                p = rs.getString("password");
                r = rs.getString("role");
                i = rs.getInt("ID_user");
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
            } else if (res_co == 2) {
                query = "SELECT * FROM `secretaire` WHERE ID_user = " + i;
                rs = st.executeQuery(query);
                while (rs.next()) {
                    user = new SecretaireAdmin(rs.getString("nom"), rs.getString("prenom"), i, u, p, rs.getInt("telephone"));
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return user;
    }

    public void ajouterLogin(int id_user, String username, String password, int role) {
        //roles : 1=medecin, 2=SA
        String sql = "insert into users(id_user,username,password,role) values (?,?,?,?)";

        try {
            PreparedStatement pstm = con.prepareStatement(sql);

            pstm.setInt(1, id_user);
            pstm.setString(2, username);
            pstm.setString(3, password);
            pstm.setInt(4, role);
            pstm.executeUpdate();

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void ajouterMedecin(int ID_user, String nom, String prenom, int telephone, String specialite, String service) {
        String sql = "insert into praticien(ID_user,nom,prenom,telephone,specialite,service) values (?,?,?,?,?,?)";

        try {
            PreparedStatement pstm = con.prepareStatement(sql);

            pstm.setInt(1, ID_user);
            pstm.setString(2, nom);
            pstm.setString(3, prenom);
            pstm.setInt(4, telephone);
            pstm.setString(5, specialite);
            pstm.setString(6, service);
            pstm.executeUpdate();

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void ajouterSA(int ID_user, String nom, String prenom, int telephone) {
        String sql = "insert into secretaire(ID_user,nom,prenom,telephone) values (?,?,?,?)";

        try {
            PreparedStatement pstm = con.prepareStatement(sql);

            pstm.setInt(1, ID_user);
            pstm.setString(2, nom);
            pstm.setString(3, prenom);
            pstm.setInt(4, telephone);
            pstm.executeUpdate();

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void ajouterDMA(String nom, String prenom, Date dateNaissance, String lieuNaissance, String sexe, int numeroVoie, String typeVoie, String complement, int codePostal, String ville, String pays, int portable, int fixe, String mail) {

        String sql = "insert into d_m_a(IPP, nom, prenom, dateNaissance, lieuNaissance, sexe, numeroVoie, typeVoie, complement, codePostal, ville, pays, portable, fixe, mail) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        int ipp = this.genererIPP();
        try {
            PreparedStatement pstm = con.prepareStatement(sql);

            pstm.setInt(1, ipp);
            pstm.setString(2, nom);
            pstm.setString(3, prenom);
            pstm.setDate(4, dateNaissance);
            pstm.setString(5, lieuNaissance);
            pstm.setString(6, sexe);
            pstm.setInt(7, numeroVoie);
            pstm.setString(8, typeVoie);
            pstm.setString(9, complement);
            pstm.setInt(10, codePostal);
            pstm.setString(11, ville);
            pstm.setString(12, pays);
            pstm.setInt(13, portable);
            pstm.setInt(14, fixe);
            pstm.setString(15, mail);

            pstm.executeUpdate();
            javax.swing.JOptionPane.showMessageDialog(null, "Dossier Médico-Administratif ajouté à la base de données", "Patient ajouté", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            System.out.println(ex);
            javax.swing.JOptionPane.showMessageDialog(null, "Informations incorrectes", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void modifierPatient(Patient p) {
        PreparedStatement pstmt = null;
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
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, p.getNumeroVoie());
            pstmt.setString(2, p.getTypeVoie());
            pstmt.setString(3, p.getComplement());
            pstmt.setInt(4, p.getCodePostal());
            pstmt.setString(5, p.getVille());
            pstmt.setString(6, p.getPays());
            pstmt.setInt(7, p.getTel());
            pstmt.setInt(8, p.getFixe());
            pstmt.setString(9, p.getMail());
            pstmt.setInt(10, p.getIPP());
            pstmt.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void ajouterDM(DM d) {

        int ipp = d.getP().getIPP();
        String let = d.getLet();
        String res = d.getResultats();
        String obs = d.getObservation();
        String inf = d.getOpInf();
        String pres = d.getPres();
        int idmed = d.getMedref().getId_user();
        Date date = d.getDate();

        String sql = "insert into d_m(IPP, date, PH, lettre_Sortie, id_dm, observation, prescription, Resultat, opération_Infirmière) values (?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pstm = con.prepareStatement(sql);

            pstm.setInt(1, ipp);
            pstm.setDate(2, date);
            pstm.setInt(3, idmed);
            pstm.setString(4, let);
            pstm.setInt(5, this.genererIDDM());
            pstm.setString(6, obs);
            pstm.setString(7, pres);
            pstm.setString(8, res);
            pstm.setString(9, inf);
            pstm.executeUpdate();

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public int genererIDDM() {
        int iddm = 0;
        DateFormat dateFormat = new SimpleDateFormat("yyMM");
        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        String annee = dateFormat.format(date);
        try {
            String query = "select max(id_dm) from d_m where substring(id_dm,1,4)=" + Integer.parseInt(annee);
            rs = st.executeQuery(query);
            rs.next();
            if (rs.getInt("max(id_dm)") != 0) {
                iddm = rs.getInt("max(id_dm)") + 1;
            } else {
                iddm = Integer.parseInt(annee) * 100000;
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        //System.out.println(ipp);
        return iddm;
    }
    
        public int genererIDPrescription(Patient p) {
        int idp = 0;
        p.getIPP();
        DateFormat dateFormat = new SimpleDateFormat("yyMM");
        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        String annee = dateFormat.format(date);
        try {
            String query = "select max(id_dm) from d_m where substring(id_dm,1,4)=" + Integer.parseInt(annee);
            rs = st.executeQuery(query);
            rs.next();
            if (rs.getInt("max(id_dm)") != 0) {
                idp = rs.getInt("max(id_dm)") + 1;
            } else {
                idp = Integer.parseInt(annee) * 100000;
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        //System.out.println(ipp);
        return idp;
    }

    public ArrayList getDM(Medecin m) {
        int ipp = 0;
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
        Date date = null;
        ArrayList<DM> dms = new ArrayList();
        String ser = m.getService();

        try {
            Statement st1 = con.createStatement();
            ResultSet rs1;
            Statement st2 = con.createStatement();
            ResultSet rs2;

            String query = "select * from praticien WHERE service = '" + ser + "'";
            rs1 = st1.executeQuery(query);
            while (rs1.next()) {
                nom = rs1.getString("nom");
                prenom = rs1.getString("prenom");
                int tel = rs1.getInt("telephone");
                String spe = rs1.getString("specialite");
                int id = rs1.getInt("id_user");

                query = "select * from d_m where PH = " + id;
                rs = st.executeQuery(query);
                while (rs.next()) {
                    ipp = rs.getInt("IPP");// pour avoir accÃ¨s a la colonne de ma table 
                    ph = rs.getInt("PH");
                    date = rs.getDate("date");
                    lettre = rs.getString("lettre_Sortie");
                    iddm = rs.getInt("id_dm");
                    obs = rs.getString("observation");
                    prescription = rs.getString("prescription");
                    resultat = rs.getString("Resultat");
                    opeInf = rs.getString("opération_Infirmière");

                    String query2 = "select * from users WHERE ID_user = " + ph;
                    rs2 = st2.executeQuery(query2);
                    while (rs2.next()) {
                        String username = rs2.getString("username");
                        String mdp = rs2.getString("password");
                        m = new Medecin(nom, prenom, ph, username, mdp, tel, spe, ser);

                    }

                    query = "select * from d_m_a WHERE IPP = " + ipp;
                    rs1 = st1.executeQuery(query);
                    while (rs1.next()) {
                        nom = rs1.getString("nom");
                        prenom = rs1.getString("prenom");
                        dateN = rs1.getDate("dateNaissance");
                        p = new Patient(nom, prenom, dateN);
                        DM dm = new DM(p, m, lettre, iddm, obs, prescription, resultat, opeInf, date);
                        dms.add(dm);
                    }

                }

            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return dms;
    }

    public ArrayList<DM> getDMPatient(int ipp) {

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
            rs = st.executeQuery(query);
            while (rs.next()) {
                ph = rs.getInt("PH");
                date = rs.getDate("date");
                lettre = rs.getString("lettre_Sortie");
                iddm = rs.getInt("id_dm");
                obs = rs.getString("observation");
                prescription = rs.getString("prescription");
                resultat = rs.getString("Resultat");
                opeInf = rs.getString("opération_Infirmière");

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
                DM dm = new DM(p, m, lettre, iddm, obs, prescription, resultat, opeInf, date);
                dms.add(dm);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return dms;
    }

    public int genererIPP() {
        int ipp = 0;
        DateFormat dateFormat = new SimpleDateFormat("yy");
        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        String annee = dateFormat.format(date);
        try {
            String query = "select max(IPP) from bd.d_m_a where substring(ipp,1,2)=" + Integer.parseInt(annee);
            rs = st.executeQuery(query);
            rs.next();
            if (rs.getInt("max(IPP)") != 0) {
                ipp = rs.getInt("max(IPP)") + 1;
                System.out.println(rs.getInt("max(IPP)"));
            } else {
                ipp = Integer.parseInt(annee) * 10000000;
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        //System.out.println(ipp);
        return ipp;
    }

    public ArrayList<Patient> getPatients() {
        lp = new ArrayList<Patient>();
        try {
            String query = "select * from bd.d_m_a";
            rs = st.executeQuery(query);
            while (rs.next()) {
                ipp = rs.getInt("IPP");// pour avoir accès a la colonne de ma table 
                nom = rs.getString("nom");
                prenom = rs.getString("prenom");
                dateNaissance = rs.getDate("dateNaissance");
                lieuNaissance = rs.getString("lieuNaissance");
                sexe = rs.getString("sexe");
                Patient p = new Patient(nom, prenom, ipp, dateNaissance, lieuNaissance, sexe);
                lp.add(p);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return lp;
    }

    public Medecin rechercheMedecin(int id) {
        try {
            String query = "select * from praticien where ID_user='" + id + "'";
            rs = st.executeQuery(query);
            query = "select * from praticien WHERE ID_user = " + id;
            rs = st.executeQuery(query);
            while (rs.next()) {
                nom = rs.getString("nom");
                prenom = rs.getString("prenom");
                int tel = rs.getInt("telephone");
                String spe = rs.getString("specialite");
                String ser = rs.getString("service");

                String query2 = "select * from users WHERE ID_user = " + id;
                rs = st.executeQuery(query2);
                while (rs.next()) {
                    String username = rs.getString("username");
                    String mdp = rs.getString("password");
                    m = new Medecin(nom, prenom, id, username, mdp, tel, spe, ser);
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return m;
    }

    public ArrayList<Patient> recherchePatientsNom(String name) {
        lp = new ArrayList<Patient>();
        try {
            String query = "select * from bd.d_m_a where nom='" + name + "'";
            rs = st.executeQuery(query);
            while (rs.next()) {
                ipp = rs.getInt("IPP");// pour avoir accès a la colonne de ma table 
                nom = rs.getString("nom");
                prenom = rs.getString("prenom");
                dateNaissance = rs.getDate("dateNaissance");
                lieuNaissance = rs.getString("lieuNaissance");
                sexe = rs.getString("sexe");
                Patient p = new Patient(nom, prenom, ipp, dateNaissance, lieuNaissance, sexe);
                lp.add(p);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return lp;
    }

    public ArrayList<Patient> recherchePatientsPrenom(String surname) {
        lp = new ArrayList<Patient>();
        try {
            String query = "select * from bd.d_m_a where prenom='" + surname + "'";
            rs = st.executeQuery(query);
            while (rs.next()) {
                ipp = rs.getInt("IPP");// pour avoir accès a la colonne de ma table 
                nom = rs.getString("nom");
                prenom = rs.getString("prenom");
                dateNaissance = rs.getDate("dateNaissance");
                lieuNaissance = rs.getString("lieuNaissance");
                sexe = rs.getString("sexe");
                Patient p = new Patient(nom, prenom, ipp, dateNaissance, lieuNaissance, sexe);
                lp.add(p);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return lp;
    }

    public Patient recherchePatientsIPP(int identifiant) {
        try {
            String query = "select * from d_m_a where IPP='" + identifiant + "'";
            rs = st.executeQuery(query);
            while (rs.next()) {
                ipp = rs.getInt("IPP");// pour avoir accès a la colonne de ma table 
                nom = rs.getString("nom");
                prenom = rs.getString("prenom");
                dateNaissance = rs.getDate("dateNaissance");
                lieuNaissance = rs.getString("lieuNaissance");
                sexe = rs.getString("sexe");
                p = new Patient(nom, prenom, ipp, dateNaissance, lieuNaissance, sexe);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return p;
    }

    public ArrayList<Patient> recherchePatientsNomPrenom(String name, String surname) {
        lp = new ArrayList<Patient>();
        try {
            String query = "select * from bd.d_m_a where nom='" + name + "' AND prenom='" + surname + "'";
            rs = st.executeQuery(query);
            System.out.println("contenu de la base de donnée");
            while (rs.next()) {
                ipp = rs.getInt("IPP");// pour avoir accès a la colonne de ma table 
                nom = rs.getString("nom");
                prenom = rs.getString("prenom");
                dateNaissance = rs.getDate("dateNaissance");
                lieuNaissance = rs.getString("lieuNaissance");
                sexe = rs.getString("sexe");
                Patient p = new Patient(nom, prenom, ipp, dateNaissance, lieuNaissance, sexe);
                lp.add(p);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return lp;
    }

    public ArrayList<Patient> recherchePatientsNomPrenomIPP(String name, String surname, int identifiant) {
        lp = new ArrayList<Patient>();
        try {
            String query = "select * from bd.d_m_a where nom='" + name + "' AND prenom='" + surname + "' AND IPP='" + identifiant + "'";
            rs = st.executeQuery(query);
            System.out.println("contenu de la base de donnée");
            while (rs.next()) {
                ipp = rs.getInt("IPP");// pour avoir accès a la colonne de ma table 
                nom = rs.getString("nom");
                prenom = rs.getString("prenom");
                dateNaissance = rs.getDate("dateNaissance");
                lieuNaissance = rs.getString("lieuNaissance");
                sexe = rs.getString("sexe");
                Patient p = new Patient(nom, prenom, ipp, dateNaissance, lieuNaissance, sexe);
                lp.add(p);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return lp;
    }

    public ArrayList<Patient> recherchePatientsNomIPP(String name, int identifiant) {
        lp = new ArrayList<Patient>();
        try {
            String query = "select * from bd.d_m_a where nom='" + name + "' AND IPP='" + identifiant + "'";
            rs = st.executeQuery(query);
            System.out.println("contenu de la base de donnée");
            while (rs.next()) {
                ipp = rs.getInt("IPP");// pour avoir accès a la colonne de ma table 
                nom = rs.getString("nom");
                prenom = rs.getString("prenom");
                dateNaissance = rs.getDate("dateNaissance");
                lieuNaissance = rs.getString("lieuNaissance");
                sexe = rs.getString("sexe");
                Patient p = new Patient(nom, prenom, ipp, dateNaissance, lieuNaissance, sexe);
                lp.add(p);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return lp;
    }

    public ArrayList<Patient> recherchePatientsPrenomIPP(String surname, int identifiant) {
        lp = new ArrayList<Patient>();
        try {
            String query = "select * from bd.d_m_a where prenom='" + surname + "' AND IPP='" + identifiant + "'";
            rs = st.executeQuery(query);
            System.out.println("contenu de la base de donnée");
            while (rs.next()) {
                ipp = rs.getInt("IPP");// pour avoir accès a la colonne de ma table 
                nom = rs.getString("nom");
                prenom = rs.getString("prenom");
                dateNaissance = rs.getDate("dateNaissance");
                lieuNaissance = rs.getString("lieuNaissance");
                sexe = rs.getString("sexe");
                numeroVoie = rs.getInt("numeroVoie");
                typeVoie = rs.getString("typeVoie");
                complement = rs.getString("complement");
                codePostal = rs.getInt("codePostal");
                ville = rs.getString("ville");
                pays = rs.getString("pays");
                portable = rs.getInt("portable");
                fixe = rs.getInt("fixe");
                mail = rs.getString("mail");
                p = new Patient(nom, prenom, ipp, dateNaissance, lieuNaissance, sexe, numeroVoie, typeVoie, complement, codePostal, ville, pays, portable, fixe, mail);
                lp.add(p);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return lp;
    }
//date.getYear() + "-" + date.getMonth() + "-" + date.getDate() + "'"

    public Patient recherchePatientsNomPrenomDate(String name, String surname, Date date) {
        p = new Patient(null, null, null);
        try {
            String query = "select * from bd.d_m_a where nom='" + name + "' AND prenom='" + surname + "' AND dateNaissance='" + date.toString() + "'";
            rs = st.executeQuery(query);
            while (rs.next()) {
                ipp = rs.getInt("IPP");// pour avoir accès a la colonne de ma table 
                nom = rs.getString("nom");
                prenom = rs.getString("prenom");
                dateNaissance = rs.getDate("dateNaissance");
                lieuNaissance = rs.getString("lieuNaissance");
                sexe = rs.getString("sexe");
                numeroVoie = rs.getInt("numeroVoie");
                typeVoie = rs.getString("typeVoie");
                complement = rs.getString("complement");
                codePostal = rs.getInt("codePostal");
                ville = rs.getString("ville");
                pays = rs.getString("pays");
                portable = rs.getInt("portable");
                fixe = rs.getInt("fixe");
                mail = rs.getString("mail");
                p = new Patient(nom, prenom, ipp, dateNaissance, lieuNaissance, sexe, numeroVoie, typeVoie, complement, codePostal, ville, pays, portable, fixe, mail);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return p;
    }

}
