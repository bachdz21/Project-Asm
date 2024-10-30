package entity.production;

import java.sql.Date;
import java.util.ArrayList;

public class Plan {
    private int id;
    private String name;
    private Date start;
    private Date end;
    private Department dept;
    private ArrayList<PlanCampaign> campaigns = new ArrayList<>();
    private boolean isDeleted; // Thêm thuộc tính isDeleted

    public Plan() {
    }

    public Plan(int id) {
        this.id = id;
    }

    public Plan(int id, String name, Date start, Date end, Department dept, boolean isDeleted) {
        this.id = id;
        this.name = name;
        this.start = start;
        this.end = end;
        this.dept = dept;
        this.isDeleted = isDeleted;
    }

    // Getter và Setter cho id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter và Setter cho name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter và Setter cho start
    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    // Getter và Setter cho end
    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    // Getter và Setter cho dept
    public Department getDept() {
        return dept;
    }

    public void setDept(Department dept) {
        this.dept = dept;
    }

    // Getter và Setter cho campaigns
    public ArrayList<PlanCampaign> getCampaigns() {
        return campaigns;
    }

    public void setCampaigns(ArrayList<PlanCampaign> campaigns) {
        this.campaigns = campaigns;
    }

    // Getter và Setter cho isDeleted
    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}
