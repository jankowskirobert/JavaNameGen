/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package namegenerator;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author init0
 */
/*
 Methods:
 1.Check cases
 2.Check name[space]surname
 3.Menu option -> checking point 2.
 4.Info about errors
 5.Generate!
 */
public class NameGeneratorFun {

    private ArrayList<String> list = new ArrayList<>();
    private String fileName;
    
    //only for test

    public NameGeneratorFun(String fileName) {
        pareNames(readFile("names.txt"));
        System.out.println("Wylosowalem: " + randName());
    }

    public String readFile(String filename) {
        String content = null;
        File file = new File(filename);
        try {
            FileReader reader = new FileReader(file);
            char[] chars = new char[(int) file.length()];
            reader.read(chars);
            content = new String(chars);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    public ArrayList<String> pareNames(String input) {

        String x[] = input.split("\n");
        for (String x1 : x) {
            list.add(x1);
        }
        Collections.sort(list); //sort by alpha
        for (String g : list) {
            System.out.println(g);
        }
        return list;
    }

    public String randName() {
        String tmp = "";
        Random rand = new Random();
        if(!list.isEmpty()){
        int randomNum = rand.nextInt((list.size() - 1) + 1) + 1;
        tmp = list.get(randomNum);
        }
        return tmp;
    }

}
