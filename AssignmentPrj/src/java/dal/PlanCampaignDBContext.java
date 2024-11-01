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
        String sql = "INSERT INTO PlanCampaign (PlanID, ProductID, Quantity, Estimate) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // Thiết lập các giá trị cho các tham số trong câu lệnh SQL
            stm.setInt(1, entity.getPlan().getId());        // PlanID
            stm.setInt(2, entity.getProduct().getId());     // ProductID
            stm.setInt(3, entity.getQuantity());            // Quantity
            stm.setFloat(4, entity.getEstimate());          // Estimate

            // Thực thi lệnh INSERT
            stm.executeUpdate();

            // Lấy khóa chính tự động tạo (nếu cần)
            ResultSet generatedKeys = stm.getGeneratedKeys();
            if (generatedKeys.next()) {
                entity.setId(generatedKeys.getInt(1)); // Đặt ID vừa tạo cho PlanCampaign
            }
        } catch (SQLException ex) {
            Logger.getLogger(PlanCampaignDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
public void update(PlanCampaign entity) {
    String sql = "UPDATE PlanCampaign SET Quantity = ?, Estimate = ? WHERE PlanID = ? AND ProductID = ?";
    try (PreparedStatement stm = connection.prepareStatement(sql)) {
        // Thiết lập các giá trị cho các tham số trong câu lệnh SQL
        stm.setInt(1, entity.getQuantity());            // Quantity
        stm.setFloat(2, entity.getEstimate());          // Estimate
        stm.setInt(3, entity.getPlan().getId());        // PlanID
        stm.setInt(4, entity.getProduct().getId());     // ProductID
        
        // Thực thi lệnh UPDATE
        stm.executeUpdate();
    } catch (SQLException ex) {
        Logger.getLogger(PlanCampaignDBContext.class.getName()).log(Level.SEVERE, null, ex);
    }
}

    @Override
    public void delete(PlanCampaign entity) {
        String sql = "DELETE FROM PlanCampaign WHERE PlanCampnID = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            // Thiết lập giá trị cho tham số PlanCampnID
            stm.setInt(1, entity.getId());

            // Thực thi lệnh DELETE
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PlanCampaignDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        
       public void deleteByPlanIDAndProductID(int planID, int productID) {
        String sqlDeleteSchedualCampaign = "DELETE FROM SchedualCampaign WHERE PlanCampnID IN "
                + "(SELECT PlanCampnID FROM PlanCampaign WHERE PlanID = ? AND ProductID = ?)";
        String sqlDeletePlanCampaign = "DELETE FROM PlanCampaign WHERE PlanID = ? AND ProductID = ?";

        try {
            // Bắt đầu một transaction để đảm bảo cả hai câu lệnh đều được thực hiện
            connection.setAutoCommit(false);

            // Xóa các bản ghi trong SchedualCampaign liên quan đến PlanCampaign cụ thể
            try (PreparedStatement stm1 = connection.prepareStatement(sqlDeleteSchedualCampaign)) {
                stm1.setInt(1, planID);
                stm1.setInt(2, productID);
                stm1.executeUpdate();
            }

            // Xóa bản ghi trong PlanCampaign
            try (PreparedStatement stm2 = connection.prepareStatement(sqlDeletePlanCampaign)) {
                stm2.setInt(1, planID);
                stm2.setInt(2, productID);
                stm2.executeUpdate();
            }

            // Xác nhận transaction
            connection.commit();

        } catch (SQLException ex) {
            try {
                // Rollback nếu có lỗi xảy ra
                connection.rollback();
            } catch (SQLException rollbackEx) {
                Logger.getLogger(PlanCampaignDBContext.class.getName()).log(Level.SEVERE, null, rollbackEx);
            }
            Logger.getLogger(PlanCampaignDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(PlanCampaignDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    } 
}
