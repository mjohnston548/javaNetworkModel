
package networkclasses;

/**
 * Represents a PC. Which should only connect to a switch.
 * @author 1800329
 */
public class PC extends Host{
    private final String hostType;
    public PC(String hostType) {
        super(hostType);
        this.hostType=hostType;
        
    }
    
}
