
package networkclasses;
/**
 * Represents a web server.
 * @author 1800329
 */
public class WebServer extends Server{
    private final String hostType;
    public WebServer(String hostType) {
        super( hostType);
        this.hostType=hostType;
        setInitialPortNumbers(new int[]{80,443}) ;
        for (int i = 0; i < this.getInitialPortNumbers().length; i++) {

            addPortNumber(this.getInitialPortNumbers()[i]);
        }
    }
    
}
