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
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Attr;
import org.xml.sax.SAXException;

/**
 *
 * @author init0 
 * Singleton
 */
public class ReadingXMLconfig extends ConfigXML {

    private static final String fileName = "config.xml";
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
                    tmp.add(new ConfigXML());
                    Node nNode = nodes.item(i);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                        Element eElement = (Element) nNode;
                        ConfigXML tmpObj = tmp.get(i);

                        //add user name to object
                        tmpObj.setUserName(eElement.getAttribute("username"));

                        //add decision about loading names from external source
                        boolean test = Boolean.valueOf(returnE(eElement, "readnamesfromfile").toLowerCase());
                        tmpObj.setReadFromFile(test);

                        //add last saved session
                        tmpObj.setLastSession(super.parseStringToDate(returnE(eElement, "lastsession")));

                        NodeList nList2 = eElement.getElementsByTagName("generator");
                        NodeList nList3 = ((Element)nList2.item(0)).getElementsByTagName("lastName");

                        for (int temp = 0; temp < nList3.getLength(); temp++) {
                            Node nNode2 = nList3.item(temp);

                            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                                Element eElement2 = (Element) nNode2;

                                tmp.get(i).setLastname(eElement2.getAttribute("name"));

                                tmp.get(i).setDate(super.parseStringToDate(returnE(eElement2,"date")), temp);

                            }
                        }
                    }
                }
            } catch (NullPointerException ex/* :| so bad... */) {
                Logger.getLogger(ReadingXMLconfig.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return tmp;
    }

    public void createXML(ArrayList<ConfigXML> tmp) {

        try{

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuild = dbFactory.newDocumentBuilder();
            Document doc = dBuild.newDocument();

            /*Main element*/
            Element rootE = doc.createElement("settings");
            doc.appendChild(rootE);
            for (int i = 0 ; i < tmp.size() ; i++) {

                ConfigXML temConf = tmp.get(i);

                Element userE = doc.createElement("user");
                Attr userElementAttr = doc.createAttribute("username");
                userElementAttr.setValue(temConf.getUserName());
                userE.setAttributeNode(userElementAttr);
                rootE.appendChild(userE);

                Element lastSessionE = doc.createElement("lastsession");
                lastSessionE.appendChild(doc.createTextNode(String.valueOf(temConf.getLastSession())));
                userE.appendChild(lastSessionE);

                Element readFromE = doc.createElement("readnamesfromfile");
                readFromE.appendChild(doc.createTextNode(String.valueOf(temConf.isReadFromFile())));
                userE.appendChild(readFromE);

                Element generatorE = doc.createElement("generator");

                for (int j = 0 ; j < temConf.getLastName().size() ; j++) {

                    Element lastNameE = doc.createElement("lastName");
                    Attr lastNameAttr = doc.createAttribute("name");
                    lastNameAttr.setValue(temConf.getLastname(j));
                    lastNameE.setAttributeNode(lastNameAttr);
                    generatorE.appendChild(lastNameE);

                    Element dateE = doc.createElement("date");
                    dateE.appendChild(doc.createTextNode(String.valueOf(temConf.getDate(j))));
                    lastNameE.appendChild(dateE);
                    userE.appendChild(generatorE);
                }

            }

            TransformerFactory transformerFactory =
                    TransformerFactory.newInstance();
            Transformer transformer =
                    transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result =
                    new StreamResult(new File("text.xml"));
            transformer.transform(source, result);
            // Output to console for testing
            StreamResult consoleResult =
                    new StreamResult(System.out);
            transformer.transform(source, consoleResult);

        }catch (NullPointerException x /*BADDDDD*/){
            System.err.println(""+x);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }

    }
    public String returnE(Element e, String node){
        return e.getElementsByTagName(node).item(0).getTextContent();
    }


    public static void main(String[] args) {
        ReadingXMLconfig xml = new ReadingXMLconfig().getInstance();
//        xml.readXML();
//        boolean test = xml.isConfig();
//        if (test) {
//
//            System.out.println("TAK");
//        } else {
//            System.out.println("NIE");
//        }
    }


}
