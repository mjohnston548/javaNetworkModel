
package networkclasses;
/**
 * Represents a file server.
 * @author 1800329
 */
public class FileServer extends Server{
    private final String hostType;
    
    public FileServer(String hostType) {
        super(hostType);
        this.hostType=hostType;
        setInitialPortNumbers(new int[]{137, 138, 139,445}) ;
        for (int i = 0; i < this.getInitialPortNumbers().length; i++) {

            addPortNumber(this.getInitialPortNumbers()[i]);
        }
    }
    
}
