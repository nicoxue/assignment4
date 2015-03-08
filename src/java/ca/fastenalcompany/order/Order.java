/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ca.fastenalcompany.order;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author shitong zhao <alpseinstein@gmail.com>
 */
public class Order {
    private String customerID;
    private String customerName;
    private Date timeRecevied;
    private Date timeProcessed;
    private Date timeFulfilled;
    private List<Purchase> purchases;
    private String note;

    public Order() {
        customerID="";
        customerName="";
        this.timeRecevied = null;
        this.timeProcessed = null;
        this.timeFulfilled = null;
        this.purchases = null;
        this.note = "";
    }

    public Order(String customerID, String customerName) {
        this();
        this.customerID = customerID;
        this.customerName = customerName;
    }
    
    public Order(String customerID, String customerName, Date timeRecevied, Date timeProcessed, Date timeFulfilled, List<Purchase> purchase, String note) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.timeRecevied = timeRecevied;
        this.timeProcessed = timeProcessed;
        this.timeFulfilled = timeFulfilled;
        this.purchases = purchase;
        this.note = note;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Date getTimeRecevied() {
        return timeRecevied;
    }

    public void setTimeRecevied(Date timeRecevied) {
        this.timeRecevied = timeRecevied;
    }

    public Date getTimeProcessed() {
        return timeProcessed;
    }

    public void setTimeProcessed(Date timeProcessed) {
        this.timeProcessed = timeProcessed;
    }

    public Date getTimeFulfilled() {
        return timeFulfilled;
    }

    public void setTimeFulfilled(Date timeFulfilled) {
        this.timeFulfilled = timeFulfilled;
    }

    public List<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<Purchase> purchases) {
        this.purchases = purchases;
    }

    public void addPurchase(int productID,int quantity){
        if(purchases==null)this.purchases=new ArrayList<>();
        this.purchases.add(new Purchase(productID,quantity));
    }
    
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "{" + "customerID : " + customerID + ", customerName : " + customerName+", timeRecevied : " + (timeRecevied==null?"":new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(timeRecevied)) + ", timeProcessed : " + (timeProcessed==null?"":new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(timeProcessed)) + ", timeFulfilled : " + (timeFulfilled==null?"":new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(timeFulfilled)) + ", purchase : " + (purchases==null?"":purchases) + ", note : " + note + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.customerID);
        hash = 97 * hash + Objects.hashCode(this.customerName);
        hash = 97 * hash + Objects.hashCode(this.purchases);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Order other = (Order) obj;
        if (!Objects.equals(this.customerID, other.customerID)) {
            return false;
        }
        if (!Objects.equals(this.customerName, other.customerName)) {
            return false;
        }
        if (!Objects.equals(this.purchases, other.purchases)) {
            return false;
        }
        return true;
    }
 
    
}
