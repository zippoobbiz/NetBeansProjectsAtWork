/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package databasecontroller;

import databasecontroller.entity.Pair;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author Phil
 *
 * Compare the file names in warehouse to the sharecompany table,
 * actually, we can use sharemanager table instead of files in warehouse folder.
 *
 * 10/2/2016
 * Today, I am going to check the missing companies, if they have duplicate shares in different country, or just have different share name
 *
 */
public class Main4 {

    ArrayList<String> missingCompaniesId = new ArrayList();
    ArrayList<String> missingCompaniesShareId = new ArrayList();
    ArrayList<String> badFiles = new ArrayList();
    ArrayList<String> emptyCompaniesShareId = new ArrayList();
    ArrayList<Pair> pairList = new ArrayList();

    public static void main(String[] args) {
        Main4 m = new Main4();
        m.report();
        m.listEmptyCompaniesShares();
        m.CheckIntigrityOfMissingCompanies();
//        m.listMissingCompaniesShares();
    }

    /**
     * List Pairs of duplications, empty companies in DB, Missing cimpanies and Bad files
     */
    public void report() {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = DBHelper.getConnectionFromCertainDB("bcmglobalshares");
            stmt = conn.createStatement();
            HashSet<String> fileNames = FileHelper.getAllFileName("E:\\BCM-World_New_checkout\\RealtimeDataDownload\\data\\Share\\warehouse");
            String sql = "SELECT shareclassid FROM sharecompany";
            ResultSet rs = stmt.executeQuery(sql);
            HashSet<String> set = new HashSet();
            while (rs.next()) {
                set.add(rs.getString(1));
            }

            System.out.println("Shares from sharecompany: " + set.size());
            System.out.println("Shares from warehouse folder: " + fileNames.size());
            fileNames.removeAll(set);
            System.out.println("The difference between above lists" + fileNames.size());




            for (String code : fileNames) {
                File f = new File("E:\\BCM-World_New_checkout\\RealtimeDataDownload\\data\\Share\\warehouse\\" + code + ".xml");
//                String companyId = XMLHelper.getCompanyId(f);
                String companyId = FileHelper.findCompanyId(f);
                if (companyId.equals("html")) {
                    badFiles.add(code);
                } else {
                    sql = "SELECT shareclassid FROM sharecompany where companyid = '" + companyId + "'";
                    rs = stmt.executeQuery(sql);
                    if (rs.next()) {
                        if (rs.getString(1) == null) {
                            emptyCompaniesShareId.add(code);
                        } else if (!code.equals(rs.getString(1))) {
                            pairList.add(new Pair(companyId, code, rs.getString(1)));
                            System.out.println(companyId + "," + code + "," + rs.getString(1));
                        }
                    } else {
                        missingCompaniesId.add(companyId);
                        missingCompaniesShareId.add(code);
//                    System.out.println("Cannot find " + companyId);
                    }
                }
            }
            System.out.println("pairList : " + pairList.size());
            System.out.println("emptyCompaniesShareId : " + emptyCompaniesShareId.size());
            System.out.println("missingCompaniesId : " + missingCompaniesId.size());
            System.out.println("badFiles : " + badFiles.size());
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }
    }

    public void printDetailsOfPairListFromDB() {
        Connection conn = DBHelper.getConnectionFromCertainDB("bcmglobalshares");
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = null;
            String sql = null;
            for (Pair p : pairList) {
                sql = "SELECT companyName FROM sharemanager where morningstarPerformanceid = '" + p.getShareClass1() + "'";
                rs = stmt.executeQuery(sql);
                if (rs.next()) {
                    p.setShare1CompanyName(rs.getString(1));
                }
                sql = "SELECT companyName FROM sharemanager where morningstarPerformanceid = '" + p.getShareClass2() + "'";
                rs = stmt.executeQuery(sql);
                if (rs.next()) {
                    p.setShare2CompanyName(rs.getString(1));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Main4.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (Pair p : pairList) {
            if (p.getShare1CompanyName() != null && p.getShare2CompanyName() != null && !p.getShare1CompanyName().equals(p.getShare2CompanyName())) {
                System.out.println(p.getCompany());
                System.out.println(p.getShare1CompanyName() + " - " + p.getShare2CompanyName());
            }

        }

    }

//    public void printDetailsOfPairListFromWarehouse() {
//
//        for (Pair p : pairList) {
//            p.setShare1Name(XMLHelper.getShareName(p.getShareClass1()));
//            p.setShare2Name(XMLHelper.getShareName(p.getShareClass2()));
//        }
//
//        for (Pair p : pairList) {
//            if (p.getShare1Name() != null && p.getShare2Name() != null && !p.getShare1Name().equals(p.getShare2Name())) {
//                System.out.println(p.getCompany());
//                System.out.println(p.getShare1Name() + " - " + p.getShare2Name());
//            }
//
//        }
//
//    }
    public void listMissingCompaniesShares() {
        Connection conn = DBHelper.getConnectionFromCertainDB("bcmglobalshares");
        String html = "";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = null;
            String sql = null;
            for (String shareId : missingCompaniesShareId) {
                sql = "SELECT companyName, isincode, exchangecode,securitytype, trickersymbol FROM sharemanager where morningstarPerformanceid = '" + shareId + "'";
                rs = stmt.executeQuery(sql);
                if (rs.next()) {
                    html += "<tr>";
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Main4.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (Pair p : pairList) {
            if (p.getShare1CompanyName() != null && p.getShare2CompanyName() != null && !p.getShare1CompanyName().equals(p.getShare2CompanyName())) {
                System.out.println(p.getCompany());
                System.out.println(p.getShare1CompanyName() + " - " + p.getShare2CompanyName());
            }

        }
    }

    public void CheckIntigrityOfMissingCompanies() {
        int count = 0;
        for (String s : missingCompaniesShareId) {
            try {
                XMLHelper.checkIntigrity(new File("E:\\BCM-World_New_checkout\\RealtimeDataDownload\\data\\Share\\warehouse\\" + s + ".xml"));
                System.out.println(s);
                count++;
            } catch (ParserConfigurationException ex) {
                Logger.getLogger(Main4.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SAXException ex) {
                Logger.getLogger(Main4.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Main4.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println(count);
    }

    public void listEmptyCompaniesShares() {
        for (String s : emptyCompaniesShareId) {
            System.out.println(s);
        }
    }
}
