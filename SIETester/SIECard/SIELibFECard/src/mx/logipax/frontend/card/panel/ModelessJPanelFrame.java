/*
Derechos Reservados (c)
Ing. Jorge Guzmán Camarena / Consultoria Integral en Sistemas y Finanzas, S.A. de C.V.
2009, 2010, 2011, 2012, 2013, 2014
logipax Marca Registrada (R)  2003, 2014
*/
package mx.logipax.frontend.card.panel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import mx.logipax.ide.IDEPanels;
import mx.logipax.ide.display.ModelessJPanel;
import mx.logipax.ide.resource.ColorHeaderRec;
import mx.logipax.ide.resource.TableHeaderRec;
import mx.logipax.ide.utility.EditablePanelInterfase;

public class ModelessJPanelFrame extends ModelessJPanel implements IDEPanels {
    public ModelessJPanelFrame(IDEPanels frame, String name, javax.swing.JPanel mainpn2) {
        this(frame, name,true, false);
    }
    public ModelessJPanelFrame(IDEPanels frame, String name, boolean showChanged, boolean askExit) {
        super(frame, name, showChanged, askExit);
        //this.frame = frame;
    }
    public ModelessJPanelFrame(IDEPanels frame, String name, boolean showChanged, boolean askExit, boolean qback, boolean qok, boolean qerror) {
        super(frame, name, showChanged, askExit, qback, qok, qerror);
        //this.frame = frame;
    }
    
    protected class DialogPanel {

        String id;
        java.awt.Container panel;
 
        DialogPanel(String id,
                java.awt.Container panel) {
            this.id = id;
            this.panel = panel;
        }
    }
    //private final IDEPanels frame;
    private javax.swing.JPanel mainpn;
    private DialogPanel dpanels[];

    
    public void panel(javax.swing.JPanel mainpn2) {
        this.mainpn = mainpn2;
        dpanels = new DialogPanel[]{new DialogPanel("main", mainpn)};
        change(mainpn, dpanels[dpanels.length - 1].panel);
    }

    @Override
    public JFrame getFrame() {
        return frame.getFrame();
    }

    @Override
    public Icon iconRefresh() {
        return frame.iconRefresh();
    }

    @Override
    public Icon iconWaiting() {
        return frame.iconWaiting();
    }

    @Override
    public Icon iconClick() {
        return frame.iconClick();
    }

    @Override
    public Icon iconAlt() {
        return frame.iconAlt();
    }

    @Override
    public ImageIcon buttonBack() {
        return frame.buttonBack();
    }

    @Override
    public ImageIcon buttonOk() {
        return frame.buttonOk();
    }

    @Override
    public ImageIcon buttonCancel() {
        return frame.buttonCancel();
    }

    @Override
    public void paintComponent(Graphics g, Component c, int alpha, boolean ads) {
        frame.paintComponent(g, c, alpha, ads);
    }

    @Override
    public TableHeaderRec tableHeader() {
        return frame.tableHeader();
    }
    
    @Override
    public int panels() {
        return (dpanels.length);
    }

    @Override
    public java.awt.Container currentPanel() {
        return (dpanels[dpanels.length - 1].panel);
    }

    @Override
    public void push(String id, java.awt.Container panel, java.awt.Container process,
            javax.swing.JLabel status, javax.swing.JLabel status2,
            javax.swing.JButton chat[],
            javax.swing.JLabel clock,
            javax.swing.JLabel error) {
        if (dpanels.length == 1) {
            deactivate(false);
        }
        if (id == null) {
            id = "";
        }
        dpanels[dpanels.length - 1].panel.setVisible(false);
        DialogPanel dpanels2[] = new DialogPanel[dpanels.length + 1];
        System.arraycopy(dpanels, 0, dpanels2, 0, dpanels.length);
        dpanels2[dpanels.length] = new DialogPanel(id, panel);
        dpanels = dpanels2;
        dpanels[dpanels.length - 1].panel.setVisible(true);
        change(mainpn, dpanels[dpanels.length - 1].panel);
        dpanels[dpanels.length - 1].panel.repaint();
    }

    @Override
    public java.awt.Container back() {
        if (dpanels.length < 1) {
            return null;
        }
        if (dpanels.length <= 1) {
            return dpanels[dpanels.length - 1].panel;
        }
        dpanels[dpanels.length - 1].panel.setVisible(false);
        DialogPanel dpanels2[] = new DialogPanel[dpanels.length - 1];
        System.arraycopy(dpanels, 0, dpanels2, 0, dpanels.length - 1);
        dpanels = dpanels2;
        dpanels[dpanels.length - 1].panel.setVisible(true);
        change(mainpn, dpanels[dpanels.length - 1].panel);
        dpanels[dpanels.length - 1].panel.repaint();
        java.awt.Container panel = dpanels[dpanels.length - 1].panel;
        if (panel instanceof ModelessJPanel) {
            //((ModelessJPanel) panel).refresh();
        }
        if (dpanels.length == 1) {
            activate();
        }
        return dpanels[dpanels.length - 1].panel;
    }

    @Override
    public void reload(javax.swing.JPanel panel) {
        dpanels[dpanels.length - 1].panel.setVisible(false);
        dpanels[dpanels.length - 1].panel = panel;
        dpanels[dpanels.length - 1].panel.setVisible(true);
        add(dpanels[dpanels.length - 1].panel, 0);//StackLayout.TOP);
    }
    @Override
    public void setFrameEnabled(Vector<Component> data, boolean enabled) {
        frame.setFrameEnabled(data, enabled);
    }

    @Override
    public String statusPush(int icon, String txt) {
        return frame.statusPush(icon, txt);
    }

    @Override
    public void statusUpdate(String id, int icon, String txt) {
        frame.statusUpdate(id, icon, txt);
    }

    @Override
    public void statusPop(String id) {
        frame.statusPop(id);
    }

    @Override
    public String status2Push(int icon, String txt) {
        return frame.status2Push(icon, txt);
    }

    @Override
    public void status2Update(String id, int icon, String txt) {
        frame.status2Update(id, icon, txt);
    }

    @Override
    public void status2Pop(String id) {
        frame.status2Pop(id);
    }

    @Override
    public void statusError(String txt) {
        frame.statusError(txt);
    }

    @Override
    public ColorHeaderRec colorRec() {
        return frame.colorRec();
    }

    @Override
    public NumberFormat getIntegerFormatter(boolean print) {
        return frame.getIntegerFormatter(print);
    }

    @Override
    public NumberFormat getQuantityFormatter(boolean print) {
        return frame.getQuantityFormatter(print);
    }

    @Override
    public NumberFormat getPriceFormatter(boolean print) {
        return frame.getPriceFormatter(print);
    }

    @Override
    public NumberFormat getCurrencyFormatter(boolean print) {
        return frame.getCurrencyFormatter(print);
    }

    @Override
    public NumberFormat getPercentFormatter(boolean print) {
        return frame.getPercentFormatter(print);
    }

    @Override
    public String getTicket() {
        return frame.getTicket();
    }

    @Override
    public String getUserId() {
        return frame.getUserId();
    }

    @Override
    public String getPasswordMD5() {
        return frame.getPasswordMD5();
    }

    @Override
    public String getUserName() {
        return frame.getUserName();
    }

    @Override
    public String getUserEMail() {
        return frame.getUserEMail();
    }

    @Override
    public String getUserProfile() {
        return frame.getUserProfile();
    }

    @Override
    public String getUserTasks() {
        return frame.getUserTasks();
    }

    @Override
    public String getUserRoles() {
        return frame.getUserRoles();
    }

    @Override
    public String getUserParent() {
        return frame.getUserParent();
    }

    @Override
    public String getHTTPSync() {
        return frame.getHTTPSync();
    }

    @Override
    public String getHTTPService() {
        return frame.getHTTPService();
    }

    @Override
    public String getHTTPMailer() {
        return frame.getHTTPMailer();
    }

    @Override
    public void enterModule(String id) {
        frame.enterModule(id);
    }

    @Override
    public void exitModule(String id) {
        frame.exitModule(id);
    }

    @Override
    public void reportErrorFatal(String txt) {
        frame.reportErrorFatal(txt);
    }

    @Override
    public void out(String txt) {
        frame.out(txt);
    }

    @Override
    public void err(String txt) {
        frame.err(txt);
    }

    @Override
    public long currentTimeMillis() {
        return frame.currentTimeMillis();
    }

    @Override
    public void reportError(String txt) {
        frame.reportError(txt);
    }
    
    
    private void change(java.awt.Panel panel, javax.swing.JPanel owner) {
        //panel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
                panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(owner, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        panelLayout.setVerticalGroup(
                panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(owner, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        panel.setLayout(panelLayout);
    }

    private void change(javax.swing.JPanel panel, java.awt.Container owner) {
        //panel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
                panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(owner, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        panelLayout.setVerticalGroup(
                panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(owner, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        panel.setLayout(panelLayout);
    }
//
//    private void change2(javax.swing.JPanel panel, javax.swing.JPanel owner) {
//        Dimension xsize = frame.getSize();
//        double w = xsize.getWidth();
//        double h = xsize.getHeight();
//        if (w < 1024) {
//            w = 1024;
//        }
//        if (h < 720) {
//            h = 720;
//        }
//        panel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
//        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
//        panel.setLayout(panelLayout);
//        panelLayout.setHorizontalGroup(
//                panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(owner, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
//        panelLayout.setVerticalGroup(
//                panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(owner, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
//        panel.setLayout(panelLayout);
//
//        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
//        getContentPane().setLayout(layout);
//        layout.setHorizontalGroup(
//                layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(panel, 1024, (int) w, Short.MAX_VALUE));
//        layout.setVerticalGroup(
//                layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(panel, 720, (int) h, Short.MAX_VALUE));
//        frame.addContainerListener(new java.awt.event.ContainerAdapter() {
//            public void componentResized(ContainerEvent evt) {
//                Container c = (Container) evt.getSource();
//                Dimension xsize = c.getSize();
//                double w = xsize.getWidth();
//                double h = xsize.getHeight();
//                if (w < 1024) {
//                    w = 1024;
//                }
//                if (h < 720) {
//                    h = 720;
//                }
//                if (w != xsize.getWidth() || h != xsize.getHeight()) {
//                    frame.setSize((int) w, (int) h);
//                    out("size x:" + xsize.getWidth() + " y:" + xsize.getHeight());
//                }
//            }
//        });
//
//        pack();
//    }
//
//    public void change(java.awt.Panel mainpn) {
//        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
//        getContentPane().setLayout(layout);
//        layout.setHorizontalGroup(
//                layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(mainpn, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
//        layout.setVerticalGroup(
//                layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(mainpn, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
//        pack();
//    }
//
//    private void change3(java.awt.Panel panel) {
//        Container cp = frame.getContentPane();
//        cp.setLayout(new BorderLayout());
//        cp.add(BorderLayout.CENTER, panel);
//        pack();
//    }
    
}
