/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networkinfrastructure;
import networkinfrastructure.Router;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author 1800329
 */
public class RouterTest {
    
    
    public RouterTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        
    }
    
    @After
    public void tearDown() {
    }
    
    /**
     * Test of getMACAddressAndStaticIPAddress method, of class Router.
     */
    @Test
    public void testGetMACAddressAndStaticIPAddress() {
        System.out.println("getMACAddressAndStaticIPAddress");
        int numberOfNodes = 1;
        Router tempRouter=new Router( "router");
        tempRouter.getMACAddressAndStaticIPAddress(numberOfNodes);
        
        assertEquals("172.20.20.245", tempRouter.getIPAddress());
    }
    
}
