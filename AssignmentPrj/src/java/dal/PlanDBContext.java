package dal;

import dal.*;
import dal.DBContext;
import entity.production.Department;
import entity.production.Plan;
import entity.production.PlanCampaign;
import entity.production.Product;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;
import java.util.List;

public class PlanDBContext extends DBContext<Plan> {

    @Override
    public void insert(Plan entity) {
        try {
            connection.setAutoCommit(false);

            String sql_insert_plan = "INSERT INTO [Plan]\n"
                    + "           ([PlanName]\n"
                    + "           ,[StartDate]\n"
                    + "           ,[EndDate]\n"
                    + "           ,[DepartmentID])\n"
                    + "     VALUES\n"
                    + "           (?\n"
                    + "           ,?\n"
                    + "           ,?\n"
                    + "           ,?)";
            PreparedStatement stm_insert_plan = connection.prepareStatement(sql_insert_plan);
            stm_insert_plan.setString(1, entity.getName());
            stm_insert_plan.setDate(2, entity.getStart());
            stm_insert_plan.setDate(3, entity.getEnd());
            stm_insert_plan.setInt(4, entity.getDept().getId());
            stm_insert_plan.executeUpdate();

            String sql_select_plan = "SELECT @@IDENTITY as PlanID";
            PreparedStatement stm_select_plan = connection.prepareStatement(sql_select_plan);
            ResultSet rs = stm_select_plan.executeQuery();
            if (rs.next()) {
                entity.setId(rs.getInt("PlanID"));
            }

            for (PlanCampaign campaign : entity.getCampaigns()) {
                String sql_insert_campaign = "INSERT INTO [PlanCampaign]\n"
                        + "           ([PlanID]\n"
                        + "           ,[ProductID]\n"
                        + "           ,[Quantity]\n"
                        + "           ,[Estimate])\n"
                        + "     VALUES\n"
                        + "           (?\n"
                        + "           ,?\n"
                        + "           ,?\n"
                        + "           ,?)";

                PreparedStatement stm_insert_campaign = connection.prepareStatement(sql_insert_campaign);
                stm_insert_campaign.setInt(1, entity.getId());
                stm_insert_campaign.setInt(2, campaign.getProduct().getId());
                stm_insert_campaign.setInt(3, campaign.getQuantity());
                stm_insert_campaign.setFloat(4, campaign.getEstimate());
                stm_insert_campaign.executeUpdate();
            }

            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @Override
    public ArrayList<Plan> list() {
        ArrayList<Plan> plans = new ArrayList<>();
        try {
            String sql = "SELECT p.[PlanID], p.[PlanName], p.[StartDate], p.[EndDate], d.[DepartmentID], d.[DepartmentName], d.[type] "
                    + "FROM [Plan] p INNER JOIN [Department] d ON p.[DepartmentID] = d.[DepartmentID] "
                    + "WHERE p.isDeleted = 0";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Department dept = new Department(rs.getInt("DepartmentID"), rs.getString("DepartmentName"), rs.getString("type"));
                Plan plan = new Plan();
                plan.setId(rs.getInt("PlanID"));
                plan.setName(rs.getString("PlanName"));
                plan.setStart(rs.getDate("StartDate"));
                plan.setEnd(rs.getDate("EndDate"));
                plan.setDept(dept);
                plans.add(plan);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return plans;
    }

    @Override
    public void delete(Plan entity) {
        try {
            String sql = "UPDATE [Plan] SET isDeleted = 1 WHERE PlanID = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, entity.getId());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void update(Plan entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Plan get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public ArrayList<Product> getProductsByplanID(int id) {
        ArrayList<Product> products = new ArrayList<>();
        try {
            String sql = "SELECT p.ProductID, p.ProductName FROM Product p "
                    + "INNER JOIN PlanCampaign pc ON p.ProductID = pc.ProductID "
                    + "INNER JOIN Plan pl ON pc.PlanID = pl.PlanID "
                    + "WHERE pc.PlanID = ? AND pl.isDeleted = 0";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("ProductID"));
                product.setName(rs.getString("ProductName"));
                products.add(product);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return products;
    }

    public ArrayList<Date> getDateByplanID(int id) {
        ArrayList<Date> dates = new ArrayList<>();
        String sql = "SELECT StartDate, EndDate FROM [Plan] WHERE PlanID = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, id);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    Date startDate = rs.getDate("StartDate");
                    Date endDate = rs.getDate("EndDate");

                    // Tạo danh sách các ngày từ startDate đến endDate
                    java.util.Calendar calendar = java.util.Calendar.getInstance();
                    calendar.setTime(startDate);

                    while (!calendar.getTime().after(endDate)) {
                        dates.add(new Date(calendar.getTimeInMillis()));
                        calendar.add(java.util.Calendar.DAY_OF_MONTH, 1); // Chuyển sang ngày tiếp theo
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dates;
    }

    public void delete(int planID) {
        try {
            String sql = "UPDATE [Plan] SET isDeleted = 1 WHERE PlanID = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, planID);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public ArrayList<Plan> listPlanIsDelete() {
        ArrayList<Plan> deletedPlans = new ArrayList<>();
        String sql = "SELECT p.[PlanID], p.[PlanName], p.[StartDate], p.[EndDate], d.[DepartmentID], d.[DepartmentName], d.[type] "
                + "FROM [Plan] p INNER JOIN [Department] d ON p.[DepartmentID] = d.[DepartmentID] "
                + "WHERE p.isDeleted = 1";
        try (PreparedStatement stm = connection.prepareStatement(sql); ResultSet rs = stm.executeQuery()) {
            while (rs.next()) {
                Department dept = new Department(rs.getInt("DepartmentID"), rs.getString("DepartmentName"), rs.getString("type"));
                Plan plan = new Plan();
                plan.setId(rs.getInt("PlanID"));
                plan.setName(rs.getString("PlanName"));
                plan.setStart(rs.getDate("StartDate"));
                plan.setEnd(rs.getDate("EndDate"));
                plan.setDept(dept);
                deletedPlans.add(plan);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return deletedPlans;
    }

    public void restorePlanByPlanID(int planID) {
        String sql = "UPDATE [Plan] SET isDeleted = 0 WHERE PlanID = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, planID);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   public ArrayList<Plan> searchPlans(String title, Date from, Date to, Integer workshop, Integer product) {
    String sql = "SELECT p.PlanID, p.PlanName, p.StartDate, p.EndDate, d.DepartmentID, d.DepartmentName " +
                 "FROM Plan p " +
                 "INNER JOIN Department d ON p.DepartmentID = d.DepartmentID " +
                 "WHERE (1=1)";

    ArrayList<Plan> plans = new ArrayList<>();
    ArrayList<Object> paramValues = new ArrayList<>();

    // Lọc theo tiêu chí Plan Name
    if (title != null && !title.isBlank()) {
        sql += " AND p.PlanName LIKE '%' + ? + '%'";
        paramValues.add(title);
    }

    // Lọc theo ngày bắt đầu
    if (from != null) {
        sql += " AND p.StartDate >= ?";
        paramValues.add(from);
    }

    // Lọc theo ngày kết thúc
    if (to != null) {
        sql += " AND p.EndDate <= ?";
        paramValues.add(to);
    }

    // Lọc theo Workshop (Department)
    if (workshop != null && workshop != -1) {
        sql += " AND d.DepartmentID = ?";
        paramValues.add(workshop);
    }

    // Lọc theo Product
    if (product != null && product != -1) {
        sql += " AND EXISTS (SELECT 1 FROM PlanCampaign pc WHERE pc.PlanID = p.PlanID AND pc.ProductID = ?)";
        paramValues.add(product);
    }

    PreparedStatement stm = null;
    try {
        stm = connection.prepareStatement(sql);
        for (int i = 0; i < paramValues.size(); i++) {
            stm.setObject(i + 1, paramValues.get(i));
        }
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            Plan plan = new Plan();
            plan.setId(rs.getInt("PlanID"));
            plan.setName(rs.getString("PlanName"));
            plan.setStart(rs.getDate("StartDate"));
            plan.setEnd(rs.getDate("EndDate"));

            Department dept = new Department();
            dept.setId(rs.getInt("DepartmentID"));
            dept.setName(rs.getString("DepartmentName"));
            plan.setDept(dept);

            plans.add(plan);
        }
    } catch (SQLException ex) {
        Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
        try {
            if (stm != null) stm.close();
            if (connection != null) connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(PlanDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    return plans;
}


    public static void main(String[] args) {
        PlanDBContext planDB = new PlanDBContext();
        List<Product> products = planDB.getProductsByplanID(1);
        System.out.println(products);
    }

}
