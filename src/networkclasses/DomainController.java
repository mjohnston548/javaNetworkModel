
package networkclasses;
/**
 * Represents a domain controller server.
 * @author 1800329
 */
public class DomainController extends Server{
    
    private final String hostType;
    
    public DomainController( String hostType) {
        super(hostType);
        this.hostType=hostType;
        setInitialPortNumbers(new int[]{67, 68, 53}) ;
        for (int i = 0; i < this.getInitialPortNumbers().length; i++) {

            addPortNumber(this.getInitialPortNumbers()[i]);
        }
    }
    
}
