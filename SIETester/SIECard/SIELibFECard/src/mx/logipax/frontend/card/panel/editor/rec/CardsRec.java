package mx.logipax.frontend.card.panel.editor.rec;

import mx.logipax.ide.SHRObjects;

public class CardsRec {
    private SHRObjects frame;
    private CardRec[] data;
    private static String headers[] = new String[]{"Id", "Nombre", "Creación"};
    private static int headersJustify[] = new int[]{-1, -1, -1};

    public CardsRec(SHRObjects frame) {
        this.frame = frame;
        data = new CardRec[0];
    }
    
    private CardRec[] append(CardRec item, CardRec[] list) {
        CardRec list2[] = new CardRec[list.length + 1];
        int index = list.length;
        System.arraycopy(list, 0, list2, 0, index);
        list2[index] = item;
        System.arraycopy(list, index, list2, index + 1, list.length - index);
        return list2;
    }
    
    public void dispose() {
        data = new CardRec[0];
    }
    public CardRec[] getCards() {
        return(data);
    }
    public void move(int from, int to) {
        int index = from;
        CardRec page = data[from];
        CardRec pages2[] = new CardRec[data.length - 1];
        System.arraycopy(data, 0, pages2, 0, index);
        System.arraycopy(data, index + 1, pages2, index, data.length - (index + 1));
        data = pages2;

        index = to;
        pages2 = new CardRec[data.length + 1];
        System.arraycopy(data, 0, pages2, 0, index);
        pages2[index] = page;
        System.arraycopy(data, index, pages2, index + 1, data.length - index);
        data = pages2;
    }
    public String[] getHeaders() {
        return(headers);
    }
    public int[] getHeadersJustify() {
        return(headersJustify);
    }
    public String getValue(CardRec c, int col) {
        return c.getValue(col);
    }
    public void setValue(CardRec c, int col, Object value) {
        c.setValue(col, value);
    }
   
    public int insertCard(CardRec m) {
        if (data == null) {
            return -1;
        }
        return (insertCard(m, data.length));
    }

    public int insertCard(CardRec m, int index) {
        if (data == null) {
            return -1;
        }
        if (m == null) {
            return data.length;
        }
        CardRec cregions2[] = new CardRec[data.length + 1];
        System.arraycopy(data, 0, cregions2, 0, index);
        cregions2[index] = m;
        System.arraycopy(data, index, cregions2, index + 1, data.length - index);
        data = cregions2;
        return (index);
    }

    public int find(CardRec m) {
        if (data == null || m == null) {
            return (-1);
        }
        for (int i = 0; i < data.length; i++) {
            if (m.getRecord().getCardId().equals(data[i].getRecord().getCardId())) {
                return (i);
            }
        }
        return (-1);
    }

    public int deleteCard(CardRec m) {
        if (data == null) {
            return -1;
        }
        int index = find(m);
        if (index < 0) {
            return (0);
        }
        CardRec cregions2[] = new CardRec[data.length - 1];
        System.arraycopy(data, 0, cregions2, 0, index);
        System.arraycopy(data, index + 1, cregions2, index, data.length - (index + 1));
        data = cregions2;
        deleteCard(m);
        return (index);
    }

    public int updateCard(CardRec m) {
        if (data == null) {
            return -1;
        }
        int index = find(m);
        if (index < 0) {
            return (0);
        }
        data[index] = m;
        return (index);
    }
}
