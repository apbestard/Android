package mx.logipax.frontend.card.panel.editor.cat;

import javax.swing.JOptionPane;
import mx.logipax.db.interfase.FEMain;
import mx.logipax.frontend.card.panel.CatalogUtility;
import mx.logipax.frontend.card.panel.editor.rec.PersonaRec;
import mx.logipax.frontend.card.panel.editor.rec.PersonasRec;
import mx.logipax.shared.DlgProcessing;
import mx.logipax.ide.IDELogipax;
import mx.logipax.ide.utility.EditCombo;
import mx.logipax.ide.display.ModalJPanel;
import mx.logipax.ide.display.ModalParentInterfase;
import mx.logipax.ide.utility.Editable;
import mx.logipax.ide.utility.EditableDate;
import mx.logipax.interfase.SIE;
import mx.logipax.shared.objects.card.cat.CardSuc;
import mx.logipax.shared.objects.card.cat.CardSucIn;
import mx.logipax.shared.objects.card.cat.CardSucPersona;
import mx.logipax.shared.objects.card.cat.CardSucs;
import org.json.JSONObject;

public class PersonaForm extends ModalJPanel {

    private FEMain main;
    private IDELogipax frame;
    private PersonaForm panel;
    private String function;
    private PersonasRec rrecords;
    private ParentFormInterfase form;
    private PersonaRec rrecord;
    private CardSucPersona inidata = null;
    private boolean started = false;
    private String creador = "";
    private boolean edit = false;
    private boolean displayed = false;
    private EditCombo fdeptcb;
    private EditCombo fpuestocb;
    private Editable fpers;
    private Editable fvacs;
    private EditableDate faper;

    public PersonaForm(FEMain main, IDELogipax frame, String name, String function, PersonasRec rrecords, PersonaRec rrecord, ParentFormInterfase form, String creador, boolean edit, ModalParentInterfase parent, int id) {
        super(frame, name, parent, id);
        this.main = main;
        this.frame = frame;
        this.panel = this;
        this.rrecords = rrecords;
        this.rrecord = rrecord;
        this.form = form;
        this.function = function;
        this.creador = creador;
        this.edit = edit;
        initComponents();
        setBackground(frame.colorRec().backgroundDark);
        pn.setBackground(frame.colorRec().background2Dark);
        create();
    }

    private void create() {
        fdeptcb = new EditCombo(frame, this, "Departamento", deptcb, null, new String[]{}, "Seleccione el Departamento", null, error);
        fpuestocb = new EditCombo(frame, this, "Departamento", puestocb, null, new String[]{}, "Seleccione el Puesto", null, error);
        main.getAsync().enqueueJob(new Runnable() {
            public void run() {
                CatalogUtility.loadTabla(frame, main.getURL(), main.getDispatcher(), main.getTicket(), true, "departamento", "", "", fdeptcb, error);
                CatalogUtility.loadTabla(frame, main.getURL(), main.getDispatcher(), main.getTicket(), true, "puesto", "", "", fpuestocb, error);
                frame.waitingPop();
            }
        });
        fpers = new Editable(frame, this, "No Personas", pers, null, "Ingrese el No de Personas", null, error);
        fvacs = new Editable(frame, this, "No Vacantes", pers, null, "Ingrese el No de Vacantes", null, error);
        frame.waitingPush("Inicializando...");
    }

    private void get0(final String responsableId) {
        error.setText(null);
        final CardSucPersona rrecord = new CardSucPersona(new JSONObject());
        rrecord.setDeptoId(responsableId);
        rrecord.update();
        main.getAsync().enqueueJob(new Runnable() {
            public void run() {
//                CardSucIn in = new CardSucIn(new JSONObject());
//                in.setTicket(main.getTicket());
//                in.setFunction(DlgProcessing.SHW);
//                in.update(rrecord.json());
//                //                   
//                JSONObject jresult = SIE.interfase(frame.getClass(), main.getURL(), main.getDispatcher(), "rrecord", in.json());
//                frame.waitingPop();
//                String errorStr = null;
//                if (jresult.has("exception")) {
//                    JSONObject exception = jresult.getJSONObject("exception");
//                    errorStr = exception.getString("error");
//                }
//                if (jresult.has("error")) {
//                    errorStr = jresult.getString("error");
//                }
//                if (errorStr != null) {
//                    if (!started) {
//                        JOptionPane.showMessageDialog(frame.getFrame(), errorStr, "Error", JOptionPane.ERROR_MESSAGE);
//                    } else {
//                        error.setText(errorStr);
//                    }
//                    return;
//                }
//                CardSucs rrecords = new CardSucs(jresult);
//                if (started) {
//                    if (form != null) {
//                        form.loadCardSucs(rrecords);
//                    }
//                    ok();
//                    return;
//                }
//                if (!load(rrecords)) {
//                    JOptionPane.showMessageDialog(frame.getFrame(), "No pudo cargar rrecords", "Error", JOptionPane.ERROR_MESSAGE);
//                    return;
//                }
//                started = true;
//                if (!edit && responsableId != null) {
//                    panel.pushThis(new DialogModelessJPanel(frame, panel));
//                }
//                refresh();
            }
        });
        frame.waitingPush(responsableId + "...");
    }

    private void update() {
        rrecord.getRecord().setDeptoId(CatalogUtility.getKey(fdeptcb.get()));
        rrecord.getRecord().update();
        if (function.equals(DlgProcessing.DEL)) {
            rrecords.deleteRecord(rrecord);
        } else if (function.equals(DlgProcessing.EDT)) {
            rrecords.updateRecord(rrecord);
        } else if (DlgProcessing.isAdd(function)) {
            rrecords.insertRecord(rrecord);
        }
        main.getAsync().enqueueJob(new Runnable() {
            public void run() {
                CardSucIn in = new CardSucIn(new JSONObject());
                in.setTicket(main.getTicket());
                in.setFunction(function);
                in.update(rrecord.getRecord().json());
                JSONObject jresult = new JSONObject();
                if (function.equals(DlgProcessing.EDT) || function.equals(DlgProcessing.ADD) || function.equals(DlgProcessing.DEL)) {
                    jresult = SIE.interfase(frame.getClass(), main.getURL(), main.getDispatcher(), "rrecord", in.json());
                } else {
                    ok();
                }
                frame.waitingPop();
                String errorStr = null;
                if (jresult.has("exception")) {
                    JSONObject exception = jresult.getJSONObject("exception");
                    errorStr = exception.getString("error");
                }
                if (jresult.has("error")) {
                    errorStr = jresult.getString("error");
                }
                if (errorStr != null) {
                    if (!started) {
                        JOptionPane.showMessageDialog(frame.getFrame(), errorStr, "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        error.setText(errorStr);
                    }
                    return;
                }
                CardSucs rrecords = new CardSucs(jresult);
                if (started) {
                    if (form != null) {
                        form.load(jresult);
                    }
                    ok();
                    return;
                }
                if (!load(rrecords.record)) {
                    JOptionPane.showMessageDialog(frame.getFrame(), "No pudo cargar rrecords", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                started = true;
//                if (!edit && responsableId != null) {
//                    panel.pushThis(new DialogModelessJPanel(frame, panel));
//                }
                refresh();
            }
        });
        frame.waitingPush(fdeptcb.get().toLowerCase() + "...");
    }

    public void activate() {
        super.activate();
        setOkDefaultButton(ok);
        error.setText(null);
        if (function.equals(DlgProcessing.EDT)) {
        } else if (function.equals(DlgProcessing.ADD)) {
            setFirstComponent(deptcb);
        }
        refresh();
    }

    private boolean load(CardSuc data) {
        String id = null;
//        if (data.rrecord.getDeptoId() != null && data.rrecord.getDeptoId().length() > 0) {
//            id = data.rrecord.getDeptoId();
//            inidata = data.rrecord;
//        } else {
//            for (int i = 0; i < data.rrecords.length; i++) {
//                id = data.rrecords[i].getDeptoId();
//                if (id != null) {
//                    inidata = data.rrecords[i];
//                    break;
//                }
//            }
//        }
        if (inidata == null) {
            return false;
        }
        rrecord = new PersonaRec(frame, rrecords, id, inidata);
        return rrecord != null;
    }

    private void refresh() {
        if (rrecord != null) {
            fdeptcb.set(rrecord.getRecord().getDeptoId());
        }
        if (inidata != null) {
            fdeptcb.set(inidata.getDeptoId());
        }
        if (fdeptcb != null) {
            fdeptcb.set();
        }
        boolean noedit = (function.equals(DlgProcessing.DEL) || function.equals(DlgProcessing.SHW) || function.equals(DlgProcessing.ADD3));
        if (!noedit && !edit) {
            noedit = true;
        }
        if (!function.equals(DlgProcessing.EDT)) {
            ok.setText(function);
        } else if (edit) {
            ok.setText("Actualizar");
        } else {
            ok.setText("Editar");
        }
        if (!function.equals(DlgProcessing.ADD)) {
        }
        if (!noedit) {
            //fdeptcb.filter(1, 32, Editable.ANY);
        }
        if (!noedit && function.equals(DlgProcessing.ADD)) {
        }
        fdeptcb.setEditable(!noedit && function.equals(DlgProcessing.ADD));
        if (function.equals(DlgProcessing.EDT) || function.equals(DlgProcessing.ADD3) || function.equals(DlgProcessing.DEL) || function.equals(DlgProcessing.SHW)) {
            if (fdeptcb != null) {
                fdeptcb.setEditable(false);
            }
        }
        if (function.equals(DlgProcessing.DEL)) {
            fdeptcb.setEditable(false);
        }
        displayed = true;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainpn = new javax.swing.JPanel();
        error = new javax.swing.JLabel();
        pn = new javax.swing.JPanel();
        deptlb = new javax.swing.JLabel();
        deptcb = new javax.swing.JComboBox();
        cancel = new javax.swing.JButton();
        ok = new javax.swing.JButton();
        puestolb = new javax.swing.JLabel();
        puestocb = new javax.swing.JComboBox();
        perslb = new javax.swing.JLabel();
        pers = new javax.swing.JTextField();
        vacslb = new javax.swing.JLabel();
        vacs = new javax.swing.JTextField();

        setBackground(new java.awt.Color(255, 255, 255));
        setOpaque(false);

        mainpn.setBackground(new java.awt.Color(255, 255, 255));
        mainpn.setOpaque(false);

        error.setForeground(new java.awt.Color(192, 0, 0));
        error.setText(" ");

        pn.setBackground(new java.awt.Color(255, 255, 255));
        pn.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        deptlb.setText("Departamento:");

        cancel.setText("Cancelar");
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelActionPerformed(evt);
            }
        });

        ok.setText("Aceptar");
        ok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okActionPerformed(evt);
            }
        });

        puestolb.setText("Puesto:");

        perslb.setText("No Personas:");

        vacslb.setText("Vacantes:");

        org.jdesktop.layout.GroupLayout pnLayout = new org.jdesktop.layout.GroupLayout(pn);
        pn.setLayout(pnLayout);
        pnLayout.setHorizontalGroup(
            pnLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(pnLayout.createSequentialGroup()
                .add(20, 20, 20)
                .add(pnLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(pnLayout.createSequentialGroup()
                        .add(ok, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 113, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(cancel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 113, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(pnLayout.createSequentialGroup()
                        .add(deptlb)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(deptcb, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 305, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(pnLayout.createSequentialGroup()
                        .add(puestolb)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(puestocb, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 305, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(pnLayout.createSequentialGroup()
                        .add(pnLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(pnLayout.createSequentialGroup()
                                .add(perslb)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(pers, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 93, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(pnLayout.createSequentialGroup()
                                .add(vacslb)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(vacs, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 93, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                        .add(212, 212, 212)))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnLayout.setVerticalGroup(
            pnLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(pnLayout.createSequentialGroup()
                .addContainerGap()
                .add(pnLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(deptlb)
                    .add(deptcb, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(pnLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(puestolb)
                    .add(puestocb, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(pnLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(perslb)
                    .add(pers, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(pnLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(vacslb)
                    .add(vacs, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(pnLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(cancel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 37, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(ok, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 37, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout mainpnLayout = new org.jdesktop.layout.GroupLayout(mainpn);
        mainpn.setLayout(mainpnLayout);
        mainpnLayout.setHorizontalGroup(
            mainpnLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(error, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, mainpnLayout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .add(pn, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );
        mainpnLayout.setVerticalGroup(
            mainpnLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(mainpnLayout.createSequentialGroup()
                .add(error)
                .add(18, 18, Short.MAX_VALUE)
                .add(pn, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(mainpn, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(mainpn, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelActionPerformed
        // TODO add your handling code here:
        exit();
    }//GEN-LAST:event_cancelActionPerformed

    private void okActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okActionPerformed
        // TODO add your handling code here:
        error.setText(null);
        if (!edit) {
            edit = true;
            refresh();
            return;
        }
//    responsableId = fdeptcb.get();
        if (function.equals(DlgProcessing.SHW)) {
        } else if (function.equals(DlgProcessing.DEL)) {
            String msg = rrecord.getRecord().getDeptoId();
            int response = JOptionPane.showConfirmDialog(frame.getFrame(), "¿Eliminar Persona " + msg + "?", "Eliminar", JOptionPane.YES_NO_OPTION);
            //System.out.println("response=" + response);
            if (response != JOptionPane.YES_OPTION) {
                return;
            }
        } else {
            if (!fdeptcb.valid()) {
                return;
            }
            if (!fpuestocb.valid()) {
                return;
            }
            if (function.equals(DlgProcessing.ADD) || function.equals(DlgProcessing.EDT)) {
            }
        }
        if (function.equals(DlgProcessing.ADD)) {
            started = true;
            update();
        } else if (function.equals(DlgProcessing.EDT)) {
            update();
        } else if (function.equals(DlgProcessing.DEL)) {
            update();
        } else {
            ok();
        }
        super.ok();
    }//GEN-LAST:event_okActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancel;
    private javax.swing.JComboBox deptcb;
    private javax.swing.JLabel deptlb;
    private javax.swing.JLabel error;
    private javax.swing.JPanel mainpn;
    private javax.swing.JButton ok;
    private javax.swing.JTextField pers;
    private javax.swing.JLabel perslb;
    private javax.swing.JPanel pn;
    private javax.swing.JComboBox puestocb;
    private javax.swing.JLabel puestolb;
    private javax.swing.JTextField vacs;
    private javax.swing.JLabel vacslb;
    // End of variables declaration//GEN-END:variables
}
