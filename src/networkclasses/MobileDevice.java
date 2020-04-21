
package networkclasses;

/**
 *
 * @author 1800329
 * This class represents mobile device or laptop, tablet etc
 */
public class MobileDevice extends Host{
    
    private final String hostType;
    
    public MobileDevice( String hostType) {
        super(hostType);
        this.hostType=hostType;
    }
    
}
