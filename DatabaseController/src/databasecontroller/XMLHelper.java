/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package databasecontroller;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 *
 * @author Phil
 */
public class XMLHelper {

    public static String getCompanyId(File inputFile) {
        try {
//            File inputFile = new File("E:\\BCM-World_New_checkout\\RealtimeDataDownload\\data\\Share\\warehouse\\0P0000000I.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            Element eElement = (Element) doc.getElementsByTagName("BasicInfo").item(0).getFirstChild();
            return eElement.getAttribute("companyId");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

//    public static String getShareName(String shareClassId) {
//        return "";
//    }
    public static void checkIntigrity(File inputFile) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(inputFile);
        doc.getDocumentElement().normalize();
    }
}
