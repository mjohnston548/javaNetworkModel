
package networkinfrastructure;

import networkclasses.Host;
import networkutility.IPAddressStatic;

/**
 * Represents a router. Maintains a routing table ie an inner network address
 * and outer network address.
 * @author 1800329
 * 
 */
public class Router extends Host implements IPAddressStatic {

    private boolean ipAddressAquired;

    private String internetFacingIPAddress;
    
    
    
    public Router(String hostType) {
        super(hostType);
        
    }
    /**
     * Acquires a MAC and IP address for a server. If it already has one, this 
     * function will not acquire a new one.
     * @param numberOfNodes 
     */
    @Override
    public void getMACAddressAndStaticIPAddress(int numberOfNodes) {
        if (ipAddressAquired) {
            System.out.println("This device has a static IP Address and cannot be changed.");

        } else {
            System.out.println("Searching for a new IP Address");
            getMACAddressAndDynamicIPAddressAndName(numberOfNodes);
            ipAddressAquired = true;

        }
    }

    public String getInternetFacingIPAddress() {
        return internetFacingIPAddress;
    }

    public void setInternetFacingIPAddress(String internetFacingIPAddress) {
        this.internetFacingIPAddress = internetFacingIPAddress;
    }
    
    
    
    
    
}
