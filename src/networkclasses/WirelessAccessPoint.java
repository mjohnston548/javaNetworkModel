
package networkclasses;

import java.util.ArrayList;
import networkutility.IPAddressStatic;
import networkutility.MACAddressI;

/**
 * Represents a Wireless access point. Maintains a list of
 * connected MAC addresses and allows connections from mobile devices.
 * @author 1800329 
 */
public class WirelessAccessPoint extends Host implements IPAddressStatic,MACAddressI {

    private ArrayList<String> macAddressList;
    private String ipAddress;
    private boolean ipAddressAcquired;
    private String macAddress;
    private final String hostType;
    

    public WirelessAccessPoint( String hostType) {
        super(hostType );
        this.hostType=hostType;
        
        macAddressList=new ArrayList<>();
        System.out.println("Wireless Access Point created.");
    }
    /**
     * Acquires a MAC and IP address for a server. If it already has one, this 
     * function will not acquire a new one.
     * @param numberOfNodes 
     */
    @Override
    public void getMACAddressAndStaticIPAddress( int numberOfNodes) {
        if (ipAddressAcquired) {
            System.out.println("This server has a static IP Address and cannot be changed.");

        } else {
            System.out.println(this.getName()+" Searching for a new IP Address");
            getMACAddressAndDynamicIPAddressAndName( numberOfNodes);
            ipAddressAcquired = true;

        }
    }
    
    @Override
    public ArrayList<String> getMACAddressList(){
        return macAddressList;
    }
    @Override
    public void addMACAddress(String macAddress){
        this.macAddressList.add(macAddress);
    }
    @Override
    public void removeMACAddress(String macAddress){
        this.macAddressList.remove(macAddress);
    }


}
