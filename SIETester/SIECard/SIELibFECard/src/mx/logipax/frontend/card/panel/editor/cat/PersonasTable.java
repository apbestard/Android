package mx.logipax.frontend.card.panel.editor.cat;

import javax.swing.ImageIcon;
import mx.logipax.db.interfase.FEMain;
import mx.logipax.shared.DlgProcessing;
import mx.logipax.ide.IDELogipax;
import mx.logipax.ide.display.ModalJPanel;
import mx.logipax.ide.display.ModalParentInterfase;
import mx.logipax.ide.table.EditTableModel;
import mx.logipax.shared.objects.card.cat.CardSucPersona;
import mx.logipax.frontend.card.panel.editor.rec.PersonaRec;
import mx.logipax.frontend.card.panel.editor.rec.PersonasRec;
import org.json.JSONObject;

public class PersonasTable extends EditTableModel {

    private FEMain main;
    private IDELogipax frame;
    private ParentFormInterfase form;
    private String owner;
    private int tabId; 
    private PersonasRec rrecords;
    private final static String TITLE = "Persona";

    public PersonasTable(FEMain main, IDELogipax frame, String name, ParentFormInterfase form, String owner, PersonasRec rrecords, String headers[], int headersJustify[], boolean newrow, int tabId, ImageIcon[] icons) {
        super(frame, name, 0, false, false, true, headers, headersJustify, newrow, icons);
        this.main = main;
        this.frame = frame;
        this.form = form;
        this.owner = owner;
        this.tabId = tabId;
        this.rrecords = rrecords;
        //super.add = frame.imagesRec().edticon;
        start();
    }

    public Object[] getData() {
        Object[] data = rrecords.getRecords();
        if (data != null)
            return(data);
        return new PersonaRec[0];
    }

    public String getDataValueDisplay(Object item, int col) {
        PersonaRec cid = (PersonaRec)item;
        String value = cid.getValue(col);
        if (value == null) {
            value = "";
        }
        return (value);
    }

    public Object getDataValue(int row, int col) {
        PersonaRec cclientes[] = rrecords.getRecords();
        if (cclientes == null || row < 0 || row >= cclientes.length) return null;
        return rrecords.getValue(cclientes[row], col);
    }

    public void setDataValue(int row, int col, Object value) {
        PersonaRec cclientes[] = rrecords.getRecords();
        if (cclientes == null || row < 0 || row >= cclientes.length) return;
        rrecords.setValue(cclientes[row], col, value);
    }

    public ModalJPanel getAddRec(ModalParentInterfase parent, int id) {
        if (tabId > 0) {
            form.tabSelected(tabId);
            return null;
        }
        CardSucPersona record = new CardSucPersona(new JSONObject());
        PersonaRec zrrecord = new PersonaRec(frame, rrecords, null, record);
        return new PersonaForm(main, frame, TITLE+"::"+DlgProcessing.ADD, DlgProcessing.ADD, rrecords, zrrecord, form, owner, true, parent, id);
    }

    public ModalJPanel getUpdateRec(int row, ModalParentInterfase parent, int id) {
        PersonaRec data2[] = rrecords.getRecords();
        if (data2 == null || row < 0 || row >= data2.length) return null;
        return new PersonaForm(main, frame, TITLE+"::"+DlgProcessing.EDT, DlgProcessing.EDT, rrecords, data2[row], form, owner, true, parent, id);
    }

    public ModalJPanel getDeleteRec(int row, ModalParentInterfase parent, int id) {
        PersonaRec data2[] = rrecords.getRecords();
        if (data2 == null || row < 0 || row >= data2.length) return null;
        return new PersonaForm(main, frame, TITLE+"::"+DlgProcessing.DEL, DlgProcessing.DEL, rrecords, data2[row], form, owner, true, parent, id);
    }

    public ModalJPanel getOtherRec(int row, ImageIcon icon, ModalParentInterfase parent, int id) {
        PersonaRec data2[] = rrecords.getRecords();
        if (data2 == null || row < 0 || row >= data2.length) return null;
        return new PersonaForm(main, frame, TITLE+"::"+DlgProcessing.SHW, DlgProcessing.SHW, rrecords, data2[row], form, owner, false, parent, id);
    }
    
    public void moveObject(int row, int row2) {
        rrecords.move(row, row2);
    }

}
