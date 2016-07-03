/*
Derechos Reservados (c)
Ing. Jorge Guzmán Camarena / Consultoria Integral en Sistemas y Finanzas, S.A. de C.V.
2009, 2010, 2011, 2012, 2013, 2014
logipax Marca Registrada (R)  2003, 2014
*/
package backend.card.io.db;

public class NivelName {
    public String nombre;
    public int index;
    public String padre;

    public NivelName(String nombre, int index, String padre) {
        this.nombre = nombre==null?"-":nombre;
        this.index = index;
        this.padre = padre==null?"*":padre;
    }
}
