/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import entity.production.PlanCampaign;
import entity.production.SchedualCampaign;
import java.util.ArrayList;

import entity.production.SchedualCampaign;
import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class SchedualCampaignDBContext extends DBContext<SchedualCampaign> {

    public void deleteByIdPlanAndDate(int planID, List<Date> oldDates) {
        if (oldDates.isEmpty()) {
            return;  // Nếu danh sách ngày cũ trống, không cần xóa gì cả
        }
        // Tạo một câu truy vấn để xóa với các ngày được chỉ định trong oldDates
        StringBuilder sql = new StringBuilder("DELETE FROM SchedualCampaign WHERE PlanCampnID IN ");
        sql.append("(SELECT PlanCampnID FROM PlanCampaign WHERE PlanID = ?) AND Date IN (");

        // Thêm từng dấu chấm hỏi cho mỗi ngày để dùng trong PreparedStatement
        for (int i = 0; i < oldDates.size(); i++) {
            sql.append("?");
            if (i < oldDates.size() - 1) {
                sql.append(", ");
            }
        }
        sql.append(")");

        try (PreparedStatement stm = connection.prepareStatement(sql.toString())) {
            stm.setInt(1, planID);  // Thiết lập tham số planID

            // Thiết lập các ngày trong danh sách oldDates cho PreparedStatement
            for (int i = 0; i < oldDates.size(); i++) {
                stm.setDate(i + 2, oldDates.get(i));  // +2 vì planID là tham số đầu tiên
            }

            // Thực hiện câu lệnh xóa
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SchedualCampaignDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void insert(SchedualCampaign entity) {
        String sql = "INSERT INTO SchedualCampaign (PlanCampnID, Date, Shift, Quantity) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // Set the parameters for the insert statement
            stm.setInt(1, entity.getPlanCampaign().getId());
            stm.setDate(2, entity.getDate());
            stm.setInt(3, entity.getShift());
            stm.setInt(4, entity.getQuantity());

            // Execute the insert
            stm.executeUpdate();

            // Retrieve and set the generated scID (primary key) if needed
            ResultSet generatedKeys = stm.getGeneratedKeys();
            if (generatedKeys.next()) {
                entity.setScID(generatedKeys.getInt(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SchedualCampaignDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(SchedualCampaign entity) {
        String sql = "UPDATE SchedualCampaign SET PlanCampnID = ?, Date = ?, Shift = ?, Quantity = ? WHERE scID = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            // Thiết lập các tham số cho câu lệnh cập nhật
            stm.setInt(1, entity.getPlanCampaign().getId());  // PlanCampnID
            stm.setDate(2, entity.getDate());                 // Date
            stm.setInt(3, entity.getShift());                 // Shift
            stm.setInt(4, entity.getQuantity());              // Quantity
            stm.setInt(5, entity.getScID());                  // scID (điều kiện WHERE)

            // Thực hiện cập nhật
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SchedualCampaignDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(SchedualCampaign entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<SchedualCampaign> list() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public SchedualCampaign get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public ArrayList<SchedualCampaign> list(PlanCampaign planCampaign) {
        ArrayList<SchedualCampaign> schedualCampaigns = new ArrayList<>();
        String sql = "SELECT scID, PlanCampnID, Date, Shift, Quantity FROM SchedualCampaign WHERE PlanCampnID = ?";

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, planCampaign.getId());
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                // Tạo một đối tượng SchedualCampaign mới
                SchedualCampaign schedualCampaign = new SchedualCampaign();
                schedualCampaign.setScID(rs.getInt("scID"));

                // Thiết lập PlanCampaign cho SchedualCampaign
                schedualCampaign.setPlanCampaign(planCampaign);

                // Thiết lập Date, Shift và Quantity
                schedualCampaign.setDate(rs.getDate("Date"));
                schedualCampaign.setShift(rs.getInt("Shift"));
                schedualCampaign.setQuantity(rs.getInt("Quantity"));

                // Thêm đối tượng SchedualCampaign vào danh sách
                schedualCampaigns.add(schedualCampaign);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SchedualCampaignDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }

        return schedualCampaigns;
    }

}
