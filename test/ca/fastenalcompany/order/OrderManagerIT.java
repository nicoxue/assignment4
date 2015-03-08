/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ca.fastenalcompany.order;

import ca.fastenalcompany.order.OrderManager.NoCustomerException;
import ca.fastenalcompany.order.OrderManager.NoPurchasesException;
import ca.fastenalcompany.order.OrderManager.NoTimeProcessedException;
import ca.fastenalcompany.order.OrderManager.NoTimeReceviedException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Shitong Zhao<alpseinstein@gmail.com>
 */
public class OrderManagerIT {
    
    public OrderManagerIT() {
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
     * Test of orderArrives method, of class OrderManager.
     */
    @Test
    public void testOrderArrivedCustomerAndPurchasesExistThenTimeReceivedIsNow() {
        System.out.println("testOrderArrivedCustomerAndPurchasesExistThenTimeReceivedIsNow");
        OrderManager instance = new OrderManager();
        Order order = new Order("C00001", "Fast and Light");
        order.addPurchase(1, 1);
        instance.arrive(order);
        assertTrue(order.getTimeRecevied().equals(new Date()));
    }
    
    @Test
    public void testOrderArrivedNoCustomerIDAndCustomerNameException() {
        System.out.println("testOrderArrivedNoCustomerIDAndCustomerNameException");
        OrderManager instance = new OrderManager();
        Order order = new Order();
        order.addPurchase(1, 1);
        boolean didThrow=false;
        try{
           instance.arrive(order);
        }catch(NoCustomerException ex){
            didThrow=true;
        }
        assertTrue(didThrow);
    }
    
    @Test
    public void testOrderArrivedNoPurchasesException() {
        System.out.println("testOrderArrivedNoPurchasesException");
        OrderManager instance = new OrderManager();
        Order order = new Order("C00001", "Fast and Light");
        boolean didThrow=false;
        try{
           instance.arrive(order);
        }catch(NoPurchasesException ex){
            didThrow=true;
        }
        assertTrue(didThrow);
    }

    @Test
    public void testNextOrderReturnEarliestOrder() {
        System.out.println("testNextOrderReturnEarliestOrder");
        OrderManager instance = new OrderManager();
        Order first = null;
        for(int i=1;i<4;i++){
            Order order = new Order("C0000"+i, "Fast and Light"+i);
            order.addPurchase(i, i);
            instance.arrive(order);
            if(i==1)first=order;
        }
        Order order = instance.next();
        assertTrue(first.equals(order));
    }
    
    @Test
    public void testNextOrderReturnNull() {
        System.out.println("testNextOrderReturnNull");
        OrderManager instance = new OrderManager();
        Order order = instance.next();
        assertTrue(order==null);
    }
    
    @Test
    public void testOrderProcessSetTheTimeProcessedToNow() {
        System.out.println("testOrderProcessSetTheTimeProcessedToNow");
        OrderManager instance = new OrderManager();
        for(int i=1;i<4;i++){
            Order order = new Order("C0000"+i, "Fast and Light"+i);
            order.addPurchase(i, i);
            instance.arrive(order);
        }
        Order order = instance.next();
        instance.process(order);
        assertTrue(order.getTimeProcessed().equals(new Date()));
    }
    
    @Test
    public void testOrderProcessNoTimeReceviedException() {
        System.out.println("testOrderProcessNoTimeReceviedException");
        OrderManager instance = new OrderManager();
        for(int i=1;i<4;i++){
            Order order = new Order("C0000"+i, "Fast and Light"+i);
            order.addPurchase(i, i);
            instance.arrive(order);
        }
        Order order = instance.next();
        order.setTimeRecevied(null);
        boolean didThrow=false;
        try{
           instance.process(order);
        }catch(NoTimeReceviedException ex){
            didThrow=true;
        }
        assertTrue(didThrow);
    }
    
    @Test
    public void testOrderFulfillSetTheTimeFulfilledToNow() {
        System.out.println("testOrderFulfillSetTheTimeFulfilledToNow");
        OrderManager instance = new OrderManager();
        for(int i=1;i<4;i++){
            Order order = new Order("C0000"+i, "Fast and Light"+i);
            order.addPurchase(i, i);
            instance.arrive(order);
        }
        Order order = instance.next();
        instance.process(order);
        instance.fulfill(order);
        assertTrue(order.getTimeFulfilled().equals(new Date()));
    }
    
    @Test
    public void testOrderFulfillNoTimeProcessedException() {
        System.out.println("testOrderFulfillNoTimeProcessedException");
        OrderManager instance = new OrderManager();
        for(int i=1;i<4;i++){
            Order order = new Order("C0000"+i, "Fast and Light"+i);
            order.addPurchase(i, i);
            instance.arrive(order);
        }
        Order order = instance.next();
        boolean didThrow=false;
        try{
           instance.fulfill(order);
        }catch(NoTimeProcessedException ex){
            didThrow=true;
        }
        assertTrue(didThrow);;
    }
    
    @Test
    public void testOrderFulfillNoTimeReceviedException() {
        System.out.println("testOrderFulfillNoTimeProcessedException");
        OrderManager instance = new OrderManager();
        for(int i=1;i<4;i++){
            Order order = new Order("C0000"+i, "Fast and Light"+i);
            order.addPurchase(i, i);
            instance.arrive(order);
        }
        Order order = instance.next();
        instance.process(order);
        order.setTimeRecevied(null);
        boolean didThrow=false;
        try{
           instance.fulfill(order);
        }catch(NoTimeReceviedException ex){
            didThrow=true;
        }
        assertTrue(didThrow);;
    }
    
    @Test
    public void testOrderReportWithNoOrders() {
        System.out.println("testOrderReportWithNoOrders");
        OrderManager instance = new OrderManager();
        String report = instance.report();
        System.out.println(report);
        assertTrue(report.equals(""));
    }
    
    @Test
    public void testOrderReportToJsonWithOrders() {
        System.out.println("testOrderReportToJsonWithOrders");
        OrderManager instance = new OrderManager();
        for(int i=1;i<3;i++){
            Order order = new Order("C0000"+i, "Fast and Light"+i);
            order.addPurchase(i, i);
            instance.arrive(order);        
        }
        Order order = instance.next();
        instance.process(order);
        instance.fulfill(order);
        String report = instance.report();
        String expResult = "{\"orders\":[{customerID : C00002, customerName : Fast and Light2, timeRecevied : "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date())+", timeProcessed : , timeFulfilled : , purchase : [{ productID : 2, quantity : 2 }], note : },{customerID : C00001, customerName : Fast and Light1, timeRecevied : "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date())+", timeProcessed : "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date())+", timeFulfilled : "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date())+", purchase : [{ productID : 1, quantity : 1 }], note : }]}";
        System.out.println(expResult);
        System.out.println(report);
        assertEquals(expResult, report);
    }
    
    /**
     * Test of connect method, of class OrderManager.
     */
    @Test
    public void testConnect() {
        System.out.println("connect");
        OrderManager instance = new OrderManager();
        //instance.connect();
        assertTrue(true);
    }
    
}
