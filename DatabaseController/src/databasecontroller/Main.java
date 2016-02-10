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
public class Main {

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        HashSet<String> nameCode = getUploadedCompanies();
        try {
            conn = DBHelper.getConnectionFromCertainDB("bcmglobalshares");
            stmt = conn.createStatement();
            String sql = "SELECT ExchangeCode,SecurityType,TickerSymbol FROM sharemanager";
            ResultSet rs = stmt.executeQuery(sql);
            int counter = 0;
            while (rs.next()) {
                if (nameCode.contains(rs.getString(1) + "." + rs.getString(2) + "." + rs.getString(3))) {
                    nameCode.remove(rs.getString(1) + "." + rs.getString(2) + "." + rs.getString(3));
                } else {
                    System.out.println(rs.getString(1) + "." + rs.getString(2) + "." + rs.getString(3));
                }

            }
            StringBuffer html = new StringBuffer("<html><head><title>Missing countries</title><link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css\" integrity=\"sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7\" crossorigin=\"anonymous\"></head><body><table align=\"center\"><thead><tr><th>Country</th><th>Exchange Code</th><th>Security Type</th><th>Ticker Symbol</th></tr></thead>");
            int c = 0, d = 0;
            Map<String, String> a = getFiveKCompanies();
            for (String code : nameCode) {
                if (a.containsKey(code)) {
                    c++;
                } else {
                    d++;
                    System.out.println(code);
                }
                html.append("<tr><td >");
                html.append(a.get(code));
                html.append("</td><td align=\"center\">");
                html.append(code.substring(0,code.indexOf(".")));
                html.append("</td><td align=\"center\">");
                html.append(code.substring(code.indexOf(".")+1,code.indexOf(".",code.indexOf(".")+1)));
                html.append("</td><td align=\"center\">");
                html.append(code.substring(code.indexOf(".",code.indexOf(".")+1)+1));
                html.append("</td></tr>");

            }
            html.append("</table></body></html>");
            System.out.println("c:" + c);
            System.out.println("d:" + d);
            FileHelper.writeToFile(html.toString(), "new.html");
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
