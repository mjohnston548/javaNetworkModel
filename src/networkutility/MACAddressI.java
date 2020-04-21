
package networkutility;

import java.util.ArrayList;

/**
 *
 * @author 1800329 
 * For use by network infrastructure objects to maintain MAC
 * address lists of connected hosts. This also allows hosts to be connected to
 * network devices by providing an umbrella type for which they can be referred 
 * to.
 */
public interface MACAddressI {

    ArrayList<String> getMACAddressList();

    void addMACAddress(String macAddress);

    void removeMACAddress(String macAddress);
}
