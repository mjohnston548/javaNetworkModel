package networkclasses;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import networkutility.IPAddressDynamicI;
import networkutility.MACAddressI;

/**
 * Represents an abstract device on the network which may connect to a wireless
 * access point or switch.
 *
 * @author 1800329
 */
public abstract class Host implements IPAddressDynamicI {

    private String macAddress;
    private boolean macAddressAcquired;
    private String ipAddress;
    private String name;
    public static ArrayList<Integer> occupiedIdList = new ArrayList<>();

    //This string must be written the same way it is written as in the 
    //nodetype field in the database, same for the constructors nodetype
    private String hostType;

    public Host(String hostType) {

        this.hostType = hostType;
        macAddressAcquired = false;
        this.getMACAddressAndDynamicIPAddressAndName(networkmodel.NetworkModel.nodeTypeCounter(hostType));
    }

    /**
     *
     * @param numberOfNodes Gets an IP Address from the database (at random)
     *
     */
    @Override
    public void getMACAddressAndDynamicIPAddressAndName(int numberOfNodes) {

        Connection connection = null;
        try {
            // create a database connection
            connection = DriverManager.getConnection(networkmodel.NetworkModel.databasePath);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            //Finds the database rows with nodetype matching this functions argument
            ResultSet rs = statement.executeQuery("select id, macaddress, ipaddress, nodetype,nodename from nwdevices2020 where nodetype=\'" + hostType + "\'");

            //Step through the result set by a random number of steps picking a
            //row which has not previously been picked
            boolean ipFound = false;
            while (ipFound == false) {
                for (int i = 0; i < ThreadLocalRandom.current().nextInt(1, numberOfNodes + 1); i++) {
                    rs.next();

                }
                if (occupiedIdList.contains(rs.getInt(1))) {
                } else {
                    ipFound = true;
                }
            }

            //Found the winning row now take values from it
            ipAddress = rs.getString(3);
            //add the corresponding id to a list to keep track of taken ip addresses, ids etc
            occupiedIdList.add(rs.getInt(1));
            //set host name too
            name = rs.getString("nodename");

            //while we're here we might as well assign the corresponding MAC Address too
            //MAC Address will be acquired if if the object doesnt already have one
            //if this function is called more than once on an object we will be mixing up database row-details
            if (!macAddressAcquired) {
                macAddress = rs.getString(2);
                macAddressAcquired = true;
            }

        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

    }

    /**
     *
     * @param <T>
     * @param infraStructObj Passes the mac address of the first parameter's
     * type to the mac address list of the second parameter's type. This allows
     * the wap and layer2switch to keep track of the connected hosts. Also
     * ensures that the layer2switch accepts a maximum of 48 connections.
     */
    public <T extends MACAddressI> void connectToInfrastucture(T infraStructObj) {
        //if the object which wants to connect is a wap, pass mac address list and own mac address
        infraStructObj.addMACAddress(this.macAddress);
        if (this instanceof WirelessAccessPoint) {
            //get mac address list from wap
            ArrayList<String> macAddList = ((WirelessAccessPoint) this).getMACAddressList();
            //iterate through the mac address list the wap is holding and pass them to the switch
            for (String string : macAddList) {
                infraStructObj.addMACAddress(string);
            }

        }

    }

    /**
     *
     * @param <T>
     * @param infraStructObj Removes a mac address from a network infrastructure
     * device
     */
    public < T extends MACAddressI> void disconnectFromInfrastucture(T infraStructObj) {
        infraStructObj.removeMACAddress(this.macAddress);
    }

    public String getIPAddress() {
        return ipAddress;
    }

    public String getHostType() {
        return hostType;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public String getName() {
        return name;
    }

}
