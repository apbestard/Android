/*
Derechos Reservados (c)
Ing. Jorge Guzmán Camarena / Consultoria Integral en Sistemas y Finanzas, S.A. de C.V.
2009, 2010, 2011, 2012, 2013, 2014
logipax Marca Registrada (R)  2003, 2014
*/
package backend.card.io;

import mx.logipax.shared.objects.card.data.tmp.Card;
import mx.logipax.shared.objects.viewer.Report;

public class ViewRecords {

    public static class SModule {

        String profileIds[];
        String id;
        String name;
        String filter;
        String reportIds[];

        public SModule(String profileIds[], String id, String name, String filter) {
            this.profileIds = profileIds;
            this.id = id;
            this.name = name;
            this.filter = filter;
        }
    }

    public static class SProfile {

        String id;
        SModule modules[];

        public SProfile(String id) {
            this.id = id;
        }
    }

    public static class SUser {

        String id;
        String name;
        String profile;
        SModule modules[];

        public SUser(String id, String name, String profile) {
            this.id = id;
            this.name = name;
            this.profile = profile;
        }
    }
//---------------      

    public static class STableRow {

        public String id;
        public String name;
        public String subname;
        public String subsubname;
        public int bkcolor;
        public int color;
        public int tabHSize = 16;
        public String attrs;
        public String type;
        public String desc;

        public STableRow() {
        }

        public STableRow(String id, String name, String subname, String type, String desc) {
            this.id = id;
            this.name = name;
            this.subname = subname;
            this.type = type;
            this.desc = desc;
        }
    }

    public static class STableColumn {

        public String id;
        public String name;
        public String subname;
        public String subsubname;
        public int bkcolor;
        public int color;
        public int tabWSize;
        public String attrs;
        public String type;
        public String desc;

        public STableColumn() {
        }

        public STableColumn(String id, String name, String subname, String type, String desc, int tabWSize) {
            this.id = id;
            this.name = name;
            this.subname = subname;
            this.type = type;
            this.desc = desc;
            this.tabWSize = tabWSize;
        }
    }

    public static class SPeriod {

        public String id;
        public String name;
        public String type;

        public SPeriod() {
        }

        public SPeriod(String id, String name, String type) {
            this.id = id;
            this.name = name;
            this.type = type;
        }
    }

    public static class SRepoView {

        public String id;
        public String name;
        public String desc;
        public String attrs;
        public int divide;
        public int[] tabcolumns;
        public int[] tabrows;
        public int grppointsize;
        public String grppntnames[];
        public int grppntrows[];
        public int grppntcolumns[];
        public int grppntrefs[];
        public int grppntoffsets[];
        public int grppntescales[];
        public int grppntcolors[];
        public String grpescales[];
        public int grpescalecolors[];

        public SRepoView() {
        }

        public SRepoView(String id, String name, String desc, int divide, int[] tabcolumns, int[] tabrows,
                int grppointsize, String[] grppntnames, int[] grppntrows, int[] grppntcolumns,
                int[] grppntrefs, int[] grppntoffsets, int[] grppntescales, int[] grppntcolors,
                String[] grpescales, int[] grpescalecolors) {
            this.id = id;
            this.name = name;
            this.desc = desc;
            this.tabcolumns = tabcolumns;
            this.tabrows = tabrows;
            this.divide = divide;

            this.grppointsize = grppointsize;
            this.grppntnames = grppntnames;
            this.grppntrows = grppntrows;
            this.grppntcolumns = grppntcolumns;
            this.grppntrefs = grppntrefs;
            this.grppntoffsets = grppntoffsets;
            this.grppntescales = grppntescales;
            this.grppntcolors = grppntcolors;
            this.grpescales = grpescales;
            this.grpescalecolors = grpescalecolors;

        }
    }
    
    public static class SRepoLevelData {
    public int level = 0;
    public String id = "";
    public String apertura = "";
    public int asignadas = 0;
    public int vacantes = 0;
    
        public SRepoLevelData() {
        }

        public SRepoLevelData(int level, String id, String apertura, int asignadas, int vacantes) {
            this.level = level;
            this.id = id;
            this.apertura = apertura;
            this.asignadas = asignadas;
            this.vacantes = vacantes;
        }
    }
    

    public static class SRepoLevel {

        public int level;
        public String id;
        public String name;
        public String desc;
        public SRepoLevelData data;
        public SRepoLevel levels[];

        public SRepoLevel() {
        }

        public SRepoLevel(int level, String id, String name, String desc, SRepoLevelData data, SRepoLevel levels[]) {
            this.level = level;
            this.id = id;
            this.name = name;
            this.desc = desc;
            this.data = data;
            this.levels = levels;
        }
    }

    public static class SReport {

        public String moduleId;
        public String id;
        public String idimage;
        public String pool;
        public String pretab0;
        public String pretab1;
        
        public String name;
        public String desc;
        public int tabHSize = 16;
        public int columnFixed;
        public String attrs;
        public String datosId;
        public boolean edit;
        public String filter;
        public String[] levelIds;
        public String[] levelNames;
        public SRepoLevel[] levels;
        public int treeHSize;
        public SRepoView views[];
        public SPeriod periods[];
        public STableColumn tabcolumns[];
        public STableRow tabrows[];

        public SReport() {
        }

        public SReport(String moduleId, String id, String idimage, String pool, String prtab0, String prtab1,
                String name, String desc, String datosId,
                boolean edit, String attrs, String filter,
                String[] levelIds, String[] levelNames,
                SRepoLevel[] levels, int treeHSize, int columnFixed, 
                SRepoView views[], SPeriod periods[],
                STableColumn tabcolumns[], STableRow tabrows[]) {
            this.moduleId = moduleId;
            this.id = id;
            this.idimage = idimage;
            this.pool = pool;
            this.pretab0 = pretab0;
            this.pretab1 = pretab1;
            this.name = name;
            this.desc = desc;
            this.datosId = datosId;
            this.levelIds = levelIds;
            this.levelNames = levelNames;
            this.levels = levels;
            this.treeHSize = treeHSize;
            this.columnFixed = columnFixed;
            this.edit = edit;
            this.attrs = attrs;
            this.filter = filter;
            this.views = views;
            this.periods = periods;
            this.tabcolumns = tabcolumns;
            this.tabrows = tabrows;
        }

        public boolean ranking() {
            if (attrs == null) {
                return false;
            }
            return attrs.indexOf(Report.ATTRRANK) >= 0;
        }
    }

    public static class SCreditList {
        public String ejecutive = "";
        public String credits[];
    }

    public static class SCredit {
        public String credit = "";
        public String customer = "";
        public String name = "";
        public double capital = 0;
        public double exgcapital = 0;
        public double exgintord = 0;
        public double exgintmora = 0;
    }
}
