/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ca.fastenalcompany.order;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author JIAJUN XUE <nicoxue0324@gmail.com>
 */
public class PurchaseTest {
    
    public PurchaseTest() {
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
     * Test of toString method, of class Purchase.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Purchase instance = new Purchase();
        String expResult = "productID :0, quantity :0";
        String result = instance.toString();
        assertEquals(expResult, result);
    }
    
}
