/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package namegenerator;

import java.util.ArrayList;

/**
 *
 * @author rrjj
 */
public class nameGeneratorConsole{
    public static void main(String[] args) {
         ReadingXMLconfig xml = ReadingXMLconfig.getInstance();
         ArrayList<ConfigXML> array = xml.readXML();
        xml.createXML(array);
        for (ConfigXML array1 : array) {
            //System.out.printf("%10s %n",array1.toString());
            System.out.println("");
        }
        boolean test = xml.isConfig();
        if (test) {

            System.out.println("TAK");
        } else {
            System.out.println("NIE");
        }
        System.gc();
    }
}
