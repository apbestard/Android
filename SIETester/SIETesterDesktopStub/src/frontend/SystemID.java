/*
Derechos Reservados (c)
Ing. Jorge Guzmán Camarena / Consultoria Integral en Sistemas y Finanzas, S.A. de C.V.
2009, 2010, 2011, 2012, 2013, 2014
logipax Marca Registrada (R)  2003, 2014
*/
package frontend;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class SystemID {

	public static final int OS_UNSUPPORTED = -1;
	public static final int OS_LINUX = 1;
	public static final int OS_WINDOWS = 2;
	public static final int OS_WINDOWS_CE = 3;
	public static final int OS_MAC_OS_X = 4;
	public static int os = 0;
	public static String licencesDeflDir = null;
        public static final String ZENDID = "zendid";
        public static final String ALLID = "allid";
        public static final String ONEID = "";
        
	public SystemID() {
	}


        public static boolean validate(String options) {
            String licences[] = getIDs(ZENDID, ALLID);
            if (licences == null) return(false);
            String licence = readLicence(".\\logipax.lic");
            for (int i=0;i<licences.length;i++) {
                String data = getEncrypted((licences[i]+options).getBytes());
                if (data.equals(licence)) {
                    return(true);
                }
            }
            return(false);
        }
        
	public static boolean create(String options) {
            String licences[] = getIDs(ZENDID, ONEID);
            String data = getEncrypted((licences[0]+options).getBytes());
            createLicence(".\\logipax.lic", data);
            return(true);
	}
        
        
	public static String[] getIDs(String name, String params) {
		return getIDs(name, null, params);
	}

	public static String[] getIDs(String name, Class stackClass, String params) {
		String libName = name;
		String libFileName = libName;
		String sysName = System.getProperty("os.name");
		String sysArch = System.getProperty("os.arch");
		if (sysArch != null) {
                    sysArch = sysArch.toLowerCase();
		} else {
                    sysArch = "";
		}
		switch (getOS()) {
		case OS_WINDOWS_CE:
		case OS_WINDOWS:
			libFileName = libFileName + ".exe";
			break;
		case OS_MAC_OS_X:
			libFileName = libFileName+"_mac";
			break;
		case OS_LINUX:
			libFileName = libName;
			break;
		case OS_UNSUPPORTED:
			//System.err.println("Native Library " + name + " not available on [" + sysName + "] platform");
			return null;
		default:
			return null;
		}
                String result[] = loadAsSystemResource(libFileName, stackClass, params);
		if (result == null) {
                    ////System.err.println("Native Library " + libName + " not available");
                    ////System.out.println("java.library.path"+ System.getProperty("java.library.path"));
                    return(null);
		}
		return result;
	}

	public static int getOS() {
		if (os != 0) {
			return os;
		}
		String sysName = System.getProperty("os.name");
		if (sysName == null) {
			//System.err.println("Native Library not available on unknown platform");
			os = OS_UNSUPPORTED;
		} else {
			sysName = sysName.toLowerCase();
			if (sysName.indexOf("windows") != -1) {
				if (sysName.indexOf("ce") != -1) {
					os = OS_WINDOWS_CE;
				} else {
					os = OS_WINDOWS;
				}
			} else if (sysName.indexOf("mac os x") != -1) {
				os = OS_MAC_OS_X;
			} else if (sysName.indexOf("linux") != -1) {
				os = OS_LINUX;
			} else {
				//System.err.println("Native Library not available on platform " + sysName);
				os = OS_UNSUPPORTED;
			}
		}
		return os;
	}

	private static String[] loadAsSystemResource(String libFileName, Class stackClass, String params) {
		InputStream is = null;
		try {
			ClassLoader clo = null;
			try {
				if (stackClass != null) {
					clo = stackClass.getClassLoader();
					////System.out.println("Use stack ClassLoader");
				} else {
					clo = SystemID.class.getClassLoader();
				}
			} catch (Throwable j9) {
			}
			if (clo == null) {
				//System.out.println("Use System ClassLoader");
				is = ClassLoader.getSystemResourceAsStream(libFileName);
			} else {
				is = clo.getResourceAsStream(libFileName);
			}
		} catch (Throwable e) {
			//System.err.println("Native Library " + libFileName + " is not a Resource !");
			return new String[0];
		}
		if (is == null) {
			//System.err.println("Native Library " + libFileName + " is not a Resource !");
			return new String[0];
		}
                    java.io.File fd = makeTempName(libFileName);
		try {
			if (!copy2File(is, fd)) {
				return new String[0];
			}
		} finally {
			try {
				is.close();
			} catch (IOException ignore) {
				is = null;
			}
		}
		try {
			fd.deleteOnExit();
		} catch (Throwable e) {
			// Java 1.1 or J9
		}
                return tryExec(fd.getPath(), params);

	}

	private static boolean copy2File(InputStream is, java.io.File fd) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(fd);
			byte b[] = new byte[1000];
			int len;
			while ((len = is.read(b)) >= 0) {
				fos.write(b, 0, len);
			}
			return true;
		} catch (Throwable e) {
			//System.out.println("Can't create temporary file "+ e);
			//System.err.println("Can't create temporary file " + fd.getAbsolutePath());
			return false;
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException ignore) {
					fos = null;
				}
			}
		}
	}

	private static java.io.File makeTempName(String libFileName) {
		if (licencesDeflDir != null) {
			return new java.io.File(licencesDeflDir, libFileName);
		}
		String tmpDir = System.getProperty("java.io.tmpdir");
		String uname = System.getProperty("user.name");
		int count = 0;
		java.io.File fd = null;
		java.io.File dir = null;
		selectDirectory: while (true) {
			if (count > 10) {
				//System.out.println("Can't create temporary dir " + dir.getAbsolutePath());
				return new java.io.File(tmpDir, libFileName);
			}
			dir = new java.io.File(tmpDir, "btood_" + uname + "_" + (count++));
			if (dir.exists()) {
				if (!dir.isDirectory()) {
					continue selectDirectory;
				}
				// Remove all files.
				try {
					java.io.File[] files = dir.listFiles();
					for (int i = 0; i < files.length; i++) {
						if (!files[i].delete()) {
							continue selectDirectory;
						}
					}
				} catch (Throwable e) {
					// Java 1.1 or J9
				}
			}
			if ((!dir.exists()) && (!dir.mkdirs())) {
				//System.out.println("Can't create temporary dir "+ dir.getAbsolutePath());
				continue selectDirectory;
			}
			try {
				dir.deleteOnExit();
			} catch (Throwable e) {
				// Java 1.1 or J9
			}
			fd = new java.io.File(dir, libFileName);
			if ((fd.exists()) && (!fd.delete())) {
				continue;
			}
			try {
				if (!fd.createNewFile()) {
					//System.out.println("Can't create file in temporary dir "+ fd.getAbsolutePath());
					continue;
				}
                                //java.io.FilePermission fp = new java.io.FilePermission(fd.getPath(), "execute");
                                fd.setExecutable(true);

			} catch (IOException e) {
				//System.out.println("Can't create file in temporary dir "+ fd.getAbsolutePath());
				continue;
			} catch (Throwable e) {
				// Java 1.1 or J9
			}
			licencesDeflDir = dir.getAbsolutePath();
			break;
		}
		return fd;
	}

        public static String[] tryExec(String name, String params) {
            Process proc = null; 
            String result[] = new String[0];
            try { 
               proc = Runtime.getRuntime().exec(name+" "+params); 
            } catch (IOException e) { 
                System.out.println(e.toString());
            }
            if (proc != null) { 
               BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream())); 
               PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(proc.getOutputStream())), true); 
               try { 
                  String line; 
                    while ((line = in.readLine()) != null) { 
                        ////System.out.println("out : "+line); 
                        int inx = line.indexOf(":");
                        String line2 = line;
                        if (inx >= 0) line2 = line.substring(inx+1);
                        result = append(result, line2);
                    } 
                    proc.waitFor(); 
                  in.close(); 
                  out.close(); 
                  proc.destroy(); 
               } 
               catch (Exception e) { 
                //System.out.println(e.toString());
                return(null);
               } 
            }
            return(result);
        }

        public static String tryExecFirstLine(String params[]) {
            Process proc = null; 
            String result[] = new String[0];
            try { 
               proc = Runtime.getRuntime().exec(params); 
               //proc = Runtime.getRuntime().exec(name, params, new java.io.File(dir)); 
            } catch (IOException e) { 
                return(e.getMessage());//System.out.println(e.toString());
            }
            if (proc != null) { 
               BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream())); 
               PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(proc.getOutputStream())), true); 
               try { 
                  String line; 
                    while ((line = in.readLine()) != null) { 
                        System.out.println("out : "+line); 
                        int inx = line.indexOf(":");
                        String line2 = line;
                        if (inx >= 0) line2 = line.substring(inx+1);
                        result = append(result, line2);
                        return(null);
                    } 
                  int exitCode = proc.waitFor(); 
                  in.close(); 
                  out.close(); 
                  proc.destroy(); 
                  return("Error en ejecución: "+exitCode);
               } 
               catch (Exception e) { 
                    return(e.getMessage());//System.out.println(e.toString());
               } 
            }
            return(null);
        }
        
        public static String tryExecFirstLine(String name, String params) {
            Process proc = null; 
            String result[] = new String[0];
            try {
               proc = Runtime.getRuntime().exec(name+" "+params); 
            } catch (IOException e) { 
                return(e.getMessage());//System.out.println(e.toString());
            }
            if (proc != null) { 
               BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream())); 
               PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(proc.getOutputStream())), true); 
               try { 
                    String line = "";
                    if (true) {
                    //while ((line = in.readLine()) != null) {
                        int inx = line.indexOf(":");
                        String line2 = line;
                        if (inx >= 0) line2 = line.substring(inx+1);
                        result = append(result, line2);
                        return(null);
                    //}
                    }
                    int exitCode = proc.waitFor();
                    in.close();
                    out.close();
                    proc.destroy();
                    return("Error en ejecución: "+exitCode);
               } catch (Exception e) { 
                    return(e.getMessage());//System.out.println(e.toString());
               } 
            }
            return(null);
        }
        

    public static void createLicence(String name, String data) {
      try {
            BufferedWriter w = new BufferedWriter(new FileWriter(name, true)); 
            w.write(data);
            w.newLine();
            w.flush();
            w.close();
      } catch (Exception ex) {
      }
    }


    public static String readLicence(String name) {
        try {
            BufferedReader r = new BufferedReader(new FileReader(name));
            String data = "";
            String line = r.readLine();; 
            while (line != null) {
                if (data.length() > 0) {
                    data += "\n";
                }
                data += line;
                line = r.readLine();; 
            }
            r.close();
            return(data);
        } catch (Exception ex) {
            //System.err.println(ex.toString());
        }
        return (null);
    }
        
    
    public static String getEncrypted(byte[] pwd) {
        byte[] hash = new byte[0];
        try {
            java.security.MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(pwd);
            hash = digest.digest();
        } catch(Exception ex) {
        }
        String hashStr = "";
        for (int i=0;i<hash.length;i++) {
            int val = 0xff & hash[i];
            String valStr = Integer.toHexString(val);
            while (valStr.length() < 2) {
                valStr = "0"+valStr;
            }
            hashStr += valStr;
        }
        return(hashStr);
    }
    
    public static String [] append(String[] list, String item) {
        if (java.util.Arrays.binarySearch(list, item) < 0) {
            String [] list2 = new String[list.length+1];
            System.arraycopy(list, 0, list2, 0, list.length);
            list2[list.length] = item;
            java.util.Arrays.sort(list2);
            return(list2);
        }
        return(list);
    }            

}
