
package networkutility;

/**
 * Classes which implement this interface acquire IP addresses dynamically, 
 * and can do so repeatedly.
 * @author 1800329
 */
public interface IPAddressDynamicI {
    
    void getMACAddressAndDynamicIPAddressAndName( int numberOfNodes);
    
}
