package ICCAPictures;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 
 * @author Gao
 * 
 * This class is the Model component of the MVC, and it models the business
 * logic for the web application.  In this case, the business logic involves
 * getting the image source url for a given subject.
 */
public class SearchPicturesModel {

    private String pictureTag; // The search string of the desired picture
    private String pictureURL; // The URL of the picture image

    /**
     * This method searches for the images for a given subject. It returns a
     * random page in a given subject and fit the pixel to the device type.
     * 
     * @param searchTag The given subject for the search.
     * @param deviceType The device type determining output pixels.
     * @return A string of the image source URL.
     */
    public String searchPictureUrl(String searchTag, String deviceType) {
        pictureTag = searchTag;
        String response;
        String urlListStr;
        String imageId;
        String[] idList;
        int randomIndex;
        
        // Create a URL for the desired page
        String accaURL = "http://digital.library.illinoisstate.edu/cdm/search/collection/icca/searchterm/" + searchTag + "*" + "/order/title";
        response = fetch(accaURL);
        
        // Substract the number of pictures represented in the page for a given subject
        int listValueLeft = response.indexOf("cdm_results_total");
        String valueStr = response.substring(listValueLeft);
        int listValueRight = valueStr.indexOf("\"", 26);
        int value = Integer.valueOf(valueStr.substring(26, listValueRight));
        
        // If the value is 0, there is no image for the given subject
        if (value == 0) {
            return null;
        } else {
            // Get the list of image id list
            int leftIndex = response.indexOf("id=\"cdm_results_list\"");
            urlListStr = response.substring(leftIndex);
            int rightIndex = urlListStr.indexOf("/\"");
            urlListStr = urlListStr.substring(29, rightIndex);

            urlListStr = urlListStr.replace("/icca", "\t").replace(">\t<", "\t");
            urlListStr = urlListStr.substring(5, urlListStr.length() - 1);
            idList = urlListStr.split("\t"); // A list of the image ids in a page for a given subject

            // Get a random value for showing the picture for a given subject
            randomIndex = 0 + (int)(Math.random()*(idList.length - 1));
            // Get the random image id
            imageId = idList[randomIndex];
            // Get the image height fitting for the device type; width - 800px for PC and width - 300 for mobile devices
            int height = getPictureHeight(deviceType, imageId);

            // Setting the picture URL according to device type
            if (deviceType.equalsIgnoreCase("mobile")) {
                pictureURL = "http://digital.library.illinoisstate.edu/utils/ajaxhelper/?CISOROOT=icca&CISOPTR=" + imageId + "&action=2&DMSCALE=56&DMWIDTH=300&DMHEIGHT=" + height + "&DMX=0&DMY=0&DMTEXT=birds&DMROTATE=0";

            } else {
                pictureURL = "http://digital.library.illinoisstate.edu/utils/ajaxhelper/?CISOROOT=icca&CISOPTR=" + imageId + "&action=2&DMSCALE=56&DMWIDTH=800&DMHEIGHT=" + height + "&DMX=0&DMY=0&DMTEXT=birds&DMROTATE=0";
            }
                return pictureURL;
            }
        }

        /**
         * This method calculates the corresponding height for the width in a given device type
         * @param deviceType The given device type
         * @param imageId The image id 
         * @return The appropriate height
         */
        public int getPictureHeight(String deviceType, String imageId) {
            String response;
            String imageUrl = "http://digital.library.illinoisstate.edu/cdm/singleitem/collection/icca/id/" + imageId + "/rec/1";
            response = fetch(imageUrl);

            // Get the original height of the image
            int heightLeftIndex = response.indexOf("cdm_item_height");
            int heightRightIndex = response.indexOf(" />", heightLeftIndex);
            String heightString = response.substring(heightLeftIndex, heightRightIndex);
            heightString = heightString.substring(24, heightString.length() - 1);
            int height = Integer.parseInt(heightString);

            // Get the original width of the image 
            int widthLeftIndex = response.indexOf("cdm_item_width");
            int widthRightIndex = response.indexOf(" />", widthLeftIndex);
            String widthString = response.substring(widthLeftIndex, widthRightIndex);
            widthString = widthString.substring(23, widthString.length() - 1);
            int width = Integer.parseInt(widthString);

            // Calculate the corresponding height and return 
            if (deviceType.equals("mobile")) {
                return (int)(height * (300.0 / width));
            } else {
                return (int)(height * (800.0 / width));
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
            try ( 
                // Read all the text returned by the server
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"))) {
                String str;
                // Read each line of "in" until done, adding each to "response"
                while ((str = in.readLine()) != null) {
                    // str is one line of text readLine() strips newline characters
                    response += str;
                }
            }
        } catch (IOException e) {
            System.out.println("Eeek, an exception: " + e.getMessage());
        }
        return response;
    }
}
