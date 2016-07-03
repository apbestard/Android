package mx.logipax.frontend.card.panel.editor.cat;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import mx.logipax.db.interfase.FEMain;
import mx.logipax.frontend.card.panel.CatalogUtility;
import mx.logipax.frontend.card.panel.editor.rec.EquiposRec;
import mx.logipax.frontend.card.panel.editor.rec.PersonasRec;
import mx.logipax.shared.DlgProcessing;
import mx.logipax.ide.IDELogipax;
import mx.logipax.ide.display.ModelessJPanel;
import mx.logipax.ide.display.PanelUtility;
import mx.logipax.ide.panels.DialogModelessJPanel;
import mx.logipax.ide.utility.EditCombo;
import mx.logipax.ide.utility.Editable;
import mx.logipax.ide.utility.EditableDate;
import mx.logipax.interfase.SIE;
import mx.logipax.shared.objects.card.cat.CardSucIn;
import mx.logipax.shared.objects.card.cat.CardSuc;
import mx.logipax.shared.objects.card.cat.CardSucs;
import mx.logipax.shared.objects.cat.Tabla;
import org.json.JSONObject;

public class CardPersonalFrom extends ModelessJPanel implements ParentFormInterfase {

    private FEMain main;
    private IDELogipax frame;
    private CardPersonalFrom panel;
    private String function;
    private CardSuc rrecordini;
    private FormInterfase form;
    private int cardLevel;
    private String cardId;
    private CardSuc rrecord = null;
    private boolean started = false;
    private CardSuc inidata = null;
    private String creador = "";
    private boolean edit = false;
    private boolean displayed = false;
//    private EditCombo fresponsable;
//    private EditCombo fvisor;
//    private EditCombo fformato;
//    private EditCombo fmismas;
//    private EditableDate fapertura;
//    private Editable femail;
    private String id = "";
    private PersonasTable t01;
    private PersonasRec r01;
    private EquiposTable t02;
    private EquiposRec r02;
    
    public CardPersonalFrom(FEMain main, IDELogipax frame, String name, String function, CardSuc rrecord2, FormInterfase form, String creador, boolean edit, int cardLevel, String cardId) {
        super(frame, name, true, false, true, false, false);
        this.main = main;
        this.frame = frame;
        this.panel = this;
        this.rrecordini = rrecord2;
        this.form = form;
        this.function = function;
        this.creador = creador;
        this.edit = edit;
        this.cardLevel = cardLevel;
        this.cardId = cardId;
        this.id = "ID";
        initComponents();
        setBackground(frame.colorRec().backgroundDark);
        pn.setBackground(frame.colorRec().backgroundDark);
        boolean addrow = true;
        ImageIcon[] rfcicons = new ImageIcon[]{frame.imagesRec().shwicon};
        if (true) {
            rfcicons = new ImageIcon[]{frame.imagesRec().shwicon, frame.imagesRec().edticon, frame.imagesRec().delicon};
        }
        r01 = new PersonasRec(frame);
        t01 = new PersonasTable(main, frame, name, this, id, r01, r01.getHeaders(), r01.getHeadersJustify(), addrow, 0, rfcicons);
        PanelUtility.set(tabpn, t01);
        create();
    }

    private void create() {
//        fresponsable = new EditCombo(frame, this, "Responsable", respcb, null, new String[]{}, "Seleccione el responsable", null, error);
//        fvisor = new EditCombo(frame, this, "Visor", visorcb, null, new String[]{}, "Seleccione el usuario Visor", null, error);
//        fformato = new EditCombo(frame, this, "Formato", formcb, null, new String[]{}, "Seleccione el formato", null, error);
//        fmismas = new EditCombo(frame, this, "Mismas", apercb, null, new String[]{}, "Seleccione ela clasificación de mismas", null, error);
//        femail = new Editable(frame, this, "Correo Electrónico", mail, "inicio", "Ingrese el correo electrónico", null, error);
//        fapertura = new EditableDate(frame, this, "Fecha de Apertura", aper, new java.util.Date().getTime(), "Ingrese la fecha de apertura", null, error);
//        main.getAsync().enqueueJob(new Runnable() {
//            public void run() {
//                CatalogUtility.loadTabla(frame, main.getURL(), main.getDispatcher(), main.getTicket(), true, "responsable", "", "", fresponsable, error);
//                CatalogUtility.loadTabla(frame, main.getURL(), main.getDispatcher(), main.getTicket(), true, "uservisor", "", "", fvisor, error);
//                CatalogUtility.loadTabla(frame, main.getURL(), main.getDispatcher(), main.getTicket(), false, "formato", "", "", fformato, error);
//                CatalogUtility.loadTabla(frame, main.getURL(), main.getDispatcher(), main.getTicket(), false, "mismas", "", "", fmismas, error);
//                frame.waitingPop();
//            }
//        });
//        frame.waitingPush("Inicializando...");
    }
    
    public void load(JSONObject jresult) {
        form.load(jresult);
    }
    
    public void tabSelected(int id) {
    }
    
    String responsableIdEx = "";

    private void responsable(final String responsableId) {
        if (responsableIdEx.equals(responsableId)) {
            return;
        }
        main.getAsync().enqueueJob(new Runnable() {
            public void run() {
                Tabla tabla = CatalogUtility.loadTabla(frame, main.getURL(), main.getDispatcher(), main.getTicket(), false, "responsables", responsableId, "");
                if (tabla == null) {
                    return;
                }
                String[] ids = tabla.getIds();
            }
        });
    }

    private void get(final String cardId) {
        error.setText(null);
        final CardSuc rrecord = new CardSuc(new JSONObject());
        rrecord.setCardId(cardId);
        rrecord.update();
        main.getAsync().enqueueJob(new Runnable() {
            public void run() {
                CardSucIn in = new CardSucIn(new JSONObject());
                in.setTicket(main.getTicket());
                in.setFunction(DlgProcessing.SHW);
                in.update(rrecord.json());
                //                   
                JSONObject jresult = SIE.interfase(frame.getClass(), main.getURL(), main.getDispatcher(), "rrecord", in.json());
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
                if (!loadCardSuc(rrecordini)) {
                    JOptionPane.showMessageDialog(frame.getFrame(), "No pudo cargar rrecords", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                started = true;
                if (!edit && cardId != null) {
                    panel.pushThis(new DialogModelessJPanel(frame, panel));
                }

                refresh();
            }
        });
        frame.waitingPush(cardId + "...");
    }

    private void update() {
        final CardSuc rrecord = new CardSuc(new JSONObject());
        rrecord.setCardLevel(cardLevel);
        rrecord.setCardId(cardId);
//        rrecord.setCardNombre("NOMBRE");
//        rrecord.setResponsableId(fresponsable.getId());
//        rrecord.setResponsableNombre(CatalogUtility.getName(fresponsable.get()));
//        rrecord.setVisorId(fvisor.getId());
//        rrecord.setVisorNombre(CatalogUtility.getName(fvisor.get()));
//        rrecord.setFormatoId(fformato.getId());
//        rrecord.setMismaId(fmismas.getId());
//        rrecord.setFecha(new java.util.Date(fapertura.get()));
//        rrecord.setEMail(femail.get());
//        rrecord.setEstatus("ACTIVO");
        rrecord.update();
        main.getAsync().enqueueJob(new Runnable() {
            public void run() {
                CardSucIn in = new CardSucIn(new JSONObject());
                in.setTicket(main.getTicket());
                in.setFunction(function+"|main");
                in.update(rrecord.json());
                JSONObject jresult = new JSONObject();
                if (function.equals(DlgProcessing.EDT) || function.equals(DlgProcessing.ADD) || function.equals(DlgProcessing.DEL)) {
                    jresult = SIE.interfase(frame.getClass(), main.getURL(), main.getDispatcher(), "cardsuc", in.json());
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
                if (!loadCardSuc(rrecordini)) {
                    JOptionPane.showMessageDialog(frame.getFrame(), "No pudo cargar registro", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                started = true;
                if (!edit && cardId != null) {
                    panel.pushThis(new DialogModelessJPanel(frame, panel));
                }
                refresh();
            }
        });
        frame.waitingPush(rrecordini.getCardId().toLowerCase() + "...");
    }

    public void activate() {
        super.activate();
        setOkDefaultButton(ok);
        error.setText(null);
        if (function.equals(DlgProcessing.EDT)) {
            setFirstComponent(tabpn);
        } else if (function.equals(DlgProcessing.ADD)) {
            setFirstComponent(tabpn);
        }
        if (edit && cardId != null) {
            get(cardId);
        } else {
            refresh();
        }
    }

    private boolean loadCardSuc(CardSuc data) {
        String id = null;
        if (data.getCardId() != null && data.getCardId().length() > 0) {
            id = data.getCardId();
            inidata = data;
        }
        if (inidata == null) {
            return false;
        }
        rrecord = inidata;
        return rrecord != null;
    }

    private void refresh() {
        if (rrecord != null) {
//            card.setText(rrecord.getCardId());
//            fresponsable.set(rrecord.getResponsableId());
//            fvisor.set(rrecord.getVisorId());
//            fformato.set(rrecord.getFormatoId());
//            fmismas.set(rrecord.getMismaId());
        } else
        if (inidata != null) {
//            fresponsable.set(inidata.getCardId());
//            fapertura.set(inidata.getFecha().getTime());
        }
//        fresponsable.set();
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
            //femail.filter(4, 64, Editable.ANY);
            //fapertura.filter(1, 100, Editable.ANY);
        }
        if (!noedit && function.equals(DlgProcessing.ADD)) {
        }
//        fapertura.setEditable(!noedit);
//        fresponsable.setEditable(!noedit);
        if (function.equals(DlgProcessing.EDT) || function.equals(DlgProcessing.ADD3) || function.equals(DlgProcessing.DEL) || function.equals(DlgProcessing.SHW)) {
        }
        if (function.equals(DlgProcessing.DEL)) {
            //cardfd.setEditable(false);
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
        idpn = new javax.swing.JPanel();
        fpn = new javax.swing.JPanel();
        ok = new javax.swing.JButton();
        cancel = new javax.swing.JButton();
        tabpn = new javax.swing.JPanel();

        setBackground(new java.awt.Color(255, 255, 255));
        setOpaque(false);

        mainpn.setBackground(new java.awt.Color(255, 255, 255));
        mainpn.setOpaque(false);

        error.setForeground(new java.awt.Color(192, 0, 0));
        error.setText(" ");

        pn.setBackground(new java.awt.Color(255, 255, 255));
        pn.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        idpn.setBackground(new java.awt.Color(255, 255, 255));
        idpn.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        idpn.setToolTipText("");
        idpn.setOpaque(false);

        fpn.setBackground(new java.awt.Color(255, 255, 255));

        ok.setText("Actualizar");
        ok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okActionPerformed(evt);
            }
        });

        cancel.setText("Cancelar");
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout fpnLayout = new org.jdesktop.layout.GroupLayout(fpn);
        fpn.setLayout(fpnLayout);
        fpnLayout.setHorizontalGroup(
            fpnLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(fpnLayout.createSequentialGroup()
                .addContainerGap()
                .add(fpnLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(ok, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
                    .add(cancel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        fpnLayout.setVerticalGroup(
            fpnLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, fpnLayout.createSequentialGroup()
                .addContainerGap()
                .add(ok, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 37, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(cancel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 37, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(210, Short.MAX_VALUE))
        );

        tabpn.setBackground(new java.awt.Color(255, 255, 255));
        tabpn.setBorder(javax.swing.BorderFactory.createTitledBorder("Personal"));
        tabpn.setToolTipText("");
        tabpn.setOpaque(false);

        org.jdesktop.layout.GroupLayout tabpnLayout = new org.jdesktop.layout.GroupLayout(tabpn);
        tabpn.setLayout(tabpnLayout);
        tabpnLayout.setHorizontalGroup(
            tabpnLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 498, Short.MAX_VALUE)
        );
        tabpnLayout.setVerticalGroup(
            tabpnLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 0, Short.MAX_VALUE)
        );

        org.jdesktop.layout.GroupLayout idpnLayout = new org.jdesktop.layout.GroupLayout(idpn);
        idpn.setLayout(idpnLayout);
        idpnLayout.setHorizontalGroup(
            idpnLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(idpnLayout.createSequentialGroup()
                .add(tabpn, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(fpn, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );
        idpnLayout.setVerticalGroup(
            idpnLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, fpn, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .add(idpnLayout.createSequentialGroup()
                .addContainerGap()
                .add(tabpn, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        org.jdesktop.layout.GroupLayout pnLayout = new org.jdesktop.layout.GroupLayout(pn);
        pn.setLayout(pnLayout);
        pnLayout.setHorizontalGroup(
            pnLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(pnLayout.createSequentialGroup()
                .add(idpn, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnLayout.setVerticalGroup(
            pnLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(pnLayout.createSequentialGroup()
                .addContainerGap()
                .add(idpn, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout mainpnLayout = new org.jdesktop.layout.GroupLayout(mainpn);
        mainpn.setLayout(mainpnLayout);
        mainpnLayout.setHorizontalGroup(
            mainpnLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(error, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, pn, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        mainpnLayout.setVerticalGroup(
            mainpnLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(mainpnLayout.createSequentialGroup()
                .add(error)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(pn, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 679, Short.MAX_VALUE)
            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(layout.createSequentialGroup()
                    .add(0, 0, 0)
                    .add(mainpn, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(0, 0, 0)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 344, Short.MAX_VALUE)
            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(layout.createSequentialGroup()
                    .add(0, 0, 0)
                    .add(mainpn, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(0, 0, 0)))
        );
    }// </editor-fold>//GEN-END:initComponents

private void okActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okActionPerformed
// TODO add your handling code here:
    error.setText(null);
    if (!edit) {
        edit = true;
        refresh();
        return;
    }
    if (function.equals(DlgProcessing.SHW)) {
    } else if (function.equals(DlgProcessing.DEL)) {
        String msg = rrecord.getCardId();
        int response = JOptionPane.showConfirmDialog(frame.getFrame(), "¿Eliminar registro " + msg + "?", "Eliminar", JOptionPane.YES_NO_OPTION);
        //System.out.println("response=" + response);
        if (response != JOptionPane.YES_OPTION) {
            return;
        }
    } else {
        if (function.equals(DlgProcessing.ADD) || function.equals(DlgProcessing.EDT)) {
//            if (!fresponsable.valid()) {
//                //return;
//            }
//            if (!fvisor.valid()) {
//                //return;
//            }
//            if (!fformato.valid()) {
//                return;
//            }
//            if (!fmismas.valid()) {
//                return;
//            }
//            if (!fapertura.valid()) {
//                return;
//            }
//            if (!femail.valid()) {
//                return;
//            }
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
}//GEN-LAST:event_okActionPerformed

    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelActionPerformed
        // TODO add your handling code here:
        exit();
    }//GEN-LAST:event_cancelActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancel;
    private javax.swing.JLabel error;
    private javax.swing.JPanel fpn;
    private javax.swing.JPanel idpn;
    private javax.swing.JPanel mainpn;
    private javax.swing.JButton ok;
    private javax.swing.JPanel pn;
    private javax.swing.JPanel tabpn;
    // End of variables declaration//GEN-END:variables

}
