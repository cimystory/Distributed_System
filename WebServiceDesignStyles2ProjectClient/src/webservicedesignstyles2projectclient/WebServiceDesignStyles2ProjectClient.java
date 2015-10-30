package webservicedesignstyles2projectclient;

/**
 * A class implements the client of the web service
 * 
 * @author Gao
 */
public class WebServiceDesignStyles2ProjectClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String result = "";
        
        System.out.println("Adding spy jamesb");
        Spy bond = new Spy("jamesb", "spy", "London","james");
        SpyMessage sb = new SpyMessage(bond,"addSpy");
        result = spyOperation(sb.toXML());
        System.out.println(result);

        System.out.println("Adding spy seanb");
        Spy beggs = new Spy("seanb", "spy master", "Pittsburgh","sean");
        SpyMessage ss = new SpyMessage(beggs,"addSpy");
        result = spyOperation(ss.toXML());
        System.out.println(result);

        System.out.println("Adding spy joem");
        Spy mertz = new Spy("joem", "spy", "Los Angeles","joe");
        SpyMessage sj = new SpyMessage(mertz,"addSpy");
        result = spyOperation(sj.toXML());
        System.out.println(result);

        System.out.println("Adding spy mikem");
        Spy mccarthy = new Spy("mikem", "spy", "Ocean City Maryland","sesame");
        SpyMessage sm = new SpyMessage(mccarthy,"addSpy");
        result = spyOperation(sm.toXML());
        System.out.println(result);

        System.out.println("Displaying spy list");
        SpyMessage list = new SpyMessage(new Spy(),"getList");
        result = spyOperation(list.toXML());
        System.out.println(result);

        System.out.println("Displaying spy list as XML");
        SpyMessage listXML = new SpyMessage(new Spy(),"getListAsXML");
        result = spyOperation(listXML.toXML());
        System.out.println(result);

        System.out.println("Updating spy jamesb");
        Spy newJames = new Spy("jamesb","Cool Spy","New Jersey","sesame");
        SpyMessage um = new SpyMessage(newJames,"updateSpy");
        result = spyOperation(um.toXML());
        System.out.println(result);

        System.out.println("Displaying spy list");
        list = new SpyMessage(new Spy(),"getList");
        result = spyOperation(list.toXML());
        System.out.println(result);

        System.out.println("Deleting spy jamesb");
        Spy james = new Spy("jamesb");
        SpyMessage dm = new SpyMessage(james,"deleteSpy");
        result = spyOperation(dm.toXML());
        System.out.println(result);

        System.out.println("Displaying spy list");
        list = new SpyMessage(new Spy(),"getList");
        result = spyOperation(list.toXML());
        System.out.println(result);

        System.out.println("Displaying spy list as XML");
        listXML = new SpyMessage(new Spy(),"getListAsXML");
        result = spyOperation(listXML.toXML());
        System.out.println(result);


        System.out.println("Deleting spy Amos");
        Spy amos = new Spy("amos");
        SpyMessage am = new SpyMessage(amos,"deleteSpy");
        result = spyOperation(am.toXML());
        System.out.println(result);
        
        System.out.println("Adding spy Gao Jiang");
        Spy gaoj = new Spy("Gao Jiang", "spy", "Pittsburgh","123456");
        SpyMessage gm = new SpyMessage(gaoj,"addSpy");
        result = spyOperation(gm.toXML());
        System.out.println(result);
        
        System.out.println("Displaying spy list after adding Gao Jiang");
        list = new SpyMessage(new Spy(),"getList");
        result = spyOperation(list.toXML());
        System.out.println(result);

        System.out.println("Displaying spy list as XML after adding Gao Jiang");
        listXML = new SpyMessage(new Spy(),"getListAsXML");
        result = spyOperation(listXML.toXML());
        System.out.println(result);
        
        
           
    }

    private static String spyOperation(java.lang.String xmlString) {
        cmu.edu.andrew.gaoj.SpyMessageService_Service service = new cmu.edu.andrew.gaoj.SpyMessageService_Service();
        cmu.edu.andrew.gaoj.SpyMessageService port = service.getSpyMessageServicePort();
        return port.spyOperation(xmlString);
    }
    
}
