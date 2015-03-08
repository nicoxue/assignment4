/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ca.fastenalcompany.order;

/**
 *
 * @author JIAJUN XUE <nicoxue0324@gmail.com>
 */
public class Purchase {
    private int ProductID,Quantity;
    
    public Purchase(){
    }
    
    public Purchase(int ProductID, int Quantity) {
        this.ProductID = ProductID;
        this.Quantity = Quantity;
    }

    public int getProductID() {
        return ProductID;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setProductID(int ProductID) {
        this.ProductID = ProductID;
    }

    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }
    
    @Override
    public String toString(){
        return "{ productID : "+ProductID +", quantity : "+Quantity+" }";
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.ProductID;
        hash = 89 * hash + this.Quantity;
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
        final Purchase other = (Purchase) obj;
        if (this.ProductID != other.ProductID) {
            return false;
        }
        if (this.Quantity != other.Quantity) {
            return false;
        }
        return true;
    }
    
    
}
