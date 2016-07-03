/*
 Derechos Reservados (c)
 Ing. Jorge Guzmán Camarena / Consultoria Integral en Sistemas y Finanzas, S.A. de C.V.
 2009, 2010, 2011, 2012, 2013, 2014
 logipax Marca Registrada (R)  2003, 2014
 */
package mx.logipax.frontend.card.panel;

import java.awt.Component;
import java.awt.Graphics;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Vector;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import mx.logipax.db.interfase.FEMain;
import mx.logipax.frontend.card.panel.editor.EditorInterfase;
import mx.logipax.frontend.card.panel.editor.EditorSucursal;
import mx.logipax.frontend.viewer.panel.ViewerInterfase;
import mx.logipax.frontend.viewer.panel.data.ExtendedData;
import mx.logipax.frontend.viewer.panel.tree.ReportTreeLevelD;
import mx.logipax.frontend.viewer.panel.tree.TreeInterfase;
import mx.logipax.ide.IDELogipax;
import mx.logipax.ide.IDEPanels;
import mx.logipax.ide.dialogs.ClickWnd;
import mx.logipax.ide.display.ModelessJDialog;
import mx.logipax.ide.display.ModelessJPanel;
import mx.logipax.ide.resource.ColorHeaderRec;
import mx.logipax.ide.resource.TableHeaderRec;
import mx.logipax.interfase.SIE;
import mx.logipax.shared.DlgProcessing;
import mx.logipax.shared.objects.Login;
import mx.logipax.shared.objects.RepoLevel;
import mx.logipax.shared.objects.card.CardIn;
import mx.logipax.shared.objects.card.Cards;
import mx.logipax.shared.objects.viewer.Report;
import mx.logipax.shared.objects.viewer.ReportIn;
import mx.logipax.utility.IOFile;
import mx.logipax.utility.KeyFileFilter;
import org.json.JSONObject;

public class CardForm extends ModelessJPanel implements IDEPanels, ViewerInterfase {
    
    public static CardForm create(FEMain main, IDELogipax frame, String name, String reportId) {
        int last = main.getTabCount();
        for (int i = 0; i < last; i++) {
            Component dlg = main.getComponentAt(i);
            if (dlg instanceof CardForm) {
                CardForm fe = (CardForm) main.getComponentAt(i);
                if ((fe.name.equals(name))) {
                    main.setSelectedComponent((javax.swing.JPanel) dlg);
                    return (CardForm) dlg;
                }
            }
        }
        CardForm panel = new CardForm(main, frame, name, reportId);
        return panel;
    }
    private IDELogipax frame;
    private IDEPanels panels;
    private FEMain main;
    private CardForm panel;
    private final Login login;
    private final Cards mnucard;
    private int minx = 0;
    private int rinx = 0;
    private int vinx = 0;
    private int pinx = -1;
    private int npnt = -1;
    private int nsel = -1;
    private Cards cards = null;
    private boolean extended = false;
    private String[][] drnodesD1 = null;
    private RepoLevel[] drlevelD1 = null;
    private int ndsel = -1;
    private TreeInterfase treeD1;
    private EditorInterfase ptab;
    private final ExtendedData reportExtended;
    private int counter = 0;
    private Object tabvalues[][] = new Object[0][0];
    private boolean loaded = false;
    private boolean loading = false;
    private int segmentSel = -1;
    private int segmentVarSel = -1;
    private int segmentItemSel = -1;
    private int indicatorSel = -1;
    private ImageIcon checkIcon = null;
    private ImageIcon uncheckIcon = null;
    private int levels = 0;
    private int level = 0;
    private final javax.swing.JSplitPane mainsp = null;
    private final boolean multiselection = false;
    private final boolean rankselection = false;
    
    public CardForm(FEMain main, IDELogipax xframe, String xname, String reportId) {
        super(xframe, xname, false, false, true, false, false);
        this.frame = xframe;
        this.panels = this;
        this.main = main;
        this.panel = this;
        //this.reportRow = new ReportData(main);
        this.reportExtended = new ExtendedData(main);
        this.login = main.getLoginRec();
        mnucard = new Cards(new JSONObject());
        mnucard.card = new JSONObject();
        mnucard.report = new Report(new JSONObject());
        mnucard.report.levelIds = new String[]{""};
        mnucard.report.levelNames = new String[]{
            "ORGANIZACIÓN"};
        initComponents();
        checkIcon = frame.colorRec().checkicon;
        uncheckIcon = frame.colorRec().uncheckicon;
        mlevel1.setIcon(uncheckIcon);
        changedReportOptions();
        mainpn.setBackground(frame.colorRec().backgroundDark);
        mainpn.setOpaque(true);
        //hdrpn.setBackground(frame.colorRec().backgroundDark);
        treepn.setBackground(frame.colorRec().backgroundDark);
        pnlevel1.setBackground(frame.colorRec().backgroundDark);
        level1name.setBackground(frame.colorRec().backgroundDark);
        ptlevel1pn.setBackground(frame.colorRec().backgroundDark);
        level1str.setBackground(frame.colorRec().backgroundDark);
        //datapn.setBackground(frame.colorRec().background);
        dataspn.setBackground(frame.colorRec().background);
        //grppn.setBackground(frame.colorRec().background);
        spubtree.setBackground(frame.colorRec().background);
        table.setBackground(frame.colorRec().background);
        tablepn.setBackground(frame.colorRec().background);
        tabpn.setBackground(frame.colorRec().background);
        treeD1 = new ReportTreeLevelD(frame, this, 0, mlevel1, level1str);
        treeD1.empty();
        levelD1Selected(false);
        //listbt.setEnabled(false);
        listbt.setVisible(true);
        start();
    }

    public void activate() {
        super.activate();
        controls(true);
    }
    
    public void deactivate(boolean canceled) {
        super.deactivate(canceled);
        controls(false);
    }

    public boolean getMenuStatus() {
        return mainsp.getLeftComponent() != null;
    }

    public void setOpenMenu() {
        mainsp.setLeftComponent(main.getMenu());
        mainsp.setDividerLocation(250);
    }

    public void setCloseMenu() {
        mainsp.setLeftComponent(null);
    }

    public boolean isAdmin() {
        return login.user.equals("admin");
    }

    public JSONObject getCacheFile(String process, JSONObject jinfo) {
        return null;
    }

    public void saveCacheFile(String process, JSONObject jinfo, JSONObject jdata) {
    }

    public String loadExtended(String function, mx.logipax.shared.objects.viewer.RepoAgreement agreement) {
        return null;
    }

//    public void actionPerformed(ActionEvent e) {
//        if (e.getActionCommand() == null) {
//            return;
//        }
//        if (e.getActionCommand().equals("Directorio assets")) {
//            //panel.updateAssetsDir();
//        }
//    }
//
//    private JMenuItem makeMenuItem(String label) {
//        JMenuItem item = new JMenuItem(label);
//        item.addActionListener(this);
//        return item;
//    }

    public void refresh() {
        info(mnucard);
    }

    public void exec() {
    }

    private void cache() {
    }

    int waiting = 0;
    private ClickWnd click0 = null;
    private final Object clickLock = new Object();

    private void controls(boolean enabled) {
        //System.out.println("controls:" + enabled);
        mlevel1.setEnabled(enabled);
        sel1.setEnabled(enabled && rankselection);
        //level1str.setEnabled(enabled);
        sel0.setEnabled(enabled && rankselection);
        sel30.setEnabled(enabled && rankselection);
        //listbt.setEnabled(ptab != null && ptab.detailactive(-1, nsel, indicatorSel, segmentSel, segmentItemSel, segmentVarSel, pinx, vinx, npnt, Dates.toInt(reportRow.list().getDate(npnt).getTime()), drlevelD1, drlevelD1));
        //listbt.setVisible(ptab != null && ptab.detailactive(-1, nsel, indicatorSel, segmentSel, segmentItemSel, segmentVarSel, pinx, vinx, npnt, Dates.toInt(reportRow.list().getDate(npnt).getTime()), drlevelD1, drlevelD1));
        if (treeD1 != null) {
            treeD1.setEnabled(enabled);
        }
    }

    public void startWaiting(String message, Runnable job) {
        synchronized (clickLock) {
            if (waiting == 0) {
                controls(false);
            }
            ++waiting;
            main.getAsync().enqueueJob(job);
            frame.waitingPush("Asignaciones...");
        }
    }

    public synchronized void stopWaiting() {
        synchronized (clickLock) {
            if (waiting <= 0) {
                waiting = 1;
                System.err.println("click0 negative");
            }
            --waiting;
            if (waiting > 0) {
                return;
            }
            frame.waitingPop();
            controls(true);
        }
    }

    public int getSourceSize() {
        return 0;
    }

    public void start() {
        main.addTab("." + "ff7f0000" + "." + name, this);
        main.setSelectedComponent(this);
        loaded = true;
        refresh();
    }
    
    public Report getReport() {
        return mnucard.report;
    }

    public void status(Icon icon, String text) {
    }

    public void changeDivide(int divide) {
//        if (cards.report == null || cards.report.views == null || vinx < 0 || vinx >= cards.report.views.length) {
//            return;
//        }
//        if (cards.report.views[vinx].divide == 0) {
//            dataspn.setDividerLocation(0.5);
//            if (divide < 0) {
//                dataspn.setDividerSize(0);
//                dataspn.setDividerLocation(0.0);
//            } else if (divide > 0) {
//                dataspn.setDividerSize(0);
//                dataspn.setDividerLocation(1.0);
//            } else {
//                dataspn.setDividerSize(3);
//                dataspn.setDividerLocation(0.5);
//            }
//            dataspn.invalidate();
//        }
    }

    public void changeDivideData(boolean started) {
//        if (cards.report == null || cards.report.views == null || vinx < 0 || vinx >= cards.report.views.length) {
//            return;
//        }
//        if (started) {
//            if (cards.report.views[vinx].divide < 0) {
//                dataspn.setDividerSize(0);
//                dataspn.setDividerLocation(0.0);
//            } else if (cards.report.views[vinx].divide > 0) {
//                dataspn.setDividerSize(0);
//                dataspn.setDividerLocation(1.0);
//            } else {
//                dataspn.setDividerSize(3);
//                dataspn.setDividerLocation(0.5);
//            }
//        }
//        dataspn.invalidate();
    }

    public void info(final Cards busgets2) {
        this.cards = busgets2;
        if (cards == null) {
            treepn.setVisible(false);
            //hdrpn.setVisible(false);
            status(null, null);
            return;
        }
        cards.report.loaded = false;
        if (cards.report.loaded) {
            reportLoaded(cards.report.id);
            return;
        }
        //hdrpn.setVisible(true);
        spubtree.setOneTouchExpandable(true);
        spubtree.setDividerLocation(cards.report.treeHSize);
        //repotxt.setText("");
        seltxt.setText("");
        level1name.setText(cards.report.levelNames[0]);
        treeD1.clear();
//        java.awt.Component c = dataspn.getLeftComponent();
//        if (c != null) {
//            c.setVisible(false);
//        }
//        c = dataspn.getRightComponent();
//        if (c != null) {
//            c.setVisible(false);
//        }
//        dataspn.setRightComponent(null);
//        dataspn.setLeftComponent(null);
        if (ptab != null) {
            ptab.clear();
        }
        loadCard();
    }

    public void loadCard() {
        status(frame.colorRec().yellowicon, cards.report.desc);
        startWaiting("Cargando presupuesto...", new Runnable() {
            public void run() {
                try {
                    long start = System.currentTimeMillis();
                    CardIn in = new CardIn(new JSONObject());
                    in.ticket = login.ticket;
                    in.setFunction(DlgProcessing.LST);
                    in.cards = new Cards(new JSONObject());
                    in.update();
                    JSONObject jreport = SIE.interfase(frame.getClass(), main.getURL(), main.getDispatcher(), "card", in.json());
                    String error = null;
                    if (jreport.has("exception")) {
                        JSONObject exception = jreport.getJSONObject("exception");
                        error = exception.getString("error");
                    }
                    if (jreport.has("error")) {
                        error = jreport.getString("error");
                    }
                    if (error != null) {
                        status(frame.colorRec().redicon, error);
                        javax.swing.JOptionPane.showMessageDialog(frame.getFrame(), error, "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    cards.reload(jreport);
                    reportLoaded(cards.report.id);
                } catch (Exception ex) {
                    System.err.println("err=" + ex.toString());
                } finally {
                    stopWaiting();
                }
            }
        });
    }

    private void initView() {
        vinx = 0;
        //listbt.setEnabled(ptab != null && ptab.detailactive(-1, nsel, indicatorSel, segmentSel, segmentItemSel, segmentVarSel, pinx, vinx, npnt, Dates.toInt(reportRow.list().getDate(npnt).getTime()), drlevelD1, drlevelD1));
        //listbt.setVisible(ptab != null && ptab.detailactive(-1, nsel, indicatorSel, segmentSel, segmentItemSel, segmentVarSel, pinx, vinx, npnt, Dates.toInt(reportRow.list().getDate(npnt).getTime()), drlevelD1, drlevelD1));
    }

    private boolean uncheck(boolean load) {
        return check(0, load, false);
    }

    private boolean segment(int sel, int varsel) {
        if (segmentSel == sel && varsel == segmentVarSel) {
            return false;
        }
        int chk = (segmentSel != sel) ? 3 : 0;
        segmentSel = sel;
        segmentVarSel = varsel;
        segmentItemSel = ptab.setGroupSegment(sel, varsel);
        check(chk, true, true);
        return true;
    }

    private boolean check(int sel, boolean load, boolean force) {
        if (!force && nsel == sel) {
            sel0.setSelected(nsel == 0);
            sel1.setSelected(nsel == 1);
            sel30.setSelected(!sel0.isSelected() && !sel1.isSelected());
            return false;
        }
        nsel = sel;
        sel0.setSelected(nsel == 0);
        sel1.setSelected(nsel == 1);
        sel30.setSelected(!sel0.isSelected() && !sel1.isSelected());
        //listbt.setEnabled(ptab != null && ptab.detailactive(-1, nsel, indicatorSel, segmentSel, segmentItemSel, segmentVarSel, pinx, vinx, npnt, Dates.toInt(reportRow.list().getDate(npnt).getTime()), drlevelD1, drlevelD1));
        //listbt.setVisible(ptab != null && ptab.detailactive(-1, nsel, indicatorSel, segmentSel, segmentItemSel, segmentVarSel, pinx, vinx, npnt, Dates.toInt(reportRow.list().getDate(npnt).getTime()), drlevelD1, drlevelD1));
        if (treeD1 != null) {
            treeD1.detailed(sel1.isSelected());
        }
        if (load) {
            loadData(force, drnodesD1, drlevelD1, nsel);
        }
        return true;
    }

    private void reportLoaded(String reportId) {
        indicatorSel = -1;
        segmentItemSel = -1;
        segmentSel = -1;
        segmentVarSel = -1;
        if (ptab != null) {
            ptab.dispose();
        }
        if (false || reportId.startsWith("zzcard")) {
            ptab = new EditorSucursal(main, frame, panels, this, this.extended ? reportExtended : null, cards.report);
        } else {
            ptab = new EditorSucursal(main, frame, panels, this, this.extended ? reportExtended : null, cards.report);
        }
        panelInitialize(ptab.getModelessJPanel());
        sel0.setEnabled(ptab.selactive(0) && rankselection);
        sel0.setVisible(ptab.selactive(0) && rankselection);
        sel1.setEnabled(ptab.selactive(1) && rankselection);
        sel1.setVisible(ptab.selactive(1) && rankselection);
        sel30.setEnabled(ptab.selactive(3) && rankselection);
        sel30.setVisible(ptab.selactive(3) && rankselection);
        //listbt.setEnabled(false);
        //listbt.setVisible(false);
        sel0.setEnabled(false);
        sel1.setEnabled(false);
        sel30.setEnabled(false);
        changedReportOptions();
        ptab.reportLoaded(cards.report);
        status(frame.colorRec().greenicon, cards.report.desc);
        pinx = 0;
        initView();
        spubtree.setVisible(true);
        tablepn.setVisible(true);
        treepn.setVisible(true);
        boolean set01 = false;
        set01 = treeD1.set(cards.report, mlevel1.isSelected(), sel1.isSelected(), cards.report.edit);
        mlevel1.setVisible(set01 && multiselection);
        
        level1name.setVisible(set01);
        sel1.setVisible(set01 && rankselection);
        pnlevel1.setVisible(set01);
        spubtree.setOneTouchExpandable(true);
        spubtree.setDividerLocation(cards.report.treeHSize);
        spubtree.setDividerSize(5);
        tabvalues = new Object[0][];
        RepoLevel level0 = cards.report.levels.length <= 0 ? new RepoLevel(new JSONObject()) : cards.report.levels[0];
//        reportRow.set(
//                new String[]{level0.id}, level0,
//                new String[]{level1.id}, level1,
//                cards.report.periods[pinx], null);
        npnt = ptab.reportChanged(cards.report.edit, pinx, vinx, -1, -1, null);
        changeDivideData(true);
        drnodesD1 = null;
        drlevelD1 = null;
        this.nsel = -1;
        check(0, false, false);
        loadData(false, new String[][]{
            new String[]{level0.id}},
                new RepoLevel[]{level0},
                nsel);
        cards.report.loaded = true;
    }

    private RepoLevel[] getLevels() {
        if (cards.report == null || cards.report.levels[1] == null) {
            return new RepoLevel[0];
        }
        RepoLevel[] levels = appendLevels(cards.report.levels[1], new RepoLevel[0]);
        return levels;
    }

    private RepoLevel[] appendLevels(RepoLevel level, RepoLevel[] levels) {
        for (int i = 0; i < level.levels.length; i++) {
            if (level.levels[i].levels.length > 0) {
                levels = appendLevels(level.levels[i], levels);
            } else if (level.levels[i].check == 1) {
                RepoLevel[] levels2 = new RepoLevel[levels.length + 1];
                System.arraycopy(levels, 0, levels2, 0, levels.length);
                levels2[levels.length] = level.levels[i];
                levels = levels2;
            }
        }
        return levels;
    }

    private boolean equal(RepoLevel[] level, RepoLevel[] level2) {
        if (level == null && level2 == null) {
            return true;
        }
        if (level != null && level2 == null) {
            return false;
        }
        if (level == null && level2 != null) {
            return false;
        }
        if (level.length != level2.length) {
            return false;
        }
        for (int i = 0; i < level.length; i++) {
            boolean found = false;
            for (int ii = 0; ii < level2.length; ii++) {
                if (level[i].id.equals(level2[ii].id)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }
        return true;
    }

    public void loadData(final boolean force, String[][] nodesD1, RepoLevel[] levelD1, final int sel) {
        if (cards == null || cards.report == null || levelD1 == null) {
            return;
        }
        if (!force && equal(drlevelD1, levelD1) && sel == ndsel) {
            return;
        }
        boolean load = !equal(drlevelD1, levelD1);
        if (levelD1 != null) {
            drnodesD1 = nodesD1;
            drlevelD1 = levelD1;
            load = true;
        }
        final boolean request = load;
        ndsel = nsel;
        String level = "";
        if (drlevelD1 != null) {
            level += levelNames(drlevelD1);
        } else {
            System.out.println("No levels");
            return;
        }
        seltxt.setText(level);//cards.report.desc+"::"+
        status(frame.colorRec().yellowicon, cards.report.desc + " " + level);
           startWaiting("Cargando datos...", new Runnable() {
            public void run() {
                try {
                    if (request) {
                        long start = System.currentTimeMillis();
                        String extId = Integer.toString(ndsel == 3 ? 0 : nsel);
                        ReportIn in = new ReportIn(new JSONObject());
                        in.ticket = login.ticket;
                        in.id = cards.report.id;
                        in.extid = extId;
                        in.info = false;
                        in.level01No = SIE.listLevels(drlevelD1);
                        in.level01Id = SIE.listIds(drlevelD1);
                        in.update();
                        JSONObject jdata = SIE.interfase(frame.getClass(), main.getURL(), main.getDispatcher(), "carddata", in.json());
                        String error = null;
                        if (jdata.has("exception")) {
                            JSONObject exception = jdata.getJSONObject("exception");
                            error = exception.getString("error");
                        }
                        if (jdata.has("error")) {
                            error = jdata.getString("error");
                        }
                        if (error != null) {
                            status(frame.colorRec().redicon, error);
                            javax.swing.JOptionPane.showMessageDialog(frame.getFrame(), error, "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        cards.reload(jdata);
                        ptab.dataLoaded(cards.card);
                    }
                    dataLoaded();
                    status(frame.colorRec().greenicon, cards.report.desc + " " + drlevelD1[0].name);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    System.err.println(ex.toString());
                } finally {
                    stopWaiting();
                }
            }
        });
    }

    public void dataLoaded() {
//        fontSizeReset();
    }

    public int pointChanged(int pnt, int toppnt, boolean started) {
        if (ptab.createValues()) {
            tabvalues = new Object[][]{};
        }
        return -1;
    }

    public Object getTableCell(int row, int col, Object[][] tabvalues) {
        if (cards.report == null || col >= cards.report.tabcolumns.length || row >= cards.report.tabrows.length || tabvalues == null) {
            return null;
        }
        if (cards.report.tabcolumns[col] == null || cards.report.tabrows[row] == null || row >= tabvalues.length || tabvalues[row] == null) {
            return null;
        }
//        if (cards.report.tabcolumns[col].type.equals(ObjectClass.INTEGER)) {
//            return new Integer(1);
//        } else if (cards.report.tabcolumns[col].type.equals(ObjectClass.REAL)) {
//            return new Double(2);
//        } else 
        if (cards.report.tabcolumns[col].type.equals(mx.logipax.shared.objects.viewer.ObjectClass.DATE)) {
            return new java.util.Date(System.currentTimeMillis());
        }

        if (row < 0 || row >= tabvalues.length) {
            System.err.println("tabvalues row err");
            return null;
        }
        if (col < 0 || col >= tabvalues[row].length) {
            System.err.println("tabvalues col err");
            return null;
        }
        //                 cards.report.tabrows[row].values[col];
        return tabvalues[row][col];
    }

//    void changePoint2(boolean scroll) {
//        
//        Object obj = periodpnt.getValue();
//        if (obj instanceof Integer) {
//            int value = ((Integer) periodpnt.getValue()).intValue();
//            if (value < 0) {
//                periodpnt.setValue(new Integer(0));
//            } else {
//                npnt = pointChanged(value, false, scroll);
//            }
//        }
//    }
//    public void changePeriod(int inx) {
//        if (inx == pinx || inx < 0 || inx >= cards.report.periods.length) {
//            return;
//        }
//        pinx = inx;
//        vinx = 0;
//        npnt = ptab.periodChanged(cards.report.edit, pinx, vinx, 0, tabvalues);
//        if (npnt < 0 && cards.report.loaded) {
//            if (cards.report.loaded) {
//                loadData(false, drnodesD1, drlevelD1, nsel);
//                //listbt.setEnabled(ptab != null && ptab.detailactive(-1, nsel, indicatorSel, segmentSel, segmentItemSel, segmentVarSel, pinx, vinx, npnt, Dates.toInt(reportRow.list().getDate(npnt).getTime()), drlevelD1, drlevelD1));
//                //listbt.setVisible(ptab != null && ptab.detailactive(-1, nsel, indicatorSel, segmentSel, segmentItemSel, segmentVarSel, pinx, vinx, npnt, Dates.toInt(reportRow.list().getDate(npnt).getTime()), drlevelD1, drlevelD1));
//            } else {
//                initView();
//            }
//        } else {
//            initView();
//        }
//    }
//
//    public void changeView(int inx) {
//        if (inx == vinx || inx < 0) {
//            return;
//        }
//        vinx = inx;
//        npnt = ptab.viewChanged(vinx, ptab.selectedPnt(), ptab.topPnt(), tabvalues);
//        changeDivideData(true);
//        if (npnt < 0) {
//            if (cards.report.loaded) {
//                loadData(false, drnodesD1, drlevelD1, nsel);
//            } else {
//                javax.swing.JOptionPane.showMessageDialog(frame.getFrame(), "No cargó la información correctamente.\nreintente nuevamente la opción.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
//            }
//        }
//        //listbt.setEnabled(ptab != null && ptab.detailactive(-1, nsel, indicatorSel, segmentSel, segmentItemSel, segmentVarSel, pinx, vinx, npnt, Dates.toInt(reportRow.list().getDate(npnt).getTime()), drlevelD1, drlevelD1));
//        //listbt.setVisible(ptab != null && ptab.detailactive(-1, nsel, indicatorSel, segmentSel, segmentItemSel, segmentVarSel, pinx, vinx, npnt, Dates.toInt(reportRow.list().getDate(npnt).getTime()), drlevelD1, drlevelD1));
//    }

    private void multiLevel1(boolean selected) {
        mlevel1.setIcon(selected ? checkIcon : uncheckIcon);
        if (treeD1 != null) {
            treeD1.change(selected);
        }
    }

    public void mouseTreeD1Selected() {
//        ptab.mouseTitle(-1);
//        ptab.mouseCell(-1, -1);
    }

    private void levelD1Selected(boolean selected) {
    }

    public void changeTreeD1SelectionNone() {
        drlevelD1 = null;
        levelD1Selected(false);
    }

    private static String levelNames(RepoLevel[] levels) {
        String name = "";
        for (int i = 0; i < levels.length; i++) {
            if (name.length() > 0) {
                name += ", ";
            }
            name += levels[i].name;
        }
        return name;
    }

    public void changeTreeD1Selection(int[] ainx, RepoLevel[] level, String[][] nodes) {
        //repotxt.setText(cards.report.desc);
        seltxt.setText(levelNames(level));
        if (cards.report.loaded) {
            loadData(false, nodes, level, nsel);
        } else {
            javax.swing.JOptionPane.showMessageDialog(frame.getFrame(), "No cargó la información correctamente.\nreintente nuevamente la opción.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
        levelD1Selected(true);
    }

    public void mouseTreeDSelected(int inx) {
        if (ptab == null) {
            return;
        }
//        ptab.mouseTitle(-1);
//        ptab.mouseCell(-1, -1);
    }

    public void changeTreeDSelectionNone(int inx) {
        if (inx == 0) {
            changeTreeD1SelectionNone();
        }
    }

    public void changeTreeDExecute(int inx, int[] ainx, RepoLevel[] level, String[][] nodes, String option) {
    }
    public void changeTreeDSelection(int inx, int[] ainx, RepoLevel[] level, String[][] nodes) {
        if (inx == 0) {
            changeTreeD1Selection(ainx, level, nodes);
        }
    }

    public void changePeriodSelection(mx.logipax.shared.objects.viewer.RepoPeriod period) {
        if (cards.report.loaded) {
            loadData(false, drnodesD1, drlevelD1, nsel);
        } else {
            javax.swing.JOptionPane.showMessageDialog(frame.getFrame(), "No cargó la información correctamente.\nreintente nuevamente la opción.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean sort() {
        if (cards == null || cards.report == null) {
            return false;
        }
        return cards.report.ranking();
    }

    public int counter() {
        return counter;
    }

    public void counterPlus() {
        ++counter;
    }

    public boolean graphics() {
        if (cards == null || cards.report == null) {
            return false;
        }
        return cards.report.graphics(vinx);
    }

    public void plus() {
//        int inx = graphLabels.getPointSize();
//        if (inx == 0) {
//            return;
//        }
//        graphLabels.setPointSize(inx - 1);
    }

    public void minus() {
//        int inx = graphLabels.getPointSize();
//        graphLabels.setPointSize(inx + 1);
    }

    public void menu() {
//        if (main.getMenuStatus()) {
//            main.setCloseMenu();
//        } else {
//            main.setOpenMenu();
//        }
    }

    public void detailSelected(java.awt.Component c, int x, int y, int index) {
//        detail(c, x, y, index);
    }

    public void periodChanged(int index) {
    }

    public void viewChanged(int index) {
    }

    public void dataChanged(Object data) {
        if (data instanceof Integer[]) {
            Integer[] values = (Integer[]) data;
            if (values.length >= 3) {
                int v0inx = ((Integer) values[0]).intValue();
                int n0data = ((Integer) values[1]).intValue();
                int n0real = ((Integer) values[2]).intValue();
                if (nsel == 0) {
                    indicatorSel = n0data;
                }
                if (nsel == 3) {
                    segmentItemSel = n0data;
                }
            }
        }
        ptab.dataChanged(data);
    }

    boolean center = true;

    public int setOffset(int pnt) {
        return 0;
    }

    public int getOffset() {
        return 0;
    }

    private boolean listenrange = true;

    public int rangeOffset() {
        return 0;
    }

    public void pointChanged(int pnt, boolean center) {
//        npnt = pointChanged(pnt, center ? -1 : ptab.topPnt(), false);
    }

    public java.io.File saveFile(String ext, String name, String title, javax.swing.JFrame frame, byte[] data) {
        String currentDir = System.getProperty("user.dir.choose");
        if (currentDir == null) {
            currentDir = System.getProperty("user.dir");
        }
        javax.swing.JFileChooser chooser = new javax.swing.JFileChooser(currentDir);
        KeyFileFilter filter = new KeyFileFilter();
        //filter.setDescription(name);
        if (ext != null) {
            filter.addExtension(ext);
            chooser.setFileFilter(filter);
        }
        chooser.setDialogTitle(title);
        chooser.setName(name);
        try {
            String path = name;
            if (currentDir != null && currentDir.length() > 0) {
                if (!path.endsWith("/") && !path.endsWith("\\")) {
                    path += "/";
                }
                path += name;
            }
            java.io.File f = new java.io.File(new java.io.File(path).getCanonicalPath());
            chooser.setSelectedFile(f);
        } catch (IOException ex3) {
        }
        int returnVal = chooser.showSaveDialog(frame);
        if (returnVal == javax.swing.JFileChooser.APPROVE_OPTION) {
            currentDir = chooser.getSelectedFile().getParent();
            System.setProperty("user.dir.choose", currentDir);
            String path = chooser.getSelectedFile().getAbsolutePath();
            String name2 = chooser.getSelectedFile().getName();
            if (name != null) {
                if (!name2.equals(name)) {
                    //return null;
                }
            }
            int inx = name.indexOf(".");
            if (inx < 0 && ext != null) {
                name2 += "." + ext;
                path = chooser.getSelectedFile().getParent() + "/" + name2;
            }
            if (data != null) {
                IOFile.create(data, new java.io.File(path));
            }
            return (chooser.getSelectedFile());
        }
        return (null);
    }

    private void changedReportOptions() {
//        if (ptab == null) {
//            pdfbt.setVisible(false);
//            xlsbt.setVisible(false);
//            return;
//        }
//        final JPanel panel[] = ptab.getPanels();
//        final String title = ptab.title();
//        pdfbt.setVisible(panel != null);
//        xlsbt.setVisible(//title != null && 
//                (login.profile.equals(DBDefaults.PROFILEADMINISTRADOR)));
//        // || login.profile.equals(DBDefaults.PROFILEDIRECTOR)));
    }
//
//    private void fontSizeReset() {
//        if (ptab == null) {
//            levels = 0;
//            level = 0;
//        } else {
//            levels = ptab.fontsSize();
//            level = ptab.level();
//        }
//        fontupbt.setVisible(levels > 0);
//        fontdownbt.setVisible(levels > 0);
//        if (levels > 0) {
//            if (level < 0) {
//                level = 0;
//            }
//            if (level >= levels) {
//                level = levels - 1;
//            }
//            fontupbt.setEnabled(level < levels - 1);
//            fontdownbt.setEnabled(level > 0);
//        }
//    }
//
//    private void fontSizeUp() {
//        if (ptab == null) {
//            return;
//        }
//        if (levels > 0 && level < levels - 1) {
//            ++level;
//            ptab.fontSize(level);
//        }
//        fontupbt.setEnabled(level < levels - 1);
//        fontdownbt.setEnabled(level > 0);
//    }
//
//    private void fontSizeDown() {
//        if (levels > 0 && level > 0) {
//            --level;
//            ptab.fontSize(level);
//        }
//        fontupbt.setEnabled(level < levels - 1);
//        fontdownbt.setEnabled(level > 0);
//    }
//
//    private void detail(java.awt.Component c, int x, int y, int index) {
//        if (ptab != null) {
//            int[][] datesinx = ptab.getSelectedDates();
//            ptab.detail(-1, c, x, y, index, nsel, indicatorSel, segmentSel, segmentItemSel, segmentVarSel, pinx, vinx, npnt,
//                    datesinx,
//                    "", "",
//                    drlevelD1, drlevelD1);
//        }
//    }
//
//    private void pdf() {
//        if (ptab == null) {
//            return;
//        }
//        String title = ptab.title();
//        if (title == null || title.length() == 0) {
//            return;
//        }
//        char[] chtitle = title.toCharArray();
//        for (int i = 0; i < chtitle.length; i++) {
//            if (chtitle[i] == ' '
//                    || chtitle[i] == '/'
//                    || chtitle[i] == '\\'
//                    || chtitle[i] == ':'
//                    || chtitle[i] == '.'
//                    || chtitle[i] == '?') {
//                chtitle[i] = '_';
//            }
//        }
//        final String fname = String.copyValueOf(chtitle);
//
//        String[][] sels = new String[3][cards.report.views.length];
//        for (int i = 0; i < sels.length - 1; i++) {
//            for (int ii = 0; ii < sels[i].length; ii++) {
//                sels[i][ii] = cards.report.views[ii].name;
//            }
//        }
//        boolean labelMark = ptab.markGroups();
//
//        String label = "Indicadores";
//        String[] labels = ptab.getGroups();
//        if (labels == null) {
//            label = "";
//            labels = new String[0];
//        }
//        sels[2] = new String[labels == null ? 0 : labels.length];
//        for (int i = 0; i < sels[2].length; i++) {
//            sels[2][i] = "true";
//        }
//
//        boolean table = false;
//        if ((dataspn.getDividerLocation() > 10 || dataspn.getDividerSize() > 0)) {
//            table = true;
//        }
//        boolean graph = false;
//        if (dataspn.getRightComponent() instanceof JPanel && (dataspn.getDividerLocation() <= 10 || dataspn.getDividerSize() > 0)) {
//            graph = true;
//        }
//
//        final boolean[][] result = new PDFDlg(frame).display("PDF", new boolean[]{table, graph}, 0, sels, label, labels, labelMark);
//        if (result == null) {
//            return;
//        }
//        final JPanel panel0[] = ptab.getPanels();
//            javax.swing.JOptionPane.showMessageDialog(frame.getFrame(), "No tiene opción de exportación", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
//            return;
////        if (rinfo != null) {
////            rinfo.moduleInx = (Integer) modscb.getSelectedItem();
////            rinfo.reportInx = (Integer) repscb.getSelectedItem();
////            rinfo.periodInx = (Integer) periodcb.getSelectedItem();
////            //rinfo.viewsInx = new int[]{(Integer) viewcb.getSelectedItem()};
////            rinfo.module = rinfo.moduleInx < 0 ? "" : modules[rinfo.moduleInx].name;
////            rinfo.report = rinfo.reportInx < 0 ? "" : modules[rinfo.moduleInx].reports[rinfo.reportInx].name;
////            rinfo.period = rinfo.periodInx < 0 ? "" : report.periods[rinfo.periodInx].name;
////            //rinfo.views = new String[]{(Integer) viewcb.getSelectedItem() < 0 ? "" : report.views[(Integer) viewcb.getSelectedItem()].name};
////            rinfo.title = repotxt.getText();
////            rinfo.subtitle = seltxt.getText();
////            rinfo.user = login.getName();
////
////        }
////
////        startWaiting("Creando PDF...", new Runnable() {
////            public void run() {
////                String error = null;
////                try {
////                    long start = System.currentTimeMillis();
////                    java.awt.Dimension d = hdrpn.getSize();
////                    byte[] data = null;
////                    if (rinfo == null) {
////                        javax.swing.JPanel[] panels = new javax.swing.JPanel[panel0.length + 1];
////                        panels[0] = hdrpn;
////                        System.arraycopy(panel0, 0, panels, 1, panel0.length);
////                        data = null;//PDFExporter.exportPanel(d.width + 100, panels);
////                    } else {
////                        data = new PDFExporter(frame, rinfo).exportPanel(PDFExporter.getWidth(), PDFExporter.getHeight());
////                    }
////                    if (data != null) {
////                        final byte[] data2 = data;
////                        SwingUtilities.invokeLater(new Runnable() {
////                            public void run() {
////                                new PDFDialog(main.getFrame(), data2, fname, repotxt.getText(), seltxt.getText()).display("PDF");
////                            }
////                        });
////                    }
////                } catch (Exception ex) {
////                    error = ex.toString();
////                    System.err.println(ex.toString());
////                } finally {
////                    stopWaiting();
////                }
////            }
////        });
//    }
//
//    private void xls() {
//        if (ptab == null) {
//            return;
//        }
//        String title = ptab.title();
//        if (title == null || title.length() == 0) {
//            return;
//        }
//        char[] chtitle = title.toCharArray();
//        for (int i = 0; i < chtitle.length; i++) {
//            if (chtitle[i] == ' '
//                    || chtitle[i] == '/'
//                    || chtitle[i] == '\\'
//                    || chtitle[i] == ':'
//                    || chtitle[i] == '.'
//                    || chtitle[i] == '?') {
//                chtitle[i] = '_';
//            }
//        }
//        title = String.copyValueOf(chtitle) + ".xls";
//        final java.io.File file = saveFile("xls", title, "Reporte", frame.getFrame(), null);
//        if (file == null) {
//            return;
//        }
//        startWaiting("Creando Excel...", new Runnable() {
//            public void run() {
//                String error = null;
//                try {
//                    long start = System.currentTimeMillis();
//                    //new ExcelExporter().exportTable(ptab, file);
//                } catch (Exception ex) {
//                    error = ex.toString();
//                    System.err.println(ex.toString());
//                } finally {
//                    stopWaiting();
//                }
//                if (error != null) {
//                    javax.swing.JOptionPane.showMessageDialog(frame.getFrame(), error, "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
//                }
//            }
//        });
//    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tablepn = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        colorpn = new javax.swing.JPanel();
        collb = new javax.swing.JLabel();
        ccircle = new javax.swing.JLabel();
        crectangle = new javax.swing.JLabel();
        colordlg = new javax.swing.JColorChooser();
        jComboBox1 = new javax.swing.JComboBox();
        mainpn = new javax.swing.JPanel();
        spubtree = new javax.swing.JSplitPane();
        treepn = new javax.swing.JPanel();
        pnlevel1 = new javax.swing.JPanel();
        mlevel1 = new javax.swing.JCheckBox();
        level1name = new javax.swing.JLabel();
        sel1 = new javax.swing.JRadioButton();
        ptlevel1pn = new javax.swing.JScrollPane();
        level1str = new javax.swing.JTree();
        tabpn = new javax.swing.JPanel();
        dataspn = new javax.swing.JPanel();
        sel0 = new javax.swing.JRadioButton();
        sel30 = new javax.swing.JRadioButton();
        listbt = new javax.swing.JButton();
        seltxt = new javax.swing.JLabel();

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        table.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tablepn.setViewportView(table);

        collb.setText("Colores usados.");

        ccircle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/colors/circle.png"))); // NOI18N

        crectangle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/colors/rectangle.png"))); // NOI18N

        org.jdesktop.layout.GroupLayout colorpnLayout = new org.jdesktop.layout.GroupLayout(colorpn);
        colorpn.setLayout(colorpnLayout);
        colorpnLayout.setHorizontalGroup(
            colorpnLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(colorpnLayout.createSequentialGroup()
                .add(colorpnLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(colorpnLayout.createSequentialGroup()
                        .add(35, 35, 35)
                        .add(collb)
                        .add(0, 0, Short.MAX_VALUE))
                    .add(colorpnLayout.createSequentialGroup()
                        .addContainerGap()
                        .add(ccircle, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 376, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(crectangle)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 20, Short.MAX_VALUE)
                        .add(colordlg, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 694, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        colorpnLayout.setVerticalGroup(
            colorpnLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(colorpnLayout.createSequentialGroup()
                .addContainerGap()
                .add(collb)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(colorpnLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(colorpnLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                        .add(crectangle, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(ccircle, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .add(colordlg, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 376, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(70, 70, 70))
        );

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        setBackground(new java.awt.Color(255, 255, 255));
        setAlignmentX(0.1F);
        setAlignmentY(0.1F);

        mainpn.setBackground(new java.awt.Color(255, 255, 255));

        spubtree.setBackground(new java.awt.Color(255, 255, 255));
        spubtree.setDividerLocation(100);

        treepn.setBackground(new java.awt.Color(255, 255, 255));

        pnlevel1.setBackground(new java.awt.Color(255, 255, 255));

        mlevel1.setBackground(new java.awt.Color(255, 255, 255));
        mlevel1.setText("+");
        mlevel1.setToolTipText("Multiselección");
        mlevel1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mlevel1ActionPerformed(evt);
            }
        });

        level1name.setBackground(new java.awt.Color(255, 255, 255));
        level1name.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        level1name.setText("ESTRUCTURA");
        level1name.setMaximumSize(new java.awt.Dimension(254, 16));

        sel1.setBackground(new java.awt.Color(255, 255, 255));
        sel1.setToolTipText("Selección de Organización");
        sel1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sel1ActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout pnlevel1Layout = new org.jdesktop.layout.GroupLayout(pnlevel1);
        pnlevel1.setLayout(pnlevel1Layout);
        pnlevel1Layout.setHorizontalGroup(
            pnlevel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(pnlevel1Layout.createSequentialGroup()
                .add(0, 0, 0)
                .add(mlevel1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(level1name, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(0, 0, 0)
                .add(sel1)
                .add(0, 0, 0))
        );
        pnlevel1Layout.setVerticalGroup(
            pnlevel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, pnlevel1Layout.createSequentialGroup()
                .add(0, 0, 0)
                .add(pnlevel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(mlevel1)
                    .add(level1name, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(sel1)))
        );

        ptlevel1pn.setPreferredSize(new java.awt.Dimension(45, 384));
        ptlevel1pn.setViewportView(level1str);

        org.jdesktop.layout.GroupLayout treepnLayout = new org.jdesktop.layout.GroupLayout(treepn);
        treepn.setLayout(treepnLayout);
        treepnLayout.setHorizontalGroup(
            treepnLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(ptlevel1pn, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, pnlevel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        treepnLayout.setVerticalGroup(
            treepnLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, treepnLayout.createSequentialGroup()
                .add(pnlevel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(ptlevel1pn, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE))
        );

        spubtree.setLeftComponent(treepn);

        tabpn.setBackground(new java.awt.Color(255, 255, 255));

        org.jdesktop.layout.GroupLayout dataspnLayout = new org.jdesktop.layout.GroupLayout(dataspn);
        dataspn.setLayout(dataspnLayout);
        dataspnLayout.setHorizontalGroup(
            dataspnLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 0, Short.MAX_VALUE)
        );
        dataspnLayout.setVerticalGroup(
            dataspnLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 253, Short.MAX_VALUE)
        );

        sel0.setBackground(new java.awt.Color(255, 255, 255));
        sel0.setToolTipText("Selección de Indicadores");
        sel0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sel0ActionPerformed(evt);
            }
        });

        sel30.setBackground(new java.awt.Color(255, 255, 255));
        sel30.setToolTipText("Selección por Segmento");
        sel30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sel30ActionPerformed(evt);
            }
        });

        listbt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons/list.png"))); // NOI18N
        listbt.setBorder(null);
        listbt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listbtActionPerformed(evt);
            }
        });

        seltxt.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        seltxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        seltxt.setText("LEVEL");
        seltxt.setPreferredSize(new java.awt.Dimension(39, 26));

        org.jdesktop.layout.GroupLayout tabpnLayout = new org.jdesktop.layout.GroupLayout(tabpn);
        tabpn.setLayout(tabpnLayout);
        tabpnLayout.setHorizontalGroup(
            tabpnLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(dataspn, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .add(tabpnLayout.createSequentialGroup()
                .add(sel0)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(sel30)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(listbt)
                .add(0, 0, 0)
                .add(seltxt, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE))
        );
        tabpnLayout.setVerticalGroup(
            tabpnLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(tabpnLayout.createSequentialGroup()
                .addContainerGap()
                .add(tabpnLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(sel0)
                    .add(sel30)
                    .add(listbt)
                    .add(seltxt, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(dataspn, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabpnLayout.linkSize(new java.awt.Component[] {listbt, sel0, sel30, seltxt}, org.jdesktop.layout.GroupLayout.VERTICAL);

        spubtree.setRightComponent(tabpn);

        org.jdesktop.layout.GroupLayout mainpnLayout = new org.jdesktop.layout.GroupLayout(mainpn);
        mainpn.setLayout(mainpnLayout);
        mainpnLayout.setHorizontalGroup(
            mainpnLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(mainpnLayout.createSequentialGroup()
                .add(0, 0, 0)
                .add(spubtree)
                .add(0, 0, 0))
        );
        mainpnLayout.setVerticalGroup(
            mainpnLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, mainpnLayout.createSequentialGroup()
                .add(0, 0, 0)
                .add(spubtree)
                .add(0, 0, 0))
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(0, 0, 0)
                .add(mainpn, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(0, 0, 0)
                .add(mainpn, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void mlevel1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mlevel1ActionPerformed
        // TODO add your handling code here:
        multiLevel1(mlevel1.isSelected());
    }//GEN-LAST:event_mlevel1ActionPerformed

    private void sel1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sel1ActionPerformed
        // TODO add your handling code here:
        check(1, true, false);
    }//GEN-LAST:event_sel1ActionPerformed

    private void sel0ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sel0ActionPerformed
        // TODO add your handling code here:
        check(0, true, false);

    }//GEN-LAST:event_sel0ActionPerformed

    private void sel30ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sel30ActionPerformed
        // TODO add your handling code here:
        check(3, true, false);
    }//GEN-LAST:event_sel30ActionPerformed

    private void listbtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listbtActionPerformed
        // TODO add your handling code here:
//        detail(tabpn, 0, 0, -1);
    }//GEN-LAST:event_listbtActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ccircle;
    private javax.swing.JLabel collb;
    private javax.swing.JColorChooser colordlg;
    private javax.swing.JPanel colorpn;
    private javax.swing.JLabel crectangle;
    private javax.swing.JPanel dataspn;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel level1name;
    private javax.swing.JTree level1str;
    private javax.swing.JButton listbt;
    private javax.swing.JPanel mainpn;
    private javax.swing.JCheckBox mlevel1;
    private javax.swing.JPanel pnlevel1;
    private javax.swing.JScrollPane ptlevel1pn;
    private javax.swing.JRadioButton sel0;
    private javax.swing.JRadioButton sel1;
    private javax.swing.JRadioButton sel30;
    private javax.swing.JLabel seltxt;
    private javax.swing.JSplitPane spubtree;
    private javax.swing.JTable table;
    private javax.swing.JScrollPane tablepn;
    private javax.swing.JPanel tabpn;
    private javax.swing.JPanel treepn;
    // End of variables declaration//GEN-END:variables

    protected class DialogPanel {

        String id;
        java.awt.Container panel;
        java.awt.Container panelProcess;
        javax.swing.JLabel status;
        javax.swing.JLabel status2;
        javax.swing.JButton chat[];
        javax.swing.JLabel clock;
        javax.swing.JLabel error;

        DialogPanel(String id,
                java.awt.Container panel,
                java.awt.Container panelProcess,
                javax.swing.JLabel status,
                javax.swing.JLabel status2,
                javax.swing.JButton chat[],
                javax.swing.JLabel clock,
                javax.swing.JLabel error) {
            this.id = id;
            this.panel = panel;
            this.panelProcess = panelProcess;
            this.status = status;
            this.status2 = status2;
            this.chat = chat;
            this.clock = clock;
            this.error = error;
            if (error != null) {
                error.setVisible(false);
            }
//            for (int i = 0; i < chat.length; i++) {
//                if (chat[i] != null) {
//                    chat[i].setVisible(false);
//                }
//            }

            if (chat != null) {
                for (int i = 0; i < chat.length; i++) {
                    if (chat[i] != null) {
                        final int inx = i;
                        chat[i].addActionListener(new java.awt.event.ActionListener() {
                            public void actionPerformed(java.awt.event.ActionEvent evt) {
//1                            supportpn[inx].setVisibleDlg(true, true);
                            }
                        });
                    }
                }
            }
        }
    }
    
    protected class DialogPanel0 {

        String id;
        java.awt.Container panel;
 
        DialogPanel0(String id,
                java.awt.Container panel) {
            this.id = id;
            this.panel = panel;
        }
    }
    
    class MainPanel extends ModelessJDialog {

        public MainPanel(IDEPanels frame, ModelessJPanel modelesspanel) {
            super(frame, modelesspanel); 
        }
    }
    private DialogPanel dpanels[];
    public void panelInitialize(ModelessJPanel modelesspanel) {
        MainPanel mainPanel = new MainPanel(this, modelesspanel);
        modelesspanel.dialog(mainPanel);
        dpanels = new DialogPanel[]{new DialogPanel("SIEWnd", modelesspanel, mainPanel, mainPanel.statusLabel(), mainPanel.status2Label(), mainPanel.chatButton(), mainPanel.clockLabel(), mainPanel.errorLabel())};
//        dpanels = new DialogPanel[]{new DialogPanel("main", dataspn2)};
        change(dataspn, dpanels[dpanels.length - 1].panel);
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
        dpanels2[dpanels.length] = new DialogPanel(id, panel, process, status, status2, chat, clock, error);
        dpanels = dpanels2;
        dpanels[dpanels.length - 1].panel.setVisible(true);
        change(dataspn, dpanels[dpanels.length - 1].panel);
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
        change(dataspn, dpanels[dpanels.length - 1].panel);
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
        add(dpanels[dpanels.length - 1].panel, 0);
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
    
    private void change(javax.swing.JTabbedPane panel, java.awt.Container owner) {
        //panel.removeAll();
        //panel.add(owner);
    }
    private void change(javax.swing.JPanel panel, java.awt.Container owner) {
        //panel.removeAll();
        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
                panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(owner, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        panelLayout.setVerticalGroup(
                panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(owner, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        panel.setLayout(panelLayout);
    }

}
