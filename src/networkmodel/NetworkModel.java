package networkmodel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Objects;
import networkclasses.Host;
import networkclasses.MobileDevice;
import networkclasses.PC;
import networkclasses.Layer2Switch;
import networkinfrastructure.Router;
import networkclasses.Server;
import networkclasses.WirelessAccessPoint;
import networkclasses.DatabaseServer;
import networkclasses.DomainController;
import networkclasses.FileServer;
import networkclasses.WebServer;

/**
 *
 * @author 1800329
 */
public class NetworkModel {

    public static String databasePath = "jdbc:sqlite:F:\\collegeUSBGoogleDriveSync\\year2\\objectOrientedDesign\\NetworkModel - Copy\\src\\nwdevices2020.db";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Router firstRouter = new Router("router");
        
        
        
        System.out.println(singleHostProbe(firstRouter));
        

        Layer2Switch firstSwitch = new Layer2Switch("switch");
        
        
        System.out.println(singleHostProbe(firstSwitch));
        

        WirelessAccessPoint wap1 = new WirelessAccessPoint("wap");
        
        //pass the waps mac address on later

        MobileDevice dv1 = new MobileDevice("mobile device");
        MobileDevice dv2 = new MobileDevice("mobile device");
        MobileDevice dv3 = new MobileDevice("mobile device");
        MobileDevice dv4 = new MobileDevice("mobile device");
        MobileDevice dv5 = new MobileDevice("mobile device");



        PC pc1 = new PC("PC");
        PC pc2 = new PC("PC");
        PC pc3 = new PC("PC");
        PC pc4 = new PC("PC");
        PC pc5 = new PC("PC");



        WebServer webServ = new WebServer("web server");
        DatabaseServer dataServ = new DatabaseServer("database server");
        FileServer fileServ1 = new FileServer("file server");
        //NetworkPrinter networkPrinter = new NetworkPrinter("networkPrint1", "network printer"); //NO NETWORK PRINTER IN DATABASE!!!
        DomainController domCont = new DomainController("domain controller");

        ArrayList<Host> mobileList = new ArrayList<>();
        ArrayList<PC> pcList = new ArrayList<>();
        ArrayList<Server> serverList = new ArrayList<>();
        serverList.add(webServ);
        serverList.add(dataServ);
        serverList.add(fileServ1);
        //serverList.add(networkPrinter); no network printers in database
        serverList.add(domCont);
        pcList.add(pc1);
        pcList.add(pc2);
        pcList.add(pc3);
        pcList.add(pc4);
        pcList.add(pc5);
        mobileList.add(dv1);
        mobileList.add(dv2);
        mobileList.add(dv3);
        mobileList.add(dv4);
        mobileList.add(dv5);

        for (Server server : serverList) {
            //server.getMACAddressAndStaticIPAddress(nodeTypeCounter(server.getHostType()));

            server.connectToInfrastucture(firstSwitch);

            System.out.println(server.getName() + " connected to network. IP address: " + server.getIPAddress());
        }

        for (PC pc : pcList) {
            pc.connectToInfrastucture(firstSwitch);
            System.out.println(pc.getName() + " connected to network. IP address: " + pc.getIPAddress());
        }
        for (Host mobile : mobileList) {
            mobile.connectToInfrastucture(wap1);
            System.out.println(mobile.getName() + " connected to network. IP address: " + mobile.getIPAddress());
        }

        //not fun having to connect wap after the mobile devices connect to it.
        wap1.connectToInfrastucture(firstSwitch);

        System.out.println("The number of hosts currently online: " + onlineHostCounter(wap1, firstSwitch));
        System.out.println("Here's some details for single hosts: ");
        System.out.println("MAC and IP address for pc5" + singleHostMACIP(pc5) + "\nAnd mobile device 4: " + singleHostMACIP(dv4));
        System.out.println("");
        System.out.println("Network number and subnet mask: " + getNetworkDetails());
        System.out.println("");
        System.out.println("The number of free ip addresses left: " + numberOfFreeIPs());
        System.out.println("");
        
        System.out.println("State and port numbers of servers: ");
        multiServerStateProbe(serverList);
        multiServerPortProbe(serverList);
        //display mac addresses connected to wap
        System.out.println("The mac addresses currently connected to the wap:");
        System.out.println(wap1.getMACAddressList().toString());
        //wap passes all its mac addresses connected to it to the switch
        System.out.println("");
        System.out.println("The mac addresses currently connected to the layer2switch:");
        
        System.out.println(macAddressListProbe(firstSwitch));
        System.out.println("");
        System.out.println("The ip address and name mapping of currently connected hosts:");
        ArrayList<Host> onlineHosts=new ArrayList<>();
        onlineHosts.addAll(pcList);
        onlineHosts.addAll(mobileList);
        multiHostProbe(onlineHosts);

    }
    /**
     * This function returns the MAC address list of the given switch.
     * (This satisfies use-case 6).
     * @param switchObj
     * @return 
     */
    public static String macAddressListProbe(Layer2Switch switchObj){
        return switchObj.getMACAddressList().toString();
    
    }

    /**
     * This function returns the number of nodetypes of a given nodetype from
     * groups_view. This query must be in a separate function instead of being
     * in the getIPAddress function
     *
     * @param nodetype
     * @return
     */
    public static int nodeTypeCounter(String nodetype) {
        int size = 0;

        Connection connection = null;
        try {
            // create a database connection
            connection = DriverManager.getConnection(databasePath);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            //use groups_view to find the number of rows of a particular device type that are in the query above
            ResultSet rs = statement.executeQuery("select * from groups_view where nodetype=\'" + nodetype + "\'");
            size = rs.getInt(2);

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
        return size;
    }

    /**
     * Returns an arraylist of ids from the groups_view table of a given
     * nodeType
     *
     * @param nodeType
     * @return
     */
    public static ArrayList<Integer> nodeTypeIdCollecter(String nodeType) {

        ArrayList<Integer> idList = new ArrayList<>();

        Connection connection = null;
        try {
            // create a database connection
            connection = DriverManager.getConnection(databasePath);
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            //use groups_view to find the number of rows of a particular device type that are in the query above
            ResultSet rs = statement.executeQuery("select * from nwdevices2020 where nodetype=\'" + nodeType + "\'");

            while (rs.next()) {
                idList.add(rs.getInt(1));
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
        return idList;

    }


    /**
     * Returns the number of hosts connected to the network. (This satisfies use-case 1). Hosts includes
     * servers, pcs and mobile devices. This function should only be run after
     * the wap has passed on active host mac addresses to the switch
     *
     * @param activeWap
     * @param activeSwitch
     * @return
     */
    public static int onlineHostCounter(WirelessAccessPoint activeWap, Layer2Switch activeSwitch) {

        //count the number of mac addresses the layer2switch is holding onto -1
        //for the wap's mac address
        int count = activeSwitch.getMACAddressList().size() - 1;
        return count;
    }

    /**
     * Returns a host's name and IP address. (This satisfies use-case 7)
     *
     * @param host
     * @return
     */
    public static String singleHostProbe(Host host) {

        return "Host name: " + host.getName() + " " + " Host IP Address: " + host.getIPAddress();
    }

    /**
     * Returns the name and ip address of all online hosts having been passed an
     * array of all hosts (This satisfies use-case 7)
     *
     * @param hostArray
     */
    public static void multiHostProbe(ArrayList<Host> hostArray) {
        for (Host host : hostArray) {
            System.out.println("Host name: " + host.getName() + " " + " Host IP Address: " + host.getIPAddress());
        }
    }

    /**
     * Returns the mac and ip address of a single host.(This satisfies use-case 2)
     *
     * @param host
     * @return
     */
    public static String singleHostMACIP(Host host) {

        return "Host MAC Address: " + host.getMacAddress() + " " + " Host IP Address: " + host.getIPAddress();
    }

    /**
     * Goes through all nodetypes in the database and finds the difference
     * between the total amount of ips(from nodetype counter) and the amount
     * taken(occupied idlist).(This satisfies use-case 4)
     *
     * @return
     */
    public static int numberOfFreeIPs() {
        //go through all nodetypes in the database and find the difference 
        //between the total amount of  ips(from nodetype counter)
        //and the amount taken(occupied idlist)
        int freeIdCount = 0;

        freeIdCount += freeIdCounter("database server");
        freeIdCount += freeIdCounter("domain controller");
        freeIdCount += freeIdCounter("file server");
        freeIdCount += freeIdCounter("router");
        freeIdCount += freeIdCounter("web server");
        //switches don't currently acquire ip addresses though there are 5
        // in the database
        freeIdCount += freeIdCounter("wap");
        freeIdCount += freeIdCounter("laptop");
        freeIdCount += freeIdCounter("mobile device");
        freeIdCount += freeIdCounter("PC");

        return freeIdCount;

    }

    /**
     * Returns the number of free ids in the database after comparing them to
     * the ids in occupiedIdList
     *
     * @param nodeType
     * @return
     */
    public static int freeIdCounter(String nodeType) {
        int occupiedIdCount = 0;
        ArrayList<Integer> potentialIds = nodeTypeIdCollecter(nodeType);
        for (Integer potentialId : potentialIds) {
            for (Integer occupiedId : Host.occupiedIdList) {
                if (Objects.equals(potentialId, occupiedId)) {
                    occupiedIdCount++;
                }
            }
        }

        return nodeTypeCounter(nodeType) - occupiedIdCount;
    }
    /**
     * Returns the state(online/offline) of servers that are passed in as
     * an arraylist.(This satisfies use-case 5)
     * @param serverList 
     */
    public static void multiServerStateProbe(ArrayList<Server> serverList) {
        for (Server server : serverList) {
            System.out.println(server.getName()+server.getStatus());
        }
    }
    /**
     * Returns the port numbers of servers having been passed an arraylist
     * of servers.(This satisfies use-case 5)
     * @param serverList 
     */
    public static void multiServerPortProbe(ArrayList<Server> serverList){
        
        for (Server server : serverList) {
            System.out.print("Open ports for "+server.getName()+": ");
            ArrayList<Integer> portNumbs=server.getOpenPortNumbers();
            for (Integer portNumb : portNumbs) {
                System.out.print(" "+portNumb);
            }
            System.out.println("");
        }
    }
    /**
     * This method makes some servers, puts them in an arraylist and then probes them.
     * This was created such that a sequence diagram of it can be made.
     */
    public static void makeServersAndConnectThem(){
        WebServer webServ = new WebServer("web server");
        DatabaseServer dataServ = new DatabaseServer("database server");
        //FileServer fileServ1 = new FileServer("file server");
        //NetworkPrinter networkPrinter = new NetworkPrinter("networkPrint1", "network printer"); //NO NETWORK PRINTER IN DATABASE!!!
        //DomainController domCont = new DomainController("domain controller");

        Layer2Switch tempSwitch=new Layer2Switch("switch");
        ArrayList<Server> serverList = new ArrayList<>();
        serverList.add(webServ);
        serverList.add(dataServ);
        //serverList.add(fileServ1);
        //serverList.add(networkPrinter); no network printers in database
        //serverList.add(domCont);
        
        for (Server server : serverList) {
            //server.getMACAddressAndStaticIPAddress(nodeTypeCounter(server.getHostType()));

            server.connectToInfrastucture(tempSwitch);

            System.out.println(server.getName() + " connected to network. IP address: " + server.getIPAddress());
        }
        
        //multiServerStateProbe(serverList);
    
    }
    /**
     * Returns a string of the network IP Address and subnet mask. (This satisfies
     * use-case 3)
     * @return 
     */
    public static String getNetworkDetails() {

        WirelessAccessPoint tempWap = new WirelessAccessPoint("wap");
        tempWap.getMACAddressAndDynamicIPAddressAndName(nodeTypeCounter("wap"));

        String networkIPAddress = tempWap.getIPAddress().substring(0, 10) + "0";

        String firstOctet = networkIPAddress.substring(0, 3);

        String subnetMask = null;
        int numberOfMaskedBits = 0;

        if (Integer.parseInt(firstOctet) <= 127) {
            subnetMask = "255.0.0.0";
            numberOfMaskedBits = 8;
        }
        if (Integer.parseInt(firstOctet) > 127 && Integer.parseInt(firstOctet) <= 191) {
            subnetMask = "255.255.0.0";
            numberOfMaskedBits = 16;
        }
        if (Integer.parseInt(firstOctet) > 191 && Integer.parseInt(firstOctet) <= 223) {
            subnetMask = "255.255.255.0";
            numberOfMaskedBits = 24;
        }

        networkIPAddress += "/" + numberOfMaskedBits;

        String networkString = "The network IP address is: " + networkIPAddress + " and the subnet mask is: " + subnetMask;

        return networkString;
    }
}
