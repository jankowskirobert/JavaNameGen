package namegenerator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by init0 on 19.11.14.
 */
public class ConfigXML {

    private boolean readFromFile;
    private Date lastSesion;
    private ArrayList<String> lastName = new ArrayList<String>();
    private Map<Integer, Date> date = new HashMap<Integer, Date>();
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isReadFromFile() {
        return readFromFile;
    }

    public void setReadFromFile(boolean readFromFile) {
        this.readFromFile = readFromFile;
    }

    public ArrayList<String> getLastName() {
        return lastName;
    }

    public void setLastName(ArrayList<String> lastName) {
        this.lastName = lastName;
    }

    public String getLastname(int i) {
        return lastName.get(i);
    }

    public void setLastname(String name) {
        this.lastName.add(name);
    }

    public Map<Integer, Date> getDate() {
        return date;
    }

//        public void setDate(Date date) {
//            this.date = date;
//        }

    public Date getDate(int i) {
        return date.get(i);
    }

    public void setDate(Date d, int i) {
        this.date.put(i, d);
    }

    public Date parseStringToDate(String input) {
        Date err = new Date(1939, 9, 1);
        SimpleDateFormat ft
                = //Tue, 11 Nov 14 21:56:05 +0100
                new SimpleDateFormat("EEE, dd MMM yy HH:mm:ss Z");
        try {
            Date date = ft.parse(input);
            return date;
        } catch (Exception e) {
            e.printStackTrace();
            return err;
        }

    }
    @Override
    public String toString(){
        String tmp;
        tmp = "XML Config File Overview";

        return tmp;
    }

}

