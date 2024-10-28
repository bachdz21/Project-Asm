package entity.production;

public class SchedualEmployee {
    private int schEmpID;
    private int scID;
    private int employeeID;
    private double quantity;

    // Constructors, Getters, and Setters
    public SchedualEmployee(int schEmpID, int scID, int employeeID, double quantity) {
        this.schEmpID = schEmpID;
        this.scID = scID;
        this.employeeID = employeeID;
        this.quantity = quantity;
    }

    public int getSchEmpID() {
        return schEmpID;
    }

    public void setSchEmpID(int schEmpID) {
        this.schEmpID = schEmpID;
    }

    public int getScID() {
        return scID;
    }

    public void setScID(int scID) {
        this.scID = scID;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
} 
