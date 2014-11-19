/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package namegenerator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author init0 
 * Singleton
 */
public class ReadingXMLconfig {

    private static ReadingXMLconfig instance = new ReadingXMLconfig();
    private File xmlConfig;
    private BufferedReader br;
    
    private ReadingXMLconfig(){
        xmlConfig = new File("config.xml");
        try {
            br = new BufferedReader(new FileReader(xmlConfig.getAbsolutePath()));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReadingXMLconfig.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static ReadingXMLconfig getInstance(){
        return instance;
    }
    
    public boolean isConfig() {
        if (xmlConfig.exists()) {
            try {
                //int b = fis.read();
                if (br.readLine() == null) {
                    System.out.println("!!File " + xmlConfig.getName() + " empty!!");
                    return false;
                } else {
                    return true;
                }
            } catch (IOException ex) {
                Logger.getLogger(ReadingXMLconfig.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Zastanowić się nad zwracaniem i sensem metody Dodać czytanie noda
     * <generator>
     * dodać funkcje createXML oraz modyfiy jesli sie da
     *
     * @return ConfigXML
     */
    public ArrayList<ConfigXML> readXML() {
        ArrayList<ConfigXML> tmp = new ArrayList<ConfigXML>();// = new ConfigXML();
        if (isConfig()) {
            try {
                DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                Document doc2 = builder.parse(xmlConfig);
                NodeList nodes = doc2.getElementsByTagName("user");

                for (int i = 0; i < nodes.getLength(); i++) {
                    tmp.add(i, new ConfigXML());
                    Node nNode = nodes.item(i);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        System.out.println("_______________________");
                        Element eElement = (Element) nNode;

                        tmp.get(i).setUserName(eElement.getAttribute("username"));

                        tmp.get(i).setReadFromFile(Boolean.valueOf(
                                eElement.getElementsByTagName("readfromfile").
                                item(0).getTextContent()));

                        NodeList nList2 = eElement.getElementsByTagName("lastname");
                        System.out.println("----------------------------");
                        for (int temp = 0; temp < nList2.getLength(); temp++) {
                            Node nNode2 = nList2.item(temp);

                            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                                Element eElement2 = (Element) nNode2;
                                tmp.get(i).setLastname(eElement2.getAttribute("name"));
                                tmp.get(i).setDate(tmp.get(i).parseStringToDate(eElement2
                                        .getElementsByTagName("date")
                                        .item(0)
                                        .getTextContent()), temp);
                            }
                        }
                    }
                }
            } catch (Exception ex) {
                Logger.getLogger(ReadingXMLconfig.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return tmp;
    }

    public void createXML(ArrayList<ConfigXML> tmp) {
        
    }

    public static void main(String[] args) {
        ReadingXMLconfig xml = new ReadingXMLconfig().getInstance();
        xml.readXML().get(0);
        boolean test = xml.isConfig();
        if (test) {

            System.out.println("TAK");
        } else {
            System.out.println("NIE");
        }
    }


}
