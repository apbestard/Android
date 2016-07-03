/*
Derechos Reservados (c)
Ing. Jorge Guzmán Camarena / Consultoria Integral en Sistemas y Finanzas, S.A. de C.V.
2009, 2010, 2011, 2012, 2013, 2014
logipax Marca Registrada (R)  2003, 2014
*/
package backend.card.data;

import java.awt.Color;
import backend.card.io.ViewRecords.SRepoView;

public class Views {

    public static SRepoView view01() {
        return new SRepoView("v0", "Todo", "Todos las columnas y renglones", 1, new int[]{}, new int[]{},
                3, new String[]{"Nombre 01", "Nombre 02", "Nombre 03"},
                new int[]{0, 1, 2},
                new int[]{0, 0, 1},
                new int[]{-1, -1, -2},
                new int[]{0, 1, -1},
                new int[]{0, 0, 1},
                new int[]{Color.ORANGE.getRGB(), Color.CYAN.getRGB(), Color.PINK.getRGB()},
                new String[]{"$", "#"},
                new int[]{Color.BLACK.getRGB(), Color.GRAY.getRGB()});

    }

    public static SRepoView view02() {
        return new SRepoView("v1", "Saldos", "Saldos", 0, new int[]{0, 1, 2, 3, 4, 5}, new int[]{0, 1, 2, 3, 4},
                3, new String[]{"Créditos Vigente", "Créditos Vencido", "Créditos Por Liq", "Saldo Vigente", "Saldos Vencido", "Saldo Por Liq", "% Saldo Vencido"},
                new int[]{0, 1, 2, 0, 1, 2, 1},
                new int[]{0, 0, 0, 2, 2, 2, 3},
                new int[]{-1, 0, 1, -1, 3, 4, -2},
                new int[]{0, 0, 0, 1, 1, 1, -1},
                new int[]{0, 0, 0, 1, 1, 1, 2},
                new int[]{Color.BLACK.getRGB(), Color.RED.getRGB(), Color.PINK.getRGB(), Color.GRAY.getRGB(), Color.ORANGE.getRGB(), Color.YELLOW.getRGB(), Color.BLUE.getRGB()},
                new String[]{"#", "$", "%"},
                new int[]{Color.BLACK.getRGB(), Color.GRAY.getRGB(), Color.LIGHT_GRAY.getRGB()});

    }

    public static SRepoView view03() {
        return new SRepoView("v2", "Vista 02", "Vista 02", 0, new int[]{0}, new int[]{0, 1},
                3, new String[]{"Nombre 01", "Nombre 02", "Nombre 03"},
                new int[]{0, 1, 2},
                new int[]{0, 0, 1},
                new int[]{-1, 0, -2},
                new int[]{0, 0, -1},
                new int[]{0, 0, 0},
                new int[]{Color.GREEN.getRGB(), Color.CYAN.getRGB(), Color.PINK.getRGB()},
                new String[]{"$", "#"},
                new int[]{Color.BLACK.getRGB(), Color.GRAY.getRGB()});
        
    }
    

    public static SRepoView view04() {
        return new SRepoView("v3", "Vista 03", "Vista 03", -1, new int[]{1}, new int[]{0},
                3, new String[]{"Nombre 01", "Nombre 02", "Nombre 03"},
                new int[]{0, 1, 2},
                new int[]{0, 0, 1},
                new int[]{-1, -1, -2},
                new int[]{0, 1, -1},
                new int[]{0, 0, 1},
                new int[]{Color.GRAY.getRGB(), Color.CYAN.getRGB(), Color.PINK.getRGB()},
                new String[]{"$", "#"},
                new int[]{Color.BLACK.getRGB(), Color.GRAY.getRGB()});

    }
}
