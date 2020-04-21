package networkclasses;

import java.util.ArrayList;

import networkutility.IPAddressStatic;

/**
 * Represents a general server.
 * @author 1800329   
 */
public abstract class Server extends Host implements IPAddressStatic {

    private ArrayList<Integer> openPortNumbers = new ArrayList<>();
    private static int[] initialPortNumbers; //used in constructors of specialised servers.

    private boolean ipAddressAquired;
    private boolean onlineStatus;
    private String ipAddress;
    private final String hostType;

    public Server(String hostType) {
        super(hostType);
        this.hostType=hostType;
        
        ipAddressAquired = false;
        openPortNumbers = new ArrayList<>();
    }
    /**
     * Acquires a MAC and IP address for a server. If it already has one, this 
     * function will not acquire a new one.
     * @param numberOfNodes 
     */
    @Override
    public void getMACAddressAndStaticIPAddress(int numberOfNodes) {

        if (ipAddressAquired) {
            System.out.println("This server has a static IP Address and cannot be changed.");

        } else {
            System.out.println(this.getName()+" Searching for a new IP Address");
            getMACAddressAndDynamicIPAddressAndName(numberOfNodes);
            ipAddressAquired = true;

        }

    }

    public String getStatus() {
        if (onlineStatus) {
            return "This server is online.";
        } else {
            return "This server is offline.";
        }

    }

    public void setStatus(boolean status) {
        this.onlineStatus = status;
    }

    public ArrayList<Integer> getOpenPortNumbers() {
        return openPortNumbers;
    }

    public void addPortNumber(Integer portNumber) {
        openPortNumbers.add(portNumber);
    }

    public void removePortNumber(Integer portNumber) {
        openPortNumbers.remove(portNumber);
    }

    public int[] getInitialPortNumbers() {
        return initialPortNumbers;
    }

    public void setInitialPortNumbers(int[] ports) {
        initialPortNumbers = ports;
    }
    
    public void connectToInfrastucture(Layer2Switch activeSwitch){
        activeSwitch.addMACAddress(this.getMacAddress());
    }

}
