
package entity.production;

import java.sql.Date;

public class SchedualCampaign {
    private int scID;
    private PlanCampaign planCampaign;
    private Date date;
    private int shift;
    private int quantity;

    public SchedualCampaign() {
    }

    public SchedualCampaign(int scID, PlanCampaign planCampaign, Date date, int shift, int quantity) {
        this.scID = scID;
        this.planCampaign = planCampaign;
        this.date = date;
        this.shift = shift;
        this.quantity = quantity;
    }

    public int getScID() {
        return scID;
    }

    public void setScID(int scID) {
        this.scID = scID;
    }

    public PlanCampaign getPlanCampaign() {
        return planCampaign;
    }

    public void setPlanCampaign(PlanCampaign planCampaign) {
        this.planCampaign = planCampaign;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getShift() {
        return shift;
    }

    public void setShift(int shift) {
        this.shift = shift;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    
}
