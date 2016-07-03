package mx.logipax.frontend.card.panel.editor;
import javax.swing.JPanel;
import mx.logipax.frontend.viewer.panel.ReportInfo;
import mx.logipax.frontend.viewer.panel.SelectorPanel;
import mx.logipax.ide.display.ModelessJPanel;
import mx.logipax.shared.objects.RepoLevel;
import mx.logipax.shared.objects.viewer.Report;
import org.json.JSONObject;

public interface EditorInterfase {
    
    public ModelessJPanel getModelessJPanel();
    public void dispose();
    public int setGroupSegment(int inx, int vinx);
//    public boolean detailactive(int bt, int nsel, int indicator, int segSel, int segInx, int segVar, int pinx, int vinx, int pnt, int dateinx, final RepoLevel[] drlevelD1, final RepoLevel[] drlevelD2);
//
//    public void detail(int bt, java.awt.Component c, int x, int y, int index, int nsel, int indicator, int segSel, int segInx, int segVar, int pinx, int vinx, int pnt, 
//            int[][] datesinx, 
//            String periodStr, String viewStr,             
//            final RepoLevel[] drlevelD1, final RepoLevel[] drlevelD2);
//    
//    public boolean external(int n);
//    public String[] externalOptions(int n);
//    public JSONObject[] externalReports(String option, JSONObject report);
//    public int[] columnTotls();
    public boolean selactive(int nsel);
//    
    public void clear();
//
    public void reportLoaded(Report report);   
// 
//    public SelectorPanel[] selectors();
//    
    public void dataLoaded(JSONObject list);    
//    
    public boolean createValues();
//    
    public int reportChanged(boolean editable, int pinx, int vinx, int pnt, int toppnt, Object[][] tabvalues);
//
//    public int periodChanged(boolean editable, int pinx, int vinx, int pnt, Object[][] tabvalues);
//
//    public int viewChanged(int vinx, int pnt, int toppnt, Object[][] tabvalues);
//
//    public int pointChanged(int pnt, int toppnt, boolean started, Object[][] tabvalues);
//    
    public void dataChanged(Object data);
//    
//    public int[][] getSelectedDates();
//    
//    public int selectedPnt();
//    public int topPnt();
//    public int selectedDateInx();
//    public int topDateInx();
//
//    public int toOffset(int pnt);
//    
//    public int fromOffset(int pnt);
//        
//    public void mouseTitle(int ainx);
//
//    public void mouseCell(int ainx, int pinx);
//    
//    public int level();
//    public int fontsSize();
//    public void fontSize(int level);
//    
//    public boolean markGroups();
//    public String[] getGroups();
//    public JPanel[] getPanels();
//    public ReportInfo getReportInfo(boolean[] btabs, int[] tabs, boolean[] bdatas, boolean[] bgraphs, int[] graphs);
//    public String title();
//    
//    public String[][] columns();
//    public Object[][] rows();
    
}
