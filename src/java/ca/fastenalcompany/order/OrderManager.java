/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ca.fastenalcompany.order;

import ca.fastenalcompany.datesource.DBManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;

/**
 *
 * @author Stone Chao <alpseinstein@gmail.com>
 */
public class OrderManager {
    private Queue<Order> orderQueue;
    private List<Order> orderList;
    
    public OrderManager(){
         orderQueue = new ArrayDeque<>();
         orderList= new ArrayList<>();
    }
    
    public void arrive(Order order){
        if(order.getCustomerID().equals("")&&order.getCustomerName().equals("")) throw new NoCustomerException();
        if(order.getPurchases()==null) throw new NoPurchasesException();
        orderQueue.add(order);
        order.setTimeRecevied(new Date());
    }
    
    public void process(Order order){
        if(order.getTimeRecevied()==null)throw new NoTimeReceviedException();
        if(!checkPurchases(order.getPurchases()))throw new NoPurchasesInStockException();
        order.setTimeProcessed(new Date());
        orderQueue.remove(order);
        orderList.add(order);
    }
    
    public void fulfill(Order order){
        if(order.getTimeProcessed()==null)throw new NoTimeProcessedException();
        if(order.getTimeRecevied()==null)throw new NoTimeReceviedException();
        if(!checkPurchases(order.getPurchases()))throw new NoPurchasesInStockException();
        order.setTimeFulfilled(new Date());
    }
    
    public String report(){
        String report="";
        if(!orderQueue.isEmpty()||!orderList.isEmpty()){
            JSONObject obj = new JSONObject();
            List<Order> list = new ArrayList(orderQueue);
            list.addAll(orderList);
            obj.put("orders", list);
            report=obj.toString();
        }
        return report;
    }
    public Order next(){
        return orderQueue.peek();
    }

    public boolean checkPurchases(List<Purchase> purchases){
        return true;
    }
    
    public void connect(){
        DBManager db= new DBManager();
        Connection conn = null;
        try {
            conn = db.getMysqlConn();
            String query = "select * from inventory";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                for(int i=1;i<=rs.getMetaData().getColumnCount();i++)
                    System.out.println(rs.getString(i));
            }
        } catch (SQLException ex) {
            Logger.getLogger(FastenalCompany.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if(conn!=null)conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(FastenalCompany.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public class NoCustomerException extends RuntimeException{}
    public class NoPurchasesException extends RuntimeException{}
    public class NoTimeReceviedException extends RuntimeException{}
    public class NoPurchasesInStockException extends RuntimeException{}
    public class NoTimeProcessedException extends RuntimeException{}
}
