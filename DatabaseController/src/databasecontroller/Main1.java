/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package databasecontroller;

import java.io.File;
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

/**
 *
 * @author Phil
 */
public class Main1 {

    public static void main(String[] args) {
        Connection conn1 = null;
        Connection conn = null;
        Statement stmt = null;
        Statement stmt1 = null;
        HashSet<String> nameCode = getUploadedCompanies();
        try {
            conn = DBHelper.getConnectionFromCertainDB("bcmglobalshares");
            conn1 = DBHelper.getConnectionFromCertainDB("testdatabase");
            stmt = conn.createStatement();
            stmt1 = conn1.createStatement();
            String sql = "SELECT MorningstarPerformanceId FROM sharemanager";
            String sql1 = "SELECT MorningstarPerformanceId FROM sharemanager";
            ResultSet rs = stmt.executeQuery(sql);
            ResultSet rs1 = stmt1.executeQuery(sql1);
            HashSet<String> set = new HashSet();
            while (rs.next()) {
                set.add(rs.getString(1));
            }
            while (rs1.next()) {
                set.remove(rs1.getString(1));
            }
            for(String code: set)
            {
                System.out.println(code);
            }

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

    public static Map getFiveKCompanies() {
        Map<String, String> nameCode = new HashMap<String, String>();
        for (String line : FileHelper.readFileToStringArray("5000company.txt")) {
            String[] colunms = new String[4];
            if (line.contains("\"")) {
                line = line.replace("\"", "");
                String[] temp = line.split(",");
                colunms[0] = line.substring(0, line.indexOf(",", line.indexOf(",") + 1));
                colunms[1] = temp[2];
                colunms[2] = temp[3];
                colunms[3] = temp[4];
            } else {
                colunms = line.split(",");
            }
            nameCode.put(colunms[1] + ".1." + colunms[2], colunms[0]);
        }
        return nameCode;
    }

    public static HashSet<String> getUploadedCompanies() {
        String[] line1 = FileHelper.readFileToStringArray("BUSTEC_ASIA_SYMBOLS.txt");
        String[] line2 = FileHelper.readFileToStringArray("BUSTEC_EUROPE_SYMBOLS.txt");
        String[] line3 = FileHelper.readFileToStringArray("BUSTEC_US_SYMBOLS.txt");
        ArrayList<String> arrayList1 = new ArrayList<String>(Arrays.asList(line1));
        ArrayList<String> arrayList2 = new ArrayList<String>(Arrays.asList(line2));
        ArrayList<String> arrayList3 = new ArrayList<String>(Arrays.asList(line3));
        HashSet<String> set = new HashSet();
        set.addAll(arrayList1);
        set.addAll(arrayList2);
        set.addAll(arrayList3);
        return set;
    }
}
