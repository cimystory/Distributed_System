package webservicedesignstyles3projectclient;

/**
 * The spy class
 * @author Gao
 */
public class Spy {
    
    // instance data for spies
    private String name;
    private String title;
    private String location;
    private String password;


    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    public String getLocation() {
        return location;
    }
    
    public String getName() {
        return name;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public Spy(String name, String title, String location, String password) {
        this.name = name;
        this.title = title;
        this.location = location;
        this.password = password;
    }
    
    public Spy(String name, String title, String location) {
        this.name = name;
        this.title = title;
        this.location = location;
        this.password = "";
    }
    
    public Spy(String name) {
        this.name = name;
        this.title = "";
        this.location = "";
        this.password = "";
    }

    public Spy() {
        this.name = "";
        this.title = "";
        this.location = "";
        this.password = "";

    }

    /**
     * Generate the XML format string for the information of a spy
     * @return the XML formatted string
     */
    public String toXML() {
        
        String xml = "";
        
        xml += "<spy>";
        xml += "\n";
        xml += "\t" + "<name>" + this.name + "</name>"; 
        xml += "\n";
        xml += "\t" + "<spyTitle>" + this.title + "</spyTitle>";
        xml += "\n";
        xml += "\t" + "<location>" + this.location + "</location>";
        xml += "\n";
        xml += "\t" + "<password>" + this.password + "</password>";
        xml += "\n";
        xml += "</spy>";
        xml += "\n";
        return xml;

    }
    
    @Override
    /**
     * override the toString method to get the formatted string text
     */
    public String toString() {
        String output = "";
        
        output += "Name: " + this.name + "\n";
        output += "Title: " + this.title + "\n";
        output += "Location: " + this.location + "\n";
        output += "Password: " + this.password + "\n";
        
        return output;
    }

    public static void main(String args[]) {
        Spy s = new Spy("james","spy", "Pittsburgh", "james");
        System.out.println(s);
    }
}
