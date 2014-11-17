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
import java.util.ArrayList;
import java.util.Date;
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
     * @return XMLconfig
     */
    public ArrayList<XMLconfig> readXML() {
        ArrayList<XMLconfig> tmp = new ArrayList<>();// = new XMLconfig();
        if (isConfig()) {
            try {
                DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                Document doc2 = builder.parse(xmlConfig);
                NodeList nodes = doc2.getElementsByTagName("user");

                for (int i = 0; i < nodes.getLength(); i++) {
                    tmp.add(i, new XMLconfig());
                    Node nNode = nodes.item(i);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        System.out.println("_______________________");
                        Element eElement = (Element) nNode;
                        System.out.println("User Name:" + eElement.getAttribute("username"));
                        tmp.get(i).setReadFromFile(Boolean.valueOf(eElement.getElementsByTagName("readfromfile").item(0).getTextContent()));

                        NodeList nList2 = eElement.getElementsByTagName("lastname");
                        System.out.println("----------------------------");
                        for (int temp = 0; temp < nList2.getLength(); temp++) {
                            Node nNode2 = nList2.item(temp);
                            System.out.println("\nCurrent Element :"
                                    + nNode2.getNodeName());
                            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                                Element eElement2 = (Element) nNode2;
                                System.out.println("First Name : "
                                        + eElement2
                                        .getElementsByTagName("date")
                                        .item(0)
                                        .getTextContent());
                                System.out.println("First Name2 : "
                                        + eElement2.getAttribute("name")
                                );
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

    public void createXML(ArrayList<XMLconfig> tmp) {
        

        
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

    class XMLconfig {

        private boolean readFromFile;
        private Date date;
        private ArrayList<String> lastname;

        public boolean isReadFromFile() {
            return readFromFile;
        }

        public void setReadFromFile(boolean readFromFile) {
            this.readFromFile = readFromFile;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public String getLastRandName(final int i) {
            return lastname.get(i);
        }

        public void addLastRandName(final int i, String lastRandName) {
            lastname.add(i, lastRandName);
        }

        public void addLastRandName(String name) {
            lastname.add(name);
        }

        public void setLastRandName(final int i, String name) {
            lastname.set(i, name);
        }

    }
}
