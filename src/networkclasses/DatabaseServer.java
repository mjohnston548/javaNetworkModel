
package networkclasses;

/**
 * Represents a database server.
 * @author 1800329
 */
public class DatabaseServer extends Server{
    private final String hostType;
    

    public DatabaseServer(String hostType) {
        super(hostType);
        this.hostType=hostType;
        setInitialPortNumbers(new int[]{1521, 3306, 5432}) ;
        for (int i = 0; i < this.getInitialPortNumbers().length; i++) {

            addPortNumber(this.getInitialPortNumbers()[i]);
        }

    }
    
}
