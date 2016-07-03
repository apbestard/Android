/*
Derechos Reservados (c)
Ing. Jorge Guzmán Camarena / Consultoria Integral en Sistemas y Finanzas, S.A. de C.V.
2009, 2010, 2011, 2012, 2013, 2014
logipax Marca Registrada (R)  2003, 2014
*/
package mx.logipax.frontend.card;

import java.awt.Component;
import mx.logipax.db.interfase.FEMain;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import mx.logipax.shared.CatConstants;

public class SIECard extends javax.swing.JPanel {

    final FEMain main;
    final javax.swing.JTabbedPane mainpanel;
    final String name;
    final SIECard thisProcess = this;
    final int mainIndex;
    String process = "";
    String section = "";
    String header = "";
    String parameters = "";
    javax.swing.JButton bts[] = new javax.swing.JButton[0];
    String modules[][] = new String[0][];
    int moduleInx = 0;
    
    public SIECard(FEMain main, javax.swing.JTabbedPane mainpanel, String name) {
        this.main = main;
        this.mainpanel = mainpanel;
        this.name = name;
        initComponents();
        mainIndex = this.main.getTabCount();
        main.addTab(name, this);
        main.setSelectedComponent(this);
        reload();
    }
    
    public static SIECard create(FEMain main, javax.swing.JTabbedPane mainpanel, String name) {
        int last = main.getTabCount();
        for (int i=0;i<last;i++) {
            Component dlg = main.getComponentAt(i);
            if (dlg instanceof  SIECard) {
                SIECard fe = (SIECard)main.getComponentAt(i);
                if ((fe.name.equals(name))) {
                    main.setSelectedComponent((javax.swing.JPanel)dlg);        
                    return (SIECard)dlg;
                }
            }
        }
        return new SIECard(main, mainpanel, name);
    }

    public void reload() {
//        request = new SyncRequest(main, "Actualizando ...", this, XMLBESupport.getServlet(main.getURL()), XMLBESupport.getXML());
        try {
            main.getAsync().enqueueJob(new Runnable() { 
                public void run() {
//                    request.start();        
                }
            });
        } catch (Exception ex) {
        }
    }    
    
    public void refresh() {
        if (bts.length > 0) return;
        optionspn.setBorder(javax.swing.BorderFactory.createTitledBorder(name));
        modules = null;//xmlbe.getModules();
        bts = new javax.swing.JButton[modules.length+1];
        for (int i=0;i<modules.length;i++) {
            bts[i] = new javax.swing.JButton();
            bts[i].setText(modules[i][2]);
            final int inx = i;
            bts[i].addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        actionPerformedCatalogButton(inx, evt);
                    }
            });
        }
        bts[modules.length] = new javax.swing.JButton();
        bts[modules.length].setText("Cerrar");
        bts[modules.length].addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                main.remove(thisProcess);
            }
        });
        setLayout(bts);
    }    
    
    private void setLayout(java.awt.Component[] bts) {
        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(optionspn);
        optionspn.setLayout(panelLayout);
        
        ParallelGroup hpgrppg =  panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        for (int i=0;i<bts.length;i++) {
            hpgrppg = hpgrppg.addComponent(bts[i], javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE);
        }        
////////////        
        SequentialGroup vsgrp = panelLayout.createSequentialGroup();
        for (int i=0;i<bts.length;i++) {
            vsgrp.addComponent(bts[i])
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
        }
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addGroup(hpgrppg)
                .addContainerGap())
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(vsgrp)
        );
    }

//    public void response(byte xml[]) {
//        request = null;
//        for (int i=0;i<bts.length;i++) {
//            bts[i].setEnabled(true);
//        }
//    }
//    public void response(char xml[]) {
//        request = null;
//        xmlbe.parse(xml);
//        refresh();
//        for (int i=0;i<bts.length;i++) {
//            bts[i].setEnabled(true);
//        }
//        String oks[] = xmlbe.getOks();
//        String errors[] = xmlbe.getErrors();
//
//        String listStr[] = new String[oks.length+errors.length+2];
//        listStr[0] = "";
//        System.arraycopy(oks, 0, listStr, 1, oks.length);
//        if (oks.length > 0) {
//            listStr[0] = "OK:";
//        }
//        listStr[oks.length+1] = "";
//        System.arraycopy(errors, 0, listStr, 2+oks.length, errors.length);
//        if (errors.length > 0) {
//            listStr[oks.length+1] = "ERRORS:";
//        }
//        list.setListData(listStr);
//        text.setIcon(null);
//        text.setText(null);
//        
//    }
//
//    public void fail(int error, String message) {
//        request = null;
//        for (int i=0;i<bts.length;i++) {
//            bts[i].setEnabled(true);
//        }
//    }
    
    private void actionPerformedCatalogButton(final int inx, java.awt.event.ActionEvent evt) {
        moduleInx = inx;
        process = modules[inx][0];
        section = modules[inx][1];
        header = modules[inx][2];
        parameters = "";
        if (modules[inx][3].indexOf(CatConstants.OPTNEDITPARAMS) >= 0) {
            mx.logipax.utility.DialogUtility dialog = new mx.logipax.utility.DialogUtility(header, main.getFrame(), params);
            prmfd.setText(parameters);
            dialog.setVisible(true);        
            
        } else {
            processing(process, section, header, null);
        }
    }     
    
    public void processing(String name, String section, String header, String option) {
        String messageText =  "¿Quiere procesar "+header+"\n ";
        if (option != null)
            messageText += "con parámetros:"+option+"?.\n ";
        int n = javax.swing.JOptionPane.showConfirmDialog(
                mx.logipax.utility.DialogUtility.parentDialog(this),
                messageText,
                "Procesar "+header+"", //An Inane
                javax.swing.JOptionPane.YES_NO_OPTION);
        if (n == javax.swing.JOptionPane.YES_OPTION) {
        } else if (n == javax.swing.JOptionPane.NO_OPTION) {
            return;
        } else {
            return;
        }
        for (int i=0;i<bts.length;i++) {
            bts[i].setEnabled(false);
        }
//        request = new SyncRequest(main, "Solicitando soporte "+header+"...", this, 
//                XMLBESupport.getServlet(main.getURL()), 
//                XMLBESupport.getXML(main.getCorporate(), name, section, option));
//        request.start();
        text.setIcon(main.getIconProcessing());
        text.setText("procesando...");
        list.setListData(new String[0]);
        final mx.logipax.utility.DialogUtility dialog = new mx.logipax.utility.DialogUtility("Procesando "+header+"...", main.getFrame(), processing);
        dialog.setVisible(true);
        
    }
    
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        processing = new javax.swing.JPanel();
        closebt = new javax.swing.JButton();
        listpn = new javax.swing.JScrollPane();
        list = new javax.swing.JList();
        text = new javax.swing.JLabel();
        params = new javax.swing.JPanel();
        prmlb = new javax.swing.JLabel();
        prmfd = new javax.swing.JTextField();
        execbt = new javax.swing.JButton();
        canbt = new javax.swing.JButton();
        optionspn = new javax.swing.JPanel();

        processing.setBackground(new java.awt.Color(255, 255, 255));

        closebt.setText("Cerrar");
        closebt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closebtActionPerformed(evt);
            }
        });

        listpn.setViewportView(list);

        text.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        text.setText("...");

        javax.swing.GroupLayout processingLayout = new javax.swing.GroupLayout(processing);
        processing.setLayout(processingLayout);
        processingLayout.setHorizontalGroup(
            processingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, processingLayout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(closebt, javax.swing.GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE)
                .addGap(42, 42, 42))
            .addComponent(listpn, javax.swing.GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE)
            .addGroup(processingLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(text, javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
                .addContainerGap())
        );
        processingLayout.setVerticalGroup(
            processingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, processingLayout.createSequentialGroup()
                .addComponent(text)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addComponent(listpn, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(closebt)
                .addGap(43, 43, 43))
        );

        prmlb.setText("Parámetros:");

        execbt.setText("Procesar");
        execbt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                execbtActionPerformed(evt);
            }
        });

        canbt.setText("Cancelar");
        canbt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                canbtActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout paramsLayout = new javax.swing.GroupLayout(params);
        params.setLayout(paramsLayout);
        paramsLayout.setHorizontalGroup(
            paramsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paramsLayout.createSequentialGroup()
                .addGroup(paramsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(paramsLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(prmlb)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(prmfd, javax.swing.GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE))
                    .addGroup(paramsLayout.createSequentialGroup()
                        .addGap(98, 98, 98)
                        .addComponent(execbt)
                        .addGap(18, 18, 18)
                        .addComponent(canbt)))
                .addContainerGap())
        );
        paramsLayout.setVerticalGroup(
            paramsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paramsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(paramsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(prmlb)
                    .addComponent(prmfd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(paramsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(execbt)
                    .addComponent(canbt))
                .addContainerGap(35, Short.MAX_VALUE))
        );

        setBackground(new java.awt.Color(255, 255, 255));

        optionspn.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout optionspnLayout = new javax.swing.GroupLayout(optionspn);
        optionspn.setLayout(optionspnLayout);
        optionspnLayout.setHorizontalGroup(
            optionspnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 136, Short.MAX_VALUE)
        );
        optionspnLayout.setVerticalGroup(
            optionspnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 90, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(33, Short.MAX_VALUE)
                .addComponent(optionspn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addComponent(optionspn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(40, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void closebtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closebtActionPerformed
// TODO add your handling code here:
        mx.logipax.utility.DialogUtility.exit(processing);

    }//GEN-LAST:event_closebtActionPerformed

private void execbtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_execbtActionPerformed
// TODO add your handling code here:
    parameters = prmfd.getText();
    processing(process, section, header, parameters);
    mx.logipax.utility.DialogUtility.exit(params);

}//GEN-LAST:event_execbtActionPerformed

private void canbtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_canbtActionPerformed
// TODO add your handling code here:
    mx.logipax.utility.DialogUtility.exit(params);
}//GEN-LAST:event_canbtActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton canbt;
    private javax.swing.JButton closebt;
    private javax.swing.JButton execbt;
    private javax.swing.JList list;
    private javax.swing.JScrollPane listpn;
    private javax.swing.JPanel optionspn;
    private javax.swing.JPanel params;
    private javax.swing.JTextField prmfd;
    private javax.swing.JLabel prmlb;
    private javax.swing.JPanel processing;
    private javax.swing.JLabel text;
    // End of variables declaration//GEN-END:variables
    
}
