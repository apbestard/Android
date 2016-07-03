package mx.logipax.frontend.card.panel.editor.rec;

import mx.logipax.ide.SHRObjects;
import mx.logipax.shared.objects.card.cat.CardSuc;

public class CardRec {

    private SHRObjects frame;
    private CardsRec parent;
    private CardSuc record;

    public CardRec(SHRObjects frame, CardsRec parent, String recordId, CardSuc record) {
        this.frame = frame;
        this.parent = parent;
        this.record = record;
    }

    public String getValue(int col) {
        switch (col) {
            case 0:
                return (record.getCardId());
            case 1:
                return (record.getCardNombre());
            case 2:
                return (record.getFechaString());
        }
        return ("?");
    }

    public void setValue(int col, Object value) {
    }

    public CardSuc getRecord() {
        return record;
    }

    protected void updateData(CardSuc record) {
        this.record = record;
    }

    public static void delete(SHRObjects frame, CardRec p) {
        p.parent.deleteCard(p);
    }
}
