package servlets;

import beutility.Strings;

public class FileInfo {
        String value = "||";
        boolean deleted = false;
        long timer = -1;
        String md5 = "";

        public FileInfo(byte[] bdata) {
            if (bdata == null) {
                initialize(new String[0]);
            } else {
                initialize(FilesMngt.tokenizer(new String(bdata)));
            }
        }

        public FileInfo(String bdata) {
            if (bdata == null) {
                initialize(new String[0]);
            } else {
                initialize(FilesMngt.tokenizer(bdata));
            }
        }

        public FileInfo(String data[]) {
            initialize(data);
        }

        public FileInfo(java.io.File file, long dtimer) {
            initialize(FilesMngt.getFileInfo(file, dtimer));
        }

        private void initialize(String data[]) {
            value = "|";
            if (data.length > 0 && data[0] != null) {
                deleted = data[0].equals("0");
                value += data[0];
            }
            value += "|";
            if (data.length > 1 && data[1] != null) {
                timer = Strings.getLong(data[1]);
                value += data[1];
            }
            value += "|";
            if (data.length > 2 && data[2] != null) {
                md5 = data[2];
                value += data[2];
            }
            value += "\n";
        }

        protected String value() {
            return (value);
        }
/*
        public FileInfo(byte[] bdata) {
            if (bdata == null) {
                initialize(new String[0]);
            } else {
                initialize(FilesMngt.tokenizer(new String(bdata)));
            }
        }

        public FileInfo(java.io.File file, long dtimer) {
            initialize(FilesMngt.getFileInfo(file, dtimer));
        }

        private void initialize(String data[]) {
            value = "|";
            if (data.length > 0 && data[0] != null) {
                deleted = data[0].equals("0");
                value += data[0];
            }
            value += "|";
            if (data.length > 1 && data[1] != null) {
                timer = Strings.getLong(data[1]);
                value += data[1];
            }
            value += "|";
            if (data.length > 2 && data[2] != null) {
                md5 = data[2];
                value += data[2];
            }
            value += "\n";
        }

        protected String value() {
            return (value);
        }
 */
}
