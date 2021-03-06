/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package medi.ui;

import java.awt.CardLayout;
import java.sql.Date;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import medi.nf.BD;
import medi.nf.DM;
import medi.nf.LancerServeurHL7;
import medi.nf.Patient;
import medi.nf.SecretaireAdmin;

/**
 *
 * @author CRISTANTE
 */
public class InterfaceSecretaireAdmin extends javax.swing.JFrame {

    /**
     * Creates new form MainFrame
     */
    BD connect;
    ArrayList<Patient> lp;
    ArrayList<DM> dm;
    Patient p;
    Patient p2;
    Patient p3;
    boolean res = false;
    SecretaireAdmin sa;
    DefaultTableModel result;
    DefaultTableModel result2;

    public InterfaceSecretaireAdmin(SecretaireAdmin sa) {
        initComponents();
        this.sa = sa;
        nomLabel.setText(sa.getPrenom() + " " + sa.getNom());
        this.setLocationRelativeTo(null);//centrer
        connect = new BD();
        result = (DefaultTableModel) resultatsTable.getModel();
        result2 = (DefaultTableModel) resultatsTable2.getModel();
        lp = connect.getPatients();
        dm = connect.sejoursEnCours();

        //Liste des patients de l'hôpital
        for (int i = 0; i < lp.size(); i++) {
            lp.get(i);
            result.addRow(new Object[]{lp.get(i).getNom(), lp.get(i).getPrenom(), lp.get(i).getIPP(), lp.get(i).getDate(), lp.get(i).getLieuNaissance(), lp.get(i).getSexe(),});
        }

        resultatsTable.setModel(result);
        resultatsTable1.setModel(result);
        resultatsTable.repaint();
        resultatsTable1.repaint();

        //On ajoute un listener pour surveiller le patient sélectionné dans la liste des patients sur la page d'accueil
        resultatsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (resultatsTable.getSelectedRow() > -1) {
                    if (event.getValueIsAdjusting() == false) {
                        p = connect.recherchePatientsNomPrenomDate(resultatsTable.getValueAt(resultatsTable.getSelectedRow(), 0).toString(), resultatsTable.getValueAt(resultatsTable.getSelectedRow(), 1).toString(), (Date) resultatsTable.getValueAt(resultatsTable.getSelectedRow(), 3));

                    }
                }
            }
        });

        //On ajoute un listener pour surveiller le patient sélectionné dans la liste des patients sur la page des séjours en cours
        resultatsTable2.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (resultatsTable2.getSelectedRow() > -1) {
                    if (event.getValueIsAdjusting() == false) {
                        p2 = connect.recherchePatientsNomPrenomDate(resultatsTable2.getValueAt(resultatsTable2.getSelectedRow(), 0).toString(), resultatsTable2.getValueAt(resultatsTable2.getSelectedRow(), 1).toString(), (Date) resultatsTable2.getValueAt(resultatsTable2.getSelectedRow(), 3));

                    }
                }
            }
        });

        resultatsTable1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (resultatsTable1.getSelectedRow() > -1) {
                    if (event.getValueIsAdjusting() == false) {
                        p3 = connect.recherchePatientsNomPrenomDate(resultatsTable1.getValueAt(resultatsTable1.getSelectedRow(), 0).toString(), resultatsTable1.getValueAt(resultatsTable1.getSelectedRow(), 1).toString(), (Date) resultatsTable1.getValueAt(resultatsTable1.getSelectedRow(), 3));

                    }
                }
            }
        });

        //On affiche les séjours en cours dans un tableau
        for (int i = 0; i < dm.size(); i++) {
            result2.addRow(new Object[]{dm.get(i).getP().getNom(), dm.get(i).getP().getPrenom(), dm.get(i).getP().getIPP(), dm.get(i).getP().getDate(), dm.get(i).getP().getLieuNaissance(), dm.get(i).getP().getSexe(),});
        }
        resultatsTable2.setModel(result2);
        resultatsTable2.repaint();

        menuPanel.add(accueil, "Accueil");
        menuPanel.add(creerDMA, "CreerDMA");
        menuPanel.add(rechercherDMA, "RechercherDMA");
        menuPanel.add(menuSejour, "Sejours");
        centrePanel.add(listePatients, "Accueil");
        centrePanel.add(recherchePatient, "RechercherDMA");
        centrePanel.add(creerPatient, "CreerDMA");
        centrePanel.add(sejours, "Sejours");
        jPanel7.add(boutonsAccueil, "BoutonsAccueil");
        jPanel7.add(boutonsVide, "Vide");        
        jPanel7.add(boutonsSejours, "BoutonsSejours");
        jPanel7.add(boutonsRecherche, "BoutonsRecherche");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jButton16 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        menuPanel = new javax.swing.JPanel();
        accueil = new javax.swing.JPanel();
        aa = new javax.swing.JButton();
        as = new javax.swing.JButton();
        ac = new javax.swing.JButton();
        ar = new javax.swing.JButton();
        creerDMA = new javax.swing.JPanel();
        ca = new javax.swing.JButton();
        cs = new javax.swing.JButton();
        cc = new javax.swing.JButton();
        cr = new javax.swing.JButton();
        rechercherDMA = new javax.swing.JPanel();
        ra = new javax.swing.JButton();
        rs = new javax.swing.JButton();
        rc = new javax.swing.JButton();
        rr = new javax.swing.JButton();
        menuSejour = new javax.swing.JPanel();
        sma = new javax.swing.JButton();
        sms = new javax.swing.JButton();
        smc = new javax.swing.JButton();
        smr = new javax.swing.JButton();
        centrePanel = new javax.swing.JPanel();
        recherchePatient = new javax.swing.JPanel();
        nomField = new javax.swing.JTextField();
        prenomField = new javax.swing.JTextField();
        ippField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        resultatsTable1 = new javax.swing.JTable();
        jButton9 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        creerPatient = new javax.swing.JPanel();
        nomField1 = new javax.swing.JTextField();
        prenomField1 = new javax.swing.JTextField();
        dateField = new com.toedter.calendar.JDateChooser();
        lieuField = new javax.swing.JTextField();
        voieField = new javax.swing.JTextField();
        complementField = new javax.swing.JTextField();
        sexeBox = new javax.swing.JComboBox<>();
        voieBox = new javax.swing.JComboBox<>();
        paysField = new javax.swing.JTextField();
        codeField = new javax.swing.JTextField();
        villeField = new javax.swing.JTextField();
        jButton10 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        portableField = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        fixeField = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        mailField = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jButton12 = new javax.swing.JButton();
        listePatients = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        resultatsTable = new javax.swing.JTable();
        sejours = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        resultatsTable2 = new javax.swing.JTable();
        jLabel21 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        nomLabel = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        boutonsAccueil = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        archiver = new javax.swing.JButton();
        boutonsVide = new javax.swing.JPanel();
        boutonsRecherche = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        archiver1 = new javax.swing.JButton();
        boutonsSejours = new javax.swing.JPanel();
        jButton8 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        archiver2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Médi+");
        setMinimumSize(new java.awt.Dimension(959, 600));

        jPanel1.setBackground(new java.awt.Color(255, 153, 0));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/medi/ui/images/LogoLogicielPetit.png"))); // NOI18N
        jPanel1.add(jLabel22, java.awt.BorderLayout.CENTER);

        jButton16.setBackground(new java.awt.Color(255, 153, 0));
        jButton16.setForeground(new java.awt.Color(255, 153, 0));
        jButton16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/medi/ui/images/preferences-parametres-icone-9540-32.png"))); // NOI18N
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton16, java.awt.BorderLayout.LINE_END);

        getContentPane().add(jPanel1, java.awt.BorderLayout.NORTH);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setMinimumSize(new java.awt.Dimension(183, 421));
        jPanel3.setPreferredSize(new java.awt.Dimension(183, 421));
        jPanel3.setLayout(new java.awt.BorderLayout());

        jButton1.setBackground(new java.awt.Color(255, 153, 0));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(102, 102, 102));
        jButton1.setText("Se déconnecter");
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton1, java.awt.BorderLayout.SOUTH);

        menuPanel.setBackground(new java.awt.Color(255, 255, 255));
        menuPanel.setMinimumSize(new java.awt.Dimension(184, 147));
        menuPanel.setRequestFocusEnabled(false);
        menuPanel.setLayout(new java.awt.CardLayout());

        accueil.setBackground(new java.awt.Color(255, 255, 255));

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

        as.setBackground(new java.awt.Color(255, 153, 0));
        as.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        as.setForeground(new java.awt.Color(102, 102, 102));
        as.setText("Séjours en cours");
        as.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        as.setMaximumSize(new java.awt.Dimension(160, 25));
        as.setMinimumSize(new java.awt.Dimension(160, 25));
        as.setPreferredSize(new java.awt.Dimension(160, 25));
        as.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                asActionPerformed(evt);
            }
        });

        ac.setBackground(new java.awt.Color(255, 153, 0));
        ac.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ac.setForeground(new java.awt.Color(102, 102, 102));
        ac.setText("Créer un DMA");
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
        ar.setText("Rechercher un DMA");
        ar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ar.setMaximumSize(new java.awt.Dimension(160, 25));
        ar.setMinimumSize(new java.awt.Dimension(160, 25));
        ar.setPreferredSize(new java.awt.Dimension(160, 25));
        ar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                arActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout accueilLayout = new javax.swing.GroupLayout(accueil);
        accueil.setLayout(accueilLayout);
        accueilLayout.setHorizontalGroup(
            accueilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(accueilLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(accueilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(aa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(as, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        accueilLayout.setVerticalGroup(
            accueilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(accueilLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(aa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(as, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(276, 276, 276))
        );

        menuPanel.add(accueil, "card2");

        creerDMA.setBackground(new java.awt.Color(255, 255, 255));

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

        cs.setBackground(new java.awt.Color(255, 153, 0));
        cs.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cs.setForeground(new java.awt.Color(102, 102, 102));
        cs.setText("Séjours en cours");
        cs.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cs.setMaximumSize(new java.awt.Dimension(160, 25));
        cs.setMinimumSize(new java.awt.Dimension(160, 25));
        cs.setPreferredSize(new java.awt.Dimension(160, 25));
        cs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                csActionPerformed(evt);
            }
        });

        cc.setBackground(new java.awt.Color(102, 102, 102));
        cc.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cc.setForeground(new java.awt.Color(255, 255, 255));
        cc.setText("Créer un DMA");
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

        javax.swing.GroupLayout creerDMALayout = new javax.swing.GroupLayout(creerDMA);
        creerDMA.setLayout(creerDMALayout);
        creerDMALayout.setHorizontalGroup(
            creerDMALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(creerDMALayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(creerDMALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        creerDMALayout.setVerticalGroup(
            creerDMALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(creerDMALayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(cs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(cc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(cr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        menuPanel.add(creerDMA, "card2");

        rechercherDMA.setBackground(new java.awt.Color(255, 255, 255));

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

        rs.setBackground(new java.awt.Color(255, 153, 0));
        rs.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        rs.setForeground(new java.awt.Color(102, 102, 102));
        rs.setText("Séjours en cours");
        rs.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        rs.setMaximumSize(new java.awt.Dimension(160, 25));
        rs.setMinimumSize(new java.awt.Dimension(160, 25));
        rs.setPreferredSize(new java.awt.Dimension(160, 25));
        rs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rsActionPerformed(evt);
            }
        });

        rc.setBackground(new java.awt.Color(255, 153, 0));
        rc.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        rc.setForeground(new java.awt.Color(102, 102, 102));
        rc.setText("Créer un DMA");
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
        rr.setText("Rechercher un DMA");
        rr.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        rr.setMaximumSize(new java.awt.Dimension(160, 25));
        rr.setMinimumSize(new java.awt.Dimension(160, 25));
        rr.setPreferredSize(new java.awt.Dimension(160, 25));
        rr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rrActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout rechercherDMALayout = new javax.swing.GroupLayout(rechercherDMA);
        rechercherDMA.setLayout(rechercherDMALayout);
        rechercherDMALayout.setHorizontalGroup(
            rechercherDMALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rechercherDMALayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(rechercherDMALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        rechercherDMALayout.setVerticalGroup(
            rechercherDMALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rechercherDMALayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(rs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(rc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(rr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        menuPanel.add(rechercherDMA, "card2");

        menuSejour.setBackground(new java.awt.Color(255, 255, 255));

        sma.setBackground(new java.awt.Color(255, 153, 0));
        sma.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        sma.setForeground(new java.awt.Color(102, 102, 102));
        sma.setText("Accueil");
        sma.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        sma.setMaximumSize(new java.awt.Dimension(160, 25));
        sma.setMinimumSize(new java.awt.Dimension(160, 25));
        sma.setPreferredSize(new java.awt.Dimension(160, 25));
        sma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                smaActionPerformed(evt);
            }
        });

        sms.setBackground(new java.awt.Color(102, 102, 102));
        sms.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        sms.setForeground(new java.awt.Color(255, 255, 255));
        sms.setText("Séjours en cours");
        sms.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        sms.setMaximumSize(new java.awt.Dimension(160, 25));
        sms.setMinimumSize(new java.awt.Dimension(160, 25));
        sms.setPreferredSize(new java.awt.Dimension(160, 25));
        sms.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                smsActionPerformed(evt);
            }
        });

        smc.setBackground(new java.awt.Color(255, 153, 0));
        smc.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        smc.setForeground(new java.awt.Color(102, 102, 102));
        smc.setText("Créer un DMA");
        smc.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        smc.setMaximumSize(new java.awt.Dimension(160, 25));
        smc.setMinimumSize(new java.awt.Dimension(160, 25));
        smc.setPreferredSize(new java.awt.Dimension(160, 25));
        smc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                smcActionPerformed(evt);
            }
        });

        smr.setBackground(new java.awt.Color(255, 153, 0));
        smr.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        smr.setForeground(new java.awt.Color(102, 102, 102));
        smr.setText("Rechercher un DMA");
        smr.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        smr.setMaximumSize(new java.awt.Dimension(160, 25));
        smr.setMinimumSize(new java.awt.Dimension(160, 25));
        smr.setPreferredSize(new java.awt.Dimension(160, 25));
        smr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                smrActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout menuSejourLayout = new javax.swing.GroupLayout(menuSejour);
        menuSejour.setLayout(menuSejourLayout);
        menuSejourLayout.setHorizontalGroup(
            menuSejourLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuSejourLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(menuSejourLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sms, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(smc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(smr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        menuSejourLayout.setVerticalGroup(
            menuSejourLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuSejourLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(sma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sms, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(smc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(smr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(276, 276, 276))
        );

        menuPanel.add(menuSejour, "card2");

        jPanel3.add(menuPanel, java.awt.BorderLayout.CENTER);

        jPanel4.add(jPanel3, java.awt.BorderLayout.WEST);

        centrePanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        centrePanel.setMinimumSize(new java.awt.Dimension(626, 435));
        centrePanel.setPreferredSize(new java.awt.Dimension(626, 435));
        centrePanel.setLayout(new java.awt.CardLayout());

        recherchePatient.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        recherchePatient.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));

        nomField.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        nomField.setForeground(new java.awt.Color(51, 51, 51));
        nomField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nomFieldActionPerformed(evt);
            }
        });

        prenomField.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        prenomField.setForeground(new java.awt.Color(51, 51, 51));
        prenomField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prenomFieldActionPerformed(evt);
            }
        });

        ippField.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ippField.setForeground(new java.awt.Color(51, 51, 51));
        ippField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ippFieldActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setText("Nom");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 102, 102));
        jLabel4.setText("Prénom");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(102, 102, 102));
        jLabel6.setText("IPP");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(102, 102, 102));
        jLabel8.setText("Résultats");

        jScrollPane2.setPreferredSize(new java.awt.Dimension(100, 100));

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
        jScrollPane2.setViewportView(resultatsTable1);

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

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Rechercher grâce à 1 ou plusieurs champs un DMA");

        javax.swing.GroupLayout recherchePatientLayout = new javax.swing.GroupLayout(recherchePatient);
        recherchePatient.setLayout(recherchePatientLayout);
        recherchePatientLayout.setHorizontalGroup(
            recherchePatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(recherchePatientLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(recherchePatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, recherchePatientLayout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(recherchePatientLayout.createSequentialGroup()
                        .addGroup(recherchePatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(recherchePatientLayout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(nomField, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(prenomField, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ippField, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel5))
                        .addGap(0, 188, Short.MAX_VALUE)))
                .addContainerGap())
        );
        recherchePatientLayout.setVerticalGroup(
            recherchePatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(recherchePatientLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addGap(16, 16, 16)
                .addGroup(recherchePatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(prenomField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(nomField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(ippField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGroup(recherchePatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(recherchePatientLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(recherchePatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(recherchePatientLayout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(jLabel8)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE)
                .addContainerGap())
        );

        centrePanel.add(recherchePatient, "card3");

        creerPatient.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        creerPatient.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        creerPatient.setMinimumSize(new java.awt.Dimension(636, 435));

        nomField1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        nomField1.setForeground(new java.awt.Color(51, 51, 51));

        prenomField1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        prenomField1.setForeground(new java.awt.Color(51, 51, 51));
        prenomField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prenomField1ActionPerformed(evt);
            }
        });

        lieuField.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lieuField.setForeground(new java.awt.Color(51, 51, 51));
        lieuField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lieuFieldActionPerformed(evt);
            }
        });

        voieField.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        voieField.setForeground(new java.awt.Color(51, 51, 51));
        voieField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                voieFieldActionPerformed(evt);
            }
        });

        complementField.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        complementField.setForeground(new java.awt.Color(51, 51, 51));
        complementField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                complementFieldActionPerformed(evt);
            }
        });

        sexeBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Homme", "Femme" }));

        voieBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Rue", "Avenue", "Chemin", "Boulevard", "Route","Impasse" }));

        paysField.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        paysField.setForeground(new java.awt.Color(51, 51, 51));
        paysField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paysFieldActionPerformed(evt);
            }
        });

        codeField.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        codeField.setForeground(new java.awt.Color(51, 51, 51));
        codeField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                codeFieldActionPerformed(evt);
            }
        });

        villeField.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        villeField.setForeground(new java.awt.Color(51, 51, 51));
        villeField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                villeFieldActionPerformed(evt);
            }
        });

        jButton10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton10.setForeground(new java.awt.Color(255, 153, 0));
        jButton10.setText("Créer DMA");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(102, 102, 102));
        jLabel7.setText("Nom");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(102, 102, 102));
        jLabel9.setText("Prénom");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(102, 102, 102));
        jLabel10.setText("Date naissance");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(102, 102, 102));
        jLabel11.setText("Lieu naissance");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(102, 102, 102));
        jLabel12.setText("Sexe");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(102, 102, 102));
        jLabel13.setText("N° voie");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(102, 102, 102));
        jLabel14.setText("Type voie");

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(102, 102, 102));
        jLabel17.setText("Complément");

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(102, 102, 102));
        jLabel18.setText("Code postal");

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(102, 102, 102));
        jLabel19.setText("Ville");

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(102, 102, 102));
        jLabel20.setText("Pays");

        portableField.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        portableField.setForeground(new java.awt.Color(51, 51, 51));
        portableField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                portableFieldActionPerformed(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(102, 102, 102));
        jLabel23.setText("Portable");

        fixeField.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        fixeField.setForeground(new java.awt.Color(51, 51, 51));
        fixeField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fixeFieldActionPerformed(evt);
            }
        });

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(102, 102, 102));
        jLabel24.setText("Fixe");

        mailField.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        mailField.setForeground(new java.awt.Color(51, 51, 51));
        mailField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mailFieldActionPerformed(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(102, 102, 102));
        jLabel25.setText("Mail");

        jButton12.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton12.setForeground(new java.awt.Color(255, 153, 0));
        jButton12.setText("Annuler");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout creerPatientLayout = new javax.swing.GroupLayout(creerPatient);
        creerPatient.setLayout(creerPatientLayout);
        creerPatientLayout.setHorizontalGroup(
            creerPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(creerPatientLayout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(creerPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(creerPatientLayout.createSequentialGroup()
                        .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(creerPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(creerPatientLayout.createSequentialGroup()
                            .addGroup(creerPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(nomField1, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel7))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(creerPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(prenomField1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel9))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(creerPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel10)
                                .addComponent(dateField, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(creerPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel11)
                                .addComponent(lieuField, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(creerPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel12)
                                .addComponent(sexeBox, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addComponent(jLabel23)
                        .addComponent(portableField, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(creerPatientLayout.createSequentialGroup()
                            .addGap(155, 155, 155)
                            .addGroup(creerPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel24)
                                .addComponent(fixeField, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(34, 34, 34)
                            .addGroup(creerPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel25)
                                .addComponent(mailField)))
                        .addGroup(creerPatientLayout.createSequentialGroup()
                            .addGroup(creerPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(voieField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel13))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(creerPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(voieBox, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(creerPatientLayout.createSequentialGroup()
                                    .addGap(4, 4, 4)
                                    .addComponent(jLabel14)))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(creerPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel17)
                                .addComponent(complementField, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(creerPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(codeField, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel18))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(creerPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(villeField, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel19))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(creerPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel20)
                                .addComponent(paysField, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(129, Short.MAX_VALUE))
        );
        creerPatientLayout.setVerticalGroup(
            creerPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(creerPatientLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(creerPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(creerPatientLayout.createSequentialGroup()
                        .addGroup(creerPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11))
                        .addGap(3, 3, 3)
                        .addGroup(creerPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(dateField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(creerPatientLayout.createSequentialGroup()
                                .addComponent(lieuField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(1, 1, 1))))
                    .addGroup(creerPatientLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(3, 3, 3)
                        .addComponent(nomField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(creerPatientLayout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(3, 3, 3)
                        .addComponent(prenomField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, creerPatientLayout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(2, 2, 2)
                        .addComponent(sexeBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)))
                .addGroup(creerPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(creerPatientLayout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(voieField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(creerPatientLayout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(voieBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(creerPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(creerPatientLayout.createSequentialGroup()
                            .addComponent(jLabel18)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(codeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(creerPatientLayout.createSequentialGroup()
                            .addGroup(creerPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel20)
                                .addComponent(jLabel17))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(creerPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(complementField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(paysField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(creerPatientLayout.createSequentialGroup()
                            .addComponent(jLabel19)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(villeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(15, 15, 15)
                .addGroup(creerPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(creerPatientLayout.createSequentialGroup()
                        .addGroup(creerPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel24)
                            .addComponent(jLabel25))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(creerPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(fixeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(mailField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(creerPatientLayout.createSequentialGroup()
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(portableField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(27, 27, 27)
                .addGroup(creerPatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(214, Short.MAX_VALUE))
        );

        centrePanel.add(creerPatient, "card4");

        listePatients.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(102, 102, 102));
        jLabel15.setText("Liste patients");

        jScrollPane1.setPreferredSize(new java.awt.Dimension(100, 100));

        resultatsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nom", "Prénom", "IPP", "Date de naissance", "Lieu de naissance", "Sexe"
            }
        ));
        resultatsTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(resultatsTable);

        javax.swing.GroupLayout listePatientsLayout = new javax.swing.GroupLayout(listePatients);
        listePatients.setLayout(listePatientsLayout);
        listePatientsLayout.setHorizontalGroup(
            listePatientsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(listePatientsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(listePatientsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(listePatientsLayout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addGap(0, 632, Short.MAX_VALUE)))
                .addContainerGap())
        );
        listePatientsLayout.setVerticalGroup(
            listePatientsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(listePatientsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 383, Short.MAX_VALUE)
                .addContainerGap())
        );

        centrePanel.add(listePatients, "card2");

        sejours.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(102, 102, 102));
        jLabel16.setText("Séjours en cours");

        jScrollPane3.setPreferredSize(new java.awt.Dimension(100, 100));

        resultatsTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nom", "Prénom", "IPP", "Date de naissance", "Lieu de naissance", "Sexe"
            }
        ));
        resultatsTable2.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(resultatsTable2);

        javax.swing.GroupLayout sejoursLayout = new javax.swing.GroupLayout(sejours);
        sejours.setLayout(sejoursLayout);
        sejoursLayout.setHorizontalGroup(
            sejoursLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sejoursLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(sejoursLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(sejoursLayout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addGap(0, 614, Short.MAX_VALUE)))
                .addContainerGap())
        );
        sejoursLayout.setVerticalGroup(
            sejoursLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sejoursLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 383, Short.MAX_VALUE)
                .addContainerGap())
        );

        centrePanel.add(sejours, "card2");

        jPanel4.add(centrePanel, java.awt.BorderLayout.CENTER);
        jPanel4.add(jLabel21, java.awt.BorderLayout.PAGE_END);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 102, 0));
        jLabel1.setText("Bienvenue ");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText("Statut: Secrétaire Administrative");

        nomLabel.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        nomLabel.setForeground(new java.awt.Color(255, 102, 0));
        nomLabel.setText("jLabel3");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(nomLabel)
                        .addGap(230, 230, 230)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(384, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(13, 13, 13)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(nomLabel))
                .addContainerGap())
        );

        jPanel4.add(jPanel6, java.awt.BorderLayout.NORTH);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setPreferredSize(new java.awt.Dimension(170, 147));
        jPanel7.setLayout(new java.awt.CardLayout());

        boutonsAccueil.setBackground(new java.awt.Color(255, 255, 255));

        jButton3.setBackground(new java.awt.Color(255, 153, 0));
        jButton3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(102, 102, 102));
        jButton3.setText("Envoi par HL7");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(255, 153, 0));
        jButton2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(102, 102, 102));
        jButton2.setText("Éditer le DMA");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(255, 153, 0));
        jButton4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton4.setForeground(new java.awt.Color(102, 102, 102));
        jButton4.setText("Consulter le DMA");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        archiver.setBackground(new java.awt.Color(255, 153, 0));
        archiver.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        archiver.setForeground(new java.awt.Color(102, 102, 102));
        archiver.setText("Archiver le DMA");
        archiver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                archiverActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout boutonsAccueilLayout = new javax.swing.GroupLayout(boutonsAccueil);
        boutonsAccueil.setLayout(boutonsAccueilLayout);
        boutonsAccueilLayout.setHorizontalGroup(
            boutonsAccueilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(boutonsAccueilLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(boutonsAccueilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(archiver, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        boutonsAccueilLayout.setVerticalGroup(
            boutonsAccueilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, boutonsAccueilLayout.createSequentialGroup()
                .addContainerGap(291, Short.MAX_VALUE)
                .addComponent(jButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(archiver)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel7.add(boutonsAccueil, "card2");

        boutonsVide.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout boutonsVideLayout = new javax.swing.GroupLayout(boutonsVide);
        boutonsVide.setLayout(boutonsVideLayout);
        boutonsVideLayout.setHorizontalGroup(
            boutonsVideLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 170, Short.MAX_VALUE)
        );
        boutonsVideLayout.setVerticalGroup(
            boutonsVideLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 435, Short.MAX_VALUE)
        );

        jPanel7.add(boutonsVide, "card2");

        boutonsRecherche.setBackground(new java.awt.Color(255, 255, 255));

        jButton5.setBackground(new java.awt.Color(255, 153, 0));
        jButton5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton5.setForeground(new java.awt.Color(102, 102, 102));
        jButton5.setText("Envoi par HL7");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setBackground(new java.awt.Color(255, 153, 0));
        jButton6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton6.setForeground(new java.awt.Color(102, 102, 102));
        jButton6.setText("Éditer le DMA");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setBackground(new java.awt.Color(255, 153, 0));
        jButton7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton7.setForeground(new java.awt.Color(102, 102, 102));
        jButton7.setText("Consulter le DMA");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        archiver1.setBackground(new java.awt.Color(255, 153, 0));
        archiver1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        archiver1.setForeground(new java.awt.Color(102, 102, 102));
        archiver1.setText("Archiver le DMA");
        archiver1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                archiver1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout boutonsRechercheLayout = new javax.swing.GroupLayout(boutonsRecherche);
        boutonsRecherche.setLayout(boutonsRechercheLayout);
        boutonsRechercheLayout.setHorizontalGroup(
            boutonsRechercheLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(boutonsRechercheLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(boutonsRechercheLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(archiver1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        boutonsRechercheLayout.setVerticalGroup(
            boutonsRechercheLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, boutonsRechercheLayout.createSequentialGroup()
                .addContainerGap(291, Short.MAX_VALUE)
                .addComponent(jButton7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(archiver1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel7.add(boutonsRecherche, "card2");

        boutonsSejours.setBackground(new java.awt.Color(255, 255, 255));

        jButton8.setBackground(new java.awt.Color(255, 153, 0));
        jButton8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton8.setForeground(new java.awt.Color(102, 102, 102));
        jButton8.setText("Envoi par HL7");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton13.setBackground(new java.awt.Color(255, 153, 0));
        jButton13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton13.setForeground(new java.awt.Color(102, 102, 102));
        jButton13.setText("Éditer le DMA");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jButton14.setBackground(new java.awt.Color(255, 153, 0));
        jButton14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton14.setForeground(new java.awt.Color(102, 102, 102));
        jButton14.setText("Consulter le DMA");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        archiver2.setBackground(new java.awt.Color(255, 153, 0));
        archiver2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        archiver2.setForeground(new java.awt.Color(102, 102, 102));
        archiver2.setText("Archiver le DMA");
        archiver2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                archiver2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout boutonsSejoursLayout = new javax.swing.GroupLayout(boutonsSejours);
        boutonsSejours.setLayout(boutonsSejoursLayout);
        boutonsSejoursLayout.setHorizontalGroup(
            boutonsSejoursLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(boutonsSejoursLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(boutonsSejoursLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(archiver2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        boutonsSejoursLayout.setVerticalGroup(
            boutonsSejoursLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, boutonsSejoursLayout.createSequentialGroup()
                .addContainerGap(291, Short.MAX_VALUE)
                .addComponent(jButton14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(archiver2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel7.add(boutonsSejours, "card2");

        jPanel4.add(jPanel7, java.awt.BorderLayout.EAST);

        getContentPane().add(jPanel4, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        JOptionPane d = new JOptionPane();
        int retour = d.showConfirmDialog(null, "Êtes-vous sûr de vouloir vous déconnecter ?", "Confirmation", JOptionPane.OK_CANCEL_OPTION);
        if (retour == JOptionPane.OK_OPTION) {
            loginframe lf = new loginframe();
            lf.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void asActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_asActionPerformed
        CardLayout cardLayout = (CardLayout) menuPanel.getLayout();
        cardLayout.show(menuPanel, "Sejours");
        CardLayout card = (CardLayout) centrePanel.getLayout();
        card.show(centrePanel, "Sejours");
        CardLayout card2 = (CardLayout) jPanel7.getLayout();
        card2.show(jPanel7, "BoutonsSejours");
    }//GEN-LAST:event_asActionPerformed

    private void acActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_acActionPerformed
        CardLayout cardLayout = (CardLayout) menuPanel.getLayout();
        cardLayout.show(menuPanel, "CreerDMA");
        CardLayout card = (CardLayout) centrePanel.getLayout();
        card.show(centrePanel, "CreerDMA");
        CardLayout card2 = (CardLayout) jPanel7.getLayout();
        card2.show(jPanel7, "Vide");
    }//GEN-LAST:event_acActionPerformed

    private void arActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_arActionPerformed
        CardLayout cardLayout = (CardLayout) menuPanel.getLayout();
        cardLayout.show(menuPanel, "RechercherDMA");
        CardLayout card = (CardLayout) centrePanel.getLayout();
        card.show(centrePanel, "RechercherDMA");
        CardLayout card2 = (CardLayout) jPanel7.getLayout();
        card2.show(jPanel7, "BoutonsRecherche");
    }//GEN-LAST:event_arActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        AfficherInformationsPatient aip = new AfficherInformationsPatient(p);
        aip.setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        EditerInformationsPatient ipm = new EditerInformationsPatient(p);
        ipm.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void nomFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nomFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nomFieldActionPerformed

    private void prenomFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prenomFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_prenomFieldActionPerformed

    private void ippFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ippFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ippFieldActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        DefaultTableModel resultRecherche = (DefaultTableModel) resultatsTable.getModel();
        ArrayList<Patient> lp = null;
        resultRecherche.setRowCount(0);
        if (!nomField.getText().isEmpty() && prenomField.getText().isEmpty() && ippField.getText().isEmpty()) {
            lp = connect.recherchePatientsNom(nomField.getText());
            for (int i = 0; i < lp.size(); i++) {
                lp.get(i);
                resultRecherche.addRow(new Object[]{lp.get(i).getNom(), lp.get(i).getPrenom(), lp.get(i).getIPP(), lp.get(i).getDate(), lp.get(i).getLieuNaissance(), lp.get(i).getSexe(),});
            }
        } else if (!nomField.getText().isEmpty() && !prenomField.getText().isEmpty() && ippField.getText().isEmpty()) {
            lp = connect.recherchePatientsNomPrenom(nomField.getText(), prenomField.getText());
            for (int i = 0; i < lp.size(); i++) {
                lp.get(i);
                resultRecherche.addRow(new Object[]{lp.get(i).getNom(), lp.get(i).getPrenom(), lp.get(i).getIPP(), lp.get(i).getDate(), lp.get(i).getLieuNaissance(), lp.get(i).getSexe(),});
            }
        } else if (nomField.getText().isEmpty() && !prenomField.getText().isEmpty() && ippField.getText().isEmpty()) {
            lp = connect.recherchePatientsPrenom(prenomField.getText());
            for (int i = 0; i < lp.size(); i++) {
                lp.get(i);
                resultRecherche.addRow(new Object[]{lp.get(i).getNom(), lp.get(i).getPrenom(), lp.get(i).getIPP(), lp.get(i).getDate(), lp.get(i).getLieuNaissance(), lp.get(i).getSexe(),});
            }
        } else if (nomField.getText().isEmpty() && prenomField.getText().isEmpty() && !ippField.getText().isEmpty()) {
            p = connect.recherchePatientsIPP(Integer.parseInt(ippField.getText()));
            resultRecherche.addRow(new Object[]{p.getNom(), p.getPrenom(), p.getIPP(), p.getDate(), p.getLieuNaissance(), p.getSexe(),});
        } else if (!nomField.getText().isEmpty() && !prenomField.getText().isEmpty() && !ippField.getText().isEmpty()) {
            lp = connect.recherchePatientsNomPrenomIPP(nomField.getText(), prenomField.getText(), Integer.parseInt(ippField.getText()));
            for (int i = 0; i < lp.size(); i++) {
                lp.get(i);
                resultRecherche.addRow(new Object[]{lp.get(i).getNom(), lp.get(i).getPrenom(), lp.get(i).getIPP(), lp.get(i).getDate(), lp.get(i).getLieuNaissance(), lp.get(i).getSexe(),});
            }
        } else if (!nomField.getText().isEmpty() && !prenomField.getText().isEmpty() && ippField.getText().isEmpty()) {
            lp = connect.recherchePatientsPrenomIPP(prenomField.getText(), Integer.parseInt(ippField.getText()));
            for (int i = 0; i < lp.size(); i++) {
                lp.get(i);
                resultRecherche.addRow(new Object[]{lp.get(i).getNom(), lp.get(i).getPrenom(), lp.get(i).getIPP(), lp.get(i).getDate(), lp.get(i).getLieuNaissance(), lp.get(i).getSexe(),});
            }
        } else if (!nomField.getText().isEmpty() && prenomField.getText().isEmpty() && !ippField.getText().isEmpty()) {
            lp = connect.recherchePatientsNomIPP(nomField.getText(), Integer.parseInt(ippField.getText()));
            for (int i = 0; i < lp.size(); i++) {
                lp.get(i);
                resultRecherche.addRow(new Object[]{lp.get(i).getNom(), lp.get(i).getPrenom(), lp.get(i).getIPP(), lp.get(i).getDate(), lp.get(i).getLieuNaissance(), lp.get(i).getSexe(),});
            }
        } else if (nomField.getText().isEmpty() && prenomField.getText().isEmpty() && ippField.getText().isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(null, "Aucun champ de recherche renseigné.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
        resultatsTable.setModel(resultRecherche);
        resultatsTable.repaint();
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        JOptionPane d = new JOptionPane();
        int retour = d.showConfirmDialog(null, "Êtes-vous sûr de vouloir vider le formulaire ?", "Confirmation", JOptionPane.OK_CANCEL_OPTION);
        if (retour == JOptionPane.OK_OPTION) {
            nomField.setText(null);
            prenomField.setText(null);
            ippField.setText(null);
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

    private void csActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_csActionPerformed
        CardLayout cardLayout = (CardLayout) menuPanel.getLayout();
        cardLayout.show(menuPanel, "Sejours");
        CardLayout card = (CardLayout) centrePanel.getLayout();
        card.show(centrePanel, "Sejours");
        CardLayout card2 = (CardLayout) jPanel7.getLayout();
        card2.show(jPanel7, "BoutonsSejours");
    }//GEN-LAST:event_csActionPerformed

    private void ccActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ccActionPerformed
        CardLayout cardLayout = (CardLayout) menuPanel.getLayout();
        cardLayout.show(menuPanel, "CreerDMA");
        CardLayout card = (CardLayout) centrePanel.getLayout();
        card.show(centrePanel, "CreerDMA");
        CardLayout card2 = (CardLayout) jPanel7.getLayout();
        card2.show(jPanel7, "Vide");
    }//GEN-LAST:event_ccActionPerformed

    private void crActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_crActionPerformed
        CardLayout cardLayout = (CardLayout) menuPanel.getLayout();
        cardLayout.show(menuPanel, "RechercherDMA");
        CardLayout card = (CardLayout) centrePanel.getLayout();
        card.show(centrePanel, "RechercherDMA");
        CardLayout card2 = (CardLayout) jPanel7.getLayout();
        card2.show(jPanel7, "BoutonsRecherche");
    }//GEN-LAST:event_crActionPerformed

    private void rsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rsActionPerformed
        CardLayout cardLayout = (CardLayout) menuPanel.getLayout();
        cardLayout.show(menuPanel, "Sejours");
        CardLayout card = (CardLayout) centrePanel.getLayout();
        card.show(centrePanel, "Sejours");
        CardLayout card2 = (CardLayout) jPanel7.getLayout();
        card2.show(jPanel7, "BoutonsSejours");
    }//GEN-LAST:event_rsActionPerformed

    private void rcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rcActionPerformed
        CardLayout cardLayout = (CardLayout) menuPanel.getLayout();
        cardLayout.show(menuPanel, "CreerDMA");
        CardLayout card = (CardLayout) centrePanel.getLayout();
        card.show(centrePanel, "CreerDMA");
        CardLayout card2 = (CardLayout) jPanel7.getLayout();
        card2.show(jPanel7, "Vide");
    }//GEN-LAST:event_rcActionPerformed

    private void rrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rrActionPerformed
        CardLayout cardLayout = (CardLayout) menuPanel.getLayout();
        cardLayout.show(menuPanel, "RechercherDMA");
        CardLayout card = (CardLayout) centrePanel.getLayout();
        card.show(centrePanel, "RechercherDMA");
        CardLayout card2 = (CardLayout) jPanel7.getLayout();
        card2.show(jPanel7, "BoutonsRecherche");
    }//GEN-LAST:event_rrActionPerformed

    private void caActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_caActionPerformed
        CardLayout cardLayout = (CardLayout) menuPanel.getLayout();
        cardLayout.show(menuPanel, "Accueil");
        CardLayout card = (CardLayout) centrePanel.getLayout();
        card.show(centrePanel, "Accueil");
        CardLayout card2 = (CardLayout) jPanel7.getLayout();
        card2.show(jPanel7, "BoutonsAccueil");
    }//GEN-LAST:event_caActionPerformed

    private void aaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aaActionPerformed
        CardLayout cardLayout = (CardLayout) menuPanel.getLayout();
        cardLayout.show(menuPanel, "Accueil");
        CardLayout card = (CardLayout) centrePanel.getLayout();
        card.show(centrePanel, "Accueil");
        CardLayout card2 = (CardLayout) jPanel7.getLayout();
        card2.show(jPanel7, "BoutonsAccueil");
    }//GEN-LAST:event_aaActionPerformed

    private void raActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_raActionPerformed
        CardLayout cardLayout = (CardLayout) menuPanel.getLayout();
        cardLayout.show(menuPanel, "Accueil");
        CardLayout card = (CardLayout) centrePanel.getLayout();
        card.show(centrePanel, "Accueil");
        CardLayout card2 = (CardLayout) jPanel7.getLayout();
        card2.show(jPanel7, "BoutonsAccueil");
    }//GEN-LAST:event_raActionPerformed

    private void prenomField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prenomField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_prenomField1ActionPerformed

    private void lieuFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lieuFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lieuFieldActionPerformed

    private void voieFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_voieFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_voieFieldActionPerformed

    private void complementFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_complementFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_complementFieldActionPerformed

    private void paysFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paysFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_paysFieldActionPerformed

    private void codeFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_codeFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_codeFieldActionPerformed

    private void villeFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_villeFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_villeFieldActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        DecimalFormat nf = new DecimalFormat("00000");
        String nom;
        String prenom;
        String lieu;
        String complement;
        String ville;
        String pays;
        String mail;
        Date date;
        int portable;
        int fixe;
        try {
            if (nomField1.getText().isEmpty()) {
                nom = null;
            } else {
                nom = nomField1.getText();
            }
            if (prenomField1.getText().isEmpty()) {
                prenom = null;
            } else {
                prenom = prenomField1.getText();
            }
            if (lieuField.getText().isEmpty()) {
                lieu = null;
            } else {
                lieu = lieuField.getText();
            }
            int numeroVoie = Integer.parseInt(voieField.getText());
            if (complementField.getText().isEmpty()) {
                complement = null;
            } else {
                complement = complementField.getText();
            }
            int codePostal = Integer.parseInt(codeField.getText());
            if (villeField.getText().isEmpty()) {
                ville = null;
            } else {
                ville = villeField.getText();
            }
            if (paysField.getText().isEmpty()) {
                pays = null;
            } else {
                pays = paysField.getText();
            }
            if (dateField.getDate().toString().isEmpty()) {
                date = null;
            } else {
                date = new Date(dateField.getDate().getYear(), dateField.getDate().getMonth(), dateField.getDate().getDate());
            }
            String sexe = sexeBox.getSelectedItem().toString();
            String typeVoie = voieBox.getSelectedItem().toString();
            portable = Integer.parseInt(portableField.getText());
            fixe = Integer.parseInt(fixeField.getText());
            if (mailField.getText().isEmpty()) {
                mail = null;
            } else {
                mail = mailField.getText();
            }
            Patient patient = connect.recherchePatientsNomPrenomDate(nom, sexe, date);
            if (patient.getNom() == null && patient.getPrenom() == null && patient.getDate() == null) {
                connect.ajouterDMA(nom.toUpperCase(), prenom, date, lieu.toUpperCase(), sexe, numeroVoie, typeVoie, complement, (int) codePostal, ville.toUpperCase(), pays.toUpperCase(), portable, fixe, mail);
                java.util.Date d = new java.util.Date();
                java.util.Date d1 = (java.util.Date) date;
                
                LancerServeurHL7 l = new LancerServeurHL7();
                l.admiPatientHL7(p.getIPP(), nom, prenom, sexe, d1, d1);
                
                lp = connect.getPatients();
                for (int i = 0; i < lp.size(); i++) {
                    lp.get(i);
                    result.addRow(new Object[]{lp.get(i).getNom(), lp.get(i).getPrenom(), lp.get(i).getIPP(), lp.get(i).getDate(), lp.get(i).getLieuNaissance(), lp.get(i).getSexe(),});
                }

                resultatsTable.setModel(result);
                resultatsTable1.setModel(result);
                resultatsTable.repaint();
                resultatsTable1.repaint();
            } else {
                javax.swing.JOptionPane.showMessageDialog(null, "Ce patient est déjà présent dans la base de données.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            System.out.println(ex);
            javax.swing.JOptionPane.showMessageDialog(null, "Informations incorrectes", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton10ActionPerformed

    private void portableFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_portableFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_portableFieldActionPerformed

    private void fixeFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fixeFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fixeFieldActionPerformed

    private void mailFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mailFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mailFieldActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        JOptionPane d = new JOptionPane();
        int retour = d.showConfirmDialog(null, "Êtes-vous sûr de vouloir vider le formulaire ?", "Confirmation", JOptionPane.OK_CANCEL_OPTION);
        if (retour == JOptionPane.OK_OPTION) {
            nomField.setText(null);
            prenomField.setText(null);
            dateField.setDate(null);
            lieuField.setText(null);
            sexeBox.setSelectedIndex(0);
            voieField.setText(null);
            voieBox.setSelectedIndex(0);
            complementField.setText(null);
            codeField.setText(null);
            villeField.setText(null);
            paysField.setText(null);
            portableField.setText(null);
            fixeField.setText(null);
            mailField.setText(null);
        }
    }//GEN-LAST:event_jButton12ActionPerformed

    private void archiverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_archiverActionPerformed
        Archivage_DP adp = new Archivage_DP(p);
        adp.setVisible(true);
    }//GEN-LAST:event_archiverActionPerformed

    private void smaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_smaActionPerformed
        CardLayout cardLayout = (CardLayout) menuPanel.getLayout();
        cardLayout.show(menuPanel, "Accueil");
        CardLayout card = (CardLayout) centrePanel.getLayout();
        card.show(centrePanel, "Accueil");
        CardLayout card2 = (CardLayout) jPanel7.getLayout();
        card2.show(jPanel7, "BoutonsAccueil");
    }//GEN-LAST:event_smaActionPerformed

    private void smsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_smsActionPerformed
        CardLayout cardLayout = (CardLayout) menuPanel.getLayout();
        cardLayout.show(menuPanel, "Sejours");
        CardLayout card = (CardLayout) centrePanel.getLayout();
        card.show(centrePanel, "Sejours");
        CardLayout card2 = (CardLayout) jPanel7.getLayout();
        card2.show(jPanel7, "BoutonsSejours");
    }//GEN-LAST:event_smsActionPerformed

    private void smcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_smcActionPerformed
        CardLayout cardLayout = (CardLayout) menuPanel.getLayout();
        cardLayout.show(menuPanel, "CreerDMA");
        CardLayout card = (CardLayout) centrePanel.getLayout();
        card.show(centrePanel, "CreerDMA");
        CardLayout card2 = (CardLayout) jPanel7.getLayout();
        card2.show(jPanel7, "Vide");
    }//GEN-LAST:event_smcActionPerformed

    private void smrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_smrActionPerformed
        CardLayout cardLayout = (CardLayout) menuPanel.getLayout();
        cardLayout.show(menuPanel, "RechercherDMA");
        CardLayout card = (CardLayout) centrePanel.getLayout();
        card.show(centrePanel, "RechercherDMA");
        CardLayout card2 = (CardLayout) jPanel7.getLayout();
        card2.show(jPanel7, "BoutonsRecherche");
    }//GEN-LAST:event_smrActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        EditerInformationsPatient ipm = new EditerInformationsPatient(p3);
        ipm.setVisible(true);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        AfficherInformationsPatient aip = new AfficherInformationsPatient(p3);
        aip.setVisible(true);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void archiver1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_archiver1ActionPerformed
        Archivage_DP adp = new Archivage_DP(p3);
        adp.setVisible(true);
    }//GEN-LAST:event_archiver1ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        EditerInformationsPatient ipm = new EditerInformationsPatient(p2);
        ipm.setVisible(true);
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        AfficherInformationsPatient aip = new AfficherInformationsPatient(p2);
        aip.setVisible(true);
    }//GEN-LAST:event_jButton14ActionPerformed

    private void archiver2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_archiver2ActionPerformed
        Archivage_DP adp = new Archivage_DP(p2);
        adp.setVisible(true);
    }//GEN-LAST:event_archiver2ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        ChangerMotDePasse mdp = new ChangerMotDePasse(sa);
        mdp.setVisible(true);
    }//GEN-LAST:event_jButton16ActionPerformed

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
            java.util.logging.Logger.getLogger(InterfaceSecretaireAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InterfaceSecretaireAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InterfaceSecretaireAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InterfaceSecretaireAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
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
    private javax.swing.JButton ac;
    private javax.swing.JPanel accueil;
    private javax.swing.JButton ar;
    private javax.swing.JButton archiver;
    private javax.swing.JButton archiver1;
    private javax.swing.JButton archiver2;
    private javax.swing.JButton as;
    private javax.swing.JPanel boutonsAccueil;
    private javax.swing.JPanel boutonsRecherche;
    private javax.swing.JPanel boutonsSejours;
    private javax.swing.JPanel boutonsVide;
    private javax.swing.JButton ca;
    private javax.swing.JButton cc;
    private javax.swing.JPanel centrePanel;
    private javax.swing.JTextField codeField;
    private javax.swing.JTextField complementField;
    private javax.swing.JButton cr;
    private javax.swing.JPanel creerDMA;
    private javax.swing.JPanel creerPatient;
    private javax.swing.JButton cs;
    private com.toedter.calendar.JDateChooser dateField;
    private javax.swing.JTextField fixeField;
    private javax.swing.JTextField ippField;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField lieuField;
    private javax.swing.JPanel listePatients;
    private javax.swing.JTextField mailField;
    private javax.swing.JPanel menuPanel;
    private javax.swing.JPanel menuSejour;
    private javax.swing.JTextField nomField;
    private javax.swing.JTextField nomField1;
    private javax.swing.JLabel nomLabel;
    private javax.swing.JTextField paysField;
    private javax.swing.JTextField portableField;
    private javax.swing.JTextField prenomField;
    private javax.swing.JTextField prenomField1;
    private javax.swing.JButton ra;
    private javax.swing.JButton rc;
    private javax.swing.JPanel recherchePatient;
    private javax.swing.JPanel rechercherDMA;
    private javax.swing.JTable resultatsTable;
    private javax.swing.JTable resultatsTable1;
    private javax.swing.JTable resultatsTable2;
    private javax.swing.JButton rr;
    private javax.swing.JButton rs;
    private javax.swing.JPanel sejours;
    private javax.swing.JComboBox<String> sexeBox;
    private javax.swing.JButton sma;
    private javax.swing.JButton smc;
    private javax.swing.JButton smr;
    private javax.swing.JButton sms;
    private javax.swing.JTextField villeField;
    private javax.swing.JComboBox<String> voieBox;
    private javax.swing.JTextField voieField;
    // End of variables declaration//GEN-END:variables
}
