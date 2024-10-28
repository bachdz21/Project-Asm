
package entity.production;

import java.util.ArrayList;

public class PlanCampaign {
    private int id;
    private Plan plan;
    private Product product;
    private int quantity;
    private float estimate;
    private ArrayList<SchedualCampaign> schedualCampaigns = new ArrayList<>();
    
    public PlanCampaign() {
    }

    public ArrayList<SchedualCampaign> getSchedualCampaigns() {
        return schedualCampaigns;
    }

    public void setSchedualCampaigns(ArrayList<SchedualCampaign> schedualCampaigns) {
        this.schedualCampaigns = schedualCampaigns;
    }

    public PlanCampaign(int id) {
        this.id = id;
    }

    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getEstimate() {
        return estimate;
    }

    public void setEstimate(float estimate) {
        this.estimate = estimate;
    }
    
}
