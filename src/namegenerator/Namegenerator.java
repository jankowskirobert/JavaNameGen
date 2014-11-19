/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package namegenerator;

import java.io.IOException;

/**
 *
 * @author rrjj
 */
public class Namegenerator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        
        new NameGeneratorView().setVisible(true);
       // new NameGeneratorFun().fileMethod();
        //new NameGeneratorFun("names.txt");
    }
    
}
