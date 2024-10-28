package dal;

import entity.production.*;
import entity.production.PlanCampaign;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlanCampaignDBContext extends DBContext<PlanCampaign> {

    @Override
    public void insert(PlanCampaign entity) {
        // Insert logic here
    }

    @Override
    public void update(PlanCampaign entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(PlanCampaign entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList<PlanCampaign> list() {
        ArrayList<PlanCampaign> planCampaigns = new ArrayList<>();
        try {
            String sql = "SELECT pc.[PlanCampnID], pc.[PlanID], pc.[ProductID], pc.[Quantity], pc.[Estimate], p.[PlanName], pr.[ProductName] "
                    + "FROM [PlanCampaign] pc "
                    + "INNER JOIN [Plan] p ON pc.[PlanID] = p.[PlanID] "
                    + "INNER JOIN [Product] pr ON pc.[ProductID] = pr.[ProductID]";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Plan plan = new Plan();
                plan.setId(rs.getInt("PlanID"));
                plan.setName(rs.getString("PlanName"));

                Product product = new Product();
                product.setId(rs.getInt("ProductID"));
                product.setName(rs.getString("ProductName"));

                PlanCampaign planCampaign = new PlanCampaign();
                planCampaign.setId(rs.getInt("PlanCampnID"));
                planCampaign.setPlan(plan);
                planCampaign.setProduct(product);
                planCampaign.setQuantity(rs.getInt("Quantity"));
                planCampaign.setEstimate(rs.getFloat("Estimate"));

                planCampaigns.add(planCampaign);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PlanCampaignDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(PlanCampaignDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return planCampaigns;
    }

    @Override
    public PlanCampaign get(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ArrayList<PlanCampaign> list(int planId) {
        ArrayList<PlanCampaign> planCampaigns = new ArrayList<>();
        try {
            String sql = "SELECT pc.[PlanCampnID], pc.[PlanID], pc.[ProductID], pc.[Quantity], pc.[Estimate], "
                    + "p.[PlanName], pr.[ProductName] "
                    + "FROM [PlanCampaign] pc "
                    + "INNER JOIN [Plan] p ON pc.[PlanID] = p.[PlanID] "
                    + "INNER JOIN [Product] pr ON pc.[ProductID] = pr.[ProductID] "
                    + "WHERE pc.[PlanID] = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, planId);  // Gán tham số planId vào câu truy vấn
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Plan plan = new Plan();
                plan.setId(rs.getInt("PlanID"));
                plan.setName(rs.getString("PlanName"));

                Product product = new Product();
                product.setId(rs.getInt("ProductID"));
                product.setName(rs.getString("ProductName"));

                PlanCampaign planCampaign = new PlanCampaign();
                planCampaign.setId(rs.getInt("PlanCampnID"));
                planCampaign.setPlan(plan);
                planCampaign.setProduct(product);
                planCampaign.setQuantity(rs.getInt("Quantity"));
                planCampaign.setEstimate(rs.getFloat("Estimate"));

                planCampaigns.add(planCampaign);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PlanCampaignDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(PlanCampaignDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return planCampaigns;
    }

    public ArrayList<Date> getDateByplanCampnID(int planCampnID) {
        ArrayList<Date> dates = new ArrayList<>();
        try {
            String sql = "SELECT p.[StartDate], p.[EndDate] FROM [PlanCampaign] pc INNER JOIN [Plan] p ON pc.[PlanID] = p.[PlanID] WHERE pc.[PlanCampnID] = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, planCampnID);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                Date startDate = rs.getDate("StartDate");
                Date endDate = rs.getDate("EndDate");
                while (!startDate.after(endDate)) {
                    dates.add(startDate);
                    startDate = new Date(startDate.getTime() + (24 * 60 * 60 * 1000)); // Increment by one day
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PlanCampaignDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(PlanCampaignDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return dates;
    }

    public ArrayList<Product> getListProductByplanCampnID(int planCampnID) {
        ArrayList<Product> products = new ArrayList<>();
        try {
            String sql = "SELECT pr.[ProductID], pr.[ProductName] FROM [PlanCampaign] pc INNER JOIN [Product] pr ON pc.[ProductID] = pr.[ProductID] WHERE pc.[PlanCampnID] = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, planCampnID);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("ProductID"));
                product.setName(rs.getString("ProductName"));
                products.add(product);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PlanCampaignDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(PlanCampaignDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return products;
    }
    
        public int getPlanCampnIDByplanIDAndproductID(int planID, int productID) {
        int planCampnID = -1;
        try {
            String sql = "SELECT [PlanCampnID] FROM [PlanCampaign] WHERE [PlanID] = ? AND [ProductID] = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, planID);
            stm.setInt(2, productID);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                planCampnID = rs.getInt("PlanCampnID");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PlanCampaignDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(PlanCampaignDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return planCampnID;
    }
        public static void main(String[] args) {
        PlanCampaignDBContext db = new PlanCampaignDBContext();
            System.out.println(db.getPlanCampnIDByplanIDAndproductID(14, 1));
    }
}
