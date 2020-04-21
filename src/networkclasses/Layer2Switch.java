package networkclasses;

import java.util.ArrayList;

import networkutility.MACAddressI;

/**
 * Represents a layer 2 switch which maintains a list of MAC
 * addresses of hosts connected to it(including devices connected to the
 * WAP(indirect)). Allows direct connection to pcs and servers.
 * @author 1800329 
 *
 */
public class Layer2Switch extends Host implements MACAddressI {
    


    private final String hostType;



    private ArrayList<String> macAddressList; 

    public Layer2Switch(String hostType) {
        super(hostType);
        this.hostType=hostType;
        macAddressList =new ArrayList<>();
        System.out.println("Switch created.");
        
    }

    @Override
    public ArrayList<String> getMACAddressList() {
        return macAddressList;
    }

    @Override
    public void addMACAddress(String macAddress) {
        this.macAddressList.add(macAddress);
    }

    @Override
    public void removeMACAddress(String macAddress) {
        this.macAddressList.remove(macAddress);
    }
    

}
