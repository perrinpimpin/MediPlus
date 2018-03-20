/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medi.ui;

import java.awt.CardLayout;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import medi.nf.BD;
import medi.nf.DM;
import medi.nf.Lit;
import medi.nf.Medecin;
import medi.nf.Patient;
import medi.nf.Service;

/**
 *
 * @author CRISTANTE
 */
public class InterfaceMedecinMT extends javax.swing.JFrame {

    /**
     * Creates new form InterfaceMedecin
     */
    BD connect;
    Medecin m;
    ArrayList<DM> ldm;
    Patient p;
    Patient pc;
    DefaultTableModel result;
    DefaultTableModel result1;
    private Connection con;
    private Statement st;
    private ResultSet rs2;
    ArrayList<Patient> lp;
    ArrayList<DM> dmencours;
    ArrayList<Medecin> lm;
    DM dm;
    ArrayList<Lit> lit = new ArrayList();

    public InterfaceMedecinMT(Medecin m) {
        initComponents();
        connect = new BD();
        this.m = m;
        nomLabel.setText(m.getPrenom() + " " + m.getNom());
        serviceLabel.setText(m.getService());
        result = (DefaultTableModel) resultatsTable.getModel();
        result1 = (DefaultTableModel) resultatsTable.getModel();
        ldm = connect.getDM(m);
        dmencours = new ArrayList();
        lp = connect.getPatients();
        lm = connect.getMedecins();
        //On récupère les lits vacants pour l'attribution des lits lors de la création des DM
        try {
            lit = connect.rechercheLitVacant();
        } catch (SQLException ex) {
            Logger.getLogger(InterfaceMedecinMT.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (Lit l : lit) {
            litBox.addItem(String.valueOf(l.getNum()));
        }

        //On récupère les lits occupés pourla recherche par lit lors de la création de DM
        try {
            lit = connect.litDesDms();
        } catch (SQLException ex) {
            Logger.getLogger(InterfaceMedecinMT.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (Lit l : lit) {
            litOccupeBox.addItem(String.valueOf(l.getNum()));
        }

        //On affiche les patients présents dans le service sur la page d'accueil
        for (int i = 0; i < ldm.size(); i++) {
            if (ldm.get(i).getLet() == null) {
                dmencours.add(ldm.get(i));
                result.addRow(new Object[]{ldm.get(i).getP().getNom(), ldm.get(i).getP().getPrenom(), ldm.get(i).getP().getDate(), ldm.get(i).getDate(), ldm.get(i).getLit().getNum()});
            }
        }

        resultatsTable.setModel(result);
        resultatsTable.repaint();

        //On ajoute un listener pour surveiller le patient sélectionné dans la liste des patients sur la page d'accueil
        resultatsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (resultatsTable.getSelectedRow() > -1) {
                    if (event.getValueIsAdjusting() == false) {
                        p = connect.recherchePatientsNomPrenomDate(resultatsTable.getValueAt(resultatsTable.getSelectedRow(), 0).toString(), resultatsTable.getValueAt(resultatsTable.getSelectedRow(), 1).toString(), (Date) resultatsTable.getValueAt(resultatsTable.getSelectedRow(), 2));
                    }
                }
            }
        });

        //On ajoute un listener pour surveiller le patient sélectionné dans la liste des patients sur la page de création de DM
        resultatsTable1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (resultatsTable1.getSelectedRow() > -1) {
                    if (event.getValueIsAdjusting() == false) {
                        String s = resultatsTable1.getValueAt(resultatsTable1.getSelectedRow(), 3).toString();
                        pc = connect.recherchePatientsNomPrenomDate(resultatsTable1.getValueAt(resultatsTable1.getSelectedRow(), 0).toString(), resultatsTable1.getValueAt(resultatsTable1.getSelectedRow(), 1).toString(), (Date) resultatsTable1.getValueAt(resultatsTable1.getSelectedRow(), 3));
                    }
                }
            }
        });

        //On ajoute un listener pour surveiller le patient sélectionné dans la liste des patients sur la page de recherche de DM
        rechercheTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (rechercheTable.getSelectedRow() > -1) {
                    if (event.getValueIsAdjusting() == false) {
                        int iddm = (int) rechercheTable.getValueAt(rechercheTable.getSelectedRow(), 3);
                        dm = connect.getDM(iddm);
                    }
                }
            }
        });

        CardLayout card = (CardLayout) jPanel2.getLayout();
        card.show(jPanel2, "Patient");
        if (m.getService().equals(Service.RECHERCHE.getName())) {
            menuPanel.add(menuAccueilRecherche, "Accueil");
            menuPanel.add(menuCreerRecherche, "CreerDM");
            menuPanel.add(menuRechercherDMRecherche, "RechercherDM");
            menuPanel.add(menuRechercherCandidat, "Candidat");
            card = (CardLayout) menuPanel.getLayout();
            card.show(menuPanel, "Accueil");
            centrePanel.add(listePatients, "Accueil");
            centrePanel.add(rechercheDM, "RechercherDM");
            centrePanel.add(creerDM, "CreerDM");
            centrePanel.add(rechercheCandidat, "Candidat");
            jPanel2.add(patient, "Patient");
            jPanel2.add(medrefPanel, "Medref");
            jPanel2.add(litPanel, "Lit");
            jPanel2.add(iddmPanel, "Sejour");
            repaint();
        } else {
            menuPanel.add(menuAccueil, "Accueil");
            menuPanel.add(menuCreerDM, "CreerDM");
            menuPanel.add(menuRechercherDM, "RechercherDM");
            centrePanel.add(listePatients, "Accueil");
            centrePanel.add(rechercheDM, "RechercherDM");
            centrePanel.add(creerDM, "CreerDM");
            jPanel2.add(patient, "Patient");
            jPanel2.add(medrefPanel, "Medref");
            jPanel2.add(litPanel, "Lit");
            jPanel2.add(iddmPanel, "Sejour");
        }

        for (int i = 0; i < lm.size(); i++) {
            medBox.addItem(lm.get(i).getNom() + " " + lm.get(i).getPrenom() + " - " + lm.get(i).getSpecialite());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        menu = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        menuPanel = new javax.swing.JPanel();
        menuAccueil = new javax.swing.JPanel();
        aa = new javax.swing.JButton();
        ac = new javax.swing.JButton();
        ar = new javax.swing.JButton();
        menuCreerDM = new javax.swing.JPanel();
        ca = new javax.swing.JButton();
        cc = new javax.swing.JButton();
        cr = new javax.swing.JButton();
        menuRechercherDM = new javax.swing.JPanel();
        ra = new javax.swing.JButton();
        rc = new javax.swing.JButton();
        rr = new javax.swing.JButton();
        menuAccueilRecherche = new javax.swing.JPanel();
        aa1 = new javax.swing.JButton();
        ac1 = new javax.swing.JButton();
        ar1 = new javax.swing.JButton();
        ra6 = new javax.swing.JButton();
        menuCreerRecherche = new javax.swing.JPanel();
        ca1 = new javax.swing.JButton();
        cc1 = new javax.swing.JButton();
        cr1 = new javax.swing.JButton();
        ra5 = new javax.swing.JButton();
        menuRechercherDMRecherche = new javax.swing.JPanel();
        ra1 = new javax.swing.JButton();
        rc1 = new javax.swing.JButton();
        rr1 = new javax.swing.JButton();
        ra4 = new javax.swing.JButton();
        menuRechercherCandidat = new javax.swing.JPanel();
        ra2 = new javax.swing.JButton();
        rc2 = new javax.swing.JButton();
        rr2 = new javax.swing.JButton();
        ra3 = new javax.swing.JButton();
        header = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        nomLabel = new javax.swing.JLabel();
        serviceLabel = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        centrePanel = new javax.swing.JPanel();
        listePatients = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        resultatsTable = new javax.swing.JTable();
        rechercheDM = new javax.swing.JPanel();
        jButton16 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        rechercheTable = new javax.swing.JTable();
        jButton19 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        patient = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        nomF = new javax.swing.JTextField();
        prenomF = new javax.swing.JTextField();
        dateF = new com.toedter.calendar.JDateChooser();
        jLabel14 = new javax.swing.JLabel();
        jButton15 = new javax.swing.JButton();
        litPanel = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jButton21 = new javax.swing.JButton();
        litOccupeBox = new javax.swing.JComboBox<>();
        iddmPanel = new javax.swing.JPanel();
        sejourF = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jButton22 = new javax.swing.JButton();
        medrefPanel = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        medBox = new javax.swing.JComboBox<>();
        jButton24 = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        rechercheBox = new javax.swing.JComboBox<>();
        creerDM = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        dateField = new com.toedter.calendar.JDateChooser();
        jLabel8 = new javax.swing.JLabel();
        nomField = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        lettre = new javax.swing.JTextArea();
        jScrollPane5 = new javax.swing.JScrollPane();
        resultat = new javax.swing.JTextArea();
        prenomField = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        prescriptions = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        opinf = new javax.swing.JTextArea();
        jScrollPane7 = new javax.swing.JScrollPane();
        observation = new javax.swing.JTextArea();
        jButton20 = new javax.swing.JButton();
        jScrollPane10 = new javax.swing.JScrollPane();
        resultatsTable1 = new javax.swing.JTable();
        jLabel19 = new javax.swing.JLabel();
        creer = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        litBox = new javax.swing.JComboBox<>();
        rechercheCandidat = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        resultatsTable2 = new javax.swing.JTable();
        jButton9 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        sexeBoxRecherche = new javax.swing.JComboBox<>();
        jLabel23 = new javax.swing.JLabel();
        agemin = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        agemax = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        pathologie = new javax.swing.JTextField();
        sainBox = new javax.swing.JCheckBox();
        jPanel9 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jButton23 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.BorderLayout());

        menu.setBackground(new java.awt.Color(255, 255, 255));
        menu.setLayout(new java.awt.BorderLayout());

        jButton5.setBackground(new java.awt.Color(255, 153, 0));
        jButton5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton5.setForeground(new java.awt.Color(102, 102, 102));
        jButton5.setText("Se déconnecter");
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        menu.add(jButton5, java.awt.BorderLayout.SOUTH);

        menuPanel.setBackground(new java.awt.Color(255, 255, 255));
        menuPanel.setMinimumSize(new java.awt.Dimension(184, 147));
        menuPanel.setRequestFocusEnabled(false);
        menuPanel.setLayout(new java.awt.CardLayout());

        menuAccueil.setBackground(new java.awt.Color(255, 255, 255));

        aa.setBackground(new java.awt.Color(102, 102, 102));
        aa.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        aa.setForeground(new java.awt.Color(255, 255, 255));
        aa.setText("Accueil");
        aa.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        aa.setMaximumSize(new java.awt.Dimension(160, 25));
        aa.setMinimumSize(new java.awt.Dimension(160, 25));
        aa.setPreferredSize(new java.awt.Dimension(160, 25));
        aa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aaActionPerformed(evt);
            }
        });

        ac.setBackground(new java.awt.Color(255, 153, 0));
        ac.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ac.setForeground(new java.awt.Color(102, 102, 102));
        ac.setText("Créer un DM");
        ac.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ac.setMaximumSize(new java.awt.Dimension(160, 25));
        ac.setMinimumSize(new java.awt.Dimension(160, 25));
        ac.setPreferredSize(new java.awt.Dimension(160, 25));
        ac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                acActionPerformed(evt);
            }
        });

        ar.setBackground(new java.awt.Color(255, 153, 0));
        ar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ar.setForeground(new java.awt.Color(102, 102, 102));
        ar.setText("Rechercher un DM");
        ar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ar.setMaximumSize(new java.awt.Dimension(160, 25));
        ar.setMinimumSize(new java.awt.Dimension(160, 25));
        ar.setPreferredSize(new java.awt.Dimension(160, 25));
        ar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                arActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout menuAccueilLayout = new javax.swing.GroupLayout(menuAccueil);
        menuAccueil.setLayout(menuAccueilLayout);
        menuAccueilLayout.setHorizontalGroup(
            menuAccueilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuAccueilLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(menuAccueilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(aa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        menuAccueilLayout.setVerticalGroup(
            menuAccueilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuAccueilLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(aa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        menuPanel.add(menuAccueil, "card2");

        menuCreerDM.setBackground(new java.awt.Color(255, 255, 255));

        ca.setBackground(new java.awt.Color(255, 153, 0));
        ca.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ca.setForeground(new java.awt.Color(102, 102, 102));
        ca.setText("Accueil");
        ca.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ca.setMaximumSize(new java.awt.Dimension(160, 25));
        ca.setMinimumSize(new java.awt.Dimension(160, 25));
        ca.setPreferredSize(new java.awt.Dimension(160, 25));
        ca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                caActionPerformed(evt);
            }
        });

        cc.setBackground(new java.awt.Color(102, 102, 102));
        cc.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cc.setForeground(new java.awt.Color(255, 255, 255));
        cc.setText("Créer un DM");
        cc.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cc.setMaximumSize(new java.awt.Dimension(160, 25));
        cc.setMinimumSize(new java.awt.Dimension(160, 25));
        cc.setPreferredSize(new java.awt.Dimension(160, 25));
        cc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ccActionPerformed(evt);
            }
        });

        cr.setBackground(new java.awt.Color(255, 153, 0));
        cr.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cr.setForeground(new java.awt.Color(102, 102, 102));
        cr.setText("Rechercher un DMA");
        cr.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cr.setMaximumSize(new java.awt.Dimension(160, 25));
        cr.setMinimumSize(new java.awt.Dimension(160, 25));
        cr.setPreferredSize(new java.awt.Dimension(160, 25));
        cr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                crActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout menuCreerDMLayout = new javax.swing.GroupLayout(menuCreerDM);
        menuCreerDM.setLayout(menuCreerDMLayout);
        menuCreerDMLayout.setHorizontalGroup(
            menuCreerDMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuCreerDMLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(menuCreerDMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        menuCreerDMLayout.setVerticalGroup(
            menuCreerDMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuCreerDMLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(cr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        menuPanel.add(menuCreerDM, "card2");

        menuRechercherDM.setBackground(new java.awt.Color(255, 255, 255));

        ra.setBackground(new java.awt.Color(255, 153, 0));
        ra.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ra.setForeground(new java.awt.Color(102, 102, 102));
        ra.setText("Accueil");
        ra.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ra.setMaximumSize(new java.awt.Dimension(160, 25));
        ra.setMinimumSize(new java.awt.Dimension(160, 25));
        ra.setPreferredSize(new java.awt.Dimension(160, 25));
        ra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                raActionPerformed(evt);
            }
        });

        rc.setBackground(new java.awt.Color(255, 153, 0));
        rc.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        rc.setForeground(new java.awt.Color(102, 102, 102));
        rc.setText("Créer un DM");
        rc.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        rc.setMaximumSize(new java.awt.Dimension(160, 25));
        rc.setMinimumSize(new java.awt.Dimension(160, 25));
        rc.setPreferredSize(new java.awt.Dimension(160, 25));
        rc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rcActionPerformed(evt);
            }
        });

        rr.setBackground(new java.awt.Color(102, 102, 102));
        rr.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        rr.setForeground(new java.awt.Color(255, 255, 255));
        rr.setText("Rechercher un DM");
        rr.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        rr.setMaximumSize(new java.awt.Dimension(160, 25));
        rr.setMinimumSize(new java.awt.Dimension(160, 25));
        rr.setPreferredSize(new java.awt.Dimension(160, 25));
        rr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rrActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout menuRechercherDMLayout = new javax.swing.GroupLayout(menuRechercherDM);
        menuRechercherDM.setLayout(menuRechercherDMLayout);
        menuRechercherDMLayout.setHorizontalGroup(
            menuRechercherDMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuRechercherDMLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(menuRechercherDMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        menuRechercherDMLayout.setVerticalGroup(
            menuRechercherDMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuRechercherDMLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(rr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(574, Short.MAX_VALUE))
        );

        menuPanel.add(menuRechercherDM, "card2");

        menuAccueilRecherche.setBackground(new java.awt.Color(255, 255, 255));

        aa1.setBackground(new java.awt.Color(102, 102, 102));
        aa1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        aa1.setForeground(new java.awt.Color(255, 255, 255));
        aa1.setText("Accueil");
        aa1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        aa1.setMaximumSize(new java.awt.Dimension(160, 25));
        aa1.setMinimumSize(new java.awt.Dimension(160, 25));
        aa1.setPreferredSize(new java.awt.Dimension(160, 25));
        aa1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aa1ActionPerformed(evt);
            }
        });

        ac1.setBackground(new java.awt.Color(255, 153, 0));
        ac1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ac1.setForeground(new java.awt.Color(102, 102, 102));
        ac1.setText("Créer un DM");
        ac1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ac1.setMaximumSize(new java.awt.Dimension(160, 25));
        ac1.setMinimumSize(new java.awt.Dimension(160, 25));
        ac1.setPreferredSize(new java.awt.Dimension(160, 25));
        ac1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ac1ActionPerformed(evt);
            }
        });

        ar1.setBackground(new java.awt.Color(255, 153, 0));
        ar1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ar1.setForeground(new java.awt.Color(102, 102, 102));
        ar1.setText("Rechercher un DM");
        ar1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ar1.setMaximumSize(new java.awt.Dimension(160, 25));
        ar1.setMinimumSize(new java.awt.Dimension(160, 25));
        ar1.setPreferredSize(new java.awt.Dimension(160, 25));
        ar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ar1ActionPerformed(evt);
            }
        });

        ra6.setBackground(new java.awt.Color(255, 153, 0));
        ra6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ra6.setForeground(new java.awt.Color(102, 102, 102));
        ra6.setText("Rechercher candidat");
        ra6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ra6.setMaximumSize(new java.awt.Dimension(160, 25));
        ra6.setMinimumSize(new java.awt.Dimension(160, 25));
        ra6.setPreferredSize(new java.awt.Dimension(160, 25));
        ra6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ra6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout menuAccueilRechercheLayout = new javax.swing.GroupLayout(menuAccueilRecherche);
        menuAccueilRecherche.setLayout(menuAccueilRechercheLayout);
        menuAccueilRechercheLayout.setHorizontalGroup(
            menuAccueilRechercheLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuAccueilRechercheLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(menuAccueilRechercheLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(aa1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ac1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ra6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        menuAccueilRechercheLayout.setVerticalGroup(
            menuAccueilRechercheLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuAccueilRechercheLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(aa1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ac1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ra6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(540, Short.MAX_VALUE))
        );

        menuPanel.add(menuAccueilRecherche, "card2");

        menuCreerRecherche.setBackground(new java.awt.Color(255, 255, 255));

        ca1.setBackground(new java.awt.Color(255, 153, 0));
        ca1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ca1.setForeground(new java.awt.Color(102, 102, 102));
        ca1.setText("Accueil");
        ca1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ca1.setMaximumSize(new java.awt.Dimension(160, 25));
        ca1.setMinimumSize(new java.awt.Dimension(160, 25));
        ca1.setPreferredSize(new java.awt.Dimension(160, 25));
        ca1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ca1ActionPerformed(evt);
            }
        });

        cc1.setBackground(new java.awt.Color(102, 102, 102));
        cc1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cc1.setForeground(new java.awt.Color(255, 255, 255));
        cc1.setText("Créer un DM");
        cc1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cc1.setMaximumSize(new java.awt.Dimension(160, 25));
        cc1.setMinimumSize(new java.awt.Dimension(160, 25));
        cc1.setPreferredSize(new java.awt.Dimension(160, 25));
        cc1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cc1ActionPerformed(evt);
            }
        });

        cr1.setBackground(new java.awt.Color(255, 153, 0));
        cr1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cr1.setForeground(new java.awt.Color(102, 102, 102));
        cr1.setText("Rechercher un DMA");
        cr1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cr1.setMaximumSize(new java.awt.Dimension(160, 25));
        cr1.setMinimumSize(new java.awt.Dimension(160, 25));
        cr1.setPreferredSize(new java.awt.Dimension(160, 25));
        cr1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cr1ActionPerformed(evt);
            }
        });

        ra5.setBackground(new java.awt.Color(255, 153, 0));
        ra5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ra5.setForeground(new java.awt.Color(102, 102, 102));
        ra5.setText("Rechercher candidat");
        ra5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ra5.setMaximumSize(new java.awt.Dimension(160, 25));
        ra5.setMinimumSize(new java.awt.Dimension(160, 25));
        ra5.setPreferredSize(new java.awt.Dimension(160, 25));
        ra5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ra5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout menuCreerRechercheLayout = new javax.swing.GroupLayout(menuCreerRecherche);
        menuCreerRecherche.setLayout(menuCreerRechercheLayout);
        menuCreerRechercheLayout.setHorizontalGroup(
            menuCreerRechercheLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuCreerRechercheLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(menuCreerRechercheLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ca1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cc1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cr1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ra5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        menuCreerRechercheLayout.setVerticalGroup(
            menuCreerRechercheLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuCreerRechercheLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ca1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cc1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(cr1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ra5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(542, Short.MAX_VALUE))
        );

        menuPanel.add(menuCreerRecherche, "card2");

        menuRechercherDMRecherche.setBackground(new java.awt.Color(255, 255, 255));

        ra1.setBackground(new java.awt.Color(255, 153, 0));
        ra1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ra1.setForeground(new java.awt.Color(102, 102, 102));
        ra1.setText("Accueil");
        ra1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ra1.setMaximumSize(new java.awt.Dimension(160, 25));
        ra1.setMinimumSize(new java.awt.Dimension(160, 25));
        ra1.setPreferredSize(new java.awt.Dimension(160, 25));
        ra1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ra1ActionPerformed(evt);
            }
        });

        rc1.setBackground(new java.awt.Color(255, 153, 0));
        rc1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        rc1.setForeground(new java.awt.Color(102, 102, 102));
        rc1.setText("Créer un DM");
        rc1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        rc1.setMaximumSize(new java.awt.Dimension(160, 25));
        rc1.setMinimumSize(new java.awt.Dimension(160, 25));
        rc1.setPreferredSize(new java.awt.Dimension(160, 25));
        rc1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rc1ActionPerformed(evt);
            }
        });

        rr1.setBackground(new java.awt.Color(102, 102, 102));
        rr1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        rr1.setForeground(new java.awt.Color(255, 255, 255));
        rr1.setText("Rechercher un DM");
        rr1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        rr1.setMaximumSize(new java.awt.Dimension(160, 25));
        rr1.setMinimumSize(new java.awt.Dimension(160, 25));
        rr1.setPreferredSize(new java.awt.Dimension(160, 25));
        rr1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rr1ActionPerformed(evt);
            }
        });

        ra4.setBackground(new java.awt.Color(255, 153, 0));
        ra4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ra4.setForeground(new java.awt.Color(102, 102, 102));
        ra4.setText("Rechercher candidat");
        ra4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ra4.setMaximumSize(new java.awt.Dimension(160, 25));
        ra4.setMinimumSize(new java.awt.Dimension(160, 25));
        ra4.setPreferredSize(new java.awt.Dimension(160, 25));
        ra4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ra4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout menuRechercherDMRechercheLayout = new javax.swing.GroupLayout(menuRechercherDMRecherche);
        menuRechercherDMRecherche.setLayout(menuRechercherDMRechercheLayout);
        menuRechercherDMRechercheLayout.setHorizontalGroup(
            menuRechercherDMRechercheLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuRechercherDMRechercheLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(menuRechercherDMRechercheLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ra1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rc1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rr1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ra4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        menuRechercherDMRechercheLayout.setVerticalGroup(
            menuRechercherDMRechercheLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuRechercherDMRechercheLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ra1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rc1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(rr1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ra4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(542, Short.MAX_VALUE))
        );

        menuPanel.add(menuRechercherDMRecherche, "card2");

        menuRechercherCandidat.setBackground(new java.awt.Color(255, 255, 255));

        ra2.setBackground(new java.awt.Color(255, 153, 0));
        ra2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ra2.setForeground(new java.awt.Color(102, 102, 102));
        ra2.setText("Accueil");
        ra2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ra2.setMaximumSize(new java.awt.Dimension(160, 25));
        ra2.setMinimumSize(new java.awt.Dimension(160, 25));
        ra2.setPreferredSize(new java.awt.Dimension(160, 25));
        ra2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ra2ActionPerformed(evt);
            }
        });

        rc2.setBackground(new java.awt.Color(255, 153, 0));
        rc2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        rc2.setForeground(new java.awt.Color(102, 102, 102));
        rc2.setText("Créer un DM");
        rc2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        rc2.setMaximumSize(new java.awt.Dimension(160, 25));
        rc2.setMinimumSize(new java.awt.Dimension(160, 25));
        rc2.setPreferredSize(new java.awt.Dimension(160, 25));
        rc2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rc2ActionPerformed(evt);
            }
        });

        rr2.setBackground(new java.awt.Color(255, 153, 0));
        rr2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        rr2.setForeground(new java.awt.Color(102, 102, 102));
        rr2.setText("Rechercher un DM");
        rr2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        rr2.setMaximumSize(new java.awt.Dimension(160, 25));
        rr2.setMinimumSize(new java.awt.Dimension(160, 25));
        rr2.setPreferredSize(new java.awt.Dimension(160, 25));
        rr2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rr2ActionPerformed(evt);
            }
        });

        ra3.setBackground(new java.awt.Color(102, 102, 102));
        ra3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ra3.setForeground(new java.awt.Color(255, 255, 255));
        ra3.setText("Rechercher candidat");
        ra3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ra3.setMaximumSize(new java.awt.Dimension(160, 25));
        ra3.setMinimumSize(new java.awt.Dimension(160, 25));
        ra3.setPreferredSize(new java.awt.Dimension(160, 25));
        ra3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ra3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout menuRechercherCandidatLayout = new javax.swing.GroupLayout(menuRechercherCandidat);
        menuRechercherCandidat.setLayout(menuRechercherCandidatLayout);
        menuRechercherCandidatLayout.setHorizontalGroup(
            menuRechercherCandidatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuRechercherCandidatLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(menuRechercherCandidatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ra2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rc2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rr2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ra3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        menuRechercherCandidatLayout.setVerticalGroup(
            menuRechercherCandidatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuRechercherCandidatLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ra2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rc2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(rr2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ra3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(542, Short.MAX_VALUE))
        );

        menuPanel.add(menuRechercherCandidat, "card2");

        menu.add(menuPanel, java.awt.BorderLayout.CENTER);

        jPanel4.add(menu, java.awt.BorderLayout.WEST);

        header.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 102, 0));
        jLabel1.setText("Bienvenue ");

        nomLabel.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        nomLabel.setForeground(new java.awt.Color(255, 102, 0));
        nomLabel.setText("jLabel3");

        serviceLabel.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        serviceLabel.setForeground(new java.awt.Color(102, 102, 102));
        serviceLabel.setText("jLabel3");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("Service :");

        javax.swing.GroupLayout headerLayout = new javax.swing.GroupLayout(header);
        header.setLayout(headerLayout);
        headerLayout.setHorizontalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(headerLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(371, 371, 371))
                    .addGroup(headerLayout.createSequentialGroup()
                        .addComponent(nomLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addComponent(serviceLabel)
                .addContainerGap(783, Short.MAX_VALUE))
        );
        headerLayout.setVerticalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGroup(headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(headerLayout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addGroup(headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(serviceLabel)))
                    .addGroup(headerLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nomLabel)))
                .addContainerGap())
        );

        jPanel4.add(header, java.awt.BorderLayout.NORTH);

        centrePanel.setLayout(new java.awt.CardLayout());

        listePatients.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        listePatients.setPreferredSize(new java.awt.Dimension(0, 0));

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(102, 102, 102));
        jLabel15.setText("Liste des patients présents dans le service :");

        jScrollPane1.setPreferredSize(new java.awt.Dimension(100, 100));

        resultatsTable.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        resultatsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nom", "Prénom", "Date de naissance", "Date du DM", "Lit"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        resultatsTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(resultatsTable);

        javax.swing.GroupLayout listePatientsLayout = new javax.swing.GroupLayout(listePatients);
        listePatients.setLayout(listePatientsLayout);
        listePatientsLayout.setHorizontalGroup(
            listePatientsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(listePatientsLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel15)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(listePatientsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        listePatientsLayout.setVerticalGroup(
            listePatientsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(listePatientsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 638, Short.MAX_VALUE)
                .addContainerGap())
        );

        centrePanel.add(listePatients, "card2");

        rechercheDM.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jButton16.setBackground(new java.awt.Color(255, 153, 0));
        jButton16.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButton16.setForeground(new java.awt.Color(102, 102, 102));
        jButton16.setText("Afficher le DM");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        jButton17.setBackground(new java.awt.Color(255, 153, 0));
        jButton17.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton17.setForeground(new java.awt.Color(102, 102, 102));
        jButton17.setText("Lettre de sortie");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });

        jButton18.setBackground(new java.awt.Color(255, 153, 0));
        jButton18.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton18.setForeground(new java.awt.Color(102, 102, 102));
        jButton18.setText("Imprimer");
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });

        jScrollPane3.setPreferredSize(new java.awt.Dimension(100, 100));

        rechercheTable.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rechercheTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nom", "Prénom", "Date de naissance", "ID du DM", "Date du DM", "Praticien référent", "Service de séjour", "Lit"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, true, true, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        rechercheTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(rechercheTable);

        jButton19.setBackground(new java.awt.Color(255, 153, 0));
        jButton19.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButton19.setForeground(new java.awt.Color(102, 102, 102));
        jButton19.setText("Éditer le DM");
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });

        jPanel2.setLayout(new java.awt.CardLayout());

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 102, 102));
        jLabel4.setText("Prénom patient");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setText("Nom patient");

        nomF.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        nomF.setForeground(new java.awt.Color(51, 51, 51));

        prenomF.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        prenomF.setForeground(new java.awt.Color(51, 51, 51));
        prenomF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prenomFActionPerformed(evt);
            }
        });

        dateF.setDateFormatString("yyyy-MM-dd");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(102, 102, 102));
        jLabel14.setText("Date de naissance");

        jButton15.setBackground(new java.awt.Color(255, 153, 0));
        jButton15.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButton15.setForeground(new java.awt.Color(102, 102, 102));
        jButton15.setText("Rechercher");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout patientLayout = new javax.swing.GroupLayout(patient);
        patient.setLayout(patientLayout);
        patientLayout.setHorizontalGroup(
            patientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(patientLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(patientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(nomF, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41)
                .addGroup(patientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4)
                    .addComponent(prenomF))
                .addGap(43, 43, 43)
                .addGroup(patientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addGroup(patientLayout.createSequentialGroup()
                        .addComponent(dateF, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(370, Short.MAX_VALUE))
        );
        patientLayout.setVerticalGroup(
            patientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, patientLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(patientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(patientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(patientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(nomF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(prenomF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(dateF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton15))
                .addContainerGap())
        );

        jPanel2.add(patient, "card3");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 102, 102));
        jLabel5.setText("Lit");

        jButton21.setBackground(new java.awt.Color(255, 153, 0));
        jButton21.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButton21.setForeground(new java.awt.Color(102, 102, 102));
        jButton21.setText("Rechercher");
        jButton21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton21ActionPerformed(evt);
            }
        });

        litOccupeBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));

        javax.swing.GroupLayout litPanelLayout = new javax.swing.GroupLayout(litPanel);
        litPanel.setLayout(litPanelLayout);
        litPanelLayout.setHorizontalGroup(
            litPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(litPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(litPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(litOccupeBox, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton21, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        litPanelLayout.setVerticalGroup(
            litPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(litPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(litPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton21)
                    .addComponent(litOccupeBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel2.add(litPanel, "card4");

        sejourF.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        sejourF.setForeground(new java.awt.Color(51, 51, 51));
        sejourF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sejourFActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(102, 102, 102));
        jLabel6.setText("N° séjour");

        jButton22.setBackground(new java.awt.Color(255, 153, 0));
        jButton22.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButton22.setForeground(new java.awt.Color(102, 102, 102));
        jButton22.setText("Rechercher");
        jButton22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton22ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout iddmPanelLayout = new javax.swing.GroupLayout(iddmPanel);
        iddmPanel.setLayout(iddmPanelLayout);
        iddmPanelLayout.setHorizontalGroup(
            iddmPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(iddmPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(iddmPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(iddmPanelLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(95, 95, 95))
                    .addComponent(sejourF, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton22, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(670, Short.MAX_VALUE))
        );
        iddmPanelLayout.setVerticalGroup(
            iddmPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(iddmPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(iddmPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sejourF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton22))
                .addContainerGap())
        );

        jPanel2.add(iddmPanel, "card5");

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(102, 102, 102));
        jLabel20.setText("Médecin référent");

        medBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));

        jButton24.setBackground(new java.awt.Color(255, 153, 0));
        jButton24.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButton24.setForeground(new java.awt.Color(102, 102, 102));
        jButton24.setText("Rechercher");
        jButton24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton24ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout medrefPanelLayout = new javax.swing.GroupLayout(medrefPanel);
        medrefPanel.setLayout(medrefPanelLayout);
        medrefPanelLayout.setHorizontalGroup(
            medrefPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(medrefPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(medrefPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20)
                    .addGroup(medrefPanelLayout.createSequentialGroup()
                        .addComponent(medBox, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton24, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(502, Short.MAX_VALUE))
        );
        medrefPanelLayout.setVerticalGroup(
            medrefPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(medrefPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(medrefPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(medBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton24))
                .addContainerGap())
        );

        jPanel2.add(medrefPanel, "card7");

        jLabel21.setText("Rechercher par :");

        rechercheBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nom-Prénom-Date de naissance", "Lit", "Numéro de séjour", "Médecin référent" }));
        rechercheBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rechercheBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout rechercheDMLayout = new javax.swing.GroupLayout(rechercheDM);
        rechercheDM.setLayout(rechercheDMLayout);
        rechercheDMLayout.setHorizontalGroup(
            rechercheDMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(rechercheDMLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(rechercheDMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rechercheDMLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton18)
                        .addGap(155, 155, 155))
                    .addGroup(rechercheDMLayout.createSequentialGroup()
                        .addGroup(rechercheDMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(rechercheDMLayout.createSequentialGroup()
                                .addComponent(jButton16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton19))
                            .addGroup(rechercheDMLayout.createSequentialGroup()
                                .addComponent(jLabel21)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rechercheBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        rechercheDMLayout.setVerticalGroup(
            rechercheDMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rechercheDMLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(rechercheDMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(rechercheBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(rechercheDMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton16)
                    .addComponent(jButton19))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 274, Short.MAX_VALUE)
                .addGroup(rechercheDMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44))
        );

        centrePanel.add(rechercheDM, "card3");

        creerDM.setLayout(new java.awt.BorderLayout());

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(102, 102, 102));
        jLabel10.setText("Opération Infirmière");

        jLabel8.setText("Prénom");

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(102, 102, 102));
        jLabel18.setText("Lettre de sortie");

        lettre.setColumns(20);
        lettre.setRows(5);
        jScrollPane8.setViewportView(lettre);

        resultat.setColumns(20);
        resultat.setRows(5);
        jScrollPane5.setViewportView(resultat);

        jLabel9.setText("Date de naissance");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(102, 102, 102));
        jLabel13.setText("Prescriptions");

        prescriptions.setColumns(20);
        prescriptions.setRows(5);
        jScrollPane6.setViewportView(prescriptions);

        jLabel7.setText("Nom");

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(102, 102, 102));
        jLabel16.setText("Observations");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(102, 102, 102));
        jLabel12.setText("Résultat");

        opinf.setColumns(20);
        opinf.setRows(5);
        jScrollPane4.setViewportView(opinf);

        observation.setColumns(20);
        observation.setRows(5);
        jScrollPane7.setViewportView(observation);

        jButton20.setText("Rechercher");
        jButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton20ActionPerformed(evt);
            }
        });

        jScrollPane10.setPreferredSize(new java.awt.Dimension(100, 100));

        resultatsTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nom", "Prénom", "IPP", "Date de naissance", "Lieu de naissance", "Sexe"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane10.setViewportView(resultatsTable1);

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel19.setText("Sélectionnez le patient :");

        creer.setText("Créer DM");
        creer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                creerActionPerformed(evt);
            }
        });

        jLabel17.setText("Lits disponibles");

        litBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));
        litBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                litBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addGap(18, 18, 18)
                        .addComponent(litBox, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(creer))
                    .addComponent(jScrollPane4)
                    .addComponent(jScrollPane5)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(nomField, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(prenomField, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(dateField, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 301, Short.MAX_VALUE)
                                .addComponent(jButton20, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel19)
                            .addComponent(jLabel10)
                            .addComponent(jLabel12)
                            .addComponent(jLabel13)
                            .addComponent(jLabel16)
                            .addComponent(jLabel18))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(jLabel9)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dateField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(nomField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(prenomField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(creer))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(litBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(35, Short.MAX_VALUE))
        );

        creerDM.add(jPanel3, java.awt.BorderLayout.CENTER);

        centrePanel.add(creerDM, "card4");

        rechercheCandidat.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        rechercheCandidat.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));

        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(102, 102, 102));
        jLabel25.setText("Résultats");

        jScrollPane2.setPreferredSize(new java.awt.Dimension(100, 100));

        resultatsTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nom", "Prénom", "IPP", "Date de naissance", "Lieu de naissance", "Sexe"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(resultatsTable2);

        jButton9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton9.setForeground(new java.awt.Color(255, 153, 0));
        jButton9.setText("Rechercher");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton11.setForeground(new java.awt.Color(255, 153, 0));
        jButton11.setText("Annuler");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel26.setText("Rechercher grâce à 1 ou plusieurs champs un candidat à une étude :");

        jLabel11.setText("Sexe");

        sexeBoxRecherche.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "","Homme", "Femme" }));

        jLabel23.setText("Âge entre");

        jLabel24.setText("et");

        jLabel27.setText("ans");

        jLabel28.setText("Ayant présenté la pathologie");

        pathologie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pathologieActionPerformed(evt);
            }
        });

        sainBox.setText("Sujet sain");

        javax.swing.GroupLayout rechercheCandidatLayout = new javax.swing.GroupLayout(rechercheCandidat);
        rechercheCandidat.setLayout(rechercheCandidatLayout);
        rechercheCandidatLayout.setHorizontalGroup(
            rechercheCandidatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rechercheCandidatLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(rechercheCandidatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rechercheCandidatLayout.createSequentialGroup()
                        .addGroup(rechercheCandidatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel25)
                            .addComponent(sexeBoxRecherche, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(rechercheCandidatLayout.createSequentialGroup()
                        .addGroup(rechercheCandidatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel26)
                            .addGroup(rechercheCandidatLayout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addGap(91, 91, 91)
                                .addGroup(rechercheCandidatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel23)
                                    .addGroup(rechercheCandidatLayout.createSequentialGroup()
                                        .addComponent(agemin, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel24)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(agemax, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel27)))
                                .addGap(40, 40, 40)
                                .addGroup(rechercheCandidatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel28)
                                    .addComponent(pathologie, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(50, 50, 50)
                                .addComponent(sainBox)))
                        .addGap(0, 384, Short.MAX_VALUE)))
                .addContainerGap())
        );
        rechercheCandidatLayout.setVerticalGroup(
            rechercheCandidatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rechercheCandidatLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel26)
                .addGroup(rechercheCandidatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(rechercheCandidatLayout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(rechercheCandidatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(rechercheCandidatLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(rechercheCandidatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(jLabel23)
                            .addComponent(jLabel28))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(rechercheCandidatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(sexeBoxRecherche, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(agemin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel24)
                            .addComponent(agemax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel27)
                            .addComponent(pathologie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sainBox))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                        .addComponent(jLabel25)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 546, Short.MAX_VALUE)
                .addContainerGap())
        );

        centrePanel.add(rechercheCandidat, "card3");

        jPanel4.add(centrePanel, java.awt.BorderLayout.CENTER);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        jButton3.setBackground(new java.awt.Color(255, 153, 0));
        jButton3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(102, 102, 102));
        jButton3.setText("Envoi par HL7");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton8.setBackground(new java.awt.Color(255, 153, 0));
        jButton8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton8.setForeground(new java.awt.Color(102, 102, 102));
        jButton8.setText("Consulter le DMA");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton8, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(627, Short.MAX_VALUE)
                .addComponent(jButton8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel4.add(jPanel9, java.awt.BorderLayout.EAST);

        getContentPane().add(jPanel4, java.awt.BorderLayout.CENTER);

        jPanel1.setBackground(new java.awt.Color(255, 153, 0));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/medi/ui/images/LogoLogicielPetit.png"))); // NOI18N
        jPanel1.add(jLabel22, java.awt.BorderLayout.CENTER);

        jButton23.setBackground(new java.awt.Color(255, 153, 0));
        jButton23.setForeground(new java.awt.Color(255, 153, 0));
        jButton23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/medi/ui/images/preferences-parametres-icone-9540-32.png"))); // NOI18N
        jButton23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton23ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton23, java.awt.BorderLayout.LINE_END);

        getContentPane().add(jPanel1, java.awt.BorderLayout.NORTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        AfficherListeDMPatient adm = new AfficherListeDMPatient(p, m);
        adm.setVisible(true);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void prenomFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prenomFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_prenomFActionPerformed

    private void sejourFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sejourFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sejourFActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        //On utilise les différentes méthodes de la classe BD pour rechercher la liste des DM d'un patient à partir des champs renseignés
        result = (DefaultTableModel) rechercheTable.getModel();
        result.setRowCount(0);
        if (!nomF.getText().isEmpty() && !prenomF.getText().isEmpty() && dateF.getDate() != null) {
            ArrayList<DM> ldm = new ArrayList<>();
            java.util.Date utilDate = dateF.getDate();
            java.sql.Date d = new java.sql.Date(utilDate.getTime());

            Patient p = connect.recherchePatientsNomPrenomDate(nomF.getText(), prenomF.getText(), d);
            ldm = connect.getDMPatient(p.getIPP());
            for (int i = 0; i < ldm.size(); i++) {
                result.addRow(new Object[]{ldm.get(i).getP().getNom(), ldm.get(i).getP().getPrenom(), ldm.get(i).getP().getDate(), ldm.get(i).getIddm(), ldm.get(i).getDate(), ldm.get(i).getMedref().getNom() + " " + ldm.get(i).getMedref().getPrenom(), ldm.get(i).getMedref().getService(), ldm.get(i).getLit().getNum()});
            }

            rechercheTable.setModel(result);
            rechercheTable.repaint();

        } else if (!nomF.getText().isEmpty() && !prenomF.getText().isEmpty() && dateF.getDate() == null) {
            ArrayList<DM> ldm = new ArrayList<>();
            ArrayList<Patient> lp = connect.recherchePatientsNomPrenom(nomF.getText(), prenomF.getText());
            for (int i = 0; i < lp.size(); i++) {
                ldm = connect.getDMPatient(lp.get(i).getIPP());
                for (int j = 0; j < ldm.size(); j++) {
                    result.addRow(new Object[]{ldm.get(j).getP().getNom(), ldm.get(j).getP().getPrenom(), ldm.get(j).getP().getDate(), ldm.get(j).getIddm(), ldm.get(j).getDate(), ldm.get(j).getMedref().getNom() + " " + ldm.get(j).getMedref().getPrenom(), ldm.get(j).getMedref().getService(), ldm.get(j).getLit().getNum()});
                }
            }

            rechercheTable.setModel(result);
            rechercheTable.repaint();
        } else if (!nomF.getText().isEmpty() && prenomF.getText().isEmpty() && dateF.getDate() == null) {
            ArrayList<DM> ldm = new ArrayList<>();
            ArrayList<Patient> lp = connect.recherchePatientsNom(nomF.getText());
            for (int i = 0; i < lp.size(); i++) {
                ldm = connect.getDMPatient(lp.get(i).getIPP());
                for (int j = 0; j < ldm.size(); j++) {
                    result.addRow(new Object[]{ldm.get(j).getP().getNom(), ldm.get(j).getP().getPrenom(), ldm.get(j).getP().getDate(), ldm.get(j).getIddm(), ldm.get(j).getDate(), ldm.get(j).getMedref().getNom() + " " + ldm.get(j).getMedref().getPrenom(), ldm.get(j).getMedref().getService(), ldm.get(j).getLit().getNum()});
                }
            }

            rechercheTable.setModel(result);
            rechercheTable.repaint();
        } else if (nomF.getText().isEmpty() && !prenomF.getText().isEmpty() && dateF.getDate() == null) {
            ArrayList<DM> ldm = new ArrayList<>();
            ArrayList<Patient> lp = connect.recherchePatientsPrenom(prenomF.getText());
            for (int i = 0; i < lp.size(); i++) {
                ldm = connect.getDMPatient(lp.get(i).getIPP());
                for (int j = 0; j < ldm.size(); j++) {
                    result.addRow(new Object[]{ldm.get(j).getP().getNom(), ldm.get(j).getP().getPrenom(), ldm.get(j).getP().getDate(), ldm.get(j).getIddm(), ldm.get(j).getDate(), ldm.get(j).getMedref().getNom() + " " + ldm.get(j).getMedref().getPrenom(), ldm.get(j).getMedref().getService(), ldm.get(j).getLit().getNum()});
                }
            }

            rechercheTable.setModel(result);
            rechercheTable.repaint();
        } else {
            javax.swing.JOptionPane.showMessageDialog(null, "Veuillez renseigner tous les champs.", "Confirmation", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton17ActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton18ActionPerformed

    private void jButton20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton20ActionPerformed
        //On utilise les différentes méthodes de la classe BD pour rechercher un patient à partir des champs renseignés
        DefaultTableModel resultRecherche = (DefaultTableModel) resultatsTable1.getModel();
        ArrayList<Patient> lp = null;
        resultRecherche.setRowCount(0);
        Date d = (Date) dateField.getDate();

        if (!nomField.getText().isEmpty() && prenomField.getText().isEmpty() && dateField.getDate() == null) {
            lp = connect.recherchePatientsNom(nomField.getText());
            for (int i = 0; i < lp.size(); i++) {
                lp.get(i);
                resultRecherche.addRow(new Object[]{lp.get(i).getNom(), lp.get(i).getPrenom(), lp.get(i).getIPP(), lp.get(i).getDate(), lp.get(i).getLieuNaissance(), lp.get(i).getSexe(),});
            }
        } else if (!nomField.getText().isEmpty() && !prenomField.getText().isEmpty() && dateField.getDate() == null) {
            lp = connect.recherchePatientsNomPrenom(nomField.getText(), prenomField.getText());
            for (int i = 0; i < lp.size(); i++) {
                lp.get(i);
                resultRecherche.addRow(new Object[]{lp.get(i).getNom(), lp.get(i).getPrenom(), lp.get(i).getIPP(), lp.get(i).getDate(), lp.get(i).getLieuNaissance(), lp.get(i).getSexe(),});
            }
        } else if (nomField.getText().isEmpty() && !prenomField.getText().isEmpty() && dateField.getDate() == null) {
            lp = connect.recherchePatientsPrenom(prenomField.getText());
            for (int i = 0; i < lp.size(); i++) {
                lp.get(i);
                resultRecherche.addRow(new Object[]{lp.get(i).getNom(), lp.get(i).getPrenom(), lp.get(i).getIPP(), lp.get(i).getDate(), lp.get(i).getLieuNaissance(), lp.get(i).getSexe(),});
            }
        } else if (!nomField.getText().isEmpty() && prenomField.getText().isEmpty() && dateField.getDate() != null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(dateField.getDate().getYear(), dateField.getDate().getMonth(), dateField.getDate().getDate());
            Patient patient = connect.recherchePatientsNomPrenomDate(nomField.getText(), prenomField.getText(), date);
            resultRecherche.addRow(new Object[]{p.getNom(), p.getPrenom(), p.getIPP(), p.getDate(), p.getLieuNaissance(), p.getSexe(),});
        } else if (nomField.getText().isEmpty() && prenomField.getText().isEmpty() && dateField.getDate() == null) {
            javax.swing.JOptionPane.showMessageDialog(null, "Aucun champ de recherche renseigné.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }

        resultatsTable1.setModel(resultRecherche);
        resultatsTable1.repaint();


    }//GEN-LAST:event_jButton20ActionPerformed

    private void creerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_creerActionPerformed
        //Création de DM
        int ph = m.getId_user();
        int iddm = connect.genererIDDM();
        String let = null;
        Lit l = null;
        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        if (observation.getText().length() != 0) {
            ArrayList<String> obs = new ArrayList<String>();
            for (String s : observation.getText().split("\n")) {
                obs.add(s);
            }
            for (int i = 0; i < obs.size(); i++) {
                connect.genererIDObs();
                connect.ajouterObs(connect.genererIDObs(), ph, obs.get(i), iddm);
            }
        }
        if (prescriptions.getText().length() != 0) {
            ArrayList<String> presc = new ArrayList<String>();
            for (String s : prescriptions.getText().split("\n")) {
                presc.add(s);
            }
            for (int i = 0; i < presc.size(); i++) {
                connect.ajouterPrescription(connect.genererIDPrescription(), ph, presc.get(i), iddm);
            }
        }

        if (resultat.getText().length() != 0) {
            ArrayList<String> res = new ArrayList<String>();
            for (String s : resultat.getText().split("\n")) {
                res.add(s);
            }
            for (int i = 0; i < res.size(); i++) {
                connect.ajouterResultat(connect.genererIDRes(), ph, res.get(i), iddm);
            }
        }

        if (opinf.getText().length() != 0) {
            ArrayList<String> op = new ArrayList<String>();
            for (String s : opinf.getText().split("\n")) {
                op.add(s);
            }
            for (int i = 0; i < op.size(); i++) {
                connect.ajouterOpeInf(connect.genererIDOpe(), ph, op.get(i), iddm);
            }
        }

        if (lettre.getText().length() == 0) {
            let = null;
        } else {
            let = lettre.getText();
        }
        l = connect.rechercherLit(litBox.getSelectedItem().toString());
        DM dm = new DM(pc, m, let, iddm, date, l);
        connect.ajouterDM(dm);

        this.ldm = connect.getDM(m);
        dmencours = new ArrayList();
        lp = connect.getPatients();
        result.setRowCount(0);
        for (int i = 0; i < this.ldm.size(); i++) {
            if (this.ldm.get(i).getLet() == null) {
                dmencours.add(this.ldm.get(i));
                result.addRow(new Object[]{this.ldm.get(i).getP().getNom(), this.ldm.get(i).getP().getPrenom(), this.ldm.get(i).getP().getDate(), this.ldm.get(i).getDate()});
            }
        }

        resultatsTable.setModel(result);
        resultatsTable.repaint();
    }//GEN-LAST:event_creerActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        //Déconnection de la session ouverte
        JOptionPane d = new JOptionPane();
        int retour = d.showConfirmDialog(null, "Êtes-vous sûr de vouloir vous déconnecter ?", "Confirmation", JOptionPane.OK_CANCEL_OPTION);
        if (retour == JOptionPane.OK_OPTION) {
            loginframe lf = new loginframe();
            lf.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void aaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aaActionPerformed
        CardLayout cardLayout = (CardLayout) menuPanel.getLayout();
        cardLayout.show(menuPanel, "Accueil");
        CardLayout card = (CardLayout) centrePanel.getLayout();
        card.show(centrePanel, "Accueil");
    }//GEN-LAST:event_aaActionPerformed

    private void acActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_acActionPerformed
        CardLayout cardLayout = (CardLayout) menuPanel.getLayout();
        cardLayout.show(menuPanel, "CreerDM");
        CardLayout card = (CardLayout) centrePanel.getLayout();
        card.show(centrePanel, "CreerDM");
    }//GEN-LAST:event_acActionPerformed

    private void arActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_arActionPerformed
        CardLayout cardLayout = (CardLayout) menuPanel.getLayout();
        cardLayout.show(menuPanel, "RechercherDM");
        CardLayout card = (CardLayout) centrePanel.getLayout();
        card.show(centrePanel, "RechercherDM");
    }//GEN-LAST:event_arActionPerformed

    private void caActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_caActionPerformed
        CardLayout cardLayout = (CardLayout) menuPanel.getLayout();
        cardLayout.show(menuPanel, "Accueil");
        CardLayout card = (CardLayout) centrePanel.getLayout();
        card.show(centrePanel, "Accueil");
    }//GEN-LAST:event_caActionPerformed

    private void ccActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ccActionPerformed
        CardLayout cardLayout = (CardLayout) menuPanel.getLayout();
        cardLayout.show(menuPanel, "CreerDM");
        CardLayout card = (CardLayout) centrePanel.getLayout();
        card.show(centrePanel, "CreerDM");
    }//GEN-LAST:event_ccActionPerformed

    private void crActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_crActionPerformed
        CardLayout cardLayout = (CardLayout) menuPanel.getLayout();
        cardLayout.show(menuPanel, "RechercherDM");
        CardLayout card = (CardLayout) centrePanel.getLayout();
        card.show(centrePanel, "RechercherDM");
    }//GEN-LAST:event_crActionPerformed

    private void raActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_raActionPerformed
        CardLayout cardLayout = (CardLayout) menuPanel.getLayout();
        cardLayout.show(menuPanel, "Accueil");
        CardLayout card = (CardLayout) centrePanel.getLayout();
        card.show(centrePanel, "Accueil");
    }//GEN-LAST:event_raActionPerformed

    private void rcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rcActionPerformed
        CardLayout cardLayout = (CardLayout) menuPanel.getLayout();
        cardLayout.show(menuPanel, "CreerDM");
        CardLayout card = (CardLayout) centrePanel.getLayout();
        card.show(centrePanel, "CreerDM");
    }//GEN-LAST:event_rcActionPerformed

    private void rrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rrActionPerformed
        CardLayout cardLayout = (CardLayout) menuPanel.getLayout();
        cardLayout.show(menuPanel, "RechercherDM");
        CardLayout card = (CardLayout) centrePanel.getLayout();
        card.show(centrePanel, "RechercherDM");
    }//GEN-LAST:event_rrActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        AfficherDMMT addm = new AfficherDMMT(dm);
        addm.setVisible(true);
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        EditerDMMT edm = new EditerDMMT(dm, m);
        edm.setVisible(true);
    }//GEN-LAST:event_jButton19ActionPerformed

    private void litBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_litBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_litBoxActionPerformed

    private void rechercheBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rechercheBoxActionPerformed
        //Afficher différentes interfaces en fonction de la modalité choisie dans la rechercheBox
        if (rechercheBox.getSelectedItem().toString() == "Nom-Prénom-Date de naissance") {
            CardLayout card = (CardLayout) jPanel2.getLayout();
            card.show(jPanel2, "Patient");
        } else if (rechercheBox.getSelectedItem().toString() == "Lit") {
            CardLayout card = (CardLayout) jPanel2.getLayout();
            card.show(jPanel2, "Lit");
        } else if (rechercheBox.getSelectedItem().toString() == "Numéro de séjour") {
            CardLayout card = (CardLayout) jPanel2.getLayout();
            card.show(jPanel2, "Sejour");
        } else if (rechercheBox.getSelectedItem().toString() == "Médecin référent") {
            CardLayout card = (CardLayout) jPanel2.getLayout();
            card.show(jPanel2, "Medref");
        }
    }//GEN-LAST:event_rechercheBoxActionPerformed

    private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton21ActionPerformed
        //Recherche d'un DM à partir d'un lit
        result = (DefaultTableModel) rechercheTable.getModel();
        result.setRowCount(0);
        try {
            DM dm = connect.GetDMLit(litOccupeBox.getSelectedItem().toString());
            result.addRow(new Object[]{dm.getP().getNom(), dm.getP().getPrenom(), dm.getP().getDate(), dm.getIddm(), dm.getDate(), dm.getMedref().getNom() + " " + dm.getMedref().getPrenom(), dm.getMedref().getService(), dm.getLit().getNum()});
        } catch (SQLException ex) {
            Logger.getLogger(InterfaceMedecinMT.class.getName()).log(Level.SEVERE, null, ex);
        }
        rechercheTable.setModel(result);
        rechercheTable.repaint();
    }//GEN-LAST:event_jButton21ActionPerformed

    private void jButton22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton22ActionPerformed
        //Recherche d'un DM à partir du de l'ID du DM
        result = (DefaultTableModel) rechercheTable.getModel();
        result.setRowCount(0);
        DM dm = connect.getDM(Integer.parseInt(sejourF.getText()));
        result.addRow(new Object[]{dm.getP().getNom(), dm.getP().getPrenom(), dm.getP().getDate(), dm.getIddm(), dm.getDate(), dm.getMedref().getNom() + " " + dm.getMedref().getPrenom(), dm.getMedref().getService(), dm.getLit().getNum()});
        rechercheTable.setModel(result);
        rechercheTable.repaint();
    }//GEN-LAST:event_jButton22ActionPerformed

    private void jButton24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton24ActionPerformed
        //Recherche de DM à partir du médecin référent du DM
        result = (DefaultTableModel) rechercheTable.getModel();
        ArrayList<DM> ldm = new ArrayList();
        ldm = connect.getDM(lm.get(medBox.getSelectedIndex() - 1));
        result.setRowCount(0);
        for (int i = 0; i < ldm.size(); i++) {
            result.addRow(new Object[]{ldm.get(i).getP().getNom(), ldm.get(i).getP().getPrenom(), ldm.get(i).getP().getDate(), ldm.get(i).getIddm(), ldm.get(i).getDate(), ldm.get(i).getMedref().getNom() + " " + ldm.get(i).getMedref().getPrenom(), ldm.get(i).getMedref().getService(), ldm.get(i).getLit().getNum()});
        }
        rechercheTable.setModel(result);
        rechercheTable.repaint();
    }//GEN-LAST:event_jButton24ActionPerformed

    private void jButton23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton23ActionPerformed
        ChangerMotDePasse mdp = new ChangerMotDePasse(m);
        mdp.setVisible(true);
    }//GEN-LAST:event_jButton23ActionPerformed

    private void aa1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aa1ActionPerformed
        CardLayout cardLayout = (CardLayout) menuPanel.getLayout();
        cardLayout.show(menuPanel, "Accueil");
        CardLayout card = (CardLayout) centrePanel.getLayout();
        card.show(centrePanel, "Accueil");
    }//GEN-LAST:event_aa1ActionPerformed

    private void ac1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ac1ActionPerformed
        CardLayout cardLayout = (CardLayout) menuPanel.getLayout();
        cardLayout.show(menuPanel, "CreerDM");
        CardLayout card = (CardLayout) centrePanel.getLayout();
        card.show(centrePanel, "CreerDM");
    }//GEN-LAST:event_ac1ActionPerformed

    private void ar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ar1ActionPerformed
        CardLayout cardLayout = (CardLayout) menuPanel.getLayout();
        cardLayout.show(menuPanel, "RechercherDM");
        CardLayout card = (CardLayout) centrePanel.getLayout();
        card.show(centrePanel, "RechercherDM");
    }//GEN-LAST:event_ar1ActionPerformed

    private void ca1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ca1ActionPerformed
        CardLayout cardLayout = (CardLayout) menuPanel.getLayout();
        cardLayout.show(menuPanel, "Accueil");
        CardLayout card = (CardLayout) centrePanel.getLayout();
        card.show(centrePanel, "Accueil");
    }//GEN-LAST:event_ca1ActionPerformed

    private void cc1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cc1ActionPerformed
        CardLayout cardLayout = (CardLayout) menuPanel.getLayout();
        cardLayout.show(menuPanel, "CreerDM");
        CardLayout card = (CardLayout) centrePanel.getLayout();
        card.show(centrePanel, "CreerDM");
    }//GEN-LAST:event_cc1ActionPerformed

    private void cr1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cr1ActionPerformed
        CardLayout cardLayout = (CardLayout) menuPanel.getLayout();
        cardLayout.show(menuPanel, "RechercherDM");
        CardLayout card = (CardLayout) centrePanel.getLayout();
        card.show(centrePanel, "RechercherDM");
    }//GEN-LAST:event_cr1ActionPerformed

    private void ra1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ra1ActionPerformed
        CardLayout cardLayout = (CardLayout) menuPanel.getLayout();
        cardLayout.show(menuPanel, "Accueil");
        CardLayout card = (CardLayout) centrePanel.getLayout();
        card.show(centrePanel, "Accueil");
    }//GEN-LAST:event_ra1ActionPerformed

    private void rc1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rc1ActionPerformed
        CardLayout cardLayout = (CardLayout) menuPanel.getLayout();
        cardLayout.show(menuPanel, "CreerDM");
        CardLayout card = (CardLayout) centrePanel.getLayout();
        card.show(centrePanel, "CreerDM");
    }//GEN-LAST:event_rc1ActionPerformed

    private void rr1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rr1ActionPerformed
        CardLayout cardLayout = (CardLayout) menuPanel.getLayout();
        cardLayout.show(menuPanel, "RechercherDM");
        CardLayout card = (CardLayout) centrePanel.getLayout();
        card.show(centrePanel, "RechercherDM");
    }//GEN-LAST:event_rr1ActionPerformed

    private void ra2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ra2ActionPerformed
        CardLayout cardLayout = (CardLayout) menuPanel.getLayout();
        cardLayout.show(menuPanel, "Accueil");
        CardLayout card = (CardLayout) centrePanel.getLayout();
        card.show(centrePanel, "Accueil");
    }//GEN-LAST:event_ra2ActionPerformed

    private void rc2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rc2ActionPerformed
        CardLayout cardLayout = (CardLayout) menuPanel.getLayout();
        cardLayout.show(menuPanel, "CreerDM");
        CardLayout card = (CardLayout) centrePanel.getLayout();
        card.show(centrePanel, "CreerDM");
    }//GEN-LAST:event_rc2ActionPerformed

    private void rr2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rr2ActionPerformed
        CardLayout cardLayout = (CardLayout) menuPanel.getLayout();
        cardLayout.show(menuPanel, "RechercherDM");
        CardLayout card = (CardLayout) centrePanel.getLayout();
        card.show(centrePanel, "RechercherDM");
    }//GEN-LAST:event_rr2ActionPerformed

    private void ra4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ra4ActionPerformed
        CardLayout cardLayout = (CardLayout) menuPanel.getLayout();
        cardLayout.show(menuPanel, "Candidat");
        CardLayout card = (CardLayout) centrePanel.getLayout();
        card.show(centrePanel, "Candidat");
    }//GEN-LAST:event_ra4ActionPerformed

    private void ra3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ra3ActionPerformed
        CardLayout cardLayout = (CardLayout) menuPanel.getLayout();
        cardLayout.show(menuPanel, "Candidat");
        CardLayout card = (CardLayout) centrePanel.getLayout();
        card.show(centrePanel, "Candidat");
    }//GEN-LAST:event_ra3ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        DefaultTableModel resultRecherche = (DefaultTableModel) resultatsTable2.getModel();
        ArrayList<Patient> lp = null;
        resultRecherche.setRowCount(0);
        if (sexeBoxRecherche.getSelectedItem() == "Femme" && agemin.getText().isEmpty() && agemax.getText().isEmpty() && pathologie.getText().isEmpty() && !sainBox.isSelected()) {
            lp = connect.getPatients();
            for (int i = 0; i < lp.size(); i++) {
                if (lp.get(i).getSexe().equals("Femme")) {
                    resultRecherche.addRow(new Object[]{lp.get(i).getNom(), lp.get(i).getPrenom(), lp.get(i).getIPP(), lp.get(i).getDate(), lp.get(i).getLieuNaissance(), lp.get(i).getSexe(),});
                }
            }
        } else if (sexeBoxRecherche.getSelectedItem() == "Homme" && agemin.getText().isEmpty() && agemax.getText().isEmpty() && pathologie.getText().isEmpty() && !sainBox.isSelected()) {
            lp = connect.getPatients();
            for (int i = 0; i < lp.size(); i++) {
                if (lp.get(i).getSexe().equals("Homme")) {
                    resultRecherche.addRow(new Object[]{lp.get(i).getNom(), lp.get(i).getPrenom(), lp.get(i).getIPP(), lp.get(i).getDate(), lp.get(i).getLieuNaissance(), lp.get(i).getSexe(),});
                }
            }
        } else if (sexeBoxRecherche.getSelectedItem() == "Femme" && !agemin.getText().isEmpty() && agemax.getText().isEmpty() && pathologie.getText().isEmpty() && !sainBox.isSelected()) {
            lp = connect.getPatients();
            for (int i = 0; i < lp.size(); i++) {
                if (lp.get(i).getSexe().equals("Femme") && connect.getAge(lp.get(i).getDate()) >= Integer.parseInt(agemin.getText())) {
                    resultRecherche.addRow(new Object[]{lp.get(i).getNom(), lp.get(i).getPrenom(), lp.get(i).getIPP(), lp.get(i).getDate(), lp.get(i).getLieuNaissance(), lp.get(i).getSexe(),});
                }
            }
        } else if (sexeBoxRecherche.getSelectedItem() == "Homme" && !agemin.getText().isEmpty() && agemax.getText().isEmpty() && pathologie.getText().isEmpty() && !sainBox.isSelected()) {
            lp = connect.getPatients();
            for (int i = 0; i < lp.size(); i++) {
                if (lp.get(i).getSexe().equals("Homme") && connect.getAge(lp.get(i).getDate()) >= Integer.parseInt(agemin.getText())) {
                    resultRecherche.addRow(new Object[]{lp.get(i).getNom(), lp.get(i).getPrenom(), lp.get(i).getIPP(), lp.get(i).getDate(), lp.get(i).getLieuNaissance(), lp.get(i).getSexe(),});
                }
            }
        } else if (sexeBoxRecherche.getSelectedItem() == "" && !agemin.getText().isEmpty() && agemax.getText().isEmpty() && pathologie.getText().isEmpty() && !sainBox.isSelected()) {
            lp = connect.getPatients();
            for (int i = 0; i < lp.size(); i++) {
                if (connect.getAge(lp.get(i).getDate()) >= Integer.parseInt(agemin.getText())) {
                    resultRecherche.addRow(new Object[]{lp.get(i).getNom(), lp.get(i).getPrenom(), lp.get(i).getIPP(), lp.get(i).getDate(), lp.get(i).getLieuNaissance(), lp.get(i).getSexe(),});
                }
            }
        } else if (sexeBoxRecherche.getSelectedItem() == "" && agemin.getText().isEmpty() && !agemax.getText().isEmpty() && pathologie.getText().isEmpty() && !sainBox.isSelected()) {
            lp = connect.getPatients();
            for (int i = 0; i < lp.size(); i++) {
                if (connect.getAge(lp.get(i).getDate()) <= Integer.parseInt(agemax.getText())) {
                    resultRecherche.addRow(new Object[]{lp.get(i).getNom(), lp.get(i).getPrenom(), lp.get(i).getIPP(), lp.get(i).getDate(), lp.get(i).getLieuNaissance(), lp.get(i).getSexe(),});
                }
            }
        } else if (sexeBoxRecherche.getSelectedItem() == "Femme" && agemin.getText().isEmpty() && !agemax.getText().isEmpty() && pathologie.getText().isEmpty() && !sainBox.isSelected()) {
            lp = connect.getPatients();
            for (int i = 0; i < lp.size(); i++) {
                if (lp.get(i).getSexe().equals("Femme") && connect.getAge(lp.get(i).getDate()) <= Integer.parseInt(agemax.getText())) {
                    resultRecherche.addRow(new Object[]{lp.get(i).getNom(), lp.get(i).getPrenom(), lp.get(i).getIPP(), lp.get(i).getDate(), lp.get(i).getLieuNaissance(), lp.get(i).getSexe(),});
                }
            }
        } else if (sexeBoxRecherche.getSelectedItem() == "Homme" && agemin.getText().isEmpty() && !agemax.getText().isEmpty() && pathologie.getText().isEmpty() && !sainBox.isSelected()) {
            lp = connect.getPatients();
            for (int i = 0; i < lp.size(); i++) {
                if (lp.get(i).getSexe().equals("Homme") && connect.getAge(lp.get(i).getDate()) <= Integer.parseInt(agemax.getText())) {
                    resultRecherche.addRow(new Object[]{lp.get(i).getNom(), lp.get(i).getPrenom(), lp.get(i).getIPP(), lp.get(i).getDate(), lp.get(i).getLieuNaissance(), lp.get(i).getSexe(),});
                }
            }
        } else if (sexeBoxRecherche.getSelectedItem() == "" && !agemin.getText().isEmpty() && !agemax.getText().isEmpty() && pathologie.getText().isEmpty() && !sainBox.isSelected()) {
            lp = connect.getPatients();
            for (int i = 0; i < lp.size(); i++) {
                if (connect.getAge(lp.get(i).getDate()) <= Integer.parseInt(agemax.getText()) && connect.getAge(lp.get(i).getDate()) >= Integer.parseInt(agemin.getText())) {
                    resultRecherche.addRow(new Object[]{lp.get(i).getNom(), lp.get(i).getPrenom(), lp.get(i).getIPP(), lp.get(i).getDate(), lp.get(i).getLieuNaissance(), lp.get(i).getSexe(),});
                }
            }
        } else if (sexeBoxRecherche.getSelectedItem() == "Femme" && !agemin.getText().isEmpty() && !agemax.getText().isEmpty() && pathologie.getText().isEmpty() && !sainBox.isSelected()) {
            lp = connect.getPatients();
            for (int i = 0; i < lp.size(); i++) {
                if (lp.get(i).getSexe().equals("Femme") && connect.getAge(lp.get(i).getDate()) <= Integer.parseInt(agemax.getText()) && connect.getAge(lp.get(i).getDate()) >= Integer.parseInt(agemin.getText())) {
                    resultRecherche.addRow(new Object[]{lp.get(i).getNom(), lp.get(i).getPrenom(), lp.get(i).getIPP(), lp.get(i).getDate(), lp.get(i).getLieuNaissance(), lp.get(i).getSexe(),});
                }
            }
        } else if (sexeBoxRecherche.getSelectedItem() == "Homme" && !agemin.getText().isEmpty() && !agemax.getText().isEmpty() && pathologie.getText().isEmpty() && !sainBox.isSelected()) {
            lp = connect.getPatients();
            for (int i = 0; i < lp.size(); i++) {
                if (lp.get(i).getSexe().equals("Homme") && connect.getAge(lp.get(i).getDate()) <= Integer.parseInt(agemax.getText()) && connect.getAge(lp.get(i).getDate()) >= Integer.parseInt(agemin.getText())) {
                    resultRecherche.addRow(new Object[]{lp.get(i).getNom(), lp.get(i).getPrenom(), lp.get(i).getIPP(), lp.get(i).getDate(), lp.get(i).getLieuNaissance(), lp.get(i).getSexe(),});
                }
            }
            
            
            
            
            
            
            
        } else if (sexeBoxRecherche.getSelectedItem() == "Femme" && agemin.getText().isEmpty() && agemax.getText().isEmpty() && !pathologie.getText().isEmpty() && !sainBox.isSelected()) {
            lp = connect.getPatients();
            for (int i = 0; i < lp.size(); i++) {
                if (lp.get(i).getSexe().equals("Femme") && connect.getPatho(pathologie.getText(),lp.get(i).getIPP())) {
                    resultRecherche.addRow(new Object[]{lp.get(i).getNom(), lp.get(i).getPrenom(), lp.get(i).getIPP(), lp.get(i).getDate(), lp.get(i).getLieuNaissance(), lp.get(i).getSexe(),});
                }
            }
        } else if (sexeBoxRecherche.getSelectedItem() == "Homme" && agemin.getText().isEmpty() && agemax.getText().isEmpty() && !pathologie.getText().isEmpty() && !sainBox.isSelected()) {
            lp = connect.getPatients();
            for (int i = 0; i < lp.size(); i++) {
                if (lp.get(i).getSexe().equals("Homme") && connect.getPatho(pathologie.getText(),lp.get(i).getIPP())) {
                    resultRecherche.addRow(new Object[]{lp.get(i).getNom(), lp.get(i).getPrenom(), lp.get(i).getIPP(), lp.get(i).getDate(), lp.get(i).getLieuNaissance(), lp.get(i).getSexe(),});
                }
            }
        } else if (sexeBoxRecherche.getSelectedItem() == "Femme" && !agemin.getText().isEmpty() && agemax.getText().isEmpty() && !pathologie.getText().isEmpty() && !sainBox.isSelected()) {
            lp = connect.getPatients();
            for (int i = 0; i < lp.size(); i++) {
                if (lp.get(i).getSexe().equals("Femme") && connect.getPatho(pathologie.getText(),lp.get(i).getIPP()) && connect.getAge(lp.get(i).getDate()) >= Integer.parseInt(agemin.getText())) {
                    resultRecherche.addRow(new Object[]{lp.get(i).getNom(), lp.get(i).getPrenom(), lp.get(i).getIPP(), lp.get(i).getDate(), lp.get(i).getLieuNaissance(), lp.get(i).getSexe(),});
                }
            }
        } else if (sexeBoxRecherche.getSelectedItem() == "Homme" && !agemin.getText().isEmpty() && agemax.getText().isEmpty() && !pathologie.getText().isEmpty() && !sainBox.isSelected()) {
            lp = connect.getPatients();
            for (int i = 0; i < lp.size(); i++) {
                if (lp.get(i).getSexe().equals("Homme") && connect.getPatho(pathologie.getText(),lp.get(i).getIPP()) && connect.getAge(lp.get(i).getDate()) >= Integer.parseInt(agemin.getText())) {
                    resultRecherche.addRow(new Object[]{lp.get(i).getNom(), lp.get(i).getPrenom(), lp.get(i).getIPP(), lp.get(i).getDate(), lp.get(i).getLieuNaissance(), lp.get(i).getSexe(),});
                }
            }
        } else if (sexeBoxRecherche.getSelectedItem() == "" && !agemin.getText().isEmpty() && agemax.getText().isEmpty() && !pathologie.getText().isEmpty() && !sainBox.isSelected()) {
            lp = connect.getPatients();
            for (int i = 0; i < lp.size(); i++) {
                if (connect.getAge(lp.get(i).getDate()) >= Integer.parseInt(agemin.getText()) && connect.getPatho(pathologie.getText(),lp.get(i).getIPP())) {
                    resultRecherche.addRow(new Object[]{lp.get(i).getNom(), lp.get(i).getPrenom(), lp.get(i).getIPP(), lp.get(i).getDate(), lp.get(i).getLieuNaissance(), lp.get(i).getSexe(),});
                }
            }
        } else if (sexeBoxRecherche.getSelectedItem() == "" && agemin.getText().isEmpty() && !agemax.getText().isEmpty() && !pathologie.getText().isEmpty() && !sainBox.isSelected()) {
            lp = connect.getPatients();
            for (int i = 0; i < lp.size(); i++) {
                if (connect.getAge(lp.get(i).getDate()) <= Integer.parseInt(agemax.getText()) && connect.getPatho(pathologie.getText(),lp.get(i).getIPP())) {
                    resultRecherche.addRow(new Object[]{lp.get(i).getNom(), lp.get(i).getPrenom(), lp.get(i).getIPP(), lp.get(i).getDate(), lp.get(i).getLieuNaissance(), lp.get(i).getSexe(),});
                }
            }
        } else if (sexeBoxRecherche.getSelectedItem() == "Femme" && agemin.getText().isEmpty() && !agemax.getText().isEmpty() && !pathologie.getText().isEmpty() && !sainBox.isSelected()) {
            lp = connect.getPatients();
            for (int i = 0; i < lp.size(); i++) {
                if (lp.get(i).getSexe().equals("Femme") && connect.getAge(lp.get(i).getDate()) <= Integer.parseInt(agemax.getText()) && connect.getPatho(pathologie.getText(),lp.get(i).getIPP())) {
                    resultRecherche.addRow(new Object[]{lp.get(i).getNom(), lp.get(i).getPrenom(), lp.get(i).getIPP(), lp.get(i).getDate(), lp.get(i).getLieuNaissance(), lp.get(i).getSexe(),});
                }
            }
        } else if (sexeBoxRecherche.getSelectedItem() == "Homme" && agemin.getText().isEmpty() && !agemax.getText().isEmpty() && !pathologie.getText().isEmpty() && !sainBox.isSelected()) {
            lp = connect.getPatients();
            for (int i = 0; i < lp.size(); i++) {
                if (lp.get(i).getSexe().equals("Homme") && connect.getAge(lp.get(i).getDate()) <= Integer.parseInt(agemax.getText()) && connect.getPatho(pathologie.getText(),lp.get(i).getIPP())) {
                    resultRecherche.addRow(new Object[]{lp.get(i).getNom(), lp.get(i).getPrenom(), lp.get(i).getIPP(), lp.get(i).getDate(), lp.get(i).getLieuNaissance(), lp.get(i).getSexe(),});
                }
            }
        } else if (sexeBoxRecherche.getSelectedItem() == "" && !agemin.getText().isEmpty() && !agemax.getText().isEmpty() && !pathologie.getText().isEmpty() && !sainBox.isSelected()) {
            lp = connect.getPatients();
            for (int i = 0; i < lp.size(); i++) {
                if (connect.getAge(lp.get(i).getDate()) <= Integer.parseInt(agemax.getText()) && connect.getAge(lp.get(i).getDate()) >= Integer.parseInt(agemin.getText()) && connect.getPatho(pathologie.getText(),lp.get(i).getIPP())) {
                    resultRecherche.addRow(new Object[]{lp.get(i).getNom(), lp.get(i).getPrenom(), lp.get(i).getIPP(), lp.get(i).getDate(), lp.get(i).getLieuNaissance(), lp.get(i).getSexe(),});
                }
            }
        } else if (sexeBoxRecherche.getSelectedItem() == "Femme" && !agemin.getText().isEmpty() && !agemax.getText().isEmpty() && !pathologie.getText().isEmpty() && !sainBox.isSelected()) {
            lp = connect.getPatients();
            for (int i = 0; i < lp.size(); i++) {
                if (lp.get(i).getSexe().equals("Femme") && connect.getAge(lp.get(i).getDate()) <= Integer.parseInt(agemax.getText()) && connect.getAge(lp.get(i).getDate()) >= Integer.parseInt(agemin.getText()) && connect.getPatho(pathologie.getText(),lp.get(i).getIPP())) {
                    resultRecherche.addRow(new Object[]{lp.get(i).getNom(), lp.get(i).getPrenom(), lp.get(i).getIPP(), lp.get(i).getDate(), lp.get(i).getLieuNaissance(), lp.get(i).getSexe(),});
                }
            }
        } else if (sexeBoxRecherche.getSelectedItem() == "Homme" && !agemin.getText().isEmpty() && !agemax.getText().isEmpty() && !pathologie.getText().isEmpty() && !sainBox.isSelected()) {
            lp = connect.getPatients();
            for (int i = 0; i < lp.size(); i++) {
                if (lp.get(i).getSexe().equals("Homme") && connect.getAge(lp.get(i).getDate()) <= Integer.parseInt(agemax.getText()) && connect.getAge(lp.get(i).getDate()) >= Integer.parseInt(agemin.getText()) && connect.getPatho(pathologie.getText(),lp.get(i).getIPP())) {
                    resultRecherche.addRow(new Object[]{lp.get(i).getNom(), lp.get(i).getPrenom(), lp.get(i).getIPP(), lp.get(i).getDate(), lp.get(i).getLieuNaissance(), lp.get(i).getSexe(),});
                }
            }
            
            
            
            
            
            
            
            
            
            
            
            
            
        } else if (sexeBoxRecherche.getSelectedItem() == "Femme" && agemin.getText().isEmpty() && agemax.getText().isEmpty() && pathologie.getText().isEmpty() && sainBox.isSelected()) {
            lp = connect.getPatients();
            for (int i = 0; i < lp.size(); i++) {
                if (lp.get(i).getSexe().equals("Femme") && connect.isSain(lp.get(i))) {
                    resultRecherche.addRow(new Object[]{lp.get(i).getNom(), lp.get(i).getPrenom(), lp.get(i).getIPP(), lp.get(i).getDate(), lp.get(i).getLieuNaissance(), lp.get(i).getSexe(),});
                }
            }
        } else if (sexeBoxRecherche.getSelectedItem() == "Homme" && agemin.getText().isEmpty() && agemax.getText().isEmpty() && pathologie.getText().isEmpty() && sainBox.isSelected()) {
            lp = connect.getPatients();
            for (int i = 0; i < lp.size(); i++) {
                if (lp.get(i).getSexe().equals("Homme") && connect.isSain(lp.get(i))) {
                    resultRecherche.addRow(new Object[]{lp.get(i).getNom(), lp.get(i).getPrenom(), lp.get(i).getIPP(), lp.get(i).getDate(), lp.get(i).getLieuNaissance(), lp.get(i).getSexe(),});
                }
            }
        } else if (sexeBoxRecherche.getSelectedItem() == "Femme" && !agemin.getText().isEmpty() && agemax.getText().isEmpty() && pathologie.getText().isEmpty() && sainBox.isSelected()) {
            lp = connect.getPatients();
            for (int i = 0; i < lp.size(); i++) {
                if (lp.get(i).getSexe().equals("Femme") && connect.isSain(lp.get(i)) && connect.getAge(lp.get(i).getDate()) >= Integer.parseInt(agemin.getText())) {
                    resultRecherche.addRow(new Object[]{lp.get(i).getNom(), lp.get(i).getPrenom(), lp.get(i).getIPP(), lp.get(i).getDate(), lp.get(i).getLieuNaissance(), lp.get(i).getSexe(),});
                }
            }
        } else if (sexeBoxRecherche.getSelectedItem() == "Homme" && !agemin.getText().isEmpty() && agemax.getText().isEmpty() && pathologie.getText().isEmpty() && sainBox.isSelected()) {
            lp = connect.getPatients();
            for (int i = 0; i < lp.size(); i++) {
                if (lp.get(i).getSexe().equals("Homme") && connect.isSain(lp.get(i)) && connect.getAge(lp.get(i).getDate()) >= Integer.parseInt(agemin.getText())) {
                    resultRecherche.addRow(new Object[]{lp.get(i).getNom(), lp.get(i).getPrenom(), lp.get(i).getIPP(), lp.get(i).getDate(), lp.get(i).getLieuNaissance(), lp.get(i).getSexe(),});
                }
            }
        } else if (sexeBoxRecherche.getSelectedItem() == "" && !agemin.getText().isEmpty() && agemax.getText().isEmpty() && pathologie.getText().isEmpty() && sainBox.isSelected()) {
            lp = connect.getPatients();
            for (int i = 0; i < lp.size(); i++) {
                if (connect.getAge(lp.get(i).getDate()) >= Integer.parseInt(agemin.getText()) && connect.isSain(lp.get(i))) {
                    resultRecherche.addRow(new Object[]{lp.get(i).getNom(), lp.get(i).getPrenom(), lp.get(i).getIPP(), lp.get(i).getDate(), lp.get(i).getLieuNaissance(), lp.get(i).getSexe(),});
                }
            }
        } else if (sexeBoxRecherche.getSelectedItem() == "" && agemin.getText().isEmpty() && !agemax.getText().isEmpty() && pathologie.getText().isEmpty() && sainBox.isSelected()) {
            lp = connect.getPatients();
            for (int i = 0; i < lp.size(); i++) {
                if (connect.getAge(lp.get(i).getDate()) <= Integer.parseInt(agemax.getText()) && connect.isSain(lp.get(i))) {
                    resultRecherche.addRow(new Object[]{lp.get(i).getNom(), lp.get(i).getPrenom(), lp.get(i).getIPP(), lp.get(i).getDate(), lp.get(i).getLieuNaissance(), lp.get(i).getSexe(),});
                }
            }
        } else if (sexeBoxRecherche.getSelectedItem() == "Femme" && agemin.getText().isEmpty() && !agemax.getText().isEmpty() && pathologie.getText().isEmpty() && sainBox.isSelected()) {
            lp = connect.getPatients();
            for (int i = 0; i < lp.size(); i++) {
                if (lp.get(i).getSexe().equals("Femme") && connect.getAge(lp.get(i).getDate()) <= Integer.parseInt(agemax.getText()) && connect.isSain(lp.get(i))) {
                    resultRecherche.addRow(new Object[]{lp.get(i).getNom(), lp.get(i).getPrenom(), lp.get(i).getIPP(), lp.get(i).getDate(), lp.get(i).getLieuNaissance(), lp.get(i).getSexe(),});
                }
            }
        } else if (sexeBoxRecherche.getSelectedItem() == "Homme" && agemin.getText().isEmpty() && !agemax.getText().isEmpty() && pathologie.getText().isEmpty() && sainBox.isSelected()) {
            lp = connect.getPatients();
            for (int i = 0; i < lp.size(); i++) {
                if (lp.get(i).getSexe().equals("Homme") && connect.getAge(lp.get(i).getDate()) <= Integer.parseInt(agemax.getText()) && connect.isSain(lp.get(i))) {
                    resultRecherche.addRow(new Object[]{lp.get(i).getNom(), lp.get(i).getPrenom(), lp.get(i).getIPP(), lp.get(i).getDate(), lp.get(i).getLieuNaissance(), lp.get(i).getSexe(),});
                }
            }
        } else if (sexeBoxRecherche.getSelectedItem() == "" && !agemin.getText().isEmpty() && !agemax.getText().isEmpty() && pathologie.getText().isEmpty() && sainBox.isSelected()) {
            lp = connect.getPatients();
            for (int i = 0; i < lp.size(); i++) {
                if (connect.getAge(lp.get(i).getDate()) <= Integer.parseInt(agemax.getText()) && connect.getAge(lp.get(i).getDate()) >= Integer.parseInt(agemin.getText()) && connect.isSain(lp.get(i))) {
                    resultRecherche.addRow(new Object[]{lp.get(i).getNom(), lp.get(i).getPrenom(), lp.get(i).getIPP(), lp.get(i).getDate(), lp.get(i).getLieuNaissance(), lp.get(i).getSexe(),});
                }
            }
        } else if (sexeBoxRecherche.getSelectedItem() == "Femme" && !agemin.getText().isEmpty() && !agemax.getText().isEmpty() && pathologie.getText().isEmpty() && sainBox.isSelected()) {
            lp = connect.getPatients();
            for (int i = 0; i < lp.size(); i++) {
                if (lp.get(i).getSexe().equals("Femme") && connect.getAge(lp.get(i).getDate()) <= Integer.parseInt(agemax.getText()) && connect.getAge(lp.get(i).getDate()) >= Integer.parseInt(agemin.getText()) && connect.isSain(lp.get(i))) {
                    resultRecherche.addRow(new Object[]{lp.get(i).getNom(), lp.get(i).getPrenom(), lp.get(i).getIPP(), lp.get(i).getDate(), lp.get(i).getLieuNaissance(), lp.get(i).getSexe(),});
                }
            }
        } else if (sexeBoxRecherche.getSelectedItem() == "Homme" && !agemin.getText().isEmpty() && !agemax.getText().isEmpty() && pathologie.getText().isEmpty() && sainBox.isSelected()) {
            lp = connect.getPatients();
            for (int i = 0; i < lp.size(); i++) {
                if (lp.get(i).getSexe().equals("Homme") && connect.getAge(lp.get(i).getDate()) <= Integer.parseInt(agemax.getText()) && connect.getAge(lp.get(i).getDate()) >= Integer.parseInt(agemin.getText()) && connect.isSain(lp.get(i))) {
                    resultRecherche.addRow(new Object[]{lp.get(i).getNom(), lp.get(i).getPrenom(), lp.get(i).getIPP(), lp.get(i).getDate(), lp.get(i).getLieuNaissance(), lp.get(i).getSexe(),});
                }
            }
            
            
            
            
            
            
            
        } else if (sexeBoxRecherche.getSelectedItem() == "Femme" && agemin.getText().isEmpty() && agemax.getText().isEmpty() && !pathologie.getText().isEmpty() && sainBox.isSelected()) {
            lp = connect.getPatients();
            for (int i = 0; i < lp.size(); i++) {
                if (lp.get(i).getSexe().equals("Femme") && connect.getPatho(pathologie.getText(),lp.get(i).getIPP()) && connect.isSain(lp.get(i))) {
                    resultRecherche.addRow(new Object[]{lp.get(i).getNom(), lp.get(i).getPrenom(), lp.get(i).getIPP(), lp.get(i).getDate(), lp.get(i).getLieuNaissance(), lp.get(i).getSexe(),});
                }
            }
        } else if (sexeBoxRecherche.getSelectedItem() == "Homme" && agemin.getText().isEmpty() && agemax.getText().isEmpty() && !pathologie.getText().isEmpty() && sainBox.isSelected()) {
            lp = connect.getPatients();
            for (int i = 0; i < lp.size(); i++) {
                if (lp.get(i).getSexe().equals("Homme") && connect.getPatho(pathologie.getText(),lp.get(i).getIPP()) && connect.isSain(lp.get(i))) {
                    resultRecherche.addRow(new Object[]{lp.get(i).getNom(), lp.get(i).getPrenom(), lp.get(i).getIPP(), lp.get(i).getDate(), lp.get(i).getLieuNaissance(), lp.get(i).getSexe(),});
                }
            }
        } else if (sexeBoxRecherche.getSelectedItem() == "Femme" && !agemin.getText().isEmpty() && agemax.getText().isEmpty() && !pathologie.getText().isEmpty() && sainBox.isSelected()) {
            lp = connect.getPatients();
            for (int i = 0; i < lp.size(); i++) {
                if (lp.get(i).getSexe().equals("Femme") && connect.getPatho(pathologie.getText(),lp.get(i).getIPP()) && connect.isSain(lp.get(i)) && connect.getAge(lp.get(i).getDate()) >= Integer.parseInt(agemin.getText())) {
                    resultRecherche.addRow(new Object[]{lp.get(i).getNom(), lp.get(i).getPrenom(), lp.get(i).getIPP(), lp.get(i).getDate(), lp.get(i).getLieuNaissance(), lp.get(i).getSexe(),});
                }
            }
        } else if (sexeBoxRecherche.getSelectedItem() == "Homme" && !agemin.getText().isEmpty() && agemax.getText().isEmpty() && !pathologie.getText().isEmpty() && sainBox.isSelected()) {
            lp = connect.getPatients();
            for (int i = 0; i < lp.size(); i++) {
                if (lp.get(i).getSexe().equals("Homme") && connect.getPatho(pathologie.getText(),lp.get(i).getIPP()) && connect.isSain(lp.get(i)) && connect.getAge(lp.get(i).getDate()) >= Integer.parseInt(agemin.getText())) {
                    resultRecherche.addRow(new Object[]{lp.get(i).getNom(), lp.get(i).getPrenom(), lp.get(i).getIPP(), lp.get(i).getDate(), lp.get(i).getLieuNaissance(), lp.get(i).getSexe(),});
                }
            }
        } else if (sexeBoxRecherche.getSelectedItem() == "" && !agemin.getText().isEmpty() && agemax.getText().isEmpty() && !pathologie.getText().isEmpty() && sainBox.isSelected()) {
            lp = connect.getPatients();
            for (int i = 0; i < lp.size(); i++) {
                if (connect.getAge(lp.get(i).getDate()) >= Integer.parseInt(agemin.getText()) && connect.isSain(lp.get(i)) && connect.getPatho(pathologie.getText(),lp.get(i).getIPP())) {
                    resultRecherche.addRow(new Object[]{lp.get(i).getNom(), lp.get(i).getPrenom(), lp.get(i).getIPP(), lp.get(i).getDate(), lp.get(i).getLieuNaissance(), lp.get(i).getSexe(),});
                }
            }
        } else if (sexeBoxRecherche.getSelectedItem() == "" && agemin.getText().isEmpty() && !agemax.getText().isEmpty() && !pathologie.getText().isEmpty() && sainBox.isSelected()) {
            lp = connect.getPatients();
            for (int i = 0; i < lp.size(); i++) {
                if (connect.getAge(lp.get(i).getDate()) <= Integer.parseInt(agemax.getText()) && connect.isSain(lp.get(i)) && connect.getPatho(pathologie.getText(),lp.get(i).getIPP())) {
                    resultRecherche.addRow(new Object[]{lp.get(i).getNom(), lp.get(i).getPrenom(), lp.get(i).getIPP(), lp.get(i).getDate(), lp.get(i).getLieuNaissance(), lp.get(i).getSexe(),});
                }
            }
        } else if (sexeBoxRecherche.getSelectedItem() == "Femme" && agemin.getText().isEmpty() && !agemax.getText().isEmpty() && !pathologie.getText().isEmpty() && sainBox.isSelected()) {
            lp = connect.getPatients();
            for (int i = 0; i < lp.size(); i++) {
                if (lp.get(i).getSexe().equals("Femme") && connect.isSain(lp.get(i)) && connect.getAge(lp.get(i).getDate()) <= Integer.parseInt(agemax.getText()) && connect.getPatho(pathologie.getText(),lp.get(i).getIPP())) {
                    resultRecherche.addRow(new Object[]{lp.get(i).getNom(), lp.get(i).getPrenom(), lp.get(i).getIPP(), lp.get(i).getDate(), lp.get(i).getLieuNaissance(), lp.get(i).getSexe(),});
                }
            }
        } else if (sexeBoxRecherche.getSelectedItem() == "Homme" && agemin.getText().isEmpty() && !agemax.getText().isEmpty() && !pathologie.getText().isEmpty() && sainBox.isSelected()) {
            lp = connect.getPatients();
            for (int i = 0; i < lp.size(); i++) {
                if (lp.get(i).getSexe().equals("Homme") && connect.isSain(lp.get(i)) && connect.getAge(lp.get(i).getDate()) <= Integer.parseInt(agemax.getText()) && connect.getPatho(pathologie.getText(),lp.get(i).getIPP())) {
                    resultRecherche.addRow(new Object[]{lp.get(i).getNom(), lp.get(i).getPrenom(), lp.get(i).getIPP(), lp.get(i).getDate(), lp.get(i).getLieuNaissance(), lp.get(i).getSexe(),});
                }
            }
        } else if (sexeBoxRecherche.getSelectedItem() == "" && !agemin.getText().isEmpty() && !agemax.getText().isEmpty() && !pathologie.getText().isEmpty() && sainBox.isSelected()) {
            lp = connect.getPatients();
            for (int i = 0; i < lp.size(); i++) {
                if (connect.getAge(lp.get(i).getDate()) <= Integer.parseInt(agemax.getText()) && connect.isSain(lp.get(i)) && connect.getAge(lp.get(i).getDate()) >= Integer.parseInt(agemin.getText()) && connect.getPatho(pathologie.getText(),lp.get(i).getIPP())) {
                    resultRecherche.addRow(new Object[]{lp.get(i).getNom(), lp.get(i).getPrenom(), lp.get(i).getIPP(), lp.get(i).getDate(), lp.get(i).getLieuNaissance(), lp.get(i).getSexe(),});
                }
            }
        } else if (sexeBoxRecherche.getSelectedItem() == "Femme" && !agemin.getText().isEmpty() && !agemax.getText().isEmpty() && !pathologie.getText().isEmpty() && sainBox.isSelected()) {
            lp = connect.getPatients();
            for (int i = 0; i < lp.size(); i++) {
                if (lp.get(i).getSexe().equals("Femme") && connect.getAge(lp.get(i).getDate()) <= Integer.parseInt(agemax.getText()) && connect.getAge(lp.get(i).getDate()) >= Integer.parseInt(agemin.getText()) && connect.getPatho(pathologie.getText(),lp.get(i).getIPP()) && connect.isSain(lp.get(i))) {
                    resultRecherche.addRow(new Object[]{lp.get(i).getNom(), lp.get(i).getPrenom(), lp.get(i).getIPP(), lp.get(i).getDate(), lp.get(i).getLieuNaissance(), lp.get(i).getSexe(),});
                }
            }
        } else if (sexeBoxRecherche.getSelectedItem() == "Homme" && !agemin.getText().isEmpty() && !agemax.getText().isEmpty() && !pathologie.getText().isEmpty() && sainBox.isSelected()) {
            lp = connect.getPatients();
            for (int i = 0; i < lp.size(); i++) {
                if (lp.get(i).getSexe().equals("Homme") && connect.getAge(lp.get(i).getDate()) <= Integer.parseInt(agemax.getText()) && connect.getAge(lp.get(i).getDate()) >= Integer.parseInt(agemin.getText()) && connect.getPatho(pathologie.getText(),lp.get(i).getIPP()) && connect.isSain(lp.get(i))) {
                    resultRecherche.addRow(new Object[]{lp.get(i).getNom(), lp.get(i).getPrenom(), lp.get(i).getIPP(), lp.get(i).getDate(), lp.get(i).getLieuNaissance(), lp.get(i).getSexe(),});
                }
            }
        } 
        resultatsTable2.setModel(resultRecherche);
        resultatsTable2.repaint();

    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        JOptionPane d = new JOptionPane();
        int retour = d.showConfirmDialog(null, "Êtes-vous sûr de vouloir vider le formulaire ?", "Confirmation", JOptionPane.OK_CANCEL_OPTION);
        if (retour == JOptionPane.OK_OPTION) {
            sexeBoxRecherche.setSelectedItem("");
            agemin.setText(null);
            agemax.setText(null);
            lp = connect.getPatients();
            result.setRowCount(0);
            resultatsTable1.setModel(result);
            for (int i = 0; i < lp.size(); i++) {
                lp.get(i);
                result.addRow(new Object[]{lp.get(i).getNom(), lp.get(i).getPrenom(), lp.get(i).getIPP(), lp.get(i).getDate(), lp.get(i).getLieuNaissance(), lp.get(i).getSexe(),});
            }

            resultatsTable1.setModel(result);
            resultatsTable1.repaint();
        }
    }//GEN-LAST:event_jButton11ActionPerformed

    private void ra6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ra6ActionPerformed
        CardLayout cardLayout = (CardLayout) menuPanel.getLayout();
        cardLayout.show(menuPanel, "Candidat");
        CardLayout card = (CardLayout) centrePanel.getLayout();
        card.show(centrePanel, "Candidat");
    }//GEN-LAST:event_ra6ActionPerformed

    private void ra5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ra5ActionPerformed
        CardLayout cardLayout = (CardLayout) menuPanel.getLayout();
        cardLayout.show(menuPanel, "Candidat");
        CardLayout card = (CardLayout) centrePanel.getLayout();
        card.show(centrePanel, "Candidat");
    }//GEN-LAST:event_ra5ActionPerformed

    private void pathologieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pathologieActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pathologieActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(InterfaceMedecinMT.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InterfaceMedecinMT.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InterfaceMedecinMT.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InterfaceMedecinMT.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton aa;
    private javax.swing.JButton aa1;
    private javax.swing.JButton ac;
    private javax.swing.JButton ac1;
    private javax.swing.JTextField agemax;
    private javax.swing.JTextField agemin;
    private javax.swing.JButton ar;
    private javax.swing.JButton ar1;
    private javax.swing.JButton ca;
    private javax.swing.JButton ca1;
    private javax.swing.JButton cc;
    private javax.swing.JButton cc1;
    private javax.swing.JPanel centrePanel;
    private javax.swing.JButton cr;
    private javax.swing.JButton cr1;
    private javax.swing.JButton creer;
    private javax.swing.JPanel creerDM;
    private com.toedter.calendar.JDateChooser dateF;
    private com.toedter.calendar.JDateChooser dateField;
    private javax.swing.JPanel header;
    private javax.swing.JPanel iddmPanel;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton22;
    private javax.swing.JButton jButton23;
    private javax.swing.JButton jButton24;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTextArea lettre;
    private javax.swing.JPanel listePatients;
    private javax.swing.JComboBox<String> litBox;
    private javax.swing.JComboBox<String> litOccupeBox;
    private javax.swing.JPanel litPanel;
    private javax.swing.JComboBox<String> medBox;
    private javax.swing.JPanel medrefPanel;
    private javax.swing.JPanel menu;
    private javax.swing.JPanel menuAccueil;
    private javax.swing.JPanel menuAccueilRecherche;
    private javax.swing.JPanel menuCreerDM;
    private javax.swing.JPanel menuCreerRecherche;
    private javax.swing.JPanel menuPanel;
    private javax.swing.JPanel menuRechercherCandidat;
    private javax.swing.JPanel menuRechercherDM;
    private javax.swing.JPanel menuRechercherDMRecherche;
    private javax.swing.JTextField nomF;
    private javax.swing.JTextField nomField;
    private javax.swing.JLabel nomLabel;
    private javax.swing.JTextArea observation;
    private javax.swing.JTextArea opinf;
    private javax.swing.JTextField pathologie;
    private javax.swing.JPanel patient;
    private javax.swing.JTextField prenomF;
    private javax.swing.JTextField prenomField;
    private javax.swing.JTextArea prescriptions;
    private javax.swing.JButton ra;
    private javax.swing.JButton ra1;
    private javax.swing.JButton ra2;
    private javax.swing.JButton ra3;
    private javax.swing.JButton ra4;
    private javax.swing.JButton ra5;
    private javax.swing.JButton ra6;
    private javax.swing.JButton rc;
    private javax.swing.JButton rc1;
    private javax.swing.JButton rc2;
    private javax.swing.JComboBox<String> rechercheBox;
    private javax.swing.JPanel rechercheCandidat;
    private javax.swing.JPanel rechercheDM;
    private javax.swing.JTable rechercheTable;
    private javax.swing.JTextArea resultat;
    private javax.swing.JTable resultatsTable;
    private javax.swing.JTable resultatsTable1;
    private javax.swing.JTable resultatsTable2;
    private javax.swing.JButton rr;
    private javax.swing.JButton rr1;
    private javax.swing.JButton rr2;
    private javax.swing.JCheckBox sainBox;
    private javax.swing.JTextField sejourF;
    private javax.swing.JLabel serviceLabel;
    private javax.swing.JComboBox<String> sexeBoxRecherche;
    // End of variables declaration//GEN-END:variables
}
