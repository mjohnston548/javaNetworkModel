
package networkutility;

/**
 * Classes which implement this interface acquire their IP address and can then
 * no longer change it.
 * @author 1800329
 */
public interface IPAddressStatic {
    void getMACAddressAndStaticIPAddress( int numberOfNodes);
}
