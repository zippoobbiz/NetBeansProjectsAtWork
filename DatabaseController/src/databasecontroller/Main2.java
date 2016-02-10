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
public class Main2 {

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = DBHelper.getConnectionFromCertainDB("bcmglobalshares");
            stmt = conn.createStatement();
            String sql = "SELECT MediumDescription FROM sharecompany";
            ResultSet rs = stmt.executeQuery(sql);
            HashSet<String> set = new HashSet();
            while (rs.next()) {
                set.add(rs.getString(1));
            }
            int c1 = 0, c2 = 0, c3 = 0, c4 = 0, c5 = 0, c6 = 0, c7 = 0;
            for (String code : set) {
                if (code != null) {
                    String[] temp = code.split(" ");
                    if (temp.length < 10) {
                        c1++;
                    } else if (temp.length < 20) {
                        c2++;
                    } else if (temp.length < 30) {
                        c3++;
                    } else if (temp.length < 40) {
                        c4++;
                    } else if (temp.length < 50) {
                        c5++;
                    } else if (temp.length >= 50) {
                        c6++;
                    } else {
                        c7++;
                    }
                }


            }
            System.out.println("c1:" + c1 + "\nc2:" + c2 + "\nc3:" + c3 + "\nc4:" + c4 + "\nc5:" + c5 + "\nc6:" + c6 + "\nc7:" + c7);
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
}
