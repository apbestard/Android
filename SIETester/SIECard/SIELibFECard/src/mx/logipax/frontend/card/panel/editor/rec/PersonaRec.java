package mx.logipax.frontend.card.panel.editor.rec;

import mx.logipax.ide.SHRObjects;
import mx.logipax.shared.objects.card.cat.CardSucPersona;

public class PersonaRec {

    private SHRObjects frame;
    private PersonasRec parent;
    private CardSucPersona record;

    public PersonaRec(SHRObjects frame, PersonasRec parent, String recordId, CardSucPersona record) {
        this.frame = frame;
        this.parent = parent;
        this.record = record;
    }

    public String getValue(int col) {
        switch (col) {
            case 0:
                return (record.getDeptoId()+"-"+record.getDeptoNombre());
            case 1:
                return (record.getPuestoId()+"-"+record.getPuestoNombre());
            case 2:
                return (Integer.toString(record.getPersonas()));
            case 3:
                return (Integer.toString(record.getVacantes()));
        }
        return ("?");
    }

    public void setValue(int col, Object value) {
    }

    public CardSucPersona getRecord() {
        return record;
    }

    protected void updateData(CardSucPersona record) {
        this.record = record;
    }

    public static void delete(SHRObjects frame, PersonaRec p) {
        p.parent.deleteRecord(p);
    }
}
