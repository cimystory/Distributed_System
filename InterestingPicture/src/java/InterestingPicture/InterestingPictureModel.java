package InterestingPicture;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class InterestingPictureModel {

    private String pictureTag; // The search string of the desired picture
    private String pictureURL; // The URL of the picture image

    public String searchPictureUrl(String searchTag, String deviceType) {
        pictureTag = searchTag;
        String response;
        String urlListStr;
        String imageId;
        String[] idList;
        int randomIndex;
        // Create a URL for the desired page
        String flickrURL = "http://digital.library.illinoisstate.edu/cdm/search/collection/icca/searchterm/" + searchTag + "/order/title";
        response = fetch(flickrURL);
        
        int leftIndex = response.indexOf("id=\"cdm_results_list\"");
        urlListStr = response.substring(leftIndex);

        int rightIndex = urlListStr.indexOf("/\"");
        urlListStr = urlListStr.substring(29, rightIndex);
        //System.out.println("********Total Response " + urlListStr);

        
        urlListStr = urlListStr.replace("/icca", "\t").replace(">\t<", "\t");
        urlListStr = urlListStr.substring(5, urlListStr.length() - 1);
        idList = urlListStr.split("\t");
        
        randomIndex = 0 + (int)(Math.random()*(idList.length - 1));

        imageId = idList[randomIndex];
        System.out.println("imageId = " + imageId);
        
        String imageUrl = "http://digital.library.illinoisstate.edu/cdm/singleitem/collection/icca/id/" + imageId + "/rec/1";
        response = fetch(imageUrl);
        
        int heightLeftIndex = response.indexOf("cdm_item_height");
        int heightRightIndex = response.indexOf(" />", heightLeftIndex);
        String heightString = response.substring(heightLeftIndex, heightRightIndex);
        heightString = heightString.substring(24, heightString.length() - 1);
        int height = Integer.parseInt(heightString);
        
        int widthLeftIndex = response.indexOf("cdm_item_width");
        int widthRightIndex = response.indexOf(" />", widthLeftIndex);
        String widthString = response.substring(widthLeftIndex, widthRightIndex);
        widthString = widthString.substring(23, widthString.length() - 1);
        int width = Integer.parseInt(widthString);
        
        width = getPictureWidth(deviceType, height, width);
        
        if (deviceType.equalsIgnoreCase("mobile")) {
            pictureURL = "http://digital.library.illinoisstate.edu/utils/ajaxhelper/?CISOROOT=icca&CISOPTR=" + imageId + "&action=2&DMSCALE=56&DMWIDTH=" + width + "&DMHEIGHT=300&DMX=0&DMY=0&DMTEXT=birds&DMROTATE=0";
    
        } else {
            pictureURL = "http://digital.library.illinoisstate.edu/utils/ajaxhelper/?CISOROOT=icca&CISOPTR=" + imageId + "&action=2&DMSCALE=56&DMWIDTH=" + width + "&DMHEIGHT=800&DMX=0&DMY=0&DMTEXT=birds&DMROTATE=0";
        }
        
        return pictureURL;
    }

    
    public int getPictureWidth(String picsize, int height, int width) {
        if (picsize.equals("mobile")) {
            return (int)(width * (300.0 / height));
        } else {
            return (int)(width * (800.0 / height));
        }
    }

    /*
     * Return the picture tag.  I.e. the search string.
     * 
     * @return The tag that was used to search for the current picture.
     */
    public String getPictureTag() {
        return (pictureTag);
    }

    /*
     * Make an HTTP request to a given URL
     * 
     * @param urlString The URL of the request
     * @return A string of the response from the HTTP GET.  This is identical
     * to what would be returned from using curl on the command line.
     */
    private String fetch(String urlString) {
        String response = "";
        try {
            URL url = new URL(urlString);
            /*
             * Create an HttpURLConnection.  This is useful for setting headers
             * and for getting the path of the resource that is returned (which 
             * may be different than the URL above if redirected).
             * HttpsURLConnection (with an "s") can be used if required by the site.
             */
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // Read all the text returned by the server
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String str;
            // Read each line of "in" until done, adding each to "response"
            while ((str = in.readLine()) != null) {
                // str is one line of text readLine() strips newline characters
                response += str;
            }
            in.close();
        } catch (IOException e) {
            System.out.println("Eeek, an exception");
            // Do something reasonable.  This is left for students to do.
        }
        System.out.println("response = " + response);
        return response;
    }
}
