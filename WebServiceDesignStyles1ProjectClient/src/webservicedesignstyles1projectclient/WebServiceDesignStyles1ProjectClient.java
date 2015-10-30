package webservicedesignstyles1projectclient;

/**
 * Client class for the web service
 * @author Gao
 */
public class WebServiceDesignStyles1ProjectClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        System.out.println(getList());
        System.out.println(getListAsXML());
        addSpy("mikem", "spy", "Pittsburgh", "sesame");
        addSpy("joem", "spy", "North Hills", "xyz");
        addSpy("seanb", "spy commander", "South Hills", "abcdefg");
        addSpy("jamesb", "spy", "Adelaide", "sydney");
        addSpy("adekunle", "spy", "Pittsburgh", "secret");
        System.out.println(getList());
        System.out.println(getListAsXML());
        updateSpy("mikem", "super spy", "Pittsburgh", "sesame");
        System.out.println(getListAsXML());
        String result = getSpy("jamesb");
        System.out.println(result);
        deleteSpy("jamesb");
        result = getSpy("jamesb");
        System.out.println(result);
        
        addSpy("Gao Jiang", "spy", "Pittsburgh", "123456");
        System.out.println(getList());
        System.out.println(getListAsXML());
         
    }

    private static String addSpy(java.lang.String name, java.lang.String title, java.lang.String location, java.lang.String password) {
        edu.cmu.andrew.gaoj.SpyService_Service service = new edu.cmu.andrew.gaoj.SpyService_Service();
        edu.cmu.andrew.gaoj.SpyService port = service.getSpyServicePort();
        return port.addSpy(name, title, location, password);
    }

    private static String deleteSpy(java.lang.String name) {
        edu.cmu.andrew.gaoj.SpyService_Service service = new edu.cmu.andrew.gaoj.SpyService_Service();
        edu.cmu.andrew.gaoj.SpyService port = service.getSpyServicePort();
        return port.deleteSpy(name);
    }

    private static String getList() {
        edu.cmu.andrew.gaoj.SpyService_Service service = new edu.cmu.andrew.gaoj.SpyService_Service();
        edu.cmu.andrew.gaoj.SpyService port = service.getSpyServicePort();
        return port.getList();
    }

    private static String getListAsXML() {
        edu.cmu.andrew.gaoj.SpyService_Service service = new edu.cmu.andrew.gaoj.SpyService_Service();
        edu.cmu.andrew.gaoj.SpyService port = service.getSpyServicePort();
        return port.getListAsXML();
    }

    private static String getSpy(java.lang.String name) {
        edu.cmu.andrew.gaoj.SpyService_Service service = new edu.cmu.andrew.gaoj.SpyService_Service();
        edu.cmu.andrew.gaoj.SpyService port = service.getSpyServicePort();
        return port.getSpy(name);
    }

    private static String updateSpy(java.lang.String name, java.lang.String title, java.lang.String location, java.lang.String password) {
        edu.cmu.andrew.gaoj.SpyService_Service service = new edu.cmu.andrew.gaoj.SpyService_Service();
        edu.cmu.andrew.gaoj.SpyService port = service.getSpyServicePort();
        return port.updateSpy(name, title, location, password);
    }
    
}
