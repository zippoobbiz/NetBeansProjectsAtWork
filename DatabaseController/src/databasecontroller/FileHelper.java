/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package databasecontroller;

import databasecontroller.entity.ShareDetails;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Phil
 */
public class FileHelper {

    private static String RESOURCE_FOLDER = "resources\\";

    public static String readFile(String fileName) {
        BufferedReader br = null;
        String everything = null;
        try {
            String path = new File(".").getAbsolutePath();
            path = path.substring(0, path.length() - 1) + RESOURCE_FOLDER + fileName;

            br = new BufferedReader(new FileReader(path));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line + "\n");
                line = br.readLine();
            }
            everything = sb.toString();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(FileHelper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return everything;
    }

    public static String[] readFileToStringArray(String fileName) {
        BufferedReader br = null;
        String everything = null;
        try {
            String path = new File(".").getAbsolutePath();
            path = path.substring(0, path.length() - 1) + RESOURCE_FOLDER + fileName;

            br = new BufferedReader(new FileReader(path));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line + "\n");
                line = br.readLine();
            }
            everything = sb.toString();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(FileHelper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return everything.split("\n");
    }

    public static boolean writeToFile(String content, String fileName) {
        Writer writer = null;
        String path = new File(".").getAbsolutePath();
        path = path.substring(0, path.length() - 1) + RESOURCE_FOLDER + fileName;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(path), "utf-8"));
            writer.write(content);
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            try {
                writer.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return true;
    }

    public static HashSet<String> getAllFileName(String path) {
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        HashSet<String> fileNames = new HashSet<String>();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                fileNames.add(listOfFiles[i].getName().substring(0, listOfFiles[i].getName().indexOf(".")));
            } else if (listOfFiles[i].isDirectory()) {
                System.out.println("Directory " + listOfFiles[i].getName());
            }
        }
        return fileNames;
    }

    public static String findCompanyId(File f) {
        BufferedReader br = null;
        String line = null;
        char[] chars = new char[100];
        try {
            br = new BufferedReader(new FileReader(f.getAbsolutePath()));
            do {

                br.read(chars);
                line = String.copyValueOf(chars);
            } while (!line.contains("companyId") && !line.contains("html"));
            if (line.charAt(84) == line.charAt(95)) {
                return line.substring(85, 95);
            } else if (line.contains("html")) {
                return "html";
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(FileHelper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return "";
    }

    public static HashSet readUniqueLinesToArray(String fileName) {
        BufferedReader br = null;
        HashSet<String> Codes = new HashSet();
        try {
            String path = new File(".").getAbsolutePath();
            path = path.substring(0, path.length() - 1) + RESOURCE_FOLDER + fileName;

            br = new BufferedReader(new FileReader(path));

            String line = br.readLine();
            while (line != null) {
                Codes.add(line.substring(0, line.indexOf(",")));
                line = br.readLine();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(FileHelper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return Codes;
    }

    public static HashMap<String, ShareDetails> calculateDistribution(String fileName) {
        BufferedReader br = null;
        HashMap<String, ShareDetails> distribution = new HashMap();
        try {
            String path = new File(".").getAbsolutePath();
            path = path.substring(0, path.length() - 1) + RESOURCE_FOLDER + fileName;

            br = new BufferedReader(new FileReader(path));

            String line = br.readLine();
            while (line != null) {
                String temp = line.substring(0, line.indexOf(","));
                String dateString = line.substring(line.indexOf(",")+1, line.indexOf(",", line.indexOf(",") + 1));
                DateFormat format = new SimpleDateFormat("dd-mm-yyyy", Locale.ENGLISH);
                Date date = new Date();
                try {
                    date = format.parse(dateString);
                } catch (ParseException ex) {
                    Logger.getLogger(FileHelper.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (distribution.get(temp) == null) {
                    distribution.put(temp, new ShareDetails(date));
                } else {
                    ShareDetails tmp = distribution.get(temp);
                    tmp.setItemAmount(tmp.getItemAmount() + 1);
                    if (date.before(tmp.getLastUpdate())) {
                        tmp.setLastUpdate(date);
                    }
                    distribution.put(temp, tmp);
                }
                line = br.readLine();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(FileHelper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return distribution;
    }
}
