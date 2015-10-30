package cmu.edu.andrew.gaoj;

/**
 * Spy list class containing a list of all the spy
 * 
 * @author Gao
 */
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class SpyList {
    
    private Map tree = new TreeMap();

    private static SpyList spyList = new SpyList();

    SpyList() {
    }

    public static SpyList getInstance() {
       return spyList;
    }

    public void add(Spy s) {
       tree.put(s.getName(), s);
    }

    public Object delete(Spy s) {
       return tree.remove(s.getName());
    }

    public Spy get(String userID) {
       return (Spy) tree.get(userID);
    }

    public Collection getList() {
       return tree.values();
    }

    /**
     * Transfer all the information of the spies in a plain text string
     * 
     * @return a string of all the information of the spies
     */
    public String toString() {

       StringBuffer representation = new StringBuffer();
       Collection c = getList();
       Iterator sl = c.iterator();

       while(sl.hasNext()) {
           Spy spy = (Spy)sl.next();
           representation.append("Name: " + spy.getName()+" Title: " + spy.getTitle()+
           " Location: " + spy.getLocation() + "\n");
       }

       return representation.toString();
    }

    /**
     * Generate a xml formatted string containing the information of all the spies
     * 
     * @return a xml formatted string 
     */
    public String toXML() {
       StringBuffer xml = new StringBuffer();
       xml.append("<spylist>\n");

       Collection c = getList();
       Iterator sl = c.iterator();
       while(sl.hasNext()) {
           Spy spy = (Spy)sl.next();
           xml.append(spy.toXML());
       }
       // Now, close
       xml.append("</spylist>");

       System.out.println("Spy list: " + xml.toString());
       return xml.toString();
    }
 
}
