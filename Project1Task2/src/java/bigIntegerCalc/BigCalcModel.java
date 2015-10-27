package bigIntegerCalc;

/*
 * @author Gao Jiang
 * 
 * This file is the Model component of the MVC, and it models the business
 * logic for the web application.  In this case, the business logic involves
 * doing different operations for the x and y.
 */
import java.math.BigInteger;

public class BigCalcModel {
    /**
     * This function is used to do addition
     * @param x Big integer x for calculation
     * @param y Big integer y for calculation
     * @return The sum of x and y 
     */
    public BigInteger doAddition (BigInteger x, BigInteger y) {
        return x.add(y);
    }
    
    /**
     * This function is used to do multiplication
     * @param x Big integer x for calculation
     * @param y Big integer y for calculation
     * @return The product of x and y 
     */
    public BigInteger doMultiply (BigInteger x, BigInteger y) {
        return x.multiply(y);
    }
    
    /**
     * This function is used to determine whether x and y is prime
     * @param x Big integer x for calculation
     * @param y Big integer y for calculation
     * @return 1 - is prime; 0 - is not prime
     */
    public int isRelativePrime (BigInteger x, BigInteger y) {
        int gcd = x.gcd(y).intValue();
        if(gcd == 1) 
            return 1;
        else
            return 0;
    }
    
    /**
     * This function is used to do mod
     * @param x Big integer x for calculation
     * @param y Big integer y for calculation
     * @return The mod of y to x 
     */
    public BigInteger doMod (BigInteger x, BigInteger y) {
        return x.mod(y);
    }
    
    /**
     * This function is used to do inverse mod
     * @param x Big integer x for calculation
     * @param y Big integer y for calculation
     * @return The inverse mod of y to x
     */
    public BigInteger doModInverse (BigInteger x, BigInteger y) {
        return x.modInverse(y);
    }
    
    /**
     * This function is used to do power
     * @param x Big integer x for calculation
     * @param y Big integer y for calculation
     * @return The result of x to y
     */
    public BigInteger doPower (BigInteger x, BigInteger y) {
        return x.pow(y.intValue());
    }
}
