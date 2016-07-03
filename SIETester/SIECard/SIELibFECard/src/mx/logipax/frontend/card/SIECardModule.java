/*
Derechos Reservados (c)
Ing. Jorge Guzmán Camarena / Consultoria Integral en Sistemas y Finanzas, S.A. de C.V.
2009, 2010, 2011, 2012, 2013, 2014
logipax Marca Registrada (R)  2003, 2014
*/
package mx.logipax.frontend.card;

import mx.logipax.shared.io.ProfileMenuItem;
import mx.logipax.ide.profile.CatalogObject;
import mx.logipax.ide.profile.FEModule;
import mx.logipax.db.interfase.FEMain;
import mx.logipax.ide.IDEAdmin;
import mx.logipax.ide.IDELogipax;
import mx.logipax.ide.dialogs.ClickWnd;
import mx.logipax.frontend.card.panel.CardForm;
import mx.logipax.ide.display.ModelessJPanel;

public class SIECardModule implements FEModule {

    final FEMain main;

    public SIECardModule(FEMain main) {
        this.main = main;
    }

    public CatalogObject getCatalogModule(int level, String id, javax.swing.JTabbedPane panel, ProfileMenuItem item, ProfileMenuItem[] modules, CatalogObject parent) {
        return (null);
    }

    public boolean append(javax.swing.JMenu submenumn, ProfileMenuItem item, ProfileMenuItem[] modules) {
        if (item.id.equals("CARD")) {
            javax.swing.JMenuItem itemmn = new javax.swing.JMenuItem();
            itemmn.setText(item.name);
            submenumn.add(itemmn);
            Visor(itemmn, item);
        }
        else if (item.id.equals("CARD 2")) {
            javax.swing.JMenuItem itemmn = new javax.swing.JMenuItem();
            itemmn.setText(item.name);
            submenumn.add(itemmn);
            Visor2(itemmn, item);
        }
        else {
            return (false);
        }
        return (true);
    }

    public void Visor(javax.swing.JMenuItem itemmn, ProfileMenuItem item) {
        final String name = item.name;
        itemmn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IDELogipax frame = (IDELogipax) main.getInterfase();
                frame.enterModule("CARD");
                String name2 = name+" "+main.getUserId()+"-"+main.getUserName()+" ("+main.getLoginRec().profile+" "+main.getLoginRec().level+" "+main.getLoginRec().levelvalue+")";
                ModelessJPanel panel = CardForm.create(main, frame, name2, "CEX");
            }
        });
    }
        public void Visor2(javax.swing.JMenuItem itemmn, ProfileMenuItem item) {
        final String name = item.name;
        itemmn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IDELogipax frame = (IDELogipax) main.getInterfase();
                frame.enterModule("CARD");
                String name2 = name+" "+main.getUserId()+"-"+main.getUserName()+" ("+main.getLoginRec().profile+" "+main.getLoginRec().level+" "+main.getLoginRec().levelvalue+")";
                ModelessJPanel panel = CardForm.create(main, frame, name2, "CEX");
            }
        });
    }
    private ClickWnd click = null;

    private void push(final IDEAdmin frame, final java.awt.Container panel) {
        if (click != null || panel == null) {
            return;
        }
        click = new ClickWnd(frame, "Procesando..", new Runnable() {
            public void run() {
                try {
                    frame.push("Visor:"+main.getUserId()+" "+main.getUserName(), panel, null, null, null, null, null, null);
                } catch (Exception ex) {
                } finally {
                    if (click != null) {
                        click.stop();
                    }
                    click = null;
                }
            }
        });
        if (click != null) {
            click.start();
        }
    }
}
