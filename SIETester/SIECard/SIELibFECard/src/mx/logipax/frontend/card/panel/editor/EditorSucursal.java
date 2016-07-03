/*
Derechos Reservados (c)
Ing. Jorge Guzmán Camarena / Consultoria Integral en Sistemas y Finanzas, S.A. de C.V.
2009, 2010, 2011, 2012, 2013, 2014
logipax Marca Registrada (R)  2003, 2014
*/
package mx.logipax.frontend.card.panel.editor;

import javax.swing.ImageIcon;
import mx.logipax.db.interfase.FEMain;
import mx.logipax.frontend.card.panel.editor.cat.CardEquiposFrom;
import mx.logipax.frontend.card.panel.editor.cat.CardMainFrom;
import mx.logipax.frontend.card.panel.editor.cat.CardPersonalFrom;
import mx.logipax.frontend.card.panel.editor.cat.EquiposTable;
import mx.logipax.frontend.card.panel.editor.cat.FormInterfase;
import mx.logipax.frontend.card.panel.editor.cat.ParentFormInterfase;
import mx.logipax.frontend.card.panel.editor.cat.PersonasTable;
import mx.logipax.frontend.card.panel.editor.rec.EquiposRec;
import mx.logipax.frontend.card.panel.editor.rec.PersonasRec;
import mx.logipax.ide.IDELogipax;
import mx.logipax.shared.objects.viewer.Report;
import mx.logipax.frontend.viewer.panel.VTableInterfase;
import mx.logipax.frontend.viewer.panel.data.ExtendedData;
import mx.logipax.ide.IDEPanels;
import mx.logipax.ide.display.ModelessJPanel;
import mx.logipax.ide.display.PanelUtility;
import mx.logipax.shared.DlgProcessing;
import mx.logipax.shared.objects.RepoLevel;
import mx.logipax.shared.objects.card.cat.CardSuc;
import mx.logipax.shared.objects.card.cat.CardSucs;
import org.json.JSONObject;

public class EditorSucursal extends ModelessJPanel implements EditorInterfase, FormInterfase, ParentFormInterfase {

    private FEMain main;
    private final IDELogipax frame;
    private final IDEPanels panels;
    private final VTableInterfase panel;
    private ExtendedData reportExtended;
    private final Report report;
    private final CardSuc card;
    boolean editable = true;
    private String id = "";
    private PersonasTable t01;
    private PersonasRec r01;
    private EquiposTable t02;
    private EquiposRec r02;

    public EditorSucursal(FEMain main2, IDELogipax frame2, IDEPanels panels2, VTableInterfase panel2, ExtendedData reportExtended2, Report report2) {
        super(panels2, "Cédula Sucursal", true, false, true, false, false);
        this.main = main2;
        this.frame = frame2;
        this.panels = panels2;
        this.panel = panel2;
        this.reportExtended = reportExtended2;
        this.report = report2;
        //this.dataspn = dataspn2;
        this.card = new CardSuc(new JSONObject());
        this.id = "ID";
        initComponents();
//        final Border border = cdsem.getBorder();;
//        cdsem.addMouseListener(new java.awt.event.MouseAdapter() {
//            public void mouseEntered(java.awt.event.MouseEvent evt) {
//                //cdsem.setBorder(BorderFactory.createTitledBorder(border));
//                //cdsem.setBorder(BorderFactory.createTitledBorder(border));
//                cdsem.setBorder(new LineBorder(Color.yellow, 5));
//            }
//
//            public void mouseExited(java.awt.event.MouseEvent evt) {
//                cdsem.setBorder(border);
//            }
//        });    
        
//cdsem.getModel().addChangeListener(new ChangeListener() {
//        @Override
//        public void stateChanged(ChangeEvent e) {
//            ButtonModel model = (ButtonModel) e.getSource();
//            if (model.isRollover()) {
//                cdsem.setBackground(Color.RED);//frame.colorRec().background0Dark);
//            } else {
//                cdsem.setBackground(Color.CYAN);//frame.colorRec().background2Dark);
//            }
//        }
//    });        
        boolean editable = true;
        boolean addrow = editable;
        ImageIcon[] rfcicons = new ImageIcon[]{frame.imagesRec().shwicon};
        if (false) {
            rfcicons = new ImageIcon[]{frame.imagesRec().shwicon, frame.imagesRec().edticon, frame.imagesRec().delicon};
        }
        r01 = new PersonasRec(frame);
        t01 = new PersonasTable(main, frame, name, this, id, r01, r01.getHeaders(), r01.getHeadersJustify(), addrow, editable?1:0, rfcicons);
        PanelUtility.set(perspn, t01);
        r02 = new EquiposRec(frame);
        t02 = new EquiposTable(main, frame, name, this, id, r02, r02.getHeaders(), r02.getHeadersJustify(), addrow, editable?2:0, rfcicons);
        PanelUtility.set(eqpspn, t02);
    }
  
    public ModelessJPanel getModelessJPanel() {
        return this;
    }
   
    public void tabSelected(int tabId) {
        if (tabId == 1) {
            editPersonal();
        }
        if (tabId == 2) {
            editEquipos();
        }
    }

    public void dispose() {
//        dataspn.setLeftComponent(null);
//        dataspn.setRightComponent(null);
    }

    public int setGroupSegment(int inx, int vinx) {
        return 0;
    }
    RepoLevel levelD1;

    public void setLevel(RepoLevel levelD1) {
        this.levelD1 = levelD1;
    }

    public boolean detailactive(int bt, int nsel, int indicator, int segSel, int segInx, int segVar, int pinx, int vinx, int pnt, int dateinx, final RepoLevel[] drlevelD1, final RepoLevel[] drlevelD2) {
        return false;
    }

    public void detail(int bt, java.awt.Component c, int x, int y, int index, int nsel, int indicator, int segSel, int segInx, int segVar, int pinx, int vinx, int pnt, 
            int[][] datesinx, 
            String periodStr, String viewStr,             
            final RepoLevel[] drlevelD1, final RepoLevel[] drlevelD2) {
    }

    public boolean selactive(int nsel) {
        return nsel == 0 || nsel == 1;
    }

    public void clear() {
//        data = new Object[0][];
//        leftTableModel = new ReportTable.PubTableModel(true);
//        rtable.setModel(leftTableModel);
//        leftTableModel.fireTableStructureChanged();
//
//        tableModel = new ReportTable.PubTableModel(false);
//        table.setModel(tableModel);
//        tableModel.fireTableStructureChanged();
    }

    public void reportLoaded(Report report) {
    }

    public void dataLoaded(JSONObject list) {
        card.reload(list);
        r0.setText(card.getResponsableId());
        r1.setText(card.getResponsableNombre());
        f0.setText(card.getFormatoId());
        m0.setText(card.getMismaId());
    }

    public boolean createValues() {
        return true;
    }

    public int reportChanged(boolean editable, int pinx, int vinx, int pnt, int toppnt, Object[][] tabvalues) {
        return 0;
    }
    
    public void dataChanged(Object data) {
    }

    public void load(JSONObject jresult) {
        CardSucs rrecords = new CardSucs(jresult);
    }
    
    public void editMain() {
        CardMainFrom form = new CardMainFrom(main, frame, "Cédula::Información ", DlgProcessing.ADD, card, this, "", true, card.getCardLevel(), card.getCardId());
        form.dialog(this.dialog);
        form.dialog = this.dialog;
        panels.push("main", form, null, null, null, null, null, null);
    }
    public void editPersonal() {
        CardPersonalFrom form = new CardPersonalFrom(main, frame, "Cédula::Personal ", DlgProcessing.ADD, card, this, "", true, card.getCardLevel(), card.getCardId());
        form.dialog(this.dialog);
        form.dialog = this.dialog;
        panels.push("personal", form, null, null, null, null, null, null);
    }
    public void editEquipos() {
        CardEquiposFrom form = new CardEquiposFrom(main, frame, "Cédula::Equipos ", DlgProcessing.ADD, card, this, "", true, card.getCardLevel(), card.getCardId());
        form.dialog(this.dialog);
        form.dialog = this.dialog;
        panels.push("equipos", form, null, null, null, null, null, null);
    }
    
    public void comportamiento(int tipo) {
        
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        personalpn = new javax.swing.JPanel();
        persp = new javax.swing.JScrollPane();
        pertb = new javax.swing.JTable();
        equipospn = new javax.swing.JPanel();
        eqpsp = new javax.swing.JScrollPane();
        eqptb = new javax.swing.JTable();
        responsablepn = new javax.swing.JPanel();
        resplb = new javax.swing.JLabel();
        resp = new javax.swing.JTextField();
        emaillb = new javax.swing.JLabel();
        email = new javax.swing.JTextField();
        clvlb = new javax.swing.JLabel();
        clv = new javax.swing.JTextField();
        pn = new javax.swing.JPanel();
        rlb = new javax.swing.JLabel();
        r0 = new javax.swing.JTextField();
        r1 = new javax.swing.JTextField();
        flb = new javax.swing.JLabel();
        f0 = new javax.swing.JTextField();
        m0 = new javax.swing.JTextField();
        edt = new javax.swing.JButton();
        perspn = new javax.swing.JPanel();
        eqpspn = new javax.swing.JPanel();
        comppn = new javax.swing.JPanel();
        cdsem = new javax.swing.JButton();
        cmes = new javax.swing.JButton();
        cseg = new javax.swing.JButton();
        csem = new javax.swing.JButton();
        cfer = new javax.swing.JButton();

        pertb.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Departamento", "Puesto", "Personas"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        persp.setViewportView(pertb);

        org.jdesktop.layout.GroupLayout personalpnLayout = new org.jdesktop.layout.GroupLayout(personalpn);
        personalpn.setLayout(personalpnLayout);
        personalpnLayout.setHorizontalGroup(
            personalpnLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 100, Short.MAX_VALUE)
            .add(personalpnLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(personalpnLayout.createSequentialGroup()
                    .addContainerGap()
                    .add(persp, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        personalpnLayout.setVerticalGroup(
            personalpnLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 100, Short.MAX_VALUE)
            .add(personalpnLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(personalpnLayout.createSequentialGroup()
                    .addContainerGap()
                    .add(persp, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        eqptb.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Departamento", "Equipo", "No Equipos"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        eqpsp.setViewportView(eqptb);

        org.jdesktop.layout.GroupLayout equipospnLayout = new org.jdesktop.layout.GroupLayout(equipospn);
        equipospn.setLayout(equipospnLayout);
        equipospnLayout.setHorizontalGroup(
            equipospnLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 100, Short.MAX_VALUE)
            .add(equipospnLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(equipospnLayout.createSequentialGroup()
                    .addContainerGap()
                    .add(eqpsp, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        equipospnLayout.setVerticalGroup(
            equipospnLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 100, Short.MAX_VALUE)
            .add(equipospnLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(equipospnLayout.createSequentialGroup()
                    .addContainerGap()
                    .add(eqpsp, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        resplb.setText("Responsable:");

        resp.setEditable(false);
        resp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                respActionPerformed(evt);
            }
        });

        emaillb.setText("Correo:");

        email.setEditable(false);
        email.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emailActionPerformed(evt);
            }
        });

        clvlb.setText("Perfíl Visor:");

        clv.setEditable(false);
        clv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clvActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout responsablepnLayout = new org.jdesktop.layout.GroupLayout(responsablepn);
        responsablepn.setLayout(responsablepnLayout);
        responsablepnLayout.setHorizontalGroup(
            responsablepnLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 451, Short.MAX_VALUE)
            .add(responsablepnLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(responsablepnLayout.createSequentialGroup()
                    .add(11, 11, 11)
                    .add(responsablepnLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(org.jdesktop.layout.GroupLayout.TRAILING, resplb)
                        .add(org.jdesktop.layout.GroupLayout.TRAILING, emaillb)
                        .add(org.jdesktop.layout.GroupLayout.TRAILING, clvlb))
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(responsablepnLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(resp, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                        .add(email)
                        .add(clv))
                    .addContainerGap()))
        );
        responsablepnLayout.setVerticalGroup(
            responsablepnLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 273, Short.MAX_VALUE)
            .add(responsablepnLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(responsablepnLayout.createSequentialGroup()
                    .add(88, 88, 88)
                    .add(responsablepnLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(resp, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(resplb))
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(responsablepnLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(email, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(emaillb))
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(responsablepnLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(clv, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(clvlb))
                    .addContainerGap(89, Short.MAX_VALUE)))
        );

        setBackground(new java.awt.Color(255, 255, 255));

        pn.setBackground(new java.awt.Color(255, 255, 255));

        rlb.setText("Responsable:");

        r0.setEditable(false);

        r1.setEditable(false);

        flb.setText("Formato:");

        f0.setEditable(false);

        m0.setEditable(false);

        edt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/lapiz.png"))); // NOI18N
        edt.setBorder(null);
        edt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                edtActionPerformed(evt);
            }
        });

        perspn.setBackground(new java.awt.Color(255, 255, 255));
        perspn.setBorder(javax.swing.BorderFactory.createTitledBorder("Personal"));
        perspn.setToolTipText("");
        perspn.setOpaque(false);

        org.jdesktop.layout.GroupLayout perspnLayout = new org.jdesktop.layout.GroupLayout(perspn);
        perspn.setLayout(perspnLayout);
        perspnLayout.setHorizontalGroup(
            perspnLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 0, Short.MAX_VALUE)
        );
        perspnLayout.setVerticalGroup(
            perspnLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 15, Short.MAX_VALUE)
        );

        eqpspn.setBackground(new java.awt.Color(255, 255, 255));
        eqpspn.setBorder(javax.swing.BorderFactory.createTitledBorder("Equipos"));
        eqpspn.setToolTipText("");
        eqpspn.setOpaque(false);

        org.jdesktop.layout.GroupLayout eqpspnLayout = new org.jdesktop.layout.GroupLayout(eqpspn);
        eqpspn.setLayout(eqpspnLayout);
        eqpspnLayout.setHorizontalGroup(
            eqpspnLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 0, Short.MAX_VALUE)
        );
        eqpspnLayout.setVerticalGroup(
            eqpspnLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 14, Short.MAX_VALUE)
        );

        comppn.setBackground(new java.awt.Color(255, 255, 255));
        comppn.setBorder(javax.swing.BorderFactory.createTitledBorder("Comportamiento Estadístico"));
        comppn.setToolTipText("");
        comppn.setOpaque(false);

        cdsem.setText("Día de Semana");
        cdsem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cdsemActionPerformed(evt);
            }
        });

        cmes.setText("Mensual");
        cmes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmesActionPerformed(evt);
            }
        });

        cseg.setText("Segmentos");
        cseg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                csegActionPerformed(evt);
            }
        });

        csem.setText("Semanal");
        csem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                csemActionPerformed(evt);
            }
        });

        cfer.setText("Feriados");
        cfer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cferActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout comppnLayout = new org.jdesktop.layout.GroupLayout(comppn);
        comppn.setLayout(comppnLayout);
        comppnLayout.setHorizontalGroup(
            comppnLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(comppnLayout.createSequentialGroup()
                .add(17, 17, 17)
                .add(cdsem, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(cmes, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(csem, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(cseg, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(cfer, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        comppnLayout.setVerticalGroup(
            comppnLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(comppnLayout.createSequentialGroup()
                .add(comppnLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(cdsem)
                    .add(cmes)
                    .add(cseg)
                    .add(csem)
                    .add(cfer))
                .add(12, 12, 12))
        );

        org.jdesktop.layout.GroupLayout pnLayout = new org.jdesktop.layout.GroupLayout(pn);
        pn.setLayout(pnLayout);
        pnLayout.setHorizontalGroup(
            pnLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, pnLayout.createSequentialGroup()
                .add(pnLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(eqpspn, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, perspn, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, pnLayout.createSequentialGroup()
                        .add(pnLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, rlb)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, flb))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(pnLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(pnLayout.createSequentialGroup()
                                .add(f0, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 184, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(m0, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 184, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(pnLayout.createSequentialGroup()
                                .add(r0, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 72, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(r1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 318, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                        .add(9, 9, 9)
                        .add(edt))
                    .add(comppn, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnLayout.setVerticalGroup(
            pnLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(pnLayout.createSequentialGroup()
                .addContainerGap()
                .add(pnLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, edt)
                    .add(pnLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(rlb)
                        .add(r0, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(r1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(pnLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(flb)
                    .add(f0, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(m0, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(perspn, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(0, 0, 0)
                .add(eqpspn, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(comppn, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(30, 30, 30))
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .add(30, 30, 30)
                .add(pn, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(30, 30, 30))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(30, 30, 30)
                .add(pn, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void respActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_respActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_respActionPerformed

    private void emailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_emailActionPerformed

    private void clvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clvActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_clvActionPerformed

    private void edtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_edtActionPerformed
        // TODO add your handling code here:
        editMain();
    }//GEN-LAST:event_edtActionPerformed

    private void csegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_csegActionPerformed
        // TODO add your handling code here:
        comportamiento(0);
    }//GEN-LAST:event_csegActionPerformed

    private void cdsemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cdsemActionPerformed
        // TODO add your handling code here:
        comportamiento(7);
    }//GEN-LAST:event_cdsemActionPerformed

    private void cmesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmesActionPerformed
        // TODO add your handling code here:
        comportamiento(12);       
    }//GEN-LAST:event_cmesActionPerformed

    private void csemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_csemActionPerformed
        // TODO add your handling code here:
        comportamiento(52);
    }//GEN-LAST:event_csemActionPerformed

    private void cferActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cferActionPerformed
        // TODO add your handling code here:
        comportamiento(-1);
    }//GEN-LAST:event_cferActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cdsem;
    private javax.swing.JButton cfer;
    private javax.swing.JTextField clv;
    private javax.swing.JLabel clvlb;
    private javax.swing.JButton cmes;
    private javax.swing.JPanel comppn;
    private javax.swing.JButton cseg;
    private javax.swing.JButton csem;
    private javax.swing.JButton edt;
    private javax.swing.JTextField email;
    private javax.swing.JLabel emaillb;
    private javax.swing.JScrollPane eqpsp;
    private javax.swing.JPanel eqpspn;
    private javax.swing.JTable eqptb;
    private javax.swing.JPanel equipospn;
    private javax.swing.JTextField f0;
    private javax.swing.JLabel flb;
    private javax.swing.JTextField m0;
    private javax.swing.JPanel personalpn;
    private javax.swing.JScrollPane persp;
    private javax.swing.JPanel perspn;
    private javax.swing.JTable pertb;
    private javax.swing.JPanel pn;
    private javax.swing.JTextField r0;
    private javax.swing.JTextField r1;
    private javax.swing.JTextField resp;
    private javax.swing.JLabel resplb;
    private javax.swing.JPanel responsablepn;
    private javax.swing.JLabel rlb;
    // End of variables declaration//GEN-END:variables
}
