package namegenerator;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by init0 on 19.11.14.
 */
public class ConfigXML {


    private boolean readFromFile;
    private Date lastSession;
    private ArrayList<String> lastName = new ArrayList<String>();
    private Map<Integer, Date> date = new HashMap<Integer, Date>();
    private String userName;
    private SimpleDateFormat ft = new SimpleDateFormat("E, d MMM yy HH:mm:ss Z", Locale.US); //Tue, 1 Nov 14 21:56:05 +0100 EXAMPLE
    public ConfigXML(){
        readFromFile = false;
        userName = "";
    }
    public Date getLastSession() {
        return lastSession;
    }

    public void setLastSession(Date lastSession) {
        this.lastSession = lastSession;
    }

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
        String errDate = "Tue, 1 Nov 00 06:06:06 +0100";
        Date err = new Date();
        try {

            err = (Date) ft.parseObject(errDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            Date date = (Date) ft.parseObject(input);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return err;
        }
    }
    public String parseDateToString(Date date){
        return ft.format(date);
    }
}

