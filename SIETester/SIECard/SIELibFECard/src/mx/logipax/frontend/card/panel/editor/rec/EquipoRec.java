package mx.logipax.frontend.card.panel.editor.rec;

import mx.logipax.ide.SHRObjects;
import mx.logipax.shared.objects.card.cat.CardSucEquipo;

public class EquipoRec {

    private SHRObjects frame;
    private EquiposRec parent;
    private CardSucEquipo record;

    public EquipoRec(SHRObjects frame, EquiposRec parent, String recordId, CardSucEquipo record) {
        this.frame = frame;
        this.parent = parent;
        this.record = record;
    }

    public String getValue(int col) {
        switch (col) {
            case 0:
                return (record.getDeptoId()+"-"+record.getDeptoNombre());
            case 1:
                return (record.getEquipoId()+"-"+record.getEquipoNombre());
            case 2:
                return (Integer.toString(record.getEquipos()));
        }
        return ("?");
    }

    public void setValue(int col, Object value) {
    }

    public CardSucEquipo getRecord() {
        return record;
    }

    protected void updateData(CardSucEquipo record) {
        this.record = record;
    }

    public static void delete(SHRObjects frame, EquipoRec p) {
        p.parent.deleteRecord(p);
    }
}
