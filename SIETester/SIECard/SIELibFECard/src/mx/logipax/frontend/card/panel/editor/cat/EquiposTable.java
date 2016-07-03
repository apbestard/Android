package mx.logipax.frontend.card.panel.editor.cat;

import javax.swing.ImageIcon;
import mx.logipax.db.interfase.FEMain;
import mx.logipax.shared.DlgProcessing;
import mx.logipax.ide.IDELogipax;
import mx.logipax.ide.display.ModalJPanel;
import mx.logipax.ide.display.ModalParentInterfase;
import mx.logipax.ide.table.EditTableModel;
import mx.logipax.shared.objects.card.cat.CardSucEquipo;
import mx.logipax.frontend.card.panel.editor.rec.EquipoRec;
import mx.logipax.frontend.card.panel.editor.rec.EquiposRec;
import org.json.JSONObject;

public class EquiposTable extends EditTableModel {

    private FEMain main;
    private IDELogipax frame;
    private ParentFormInterfase form;
    private String owner;
    private EquiposRec rrecords;
    private int tabId; 
    private final static String TITLE = "Equipo";

    public EquiposTable(FEMain main, IDELogipax frame, String name, ParentFormInterfase form, String owner, EquiposRec rrecords, String headers[], int headersJustify[], boolean newrow, int tabId, ImageIcon[] icons) {
        super(frame, name, 0, false, false, true, headers, headersJustify, newrow, icons);
        this.main = main;
        this.frame = frame;
        this.form = form;
        this.owner = owner;
        this.tabId = tabId;
        this.rrecords = rrecords;
        start();
    }

    public Object[] getData() {
        Object[] data = rrecords.getRecords();
        if (data != null)
            return(data);
        return new EquipoRec[0];
    }

    public String getDataValueDisplay(Object item, int col) {
        EquipoRec cid = (EquipoRec)item;
        String value = cid.getValue(col);
        if (value == null) {
            value = "";
        }
        return (value);
    }

    public Object getDataValue(int row, int col) {
        EquipoRec cclientes[] = rrecords.getRecords();
        if (cclientes == null || row < 0 || row >= cclientes.length) return null;
        return rrecords.getValue(cclientes[row], col);
    }

    public void setDataValue(int row, int col, Object value) {
        EquipoRec cclientes[] = rrecords.getRecords();
        if (cclientes == null || row < 0 || row >= cclientes.length) return;
        rrecords.setValue(cclientes[row], col, value);
    }

    public ModalJPanel getAddRec(ModalParentInterfase parent, int id) {
        if (tabId > 0) {
            form.tabSelected(tabId);
            return null;
        } 
        CardSucEquipo record = new CardSucEquipo(new JSONObject());
        EquipoRec zrrecord = new EquipoRec(frame, rrecords, null, record);
        return new EquipoForm(main, frame, TITLE+"::"+DlgProcessing.ADD, DlgProcessing.ADD, rrecords, zrrecord, form, owner, true, parent, id);
    }

    public ModalJPanel getUpdateRec(int row, ModalParentInterfase parent, int id) {
        EquipoRec data2[] = rrecords.getRecords();
        if (data2 == null || row < 0 || row >= data2.length) return null;
        return new EquipoForm(main, frame, TITLE+"::"+DlgProcessing.EDT, DlgProcessing.EDT, rrecords, data2[row], form, owner, true, parent, id);
    }

    public ModalJPanel getDeleteRec(int row, ModalParentInterfase parent, int id) {
        EquipoRec data2[] = rrecords.getRecords();
        if (data2 == null || row < 0 || row >= data2.length) return null;
        return new EquipoForm(main, frame, TITLE+"::"+DlgProcessing.DEL, DlgProcessing.DEL, rrecords, data2[row], form, owner, true, parent, id);
    }

    public ModalJPanel getOtherRec(int row, ImageIcon icon, ModalParentInterfase parent, int id) {
        EquipoRec data2[] = rrecords.getRecords();
        if (data2 == null || row < 0 || row >= data2.length) return null;
        return new EquipoForm(main, frame, TITLE+"::"+DlgProcessing.SHW, DlgProcessing.SHW, rrecords, data2[row], form, owner, false, parent, id);
    }
    
    public void moveObject(int row, int row2) {
        rrecords.move(row, row2);
    }

}
