package entity.production;

import java.sql.Date;

public class Attendence {
    private int attendentID;
    private int schEmpID;
    private double quantity;
    private float alpha;

    // Constructors, Getters, and Setters
    public Attendence(int attendentID, int schEmpID, double quantity, float alpha) {
        this.attendentID = attendentID;
        this.schEmpID = schEmpID;
        this.quantity = quantity;
        this.alpha = alpha;
    }

    public int getAttendentID() {
        return attendentID;
    }

    public void setAttendentID(int attendentID) {
        this.attendentID = attendentID;
    }

    public int getSchEmpID() {
        return schEmpID;
    }

    public void setSchEmpID(int schEmpID) {
        this.schEmpID = schEmpID;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }
}