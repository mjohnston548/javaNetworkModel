
package networkclasses;
/**
 * Represents a network printer though there are no details available for
 * network printers in the database.
 * @author 1800329
 */
public class NetworkPrinter extends Server{
    private final String hostType;
    public NetworkPrinter(String hostType ) {
        super(hostType);
        this.hostType=hostType;
        setInitialPortNumbers(new int[]{631, 9100}) ;
        for (int i = 0; i < this.getInitialPortNumbers().length; i++) {

            addPortNumber(this.getInitialPortNumbers()[i]);
        }
    }
    
}
