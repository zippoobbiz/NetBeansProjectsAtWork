/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package databasecontroller;

import databasecontroller.entity.ShareDetails;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Phil
 */
public class HistoryPriceHandler {

    HashSet<String> nameCode;
    HashMap<String, String> missingNameCode;

    public static void main(String[] args) {
        HistoryPriceHandler h = new HistoryPriceHandler();
        h.init();
//        try {
//        h.calculateAvgHistoryItems();
//        h.getMissingCompanies();

//        } catch (ParseException ex) {
//            Logger.getLogger(HistoryPriceHandler.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        h.getMissingCompanies();
        h.comparePBAndDailyPrice();
//        h.getAllNoneCompleteShares();
    }

    public void init() {
        nameCode = FileHelper.readUniqueLinesToArray("History.txt");
        missingNameCode = getUploadedCompanies();
    }

    public static HashMap getUploadedCompanies() {
        String[] line = FileHelper.readFileToStringArray("missingLongTermPrice.txt");
        HashMap<String, String> set = new HashMap();
        for (String s : line) {
            if (s.contains("\"")) {
                s = s.replaceAll("(?<=\")(.*)(?=\")", "hhh");
                System.out.println("====" + s);
            }
            String[] temp = s.split(",");
            set.put(temp[5] + "." + temp[3] + "." + temp[1], s);
        }
        return set;
    }

    public void getMissingCompanies() {
        Connection conn = null;
        Statement stmt = null;
        missingNameCode.keySet().removeAll(nameCode);
        String output = "";


        try {

            conn = DBHelper.getConnectionFromCertainDB("bcmglobalshares");
            stmt = conn.createStatement();
            ResultSet rs = null;
            String sql = "";
            for (String s : missingNameCode.values()) {
                sql = "SELECT companyId FROM sharecompany where shareclassid = '" + s.substring(0, s.indexOf(",")) + "'";
                rs = stmt.executeQuery(sql);
                if (rs.next()) {
                    output += rs.getString(1) + ",";
                } else {
                    output += "NULL,";

                }
                output += s + "\n";

            }
            if (rs != null) {
                rs.close();
            }
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



        FileHelper.writeToFile(output, "actualMissing.csv");
    }

    public void calculateAvgHistoryItems() throws ParseException {
        HashMap<String, ShareDetails> distribution = FileHelper.calculateDistribution("History.txt");
        int c1 = 0,
                c2 = 0,
                c3 = 0,
                c4 = 0,
                c5 = 0,
                c6 = 0,
                c7 = 0,
                c8 = 0,
                c9 = 0,
                c10 = 0,
                c11 = 0,
                c12 = 0,
                c13 = 0,
                c14 = 0,
                c15 = 0,
                c16 = 0,
                c17 = 0,
                c18 = 0;

//        for (String s : distribution.keySet()) {
//            System.out.println(s + ":" + distribution.get(s));
//            if (distribution.get(s).getItemAmount() < 100) {
//                c1++;
//            } else if (distribution.get(s).getItemAmount() < 200) {
//                c2++;
//            } else if (distribution.get(s).getItemAmount() < 300) {
//                c3++;
//            } else if (distribution.get(s).getItemAmount() < 400) {
//                c4++;
//            } else if (distribution.get(s).getItemAmount() < 500) {
//                c5++;
//            } else if (distribution.get(s).getItemAmount() < 600) {
//                c6++;
//            } else if (distribution.get(s).getItemAmount() < 700) {
//                c7++;
//            } else if (distribution.get(s).getItemAmount() < 900) {
//                c8++;
//            } else if (distribution.get(s).getItemAmount() < 1100) {
//                c9++;
//            } else if (distribution.get(s).getItemAmount() < 1300) {
//                c10++;
//            } else if (distribution.get(s).getItemAmount() < 1500) {
//                c11++;
//            } else if (distribution.get(s).getItemAmount() < 1700) {
//                c12++;
//            } else if (distribution.get(s).getItemAmount() < 2000) {
//                c13++;
//            } else if (distribution.get(s).getItemAmount() < 2300) {
//                c14++;
//            } else if (distribution.get(s).getItemAmount() < 2600) {
//                c15++;
//            } else if (distribution.get(s).getItemAmount() < 3000) {
//                c16++;
//            } else if (distribution.get(s).getItemAmount() < 3500) {
//                c17++;
//            } else {
//                c18++;
//            }
//        }



        for (String s : distribution.keySet()) {
            System.out.println(s + ":" + distribution.get(s));
            SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyyy");
            if (distribution.get(s).getLastUpdate().before(fmt.parse("01-01-2004"))) {
                c1++;
            } else if (distribution.get(s).getLastUpdate().before(fmt.parse("01-01-2005"))) {
                c2++;
            } else if (distribution.get(s).getLastUpdate().before(fmt.parse("01-01-2006"))) {
                c3++;
            } else if (distribution.get(s).getLastUpdate().before(fmt.parse("01-01-2007"))) {
                c4++;
            } else if (distribution.get(s).getLastUpdate().before(fmt.parse("01-01-2008"))) {
                c5++;
            } else if (distribution.get(s).getLastUpdate().before(fmt.parse("01-01-2009"))) {
                c6++;
            } else if (distribution.get(s).getLastUpdate().before(fmt.parse("01-01-2010"))) {
                c7++;
            } else if (distribution.get(s).getLastUpdate().before(fmt.parse("01-01-2011"))) {
                c8++;
            } else if (distribution.get(s).getLastUpdate().before(fmt.parse("01-01-2012"))) {
                c9++;
            } else if (distribution.get(s).getLastUpdate().before(fmt.parse("01-01-2013"))) {
                c10++;
            } else if (distribution.get(s).getLastUpdate().before(fmt.parse("01-01-2014"))) {
                c11++;
            } else if (distribution.get(s).getLastUpdate().before(fmt.parse("01-01-2015"))) {
                c12++;
            } else if (distribution.get(s).getLastUpdate().before(fmt.parse("01-01-2016"))) {
                c13++;
            } else {
                c14++;
            }
        }
        System.out.println("c1:" + c1);
        System.out.println("c2:" + c2);
        System.out.println("c3:" + c3);
        System.out.println("c4:" + c4);
        System.out.println("c5:" + c5);
        System.out.println("c6:" + c6);
        System.out.println("c7:" + c7);
        System.out.println("c8:" + c8);
        System.out.println("c9:" + c9);
        System.out.println("c10:" + c10);
        System.out.println("c11:" + c11);
        System.out.println("c12:" + c12);
        System.out.println("c13:" + c13);
        System.out.println("c14:" + c14);
        System.out.println("c15:" + c15);
        System.out.println("c16:" + c16);
        System.out.println("c17:" + c17);
        System.out.println("c18:" + c18);

    }

    public void getOverlap() {
        Connection conn = null;
        Statement stmt = null;
        ArrayList<String> overlap = new ArrayList<String>();

        for (String s : missingNameCode.keySet()) {
            if (nameCode.contains(s)) {
                overlap.add(s);
            }
        }
//        System.out.println(overlap.size());
//        System.out.println(nameCode.size());
//        System.out.println(missingNameCode.size());
        HashSet<String> set = new HashSet();
        HashSet<String> missingSet = new HashSet();
        try {

            conn = DBHelper.getConnectionFromCertainDB("bcmglobalshares");
            stmt = conn.createStatement();
            ResultSet rs = null;

            String sql;
            for (String s : overlap) {
//                System.out.println(s);

                String[] temp = s.split("\\.");
//                sql = "SELECT MorningstarPerformanceId FROM sharemanager where exchangecode = '" + temp[0] + "' and securitytype = '" + temp[1] + "' and tickersymbol = '" + temp[2] + "'";
                sql = "SELECT s.companyid FROM sharecompany s inner join sharemanager m where s.shareclassid = m.morningstarperformanceid and m.exchangecode = '" + temp[0] + "' and m.securitytype = '" + temp[1] + "' and m.tickersymbol = '" + temp[2] + "'";

                rs = stmt.executeQuery(sql);

                if (rs.next()) {
                    set.add(rs.getString(1));
                } else {
                    missingSet.add(s);

                }



            }
            if (rs != null) {
                rs.close();
            }
            stmt.close();
            conn.close();
            for (String code : set) {
                System.out.println(code);
            }
            System.out.println(set.size());
            for (String code : missingSet) {
                System.out.println(code);
            }
//            Main4 m = new Main4();

//        m.report();


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

    public void comparePBAndDailyPrice() {
        HashMap<String, ShareDetails> distribution = FileHelper.calculateDistribution("History.txt");
        HashMap<String, ShareDetails> translatedDistribution = new HashMap();
        HashMap<String, ShareDetails> failedToTranslatedDistribution = new HashMap();
        Connection conn = null;
        Statement stmt = null;


        try {
            conn = DBHelper.getConnectionFromCertainDB("bcmglobalshares");
            stmt = conn.createStatement();
            ResultSet rs = null;

            String sql;
            for (String s : distribution.keySet()) {
                String[] temp = s.split("\\.");
                sql = "SELECT MorningstarPerformanceId FROM sharemanager where exchangecode = '" + temp[0] + "' and securitytype = '" + temp[1] + "' and tickersymbol = '" + temp[2] + "'";
                rs = stmt.executeQuery(sql);
                if (rs.next()) {
                    translatedDistribution.put(rs.getString(1), distribution.get(s));
                } else {
//                    System.out.println(s);
                    failedToTranslatedDistribution.put(s, distribution.get(s));
                }
            }

//            System.out.println("translatedDistribution:" + translatedDistribution.size());
//            System.out.println("failedToTranslatedDistribution:" + failedToTranslatedDistribution.size());
            DateFormat format = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
            sql = "SELECT asofdate,shareClassID FROM valuationratio";
            rs = stmt.executeQuery(sql);


            while (rs.next()) {
                if (translatedDistribution.containsKey(rs.getString(2))) {
                    ShareDetails tmp = translatedDistribution.get(rs.getString(2));
                    Date date = format.parse(rs.getString(1));
                    if (tmp.getPBLastUpdate().after(date)) {
                        tmp.setPBLastUpdate(date);
                    }
                    if (tmp.getPBRecentUpdate().before(date)) {
                        tmp.setPBRecentUpdate(date);
                    }
                    tmp.PBitemAmoutAutoPlus();
                    translatedDistribution.put(rs.getString(2), tmp);
                }
            }
            StringBuffer output = new StringBuffer();
            output.append("CompanyID,ShareID,Daily price starts from, Price/Book value starts from\n");

            int counter = 0;
            for (String s : translatedDistribution.keySet()) {
                ShareDetails tmp = translatedDistribution.get(s);
//                System.out.println("ShareClassId: " + s + ", dailyPrice:" + format.format(tmp.getLastUpdate()) + ", starts:" + format.format(tmp.getPBLastUpdate()) + ", ends:" + format.format(tmp.getPBRecentUpdate()));
                if (tmp.getLastUpdate().after(format.parse("2006-01-01")) && tmp.getLastUpdate().after(tmp.getPBLastUpdate()) && !tmp.getPBLastUpdate().equals(format.parse("2015-02-02")) && !tmp.getPBRecentUpdate().equals(format.parse("1999-02-02"))) {
                    sql = "SELECT companyId from sharecompany where shareClassId = '" + s +"'";
                    rs = stmt.executeQuery(sql);
                    if(rs.next())
                    {
                        output.append(rs.getString(1) + "," +s + "," + format.format(tmp.getLastUpdate()) + "," + format.format(tmp.getPBLastUpdate()) + "\n");
                    }else
                    {
                        output.append("NULL," +s + "," + format.format(tmp.getLastUpdate()) + "," + format.format(tmp.getPBLastUpdate()) + "\n");
                    }
                    
                    counter++;
                }
            }
            System.out.println(counter);
            FileHelper.writeToFile(output.toString(), "comparison.csv");



            if (rs != null) {
                rs.close();
            }
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (ParseException ex) {
            Logger.getLogger(FileHelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();

        }
    }

    public void getAllNoneCompleteShares() {
        HashMap<String, ShareDetails> distribution = FileHelper.calculateDistribution("History.txt");
        StringBuffer output = new StringBuffer();
        output.append("Exchange Code,Security Type,Ticker,The earliest date\n");
        DateFormat format = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
        try {

            int counter = 0;
            for (String s : distribution.keySet()) {
                ShareDetails tmp = distribution.get(s);
                if (tmp.getLastUpdate().after(format.parse("2008-01-01"))) {
                    String[] symbols = s.split("\\.");
                    output.append(symbols[0] + "," + symbols[1] + "," + symbols[2] + "," + format.format(tmp.getLastUpdate()) + "\n");
                    counter++;
                }
            }
            System.out.println(counter);
            FileHelper.writeToFile(output.toString(), "noneCompleteShares.csv");


        } catch (ParseException ex) {
            Logger.getLogger(FileHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
