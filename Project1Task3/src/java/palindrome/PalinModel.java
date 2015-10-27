/*
 * @author Gao Jiang
 * 
 * This file is the Model component of the MVC, and it models the business
 * logic for the web application.  In this case, the business logic involves
 * determining whether an input string is palindrome.
 */
package palindrome;

public class PalinModel {
    /**
     * This function is used to determine whether a string is palindrome
     * @param input string to be determined
     * @return true - is palindrome; false - is not palindrome
     */
    public boolean isPalin (String input) {
        // ingore those that are not letters
        String strippedInput = input.replaceAll("[^a-zA-Z0-9]", "");
        // if palindrome, string.reverse == string
        return strippedInput.equalsIgnoreCase(new StringBuilder(strippedInput).reverse().toString());
    }
}
